package com.github.wangdasong.wx.dao.entity;

import com.github.wangdasong.wx.dao.entity.base.BaseEntity;

import java.util.Date;

public class Event extends BaseEntity {
    String name;
    Date startTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public long getStartTimeLeft() {
        long reTimeLeft = startTime.getTime()/1000 - (new Date()).getTime()/1000;
        return reTimeLeft < 0 ? 0 : reTimeLeft;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
