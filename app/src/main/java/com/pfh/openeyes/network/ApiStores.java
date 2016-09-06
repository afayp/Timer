package com.pfh.openeyes.network;

import com.pfh.openeyes.model.Feed;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/1.
 */
public interface ApiStores {

    //baseUrl
    String API_SERVER_URL = "http://baobab.wandoujia.com/";

    //精选,第一次加载
    @GET("api/v2/feed")
    Observable<Feed> loadFeedFirst(@Query("num") String num);// http://baobab.wandoujia.com/api/v2/feed?num=1

    //精选,加载下一页
    @GET("api/v2/feed")
    Observable<Feed> loadFeedNextPage(@Query("date") String date,@Query("num") String num);

}
