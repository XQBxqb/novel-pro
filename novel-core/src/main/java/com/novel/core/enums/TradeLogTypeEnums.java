package com.novel.core.enums;

public enum TradeLogTypeEnums {
    ERROR_STOP_TRADE(0,"交易异常中断"),

    SUCCESS_TRADE(1,"交易正常完成");

    public Integer value;
    public String describe;

    TradeLogTypeEnums(Integer value, String describe) {
        this.value = value;
        this.describe = describe;
    }
}
