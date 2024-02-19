package com.novel.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.UserFeedback;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户反馈 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2023-10-15
 */
@Mapper
public interface UserFeedbackMapper extends BaseMapper<UserFeedback> {

}
