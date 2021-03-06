package com.github.wangdasong.wx.controller;

import com.github.wangdasong.wx.dao.entity.Bonus;
import com.github.wangdasong.wx.dao.entity.Event;
import com.github.wangdasong.wx.service.BonusService;
import com.github.wangdasong.wx.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/bonus")
public class BonusController {

    @Autowired
    BonusService bonusService;


    @RequestMapping(value = "/getBonusList/{eventId}")
    @ResponseBody
    public List<Bonus> getBonusList(@PathVariable(value = "eventId") String eventId){
        Bonus queryBonus = new Bonus();
        queryBonus.setEventId(eventId);
        return bonusService.getEntityListByCondition(queryBonus);
    }

}
