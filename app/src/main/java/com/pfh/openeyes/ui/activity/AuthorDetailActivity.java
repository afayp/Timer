package com.pfh.openeyes.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pfh.openeyes.R;
import com.pfh.openeyes.model.Discovery;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.model.PgcInfo;
import com.pfh.openeyes.ui.adapter.FeedAdapter;
import com.pfh.openeyes.ui.base.BaseActivity;
import com.pfh.openeyes.util.DensityUtils;
import com.pfh.openeyes.widget.CustomRecyclerview;
import com.pfh.openeyes.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/17.
 */
public class AuthorDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    CustomToolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_description)
    TextView tv_description;
    @BindView(R.id.iv_top)
    ImageView iv_top;
    @BindView(R.id.iv_bottom)
    ImageView iv_bottom;


    public static final String PGCID = "pgcId";
    public static final String PGCINFO = "pgcInfo";
    private String pgcId;
    private String[] strategys = {"date","shareCount"};
    private HashMap<String,List<FeedItem>> mData = new HashMap<>();
    private List<CustomRecyclerview> recyclerviewList;
    private PgcInfo pgcInfo;
    private String nextPageUrl;
    private int leftMargin;
    private float startX;
    private int perDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_detail);
        pgcId = getIntent().getStringExtra(PGCID);
        pgcInfo = (PgcInfo) getIntent().getSerializableExtra(PGCINFO);
        initTop();
        initRecyclerView();
        loadData();
        initIndicator();
        initViewpager();

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


    private void initIndicator() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels =  metrics.widthPixels;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_top.getLayoutParams();
        leftMargin = layoutParams.leftMargin;
        startX = iv_top.getX() + leftMargin;
        int length = DensityUtils.dp2px(mContext,72);
        perDistance = (int) (widthPixels - 2*startX - length );
    }

    private void initViewpager() {
        viewPager.setAdapter(new PagerAdapter() {
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

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                iv_top.setX(startX  + position*perDistance + positionOffset*perDistance);
                iv_bottom.setX(startX  + position*perDistance + positionOffset*perDistance);
            }
        });

    }

    private void initTop() {
        Glide.with(mContext)
                .load(pgcInfo.getIcon())
                .into(iv_icon);
        tv_name.setText(pgcInfo.getName());
        tv_description.setText(pgcInfo.getDescription());
        toolbar.getLeftIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData() {
        for (int i = 0; i < strategys.length; i++) {
            final int index = i;
            apiStores.loadAuthorDetail(pgcId,strategys[index],"40")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Discovery>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Discovery discovery) {
                            mData.put(strategys[index],discovery.getItemList());
                            FeedAdapter feedAdapter = (FeedAdapter) recyclerviewList.get(index).getAdapter();
                            feedAdapter.refreshData(discovery.getItemList());
                        }
                    });

        }
    }
}
