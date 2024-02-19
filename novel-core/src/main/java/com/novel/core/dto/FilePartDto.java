package com.novel.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author 昴星
 * @date 2023-10-23 19:18
 * @explain
 */
@Data
@Builder
public class FilePartDto {
    private List<String> list;
}
