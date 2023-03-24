package com.penghaisoft.wcs.basicmanagement.controller;


import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.log.model.business.IWcsAgvReportLogService;
import com.penghaisoft.wcs.log.model.entity.WcsAgvReportLog;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @Description: AGV回调日志查询
 * @Author: jzh
 * @Date: 2020/7/27
 */ 
@RestController
@RequestMapping(value = "/agvReportLog")
@Slf4j
public class WcsAgvReportLogController extends BaseController {
	
		@Autowired
		private IWcsAgvReportLogService wcsAgvReportLogService;
		@Autowired
		private IUserBusiness userBusiness;

		/**
		 * @Description: AGV回调日志查询
		 * @Author: jzh
		 * @Date: 2020/7/27
		 */
		@PostMapping("list")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
                            @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsAgvReportLog wcsAgvReportLog) {
			if (wcsAgvReportLog.getOrderBy()==null||"".equals(wcsAgvReportLog.getOrderBy())){
				wcsAgvReportLog.setOrderBy("task_code desc");
			}
			Pager<WcsAgvReportLog> resp = wcsAgvReportLogService.findAgvReportLog(page,rows,wcsAgvReportLog);
			//将列表、总数转化为Map
			Map<String, Object> dateMap = new HashMap<>();
			dateMap.put("infoList", resp.getRecords());
			dateMap.put("totalNumber", resp.getTotalCount());
			ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, dateMap);
			return result;
		}

}
