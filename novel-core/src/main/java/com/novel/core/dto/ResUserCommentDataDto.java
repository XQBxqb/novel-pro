package com.novel.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-15 19:39
 * @explain
 */
@Data
@Builder
public class ResUserCommentDataDto {
    private Integer pageNum;

    private Integer pageSize;

    private Integer total;

    List<ResUserCommentDto> list;

    public ResUserCommentDataDto(Integer total) {
        this.total = total;
    }

    public ResUserCommentDataDto(Integer pageNum, Integer pageSize, Integer total, List<ResUserCommentDto> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }
}
