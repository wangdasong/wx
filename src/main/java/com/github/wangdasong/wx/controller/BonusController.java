package com.github.wangdasong.wx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/bonus")
public class BonusController {
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
}
