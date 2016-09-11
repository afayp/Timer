package com.pfh.openeyes.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/11.
 */
public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String icon;
    private String name;
    private String description;
    private String link;
    private long latestReleaseTime;
    private int videoNum;
//    private String adTrack;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getLatestReleaseTime() {
        return latestReleaseTime;
    }

    public void setLatestReleaseTime(long latestReleaseTime) {
        this.latestReleaseTime = latestReleaseTime;
    }

    public int getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
    }
}
