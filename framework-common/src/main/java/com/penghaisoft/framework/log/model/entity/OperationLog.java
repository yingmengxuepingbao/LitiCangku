package com.penghaisoft.framework.log.model.entity;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.exception.AggregateException;

/**
 * 操作日志实体
 * @author 秦超
 * 2017-08-28 10:04:58
 */
public class OperationLog {
	//操作日志id
	private Integer id;
	//操作用户id
	private Integer userId;
	//用户帐户
	private String userAccount;
	//操作用户姓名
	private String name;
	//操作菜单名
	private String operationName;
	//操作时间
	private String operationTime;
	
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
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime.substring(0,19);
	}
	
	public OperationLog(){}
	//构造操作日志对象
	public OperationLog(int userId,	String operationName, String operationTime) throws AggregateException {
		//异常聚合对象
		AggregateException aggregateException = new AggregateException();
		
		if(userId != 0){
			this.userId = userId;
		}else{
			aggregateException.addException(new Exception(RESULT.LOGMANAGEMENT_OPERATION_LOG_USER_ID_ERROR.message));
		}
		if(operationName != null && operationName != ""){
			this.operationName = operationName;
		}else{
			aggregateException.addException(new Exception(RESULT.LOGMANAGEMENT_OPERATION_LOG_OPERATION_NAME_ERROR.message));
		}
		if(operationTime != null && operationTime != ""){
			this.operationTime = operationTime;
		}else{
			aggregateException.addException(new Exception(RESULT.LOGMANAGEMENT_OPERATION_LOG_OPERATION_TIME_ERROR.message));
		}
		//给调用抛出异常信息
		if(aggregateException.count() > 0){
			throw aggregateException;
		}
	}
}

