package com.pfh.openeyes.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pfh.openeyes.R;
import com.pfh.openeyes.model.FeedItem;
import com.pfh.openeyes.util.TimeUtils;
import com.pfh.openeyes.widget.ItemImageView;

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
    public static final int TYPE_Video = 1;
    public static final int TYPE_TextHeader = 2;
    public static final int TYPE_UNKONW = 3;//未知

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
            return TYPE_Video;
        }else if (dataType.equals("TextHeader")){
            return TYPE_TextHeader;
        }
        return TYPE_UNKONW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case TYPE_Banner:
                view = inflater.inflate(R.layout.item_feed_banner,parent,false);
                holder = new MyViewHolder_Banner(view);
                break;
            case TYPE_Video:
                view = inflater.inflate(R.layout.item_feed_video,parent,false);
                holder = new MyViewHolder_Video(view);
                break;
            case TYPE_TextHeader:
                view = inflater.inflate(R.layout.item_feed_textheader,parent,false);
                holder = new MyViewHolder_TextHeader(view);
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
                        onItemClickListener.onItemClick(view,feedItem,position);
                    }
                });
                break;
            case TYPE_Video:
                MyViewHolder_Video holder_video = (MyViewHolder_Video) holder;
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
                        onItemClickListener.onItemClick(view,feedItem,position);
                    }
                });
                break;
            case TYPE_TextHeader:
                MyViewHolder_TextHeader holder_textHeader = (MyViewHolder_TextHeader) holder;
                holder_textHeader.tv_header.setText(feedItem.getData().getText());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class MyViewHolder_Video extends RecyclerView.ViewHolder{
        @BindView(R.id.item_imageview)
        ItemImageView itemImageView;

        public MyViewHolder_Video(View itemView) {
            super(itemView);
//            itemImageView = (ItemImageView) itemView.findViewById(R.id.item_imageview);
            ButterKnife.bind(this,itemView);
        }
    }

    static class MyViewHolder_Banner extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_banner)
        ImageView iv_banner;

        public MyViewHolder_Banner(View itemView) {
            super(itemView);
//            iv_banner = (ImageView) itemView.findViewById(R.id.iv_banner);
            ButterKnife.bind(this,itemView);
        }
    }

    static class MyViewHolder_TextHeader extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_header)
        TextView tv_header;

        public MyViewHolder_TextHeader(View itemView) {
            super(itemView);
//            tv_header = (TextView) itemView.findViewById(R.id.tv_header);
            ButterKnife.bind(this,itemView);
        }
    }
}
