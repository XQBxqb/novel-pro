package com.novel.author.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.AuthorInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 作者信息 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2023-10-14
 */
@Mapper
public interface AuthorInfoMapper extends BaseMapper<AuthorInfo> {

}
