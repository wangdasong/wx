package com.github.wangdasong.wx.service;

import com.github.wangdasong.wx.dao.entity.WxBns;
import com.github.wangdasong.wx.service.base.BaseService;

public interface WxBnsService extends BaseService<WxBns>{
    public WxBns secKill(String wxCode, String bonusId);
}
