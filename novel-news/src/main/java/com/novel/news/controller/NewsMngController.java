package com.novel.news.controller;


import com.novel.api.controller.NewsMngApi;
import com.novel.core.dto.ResNewsInfo;
import com.novel.core.res.RestRes;
import com.novel.news.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-04 19:09
 * @explain
 */

@RestController
@RequiredArgsConstructor
public class NewsMngController implements NewsMngApi {
    private final NewsService newsService;


    @Override
    public RestRes<ResNewsInfo> queryNewsContent(Long id) {
        return newsService.newsContent(id);
    }

    @Override
    public RestRes<List<ResNewsInfo>> queryNewsContentList() {
        return newsService.newsContentList();
    }
}
