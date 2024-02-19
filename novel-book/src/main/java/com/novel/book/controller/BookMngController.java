package com.novel.book.controller;


import com.novel.api.controller.BookMngApi;
import com.novel.book.service.BookService;
import com.novel.core.consts.RedisConsts;
import com.novel.core.consts.SystemConsts;
import com.novel.core.dto.*;
import com.novel.core.res.RestRes;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.client.protocol.ScoredEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author 昴星
 * @date 2023-09-07 21:45
 * @explain
 */

@RestController
@RequiredArgsConstructor
public class BookMngController implements BookMngApi {

    private final StringRedisTemplate redisTemplate;

    private final BookService bookService;


    @Autowired
    private Redisson redisson;


    @Override
    public RestRes<ResBookSearchDto> books(ReqBookSearchDto reqBookSearchDto) {
        RestRes<ResBookSearchDto> resBookSearchDtoRestRes = bookService.elasticSearchBook(reqBookSearchDto);
        return resBookSearchDtoRestRes;
    }

    @Override
    public RestRes<Void> visit(Long bookId) {
        RestRes<Void> voidRestRes = bookService.bookVisit(bookId);
        Double score = redisTemplate.opsForZSet()
                                    .score(RedisConsts.REDIS_BOOK_VISIT_COUNT, RedisConsts.REDIS_BOOK_VISIT_COUNT + ":" + bookId);
        if (score == null) redisTemplate.opsForZSet()
                                        .add(RedisConsts.REDIS_BOOK_VISIT_COUNT, RedisConsts.REDIS_BOOK_VISIT_COUNT + ":" + bookId, 1);
        else redisTemplate.opsForZSet()
                          .incrementScore(RedisConsts.REDIS_BOOK_VISIT_COUNT, RedisConsts.REDIS_BOOK_VISIT_COUNT + ":" + bookId, 1);
        return voidRestRes;
    }

    @Override
    public RestRes<ResBookInfoDto> getBookInfo(Long bookId) {
        return null;
    }

    @Override
    public RestRes<List<ResBookInfoDto>> visitRank() {
        RScoredSortedSet<String> zset = redisson.getScoredSortedSet(RedisConsts.REDIS_BOOK_VISIT_COUNT);
        Collection<ScoredEntry<String>> scoredEntries = zset.entryRangeReversed(0, 9);
        List<Long> idList = scoredEntries.stream()
                                         .map(t -> Long.parseLong(t.getValue()))
                                         .collect(Collectors.toList());
        return bookService.visitRankBookInfo(idList);
    }

    @Override
    public RestRes<List<ResBookInfoDto>> updateRank() {
        RScoredSortedSet<String> scoredSortedSet = redisson.getScoredSortedSet(RedisConsts.REDIS_BOOK_UPDATE_RANK);
        Collection<ScoredEntry<String>> scoredEntries = scoredSortedSet.entryRangeReversed(0, 9);
        List<Long> updateRankList = new ArrayList<>();
        scoredEntries.stream()
                     .forEach(t -> {
                         updateRankList.add(Long.parseLong(t.getValue()));
                     });
        return bookService.updateRankBookInfo(updateRankList);
    }

    @Override
    public RestRes<List<ResBookInfoDto>> newestRank() {
        RScoredSortedSet<String> scoredSortedSet = redisson.getScoredSortedSet(RedisConsts.REDIS_NEWEST_RANK);
        Collection<ScoredEntry<String>> scoredEntries = scoredSortedSet.entryRangeReversed(0, 9);
        List<Long> list = scoredEntries.stream()
                                       .map(t -> Long.parseLong(t.getValue()))
                                       .collect(Collectors.toList());
        return bookService.newestRankBookInfo(list);
    }

    @Override
    public RestRes<ResBookChapterDto> lastChapter(Long bookId) {
        RestRes<ResBookChapterDto> resBookChapterDtoRestRes = bookService.queryLastChapterById(bookId);
        return resBookChapterDtoRestRes;
    }

    @Override
    public RestRes<ResContentChapterDto> content(Long chapterId) {
        return bookService.queryContentByChapterId(chapterId);
    }

    @Override
    public RestRes<List<ResBookChapterDto>> chapterList(Long bookId) {
        return bookService.queryBookChapterListById(bookId);
    }

    @Override
    public RestRes<List<ResBookCategoryDto>> categoryList(Integer workDirection) {
        return bookService.queryCategoryListByWorkDirection(workDirection);
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = SystemConsts.DATASOURCE_LIMIT_TIME, rollbackFor = Exception.class)
    @Override
    public RestRes<Void> bookRelease(ReqAuthorBookDto bookDto) {
        return bookService.insertBookInfoOne(bookDto);
    }

    @Override
    public RestRes<Void> chapterRelease(ReqAuthorReleaseChapterDto releaseChapterDto) {
        return bookService.releaseBookChapter(releaseChapterDto);
    }

    @Override
    public RestRes<ResAuthorBooksDataDto> books(String pageNum, String pageSize) {
        return bookService.queryAuthorServerBooks(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
    }

    @Override
    public RestRes<ResAuthorChaptersDto> chapters(String bookId, String pageNum, String pageSize) {
        return bookService.queryAuthorServerChapters(Long.parseLong(bookId), Integer.parseInt(pageNum), Integer.parseInt(pageSize));
    }
}
