package com.novel.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author 昴星
 * @date 2023-09-12 20:15
 * @explain
 */
@Builder
@Data
@AllArgsConstructor
public class ReqAuthorBookDto {


    private Integer workDirection;

    private Integer categoryId;

    private String categoryName;

    private String picUrl;

    private String bookName;

    private String bookDesc;

    private Integer isVip;

    private Long authorId;

    public ReqAuthorBookDto() {
    }
}
