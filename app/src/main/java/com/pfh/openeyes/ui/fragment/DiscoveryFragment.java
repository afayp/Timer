package com.pfh.openeyes.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pfh.openeyes.R;
import com.pfh.openeyes.ui.base.BaseFragment;
import com.pfh.openeyes.util.DividerGridItemDecoration;
import com.pfh.openeyes.widget.Banner;
import com.pfh.openeyes.widget.ShowAllRecyclerView;

import butterknife.BindView;

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
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
//        recyclerView.setAdapter();


    }
}
