package com.novel.core.dto;

import lombok.Data;

/**
 * @author 昴星
 * @date 2023-09-11 17:29
 * @explain
 */
@Data
public class ESBookDto {
    private Integer authorId;

    private String authorName;

    private String bookDesc;

    private String bookName;

    private String bookStatus;

    private  Integer categoryId;

    private String categoryName;

    private Long  commentCount;
    private Long id;
    private Long isVip;
    private Long lastChapterId;
    private String lastChapterName;
    private String lastChapterUpdateTime;
    private String picUrl;
    private Integer score;
    private Long visitCount;
    private Integer wordCount;
    private Integer workDirection;
}
