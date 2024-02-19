package com.novel.zuul.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.novel.core.consts.DataSourceColumeConst;
import com.novel.core.entity.UserInfo;
import com.novel.zuul.mapper.UserInfoMapper;
import com.novel.zuul.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 昴星
 * @date 2023-10-07 23:26
 * @explain
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public Boolean isMatchToken(Long id, String token) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataSourceColumeConst.T_USER_INFO_ID,id);
        return userInfoMapper.exists(queryWrapper);
    }
}
