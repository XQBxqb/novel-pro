package com.novel.core.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookElasticES implements Serializable {

    private final long serialVersionUID = 1L;

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
    @JsonFormat( shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss")

    private LocalDateTime lastChapterUpdateTime;


    private Integer isVip;

}