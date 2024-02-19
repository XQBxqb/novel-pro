package com.novel.core.dto;

import lombok.Data;

/**
 * @author 昴星
 * @date 2023-10-12 20:19
 * @explain
 */
@Data
public class ReqAuthorUpdateChapterDto {
    private String chapterName;

    private String chapterContent;

    private Integer isVip;
}
