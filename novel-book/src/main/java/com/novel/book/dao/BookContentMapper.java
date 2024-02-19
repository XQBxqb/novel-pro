package com.novel.book.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.BookContent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * 小说内容 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2023-10-13
 */
@Mapper
public interface BookContentMapper extends BaseMapper<BookContent> {
    @Insert("INSERT INTO book_content(chapter_id,content,create_time,update_time) " +
         "VALUES(#{chapterId},#{content},CURRENT_TIMESTAMP,CURRENT_TIMESTAMP) ")
    public void insertOne(Map map);
}
