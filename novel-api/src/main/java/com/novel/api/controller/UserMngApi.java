package com.novel.api.controller;

import com.novel.core.dto.*;
import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

/**
 * @author 昴星
 * @date 2023-09-03 19:56
 * @explain
 */

@RequestMapping(RouterMapping.USER_MNG_API)
public interface UserMngApi {
    @PostMapping("/register")
    public RestRes<ResRegisterDto> register(@RequestBody ReqRegisterDto reqRegisterDto);

    @PostMapping("/login")
    public RestRes<ResLoginDto> login(@RequestBody ReqLoginDto reqLoginDto);

    @GetMapping("/")
    public RestRes<ResUserInfoDto> getUserInfo();

    @PutMapping("/user")
    public RestRes<Void> updateUserInfo(@RequestBody ReqUserInfoDto userInfoDto);

    @PutMapping("/comment/{id}")
    public RestRes<Void> updateUserComment(@PathVariable("id") String id,@RequestParam("content") String content);

    @Delete("/comment/{id}")
    public RestRes<Void> deleteComment(@PathVariable("id") String id);

    @PostMapping("/feedback")
    public RestRes<Void> feedback(@RequestParam String content);

    @GetMapping("/comments")
    public RestRes<ResUserCommentDataDto> comments(@RequestBody ReqUserCommentDto commentDto);

    @Delete("/feedback/{id}")
    public RestRes<Void> deleteFeedBack(@PathVariable("id") String id);
}
