package com.github.wangdasong.wx.dao.entity;

import com.github.wangdasong.wx.dao.entity.base.BaseEntity;

public class Token extends BaseEntity {
    String wxCode;

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }
}
