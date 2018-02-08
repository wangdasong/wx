package com.github.wangdasong.wx.controller;

import com.github.wangdasong.wx.dao.entity.Bonus;
import com.github.wangdasong.wx.dao.entity.Event;
import com.github.wangdasong.wx.service.BonusService;
import com.github.wangdasong.wx.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/event")
public class EventController {

    @Autowired
    EventService eventService;

    @RequestMapping(value = "/getFirstEvent")
    @ResponseBody
    public Event getFirstEvent(){
        Event queryEvent = new Event();
        List<Event> events = eventService.getEntityListByCondition(queryEvent);
        return events.get(0);
    }


}
