package com.novel.home.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.dto.ResHomeBookDto;
import com.novel.core.entity.HomeBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 小说推荐 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2023-10-04
 */
@Mapper
public interface HomeBookMapper extends BaseMapper<HomeBook> {
    @Select("SELECT type,book_id,pic_url,book_name,author_name,book_desc\n" + "FROM home_book h\n" + "LEFT JOIN book_info b ON h.book_id = b.id")
    public List<ResHomeBookDto> queryHomeBook();
}
