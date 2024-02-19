package com.novel.core.dto;

import lombok.Builder;

/**
 * @author 昴星
 * @date 2023-09-04 17:59
 * @explain
 */


@Builder
public class ResRegisterDto {
    private Long uid;

    private String token;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
