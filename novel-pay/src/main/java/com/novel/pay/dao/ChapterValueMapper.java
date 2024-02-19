package com.novel.pay.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.ChapterValue;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChapterValueMapper extends BaseMapper<ChapterValue> {

    @Insert("<script> " +
            "insert into chapter_value values " +
            "<foreach collection='list' item='item' index='index' separator=','> " +
            "(#{item.chapterId},#{item.coinsValue},#{item.isFree},#{item.updateTime},#{item.isAllowedCoupon}) " +
            "</foreach> " +
            "</script> ")
    public void batchInsert(@Param(value = "list") List<ChapterValue> list);
}
