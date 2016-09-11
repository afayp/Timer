package com.pfh.openeyes.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
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
public class PanoramaFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.iv_top)
    ImageView iv_top;
    @BindView(R.id.iv_bottom)
    ImageView iv_bottom;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private int leftMargin;
    private float startX;
    private int perDistance;
    private List<CustomRecyclerview> recyclerViewList;
    private String[] strategys = {"date","shareCount"};
    private Map<String,List<FeedItem>> mData;

    public static PanoramaFragment newInstance() {

        Bundle args = new Bundle();

        PanoramaFragment fragment = new PanoramaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_panorama, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initRecyclerView();
        loadData();
        initIndicator();
        initViewPager();

        toolbar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        toolbar.getRightIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("share!");
            }
        });
    }

    private void loadData() {
        mData = new HashMap<>();
        for (int i = 0; i < strategys.length; i++) {
            final int index = i;
            apiStores.loadPanorama("658",strategys[index],"40")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Discovery>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.e("PanoramaFragment network error:"+e.getMessage());

                        }

                        @Override
                        public void onNext(Discovery discovery) {
                            mData.put(strategys[index],discovery.getItemList());
                            FeedAdapter feedAdapter = (FeedAdapter) recyclerViewList.get(index).getAdapter();
                            feedAdapter.refreshData(discovery.getItemList());
                        }
                    });

        }
    }

    private void initRecyclerView() {
        recyclerViewList = new ArrayList<>();
        List<FeedItem> tempList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            CustomRecyclerview recyclerview = new CustomRecyclerview(mContext);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerview.setLayoutManager(linearLayoutManager);
            FeedAdapter feedAdapter = new FeedAdapter(tempList, mContext);
            recyclerview.setAdapter(feedAdapter);
            recyclerViewList.add(recyclerview);
        }
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

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                iv_top.setX(startX  + position*perDistance + positionOffset*perDistance);
                iv_bottom.setX(startX  + position*perDistance + positionOffset*perDistance);
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
}
