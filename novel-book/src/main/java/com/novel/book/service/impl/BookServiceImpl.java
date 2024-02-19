package com.novel.book.service.impl;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.mapping.FieldType;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.novel.api.exception.BusinessException;
import com.novel.book.dao.BookCategoryMapper;
import com.novel.book.dao.BookChapterMapper;
import com.novel.book.dao.BookContentMapper;
import com.novel.book.dao.BookInfoMapper;
import com.novel.book.service.BookService;
import com.novel.core.consts.DataSourceColumeConst;
import com.novel.core.consts.ElasticConsts;
import com.novel.core.consts.RedisConsts;
import com.novel.core.consts.SystemConsts;
import com.novel.core.dto.*;
import com.novel.core.entity.BookCategory;
import com.novel.core.entity.BookChapter;
import com.novel.core.entity.BookInfo;
import com.novel.core.entity.BookInfoCombineChapter;
import com.novel.core.enums.ErrorStatusEnums;
import com.novel.core.res.RestRes;
import com.novel.core.utils.NumsOfChineseUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 昴星
 * @date 2023-09-10 16:26
 * @explain
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final Redisson redisson;

    private final BookContentMapper bookContentMapper;
    private final ElasticsearchClient client;

    private final BookChapterMapper bookChapterMapper;

    private final BookInfoMapper bookInfoMapper;

    private final BookCategoryMapper bookCategoryMapper;

    @Override
    public RestRes<List<ResBookChapterDto>> getBookChapterList() {
        List<BookChapter> bookChapters = bookChapterMapper.selectLastestChapter();
        return getListRestRes(bookChapters);
    }

    private static RestRes<List<ResBookChapterDto>> getListRestRes(List<BookChapter> bookChapters) {
        return RestRes.ok(bookChapters.stream()
                                      .map(t -> ResBookChapterDto.builder()
                                                                 .id(t.getId())
                                                                 .bookId(t.getBookId())
                                                                 .chapterName(t.getChapterName())
                                                                 .chapterNum(t.getChapterNum())
                                                                 .isVip(t.getIsVip())
                                                                 .chapterWordCount(t.getWordCount())
                                                                 .chapterUpdateTime(t.getUpdateTime())
                                                                 .build())
                                      .collect(Collectors.toList()));
    }

    @Override
    public RestRes<ResBookSearchDto> elasticSearchBook(ReqBookSearchDto reqBookSearchDto) {
        SearchResponse<? extends ESBookDto> searchResponse = null;
        searchResponse = getResponse(reqBookSearchDto);
        if (searchResponse == null) log.info("error");
        List<BookSearchDto> bookSearchDtos = searchResponse.hits()
                                                           .hits()
                                                           .stream()
                                                           .map(t -> t.source())
                                                           .collect(Collectors.toList())
                                                           .stream()
                                                           .map(t -> mapToBookDto(t))
                                                           .collect(Collectors.toList());
        return getDtoRestRes(reqBookSearchDto, bookSearchDtos);
    }

    private SearchResponse<? extends ESBookDto> getResponse(ReqBookSearchDto reqBookSearchDto) {
        SearchResponse<? extends ESBookDto> searchResponse;
        try {
            searchResponse = searchResponse(reqBookSearchDto);
        } catch (IOException e) {
            throw new BusinessException(ErrorStatusEnums.RES_ES_ERR);
        }
        return searchResponse;
    }

    private static RestRes<ResBookSearchDto> getDtoRestRes(ReqBookSearchDto reqBookSearchDto, List<BookSearchDto> bookSearchDtos) {
        return RestRes.ok(ResBookSearchDto.builder()
                                          .list(bookSearchDtos)
                                          .pageNum(reqBookSearchDto.getPageNum())
                                          .pageSize(reqBookSearchDto.getPageSize())
                                          .total(bookSearchDtos.size())
                                          .build());
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = -1, readOnly = false, rollbackFor = Exception.class)
    @Override
    public RestRes bookVisit(Long bookId) {
        bookInfoMapper.updateBookVisitCount(bookId);
        return RestRes.ok();
    }


    @Override
    public RestRes<ResBookInfoDto> getBookInfo(Long bookId) {
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);
        ResBookInfoDto resBookInfoDto = new ResBookInfoDto();
        BeanUtils.copyProperties(bookInfo, resBookInfoDto);
        return RestRes.ok(resBookInfoDto);
    }

    @Override
    public RestRes<List<ResBookInfoDto>> visitRankBookInfo(List<Long> idList) {
        return getBookInfoListById(idList);
    }

    @Override
    public RestRes<List<ResBookInfoDto>> updateRankBookInfo(List<Long> updateIdRankList) {
        return getBookInfoListById(updateIdRankList);
    }

    @Override
    public RestRes<List<ResBookInfoDto>> newestRankBookInfo(List<Long> idList) {
        return getBookInfoListById(idList);
    }

    @Override
    public RestRes<ResBookChapterDto> queryLastChapterById(Long bookId) {
        BookInfoCombineChapter bookInfoCombineChapter = bookChapterMapper.queryLastChapterById(bookId);
        ResBookChapterDto resBookChapterDto = new ResBookChapterDto();
        BeanUtils.copyProperties(bookInfoCombineChapter, resBookChapterDto);
        resBookChapterDto.setChapterWordCount(bookInfoCombineChapter.getWordCount());
        resBookChapterDto.setChapterUpdateTime(bookInfoCombineChapter.getLastChapterUpdateTime());
        return RestRes.ok(resBookChapterDto);
    }

    @Override
    public RestRes<ResContentChapterDto> queryContentByChapterId(Long chapterId) {
        BookChapter bookChapter = bookChapterMapper.selectById(chapterId);
        Long bookId = bookChapter.getBookId();
        BookInfo bookInfo = bookInfoMapper.selectById(bookId);
        ResContentChapterDto resContentChapterDto = getResContentChapterDto(bookChapter, bookInfo);
        return RestRes.ok(resContentChapterDto);
    }

    private static ResContentChapterDto getResContentChapterDto(BookChapter bookChapter, BookInfo bookInfo) {
        ResContentChapterDto resContentChapterDto = new ResContentChapterDto();
        BeanUtils.copyProperties(bookChapter, resContentChapterDto.getChapterDto());
        BeanUtils.copyProperties(bookInfo, resContentChapterDto.getBookInfoDto());
        resContentChapterDto.getChapterDto()
                            .setChapterWordCount(bookChapter.getWordCount());
        resContentChapterDto.getChapterDto()
                            .setChapterUpdateTime(bookChapter.getUpdateTime());
        return resContentChapterDto;
    }

    @Override
    public RestRes<List<ResBookChapterDto>> queryBookChapterListById(Long bookId) {
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId);
        List<BookChapter> bookChapters = bookChapterMapper.selectList(queryWrapper);
        List<ResBookChapterDto> resBookChapterDtos = new ArrayList<>();
        extracted(bookChapters, resBookChapterDtos);
        return RestRes.ok(resBookChapterDtos);
    }

    private static void extracted(List<BookChapter> bookChapters, List<ResBookChapterDto> resBookChapterDtos) {
        entityMapToDto(bookChapters, resBookChapterDtos);
    }

    @Override
    public RestRes<List<ResBookCategoryDto>> queryCategoryListByWorkDirection(Integer workDirection) {
        QueryWrapper queryWrapperl = new QueryWrapper<>();
        queryWrapperl.eq("work_direction", workDirection);
        List<BookCategory> list = bookCategoryMapper.selectList(queryWrapperl);
        return RestRes.ok(list.stream()
                              .map(t -> mapToCategoryDto(t))
                              .collect(Collectors.toList()));
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = SystemConsts.DATASOURCE_LIMIT_TIME, rollbackFor = Exception.class)
    @Override
    public RestRes<Void> insertBookInfoOne(ReqAuthorBookDto bookDto) {
        BookInfo bookInfo = new BookInfo();
        BeanUtils.copyProperties(bookDto, bookInfo);
        bookInfoMapper.insert(bookInfo);
        return RestRes.ok();
    }

    //多事务处理+分布式锁
    @Transactional(propagation = Propagation.REQUIRED, timeout = -1, readOnly = false, rollbackFor = Exception.class)
    @Override
    public RestRes<Void> releaseBookChapter(ReqAuthorReleaseChapterDto chapterDto) {
        if (chapterDto.getChapterName() == null) return RestRes.ok();
        Long bookId = Long.parseLong(chapterDto.getBookId());
        RLock lock = redisson.getLock(RedisConsts.REDIS_LAST_CHAPTER_NUM_LOCK);
        lock.lock();

        try {
            RBucket<Integer> redisLastChapterNum = redisson.getBucket("REDIS_LAST_CHAPTER_NUM");
            Integer lastChapterNum = redisLastChapterNum.get();
            if (lastChapterNum == null) {
                lastChapterNum = syncChapterLastChapterNumToRedis(bookId);
            }
            HashMap<String, Object> map = new HashMap<>();
            Integer wordOfChines = NumsOfChineseUtils.countChineseCharacters(chapterDto.getChapterContent());
            map.put("bookId", bookId);
            map.put("chapterName", chapterDto.getChapterName());
            map.put("wordCount", wordOfChines);
            map.put("isVip", chapterDto.getIsVip());
            map.put("chapterNum", Integer.valueOf(lastChapterNum + 1));
            bookChapterMapper.insertBookChapter(map);

            BigInteger chapterIdBigInteger = (BigInteger) bookInfoMapper.queryBookChapterId(Long.parseLong(chapterDto.getBookId()))
                                                                        .get(0);
            Long chapterId = chapterIdBigInteger.longValue();
            HashMap<String, Object> contentMap = new HashMap<>();
            contentMap.put("chapterId", chapterId);
            contentMap.put("content", chapterDto.getChapterContent());
            syncBookContent(contentMap);

            HashMap<String, Object> bookMap = new HashMap<>();
            bookMap.put("bookId", chapterDto.getBookId());
            bookMap.put("wordCount", wordOfChines);
            bookMap.put("chapterId", chapterId);
            bookMap.put("chapterName", chapterDto.getChapterName());
            syncBookInfo(bookMap);
        } finally {
            lock.unlock();
        }
        return RestRes.ok();
    }

    @Override
    public RestRes<ResAuthorBooksDataDto> queryAuthorServerBooks(Integer pageNum, Integer pageSize) {
        if (pageNum == 0) pageNum = 1;
        if (pageSize == 0) pageSize = 5;
        Page<BookInfo> page = new Page<>(pageNum, pageSize);
        Page<BookInfo> bookInfoPage = bookInfoMapper.selectPage(page, null);

        List<ResAuthorBooks> booksList = new ArrayList<>();
        bookInfoPage.getRecords()
                    .stream()
                    .forEach(t -> {
                        ResAuthorBooks resAuthorBooks = new ResAuthorBooks();
                        BeanUtils.copyProperties(t, resAuthorBooks);
                        booksList.add(resAuthorBooks);
                    });

        return RestRes.ok(ResAuthorBooksDataDto.builder()
                                               .pageSize(pageSize)
                                               .pageNum(pageNum)
                                               .total(bookInfoPage.getTotal())
                                               .list(booksList)
                                               .build());
    }

    @Override
    public RestRes<ResAuthorChaptersDto> queryAuthorServerChapters(Long bookId, Integer pageNum, Integer pageSize) {
        if (pageNum == 0) pageNum = 1;
        if (pageSize == 0) pageSize = 5;
        Page<BookChapter> page = new Page<>(pageNum, pageSize);
        QueryWrapper<BookChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(DataSourceColumeConst.T_BOOK_CHAPTER_BOOK_ID, bookId);
        Page<BookChapter> bookChapterPage = bookChapterMapper.selectPage(page, queryWrapper);
        List<ResBookChapterDto> bookChapterDtos = new ArrayList<>();
        entityMapToDto(bookChapterPage.getRecords(), bookChapterDtos);
        return RestRes.ok(ResAuthorChaptersDto.builder()
                                              .list(bookChapterDtos)
                                              .pageNum(pageNum)
                                              .pageSize(pageSize)
                                              .total(bookChapterPage.getTotal())
                                              .build());
    }

    private static void entityMapToDto(List<BookChapter> bookChapterPage, List<ResBookChapterDto> bookChapterDtos) {
        bookChapterPage.stream()
                       .forEach(t -> {
                           ResBookChapterDto resBookChapterDto = new ResBookChapterDto();
                           BeanUtils.copyProperties(t, resBookChapterDto);
                           resBookChapterDto.setChapterWordCount(t.getWordCount());
                           resBookChapterDto.setChapterUpdateTime(t.getUpdateTime());
                           bookChapterDtos.add(resBookChapterDto);
                       });
    }

    private ResBookCategoryDto mapToCategoryDto(BookCategory bookCategory) {
        ResBookCategoryDto resBookCategoryDto = new ResBookCategoryDto();
        BeanUtils.copyProperties(bookCategory, resBookCategoryDto);
        return resBookCategoryDto;
    }

    private BookSearchDto mapToBookDto(ESBookDto esBookDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime bookDateTime = LocalDateTime.parse(esBookDto.getLastChapterUpdateTime()
                                                                  .substring(0, 19) + "Z", formatter);
        BookSearchDto bookSearchDto = getBookSearchDto(esBookDto, bookDateTime);
        return bookSearchDto;
    }

    private static BookSearchDto getBookSearchDto(ESBookDto esBookDto, LocalDateTime bookDateTime) {
        BookSearchDto build = BookSearchDto.builder()
                                           .id(esBookDto.getId())
                                           .bookName(esBookDto.getBookName())
                                           .bookStatus(Integer.parseInt(esBookDto.getBookStatus()))
                                           .workDirection(esBookDto.getWorkDirection())
                                           .categoryId(esBookDto.getId())
                                           .categoryName(esBookDto.getCategoryName())
                                           .picUrl(esBookDto.getPicUrl())
                                           .authorId(esBookDto.getId())
                                           .authorName(esBookDto.getAuthorName())
                                           .bookDesc(esBookDto.getBookDesc())
                                           .updateTime(bookDateTime)
                                           .isVip(esBookDto.getIsVip()
                                                           .intValue())
                                           .commentCount(esBookDto.getWordCount())
                                           .wordCount(esBookDto.getWordCount())
                                           .visitCount(esBookDto.getVisitCount())
                                           .lastChapterId(esBookDto.getLastChapterId())
                                           .lastChapterName(esBookDto.getLastChapterName())
                                           .score(esBookDto.getScore())
                                           .build();
        return build;
    }

    //sort字段，
    private SearchResponse<? extends ESBookDto> searchResponse(ReqBookSearchDto bookSearchDto) throws IOException {
        if (bookSearchDto.getPageNum() == null) bookSearchDto.setPageNum(ElasticConsts.INDEX_BOOK_DEFAULT_PAGE_NUM);
        if (bookSearchDto.getPageSize() == null)
            bookSearchDto.setPageSize(ElasticConsts.INDEX_BOOK_DEFALUT_PAGE_SIZE);
        Integer pageFrom = (bookSearchDto.getPageNum() - 1) * bookSearchDto.getPageSize();
        Integer pageSize = bookSearchDto.getPageSize();

        SearchResponse<? extends ESBookDto> search = client.search(s -> {
            s.index(ElasticConsts.INDEX_BOOK_NAME);
            s.query(q -> q.bool(b -> getBuilder(bookSearchDto, b)));
            if (StringUtils.isNotBlank(bookSearchDto.getSort()))
                s.sort(sort -> sort.field(t -> t.field(bookSearchDto.getSort())
                                                .order(SortOrder.Desc)
                                                .unmappedType(FieldType.Long)));
            s.sort(sort -> sort.score(t -> t.order(SortOrder.Asc)));
            s.from(pageFrom)
             .size(pageSize);
            return s;
        }, ESBookDto.class);
        return search;
    }

    private static BoolQuery.Builder getBuilder(ReqBookSearchDto bookSearchDto, BoolQuery.Builder b) {
        if (StringUtils.isNotBlank(bookSearchDto.getKeyword()))
            b.must(r -> r.match(z -> z.field(ElasticConsts.INDEX_BOOK_FIELD_BOOKNAME)
                                      .query(bookSearchDto.getKeyword())));
        if (bookSearchDto.getWordCountMin() != null)
            b.must(r -> r.range(z -> z.field(ElasticConsts.INDEX_BOOK_FIELD_WORDCOUNT)
                                      .gt(JsonData.of(bookSearchDto.getWordCountMin()))));
        if (bookSearchDto.getWordCountMax() != null)
            b.must(r -> r.range(z -> z.field(ElasticConsts.INDEX_BOOK_FIELD_WORDCOUNT)
                                      .lt(JsonData.of(bookSearchDto.getWordCountMax()))));
        if (bookSearchDto.getWorkDirection() != null)
            b.must(r -> r.term(t -> t.field(ElasticConsts.INDEX_BOOK_FIELD_WORKDIRECTION)
                                     .value(v -> v.longValue(bookSearchDto.getWorkDirection()))));
        if (bookSearchDto.getCategoryId() != null)
            b.must(r -> r.term(t -> t.field(ElasticConsts.INDEX_BOOK_FIELD_CATEGORYID)
                                     .value(v -> v.longValue(bookSearchDto.getCategoryId()))));
        if (bookSearchDto.getIsVip() != null) b.must(r -> r.term(t -> t.field(ElasticConsts.INDEX_BOOK_FIELD_ISVIP)
                                                                       .value(v -> v.longValue(bookSearchDto.getIsVip()))));
        if (bookSearchDto.getBookStatus() != null)
            b.must(r -> r.term(t -> t.field(ElasticConsts.INDEX_BOOK_FIELD_BOOKSTATUS)
                                     .value(v -> v.longValue(bookSearchDto.getBookStatus()))));
        if (bookSearchDto.getUpdateTimeMin() != null)
            b.must(r -> r.range(t -> t.field(ElasticConsts.INDEX_BOOK_FIELD_LASTCHAPTERUPDATETIME)
                                      .gt(JsonData.of(bookSearchDto.getUpdateTimeMin()))));
        return b;
    }

    private RestRes<List<ResBookInfoDto>> getBookInfoListById(List<Long> idList) {
        List<BookInfo> bookInfos = bookInfoMapper.selectRankListById(idList);
        List<ResBookInfoDto> resBookInfoDtoList = new ArrayList<>();
        bookInfos.stream()
                 .forEach(t -> {
                     ResBookInfoDto resBookInfoDto = new ResBookInfoDto();
                     BeanUtils.copyProperties(t, resBookInfoDto);
                     resBookInfoDtoList.add(resBookInfoDto);
                 });
        return RestRes.ok(resBookInfoDtoList);
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = SystemConsts.DATASOURCE_LIMIT_TIME, rollbackFor = Exception.class)
    public void syncBookInfo(HashMap<String, Object> map) {
        bookInfoMapper.insertChapterSyncBookInfo(map);
    }

    @Transactional(propagation = Propagation.REQUIRED, timeout = SystemConsts.DATASOURCE_LIMIT_TIME, rollbackFor = Exception.class)
    public void syncBookContent(HashMap<String, Object> map) {
        bookContentMapper.insertOne(map);
    }

    private int syncChapterLastChapterNumToRedis(Long bookId) {
        Integer lastChapterNum = bookChapterMapper.queryLastChapterNum(bookId)
                                                  .get(0);
        RBucket<Integer> bucket = redisson.getBucket(RedisConsts.REDIS_LAST_CHAPTER_NUM);
        bucket.set(lastChapterNum);
        return lastChapterNum;
    }
}
