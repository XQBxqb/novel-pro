package com.novel.pay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.UserCoin;
import com.novel.core.entity.UserCoinLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserCoinLogMapper extends BaseMapper<UserCoinLog> {

    @Insert(
            "<script> " +
                "insert into user_coin_log (user_id,coins_num,exact_cost,trade_time,is_refund) " +
                    "values " +
                    "<foreach collection='list' item = 'item' index = 'index' separator=','> " +
                    "(  " +
                        "#{item.userId},#{item.coinsNum},#{item.exactCost},#{item.tradeTime},#{item.isRefund} " +
                    ") " +
                    " </foreach>" +
                "" +
            "</script> "
    )
    public void batchInsert(@Param(value = "list")List<UserCoinLog> list);
}
