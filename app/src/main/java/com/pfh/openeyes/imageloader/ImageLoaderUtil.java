package com.pfh.openeyes.imageloader;

import android.content.Context;

/**
 * Created by Administrator on 2016/9/1.
 */
public class ImageLoaderUtil {

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    //加载策略
    public static final int LOAD_STRATEGY_NORMAL = 0;
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    //更改这个provider，即可更换使用的图片加载框架
    private BaseImageLoaderProvider mProvider;


    private static ImageLoaderUtil mInstance;

    private ImageLoaderUtil(){
        mProvider =new GlideImageLoaderProvider();//默认用Glide加载框架
    }

    //single instance
    public static ImageLoaderUtil getInstance(){
        if(mInstance ==null){
            synchronized (ImageLoaderUtil.class){
                if(mInstance == null){
                    mInstance = new ImageLoaderUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }


    public void loadImage(Context context, ImageLoader imageLoader){
        mProvider.loadImage(context,imageLoader);
    }

    public void setLoadImgProvider(BaseImageLoaderProvider provider){
        mProvider = provider;
    }
}
