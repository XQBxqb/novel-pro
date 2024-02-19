package com.novel.core.dto;

import lombok.Data;

/**
 * @author 昴星
 * @date 2023-09-07 19:35
 * @explain
 */

@Data
public class ReqLoginDto {
    private String username;

    private String password;
}
