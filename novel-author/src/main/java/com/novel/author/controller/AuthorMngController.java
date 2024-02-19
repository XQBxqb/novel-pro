package com.novel.author.controller;


import com.novel.api.controller.AuthorMngApi;
import com.novel.author.feign.AuthorFeignApi;
import com.novel.author.service.AuthorService;
import com.novel.core.dto.*;
import com.novel.core.res.RestRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 昴星
 * @date 2023-10-12 20:16
 * @explain
 */
@RestController
@RequiredArgsConstructor
public class AuthorMngController implements AuthorMngApi {
    private final AuthorService authorService;

    private final AuthorFeignApi authorFeign;

    @Override
    public RestRes<ResAuthorChapterDto> chapter(String chapterId) {
        return authorService.getChapterById(Long.parseLong(chapterId));
    }

    @Override
    public RestRes<Void> updateChapter(String chapterId, ReqAuthorUpdateChapterDto chapterDto) {
        return authorService.updateChapter(Long.parseLong(chapterId),chapterDto);
    }

    @Override
    public RestRes deleteChapter(String chapterId) {
        authorService.deleteChapter(Long.parseLong(chapterId));
        return null;
    }

    @Override
    public RestRes<Void> register(ReqAuthorRegisterDto registerDto) {
        return null;
    }

    @Override
    public RestRes<Void> book(ReqAuthorBookDto bookDto) {
        return authorService.bookRelease(bookDto);
    }

    @Override
    public RestRes<Void> bookChapter(String bookId, ReqAuthorReleaseChapterDto chapterDto) {
        return authorService.bookChapterRelease(chapterDto);
    }

    @Override
    public RestRes<Integer> status() {
        return null;
    }

    @Override
    public RestRes<ResAuthorBooksDataDto> books(String pageNum, String pageSize) {
        return authorFeign.books(pageNum,pageSize);
    }

    @Override
    public RestRes<ResAuthorChaptersDto> chapters(String bookId, String pageNum, String pageSize) {
        return authorFeign.chapters(bookId,pageNum,pageSize);
    }
}
