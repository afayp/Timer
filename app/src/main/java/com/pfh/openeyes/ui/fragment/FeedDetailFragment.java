package com.pfh.openeyes.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pfh.openeyes.R;
import com.pfh.openeyes.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/4.
 */
public class FeedDetailFragment extends BaseFragment {

//    @BindView(R.id.rl_detail_bg)
//    RelativeLayout rl_detail_bg;
    @BindView(R.id.iv_arrow_down)
    ImageView iv_arrow_down;
    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.iv_bg)
    ImageView iv_bg;

    public static final String IMG_URL = "ima_url";

    private String url;

    public static FeedDetailFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putSerializable(IMG_URL,url);

        FeedDetailFragment fragment = new FeedDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed_detail, container, false);
        ButterKnife.bind(this,view);
        url = getArguments().getString(IMG_URL);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//        rl_detail_bg.setBackground();
        Glide.with(mContext).load(url).into(iv_bg);

        iv_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 播放界面
            }
        });

        iv_arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //返回
            }
        });


    }
}
