package com.penghaisoft.framework.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.authorization.constant.SecurityManagerConstant;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.distributedsession.IHttpSession;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.ConvertFactory.Convert;
import com.penghaisoft.framework.util.ConvertFactory.ConvertFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 权限校验拦截器
 * {
 *     "code":"0601",
 *     "message":"对不起，您没有使用该功能的权限"
 * }
 */
@Component
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor{

	@Autowired
	private IHttpSession httpSession;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Value("${spring.application.name}")
	private String sysName;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.1获取相对URL
		String requestUrl = request.getRequestURI();
		//1.2 去掉URL开头的符号'/'
		if(requestUrl.startsWith("/")){
			requestUrl = requestUrl.substring(1, requestUrl.length());
		}
		//1.3获取不带项目名的URL
		String splitURL[] = requestUrl.split("/", 2);
		if(splitURL != null && splitURL.length > 1){
			requestUrl = splitURL[1];
		} else {
			ResponseResult result = new ResponseResult(Constant.RESULT.DATA_FORMAT_ERROR.code,Constant.RESULT.DATA_FORMAT_ERROR.message,null);
			writeMsg(response,result);
			return false;
		}
		boolean authFlag = checkAuth(requestUrl);
		// 如果权限校验失败，返回错误
		if(!authFlag){
			ResponseResult result = new ResponseResult(Constant.RESULT.AUTHORIZATION_ERROR.code,Constant.RESULT.AUTHORIZATION_ERROR.message,null);
			writeMsg(response,result);
		}
		return authFlag;
	}

	/**
	 * 返回给页面数据
	 * @param response
	 * @param result
	 * @throws IOException
	 */
	private void writeMsg(HttpServletResponse response,ResponseResult result) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		PrintWriter writer = response.getWriter();
		Convert jsonConvert = ConvertFactory.buildJSON();
		String resStr = jsonConvert.convert(result);
		writer.write(resStr);
		writer.close();
	}

	/**
	 * 获取资源中类型为按钮的url集合
	 * @param resources
	 * @return
	 */
	private Set<String> getBtnSet(JSONArray resources){
		Set<String> urls = new HashSet<>();
		for (int i = 0; i < resources.size(); i++) {
			JSONObject res = resources.getJSONObject(i);
			String resType = res.getString("resType");
			if (resType.equals("1")){
				urls.add(res.getString("url"));
			}
		}
		return urls;
	}

	/**
	 * 获取所有的按钮请求url
	 * @return
	 */
	private Set<String> getAllBtnUrls(){
//		从缓存中获取整个系统的资源
		String authAllStr = redisTemplate.opsForValue().get(SecurityManagerConstant.AUTHORIZATIONINFO_ALL + ":" + sysName);
		JSONArray allResources = JSONArray.parseArray(authAllStr);
		return getBtnSet(allResources);
	}

	/**
	 * 获取用户拥有的按钮资源
	 * @return
	 */
	private Set<String> getUserBtnUrls(){
		Set<String> resultSet = new HashSet<>();
		String authorizationInfoString = httpSession.getString(SecurityManagerConstant.AUTHORIZATIONINFO);
		JSONObject authJSON = JSON.parseObject(authorizationInfoString);
		JSONArray userResources = authJSON.getJSONArray("1");
		if (null!=userResources){

			resultSet = getBtnSet(userResources);
		}
		return resultSet;
	}

	/**
	 * 检验当前uri是否在用户资源内
	 * @param uri
	 * @return
	 */
	private boolean checkAuth(String uri){
		boolean flag = false;
//		0菜单1按钮2外部链接3报表
//		1 从session中获取系统中的所有资源 res-all
		Set<String> allBtnUrls = getAllBtnUrls();
		if (allBtnUrls.contains(uri)){
//			如果uri 在res-all中
//			1-1 从session中获取用户的资源 res-user
			Set<String> userBtnUrls = getUserBtnUrls();
			if (userBtnUrls.contains(uri)){
//				如果uri也在res-user中
//					放行
				flag = true;
			}else {
//				如果不在
//					返回权限校验失败
				flag = false;
			}

		}else {
//			如果不在res-all中
//			1-2 放行
			flag = true;
		}
//		log.info("权限校验:"+uri + "  =" +flag);
		return flag;
	}
}
