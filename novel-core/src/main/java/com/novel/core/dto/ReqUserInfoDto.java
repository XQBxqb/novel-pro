package com.novel.core.dto;

import lombok.Data;

/**
 * @author 昴星
 * @date 2023-10-15 18:53
 * @explain
 */

@Data
public class ReqUserInfoDto {
    private Long userId;

    private String nickName;

    private String userPhoto;

    private Integer userSex;
}
