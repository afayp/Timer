package com.pfh.openeyes.imageloader;

import android.widget.ImageView;

import com.pfh.openeyes.R;

/**
 * Created by Administrator on 2016/9/1.
 */
public class ImageLoader {

    private int type;  //类型 (大图，中图，小图)
    private String url; //需要解析的url
    private int placeHolder; // 展占位图
    private int error;//当没有成功加载的时候显示的图片
    private ImageView imgView; //ImageView的实例
    private int wifiStrategy;//加载策略，是否在wifi下才加载

    private ImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.error = builder.error;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;

    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getError(){
        return error;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public static class Builder {
        private int type;
        private String url;
        private int placeHolder;
        private int error;
        private ImageView imgView;
        private int wifiStrategy;

        public Builder() {
            //默认的设置
            this.type = ImageLoaderUtil.PIC_SMALL;
            this.url = "";
            this.placeHolder = R.drawable.default_pic_big;
            this.error = R.drawable.error_pic;
            this.imgView = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_STRATEGY_NORMAL;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder error(int error) {
            this.error = error;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }

    }
}
