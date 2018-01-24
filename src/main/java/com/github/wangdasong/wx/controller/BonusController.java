package com.github.wangdasong.wx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api/bonus")
public class BonusController {
    @RequestMapping(value = "getBonus2LeftCount")
    public String getBonus2LeftCount(){
        return "100";
    }
    @RequestMapping(value = "getBonus3LeftCount")
    public String getBonus3LeftCount(){
        return "200";
    }
}
