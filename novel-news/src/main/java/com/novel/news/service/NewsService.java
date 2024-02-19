package com.novel.news.service;

import com.novel.core.dto.ResNewsInfo;
import com.novel.core.res.RestRes;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-04 19:03
 * @explain
 */
public interface NewsService {
    public RestRes<ResNewsInfo> newsContent(Long id);

    public RestRes<List<ResNewsInfo>> newsContentList();
}
