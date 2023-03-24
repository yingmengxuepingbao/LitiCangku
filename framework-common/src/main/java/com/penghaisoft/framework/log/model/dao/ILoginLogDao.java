package com.penghaisoft.framework.log.model.dao;

import com.penghaisoft.framework.log.model.entity.LogSelectCondition;
import com.penghaisoft.framework.log.model.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 登录日志数据查询接口
 * @author Q.chao
 * 2017-09-01 15:59:27
 */
public interface ILoginLogDao {
	/**
	 * 获取登录日志列表接口
	 * @return 当前页码的登录日志列表
	 * @author 秦超
	 * 2017-08-28 10:26:44
	 */
	List<LoginLog> getLoginLogList(LogSelectCondition logSelectCondition);
	/**
	 * 获取登录日志条数接口
	 * @return 当前页码的登录日志列表
	 * @author 秦超
	 * 2017-08-28 10:26:44
	 */
	int getLoginLogTotalCount(LogSelectCondition logSelectCondition);
	/**
	 * 新增登录日志记录接口
	 * @param loginLog 登录日志实体对象
	 * @author 秦超
	 * 2017-08-28 10:28:32
	 */
	boolean insert(LoginLog loginLog);
}
