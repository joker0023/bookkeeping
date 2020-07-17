package com.jokerstation.bookkeeping.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jokerstation.bookkeeping.service.AppService;
import com.jokerstation.common.util.HttpUtil;

public class AppInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object paramObject,
			Exception paramException) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object paramObject,
			ModelAndView paramModelAndView) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object paramObject) throws Exception {
		String token = request.getHeader("token");
		if (null == token) {
			HttpUtil.setResponse(response, 403, "非法访问");
			return false;
		}
		String openId = AppService.getOpenId(token);
		if (null == openId) {
			HttpUtil.setResponse(response, 403, "token已失效");
			return false;
		}
		
		return true;
	}
}
