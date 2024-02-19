package com.novel.core.dto;

import lombok.Data;

/**
 * @author 昴星
 * @date 2023-09-11 14:26
 * @explain
 */
@Data
public class ReqBookSearchDto {
    private String keyword;

    private Integer workDirection;

    private Integer categoryId;

    private Integer isVip;

    private Integer bookStatus;

    private Integer wordCountMin;

    private Integer wordCountMax;

    private String updateTimeMin;

    private String sort;

    private Integer pageNum;

    private Integer pageSize;
}
