package com.github.wangdasong.wx.controller;

import com.github.wangdasong.wx.dao.entity.Event;
import com.github.wangdasong.wx.dao.entity.Token;
import com.github.wangdasong.wx.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/token")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @RequestMapping(value = "/check")
    @ResponseBody
    public Token check(String wxCode){
        Token queryToken = new Token();
        queryToken.setWxCode(wxCode);
        List<Token> tokens = tokenService.getEntityListByCondition(queryToken);
        if(tokens != null && tokens.size() > 0){
            return tokens.get(0);
        }
        return null;
    }
    @RequestMapping(value = "/update")
    @ResponseBody
    public Token update(String id, String wxCode){
        Token checkToken = tokenService.getEntityById(id);
        if(checkToken == null){
            return null;
        }else{
            if(checkToken.getWxCode() != null
                    && !"".equals(checkToken.getWxCode())){
                return null;
            }else{
                checkToken.setWxCode(wxCode);
                checkToken.setUpdateUserId(wxCode);
                checkToken = tokenService.editEntity(checkToken);
                return checkToken;
            }
        }
    }


}
