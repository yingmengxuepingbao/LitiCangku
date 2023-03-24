package com.penghaisoft.framework.log.model.business.impl;

import com.penghaisoft.framework.annotation.OperationLogAspect;
import com.penghaisoft.framework.constant.Constant.ConfigInfo;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.exception.AggregateException;
import com.penghaisoft.framework.log.model.business.ILoginLogBusiness;
import com.penghaisoft.framework.log.model.dao.ILoginLogDao;
import com.penghaisoft.framework.log.model.entity.LogSelectCondition;
import com.penghaisoft.framework.log.model.entity.LoginLog;
import com.penghaisoft.framework.util.DateTimeFormat;
import com.penghaisoft.framework.util.PageNumberTransfermation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录日志业务类
 * @author 秦超
 * 2017-08-31 14:47:49
 */
@Slf4j
@Service
public class LoginLogBusinessImpl implements ILoginLogBusiness {
	@Resource
	private ILoginLogDao iLoginLogDao;


	//日志管理每页数据条数
	private int numberOnePage = ConfigInfo.NUMBER_ONE_PAGE;
	//保存错误码
	private List<String> errorCodes = new ArrayList<>();
	//保存错误描述
	private List<String> errorMessages = new ArrayList<>();
	
	/**
	 * @Author 张旭
	 * @Description  获取登录日志列表接口
	 * @Date 11:54 2018/9/26
	 * @Param [pageNo, pageSize, sort, orderField, startTime, endTime, userName, userId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@Override
	@OperationLogAspect(operationName = "查看登录日志", operationType = "4")
	public Map<String, Object> getLoginLogList(int pageNo, int pageSize, String sort, String orderField, String startTime, String endTime, String userName, String userId) {
		//1. 初始化查询结果Map
		Map<String, Object> resultMap = new HashMap<>();
		//2.1 获取sql查询起始条数
		int startNumber = PageNumberTransfermation.pageNumberToSelectStartNumber(pageNo+1, pageSize);
		//2.2 日志查询条件对象
		LogSelectCondition logSelectCondition = new LogSelectCondition(startNumber, pageSize, userName, userId, startTime, endTime,sort,orderField);

		//3. 获取登录日志查询结果及条数
		try {
			int loginLogTotalCount = iLoginLogDao.getLoginLogTotalCount(logSelectCondition);
			//符合条件记录
			if (orderField!=null){
				orderField = PageNumberTransfermation.camelToUnderline(orderField);
			}
			List<LoginLog> loginLogList = iLoginLogDao.getLoginLogList(logSelectCondition);
			resultMap.put(PageNumberTransfermation.DATA_LIST,loginLogList);
			resultMap.put(PageNumberTransfermation.PAGE_NO, pageNo);
			resultMap.put(PageNumberTransfermation.PAGE_SIZE, pageSize);
			resultMap.put(PageNumberTransfermation.TOTAL_COUNT,loginLogTotalCount);
		} catch (Exception e) {
			errorCodes.add(RESULT.FAILED.code);
			errorMessages.add(RESULT.FAILED.message);
			log.error("业务抛错：", e);
			return null;
		}

		return resultMap;
	}
	
	/**
	 * 新增登录日志记录接口
	 * @param userId 用户id
	 * @param userAccount 用户帐户
	 * @param userName 用户姓名/昵称
	 * @param loginIp 登录ip
	 * @return
	 * @author 秦超
	 * 2017-08-28 10:28:32
	 * @throws AggregateException
	 */
	@Override
	public boolean addLoginLog(int userId, String userAccount, String userName, String loginIp){
		//1.获取当前时间
		String loginTime= DateTimeFormat.getCurrentDateFormat();
		
		try {
			//2.构造登录日志对象
			LoginLog loginLog = new LoginLog(userId, userAccount, userName, loginIp, loginTime);
			return iLoginLogDao.insert(loginLog);
		} catch (AggregateException ex) {
			//3.构造方法异常信息处理
			List<Exception> exceptionList = ex.getInnerExceptions();
			StringBuilder errorList = new StringBuilder();
			for(Exception e : exceptionList){
				errorList.append(e.getMessage()).append(",");
			}
			errorCodes.add(RESULT.LOGMANAGEMENT_PARAMETER_ERROR.code);
			errorMessages.add(errorList.toString());

			log.error("业务抛错：", ex);
			return false;
		} catch (Exception e) {
			errorCodes.add(RESULT.FAILED.code);
			errorMessages.add(RESULT.FAILED.message);
			log.error("业务抛错：", e);
			return false;
		}
	}
	/**
	 * 获取错误码
	 * @return
	 * @author 秦超
	 * 2017-09-01 15:23:56
	 */
	@Override
	public String getLastErrorCode(){
		return errorCodes.get(errorCodes.size()-1);
	}
	/**
	 * 获取错误信息
	 * @return
	 * @author 秦超
	 * 2017-09-01 15:24:04
	 */
	@Override
	public String getLastErrorMessage(){
		return errorMessages.get(errorMessages.size()-1);
	}


}
