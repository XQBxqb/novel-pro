package com.novel.pay.controller;


import com.novel.api.controller.PayControllerApi;
import com.novel.core.dto.ReqCouponDto;
import com.novel.core.entity.*;
import com.novel.core.enums.ErrorStatusEnums;
import com.novel.core.res.RestRes;
import com.novel.pay.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PayController implements PayControllerApi {
    private static volatile AtomicInteger anInt = new AtomicInteger(-1);

    private final PayService payService;
    @Override
    public RestRes<Void> releaseCoupon(ReqCouponDto reqCouponDto) {
        return payService.releaseCouponDocument(reqCouponDto);
    }

    @Override
    public RestRes<Void> getCoupon(String couponId, String userId) {
        if(paramIsBlank(couponId,userId))
            return RestRes.errorEnum(ErrorStatusEnums.RES_ERR_PARAM_IMP_LACK);
        return payService.getCoupon(Long.parseLong(couponId),Long.parseLong(userId));
    }

    @Override
    public RestRes<Void> shopChapter(String userId, String chapterId, String couponId) {
        return payService.shoppingChapterId(Long.parseLong(userId),Long.parseLong(chapterId),Long.parseLong(couponId));
    }

    private Boolean paramIsBlank(String...str){
        for(String st:str)
            if(StringUtils.isBlank(st))
                return true;
        return false;
    }

}
