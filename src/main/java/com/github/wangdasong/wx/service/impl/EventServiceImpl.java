package com.github.wangdasong.wx.service.impl;

import com.github.wangdasong.wx.dao.entity.Event;
import com.github.wangdasong.wx.dao.persistence.EventDaoMapper;
import com.github.wangdasong.wx.dao.persistence.base.BaseDaoMapper;
import com.github.wangdasong.wx.service.EventService;
import com.github.wangdasong.wx.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl extends BaseServiceImpl<Event> implements EventService {

    @Autowired
    EventDaoMapper eventDaoMapper;
    @Override
    public BaseDaoMapper<Event> getCurrDaoMapper() {
        return eventDaoMapper;
    }
}
