package com.github.wangdasong.wx.dao.entity;

import com.github.wangdasong.wx.dao.entity.base.BaseEntity;

public class WxBns extends BaseEntity {
    String wxCode;
    String bonusId;
    Bonus bonus;


    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public String getBonusId() {
        return bonusId;
    }

    public void setBonusId(String bonusId) {
        this.bonusId = bonusId;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }
}
