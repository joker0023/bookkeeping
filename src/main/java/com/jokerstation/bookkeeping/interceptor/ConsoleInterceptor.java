package com.jokerstation.bookkeeping.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jokerstation.bookkeeping.pojo.User;
import com.jokerstation.common.data.ResultModel;
import com.jokerstation.common.util.JsonUtils;

public class ConsoleInterceptor implements HandlerInterceptor {
	
	public final static String CONSOLE_USER = "consoleUser";

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
		User consoleUser = getConsoleUser(request);
		if (null == consoleUser) {
			setResponse(response, 403, "非法访问");
			return false;
		}
		
		return true;
	}
	
	private void setResponse(HttpServletResponse response, int status, String msg) throws Exception {
		response.setStatus(status);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		ResultModel model = new ResultModel(status + "", msg);
		response.getWriter().print(JsonUtils.toJson(model));
	}
	
	public static User getConsoleUser(HttpServletRequest request) {
		return (User)request.getSession().getAttribute(CONSOLE_USER);
	}
}