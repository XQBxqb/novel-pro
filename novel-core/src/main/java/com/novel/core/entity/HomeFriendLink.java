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
    * 友情链接
    * </p>
*
* @author zk
* @since 2023-10-04
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("home_friend_link")
    @ApiModel(value="HomeFriendLink对象", description="友情链接")
    public class HomeFriendLink implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty(value = "链接名")
        @TableField("link_name")
    private String linkName;

            @ApiModelProperty(value = "链接url")
        @TableField("link_url")
    private String linkUrl;

            @ApiModelProperty(value = "排序号")
    private Integer sort;

            @ApiModelProperty(value = "是否开启;0-不开启 1-开启")
        @TableField("is_open")
    private Integer isOpen;

            @ApiModelProperty(value = "创建时间")
        @TableField("create_time")
    private LocalDateTime createTime;

            @ApiModelProperty(value = "更新时间")
        @TableField("update_time")
    private LocalDateTime updateTime;


}
