package com.novel.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 昴星
 * @date 2023-10-03 19:49
 * @explain
 */

@Data
@AllArgsConstructor
public class ResContentChapterDto {

    private ResBookInfoDto bookInfoDto;

    private ResBookChapterDto chapterDto;

    public ResContentChapterDto() {
        bookInfoDto = new ResBookInfoDto();
        chapterDto = new ResBookChapterDto();
    }
}
