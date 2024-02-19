package com.novel.resource.servide.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.novel.api.exception.BusinessException;
import com.novel.core.consts.RedisConsts;
import com.novel.core.dto.VerifyImageDto;
import com.novel.core.enums.ErrorStatusEnums;
import com.novel.core.res.RestRes;
import com.novel.core.utils.ImgVerifyCodeUtils;
import com.novel.resource.servide.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author 昴星
 * @date 2023-09-03 16:12
 * @explain
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public RestRes<VerifyImageDto> getVerifyImageAndCode() {
        String code = ImgVerifyCodeUtils.getRandomVerifyCode(4);
        String image;
        String sessionId = IdWorker.get32UUID();
        try {
            image = ImgVerifyCodeUtils.genVerifyCodeImg(code);
        } catch (IOException e) {
            throw new BusinessException(ErrorStatusEnums.RES_SYSTEM_ERROR);
        }

        stringRedisTemplate.opsForValue()
                           .set(RedisConsts.REDIS_IMAGE_VERIFY_CODE + ":" + sessionId, code);
        stringRedisTemplate.expire(RedisConsts.REDIS_IMAGE_VERIFY_CODE + ":" + sessionId, 60, TimeUnit.SECONDS);

        return RestRes.ok(VerifyImageDto.builder()
                                        .sessionId(sessionId)
                                        .image(image)
                                        .build());
    }

    @Override
    public RestRes<Boolean> isRightCode(String code, String sessionId) {
        String redisCode = stringRedisTemplate.opsForValue()
                                              .get(RedisConsts.REDIS_IMAGE_VERIFY_CODE + ":" + sessionId);
        return code.equals(redisCode) ? RestRes.ok(true) : RestRes.ok(false);
    }
}
