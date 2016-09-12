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
import com.pfh.openeyes.util.TimeUtils;

/**
 * Created by Administrator on 2016/9/11.
 */
public class ScrollItem extends LinearLayout {

    private ImageView iv_cover;
    private TextView tv_title;
    private TextView tv_category;
    private TextView tv_duration;
    private Context context;

    public ScrollItem(Context context) {
        this(context,null);
    }

    public ScrollItem(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_scroll_item, null);
        addView(view,new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv_cover = (ImageView) view.findViewById(R.id.iv_cover);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_category = (TextView) view.findViewById(R.id.tv_category);
        tv_duration = (TextView) view.findViewById(R.id.tv_duration);
    }

    public void setFeedItem(FeedItem feedItem){
        if (!feedItem.getData().getDataType().equals("VideoBeanForClient")){
            return;
        }

        Glide.with(context)
                .load(feedItem.getData().getCover().getFeed())
                .centerCrop()
                .into(iv_cover);

        tv_title.setText(feedItem.getData().getTitle());
        tv_category.setText("#"+feedItem.getData().getCategory()+"  /  ");
        tv_duration.setText(TimeUtils.convertTime(feedItem.getData().getDuration()));
    }


}
