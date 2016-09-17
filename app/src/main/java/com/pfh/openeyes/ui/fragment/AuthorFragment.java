package com.pfh.openeyes.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pfh.openeyes.R;
import com.pfh.openeyes.model.Discovery;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.ui.activity.AuthorDetailActivity;
import com.pfh.openeyes.ui.adapter.FeedAdapter;
import com.pfh.openeyes.ui.base.BaseFragment;
import com.pfh.openeyes.util.DividerItemDecoration;
import com.pfh.openeyes.util.LogUtil;
import com.pfh.openeyes.widget.CustomRecyclerview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/3.
 */
public class AuthorFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    CustomRecyclerview recyclerview;

    private List<FeedItem> feedItemList;
    private FeedAdapter feedAdapter;

    public static AuthorFragment newInstance() {

        Bundle args = new Bundle();

        AuthorFragment fragment = new AuthorFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_author, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.e("TAG","AuthorFragment onViewCreated!");
        loadData();
    }

    private void initRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
        feedAdapter = new FeedAdapter(feedItemList, mContext);
        recyclerview.setAdapter(feedAdapter);

        feedAdapter.setOnItemClickListener(new FeedAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, FeedItem feedItem, int position) {
                if (feedItem.getData().getDataType().equals("BriefCard")){
                    Intent intent = new Intent(mContext, AuthorDetailActivity.class);
                    intent.putExtra(AuthorDetailActivity.PGCID,feedItem.getData().getId());
                    startActivity(intent);
                }else if (feedItem.getData().getDataType().equals("VideoCollection") ){

                }
            }
        });
    }

    private void loadData() {
        feedItemList = new ArrayList<>();
        apiStores.loadAuthorData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Discovery>() {
                    @Override
                    public void onCompleted() {
                        initRecyclerView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("AuthorFragment network error:"+e.getMessage());
                    }

                    @Override
                    public void onNext(Discovery discovery) {
                        feedItemList.addAll(discovery.getItemList());
//                        FeedAdapter feedAdapter = (FeedAdapter) recyclerview.getAdapter();
//                        feedAdapter.refreshData(feedItemList);
                    }
                });

    }
}
