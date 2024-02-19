package com.novel.pay.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.ChapterTradeLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public  interface ChapterTradeLogMapper extends BaseMapper<ChapterTradeLog> {
    @Insert("insert into chapter_trade_log(chapter_id,user_id,exact_coins,coupon_id,trade_type,trade_time,refund_time,is_refund) " +
            "VALUES(#{chapterId},#{userId},#{exactCoins},#{couponId},#{tradeType},#{tradeTime},#{refundTime},#{isRefund}) ")
    public int insertLog(Map<String,Object> map);

}
