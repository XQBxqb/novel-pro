package com.novel.api.controller;

import com.novel.core.dto.*;
import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-09-07 21:40
 * @explain
 */

@RequestMapping(RouterMapping.BOOK_FRONT_API)
public interface BookMngApi extends BookSearchApi {
    @PostMapping("/search/books")
    public RestRes<ResBookSearchDto> books(@RequestBody ReqBookSearchDto reqBookSearchDto);

    @PostMapping("/visit/{bookId}")
    public RestRes<Void> visit(@PathVariable Long bookId);

    @PostMapping("/{bookId}")
    public RestRes<ResBookInfoDto> getBookInfo(@PathVariable Long bookId);

    @PostMapping("/visit_rank")
    public RestRes<List<ResBookInfoDto>> visitRank();

    @PostMapping("/update_rank")
    public RestRes<List<ResBookInfoDto>> updateRank();

    @PostMapping("/newest_rank")
    public RestRes<List<ResBookInfoDto>> newestRank();

    @PostMapping("/last_chapter/about/{bookId}")
    public RestRes<ResBookChapterDto> lastChapter(@PathVariable Long bookId);

    @PostMapping("/content/{chapterId}")
    public RestRes<ResContentChapterDto> content(@PathVariable Long chapterId);

    @PostMapping("/chapter/list/{bookId}")
    public RestRes<List<ResBookChapterDto>> chapterList(@PathVariable Long bookId);

    @PostMapping("/category/list/{workDirection}")
    public RestRes<List<ResBookCategoryDto>> categoryList(@PathVariable Integer workDirection);

    @PostMapping("/feign/api/author/book")
    public RestRes<Void> bookRelease(@RequestBody ReqAuthorBookDto bookDto);

    @PostMapping("/feign/api/author/book/chapter")
    public RestRes<Void> chapterRelease(@RequestBody ReqAuthorReleaseChapterDto releaseChapterDto);

    @PostMapping("/feign/api/author/books/{pageNum}/{pageSize}")
    public RestRes<ResAuthorBooksDataDto> books(@PathVariable("pageNum") String pageNum,@PathVariable("pageSize") String pageSize);
    @PostMapping("/feign/api/author/book/chapters/{bookId}/{pageNum}/{pageSize}")
    public RestRes<ResAuthorChaptersDto> chapters(@PathVariable("bookId") String bookId,@PathVariable("pageNum") String pageNum,@PathVariable("pageSize") String pageSize);
}
