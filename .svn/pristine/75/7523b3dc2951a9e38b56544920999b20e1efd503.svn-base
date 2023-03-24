package com.penghaisoft.framework.log.model.business.impl;

import com.penghaisoft.framework.annotation.OperationLogAspect;
import com.penghaisoft.framework.constant.Constant.ConfigInfo;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.exception.AggregateException;
import com.penghaisoft.framework.log.model.business.IOperationLogBusiness;
import com.penghaisoft.framework.log.model.dao.IOperationLogDao;
import com.penghaisoft.framework.log.model.entity.LogSelectCondition;
import com.penghaisoft.framework.log.model.entity.OperationLog;
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
 * 操作日志业务类
 * @author 秦超
 * 2017-08-31 14:47:49
 */
@Slf4j
@Service
public class OperationLogBusinessImpl implements IOperationLogBusiness {
	@Resource
	private IOperationLogDao iOperationLogDao;

	//日志管理每页数据条数
	private int numberOnePage = ConfigInfo.NUMBER_ONE_PAGE;
	//保存错误码
	private List<String> errorCodes = new ArrayList<>();
	//保存错误描述
	private List<String> errorMessages = new ArrayList<>();
	

	/**
	 * 新增登录日志记录接口
	 * @param userId 操作用户id
	 * @param operationName 操作菜单名
	 * @author 秦超
	 * 2017-08-28 10:28:32
	 * @throws AggregateException
	 */
	@Override
	public boolean addOperationLog(int userId, String operationName, String operationType){
		//1.获取当前时间
		String operationTime= DateTimeFormat.getCurrentDateFormat();
		
		try {
			//2.构造操作日志对象
			OperationLog operationLog = new OperationLog(userId, operationName, operationTime);
			return iOperationLogDao.insert(operationLog);
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

	/**
	 * @param pageNo
	 * @param pageSize
	 * @param sort
	 * @param orderField
	 * @param startTime
	 * @param endTime
	 * @param userName
	 * @param userId
	 * @return java.util.Map<java.lang.String , java.lang.Object>
	 * @Author 张旭
	 * @Description 获取操作日志列表接口
	 * @Date 14:19 2018/9/26
	 * @Param [pageNo, pageSize, sort, orderField, startTime, endTime, userName, userId]
	 */
	@Override
	@OperationLogAspect(operationName = "查看操作日志", operationType = "4")
	public Map<String, Object> getOperationLogList(int pageNo, int pageSize, String sort, String orderField, String startTime, String endTime, String userName, String userId) {
		//1.初始化查询结果Map
		Map<String,Object> resultMap = new HashMap<>();

		//2.1 获取sql查询起始条数
		int startNumber = PageNumberTransfermation.pageNumberToSelectStartNumber(pageNo+1, numberOnePage);
		//2.2 日志查询条件对象
		LogSelectCondition logSelectCondition = new LogSelectCondition(startNumber,pageSize,userName,userId,startTime,endTime,sort,orderField);

		//3.获取操作日至查询结果及条数
		try {
			int operationLogTotalCount = iOperationLogDao.getOperationLogTotalCount(logSelectCondition);
			//符合条件记录
			if (orderField!=null){
				orderField = PageNumberTransfermation.camelToUnderline(orderField);
			}
			List<OperationLog> operationLogList = iOperationLogDao.getOperationLogList(logSelectCondition);
			resultMap.put(PageNumberTransfermation.DATA_LIST,operationLogList);
			resultMap.put(PageNumberTransfermation.PAGE_NO, pageNo);
			resultMap.put(PageNumberTransfermation.PAGE_SIZE, pageSize);
			resultMap.put(PageNumberTransfermation.TOTAL_COUNT,operationLogTotalCount);
		} catch (Exception e) {
			errorCodes.add(RESULT.FAILED.code);
			errorMessages.add(RESULT.FAILED.message);

			log.error("业务抛错：", e);
			return null;
		}
		return resultMap;
	}
}
