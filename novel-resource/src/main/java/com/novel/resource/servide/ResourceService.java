package com.novel.resource.servide;


import com.novel.core.dto.VerifyImageDto;
import com.novel.core.res.RestRes;

/**
 * @author 昴星
 * @date 2023-09-03 16:10
 * @explain
 */
public interface ResourceService {
    RestRes<VerifyImageDto> getVerifyImageAndCode();

    RestRes<Boolean> isRightCode(String code, String sessionId);
}
