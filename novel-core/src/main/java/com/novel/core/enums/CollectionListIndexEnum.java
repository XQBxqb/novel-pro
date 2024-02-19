package com.novel.core.enums;

public enum CollectionListIndexEnum {
    FIRST(0,"集合第一个元素");

    public Integer index;

    public String describe;

    CollectionListIndexEnum(Integer index, String describe) {
        this.index = index;
        this.describe = describe;
    }
}
