package com.pfh.openeyes.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/3.
 */
public class Provider implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private String alias;

    private String icon;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setAlias(String alias){
        this.alias = alias;
    }
    public String getAlias(){
        return this.alias;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }

}
