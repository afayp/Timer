package com.pfh.openeyes.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pfh.openeyes.R;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.model.Header;

/**
 * Created by Administrator on 2016/9/11.
 */
public class BriefCard extends LinearLayout {

    private ImageView iv_icon;
    private TextView tv_title;
    private TextView tv_subTitle;
    private TextView tv_description;
    private ImageView iv_arrow;
    private Context context;

    public BriefCard(Context context) {
        this(context,null);
    }

    public BriefCard(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BriefCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_brief_card, null);
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_subTitle = (TextView) view.findViewById(R.id.tv_subTitle);
        tv_description = (TextView) view.findViewById(R.id.tv_description);
        iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
    }

    public void setFeedItem(FeedItem feedItem){
        if (!feedItem.getData().getDataType().equals("BriefCard")){
            return;
        }
        Glide.with(context)
                .load(feedItem.getData().getIcon())
                .centerCrop()
                .into(iv_icon);
        tv_title.setText(feedItem.getData().getTitle());
        tv_subTitle.setText(feedItem.getData().getSubTitle());
        tv_description.setText(feedItem.getData().getDescription());
    }

    public void setHeader(Header header){
        Glide.with(context)
                .load(header.getIcon())
                .centerCrop()
                .into(iv_icon);
        tv_title.setText(header.getTitle());
        tv_subTitle.setText(header.getSubTitle());
        tv_description.setText(header.getDescription());
    }
}
