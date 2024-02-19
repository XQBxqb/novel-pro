package com.novel.author.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.novel.author.dao.BookChapterMapper;
import com.novel.author.feign.AuthorFeignApi;
import com.novel.author.service.AuthorService;
import com.novel.core.consts.DataSourceColumeConst;
import com.novel.core.consts.SystemConsts;
import com.novel.core.dto.ReqAuthorBookDto;
import com.novel.core.dto.ReqAuthorReleaseChapterDto;
import com.novel.core.dto.ReqAuthorUpdateChapterDto;
import com.novel.core.dto.ResAuthorChapterDto;
import com.novel.core.entity.BookChapter;
import com.novel.core.enums.ErrorStatusEnums;
import com.novel.core.res.RestRes;
import com.novel.core.thread.ThreadInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-12 20:45
 * @explain
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final BookChapterMapper bookChapterMapper;

    private final AuthorFeignApi authorFeign;

    @Override
    public RestRes<ResAuthorChapterDto> getChapterById(Long chapterId) {
        ResAuthorChapterDto resAuthorChapterDto1 = bookChapterMapper.queryChapterById(chapterId);
        return RestRes.ok(resAuthorChapterDto1);
    }
    //rollbackFor = Exception.class,定义触发事务回滚的异常类型，这里是所有异常回滚
    @Transactional(propagation = Propagation.REQUIRED,timeout = SystemConsts.DATASOURCE_LIMIT_TIME,
            rollbackFor = Exception.class)
    @Override
    public RestRes<Void> updateChapter(Long chapterId, ReqAuthorUpdateChapterDto chapterDto) {
        bookChapterMapper.updateChapterById(chapterId,chapterDto.getChapterName(),chapterDto.getChapterContent());
        return RestRes.ok();
    }
    //对于事务链（就是不同事物之间可以合成一个大事务，它们要么都成功，要么都失败，这里使用的策略是Propagation.REQUIRED,）
    @Transactional(propagation = Propagation.REQUIRED,timeout = SystemConsts.DATASOURCE_LIMIT_TIME,
            rollbackFor = Exception.class)
    @Override
    public RestRes deleteChapter(Long chapterId) {
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(DataSourceColumeConst.T_BOOK_CHAPTER_BOOK_ID);
        queryWrapper.eq(DataSourceColumeConst.T_BOOK_CHAPTER_ID,chapterId);
        List<Object> list = bookChapterMapper.selectObjs(queryWrapper);
        if(list.get(0)==null)
            return RestRes.errorEnum(ErrorStatusEnums.RES_NOT_EXIT_CHAPTER);
        BigInteger bookIdBig = (BigInteger) list.get(0);
        Long bookId = bookIdBig.longValue();
        deleteChapterStepOne(chapterId);
        updateBookInfoAferDeleteChapter(bookId);
        return RestRes.ok();
    }

    @Override
    public RestRes<Void> bookRelease(ReqAuthorBookDto bookDto) {
        bookDto.setAuthorId(Long.parseLong(
                ThreadInfo.getUserSession().get()
        ));
        return authorFeign.bookRelease(bookDto);
    }

    @Override
    public RestRes<Void> bookChapterRelease(ReqAuthorReleaseChapterDto chapterDto) {
        return authorFeign.chapterRelease(chapterDto);
    }

    @Override
    public RestRes<Integer> getAuthorStatus() {
        String idStr = ThreadInfo.getUserSession().get();
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED,timeout = SystemConsts.DATASOURCE_LIMIT_TIME,
            rollbackFor = Exception.class)
    public void deleteChapterStepOne(Long chapterId){
        bookChapterMapper.deleteChapterById(chapterId);
    }

    @Transactional(propagation = Propagation.REQUIRED,timeout = SystemConsts.DATASOURCE_LIMIT_TIME,
            rollbackFor = Exception.class)
    public void updateBookInfoAferDeleteChapter(Long bookId){
        bookChapterMapper.updateBookInfoAfterDeleteChapter(bookId);
    }
}
