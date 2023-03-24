package com.penghaisoft.framework.log.model.business;

import com.penghaisoft.framework.exception.AggregateException;

import java.util.Map;

/**
 * 操作日志业务类接口
 * @author 秦超
 * 2017-09-01 15:58:23
 */
public interface IOperationLogBusiness {
	/**
	 * 新增登录日志记录接口
	 * @param operationLog 操作日志实体对象
	 * @author 秦超
	 * 2017-08-28 10:28:32
	 * @return 
	 * @throws AggregateException
	 */
	boolean addOperationLog(int userId, String operationName, String operationType);
	/**
	 * 获取错误码
	 * @return
	 * @author 秦超
	 * 2017-09-01 15:23:56
	 */
	String getLastErrorCode();
	/**
	 * 获取错误信息
	 * @return
	 * @author 秦超
	 * 2017-09-01 15:24:04
	 */
	String getLastErrorMessage();

	/**
	 * @Author 张旭
	 * @Description  获取操作日志列表接口
	 * @Date 14:19 2018/9/26
	 * @Param [pageNo, pageSize, sort, orderField, startTime, endTime, userName, userId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
    Map<String, Object> getOperationLogList(int pageNo, int pageSize, String sort, String orderField, String startTime, String endTime, String userName, String userId);
}
