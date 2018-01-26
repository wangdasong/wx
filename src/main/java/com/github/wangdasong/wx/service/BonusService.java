package com.github.wangdasong.wx.service;

import com.github.wangdasong.wx.dao.entity.Bonus;
import com.github.wangdasong.wx.service.base.BaseService;

public interface BonusService extends BaseService<Bonus>{
    public void reloadSecKillRedis();
}
