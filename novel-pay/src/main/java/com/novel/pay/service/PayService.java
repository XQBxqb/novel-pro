package com.novel.pay.service;

import com.novel.core.dto.ReqCouponDto;
import com.novel.core.res.RestRes;

public interface PayService {
    public RestRes<Void> releaseCouponDocument(ReqCouponDto couponDto);

    public RestRes<Void> getCoupon(Long couponId,Long userId);

    public RestRes<Void> shoppingChapterId(Long userId,Long chapterId,Long couponId);
}
