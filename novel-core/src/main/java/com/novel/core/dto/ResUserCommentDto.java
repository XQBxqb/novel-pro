package com.novel.core.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
* <p>
* 用户评论
* </p>
*
* @author zk
* @since 2023-10-15
*/
@Data
@Builder
public class ResUserCommentDto {

    private String commentContent;

    private String commentBookPic;

    private String commentBook;

    private LocalDateTime commentTime;
}
