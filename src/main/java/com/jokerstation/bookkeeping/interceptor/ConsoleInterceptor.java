package com.jokerstation.bookkeeping.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jokerstation.common.util.HttpUtil;

public class ConsoleInterceptor implements HandlerInterceptor {

	public final static String CONSOLE_USER_ID = "consoleUser";

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object paramObject,
			Exception paramException) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object paramObject,
			ModelAndView paramModelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object paramObject)
			throws Exception {
		Long userId = getConsoleUserId(request);
		if (null != userId) {
			return true;
		}
		
		if (HttpUtil.isAjaxRequest(request)) {
			HttpUtil.setResponse(response, 403, "登陆已过期");
		} else {
			response.sendRedirect(request.getContextPath() + "/login.html");
		}
		return false;
	}


	public static Long getConsoleUserId(HttpServletRequest request) {
		return (Long) request.getSession().getAttribute(CONSOLE_USER_ID);
	}
}
