package com.novel.api.controller;

import com.novel.core.dto.*;
import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import org.springframework.web.bind.annotation.*;


/**
 * @author 昴星
 * @date 2023-10-12 20:16
 * @explain
 */
@RequestMapping(RouterMapping.AUTHOR_API)
public interface AuthorMngApi {
    @GetMapping("/book/chapter/{chapterId}")
    public RestRes<ResAuthorChapterDto> chapter(@PathVariable("chapterId") String chapterId);

    @PutMapping("/book/chapter/{chapterId}")
    public RestRes<Void> updateChapter(@PathVariable("chapterId") String chapterId,@RequestBody ReqAuthorUpdateChapterDto chapterDto);

    @DeleteMapping("/book/chapter/{chapterId}")
    public RestRes deleteChapter(@PathVariable("chapterId") String chapterId);

    @PostMapping("/register")
    public RestRes<Void> register(@RequestBody ReqAuthorRegisterDto registerDto);

    @PostMapping("/book")
    public RestRes<Void> book(@RequestBody ReqAuthorBookDto bookDto);

    @PostMapping("/book/chapter/{bookId}")
    public RestRes<Void> bookChapter(@PathVariable("bookId") String bookId, @RequestBody ReqAuthorReleaseChapterDto chapterDto);

    @GetMapping("/status")
    public RestRes<Integer> status();

    @PostMapping("/books/{pageNum}/{pageSize}")
    public RestRes<ResAuthorBooksDataDto> books(@PathVariable("pageNum") String pageNum,@PathVariable("pageSize") String pageSize);

    @PostMapping("/book/chapters/{bookId}/{pageNum}/{pageSize}")
    public RestRes<ResAuthorChaptersDto> chapters(@PathVariable("bookId") String bookId,
                                                          @PathVariable("pageNum") String pageNum,
                                                          @PathVariable("pageSize") String pageSize);
}
