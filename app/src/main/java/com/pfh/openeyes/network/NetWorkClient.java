package com.pfh.openeyes.network;

import com.pfh.openeyes.MyApplication;
import com.pfh.openeyes.util.FileUtils;
import com.pfh.openeyes.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/9/1.
 */
public class NetWorkClient {

    private static NetWorkClient mInstance;

    private NetWorkClient(){

    }

    public static NetWorkClient getInstance(){
        if (mInstance == null){
            synchronized (NetworkUtils.class){
                mInstance = new NetWorkClient();
                return mInstance;
            }
        }
        return mInstance;

    }

    public Retrofit retrofit() {
        return new Retrofit.Builder()
                    .baseUrl(ApiStores.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(initOkHttpClient())
                    .build();
    }

    private OkHttpClient initOkHttpClient(){

        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isNetworkAvailable(MyApplication.getContext())){
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isNetworkAvailable(MyApplication.getContext())){
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("WuXiaolong")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                }else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
        File cacheFile = FileUtils.getCacheDir(MyApplication.getContext(),"httpCache");
//        File cacheFile = FileUtils.getExternalCacheDirectory(MyApplication.getContext(), "");
        Cache cache = new Cache(cacheFile,1024*1024*50);

        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .connectTimeout(5000, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }
}
