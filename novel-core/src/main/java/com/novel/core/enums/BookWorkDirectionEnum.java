package com.novel.core.enums;

/**
 * @author 昴星
 * @date 2023-09-11 14:55
 * @explain
 */
public enum BookWorkDirectionEnum {

    MAN_DIRECT(1,"男频"),
    WOMAN_DIRECT(2,"女频");

    public Integer type;

    public String workDirection;

    BookWorkDirectionEnum(Integer type, String workDirection) {
        this.type = type;
        this.workDirection = workDirection;
    }
}
