package com.pfh.openeyes.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pfh.openeyes.R;
import com.pfh.openeyes.model.Discovery;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.ui.activity.DetailActivity;
import com.pfh.openeyes.ui.adapter.CategoryAdapter;
import com.pfh.openeyes.ui.base.BaseFragment;
import com.pfh.openeyes.util.DividerGridItemDecoration;
import com.pfh.openeyes.util.LogUtil;
import com.pfh.openeyes.widget.Banner;
import com.pfh.openeyes.widget.ShowAllRecyclerView;

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
public class DiscoveryFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    ShowAllRecyclerView recyclerView;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.iv_rankList)
    ImageView iv_rankList;
    @BindView(R.id.iv_specialTopics)
    ImageView iv_specialTopics;
    @BindView(R.id.iv_360)
    ImageView iv_360;

    private List<FeedItem> HorizontalScrollCard;//banner
    private List<FeedItem> rankAndTopic;//最受欢迎和热门专题
    private List<FeedItem> rectangleCard;//360
    private List<FeedItem> categories;//分类


    public static DiscoveryFragment newInstance() {

        Bundle args = new Bundle();

        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        loadData();
    }

    private void loadData() {

        apiStores.loadDiscovery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Discovery>() {
                    @Override
                    public void onCompleted() {

                        initBanner();
                        initImg();
                        initCategories();

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("Discovery Fragment network error: " + e.getMessage());

                    }

                    @Override
                    public void onNext(Discovery discovery) {
                        handleData(discovery);
                    }
                });
    }

    private void initBanner(){
        List<String> urls = new ArrayList<String>();
        for (int i = 0; i < HorizontalScrollCard.size(); i++) {
            urls.add(HorizontalScrollCard.get(i).getData().getImage());
        }
        banner.setPicUrls(urls);
    }

    private void initImg(){

        Glide.with(mContext)
                .load(rankAndTopic.get(0).getData().getImage())
                .centerCrop()
                .into(iv_rankList);
        Glide.with(mContext)
                .load(rankAndTopic.get(1).getData().getImage())
                .centerCrop()
                .into(iv_specialTopics);
        Glide.with(mContext)
                .load(rectangleCard.get(0).getData().getImage())
                .centerCrop()
                .into(iv_360);

        iv_rankList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.TYPE,DetailActivity.TYPE_RANK_LIST);
                startActivity(intent);
            }
        });

        iv_specialTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.TYPE,DetailActivity.TYPE_SPECIAL_TOPICS);
                startActivity(intent);
            }
        });

        iv_360.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.TYPE,DetailActivity.TYPE_PANORAMA);
                startActivity(intent);
            }
        });
    }

    private void initCategories(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, mContext);
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.onItemClickListener() {
            @Override
            public void onItemClick(FeedItem feedItem) {
                Log.e("TAG",feedItem.getData().getTitle()+"id: "+feedItem.getData().getId());
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.TYPE,DetailActivity.TYPE_CATEGORY);
                intent.putExtra(DetailActivity.FEED_ITEM,feedItem);
                startActivity(intent);
            }
        });
    }

    private void handleData(Discovery discovery) {
        HorizontalScrollCard = new ArrayList<>();
        rankAndTopic = new ArrayList<>();
        rectangleCard = new ArrayList<>();
        categories = new ArrayList<>();

        List<FeedItem> itemList = discovery.getItemList();
        for (int i = 0; i < itemList.size(); i++) {
            if (i == 0 && itemList.get(0).getType().equals("horizontalScrollCard")) {
                HorizontalScrollCard.addAll(itemList.get(i).getData().getItemList());
            } else if (i == 1 || i == 2) {
                rankAndTopic.add(itemList.get(i));
            } else if (i == 3 && itemList.get(i).getType().equals("rectangleCard")) {
                rectangleCard.add(itemList.get(i));
            } else {
                categories.add(itemList.get(i));
            }
        }
    }


}
