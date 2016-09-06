package com.pfh.openeyes.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/3.
 */
public class WebUrl implements Serializable{
    private static final long serialVersionUID = 1L;
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