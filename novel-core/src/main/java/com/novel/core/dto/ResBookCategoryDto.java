package com.novel.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 昴星
 * @date 2023-10-04 16:32
 * @explain
 */
@Data
@AllArgsConstructor
public class ResBookCategoryDto {
    private Long id;

    private String name;

    public ResBookCategoryDto() {
    }
}
