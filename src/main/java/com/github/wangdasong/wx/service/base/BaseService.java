package com.github.wangdasong.wx.service.base;

import com.github.wangdasong.wx.dao.entity.base.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity>  {
    public T addEntity(T t);
    public T editEntity(T t);
    public T delEntity(String id);
    public T getEntityById(String id);
    public T getEntityDetailById(String id);
    public List<T> getEntityListByCondition(T t);
}