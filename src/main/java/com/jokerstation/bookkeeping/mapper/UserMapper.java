package com.jokerstation.bookkeeping.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.jokerstation.bookkeeping.pojo.User;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

	@Update("update user set balance=balance + #{rate} where id = #{userId}")
	public void updateBalance(@Param("userId") Long userId, @Param("rate") double rate);
}
