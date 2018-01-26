package com.github.wangdasong.wx.service.impl;

import com.github.wangdasong.wx.config.RedisBusiness;
import com.github.wangdasong.wx.dao.entity.Bonus;
import com.github.wangdasong.wx.dao.entity.WxBns;
import com.github.wangdasong.wx.dao.persistence.WxBnsDaoMapper;
import com.github.wangdasong.wx.dao.persistence.base.BaseDaoMapper;
import com.github.wangdasong.wx.redis.MySecKillRedis;
import com.github.wangdasong.wx.service.BonusService;
import com.github.wangdasong.wx.service.WxBnsService;
import com.github.wangdasong.wx.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxBnsServiceImpl extends BaseServiceImpl<WxBns> implements WxBnsService {

    @Autowired
    BonusService bonusService;
    @Autowired
    WxBnsDaoMapper wxBnsDaoMapper;

    @Autowired
    RedisBusiness redisBusiness;
    @Override
    public BaseDaoMapper<WxBns> getCurrDaoMapper() {
        return wxBnsDaoMapper;
    }

    @Override
    public WxBns secKill(String wxCode, String bonusId) {
        Bonus bonus = bonusService.getEntityById(bonusId);
        //校验是否已经获得奖品
        WxBns queryWxBns = new WxBns();
        queryWxBns.setWxCode(wxCode);
        Bonus queryBonus = new Bonus();
        queryBonus.setEventId(bonus.getEventId());
        queryWxBns.setBonus(queryBonus);
        List<WxBns> hadWxBnsList = super.getEntityListByCondition(queryWxBns);
        if(hadWxBnsList != null && hadWxBnsList.size() > 0){
            return hadWxBnsList.get(0);
        }
        MySecKillRedis myRunnableRedis = new MySecKillRedis(redisBusiness, wxCode, bonus.getEventId(), bonusId);
        int status = myRunnableRedis.doSecKill();
        if(status == 1){
            WxBns wxBns = new WxBns();
            wxBns.setWxCode(wxCode);
            wxBns.setBonusId(bonusId);
            wxBns.setCreateUserId(wxCode);
            wxBns.setUpdateUserId(wxCode);
            return this.addEntity(wxBns);
        }
        return null;
    }
}
