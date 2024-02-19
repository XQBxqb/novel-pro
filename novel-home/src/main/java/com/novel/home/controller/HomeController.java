package com.novel.home.controller;


import com.novel.api.controller.HomeApi;
import com.novel.core.dto.ResFriendLinkDto;
import com.novel.core.dto.ResHomeBookDto;
import com.novel.core.res.RestRes;
import com.novel.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-04 17:44
 * @explain
 */
@RestController
@RequiredArgsConstructor
public class HomeController implements HomeApi {
    private final HomeService homeService;
    @Override
    public RestRes<List<ResFriendLinkDto>> friendLinkList() {
        return homeService.getAllFriendLinkList();
    }

    @Override
    public RestRes<List<ResHomeBookDto>> books() {
        return homeService.getHomeBookList();
    }
}
