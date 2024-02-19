package com.novel.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 昴星
 * @date 2023-09-03 16:02
 * @explain
 */
@Getter
@Setter
@Builder
public class VerifyImageDto {

    private String sessionId;

    private String image;
}
