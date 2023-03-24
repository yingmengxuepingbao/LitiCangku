package com.penghaisoft.wcs.basicmanagement.controller;


import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.log.model.business.IWcsCallFourwaycarLogService;
import com.penghaisoft.wcs.log.model.entity.WcsCallFourwaycarLog;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @Description: 调用四向车日志
 * @Author: jzh
 * @Date: 2020/7/30
 */ 
@RestController
@RequestMapping(value = "/callFourwaycarLog")
@Slf4j
public class WcsCallFourwaycarLogController extends BaseController {
	
		@Autowired
		private IWcsCallFourwaycarLogService wcsCallFourwaycarLogService;
		@Autowired
		private IUserBusiness userBusiness;

		/**
		 * @Description: 调用四向车日志查询
		 * @Author: jzh
		 * @Date: 2020/7/30
		 */ 
		@PostMapping("list")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
                            @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsCallFourwaycarLog wcsCallFourwaycarLog) {
			if (wcsCallFourwaycarLog.getOrderBy()==null){
				wcsCallFourwaycarLog.setOrderBy("send_time desc");
			}
			Pager<WcsCallFourwaycarLog> resp = wcsCallFourwaycarLogService.findCallFourwaycarLog(page,rows,wcsCallFourwaycarLog);
			//将列表、总数转化为Map
			Map<String, Object> dateMap = new HashMap<>();
			dateMap.put("infoList", resp.getRecords());
			dateMap.put("totalNumber", resp.getTotalCount());
			ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, dateMap);
			return result;
		}

        /**
         * @Description: 四向车日志重发
         * @Author: jzh
         * @Date: 2020/7/30
         */
        @PostMapping("resend")
        public ResponseResult resend(WcsCallFourwaycarLog wcsCallFourwaycarLog) {
            String loginName = userBusiness.getCurrentUser().getNickname();
            Resp resp =  wcsCallFourwaycarLogService.resend(wcsCallFourwaycarLog,loginName);
            ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
            return result;
        }
}
