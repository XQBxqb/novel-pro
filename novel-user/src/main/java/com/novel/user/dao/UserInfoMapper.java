package com.novel.user.dao;


import com.novel.core.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 * @author zk
 * @since 2023-09-02
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
