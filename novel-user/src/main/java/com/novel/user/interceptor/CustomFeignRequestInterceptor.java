package com.novel.user.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author 昴星
 * @date 2023-09-05 17:47
 * @explain 设置openFeigin的请求参数为body,防止url过大,同时不用自定义dto减少程序开发
 */

@Component
public class CustomFeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if(requestTemplate.method().equals("post")&&requestTemplate.body()==null){
            String query = requestTemplate.queryLine();
            requestTemplate.queries(new HashMap<>());
            if(StringUtils.isNoneBlank(query)&&query.charAt(0)=='?'){
                requestTemplate.body(query.substring(1));
            }
            requestTemplate.header("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        }
    }
}
