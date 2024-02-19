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
    * 作者信息
    * </p>
*
* @author zk
* @since 2023-10-14
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("author_info")
    @ApiModel(value="AuthorInfo对象", description="作者信息")
    public class AuthorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

            @ApiModelProperty(value = "主键")
            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "用户ID")
        @TableField("user_id")
    private Long userId;

            @ApiModelProperty(value = "邀请码")
        @TableField("invite_code")
    private String inviteCode;

            @ApiModelProperty(value = "笔名")
        @TableField("pen_name")
    private String penName;

            @ApiModelProperty(value = "手机号码")
        @TableField("tel_phone")
    private String telPhone;

            @ApiModelProperty(value = "QQ或微信账号")
        @TableField("chat_account")
    private String chatAccount;

            @ApiModelProperty(value = "电子邮箱")
    private String email;

            @ApiModelProperty(value = "作品方向;0-男频 1-女频")
        @TableField("work_direction")
    private Integer workDirection;

            @ApiModelProperty(value = "0：正常;1-封禁")
    private Integer status;

            @ApiModelProperty(value = "创建时间")
        @TableField("create_time")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "更新时间")
        @TableField("update_time")
    private LocalDateTime updateTime;


}
