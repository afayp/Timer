package com.pfh.openeyes.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/3.
 */
public class Consumption implements Serializable{

    private static final long serialVersionUID = 1L;

    private int collectionCount;

    private int shareCount;

    private int replyCount;

    public void setCollectionCount(int collectionCount){
        this.collectionCount = collectionCount;
    }
    public int getCollectionCount(){
        return this.collectionCount;
    }
    public void setShareCount(int shareCount){
        this.shareCount = shareCount;
    }
    public int getShareCount(){
        return this.shareCount;
    }
    public void setReplyCount(int replyCount){
        this.replyCount = replyCount;
    }
    public int getReplyCount(){
        return this.replyCount;
    }

}
