package com.pfh.openeyes.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pfh.openeyes.R;
import com.pfh.openeyes.model.Discovery;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.ui.adapter.FeedAdapter;
import com.pfh.openeyes.ui.base.BaseFragment;
import com.pfh.openeyes.util.DensityUtils;
import com.pfh.openeyes.util.LogUtil;
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
 * Created by Administrator on 2016/9/10.
 */
public class RankListFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.tv_weekly)
    TextView tv_weekly;
    @BindView(R.id.tv_monthly)
    TextView tv_monthly;
    @BindView(R.id.tv_history)
    TextView tv_history;
    @BindView(R.id.iv_top)
    ImageView iv_top;
    @BindView(R.id.iv_bottom)
    ImageView iv_bottom;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private String[] strategyArray = {"weekly","monthly","historical"};
    private Map<Integer,List<FeedItem>> mData;
    private List<RecyclerView> recyclerViewList;
    private int perDistance;
    private float startX;
    private int leftMargin;

    public static RankListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        RankListFragment fragment = new RankListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranklist, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerViewList = new ArrayList<>();
        mData = new HashMap<>();

        toolbar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        initRecyclerView();
        loadData();
        initIndicator();
        initViewPager();
    }

    private void initRecyclerView() {
        List<FeedItem> tempList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RecyclerView recyclerView = new RecyclerView(mContext);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            FeedAdapter feedAdapter = new FeedAdapter(tempList, mContext);
            recyclerView.setAdapter(feedAdapter);
            recyclerViewList.add(recyclerView);
        }
    }

    private void loadData() {

        for ( int i = 0; i < 3; i++) {
            final int index = i;

            apiStores.loadRankList("10",strategyArray[index])
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Discovery>() {
                        @Override
                        public void onCompleted() {
                            FeedAdapter feedAdapter = (FeedAdapter) recyclerViewList.get(index).getAdapter();
                            feedAdapter.refreshData(mData.get(index));
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.e("RankListFragment network error:"+e.getMessage());

                        }

                        @Override
                        public void onNext(Discovery discovery) {
                            mData.put(index,discovery.getItemList());
                        }
                    });
        }

    }



    private void initIndicator() {

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels =  metrics.widthPixels;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_top.getLayoutParams();
        leftMargin = layoutParams.leftMargin;
        startX = iv_top.getX() + leftMargin;
        int length = DensityUtils.dp2px(mContext,50);
        perDistance = (int) ((widthPixels - 2*startX - length )/2);

    }

    private void initViewPager() {

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return recyclerViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(recyclerViewList.get(position));
                return recyclerViewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(recyclerViewList.get(position));
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
}
