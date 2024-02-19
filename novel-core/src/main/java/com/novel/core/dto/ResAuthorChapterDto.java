package com.novel.core.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 昴星
 * @date 2023-10-12 20:19
 * @explain
 */
@Data
public class ResAuthorChapterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("chapter_name")
    private String chapterName;
    @TableField("content")
    private String chapterContent;
    @TableField("is_vip")
    private Integer isVip;
}
