package com.novel.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_own_coupon")
@ApiModel(value="UserOwnCoupon对象", description="")
@AllArgsConstructor
@Builder
public class UserOwnCoupon implements Serializable {

   private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("coupon_id")
    private Long couponId;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("is_used")
    private Integer isUsed;


    public UserOwnCoupon(Long userId, Long couponId, LocalDateTime endTime, Integer isUsed) {
        this.userId = userId;
        this.couponId = couponId;
        this.endTime = endTime;
        this.isUsed = isUsed;
    }
}
