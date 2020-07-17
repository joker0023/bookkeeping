package com.jokerstation.bookkeeping.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AdminInterceptor implements HandlerInterceptor {
	
	public final static String ADMIN_USER_ID = "adminUser";
	
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
		Long userId = (Long) request.getSession().getAttribute(ADMIN_USER_ID);
		if (null != userId) {
			return true;
		}
		
//		HttpUtil.setResponse(response, 403, "非法请求");
		response.sendRedirect(request.getContextPath() + "/admin-login.html");
		return false;
	}
	
}
