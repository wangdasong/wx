package com.github.wangdasong.wx.dao.entity;

import com.github.wangdasong.wx.dao.entity.base.BaseEntity;

import java.util.Date;

public class Bonus extends BaseEntity {
    String name;
    Integer initCount;
    Integer leftCount;
    String eventId;
    Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInitCount() {
        return initCount;
    }

    public void setInitCount(Integer initCount) {
        this.initCount = initCount;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
