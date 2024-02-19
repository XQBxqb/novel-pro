package com.novel.book.service;



import com.novel.core.dto.*;
import com.novel.core.res.RestRes;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-09-07 21:46
 * @explain
 */
public interface BookService {
    public RestRes<List<ResBookChapterDto>> getBookChapterList();

    public RestRes<ResBookSearchDto> elasticSearchBook(ReqBookSearchDto bookSearchDto);

    public RestRes<Void> bookVisit (Long bookId);

    public RestRes<ResBookInfoDto> getBookInfo(Long bookId);

    public RestRes<List<ResBookInfoDto>> visitRankBookInfo(List<Long> idList);

    public RestRes<List<ResBookInfoDto>> updateRankBookInfo(List<Long> updateIdRankList);

    public RestRes<List<ResBookInfoDto>> newestRankBookInfo(List<Long> idList);

    public RestRes<ResBookChapterDto> queryLastChapterById(Long bookId);

    public RestRes<ResContentChapterDto> queryContentByChapterId(Long chapterId);

    public RestRes<List<ResBookChapterDto>> queryBookChapterListById(Long bookId);

    public RestRes<List<ResBookCategoryDto>> queryCategoryListByWorkDirection(Integer workDirection);

    public RestRes<Void> insertBookInfoOne(ReqAuthorBookDto bookDto);

    public RestRes<Void> releaseBookChapter(ReqAuthorReleaseChapterDto chapterDto);

    public RestRes<ResAuthorBooksDataDto> queryAuthorServerBooks(Integer pageNum,Integer pageSize);

    public RestRes<ResAuthorChaptersDto> queryAuthorServerChapters(Long bookId,Integer pageNum,Integer pageSize);
}
