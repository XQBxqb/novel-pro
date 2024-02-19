package com.novel.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.BookInfo;
import org.apache.ibatis.annotations.Mapper;


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

}
