package com.pfh.openeyes.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.pfh.openeyes.util.NetworkUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/9/1.
 */
public class GlideImageLoaderProvider implements BaseImageLoaderProvider{


    @Override
    public void loadImage(Context context, ImageLoader imageLoader) {

        int strategy = imageLoader.getWifiStrategy();//加载策略
        switch (strategy){
            // 1.仅wifi下加载的策略
            case ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI:
                if (NetworkUtils.isWifi(context)){
                    loadNormal(context,imageLoader);
                }else {
                    loadCache(context,imageLoader);
                }
                break;
            //2.正常加载策略
            case ImageLoaderUtil.LOAD_STRATEGY_NORMAL:
                loadNormal(context,imageLoader);
                break;
        }

    }

    /**
     * 从网上加载
     * @param context
     * @param imageLoader
     */
    private void loadNormal(Context context, ImageLoader imageLoader) {
        Glide.with(context)
                .load(imageLoader.getUrl())
                .placeholder(imageLoader.getPlaceHolder())
                .error(imageLoader.getError())
                .into(imageLoader.getImgView());
    }

    /**
     * 加载缓存
     * @param context
     * @param imageLoader
     */
    private void loadCache(Context context, ImageLoader imageLoader) {

        Glide.with(context).using(new StreamModelLoader<String>() {
            @Override
            public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
                return new DataFetcher<InputStream>() {
                    @Override
                    public InputStream loadData(Priority priority) throws Exception {
                        throw new IOException();
                    }

                    @Override
                    public void cleanup() {

                    }

                    @Override
                    public String getId() {
                        return model;
                    }

                    @Override
                    public void cancel() {

                    }
                };
            }
        }).load(imageLoader.getUrl())
                .placeholder(imageLoader.getPlaceHolder())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageLoader.getImgView());

    }
}
