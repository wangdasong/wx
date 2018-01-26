package com.github.wangdasong.wx.service.impl;

import com.github.wangdasong.wx.dao.entity.Token;
import com.github.wangdasong.wx.dao.persistence.TokenDaoMapper;
import com.github.wangdasong.wx.dao.persistence.base.BaseDaoMapper;
import com.github.wangdasong.wx.service.TokenService;
import com.github.wangdasong.wx.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl extends BaseServiceImpl<Token> implements TokenService {

    @Autowired
    TokenDaoMapper tokenDaoMapper;
    @Override
    public BaseDaoMapper<Token> getCurrDaoMapper() {
        return tokenDaoMapper;
    }
}
