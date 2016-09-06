package com.pfh.openeyes.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pfh.openeyes.R;
import com.pfh.openeyes.model.Feed;
import com.pfh.openeyes.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/9/4.
 */
public class FeedDetailActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.ll_blur_bg)
    LinearLayout ll_blur_bg;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_category_duration)
    TextView tv_category_duration;
    @BindView(R.id.iv_arrow_up)
    ImageView iv_arrow_up;
    @BindView(R.id.tv_description)
    TextView tv_description;
    @BindView(R.id.ll_consumption)
    LinearLayout ll_consumption;
    @BindView(R.id.iv_collectionCount)
    ImageView iv_collectionCount;
    @BindView(R.id.tv_collectionCount)
    TextView tv_collectionCount;
    @BindView(R.id.iv_shareCount)
    ImageView iv_shareCount;
    @BindView(R.id.tv_shareCount)
    TextView tv_shareCount;
    @BindView(R.id.iv_replyCount)
    ImageView iv_replyCount;
    @BindView(R.id.tv_replyCount)
    TextView tv_replyCount;
    @BindView(R.id.iv_download)
    ImageView iv_download;

    public static final String FEED_MAP = "feed_map";
    public static final String GROUP_SIZE = "group_size";
    public static final String GROUP_INDEX = "group_index";
    private int currentGroupSize;
    private int indexInGroup;
    private HashMap<Integer, Feed> feedMap;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);
        initData();
        initViewPager();
    }

    private void initData() {

        currentGroupSize = getIntent().getIntExtra(GROUP_SIZE, 4);
        indexInGroup = getIntent().getIntExtra(GROUP_INDEX, 0);
        feedMap = (HashMap<Integer, Feed>) getIntent().getSerializableExtra(FEED_MAP);
        fragmentList = new ArrayList<>();


    }

    private void initViewPager() {
        for (int i = 0; i < currentGroupSize; i++) {

//            fragmentList.add(FeedDetailFragment.newInstance());
        }

    }
}
