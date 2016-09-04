package com.pfh.openeyes.model;

import java.util.List;

/**
 * Created by Administrator on 2016/9/3.
 */
public class IssueList {

    private long releaseTime;

    private String type;

    private long date;

    private long publishTime;

    private int count;

    private List<FeedItem> itemList;

    public List<FeedItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<FeedItem> itemList) {
        this.itemList = itemList;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }

}
