package com.github.wangdasong.wx.controller;

import com.github.wangdasong.wx.dao.entity.Bonus;
import com.github.wangdasong.wx.dao.entity.Event;
import com.github.wangdasong.wx.dao.entity.Token;
import com.github.wangdasong.wx.dao.entity.WxBns;
import com.github.wangdasong.wx.service.BonusService;
import com.github.wangdasong.wx.service.TokenService;
import com.github.wangdasong.wx.service.WxBnsService;
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
    @Autowired
    BonusService bonusService;
    @Autowired
    WxBnsService wxBnsService;

    @RequestMapping(value = "/check")
    @ResponseBody
    public Token check(String wxCode){
        String[] wxCodeList = wxCode.split("_");
        //前台未取得用户信息，返回空值
        System.out.println("wxCode===" +wxCode);
        if(wxCodeList.length > 1 && "".equals(wxCodeList[1])){
            return null;
        }
        Token queryToken = new Token();
        queryToken.setWxCode(wxCode);
        List<Token> tokens = tokenService.getEntityListByCondition(queryToken);
        if(tokens != null && tokens.size() > 0){
            return tokens.get(0);
        }
        return null;
      //  return new Token();
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
    @RequestMapping(value = "/getBonusInfo")
    @ResponseBody
    public WxBns getBonusInfo(String id){
        Token checkToken = tokenService.getEntityById(id);
        String wxCode = checkToken.getWxCode();
        WxBns wxBns = new WxBns();
        wxBns.setWxCode(wxCode);
        List<WxBns> wxBnsList = wxBnsService.getEntityListByCondition(wxBns);
        if(wxBnsList != null && wxBnsList.size() > 0){
            WxBns hadWxBns =  wxBnsList.get(0);
            hadWxBns.setBonus(bonusService.getEntityById(hadWxBns.getBonusId()));
            return hadWxBns;
        }
        return null;
    }


}
