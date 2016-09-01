package com.pfh.openeyes.network;

/**
 * Created by Administrator on 2016/9/1.
 */
public interface ApiCallback<T> {

    void onSuccess(T model);

    void onFailure(int code, String msg);

    void onCompleted();
}
