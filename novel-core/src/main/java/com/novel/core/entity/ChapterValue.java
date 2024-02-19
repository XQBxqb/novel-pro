package com.novel.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chapter_value")
@ApiModel(value="ChapterValue对象", description="")
@Builder
public class ChapterValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("chapter_id")
    private Long chapterId;

    @TableField("coins_value")
    private Integer coinsValue;

    @TableField("is_free")
    private Integer isFree;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("is_allowed_coupon")
    private Integer isAllowedCoupon;


}
