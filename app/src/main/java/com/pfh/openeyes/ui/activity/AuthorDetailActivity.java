package com.pfh.openeyes.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.pfh.openeyes.R;
import com.pfh.openeyes.model.Discovery;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.model.PgcInfo;
import com.pfh.openeyes.ui.base.BaseActivity;
import com.pfh.openeyes.widget.CustomToolbar;

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

    public static final String PGCID = "pgcId";
    private String pgcId;
    private String[] strategys = {"date","shareCount"};
    private HashMap<String,List<FeedItem>> mData = new HashMap<>();
    private PgcInfo pgcInfo;
    private String nextPageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_detail);
        pgcId = getIntent().getStringExtra(PGCID);
        loadData();

    }

    private void loadData() {
        for (int i = 0; i < strategys.length; i++) {
            final int index = i;
            apiStores.loadAuthorDetail(strategys[index],"date","10")
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
                            pgcInfo = discovery.getPgcInfo();
                            mData.put(strategys[index],discovery.getItemList());
                        }
                    });

        }
    }
}
