package com.novel.core.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author 昴星
 * @date 2023-09-07 19:34
 * @explain
 */

@Data
@Builder
public class ResLoginDto {
    private Long uid;

    private String token;

    private String nickName;
}
