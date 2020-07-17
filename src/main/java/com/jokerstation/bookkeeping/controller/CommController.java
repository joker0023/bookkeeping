package com.jokerstation.bookkeeping.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jokerstation.bookkeeping.interceptor.AdminInterceptor;
import com.jokerstation.bookkeeping.interceptor.ConsoleInterceptor;

@RestController
public class CommController {
	
	@RequestMapping("/hello")
	public String hello() {
		
		return "success";
	}
	
	@RequestMapping("/mockLogin")
	public String mockLogin(HttpServletRequest request) {
		request.getSession().setAttribute(ConsoleInterceptor.CONSOLE_USER_ID, 1L);
		request.getSession().setAttribute(AdminInterceptor.ADMIN_USER_ID, 1L);
		return "success";
	}
}
