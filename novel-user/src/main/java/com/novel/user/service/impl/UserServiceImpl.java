package com.novel.user.service.impl;


import com.novel.api.exception.BusinessException;
import com.novel.core.consts.DataSourceColumeConst;
import com.novel.core.consts.SystemConsts;
import com.novel.core.dto.*;
import com.novel.core.entity.BookInfo;
import com.novel.core.entity.UserComment;
import com.novel.core.entity.UserFeedback;
import com.novel.core.entity.UserInfo;
import com.novel.core.enums.ErrorStatusEnums;
import com.novel.core.res.RestRes;
import com.novel.core.thread.ThreadInfo;
import com.novel.core.utils.JwtUtils;
import com.novel.user.dao.BookInfoMapper;
import com.novel.user.dao.UserCommentMapper;
import com.novel.user.dao.UserFeedbackMapper;
import com.novel.user.feign.UserFeignApi;
import com.novel.user.dao.UserInfoMapper;
import com.novel.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 昴星
 * @date 2023-09-04 18:03
 * @explain
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserFeignApi userFeiginApi;

    @Autowired
    private UserInfoMapper userInfoMapper;

    private final UserCommentMapper userCommentMapper;

    private final UserFeedbackMapper userFeedbackMapper;

    private final BookInfoMapper bookInfoMapper;

    @Override
    public RestRes<ResRegisterDto> addOne(ReqRegisterDto reqRegisterDto) {
        if (StringUtils.isBlank(reqRegisterDto.getUsername()) ||
                StringUtils.isBlank(reqRegisterDto.getPassword()) ||
                StringUtils.isBlank(reqRegisterDto.getSessionId()) ||
                StringUtils.isBlank(reqRegisterDto.getVelCode()))
            throw new BusinessException(ErrorStatusEnums.RES_USER_INFO_SHORT);
        RestRes<Boolean> booleanRestReq = userFeiginApi.verifyCode(reqRegisterDto.getVelCode(), reqRegisterDto.getSessionId());
        if (!booleanRestReq.getData()) throw new BusinessException(ErrorStatusEnums.RES_VERIFY_CODE_NOT_MATCH);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataSourceColumeConst.T_USER_INFO_USERNAME, reqRegisterDto.getUsername());
        Long count = userInfoMapper.selectCount(queryWrapper);
        if (count == 1) throw new BusinessException(ErrorStatusEnums.RES_REGISTER_USERANAME_EXIT);
        UserInfo userInfo = new UserInfo();
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfo.setSalt("emt");
        BeanUtils.copyProperties(reqRegisterDto, userInfo);
        userInfoMapper.insert(userInfo);
        return RestRes.ok(ResRegisterDto.builder().
                                        uid(userInfo.getId()).
                                        token(JwtUtils.generateToken(userInfo.getId(), SystemConsts.JWT_TOKEN_KEY))
                                        .build());
    }

    @Override
    public Boolean isMatchToken(Long id, String token) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataSourceColumeConst.T_USER_INFO_ID, id);
        boolean exists = userInfoMapper.exists(queryWrapper);
        return exists;
    }

    @Override
    public RestRes<ResLoginDto> isMatchLogin(ReqLoginDto reqLoginDto) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataSourceColumeConst.T_USER_INFO_USERNAME, reqLoginDto.getUsername())
                    .eq(DataSourceColumeConst.T_USER_INFO_PASSWORD, reqLoginDto.getPassword());
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        if (userInfo == null) throw new BusinessException(ErrorStatusEnums.RES_LOGIN_NOT_MATCH);

        return RestRes.ok(ResLoginDto.builder()
                                     .nickName(userInfo.getNickName()).
                                     uid(userInfo.getId()).
                                     token(JwtUtils.generateToken(userInfo.getId(), SystemConsts.JWT_TOKEN_KEY))
                                     .build());
    }

    @Override
    public RestRes<ResUserInfoDto> getUserInfo(Long uid) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", uid);
        queryWrapper.select("nick_name", "user_photo", "user_sex");
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        ResUserInfoDto resUserInfoDto = new ResUserInfoDto();
        BeanUtils.copyProperties(userInfo, resUserInfoDto);
        return RestRes.ok(resUserInfoDto);
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = SystemConsts.DATASOURCE_LIMIT_TIME,
            rollbackFor = Exception.class)
    @Override
    public RestRes<Void> updateUserInfo(ReqUserInfoDto userInfoDto) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDto, userInfoDto);
        userInfo.setId(userInfoDto.getUserId());
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userInfoDto.getUserId());
        userInfoMapper.update(userInfo, queryWrapper);
        return RestRes.ok();
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = SystemConsts.DATASOURCE_LIMIT_TIME,
            rollbackFor = Exception.class)
    @Override
    public RestRes<Void> updateUserComment(Long id, String content) {
        QueryWrapper<UserComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        UserComment userComment = new UserComment();
        userComment.setUserId(id);
        userComment.setCommentContent(content);
        userComment.setUpdateTime(LocalDateTime.now());
        userCommentMapper.update(userComment, queryWrapper);
        return RestRes.ok();
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = SystemConsts.DATASOURCE_LIMIT_TIME,
            rollbackFor = Exception.class)
    @Override
    public RestRes<Void> deleteUserComment(Long id) {
        userInfoMapper.deleteById(id);
        return RestRes.ok();
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = SystemConsts.DATASOURCE_LIMIT_TIME,
            rollbackFor = Exception.class)
    @Override
    public RestRes<Void> releaseFeedback(String content) {
        String uid = ThreadInfo.getUserSession()
                               .get();
        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setUserId(Long.parseLong(uid));
        userFeedback.setContent(content);
        userFeedback.setUpdateTime(LocalDateTime.now());
        userFeedback.setCreateTime(LocalDateTime.now());
        userFeedbackMapper.insert(userFeedback);
        return RestRes.ok();
    }

    @Override
    public RestRes<ResUserCommentDataDto> queryComments(ReqUserCommentDto commentDto) {
        Integer pageSize = commentDto.getPageSize() == null ? 5 : commentDto.getPageSize();
        Integer pageNum = commentDto.getPageNum() == null ? 1 : commentDto.getPageNum();
        Long userId = Long.parseLong(ThreadInfo.getUserSession()
                                               .get());
        Page<UserComment> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UserComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderBy(true, true, "book_id");
        Page<UserComment> userCommentPage = userCommentMapper.selectPage(page, queryWrapper);
        int total = (int) userCommentPage.getTotal();
        if (total == 0)
            return RestRes.ok(new ResUserCommentDataDto(total));
        List<UserComment> userCommentList = userCommentPage.getRecords();
        List<Long> bookIdList = userCommentList.stream()
                                               .map(t -> t.getBookId())
                                               .collect(Collectors.toList());
        List<BookInfo> bookInfos = bookInfoMapper.selectBatchIds(bookIdList);
        List<ResUserCommentDto> list = new ArrayList<>();

        AtomicInteger atomicInteger = new AtomicInteger(0);
        userCommentList.stream()
                       .forEach(t -> {
                           if (bookInfos.get(atomicInteger.get())
                                        .getId() < t.getBookId()) {
                               atomicInteger.incrementAndGet();
                               return;
                           }
                           if (bookInfos.get(atomicInteger.get())
                                        .getId() > t.getBookId())
                               return;
                           BookInfo bookInfo = bookInfos.get(atomicInteger.get());
                           list.add(ResUserCommentDto.builder().
                                                     commentContent(t.getCommentContent()).
                                                     commentBook(bookInfo.getBookName()).
                                                     commentBookPic(bookInfo.getPicUrl()).
                                                     commentTime(t.getUpdateTime())
                                                     .build());
                       });

        return RestRes.ok(ResUserCommentDataDto.builder().
                                               pageNum(pageNum).
                                               pageSize(pageSize).
                                               total(total)
                                               .list(list)
                                               .build());
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = SystemConsts.DATASOURCE_LIMIT_TIME,
            rollbackFor = Exception.class)
    @Override
    public RestRes<Void> deleteFeedback(Long id) {
        userFeedbackMapper.deleteById(id);
        return RestRes.ok();
    }
}
