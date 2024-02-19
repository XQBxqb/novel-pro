package com.novel.user.service;

import com.novel.core.dto.*;
import com.novel.core.res.RestRes;

/**
 * @author 昴星
 * @date 2023-09-04 18:02
 * @explain
 */
public interface UserService {
    RestRes<ResRegisterDto> addOne(ReqRegisterDto reqRegisterDto);

    Boolean isMatchToken(Long id,String token);

    RestRes<ResLoginDto> isMatchLogin(ReqLoginDto reqLoginDto);

    RestRes<ResUserInfoDto> getUserInfo(Long uid);

    RestRes<Void> updateUserInfo(ReqUserInfoDto userInfoDto);

    RestRes<Void> updateUserComment(Long id,String content);

    RestRes<Void> deleteUserComment(Long id);

    RestRes<Void> releaseFeedback(String content);

    RestRes<ResUserCommentDataDto> queryComments(ReqUserCommentDto commentDto);

    RestRes<Void> deleteFeedback(Long id);
}
