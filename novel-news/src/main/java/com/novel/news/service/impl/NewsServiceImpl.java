package com.novel.news.service.impl;


import com.novel.core.dto.ResNewsInfo;
import com.novel.core.res.RestRes;
import com.novel.news.dao.NewsInfoMapper;
import com.novel.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-04 19:03
 * @explain
 */
@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsInfoMapper newsInfoMapper;
    @Override
    public RestRes<ResNewsInfo> newsContent(Long id) {
        return RestRes.ok(newsInfoMapper.queryNewsById(id).get(0));
    }

    @Override
    public RestRes<List<ResNewsInfo>> newsContentList() {
        return RestRes.ok(newsInfoMapper.queryListNews());
    }
}
