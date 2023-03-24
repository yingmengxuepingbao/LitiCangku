package com.penghaisoft.wcs.basicmanagement.controller;


import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.log.model.business.IWcsCallAgvLogService;
import com.penghaisoft.wcs.log.model.entity.WcsCallAgvLog;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @Description: 下发AGV指令日志
 * @Author: jzh
 * @Date: 2020/7/28
 */ 
@RestController
@RequestMapping(value = "/callAgvLog")
@Slf4j
public class WcsCallAgvLogController extends BaseController {
	
		@Autowired
		private IWcsCallAgvLogService wcsCallAgvLogService;
		@Autowired
		private IUserBusiness userBusiness;


		/**
		 * @Description: 下发AGV指令查询
		 * @Author: jzh
		 * @Date: 2020/7/28
		 */ 
		@PostMapping("list")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
                            @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsCallAgvLog wcsCallAgvLog) {
			if (wcsCallAgvLog.getOrderBy()==null){
				wcsCallAgvLog.setOrderBy("task_code desc");
			}
			Pager<WcsCallAgvLog> resp = wcsCallAgvLogService.findCallAgvLog(page,rows,wcsCallAgvLog);
			//将列表、总数转化为Map
			Map<String, Object> dateMap = new HashMap<>();
			dateMap.put("infoList", resp.getRecords());
			dateMap.put("totalNumber", resp.getTotalCount());
			ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, dateMap);
			return result;
		}

		/**
		 * @Description: AGV指令重发
		 * @Author: jzh
		 * @Date: 2020/7/28
		 */
		@PostMapping("callAgvTask")
		public ResponseResult callAgvTask(WcsCallAgvLog wcsCallAgvLog) {
			String loginName = userBusiness.getCurrentUser().getNickname();
			Resp resp =  wcsCallAgvLogService.callAgvTask(wcsCallAgvLog,loginName);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

}
