package com.novel.core.dto;

import lombok.Data;

/**
 * @author 昴星
 * @date 2023-10-15 19:34
 * @explain
 */
@Data
public class ReqUserCommentDto {
    private Integer pageNum;

    private Integer pageSize;

    private Boolean fetchAll;
}
