package com.pfh.openeyes.model;

/**
 * Created by Administrator on 2016/9/3.
 */
public class WebUrl {
    private String raw;

    private String forWeibo;

    public void setRaw(String raw){
        this.raw = raw;
    }
    public String getRaw(){
        return this.raw;
    }
    public void setForWeibo(String forWeibo){
        this.forWeibo = forWeibo;
    }
    public String getForWeibo(){
        return this.forWeibo;
    }

}