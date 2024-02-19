package com.novel.pay.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.novel.core.entity.UserOwnCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserOwnCouponMapper extends BaseMapper<UserOwnCoupon> {

    @Select("select user_id,coupon_id,end_time,is_used " +
            "from user_own_coupon " +
            "where user_id = #{userId} AND coupon_id = #{couponId} ")
    public List<UserOwnCoupon> getOwnCoupon(@Param("userId") Long userId,@Param("couponId") Long couponId);
    @Update("update user_own_coupon " +
            "set is_used = 1 " +
            "where user_id = #{userId} AND coupon_id = #{couponId}")
    public int updateOwnCoupon(@Param("userId") Long userId,@Param("couponId") Long couponId);
}
