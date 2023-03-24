package com.penghaisoft.wcs.taskmanagement.controller;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.taskmanagement.model.business.IWcsManualAgvTaskService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsManualAgvTask;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @Description: AGV手动控制管理
 * @Author: jzh
 * @Date: 2020/7/16
 */
@RestController
@RequestMapping(value = "/manualAgvTask")
@Slf4j
public class WcsManualAgvTaskController extends BaseController {
	
		@Autowired
		private IWcsManualAgvTaskService wcsManualAgvTaskService;
		@Autowired
		private IUserBusiness userBusiness;

	  /**
	   * 新增记录
	   * @return
	   */
		@PostMapping("c")
		public ResponseResult create(WcsManualAgvTask wcsManualAgvTask) {
			wcsManualAgvTask.setActiveFlag("1");
			//任务号
			wcsManualAgvTask.setTaskNo( "M-"+System.currentTimeMillis());
			wcsManualAgvTask.setTaskStatus("1");
			wcsManualAgvTask.setCreateBy(userBusiness.getCurrentUser().getNickname());
			wcsManualAgvTask.setGmtCreate(new Date());
			Resp resp =  wcsManualAgvTaskService.create(wcsManualAgvTask);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 删除记录
	   * @return
	   */
		@PostMapping("delete")
		public ResponseResult delete(WcsManualAgvTask wcsManualAgvTask) {
			Resp resp =  wcsManualAgvTaskService.delete(wcsManualAgvTask);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 查询列表
	   * @return
	   */
		@PostMapping("list")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
                            @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsManualAgvTask wcsManualAgvTask) {
			if (wcsManualAgvTask.getOrderBy()==null){
				wcsManualAgvTask.setOrderBy("gmt_create desc");
			}
			Pager<WcsManualAgvTask> resp = wcsManualAgvTaskService.findListByCondition(page,rows,wcsManualAgvTask);
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
			WcsManualAgvTask  wcsManualAgvTask = wcsManualAgvTaskService.findById(id);
			return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, wcsManualAgvTask);
		}

	  /**
	   * 修改记录
	   * @return
	   */
		@PostMapping("u")
		public ResponseResult update(WcsManualAgvTask wcsManualAgvTask) {
			wcsManualAgvTask.setLastModifiedBy(userBusiness.getCurrentUser().getNickname());
			wcsManualAgvTask.setGmtModified(new Date());
			Resp resp =  wcsManualAgvTaskService.update(wcsManualAgvTask);
	    	ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}
		/**
		 * @Description: 手动AGV任务下发
		 * @Author: jzh
		 * @Date: 2020/7/16
		 */ 
		@PostMapping("startTask")
		public ResponseResult startTask(WcsManualAgvTask wcsManualAgvTask) {
			String loginName = userBusiness.getCurrentUser().getNickname();
			Resp resp =  wcsManualAgvTaskService.startTask(wcsManualAgvTask,loginName);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

}
