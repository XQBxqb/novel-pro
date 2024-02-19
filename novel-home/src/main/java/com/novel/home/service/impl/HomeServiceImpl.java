package com.novel.home.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.novel.core.dto.ResFriendLinkDto;
import com.novel.core.dto.ResHomeBookDto;
import com.novel.core.entity.HomeFriendLink;
import com.novel.core.res.RestRes;
import com.novel.home.dao.HomeBookMapper;
import com.novel.home.dao.HomeFriendLinkMapper;
import com.novel.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 昴星
 * @date 2023-10-04 17:45
 * @explain
 */
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final HomeFriendLinkMapper homeFriendLinkMapper;
    private final HomeBookMapper homeBookMapper;

    @Override
    public RestRes<List<ResFriendLinkDto>> getAllFriendLinkList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(Arrays.asList(new String[]{"link_name", "link_url"}));
        List<HomeFriendLink> list = homeFriendLinkMapper.selectList(queryWrapper);
        return RestRes.ok(list.stream()
                              .map(t -> homeFriendLinkMapToDto(t))
                              .collect(Collectors.toList()));
    }

    @Override
    public RestRes<List<ResHomeBookDto>> getHomeBookList() {
        return RestRes.ok(homeBookMapper.queryHomeBook());
    }

    private ResFriendLinkDto homeFriendLinkMapToDto(HomeFriendLink homeFriendLink) {
        ResFriendLinkDto resFriendLinkDto = new ResFriendLinkDto();
        BeanUtils.copyProperties(homeFriendLink, resFriendLinkDto);
        return resFriendLinkDto;
    }
}
