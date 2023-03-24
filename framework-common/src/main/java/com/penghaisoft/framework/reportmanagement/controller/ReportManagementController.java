package com.penghaisoft.framework.reportmanagement.controller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.reportmanagement.model.business.IReportManagementBusiness;
import com.penghaisoft.framework.reportmanagement.model.entity.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

/**
 * 报表管理控制器
 * @author 秦超
 * 2017年9月26日 15:17:02
 */
@Controller
@RequestMapping(value = "/reportManagement")
public class ReportManagementController {
	
	@Autowired
	private IReportManagementBusiness iReportManagementBuseniss;

	private ResponseResult genResult(Object data){
		//响应结果对象
		ResponseResult responseResult = null;
		if(data != null) {
			responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, data);
		}
		else {
			responseResult = new ResponseResult(iReportManagementBuseniss.getLastErrorCode(), iReportManagementBuseniss.getLastErrorMessage(), null);
		}
		return responseResult;
	}
	
	/**
	 * 上传报表文件
	 * @param file 上传文件 
	 * @return
	 * @author 秦超
	 * 2017-09-26 15:18:58
	 */
	@RequestMapping(value = "/addReport", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult addReport(MultipartFile file, String title, String description){
		 //响应结果对象
		ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
		
		//1.校验文件
		boolean result = iReportManagementBuseniss.checkFile(file, title);
		
		//2.校验文件通过，保存文件
		if(result){
			result = iReportManagementBuseniss.addReport(file, title, description);
		}
		
		if(!result) {
			responseResult = new ResponseResult(iReportManagementBuseniss.getLastErrorCode(), iReportManagementBuseniss.getLastErrorMessage(), null);
		}
		
	    return responseResult;
	}
	
	/**
	 * 获取报表文件列表
	 * @author 秦超
	 * 2017-10-09 14:30:05
	 */
	@RequestMapping(value = "/getReportList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getReportList(int currentPage){

		Map<String, Object> reportList = iReportManagementBuseniss.getReportList(currentPage);

	    return genResult(reportList);
	}

	/**
	 * 获取所有的报表
	 * @return
	 */
	@RequestMapping(value = "/getAllReportList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getAllReportList(){
		ResponseResult responseResult = null;
		List<Report> reportList = iReportManagementBuseniss.getAllReportList();
		if (null == reportList){
			responseResult = new ResponseResult(RESULT.REPORTMANAGEMENT_UPLOAD_FILE_ERROR.code, RESULT.REPORTMANAGEMENT_UPLOAD_FILE_ERROR.message, null);
		}else{
			responseResult = genResult(reportList);
		}
		return responseResult;
	}
}
