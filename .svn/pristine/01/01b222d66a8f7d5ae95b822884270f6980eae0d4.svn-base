package com.penghaisoft.wcs.basicmanagement.controller;


import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsLocationRealService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/location")
@Slf4j
public class WcsLocationRealController extends BaseController {
	
		@Autowired
		private IWcsLocationRealService wcsLocationRealService;
		@Autowired
		private IUserBusiness userBusiness;

	  /**
	   * 新增记录
	   * @return
	   */
		@PostMapping("c")
		public ResponseResult create(WcsLocationReal wcsLocationReal) {
			wcsLocationReal.setActiveFlag("1");
//			wcsLocationReal.setLocationId(CommonUtils.getUUID());
			wcsLocationReal.setCreateBy(userBusiness.getCurrentUser().getNickname());
			wcsLocationReal.setGmtCreate(new Date());
			Resp resp =  wcsLocationRealService.create(wcsLocationReal);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 删除记录
	   * @return
	   */
		@PostMapping("delete")
		public ResponseResult delete(WcsLocationReal wcsLocationReal) {
			Resp resp =  wcsLocationRealService.delete(wcsLocationReal);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 查询列表
	   * @return
	   */
		@PostMapping("list")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
                            @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsLocationReal wcsLocationReal) {
			if (wcsLocationReal.getOrderBy()==null){
				wcsLocationReal.setOrderBy("gmt_create desc");
			}
			Pager<WcsLocationReal> resp = wcsLocationRealService.findListByCondition(page,rows,wcsLocationReal);
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
		public ResponseResult queryById(@PathVariable Integer id) {
			WcsLocationReal  wcsLocationReal = wcsLocationRealService.findById(id);
			return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, wcsLocationReal);
		}

	  /**
	   * 修改记录
	   * @return
	   */
		@PostMapping("u")
		public ResponseResult update(WcsLocationReal wcsLocationReal) {
			wcsLocationReal.setLastModifiedBy(userBusiness.getCurrentUser().getNickname());
			wcsLocationReal.setGmtModified(new Date());
			Resp resp =  wcsLocationRealService.update(wcsLocationReal);
	    	ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

}
