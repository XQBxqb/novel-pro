package com.novel.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author 昴星
 * @date 2023-10-15 0:03
 * @explain
 */
@Data
@Builder
public class ResAuthorChaptersDto {
    private Integer pageNum;

    private Integer pageSize;

    private Long total;

    private List<ResBookChapterDto> list;
}
