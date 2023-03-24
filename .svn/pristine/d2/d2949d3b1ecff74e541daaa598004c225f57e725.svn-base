package com.penghaisoft.framework.interceptor;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.distributedsession.IHttpSession;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.ConvertFactory.Convert;
import com.penghaisoft.framework.util.ConvertFactory.ConvertFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录验证拦截器(若未登录则返回错误)
 * {
 *     "code":"0118",
 *     "message":"用户未登录"
 * }
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor{

	@Autowired
	private IHttpSession httpSession;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 保存登录验证结果,已登录为true,未登录为false
		boolean isLogin = false;
		JSONObject sessionInfo = httpSession.getCurrentSession();
//		log.info("登录拦截:"+(null==sessionInfo?"用户未登录":sessionInfo.toJSONString()));
		// 如果获取到的当前用户为空,则表明未登录,跳转至登录页
		if(sessionInfo != null){
			isLogin = true;
		}else {
			response.setContentType("application/json; charset=utf-8");
			PrintWriter writer = response.getWriter();
			Convert jsonConvert = ConvertFactory.buildJSON();
			ResponseResult result = new ResponseResult(Constant.RESULT.USERMANAGEMENT_CURRENT_NOT_EXIST.code,Constant.RESULT.USERMANAGEMENT_CURRENT_NOT_EXIST.message,null);
			String resStr = jsonConvert.convert(result);
			writer.write(resStr);
			writer.close();
		}
		return isLogin;
	}
}
