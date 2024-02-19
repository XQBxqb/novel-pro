package com.novel.author.service;


import com.novel.core.dto.ReqAuthorBookDto;
import com.novel.core.dto.ReqAuthorReleaseChapterDto;
import com.novel.core.dto.ReqAuthorUpdateChapterDto;
import com.novel.core.dto.ResAuthorChapterDto;
import com.novel.core.res.RestRes;

/**
 * @author 昴星
 * @date 2023-10-12 20:45
 * @explain
 */
public interface AuthorService {
    public RestRes<ResAuthorChapterDto> getChapterById(Long chapterId);

    public RestRes<Void> updateChapter(Long chapterId, ReqAuthorUpdateChapterDto chapterDto);

    public RestRes deleteChapter(Long chapterId);

    public RestRes<Void> bookRelease(ReqAuthorBookDto bookDto);

    public RestRes<Void> bookChapterRelease(ReqAuthorReleaseChapterDto chapterDto);

    public RestRes<Integer> getAuthorStatus();
}
