package com.novel.zuul.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 昴星
 * @date 2023-10-06 20:21
 * @explain
 */
public class JwtToken implements AuthenticationToken {

    private String jwt;

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }

    @Override//类似是用户名
    public Object getPrincipal() {
        return jwt;
    }

    @Override//类似密码
    public Object getCredentials() {
        return jwt;
    }
    //返回的都是jwt
}
