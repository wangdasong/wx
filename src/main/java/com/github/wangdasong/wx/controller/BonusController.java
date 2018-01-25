package com.github.wangdasong.wx.controller;

import com.github.wangdasong.wx.dao.entity.Event;
import com.github.wangdasong.wx.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/bonus")
public class BonusController {

    @Autowired
    EventService eventService;


    @RequestMapping(value = "/getBonus2LeftCount")
    @ResponseBody
    public String getBonus2LeftCount(){
        return "100";
    }
    @RequestMapping(value = "/getBonus3LeftCount")
    @ResponseBody
    public String getBonus3LeftCount(){
        return "200";
    }


    @RequestMapping(value = "/getBonus")
    @ResponseBody
    public String getBonus(){
        return "";
    }


    @RequestMapping(value = "/getFirstEvent")
    @ResponseBody
    public Event getFirstEvent(){
        Event queryEvent = new Event();
        List<Event> events = eventService.getEntityListByCondition(queryEvent);
        return events.get(0);
    }




}
