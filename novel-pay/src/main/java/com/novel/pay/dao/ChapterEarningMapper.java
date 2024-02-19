package com.novel.pay.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.ChapterEarning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChapterEarningMapper extends BaseMapper<ChapterEarning> {
    @Select("select author_id , SUM(coins) coins " +
            "from chapter_earning " +
            "where is_refund = 0 " +
            "GROUP BY author_id")
    public List<ChapterEarning> queryEarning();

    @Update("<script>Update chapter_earning " +
            "set is_earn = 1 " +
            "where author_id in " +
            "<foreach item = 'item' collection = 'list' open = '(' separator=',' close = ')' >" +
            "#{item} " +
            "</foreach></script> ")
    public void updateEarning(@Param("list") List<Long> list);
}
