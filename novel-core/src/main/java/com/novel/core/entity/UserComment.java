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
* 用户评论
* </p>
*
* @author zk
* @since 2023-10-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_comment")
@ApiModel(value="UserComment对象", description="用户评论")
public class UserComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "评论用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty(value = "评论小说ID")
    @TableField("book_id")
    private Long bookId;

    @ApiModelProperty(value = "评价内容")
    @TableField("comment_content")
    private String commentContent;

    @ApiModelProperty(value = "回复数量")
    @TableField("reply_count")
    private Integer replyCount;

    @ApiModelProperty(value = "审核状态;0-待审核 1-审核通过 2-审核不通过")
    @TableField("audit_status")
    private Integer auditStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

}
