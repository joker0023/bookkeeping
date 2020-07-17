package com.jokerstation.bookkeeping.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.jokerstation.bookkeeping.pojo.RelBuyer;

import tk.mybatis.mapper.common.Mapper;

public interface RelBuyerMapper extends Mapper<RelBuyer> {

	@Update("update rel_buyer set balance=balance + #{rate} where user_id = #{userId} and shop_id = #{shopId}")
	public void updateBalance(@Param("userId") Long userId, @Param("shopId") Long shopId,@Param("rate") double rate);
}
