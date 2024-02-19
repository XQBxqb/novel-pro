package com.novel.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.UserComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户评论 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2023-10-15
 */
@Mapper
public interface UserCommentMapper extends BaseMapper<UserComment> {

}
