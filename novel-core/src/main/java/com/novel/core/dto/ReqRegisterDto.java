package com.novel.core.dto;

/**
 * @author 昴星
 * @date 2023-09-04 17:56
 * @explain
 */

public class ReqRegisterDto {
    private String username;

    private String password;

    private String sessionId;

    private String velCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getVelCode() {
        return velCode;
    }

    public void setVelCode(String velCode) {
        this.velCode = velCode;
    }

    public String getImgVerifyCode() {
        return imgVerifyCode;
    }

    public void setImgVerifyCode(String imgVerifyCode) {
        this.imgVerifyCode = imgVerifyCode;
    }

    private String imgVerifyCode;

}
