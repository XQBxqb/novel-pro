package com.novel.core.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
* <p>
    * 新闻信息
    * </p>
*
* @author zk
* @since 2023-10-04
*/
@Data
@AllArgsConstructor
public class ResNewsInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @TableField("category_id")
    private Long categoryId;


    @TableField("category_name")
    private String categoryName;


    @TableField("source_name")
    private String sourceName;

    @TableField("title")
    private String title;



    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("content")
    private String content;

    public ResNewsInfo() {
    }
}
