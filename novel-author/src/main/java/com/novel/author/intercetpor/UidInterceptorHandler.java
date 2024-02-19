package com.novel.author.intercetpor;


import com.novel.core.consts.ServletConsts;
import com.novel.core.thread.ThreadInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 昴星
 * @date 2023-09-07 18:55
 * @explain
 */


@RequiredArgsConstructor
public class UidInterceptorHandler implements HandlerInterceptor {
    ThreadLocal<String> userSession= ThreadInfo.getUserSession();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uid = request.getHeader(ServletConsts.HEADER_UID);
        if(StringUtils.isNotBlank(uid))
            userSession.set(uid);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadInfo.clear();
    }
}
