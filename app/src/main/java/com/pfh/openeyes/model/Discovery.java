package com.pfh.openeyes.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
public class Discovery implements Serializable{
    private static final long serialVersionUID = 1L;

    private List<FeedItem> itemList;
    private int count;
    private int total;
    private String nextPageUrl;
    private PgcInfo pgcInfo;

    @Override
    public String toString() {
        return "Discovery{" +
                "itemList=" + itemList +
                ", count=" + count +
                ", total=" + total +
                ", nextPageUrl='" + nextPageUrl + '\'' +
                ", pgcInfo=" + pgcInfo +
                '}';
    }

    public PgcInfo getPgcInfo() {
        return pgcInfo;
    }

    public void setPgcInfo(PgcInfo pgcInfo) {
        this.pgcInfo = pgcInfo;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public List<FeedItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<FeedItem> itemList) {
        this.itemList = itemList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
