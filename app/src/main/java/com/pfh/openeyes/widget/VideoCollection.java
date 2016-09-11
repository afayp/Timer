package com.pfh.openeyes.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.pfh.openeyes.R;
import com.pfh.openeyes.model.FeedItem;

import java.util.List;

/**
 * Created by Administrator on 2016/9/11.
 */
public class VideoCollection extends LinearLayout {

    private BriefCard briefCard;
    private HorizontalScrollView scrollView;
    private Context context;
    private LinearLayout ll_content;

    public VideoCollection(Context context) {
        this(context,null);
    }

    public VideoCollection(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }


    public VideoCollection(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_video_collection, null);
        briefCard = (BriefCard) view.findViewById(R.id.briefcard);
        scrollView = (HorizontalScrollView) view.findViewById(R.id.scrollView);
        ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setData(FeedItem feedItem){
        if (!feedItem.getData().getDataType().equals("VideoCollection")){
            return;
        }

        if (feedItem.getData().getHeader() != null){
            briefCard.setHeader(feedItem.getData().getHeader());
        }

        List<FeedItem> itemList = feedItem.getData().getItemList();
        for (int i = 0; i < itemList.size(); i++) {
            ScrollItem scrollItem = new ScrollItem(context);
            scrollItem.setFeedItem(itemList.get(i));
            ll_content.addView(scrollItem);
        }
    }
}
