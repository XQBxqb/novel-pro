package com.novel.news.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.dto.ResNewsInfo;
import com.novel.core.entity.NewsInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 新闻信息 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2023-10-04
 */
public interface NewsInfoMapper extends BaseMapper<NewsInfo> {
    @Select("SELECT news_content.id,category_id,category_name,source_name,title,news_content.update_time,content\n" + "FROM news_content \n" + "LEFT JOIN news_info ON news_info.id = news_content.news_id\n" + "WHERE news_content.id=#{id} \n" + "LIMIT 1")
    public List<ResNewsInfo> queryNewsById(@Param("id") Long id);

    @Select("SELECT news_content.id,category_id,category_name,source_name,title,news_content.update_time,content\n" + "FROM news_content \n" + "LEFT JOIN news_info ON news_info.id = news_content.news_id")
    public List<ResNewsInfo> queryListNews();
}
