package com.novel.book.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.BookCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 小说类别 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2023-10-04
 */
@Mapper
public interface  BookCategoryMapper extends BaseMapper<BookCategory> {

}
