package com.pfh.openeyes.presenter;

/**
 * Created by Administrator on 2016/9/1.
 */
public interface IPresenter<V> {

    void attachView(V view);

    void detachView();
}
