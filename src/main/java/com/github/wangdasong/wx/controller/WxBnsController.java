package com.github.wangdasong.wx.controller;

import com.github.wangdasong.wx.dao.entity.Bonus;
import com.github.wangdasong.wx.dao.entity.WxBns;
import com.github.wangdasong.wx.service.BonusService;
import com.github.wangdasong.wx.service.WxBnsService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/api/wxBns")
public class WxBnsController {
    private static HttpClient client = null;
    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
        client = HttpClients.custom().setConnectionManager(cm).build();
    }
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
    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String, String> login(String authCode) throws Exception {
        String result="";
        Map<String, String> reMap = new HashMap<String, String>();
        HttpGet get=new HttpGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx731f37de4b367a23&secret=SECRET&code=" + authCode + "&grant_type=authorization_code");
        HttpResponse rep= client.execute(get);
        //返回结果
        result= EntityUtils.toString(rep.getEntity(),"utf-8");
        System.out.println("result");
        reMap.put("result", result);
        return reMap;
    }
}
