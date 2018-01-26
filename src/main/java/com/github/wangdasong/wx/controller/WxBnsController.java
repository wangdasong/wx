package com.github.wangdasong.wx.controller;

import com.github.wangdasong.wx.dao.entity.Bonus;
import com.github.wangdasong.wx.dao.entity.WxBns;
import com.github.wangdasong.wx.service.BonusService;
import com.github.wangdasong.wx.service.WxBnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/wxBns")
public class WxBnsController {

    @Autowired
    WxBnsService wxBnsService;
    @Autowired
    BonusService bonusService;

    @RequestMapping(value = "/secKill")
    @ResponseBody
    public Bonus secKill(String eventId, String wxCode){
        Bonus queryBonus = new Bonus();
        queryBonus.setEventId(eventId);
        List<Bonus> bonusList = bonusService.getEntityListByCondition(queryBonus);
        WxBns wxBns = null;
        for(Bonus bonus : bonusList){
            wxBns = wxBnsService.secKill(wxCode, bonus.getId());
            if(wxBns != null){
                break;
            }
        }
        if(wxBns == null){
            return null;
        }
        String bonusId = wxBns.getBonusId();
        return bonusService.getEntityById(bonusId);
    }
    @RequestMapping(value = "/getMyBonus")
    @ResponseBody
    public Bonus getMyBonus(String eventId, String wxCode){
        WxBns queryWxBns = new WxBns();
        queryWxBns.setWxCode(wxCode);
        Bonus queryBonus = new Bonus();
        queryBonus.setEventId(eventId);
        queryWxBns.setBonus(queryBonus);
        List<WxBns> wxBnsList = wxBnsService.getEntityListByCondition(queryWxBns);
        if(wxBnsList != null && wxBnsList.size() >0){
            WxBns wxBns = wxBnsList.get(0);
            return bonusService.getEntityById(wxBns.getBonusId());
        }
        return null;
    }
}
