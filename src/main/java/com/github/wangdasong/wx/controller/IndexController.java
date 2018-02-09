package com.github.wangdasong.wx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping(value = "/")
    public String index() {
        return "forward:/bonus/bonus.html";
    }
}
