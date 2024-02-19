package com.novel.core.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 昴星
 * @date 2023-10-04 18:04
 * @explain
 */
@Data
@AllArgsConstructor
public class ResHomeBookDto   {

    @TableField(value = "type")
    private Integer type;
    @TableField(value = "book_id")
    private Long bookId;
    @TableField(value = "pic_url")
    private String pirUrl;
    @TableField(value = "book_name")
    private String bookName;
    @TableField(value = "author_name")
    private String authorName;
    @TableField(value = "book_desc")
    private String bookDesc;

    public ResHomeBookDto() {
    }
}
