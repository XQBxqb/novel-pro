package com.novel.resource.controller;

import com.novel.api.controller.ResourceMngApi;
import com.novel.core.dto.InnerVerifyDto;
import com.novel.core.dto.VerifyImageDto;
import com.novel.core.res.RestRes;
import com.novel.resource.servide.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author 昴星
 * @date 2023-09-03 16:05
 * @explain
 */

@RestController
@RequiredArgsConstructor
public class ResourceMng implements ResourceMngApi {

    private final ResourceService resourceService;

    @Override
    public RestRes<VerifyImageDto> getVerify() {
        return resourceService.getVerifyImageAndCode();
    }

    //url http://localhost:9091/...
    @Override
    public RestRes<Boolean> verifyCode(InnerVerifyDto innerVerifyDto) {
        return resourceService.isRightCode(innerVerifyDto.getCode(),innerVerifyDto.getSessionId());
    }

}
