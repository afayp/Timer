package com.pfh.openeyes.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pfh.openeyes.R;
import com.pfh.openeyes.event.ChangeDateEvent;
import com.pfh.openeyes.model.Data;
import com.pfh.openeyes.model.Feed;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.model.IssueList;
import com.pfh.openeyes.ui.adapter.FeedAdapter;
import com.pfh.openeyes.ui.base.BaseFragment;
import com.pfh.openeyes.util.LogUtil;
import com.pfh.openeyes.util.TimeUtils;
import com.pfh.openeyes.widget.CustomRecyclerview;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 精选tab页
 */
public class FeedFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    CustomRecyclerview recyclerView;

    private FeedAdapter feedAdapter;
    private List<FeedItem> allFeedItemList;//所有的feedItem

//    private List<List<FeedItem>> allFeedItemListWithIndex;

    private HashMap<Integer, Feed> feedMap;//保存每天对应的货

    private String nextPageUrl;
    private int count = 1;//一共有几天的货
//    private List<Integer> sizeEveryTime = new ArrayList<>();//每次加载对应的count，从1开始
    private HashMap<Integer,Integer> sizeEveryTime = new HashMap<>();
    private LinearLayoutManager layoutManager;
    private boolean isLoadingMore;
//    private long currentDate;


    public static FeedFragment newInstance() {

        Bundle args = new Bundle();
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        feedMap = new HashMap<>();
        allFeedItemList = new ArrayList<>();
        feedAdapter = new FeedAdapter(allFeedItemList, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(feedAdapter);
        feedAdapter.setOnItemClickListener(new FeedAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, FeedItem feedItem, int position) {
                if (feedItem.getData().getDataType().equals("Banner")) {
                    //gotoWebView();
                } else if (feedItem.getData().getDataType().equals("VideoBeanForClient")) {

                    //

                    int groupSize = 0;//这一组的数量
                    int indexInGroup = 0;//点击的一项在这组itemList中是什么位置,从0开始
                    int index = 0;//第几天的货

                    int sum = 0;
                    for (int i = 1; i < sizeEveryTime.size(); i++) {
                        sum += sizeEveryTime.get(i);
                        if (position < sum) {
                            groupSize = sizeEveryTime.get(i);
                            index = i;
                            if (i == 1) {
                                indexInGroup = position;
                            } else {
                                indexInGroup = position - (sum - sizeEveryTime.get(i - 1));
                            }
                            break;
                        }
                    }

                    List<FeedItem> itemList = feedMap.get(index).getIssueList().get(0).getItemList();
                    LogUtil.e("groupSize: "+groupSize+"/n"+"indexInGroup: "+indexInGroup+"/n"+"index: "+index);
                    LogUtil.e("itemList: "+itemList.toString());

//                    for (int i = 0; i < itemList.size(); i++) {
//                        if (itemList.get(i).getData().getId() == feedItem.getData().getId()) {
//                            indexInGroup = i;
//                            break;
//                        }
//                    }

//                    Intent intent = new Intent(getActivity(), FeedDetailActivity.class);
//                    intent.putExtra(FeedDetailActivity.GROUP_SIZE, groupSize);
//                    intent.putExtra(FeedDetailActivity.GROUP_SIZE, indexInGroup);
//                    intent.putExtra(FeedDetailActivity.FEED_MAP, feedMap);
//                    startActivity(intent);
                }
            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                Data data = allFeedItemList.get(firstVisibleItemPosition).getData();
                if (data.getDataType().equals("TextHeader")) {
                    String date = TimeUtils.convertDate(allFeedItemList.get(firstVisibleItemPosition + 1).getData().getDate());
                    changeToolbarDate(date);
                }
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int itemCount = layoutManager.getItemCount();

                if (itemCount - lastVisibleItemPosition < 3 && dy > 0) {
                    if (isLoadingMore) {
                        //正在加载
                    } else {
                        isLoadingMore = true;
                        loadMore();
                    }
                }

            }
        });

        loadFirst();

    }

    public void loadMore() {
        apiStores.loadFeedNextPage(getDate(), "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Feed>() {
                    @Override
                    public void onCompleted() {
                        isLoadingMore = false;
                    }

                    @Override
                    public void onError(Throwable e) {
//                        isLoadingMore = false;
                        LogUtil.e("FeedFragment NetWork error 2: " + e.getMessage());

                    }

                    @Override
                    public void onNext(Feed feed) {
                        handleFeed(feed);
                    }
                });


    }


    public void loadFirst() {
        //默认每次加载1页，1个为Banner或者TextHeader,其余为视频
        apiStores.loadFeedFirst("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Feed>() {
                    @Override
                    public void onCompleted() {
                        isLoadingMore = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingMore = false;
                        LogUtil.e("FeedFragment NetWork error 1: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Feed feed) {
                        LogUtil.e("FeedFragment get ok  ");
                        handleFeed(feed);
                    }
                });
    }

    private void changeToolbarDate(String date) {
        EventBus.getDefault().post(new ChangeDateEvent(date));
    }

    private void handleFeed(Feed feed) {
        IssueList issueList = feed.getIssueList().get(0);

        feedMap.put(count, feed);
        nextPageUrl = feed.getNextPageUrl();
//        sizeEveryTime.put(count,issueList.getCount());//第一条一般都是Banner或者TextHeader
//        sizeEveryTime.add(count, issueList.getCount());
        allFeedItemList.addAll(issueList.getItemList());//因为num为1，所以这里只有一个ItemList
        feedAdapter.refreshData(allFeedItemList);
    }

    private String getDate() {
        // http://baobab.wandoujia.com/api/v2/feed?date=1472918400000&num=1
        int index1 = nextPageUrl.indexOf("date");
        int index2 = nextPageUrl.indexOf("&", index1);
        String date = nextPageUrl.substring(index1 + 5, index2);
        Log.e("TAG",date);
        return date;
    }
}


