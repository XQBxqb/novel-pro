package com.novel.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
* <p>
* 小说章节
* </p>
*
* @author zk
* @since 2023-09-07
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("book_chapter")
@ApiModel(value="BookChapter对象", description="小说章节")
public class BookChapter implements Serializable {

private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "小说ID")
    @TableField("book_id")
    private Long bookId;

    @ApiModelProperty(value = "章节号")
    @TableField("chapter_num")
    private Integer chapterNum;

    @ApiModelProperty(value = "章节名")
     @TableField("chapter_name")
    private String chapterName;

    @ApiModelProperty(value = "章节字数")
    @TableField("word_count")
    private Integer wordCount;

    @ApiModelProperty(value = "是否收费;1-收费 0-免费")
    @TableField("is_vip")
    private Integer isVip;

     @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;


}
