package com.penghaisoft.framework.log.controller;


import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.log.model.business.ILoginLogBusiness;
import com.penghaisoft.framework.log.model.business.IOperationLogBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * 日志管理请求控制
 * @author 秦超
 * @time 2017-08-28 10:05:32
 */
@Controller
@RequestMapping(value = "/logManager")
public class LogController {
	@Autowired
	private ILoginLogBusiness iLoginLogBusiness;
	@Autowired
	private IOperationLogBusiness iOperationLogBusiness;
	
	/*
	 * @Author 张旭
	 * @Description  获取登录日志列表
	 * @Date 13:46 2018/9/26
	 * @Param [pageNo, pageSize, sort, orderField, startTime, endTime, userName, userId]
	 * @return com.penghaisoft.framework.entity.ResponseResult
	 **/
	@RequestMapping(value = "/getLoginLogList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getLoginLogList(int pageNo, int pageSize, @RequestParam(required = false) String sort, @RequestParam(required = false) String orderField, String startTime, String endTime, String userName, String userId){
		//1.声明返回对象
		ResponseResult responseResult = null;
		//2.获取登录日志信息
		Map<String,Object> resultMap = iLoginLogBusiness.getLoginLogList(pageNo,pageSize,sort,orderField, startTime, endTime, userName, userId);

		if(resultMap != null){
			responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message,resultMap);
		}else{
			responseResult = new ResponseResult(iLoginLogBusiness.getLastErrorCode(),iLoginLogBusiness.getLastErrorMessage(),null);
		}

		return responseResult;
	}
	
	/**
	 * 获取操作日志列表
	 * @param currentPage 当前页码
	 * @param startTime 查询开始时间
	 * @param endTime 查询截止时间
	 * @param userName 查询用户名
	 * @return 操作日志列表信息
	 * @author 秦超
	 * 2017-08-28 10:19:39
	 */
	@RequestMapping(value = "/getOperationLogList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getOperationLogList(int pageNo, int pageSize,
                                              @RequestParam(required = false) String sort, @RequestParam(required = false) String orderField,
                                              String startTime, String endTime, String userName, String userId){
		//1.声明返回对象
		ResponseResult responseResult = null;
		//2.获取操作日志信息
		Map<String,Object> resultMap = iOperationLogBusiness.getOperationLogList(pageNo,pageSize,sort,orderField,startTime, endTime, userName, userId);

		if(resultMap != null){
			responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message,resultMap);
		}else{
			responseResult = new ResponseResult(iOperationLogBusiness.getLastErrorCode(),iOperationLogBusiness.getLastErrorMessage(),null);
		}

		return responseResult;
	}
}
