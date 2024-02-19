package com.novel.core.dto;

import com.novel.core.document.CouponES;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReqCouponDto {
    private Integer limitNums;
    private CouponES.Restrict restrict;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
}
