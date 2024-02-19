package com.novel.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 昴星
 * @date 2023-09-07 21:32
 * @explain
 */

@Data
@Builder
@AllArgsConstructor
public class ResBookChapterDto {
    public ResBookChapterDto() {
    }

    private Long id;

    private Long bookId;

    private Integer chapterNum;

    private String chapterName;

    private Integer chapterWordCount;

    private Integer isVip;

    private LocalDateTime chapterUpdateTime;

}
