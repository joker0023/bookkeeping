package com.jokerstation.bookkeeping.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jokerstation.bookkeeping.interceptor.ConsoleInterceptor;
import com.jokerstation.bookkeeping.mapper.UserMapper;
import com.jokerstation.bookkeeping.pojo.User;
import com.jokerstation.common.data.ResultModel;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserMapper userMapper;

	@RequestMapping("/consoleLogin")
	public ResultModel login(HttpServletRequest request, HttpServletResponse response) {
		User record = new User();
		record.setNick("joker");
		List<User> userList = userMapper.select(record);
		request.getSession().setAttribute(ConsoleInterceptor.CONSOLE_USER, userList.get(0));
		return new ResultModel();
	}
}
