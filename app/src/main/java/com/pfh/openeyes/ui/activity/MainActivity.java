package com.pfh.openeyes.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.pfh.openeyes.R;
import com.pfh.openeyes.event.ChangeDateEvent;
import com.pfh.openeyes.ui.base.BaseActivity;
import com.pfh.openeyes.ui.fragment.AuthorFragment;
import com.pfh.openeyes.ui.fragment.DiscoveryFragment;
import com.pfh.openeyes.ui.fragment.FeedFragment;
import com.pfh.openeyes.ui.fragment.UserFragment;
import com.pfh.openeyes.widget.CustomToolbar;
import com.pfh.openeyes.widget.MainActivityIndicator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.indicator)
    MainActivityIndicator mIndicator;

    // toolba对应四个Fragment
    public static final int TOOLBAR_FEED = 0;
    public static final int TOOLBAR_DISCOVERY = 0;
    public static final int TOOLBAR_AUTHOR = 0;
    public static final int TOOLBAR_USER = 0;

    private int TOOLBAR_STATE = TOOLBAR_FEED;



    private List<android.support.v4.app.Fragment> fragmentList;
    private int statusBarHeight;//?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initViewPager();
        initIndicator();
    }

    private void initToolbar() {
        mToolbar.getRightIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
//                overridePendingTransition(R.anim.top_in,R.anim.stay);
//                startActivity(new Intent(MainActivity.this,SearchActivity.class),
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
    }

    private void initIndicator() {
        mIndicator.setIndicatorListener(new MainActivityIndicator.IndicatorListener() {
            @Override
            public void onClickInidcator(int position) {
                mViewPager.setCurrentItem(position);
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Rect rect= new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        statusBarHeight = rect.top;
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        FeedFragment feedFragment = FeedFragment.newInstance();
        DiscoveryFragment discoveryFragment = DiscoveryFragment.newInstance();
        AuthorFragment authorFragment = AuthorFragment.newInstance();
        UserFragment userFragment = UserFragment.newInstance();
        fragmentList.add(feedFragment);
        fragmentList.add(discoveryFragment);
        fragmentList.add(authorFragment);
        fragmentList.add(userFragment);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.setupIndicator(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 1,sticky = true)
    public void handleChangeDateEvent(ChangeDateEvent event){
        if (TOOLBAR_STATE == TOOLBAR_FEED){
            mToolbar.setLeftText(event.getDate());
        }

    }


}
