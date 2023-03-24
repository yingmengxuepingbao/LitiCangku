package com.penghaisoft.framework.log.model.entity;
/**
 * 日志模块查询条件实体
 * @author 秦超
 * 2017-09-01 16:01:48
 */
public class LogSelectCondition {
	//查询起始位置
	private int startNumber;
	//每页数据条数
	private int numberOnePage;
	//用户姓名
	private String userName;
	//用户帐户
	private String userId;
	//查询开始时间
	private String startTime;
	//查询截止时间
	private String endTime;
	//排序方式
	private String sort;
	//排序字段
	private String sortField;
	
	public int getStartNumber() {
		return startNumber;
	}
	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}
	public int getNumberOnePage() {
		return numberOnePage;
	}
	public void setNumberOnePage(int numberOnePage) {
		this.numberOnePage = numberOnePage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAccount() {
		return userId;
	}
	public void setUserAccount(String userId) {
		this.userId = userId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	/**
	 * 构建日志查询条件实体对象
	 * @author 秦超
	 * 2017-09-01 16:04:15
	 */
	public LogSelectCondition(int startNumber, int numberOnePage, String userName, String userId, String startTime, String endTime,String sort,String sortField){
		this.startNumber = startNumber;
		this.numberOnePage = numberOnePage;
		this.userName = userName;
		this.userId = userId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.sort = sort;
		this.sortField = sortField;
	}
}
