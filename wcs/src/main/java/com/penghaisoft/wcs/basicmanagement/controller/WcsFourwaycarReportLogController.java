package com.penghaisoft.wcs.basicmanagement.controller;


import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.log.model.business.IWcsFourwaycarReportLogService;
import com.penghaisoft.wcs.log.model.entity.WcsFourwaycarReportLog;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @Description: 四向车上报任务表
 * @Author: jzh
 * @Date: 2020/7/29
 */ 
@RestController
@RequestMapping(value = "/fourwaycarReportLog")
@Slf4j
public class WcsFourwaycarReportLogController extends BaseController {
	
		@Autowired
		private IWcsFourwaycarReportLogService wcsFourwaycarReportLogService;
		@Autowired
		private IUserBusiness userBusiness;

		/**
		 * @Description: 四向车上报任务日志查询
		 * @Author: jzh
		 * @Date: 2020/7/29
		 */ 
		@PostMapping("list")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
                            @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsFourwaycarReportLog wcsFourwaycarReportLog) {
			if (wcsFourwaycarReportLog.getOrderBy()==null){
				wcsFourwaycarReportLog.setOrderBy("receive_time desc");
			}
			Pager<WcsFourwaycarReportLog> resp = wcsFourwaycarReportLogService.findFourwaycarReportLog(page,rows,wcsFourwaycarReportLog);
			//将列表、总数转化为Map
			Map<String, Object> dateMap = new HashMap<>();
			dateMap.put("infoList", resp.getRecords());
			dateMap.put("totalNumber", resp.getTotalCount());
			ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, dateMap);
			return result;
		}

}
