package com.novel.pay.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.BookChapter;
import com.novel.core.entity.BookInfo;
import com.novel.core.entity.BookInfoCombineChapter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

;

/**
 * <p>
 * 小说章节 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2023-09-07
 */

@Mapper
public interface BookChapterMapper extends BaseMapper<BookChapter> {
    List<BookChapter> selectLastestChapter();

    List<BookChapter> getALL();

    BookInfo getBookInfo(String bookId);

    @Select("select SUM(chapter_num) sum from book_chapter where book_id = #{bookId}")
    public Integer queryBookById(@Param("bookId") Long bookId);

    @Select("select book_chapter.id ,book_chapter.book_id,book_chapter.chapter_num,book_chapter.chapter_name,book_chapter.word_count,book_info.last_chapter_update_time,book_info.is_vip " + "from book_chapter,book_info " + "where book_chapter.book_id = book_info.id AND book_info.id = #{bookId} " + "ORDER BY book_chapter.chapter_num DESC  " + "LIMIT 1 ")
    public BookInfoCombineChapter queryLastChapterById(@Param("bookId") Long bookId);

    @Insert("INSERT INTO book_chapter(book_id,chapter_num,chapter_name,word_count,create_time,update_time,is_vip)  " + "VALUES(  " + "#{bookId},  " + "#{chapterNum},  " + "#{chapterName},  " + "#{wordCount},  " + "CURRENT_TIMESTAMP,  " + "CURRENT_TIMESTAMP,  " + "#{isVip} ) ")
    public void insertBookChapter(Map<String, Object> params);

    @Select("SELECT MAX(chapter_num) maxNum " + "FROM book_chapter " + "where book_id = #{bookId} ")
    public List<Integer> queryLastChapterNum(@Param("bookId") Long bookId);

}
