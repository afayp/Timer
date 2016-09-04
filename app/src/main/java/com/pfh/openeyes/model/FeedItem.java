package com.pfh.openeyes.model;

/**
 * 精选 http://baobab.wandoujia.com/api/v2/feed?num=1，itemList字段下的JSONObject
 */
public class FeedItem {

    private String type;

    private Data data;

    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
}
