package com.novel.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-09-11 14:34
 * @explain
 */

@Data
@Builder
public class ResBookSearchDto {

    private Integer pageNum;

    private Integer pageSize;

    private Integer total;

    private Integer pages;

    private List<BookSearchDto> list;
}
