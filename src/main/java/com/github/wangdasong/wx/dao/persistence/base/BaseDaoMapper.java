package com.github.wangdasong.wx.dao.persistence.base;

import com.github.wangdasong.wx.dao.entity.base.BaseEntity;

import java.util.List;

public interface BaseDaoMapper<T extends BaseEntity> {
    /**
     * 根据ID取得对象
     * @param id
     * @return
     * @throws Exception
     */
    public <PK> T getById(PK id)throws RuntimeException;

    /**
     * 根据ID取得对象以及对象中的成员
     * @param id
     * @return
     * @throws Exception
     */
    public <PK> T getDetailById(PK id)throws RuntimeException;

    public void save(T entity)throws RuntimeException;

    public void update(T entity)throws RuntimeException;

    public <PK> void deleteById(PK id)throws RuntimeException;

    public List<T> getAllData() throws RuntimeException;

    public List<T> getListByCondition(T t) throws RuntimeException;

}
