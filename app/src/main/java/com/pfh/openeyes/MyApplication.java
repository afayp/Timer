package com.pfh.openeyes;

import android.app.Application;
import android.content.Context;

import com.pfh.openeyes.util.LogUtil;

/**
 * Created by Administrator on 2016/9/3.
 */
public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        LogUtil.isDebug = true;
    }

    public static Context getContext(){
        return mContext;
    }
}
