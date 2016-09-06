package com.pfh.openeyes.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/3.
 */
public class PlayInfo implements Serializable{
    private static final long serialVersionUID = 1L;

    private int height;

    private int width;

    private String name;

    private String type;

    private String url;

    public void setHeight(int height){
        this.height = height;
    }
    public int getHeight(){
        return this.height;
    }
    public void setWidth(int width){
        this.width = width;
    }
    public int getWidth(){
        return this.width;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }

}
