package com.novel.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
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
@TableName("chapter_earning")
@ApiModel(value="ChapterEarning对象", description="")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChapterEarning implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("chapter_id")
    private Long chapterId;

    @TableField("author_id")
    private Long authorId;

    private Integer coins;
    @TableField("is_earn")
    private Integer isEarn;

    @TableField("trade_time")
    private LocalDateTime tradeTime;

    @TableField("is_refund")
    private Integer isRefund;

    @TableField("refund_time")
    private LocalDateTime refundTime;


}
