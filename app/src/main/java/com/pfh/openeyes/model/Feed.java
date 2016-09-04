package com.pfh.openeyes.model;

import java.util.List;

/**
 * 精选 http://baobab.wandoujia.com/api/v2/feed?num=1返回数据
 * 可能为null字段：dialog
 */
public class Feed {

    private List<IssueList> issueList ;

    private String nextPageUrl;

    private long nextPublishTime;

    private String newestIssueType;

//    private String dialog;

    public void setIssueList(List<IssueList> issueList){
        this.issueList = issueList;
    }
    public List<IssueList> getIssueList(){
        return this.issueList;
    }
    public void setNextPageUrl(String nextPageUrl){
        this.nextPageUrl = nextPageUrl;
    }
    public String getNextPageUrl(){
        return this.nextPageUrl;
    }

    public long getNextPublishTime() {
        return nextPublishTime;
    }

    public void setNextPublishTime(long nextPublishTime) {
        this.nextPublishTime = nextPublishTime;
    }

    public void setNewestIssueType(String newestIssueType){
        this.newestIssueType = newestIssueType;
    }
    public String getNewestIssueType(){
        return this.newestIssueType;
    }
//    public void setDialog(String dialog){
//        this.dialog = dialog;
//    }
//    public String getDialog(){
//        return this.dialog;
//    }
}
