package com.github.wangdasong.wx.service.impl;

import com.github.wangdasong.wx.config.RedisBusiness;
import com.github.wangdasong.wx.dao.entity.Bonus;
import com.github.wangdasong.wx.dao.entity.Event;
import com.github.wangdasong.wx.dao.persistence.BonusDaoMapper;
import com.github.wangdasong.wx.dao.persistence.base.BaseDaoMapper;
import com.github.wangdasong.wx.service.BonusService;
import com.github.wangdasong.wx.service.EventService;
import com.github.wangdasong.wx.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BonusServiceImpl extends BaseServiceImpl<Bonus> implements BonusService {

    @Autowired
    EventService eventService;
    @Autowired
    BonusDaoMapper bonusDaoMapper;

    @Autowired
    private RedisBusiness redisBusiness;

    @Override
    public BaseDaoMapper<Bonus> getCurrDaoMapper() {
        return bonusDaoMapper;
    }

    @Override
    public Bonus addEntity(Bonus bonus){
        Bonus reBonus = super.addEntity(bonus);
        reloadSecKillRedis();
        return reBonus;
    }

    @Override
    public Bonus editEntity(Bonus bonus){
        Bonus reBonus = super.editEntity(bonus);
        reloadSecKillRedis();
        return reBonus;
    }

    @Override
    public Bonus delEntity(String id){
        Bonus reBonus = super.delEntity(id);
        try{
            redisBusiness.del(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return reBonus;
    }

    @Override
    @Scheduled(cron="0/1 * * * * ?")
    public void reloadSecKillRedis() {
        List<Bonus> bonuses = bonusDaoMapper.getListByCondition(new Bonus());
        for(Bonus bonus : bonuses){
            Event event = eventService.getEntityById(bonus.getEventId());
            Long timeLeft = event.getStartTime().getTime() - new Date().getTime();
            if(timeLeft > 0 ){
                try{
                    redisBusiness.set(bonus.getId(), bonus.getInitCount() + "");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                try{
                    Integer currCount = Integer.valueOf(redisBusiness.get(bonus.getId()));
                    if(currCount >=0){
                        Bonus updateBonus = new Bonus();
                        updateBonus.setLeftCount(currCount);
                        updateBonus.setId(bonus.getId());
                        bonusDaoMapper.update(updateBonus);
                        if(currCount == 0){
                            redisBusiness.set(bonus.getId(), "-1");
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
