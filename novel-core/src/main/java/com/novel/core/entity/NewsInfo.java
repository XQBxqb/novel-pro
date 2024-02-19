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
    * 新闻信息
    * </p>
*
* @author zk
* @since 2023-10-04
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("news_info")
@ApiModel(value="NewsInfo对象", description="新闻信息")
public class NewsInfo implements Serializable {

private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "类别ID")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty(value = "类别名")
    @TableField("category_name")
    private String categoryName;

    @ApiModelProperty(value = "新闻来源")
    @TableField("source_name")
    private String sourceName;

    @ApiModelProperty(value = "新闻标题")
    private String title;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
