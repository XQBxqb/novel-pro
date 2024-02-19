package com.novel.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

/**
* <p>
*
* </p>
*
* @author  MX 
* @since 2023-11-05
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("chapter_trade_log")
@ApiModel(value="ChapterTradeLog对象", description="")
@Builder
public class ChapterTradeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("chapter_id")
    private Long chapterId;

    @TableField("user_id")
    private Long userId;

    @TableField("exact__coins")
    private Integer exactCoins;

    @TableField("coupon_id")
    private Long couponId;

    @TableField("trade_type")
    private Integer tradeType;

    @TableField("trade_time")
    private LocalDateTime tradeTime;

    @TableField("refund_time")
    private LocalDateTime refundTime;

    @TableField("is_refund")
    private Integer isRefund;


}
