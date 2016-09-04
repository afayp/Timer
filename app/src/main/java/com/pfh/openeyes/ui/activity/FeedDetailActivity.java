package com.pfh.openeyes.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pfh.openeyes.R;
import com.pfh.openeyes.ui.base.BaseActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);
        initData();
        initViewPager();
    }

    private void initData() {

    }

    private void initViewPager() {

    }
}
