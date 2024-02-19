package com.novel.user.interceptor;



import com.novel.core.consts.SystemConsts;
import com.novel.core.thread.ThreadInfo;
import com.novel.core.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 昴星
 * @date 2023-09-07 18:55
 * @explain
 */


@RequiredArgsConstructor
@Component
public class TokenInterceptorHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader(SystemConsts.JWT_TOKEN_HEADER);
        if(StringUtils.isBlank(header)) return true;
        Long id = JwtUtils.parseToken(header, SystemConsts.JWT_TOKEN_KEY);
        ThreadInfo.getUserSession().set(String.valueOf(id));
        return true;
    }
}
