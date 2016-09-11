package com.pfh.openeyes.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.pfh.openeyes.R;
import com.pfh.openeyes.event.DetailFragmentEvent;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.ui.base.BaseActivity;
import com.pfh.openeyes.ui.fragment.CategoryFragment;
import com.pfh.openeyes.ui.fragment.PanoramaFragment;
import com.pfh.openeyes.ui.fragment.RankListFragment;
import com.pfh.openeyes.ui.fragment.SpecialTopicsFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016/9/10.
 */
public class DetailActivity extends BaseActivity {


    private FragmentManager manager;

    private String currentType;
    public static final String TYPE = "type";
    public static final String FEED_ITEM = "feedItem";
    public static final String TYPE_RANK_LIST = "rankList";//最受欢迎
    public static final String TYPE_SPECIAL_TOPICS = "specialTopics";//最受欢迎
    public static final String TYPE_PANORAMA = "panorama";//最受欢迎
    public static final String TYPE_CATEGORY = "category";//最受欢迎

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        manager = getSupportFragmentManager();

        currentType = getIntent().getStringExtra(TYPE);
        switch (currentType) {
            case TYPE_RANK_LIST:
                replaceContentFragment(RankListFragment.newInstance());
                break;
            case TYPE_SPECIAL_TOPICS:
                replaceContentFragment(SpecialTopicsFragment.newInstance());
                break;
            case TYPE_PANORAMA:
                replaceContentFragment(PanoramaFragment.newInstance());
                break;
            case TYPE_CATEGORY:
                FeedItem feedItem = (FeedItem) getIntent().getSerializableExtra(FEED_ITEM);
                replaceContentFragment(CategoryFragment.newInstance(feedItem));
                break;
        }


    }

    public void replaceContentFragment(Fragment fragment) {
        Log.e("TAG", "replaceContentFragment");

        manager.beginTransaction()
//                .setCustomAnimations(R.anim.fragment_slide_left_in, R.anim.fragment_slide_left_out,
//                        R.anim.fragment_slide_right_in, R.anim.fragment_slide_right_out)
                .add(R.id.fl_content, fragment, "kkk")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1, sticky = true)
    public void handleDetailFragmentEvent(DetailFragmentEvent event) {
        replaceContentFragment(event.getFragment());
    }
}
