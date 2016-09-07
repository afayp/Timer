package com.pfh.openeyes.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.pfh.openeyes.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 */
public class Banner extends RelativeLayout {
    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;

    private List<String> mPicUrls;//图片的url
    private List<ImageView> mImageViewList;//viewpager中的imageview
    private List<ImageView> mIndicatorList;
    private int mCount;//有几项item
    private Context mContext;
    private LinearLayout pointContainer;//指示器的容器
    private ViewPager mViewPager;
    private int mCurrentItem = 0;
    private long duration = 1000;

    public Banner(Context context) {
        this(context,null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();

    }

    /**
     *
     */
    private void initView() {
        //viewpger
        mViewPager = new ViewPager(mContext);
        LayoutParams lp_viewpager = new LayoutParams(RMP, RMP);
        addView(mViewPager,lp_viewpager);

        //底部的小圆点指示器的容器
        pointContainer = new LinearLayout(mContext);
        pointContainer.setOrientation(LinearLayout.HORIZONTAL);
//        pointContainer.setPadding(10,10,10,10);

        pointContainer.setBackgroundResource(R.color.skyblue);
        LayoutParams lp_indicator = new LayoutParams(RMP, 20);
        lp_indicator.addRule(ALIGN_PARENT_BOTTOM);
        lp_indicator.setMargins(0,0,0,20);
//        lp_indicator.addRule(CENTER_HORIZONTAL);
        addView(pointContainer,lp_indicator);


    }

    public void setPicUrls(List<String> picUrls){
        this.mPicUrls = picUrls;
        mCount = picUrls.size();
        mImageViewList = new ArrayList<>();

        for (int i = 0; i < mPicUrls.size(); i++) {
            ImageView iv = new ImageView(mContext);
            mImageViewList.add(iv);
        }
        loadPicFromNet();

        initViewPager();

        initIndicator();

        startPlay();

    }

    private void loadPicFromNet() {
        for (int i = 0; i < mPicUrls.size(); i++) {
            Glide.with(mContext)
                    .load(mPicUrls.get(i))
                    .centerCrop()
                    .into(mImageViewList.get(i));
        }

    }

    private void initViewPager() {
        mViewPager.setAdapter(new MyAdapter());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {//position 从0开始
                //更新Indicator
                for (int i = 0; i < mIndicatorList.size(); i++) {
                    if (i != position){
                        mIndicatorList.get(i).setBackgroundResource(R.drawable.circle_indicator_shape);
                    }else {
                        mIndicatorList.get(i).setBackgroundResource(R.drawable.circle_indicator_shape_select);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    Runnable task = new Runnable() {
        @Override
        public void run() {
//            mCurrentItem = mCurrentItem +1;
//            if (mCurrentItem > mCount - 1){
//                mCurrentItem = 0;
//            }
            mCurrentItem = mCurrentItem % mCount + 1;
            if (mCurrentItem == 1){
                mViewPager.setCurrentItem(mCurrentItem,false);//最后一页到第一页，快速转到
            }else {
                mViewPager.setCurrentItem(mCurrentItem,true);
            }
            postDelayed(task,duration);
        }
    };

    public void startPlay(){
        stopPlay();
        postDelayed(task,duration);
    }

    private void stopPlay() {
        removeCallbacks(task);
    }

    /**
     * 画指示器，这里是小圆点
     */
    private void initIndicator() {
        if (pointContainer != null){
            pointContainer.removeAllViews();
        }
        ImageView iv_indicator;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        lp.setMargins(5,5,5,5);

        mIndicatorList = new ArrayList<>();
        for (int i = 0; i < mCount; i++) {
            iv_indicator = new ImageView(mContext);
//            iv_indicator.setPadding(5,5,5,5);
//            iv_indicator.setLayoutParams(lp);
            iv_indicator.setBackgroundResource(R.drawable.circle_indicator_shape);

            pointContainer.addView(iv_indicator,lp);
            mIndicatorList.add(iv_indicator);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                stopPlay();
                break;
            case MotionEvent.ACTION_UP:
                startPlay();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }


    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViewList.get(position));
        }
    }
}
