package com.novel.user.feign;

import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 昴星
 * @date 2023-09-04 18:52
 * @explain
 */


@FeignClient(value = "presource",path = RouterMapping.RESOURCE_GET_VERIFY_IMAGE_API,
        fallback = UserFeigin.class)
public interface UserFeignApi {
    @PostMapping("/verifyCode")
    public RestRes<Boolean> verifyCode(@PathVariable("code") String code, @PathVariable("sessionId") String sessionId);
}
@Component
class UserFeigin implements UserFeignApi {
    @Override
    public RestRes<Boolean> verifyCode(String code, String sessionId) {
        return RestRes.ok(Boolean.FALSE);
    }
}
