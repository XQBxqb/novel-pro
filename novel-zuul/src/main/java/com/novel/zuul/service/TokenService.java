package com.novel.zuul.service;

/**
 * @author 昴星
 * @date 2023-10-07 23:26
 * @explain
 */
public interface TokenService {
    public Boolean isMatchToken(Long id, String token);
}
