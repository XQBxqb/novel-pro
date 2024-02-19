package com.novel.core.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
* @author 昴星
* @date 2023-09-11 14:30
* @explain
*/
@Data
@Builder
public class BookSearchDto {

    private Long id;


    private Integer workDirection;


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

    private Integer isVip;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
