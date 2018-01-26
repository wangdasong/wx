package com.github.wangdasong.wx.dao.entity;

import com.github.wangdasong.wx.dao.entity.base.BaseEntity;

import java.util.Date;

public class Bonus extends BaseEntity {
    String name;
    int initCount;
    int leftCount;
    String eventId;
    int sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitCount() {
        return initCount;
    }

    public void setInitCount(int initCount) {
        this.initCount = initCount;
    }

    public int getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(int leftCount) {
        this.leftCount = leftCount;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
