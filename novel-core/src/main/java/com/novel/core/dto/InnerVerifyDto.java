package com.novel.core.dto;

import lombok.Data;

/**
 * @author 昴星
 * @date 2023-09-07 18:22
 * @explain
 */

@Data
public class InnerVerifyDto {

    private String code;

    private String sessionId;
}
