package com.penghaisoft.pda.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class CrosInterceptor implements HandlerInterceptor{


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.info("允许跨域,拦截请求:"+request.getRequestURI());
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization");
		return true;
	}

//	/**
//	 * 允许跨域
//	 * @param request
//	 * @param response
//	 * @param handler
//	 * @param modelAndView
//	 */
//	@Override
//	public void preHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//		log.info("允许跨域");
//		response.setHeader("Access-Control-Allow-Origin","*");
//	}
}
