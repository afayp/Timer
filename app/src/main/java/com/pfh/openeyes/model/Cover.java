package com.pfh.openeyes.model;

import java.io.Serializable;

/**
 * 可能为null： sharing
 */
public class Cover implements Serializable {
    private static final long serialVersionUID = 1L;

    private String feed;

    private String detail;

    private String blurred;

    private String sharing;

    public void setFeed(String feed){
        this.feed = feed;
    }
    public String getFeed(){
        return this.feed;
    }
    public void setDetail(String detail){
        this.detail = detail;
    }
    public String getDetail(){
        return this.detail;
    }
    public void setBlurred(String blurred){
        this.blurred = blurred;
    }
    public String getBlurred(){
        return this.blurred;
    }
    public void setSharing(String sharing){
        this.sharing = sharing;
    }
    public String getSharing(){
        return this.sharing;
    }

}
