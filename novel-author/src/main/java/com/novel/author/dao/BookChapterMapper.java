package com.novel.author.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.dto.ResAuthorChapterDto;
import com.novel.core.entity.BookChapter;
import org.apache.ibatis.annotations.*;

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
    @Select("select SUM(chapter_num) sum from book_chapter where book_id = #{bookId}"
    +"group by book_id")
    public Integer queryBookById(Long bookId);
    @Select("select chapter_name ,content as chapterContent,is_vip\n" +
            "from book_chapter LEFT JOIN book_content\n" +
            "on book_chapter.id =book_content.chapter_id\n" +
            "where chapter_id = #{chapterId}")
    public ResAuthorChapterDto queryChapterById(Long chapterId);
    @Update("Update book_chapter,book_content \n" +
            "SET content = #{chapterContent}, book_chapter.chapter_name = #{chapterName}\n" +
            "where book_chapter.id = book_content.chapter_id \n" +
            "AND book_chapter.id = #{chapterId};")
    public void updateChapterById(@Param("chapterId") Long chapterId, @Param("chapterName") String chapterName,
                                  @Param("chapterContent") String chapterContent);

    @Delete("DELETE FROM book_chapter \n" +
            "WHERE  book_chapter.id = #{chapterId}\n")
    public void deleteChapterById(@Param("chapterId") Long chapterId);

    @Update("UPDATE book_info\n" +
            "JOIN (\n" +
            "    SELECT id, book_id ,update_time, chapter_name, word_count\n" +
            "    FROM book_chapter\n" +
            "    WHERE book_chapter.book_id = #{bookId}\n" +
            "    ORDER BY book_chapter.update_time DESC\n" +
            "    LIMIT 1\n" +
            ") AS tmp ON book_info.id = tmp.book_id\n" +
            "SET \n" +
            "    book_info.last_chapter_id = tmp.id,\n" +
            "    book_info.last_chapter_name = tmp.chapter_name,\n" +
            "    book_info.word_count = book_info.word_count - tmp.word_count,\n" +
            "    book_info.update_time = CURRENT_TIMESTAMP\n" +
            "WHERE book_info.id = #{bookId};\n")
    public void updateBookInfoAfterDeleteChapter(@Param("bookId") Long bookId);
}
