package com.pfh.openeyes.util;

import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2016/9/3.
 */
public class LogUtil {

    private LogUtil()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "OpenEyes";

    public static void json(String json){
        if (isDebug){
            Logger.t(TAG).json(json);
        }
    }

    // 下面四个是默认tag的函数
    public static void i(String msg)
    {
        if (isDebug)
            Logger.t(TAG).i(msg);
    }

    public static void d(String msg)
    {
        if (isDebug)
            Logger.t(TAG).d(msg);
    }

    public static void e(String msg)
    {
        if (isDebug)
            Logger.t(TAG).e(msg);

    }

    public static void v(String msg)
    {
        if (isDebug)
            Logger.t(TAG).v(msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg)
    {
        if (isDebug)
            Logger.t(tag).i(msg);

    }

    public static void d(String tag, String msg)
    {
        if (isDebug)
            Logger.t(tag).d(msg);

    }

    public static void e(String tag, String msg)
    {
        if (isDebug)
            Logger.t(tag).e(msg);

    }

    public static void v(String tag, String msg)
    {
        if (isDebug)
            Logger.t(tag).v(msg);

    }
}
