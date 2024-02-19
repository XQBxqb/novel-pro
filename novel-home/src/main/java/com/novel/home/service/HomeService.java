package com.novel.home.service;

;

import com.novel.core.dto.ResFriendLinkDto;
import com.novel.core.dto.ResHomeBookDto;
import com.novel.core.res.RestRes;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-04 17:45
 * @explain
 */
public interface HomeService {
    public RestRes<List<ResFriendLinkDto>> getAllFriendLinkList();

    public RestRes<List<ResHomeBookDto>> getHomeBookList();
}
