package com.penghaisoft.framework.log.model.business;

import com.penghaisoft.framework.exception.AggregateException;
import java.util.Map;

/**
 * 登录日志业务类接口
 * @author 秦超
 * 2017-09-01 15:57:39
 */
public interface ILoginLogBusiness {
	/**
	 * 新增登录日志记录接口
	 * @param userId 用户id
	 * @param userAccount 用户帐户
	 * @param userName 用户姓名/昵称
	 * @param loginIp 登录ip
	 * @return
	 * @author 秦超
	 * 2017-08-31 09:54:13
	 * @throws AggregateException
	 */
	boolean addLoginLog(int userId, String userAccount, String userName, String loginIp);
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
	 * @Description  获取登录日志列表接口
	 * @Date 11:54 2018/9/26
	 * @Param [pageNo, pageSize, sort, orderField, startTime, endTime, userName, userId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getLoginLogList(int pageNo, int pageSize, String sort, String orderField, String startTime, String endTime, String userName, String userId);
}
