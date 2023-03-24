package com.penghaisoft.wcs.basicmanagement.controller;


import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsPathService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsPath;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/path")
@Slf4j
public class WcsPathController extends BaseController {
	
		@Autowired
		private IWcsPathService wcsPathService;
		@Autowired
		private IUserBusiness userBusiness;

	  /**
	   * 新增记录
	   * @return
	   */
		@PostMapping("c")
		public ResponseResult create(WcsPath wcsPath) {
			wcsPath.setActiveFlag("1");
//			wcsPath.setPathId(CommonUtils.getUUID());
			wcsPath.setCreateBy(userBusiness.getCurrentUser().getNickname());
			wcsPath.setGmtCreate(new Date());
			Resp resp =  wcsPathService.create(wcsPath);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 删除记录
	   * @return
	   */
		@PostMapping("delete")
		public ResponseResult delete(WcsPath wcsPath) {
			Resp resp =  wcsPathService.delete(wcsPath);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 查询列表
	   * @return
	   */
		@PostMapping("list")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
                            @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsPath wcsPath) {
			if (wcsPath.getOrderBy()==null){
				wcsPath.setOrderBy("gmt_create desc");
			}
			Pager<WcsPath> resp = wcsPathService.findListByCondition(page,rows,wcsPath);
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
			WcsPath  wcsPath = wcsPathService.findById(id);
			return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, wcsPath);
		}

	  /**
	   * 修改记录
	   * @return
	   */
		@PostMapping("u")
		public ResponseResult update(WcsPath wcsPath) {
			wcsPath.setLastModifiedBy(userBusiness.getCurrentUser().getNickname());
			wcsPath.setGmtModified(new Date());
			Resp resp =  wcsPathService.update(wcsPath);
	    	ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

}
