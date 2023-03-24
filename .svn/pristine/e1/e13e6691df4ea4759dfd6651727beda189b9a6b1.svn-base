package com.penghaisoft.framework.log.model.entity;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.exception.AggregateException;

/**
 * 登录日志实体
 * @author 秦超
 * @time 2017-08-28 10:02:39
 */
public class LoginLog {
	//登录日志id
	private Integer id;
	//登录用户id
	private Integer userId;
	//登录用户账户
	private String userAccount;
	//登录用户姓名
	private String name;
	//登录时间
	private String loginTime;
	//登录ip
	private String loginIp;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime.substring(0,19);
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	public LoginLog(){}
	/**
	 * 构造登录日志对象
	 * @param userId
	 * @param userAccount
	 * @param name
	 * @param loginIp
	 * @param loginTime
	 * @throws AggregateException
	 */
	public LoginLog(int userId, String userAccount, String name, 
			String loginIp, String loginTime) throws AggregateException {
		//异常聚合对象
		AggregateException aggregateException = new AggregateException();
		
		if(userId != 0){
			this.userId = userId;
		}else{
			aggregateException.addException(new Exception(RESULT.LOGMANAGEMENT_LOGIN_LOG_USER_ID_ERROR.message));
		}
		if(userAccount != null && userAccount != ""){
			this.userAccount = userAccount;
		}else{
			aggregateException.addException(new Exception(RESULT.LOGMANAGEMENT_LOGIN_LOG_USER_ACCOUNT_ERROR.message));
		}
		if(name != null && name != ""){
			this.name = name;
		}else{
			aggregateException.addException(new Exception(RESULT.LOGMANAGEMENT_LOGIN_LOG_USER_NAME_ERROR.message));
		}
		if(loginIp != null && loginIp != ""){
			this.loginIp = loginIp;
		}else{
			aggregateException.addException(new Exception(RESULT.LOGMANAGEMENT_LOGIN_LOG_LOGIN_IP_ERROR.message));
		}
		if(loginTime != null && loginTime != ""){
			this.loginTime = loginTime;
		}else{
			aggregateException.addException(new Exception(RESULT.LOGMANAGEMENT_LOGIN_LOG_LOGIN_TIME_ERROR.message));
		}
		
		//给调用者抛出异常信息
		if(aggregateException.count() > 0){
			throw aggregateException;
		}
	}
}

