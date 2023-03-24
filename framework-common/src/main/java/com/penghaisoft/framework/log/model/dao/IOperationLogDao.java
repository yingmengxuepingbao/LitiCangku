package com.penghaisoft.framework.log.model.dao;

import com.penghaisoft.framework.log.model.entity.LogSelectCondition;
import com.penghaisoft.framework.log.model.entity.OperationLog;
import java.util.List;

/**
 * 操作日志数据查询接口
 * @author Q.chao
 * 2017-09-01 15:59:27
 */
public interface IOperationLogDao {
	/**
	 * 获取操作日志列表数据接口
	 * @param logSelectCondition currentPage 当前页码 startTime 查询开始时间 endTime 查询截止时间 userName 查询用户名
	 * @return 操作日志列表信息
	 * 2017-08-28 10:19:39
	 */
	List<OperationLog> getOperationLogList(LogSelectCondition logSelectCondition);
	/**
	 * 获取操作日志条数接口
	 * @param logSelectCondition currentPage 当前页码 startTime 查询开始时间 endTime 查询截止时间 userName 查询用户名
	 * @return 操作日志列表信息
	 * 2017-08-28 10:19:39
	 */
	int getOperationLogTotalCount(LogSelectCondition logSelectCondition);
	/**
	 * 新增登录日志记录数据接口
	 * @param operationLog 操作日志实体对象
	 * @author 秦超
	 * 2017-08-28 10:28:32
	 */
	boolean insert(OperationLog operationLog);
}
