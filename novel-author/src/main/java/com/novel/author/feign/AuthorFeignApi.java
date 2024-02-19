package com.novel.author.feign;


import com.novel.core.dto.ReqAuthorBookDto;
import com.novel.core.dto.ReqAuthorReleaseChapterDto;
import com.novel.core.dto.ResAuthorBooksDataDto;
import com.novel.core.dto.ResAuthorChaptersDto;
import com.novel.core.enums.ErrorStatusEnums;
import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 昴星
 * @date 2023-09-04 18:52
 * @explain
 */
@FeignClient(value = "book",path = RouterMapping.BOOK_FRONT_API,
        fallback = AuthorFeign.class)
public interface AuthorFeignApi {
    @PostMapping("/feign/api/author/book")
    public RestRes<Void> bookRelease(@RequestBody ReqAuthorBookDto bookDto);

    @PostMapping("/feign/api/author/book/chapter")
    public RestRes<Void> chapterRelease(@RequestBody ReqAuthorReleaseChapterDto releaseChapterDto);

    @PostMapping("/feign/api/author/books/{pageNum}/{pageSize}")
    public RestRes<ResAuthorBooksDataDto> books(@PathVariable("pageNum") String pageNum,
                                                @PathVariable("pageSize") String pageSize);

    @PostMapping("/feign/api/author/book/chapters/{bookId}/{pageNum}/{pageSize}")
    public RestRes<ResAuthorChaptersDto> chapters(@PathVariable("bookId") String bookId,
                                                  @PathVariable("pageNum") String pageNum,
                                                  @PathVariable("pageSize") String pageSize);
}
@Component
class AuthorFeign implements AuthorFeignApi {
    @Override
    public RestRes<Void> bookRelease(ReqAuthorBookDto bookDto) {
        return RestRes.errorEnum(ErrorStatusEnums.RES_BOOK_RELEASE_FAIL);
    }

    @Override
    public RestRes<Void> chapterRelease(ReqAuthorReleaseChapterDto releaseChapterDto) {
        return RestRes.errorEnum(ErrorStatusEnums.RES_BOOK_CHAPTER_RELEASE_FAILE);
    }

    @Override
    public RestRes<ResAuthorBooksDataDto> books(String pageNum, String pageSize) {
        return RestRes.errorEnum(ErrorStatusEnums.RES_BOOKS_GET_FAILED);
    }

    @Override
    public RestRes<ResAuthorChaptersDto> chapters(String bookId, String pageNum, String pageSize) {
        return RestRes.errorEnum(ErrorStatusEnums.RES_BOOKS_CHAPTER_GET_FAILED);
    }
}
