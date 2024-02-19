package com.novel.book.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.BookInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 小说信息 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2023-09-02
 */

@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {
    List<BookInfo> getAll();

    @Update("update book_info " +
            "set visit_count = visit_count + 1 " +
            "where id = #{bookId} ")
    public void updateBookVisitCount(@Param("bookId") Long bookId);
    @Select("<script> select id,category_id,category_name,pic_url,book_name, " +
            "author_id,author_name,book_desc,score,book_status, " +
            "visit_count,word_count,comment_count,last_chapter_id, " +
            "last_chapter_name,last_chapter_update_time,update_time " +
            "from book_info " +
            "where id IN " +
            "<foreach item = 'item' collection = 'idList' open = '(' " +
            "separator=',' close=')'> " +
            "#{item} " +
            "</foreach> </script>")
    public List<BookInfo> selectRankListById(@Param("idList") List<Long> idList);
    @Update("UPDATE book_info   " +
            "SET word_count=word_count + #{wordCount},last_chapter_id = #{chapterId},last_chapter_name = #{chapterName},   " +
            "last_chapter_update_time = CURRENT_TIMESTAMP " +
            "WHERE id = #{bookId} ")
    public void insertChapterSyncBookInfo(Map<String,Object> map);

    @Select("select id    " +
            "from book_chapter   " +
            "where book_id = #{bookId}   " +
            "ORDER BY chapter_num DESC   " +
            "LIMIT 1  ")
    public List<Object> queryBookChapterId(@Param("bookId") Long bookId);

}
