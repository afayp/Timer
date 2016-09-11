package com.pfh.openeyes.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pfh.openeyes.R;
import com.pfh.openeyes.model.Discovery;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.ui.adapter.FeedAdapter;
import com.pfh.openeyes.ui.base.BaseFragment;
import com.pfh.openeyes.util.DensityUtils;
import com.pfh.openeyes.util.LogUtil;
import com.pfh.openeyes.widget.CustomRecyclerview;
import com.pfh.openeyes.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/11.
 */
public class CategoryFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.iv_top)
    ImageView iv_top;
    @BindView(R.id.iv_bottom)
    ImageView iv_bottom;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    public static final String FEED = "feed";
    private String[] strategys = {"date","shareCount"};
    private int id;
    private FeedItem feedItem;
    private Map<Integer,List<FeedItem>> mData;
    private List<CustomRecyclerview> recyclerviewList;
    private int leftMargin;
    private float startX;
    private int perDistance;

    public static CategoryFragment newInstance(FeedItem feedItem) {

        Bundle args = new Bundle();
        args.putSerializable(FEED,feedItem);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        feedItem = (FeedItem) getArguments().getSerializable(FEED);
        toolbar.setCenterText(feedItem.getData().getTitle());
        toolbar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        id = feedItem.getData().getId();
        Log.e("TAG","id: "+id);

        initRecyclerView();
        loadData();
        initIndicator();
        initViewPager();

    }

    private void initViewPager() {
        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return recyclerviewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(recyclerviewList.get(position));
                return recyclerviewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(recyclerviewList.get(position));
            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                iv_top.setX(startX  + position*perDistance + positionOffset*perDistance);
                iv_bottom.setX(startX  + position*perDistance + positionOffset*perDistance);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initIndicator() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels =  metrics.widthPixels;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_top.getLayoutParams();
        leftMargin = layoutParams.leftMargin;
        startX = iv_top.getX() + leftMargin;
        int length = DensityUtils.dp2px(mContext,72);
        perDistance = (int) (widthPixels - 2*startX - length );
    }

    private void initRecyclerView() {
        recyclerviewList = new ArrayList<>();
        List<FeedItem> tempList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            CustomRecyclerview recyclerview = new CustomRecyclerview(mContext);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview.setLayoutManager(linearLayoutManager);
            FeedAdapter feedAdapter = new FeedAdapter(tempList, mContext);
            recyclerview.setAdapter(feedAdapter);
            recyclerviewList.add(recyclerview);
        }
    }

    private void loadData() {
        mData = new HashMap<>();
        for (int i = 0; i < strategys.length; i++) {
            final int index = i;
            apiStores.loadCategory(id+"",strategys[index],40+"")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Discovery>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.e("CategtoryFragment network error:"+e.getMessage());

                        }

                        @Override
                        public void onNext(Discovery discovery) {
                            mData.put(index,discovery.getItemList());
                            FeedAdapter feedadapter = (FeedAdapter) recyclerviewList.get(index).getAdapter();
                            feedadapter.refreshData(discovery.getItemList());
                        }
                    });
        }
    }
}
