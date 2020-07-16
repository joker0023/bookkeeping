package com.jokerstation.bookkeeping.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jokerstation.bookkeeping.interceptor.ConsoleInterceptor;
import com.jokerstation.bookkeeping.pojo.User;
import com.jokerstation.bookkeeping.service.AppService;
import com.jokerstation.bookkeeping.vo.TokenVo;
import com.jokerstation.common.data.ResultModel;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AppService appService;

	@RequestMapping(path="/consoleLogin", method=RequestMethod.POST)
	public ResultModel consoleLogin(String token, HttpServletRequest request) {
//		String openId = appService.getOpenId(token);
//		if (null == openId) {
//			return new ResultModel("-1", "token illegal");
//		}
//		User user = appService.getUserByOpenId(openId);
//		request.getSession().setAttribute(ConsoleInterceptor.CONSOLE_USER_ID, user.getId());
		
		request.getSession().setAttribute(ConsoleInterceptor.CONSOLE_USER_ID, 1L);
		return new ResultModel();
	}
	
	@RequestMapping(path="/appLogin", method=RequestMethod.POST)
	public ResultModel appLogin(String code) throws Exception {
		TokenVo tokenVo = appService.getAppUserByCode(code);
		
		return new ResultModel(tokenVo.getToken());
	}
	
	@RequestMapping("/cleanToken")
	public ResultModel cleanToken() {
		appService.cleanToken();
		return new ResultModel();
	}
}
