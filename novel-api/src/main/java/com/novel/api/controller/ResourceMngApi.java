package com.novel.api.controller;

import com.novel.core.dto.InnerVerifyDto;
import com.novel.core.dto.VerifyImageDto;
import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 昴星
 * @date 2023-09-03 15:58
 * @explain
 */
@RequestMapping( value = RouterMapping.RESOURCE_GET_VERIFY_IMAGE_API)
public interface ResourceMngApi {
    @PostMapping("/getVerify")
    public RestRes<VerifyImageDto> getVerify();

    @PostMapping("/verifyCode")
    public RestRes<Boolean> verifyCode(@RequestBody InnerVerifyDto innerVerifyDto);

}
