package com.novel.core.dto;

import lombok.Data;

/**
 * @author 昴星
 * @date 2023-10-12 20:25
 * @explain
 */
@Data
public class ReqAuthorRegisterDto {
    private String penName;

    private String telPhone;

    private String chatAccount;

    private String email;

    private Integer workDirection;
}
