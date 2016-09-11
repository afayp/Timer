package com.pfh.openeyes.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/11.
 */
public class Header implements Serializable {

    private static final long serialVersionUID = 1L;


    private int id;
    private String icon;
    private String title;
    private String subTitle;
    private String description;
    private String actionUrl;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
}
