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
@TableName("user_pay_chapter")
@ApiModel(value="UserPayChapter对象", description="")
@Builder
public class UserPayChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("chapter_id")
    private Long chapterId;

    @TableField("exact_coins")
    private Integer exactCoins;

    @TableField("is_read")
    private Integer isRead;

    @TableField("trade_time")
    private LocalDateTime tradeTime;

    @TableField("is_refund")
    private Integer isRefund;

    @TableField("refund_time")
    private LocalDateTime refundTime;


}
