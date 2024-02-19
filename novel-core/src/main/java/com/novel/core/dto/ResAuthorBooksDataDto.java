package com.novel.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-14 23:33
 * @explain
 */
@Builder
@Data
public class ResAuthorBooksDataDto {
    private Integer pageNum;

    private Integer pageSize;

    private Long total;

    List<ResAuthorBooks> list;

}
