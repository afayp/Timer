package com.pfh.openeyes.model;

/**
 * 可能为null： adTrack
 */
public class Tags {
    private int id;

    private String name;

    private String actionUrl;

    private String adTrack;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setActionUrl(String actionUrl){
        this.actionUrl = actionUrl;
    }
    public String getActionUrl(){
        return this.actionUrl;
    }
    public void setAdTrack(String adTrack){
        this.adTrack = adTrack;
    }
    public String getAdTrack(){
        return this.adTrack;
    }

}
