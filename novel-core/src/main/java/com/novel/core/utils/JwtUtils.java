package com.novel.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * JWT 工具类
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@UtilityClass
@Slf4j
public class JwtUtils {

    /**
     * JWT 加密密钥
     */
    private static final String SECRET = "E66559580A1ADF48CDD928516062F12E";

    /**
     * 定义系统标识头常量
     */
    private static final String HEADER_SYSTEM_KEY = "systemKeyHeader";

    private static final long TOKEN_EXIT_TIME=864000000;

    /**
     * 根据用户ID生成JWT
     *
     * @param uid       用户ID
     * @param systemKey 系统标识
     * @return JWT
     */
    public String generateToken(Long uid, String systemKey) {
        String token = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setHeaderParam(HEADER_SYSTEM_KEY, systemKey)
                .setSubject(uid.toString())
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXIT_TIME))
                .compact();
        return token;
    }

    /**
     * 解析JWT返回用户ID
     * @param token     JWT
     * @param systemKey 系统标识
     * @return 用户ID
     */
    public Long parseToken(String token, String systemKey) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);
            // OK, we can trust this JWT
            // 判断该 JWT 是否属于指定系统
            if (Objects.equals(claimsJws.getHeader().get(HEADER_SYSTEM_KEY), systemKey)) {
                return Long.parseLong(claimsJws.getBody().getSubject());
            }
        } catch (JwtException e) {
            log.warn("JWT解析失败:{}", token);
            // don't trust the JWT!
        }
        return null;
    }
}
