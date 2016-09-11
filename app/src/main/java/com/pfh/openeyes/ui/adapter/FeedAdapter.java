package com.pfh.openeyes.ui.adapter;

import android.content.Context;
import android.support.v4.widget.Space;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pfh.openeyes.R;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.util.TimeUtils;
import com.pfh.openeyes.widget.BriefCard;
import com.pfh.openeyes.widget.ItemImageView;
import com.pfh.openeyes.widget.VideoCollection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/4.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FeedItem> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public static final int TYPE_Banner = 0;
    public static final int TYPE_Video_BEAN_FOR_CLIENT = 1;
    public static final int TYPE_Text_Header = 2;
    public static final int TYPE_LEFT_ALGIN_TEXT_HEADER = 3;
    public static final int TYPE_BRIEF_CARD= 4;
    public static final int TYPE_BLANK_CARD= 5;
    public static final int TYPE_VIDEO_COLLECTION= 6;
    public static final int TYPE_UNKONW = 7;


    private onItemClickListener onItemClickListener;

    public FeedAdapter(List<FeedItem> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
    }

    public void refreshData(List<FeedItem> feedItemList){
        this.mData = feedItemList;
        notifyDataSetChanged();
    }

    public interface onItemClickListener{
        void onItemClick(View view,FeedItem feedItem,int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        String dataType = mData.get(position).getData().getDataType();

        if (dataType.equals("Banner")){
            return TYPE_Banner;
        }else if (dataType.equals("VideoBeanForClient")){
            return TYPE_Video_BEAN_FOR_CLIENT;
        }else if (dataType.equals("TextHeader")){
            return TYPE_Text_Header;
        }else if (dataType.equals("LeftAlignTextHeader")){
            return TYPE_LEFT_ALGIN_TEXT_HEADER;
        }else if (dataType.equals("BriefCard")){
            return TYPE_BRIEF_CARD;
        }else if (dataType.equals("BlankCard")){
            return TYPE_BLANK_CARD;
        }else if(dataType.equals("VideoCollection")){
            return TYPE_VIDEO_COLLECTION;
        }
        return TYPE_UNKONW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_Banner:
                Log.e("TYPE","TYPE_Banner");
                view = inflater.inflate(R.layout.item_feed_banner,parent,false);
                holder = new MyViewHolder_Banner(view);
                break;
            case TYPE_Video_BEAN_FOR_CLIENT:
                Log.e("TYPE","TYPE_Video_BEAN_FOR_CLIENT");
                view = inflater.inflate(R.layout.item_feed_video_bean_for_client,parent,false);
                holder = new MyViewHolder_VideoBeanForClient(view);
                break;
            case TYPE_Text_Header:
                Log.e("TYPE","TYPE_Text_Header");

                view = inflater.inflate(R.layout.item_feed_text_header,parent,false);
                holder = new MyViewHolder_TextHeader(view);
                break;
            case TYPE_LEFT_ALGIN_TEXT_HEADER:
                Log.e("TYPE","TYPE_LEFT_ALGIN_TEXT_HEADER");

                view = inflater.inflate(R.layout.item_feed_left_align_text_header,parent,false);
                holder = new MyViewHolder_LeftAlignTextHeader(view);
                break;
            case TYPE_BRIEF_CARD:
                Log.e("TYPE","TYPE_BRIEF_CARD");
                view = inflater.inflate(R.layout.item_feed_brief_card,parent,false);
                holder = new MyViewHolder_BriefCard(view);
                break;
            case TYPE_BLANK_CARD:
                Log.e("TYPE","TYPE_BLANK_CARD");
                view = inflater.inflate(R.layout.item_feed_blank_card,parent,false);
                holder = new MyViewHolder_BlankCard(view);
                break;
            case TYPE_VIDEO_COLLECTION:
                Log.e("TYPE","TYPE_VIDEO_COLLECTION");
                view = inflater.inflate(R.layout.item_feed_video_collection,parent,false);
                holder = new MyViewHolder_VideoCollection(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final FeedItem feedItem = mData.get(position);
        switch (getItemViewType(position)){
            case TYPE_Banner:
                MyViewHolder_Banner holder_banner = (MyViewHolder_Banner) holder;
                Glide.with(mContext)
                        .load(feedItem.getData().getImage())
                        .into(holder_banner.iv_banner);
                holder_banner.iv_banner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null){
                            onItemClickListener.onItemClick(view,feedItem,position);
                        }
                    }
                });
                break;
            case TYPE_Video_BEAN_FOR_CLIENT:
                MyViewHolder_VideoBeanForClient holder_video = (MyViewHolder_VideoBeanForClient) holder;
                holder_video.itemImageView.setTitle(feedItem.getData().getTitle());
                holder_video.itemImageView.setCategory("#"+feedItem.getData().getCategory()+" / ");
                holder_video.itemImageView.setDuration(TimeUtils.convertTime(feedItem.getData().getDuration()));
                Glide.with(mContext)
                        .load(feedItem.getData().getCover().getFeed())
                        .centerCrop()
                        .into(holder_video.itemImageView.getBg());
                holder_video.itemImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null){
                            onItemClickListener.onItemClick(view,feedItem,position);
                        }
                    }
                });
                break;
            case TYPE_Text_Header:
                MyViewHolder_TextHeader holder_textHeader = (MyViewHolder_TextHeader) holder;
                holder_textHeader.tv_header.setText(feedItem.getData().getText());
                break;
            case TYPE_LEFT_ALGIN_TEXT_HEADER:
                MyViewHolder_LeftAlignTextHeader holder_leftAlignTextHeader = (MyViewHolder_LeftAlignTextHeader) holder;
                holder_leftAlignTextHeader.tv_text.setText(feedItem.getData().getText());
                break;
            case TYPE_BRIEF_CARD:
                MyViewHolder_BriefCard holder_briefCard = (MyViewHolder_BriefCard) holder;
                holder_briefCard.briefCard.setFeedItem(feedItem);
                holder_briefCard.briefCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onItemClickListener != null){
                            //...
                        }
                    }
                });
                break;
            case TYPE_BLANK_CARD:
                MyViewHolder_BlankCard holder_blankCard = (MyViewHolder_BlankCard) holder;
                break;
            case TYPE_VIDEO_COLLECTION:
                MyViewHolder_VideoCollection holder_videoCollection = (MyViewHolder_VideoCollection) holder;
                holder_videoCollection.videoCollection.setData(feedItem);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class MyViewHolder_VideoBeanForClient extends RecyclerView.ViewHolder{
        @BindView(R.id.item_imageview)
        ItemImageView itemImageView;

        public MyViewHolder_VideoBeanForClient(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class MyViewHolder_Banner extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_banner)
        ImageView iv_banner;

        public MyViewHolder_Banner(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class MyViewHolder_TextHeader extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_header)
        TextView tv_header;

        public MyViewHolder_TextHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class MyViewHolder_LeftAlignTextHeader extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        TextView tv_text;
        public MyViewHolder_LeftAlignTextHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class MyViewHolder_BriefCard extends RecyclerView.ViewHolder {
        @BindView(R.id.briefcard)
        BriefCard briefCard;
        public MyViewHolder_BriefCard(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class MyViewHolder_BlankCard extends RecyclerView.ViewHolder {
        @BindView(R.id.space)
        Space space;
        public MyViewHolder_BlankCard(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    static class MyViewHolder_VideoCollection extends RecyclerView.ViewHolder {
        @BindView(R.id.videoCollection)
        VideoCollection videoCollection;

        public MyViewHolder_VideoCollection(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
