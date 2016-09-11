package com.pfh.openeyes.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pfh.openeyes.R;
import com.pfh.openeyes.model.Discovery;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.ui.adapter.FeedAdapter;
import com.pfh.openeyes.ui.base.BaseFragment;
import com.pfh.openeyes.util.LogUtil;
import com.pfh.openeyes.widget.CustomRecyclerview;
import com.pfh.openeyes.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/11.
 */
public class SpecialTopicsFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.recyclerview)
    CustomRecyclerview recyclerView;

    private List<FeedItem> feedItemList;
    private FeedAdapter feedAdapter;
    private String nextPageUrl;
    private int start = 0;
    private int num = 10;
    private LinearLayoutManager layoutManager;
    private boolean isLoadingMore;

    public static SpecialTopicsFragment newInstance() {

        Bundle args = new Bundle();

        SpecialTopicsFragment fragment = new SpecialTopicsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_topics, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initRecyclerview();

        loadData();

        toolbar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

    }

    private void initRecyclerview() {
        feedItemList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        feedAdapter = new FeedAdapter(feedItemList, mContext);
        recyclerView.setAdapter(feedAdapter);
        feedAdapter.setOnItemClickListener(new FeedAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, FeedItem feedItem, int position) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int itemCount = layoutManager.getItemCount();

                if (itemCount - lastVisibleItemPosition < 5 && dy > 0) {
                    if (start != -1){
                        if (isLoadingMore) {
                            //正在加载
                        } else {
                            loadData();
                        }
                    }

                }
            }
        });
    }

    private void loadData() {
        isLoadingMore = true;

        apiStores.loadSpecialTopics(start+"",num+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Discovery>() {
                    @Override
                    public void onCompleted() {
                        isLoadingMore = false;
                        FeedAdapter feedAdapter = (FeedAdapter) recyclerView.getAdapter();
                        feedAdapter.refreshData(feedItemList);

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("SpecialTopicsFragment network error:" + e.getMessage());
                        isLoadingMore = false;

                    }

                    @Override
                    public void onNext(Discovery discovery) {
                        feedItemList.addAll(discovery.getItemList());
                        if (discovery.getNextPageUrl() != null){
                            start += 10;
                        }else {
                            start = -1;
                        }
                    }
                });

    }

    private String getDate() {
        // http://baobab.wandoujia.com/api/v2/feed?date=1472918400000&num=1
        // http://baobab.wandoujia.com/api/v3/specialTopics?start=10&num=10
        int index1 = nextPageUrl.indexOf("start");
        int index2 = nextPageUrl.indexOf("&", index1);
        String date = nextPageUrl.substring(index1 + 5, index2);
        return date;
    }

}
