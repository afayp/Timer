package com.pfh.openeyes.event;

/**
 * 精选页面Recyclerview滑动改变toolbar左边显示日期
 */
public class ChangeDateEvent {

    private String date;

    public ChangeDateEvent(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
