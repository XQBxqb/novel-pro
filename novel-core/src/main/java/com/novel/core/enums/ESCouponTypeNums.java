package com.novel.core.enums;

public enum ESCouponTypeNums {
    DIRECT_REDUCE(0,"直减"),

    DISCOUNT_PERCENT(1,"折扣");

    public Integer value;

    public String describe;

    ESCouponTypeNums(Integer value, String describe) {
        this.value = value;
        this.describe = describe;
    }
}
