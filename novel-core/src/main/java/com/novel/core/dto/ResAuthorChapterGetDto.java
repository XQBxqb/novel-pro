package com.novel.core.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 昴星
 * @date 2023-10-12 20:19
 * @explain
 */
@Data
public class ResAuthorChapterGetDto {
    private Integer id;

    private Integer bookId;

    private String chapterName;

    private String chapterContent;

    private Integer chapterWordCount;

    private LocalDateTime chapterUpdateTime;

    private Integer isVip;
}
