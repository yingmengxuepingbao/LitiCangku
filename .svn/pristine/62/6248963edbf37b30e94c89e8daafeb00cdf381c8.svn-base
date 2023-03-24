package com.penghaisoft.wcs.basicmanagement.controller;


import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsAddressService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsAddress;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsDevice;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/address")
@Slf4j
public class WcsAddressController extends BaseController {
	
		@Autowired
		private IWcsAddressService wcsAddressService;
		@Autowired
		private IUserBusiness userBusiness;

	  /**
	   * 新增记录
	   * @return
	   */
		@PostMapping("c")
		public ResponseResult create(WcsAddress wcsAddress) {
			wcsAddress.setActiveFlag("1");
//			wcsAddress.setAddressId(CommonUtils.getUUID());
			wcsAddress.setCreateBy(userBusiness.getCurrentUser().getNickname());
			wcsAddress.setGmtCreate(new Date());
			Resp resp =  wcsAddressService.create(wcsAddress);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 删除记录
	   * @return
	   */
		@PostMapping("delete")
		public ResponseResult delete(WcsAddress wcsAddress) {
			Resp resp =  wcsAddressService.delete(wcsAddress);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 查询列表
	   * @return
	   */
		@PostMapping("list")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
                            @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsAddress wcsAddress) {
			if (wcsAddress.getOrderBy()==null){
				wcsAddress.setOrderBy("gmt_create desc");
			}
			Pager<WcsAddress> resp = wcsAddressService.findListByCondition(page,rows,wcsAddress);
			//将列表、总数转化为Map
			Map<String, Object> dateMap = new HashMap<>();
			dateMap.put("infoList", resp.getRecords());
			dateMap.put("totalNumber", resp.getTotalCount());
			ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, dateMap);
			return result;
		}

	  /**
	   * 查询单条
	   * @return
	   */
		@GetMapping("r/{id}")
		public ResponseResult queryById(@PathVariable String id) {
			WcsAddress  wcsAddress = wcsAddressService.findById(id);
			return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, wcsAddress);
		}

	  /**
	   * 修改记录
	   * @return
	   */
		@PostMapping("u")
		public ResponseResult update(WcsAddress wcsAddress) {
			wcsAddress.setLastModifiedBy(userBusiness.getCurrentUser().getNickname());
			wcsAddress.setGmtModified(new Date());
			Resp resp =  wcsAddressService.update(wcsAddress);
	    	ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

		@PostMapping("findaddress")
		public ResponseResult findAddress(WcsAddress wcsAddress) {
			List<BaseDictItem> baseDictItemList = wcsAddressService.findAddress(wcsAddress);
			return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, baseDictItemList);
		}
		/**
		 * @Description: 手动AGV任务下发，查询agv地址下拉框
		 * @Author: jzh
		 * @Date: 2020/7/16
		 */ 
		@PostMapping("findAgvAddress")
		public ResponseResult findAgvAddress(WcsAddress wcsAddress) {
			List<BaseDictItem> baseDictItemList = wcsAddressService.findAgvAddress(wcsAddress);
			return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, baseDictItemList);
		}

}
