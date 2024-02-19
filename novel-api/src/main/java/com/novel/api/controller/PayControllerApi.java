package com.novel.api.controller;


import com.novel.core.dto.ReqCouponDto;
import com.novel.core.res.RestRes;
import com.novel.core.router.RouterMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RequestMapping(RouterMapping.PAY_API)
@Validated
public interface PayControllerApi {

    @PostMapping("/release/coupon")
    public RestRes<Void> releaseCoupon(@RequestBody ReqCouponDto reqCouponDto);

    @PostMapping("/get/coupon/{couponId}/{userId}")
    public RestRes<Void> getCoupon(@PathVariable("couponId") String couponId,@PathVariable("userId") String userId);

    @PostMapping("/shop/chapter")
    public RestRes<Void> shopChapter(@RequestParam("userId") @NotBlank(message = "用户id不能为空") String userId, @RequestParam("chapterId") @NotBlank(message = "用户购买章节不能为空") String chapterId,
                                     @RequestParam("couponId")  String couponId);

}
