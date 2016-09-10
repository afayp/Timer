package com.pfh.openeyes.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.pfh.openeyes.R;
import com.pfh.openeyes.event.DetailFragmentEvent;
import com.pfh.openeyes.ui.base.BaseActivity;
import com.pfh.openeyes.ui.fragment.RankListFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016/9/10.
 */
public class DetailActivity extends BaseActivity {


    private FragmentManager manager;
    private FragmentTransaction transaction;

    private String currentType;
    public static final String TYPE = "type";
    public static final String TYPE_RANK_LIST = "rankList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        currentType = getIntent().getStringExtra(TYPE);
        switch (currentType){
            case TYPE_RANK_LIST:
                RankListFragment rankListFragment = RankListFragment.newInstance();
                replaceContentFragment(rankListFragment);
                break;
        }


    }

    public void replaceContentFragment(Fragment fragment){
        Log.e("TAG","replaceContentFragment");
        transaction
                .setCustomAnimations(R.anim.fragment_slide_left_in, R.anim.fragment_slide_left_out,
                        R.anim.fragment_slide_right_in, R.anim.fragment_slide_right_out)
                .add(R.id.fl_content,fragment,"One")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,priority = 1,sticky = true)
    public void handleDetailFragmentEvent(DetailFragmentEvent event){
        Log.e("TAG","replaceContentFragment");
        replaceContentFragment(event.getFragment());
    }
}
