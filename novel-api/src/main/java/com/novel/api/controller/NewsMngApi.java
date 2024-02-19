package com.novel.api.controller;

import com.novel.core.dto.ResNewsInfo;
import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-04 19:05
 * @explain
 */
@RequestMapping(RouterMapping.NEWS_FRONT_API)
public interface NewsMngApi {
    @PostMapping("/{id}")
    public RestRes<ResNewsInfo> queryNewsContent(@PathVariable Long id);

    @PostMapping("/lastest_list")
    public RestRes<List<ResNewsInfo>> queryNewsContentList();
}
