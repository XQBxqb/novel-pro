package com.novel.api.controller;

import com.novel.core.dto.ResFriendLinkDto;
import com.novel.core.dto.ResHomeBookDto;
import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-04 17:40
 * @explain
 */
@RequestMapping(RouterMapping.HOME_API)
public interface HomeApi {
    @PostMapping("/friend_Link/list")
    public RestRes<List<ResFriendLinkDto>> friendLinkList();

    @PostMapping("/books")
    public RestRes<List<ResHomeBookDto>> books();
}
