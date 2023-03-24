package com.penghaisoft.wcs.basicmanagement.controller;


import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;
import com.penghaisoft.wcs.operation.service.BindingService;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @Description: 绑定信息类
 * @Author: jzh
 * @Date: 2020/7/7
 */
@RestController
@RequestMapping(value = "/bindingInfo")
@Slf4j
public class WcsBindingInfoController extends BaseController {

	@Autowired
	private BindingService wcsBindingInfoService;

	/**
	 * @Description: 绑定日志查询
	 * @Author: jzh
	 * @Date: 2020/7/7
	 */
	@PostMapping("list")
	public ResponseResult list(@RequestParam(name = "page", defaultValue = "1") int page,
							   @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsBindingInfo wcsBindingInfo) {
		if (wcsBindingInfo.getOrderBy()==null || wcsBindingInfo.getOrderBy().equals("") ){
			wcsBindingInfo.setOrderBy("gmt_create desc");
		}
		Pager<WcsBindingInfo> resp = wcsBindingInfoService.findBindingInfo(page,rows,wcsBindingInfo);
		//将列表、总数转化为Map
		Map<String, Object> dateMap = new HashMap<>();
		dateMap.put("infoList", resp.getRecords());
		dateMap.put("totalNumber", resp.getTotalCount());
		ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, dateMap);
		return result;
	}

}
