package com.novel.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 昴星
 * @date 2023-09-12 20:15
 * @explain
 */
@Builder
@Data
@AllArgsConstructor
public class ResBookInfoDto {

    private Long id;


    private Long categoryId;


    private String categoryName;


    private String picUrl;


    private String bookName;


    private Long authorId;

    private String authorName;
    private String bookDesc;

    private Integer score;

    private Integer bookStatus;

    private Long visitCount;


    private Integer wordCount;


    private Integer commentCount;


    private Long lastChapterId;


    private String lastChapterName;

    private LocalDateTime lastChapterUpdateTime;


    private LocalDateTime updateTime;

    public ResBookInfoDto() {
    }
}
