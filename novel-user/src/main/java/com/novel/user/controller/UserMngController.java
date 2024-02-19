package com.novel.user.controller;

import com.novel.api.controller.UserMngApi;
import com.novel.core.dto.*;
import com.novel.core.enums.ErrorStatusEnums;
import com.novel.core.res.RestRes;
import com.novel.core.thread.ThreadInfo;
import com.novel.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 昴星
 * @date 2023-09-04 18:00
 * @explain
 */

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserMngController implements UserMngApi {

    private final UserService userService;

    @Override
    public RestRes<ResRegisterDto> register(ReqRegisterDto reqRegisterDto) {
        return userService.addOne(reqRegisterDto);
    }

    @Override
    public RestRes<ResLoginDto> login(ReqLoginDto reqLoginDto) {
        return userService.isMatchLogin(reqLoginDto);
    }

    @Override
    public RestRes<ResUserInfoDto> getUserInfo() {
        String uid = ThreadInfo.getUserSession().get();
        if(StringUtils.isNotBlank(uid))
            return RestRes.errorEnum(ErrorStatusEnums.RES_USER_UID_FAILED);
        return userService.getUserInfo(Long.parseLong(uid));
    }

    @Override
    public RestRes<Void> updateUserInfo(ReqUserInfoDto userInfoDto) {
        return userService.updateUserInfo(userInfoDto);
    }

    @Override
    public RestRes<Void> updateUserComment(String id,String content) {
        return userService.updateUserComment(Long.parseLong(id),content);
    }

    @Override
    public RestRes<Void> deleteComment(String id) {
        return userService.deleteUserComment(Long.parseLong(id));
    }

    @Override
    public RestRes<Void> feedback(String content) {
        return userService.releaseFeedback(content);
    }

    @Override
    public RestRes<ResUserCommentDataDto> comments(ReqUserCommentDto commentDto) {
        return userService.queryComments(commentDto);
    }

    @Override
    public RestRes<Void> deleteFeedBack(String id) {
        return userService.deleteFeedback(Long.parseLong(id));
    }
}
