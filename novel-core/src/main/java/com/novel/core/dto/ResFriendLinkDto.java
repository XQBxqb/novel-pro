package com.novel.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 昴星
 * @date 2023-10-04 17:42
 * @explain
 */
@Data
@AllArgsConstructor
public class ResFriendLinkDto {
    private String linkName;

    private String linkUrl;

    public ResFriendLinkDto() {
    }
}
