package com.penghaisoft.wcs.basicmanagement.controller;


import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsAgvTaskPlaneService;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;
import com.penghaisoft.wcs.operation.service.BindingService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
  * @Description M6M7的agv任务相关
  * @ClassName WcsAgvTaskPlaneController
  * @Author luot
  * @Date 2020/8/7 16:00
  **/
@RestController
@RequestMapping(value = "/wcsAgvTaskPlane")
@Slf4j
public class WcsAgvTaskPlaneController extends BaseController {

		@Autowired
		private IWcsAgvTaskPlaneService wcsAgvTaskPlaneService;

	  	/**
	  	 * @Description: 绑定日志查询
	  	 * @Author: jzh
	  	 * @Date: 2020/7/7
	  	 */ 
		@PostMapping("list")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
								   @RequestParam(name = "numberOnePage", defaultValue = "10") int rows, WcsAgvTaskPlane wcsAgvTaskPlane) {
			if (wcsAgvTaskPlane.getOrderBy()==null || wcsAgvTaskPlane.getOrderBy().equals("") ){
				wcsAgvTaskPlane.setOrderBy("gmt_create desc");
			}
			Pager<WcsAgvTaskPlane> resp = wcsAgvTaskPlaneService.findBindingInfo(page,rows,wcsAgvTaskPlane);
			//将列表、总数转化为Map
			Map<String, Object> dateMap = new HashMap<>();
			dateMap.put("infoList", resp.getRecords());
			dateMap.put("totalNumber", resp.getTotalCount());
			ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, dateMap);
			return result;
		}

}
