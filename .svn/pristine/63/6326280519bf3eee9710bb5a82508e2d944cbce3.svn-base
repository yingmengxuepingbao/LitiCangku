package com.penghaisoft.wcs.taskmanagement.controller;


import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.taskmanagement.model.business.IWcsTaskService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTaskDetailDTO;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/task")
@Slf4j
public class WcsTaskController extends BaseController {

	@Autowired
	private IWcsTaskService wcsTaskService;

	/**
	 * 查询列表
	 * @return
	 */
	@PostMapping("list")
	public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
							   @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsTask wcsTask) {
		if (wcsTask.getOrderBy()==null){
			wcsTask.setOrderBy("gmt_create desc");
		}
		Pager<WcsTask> resp = wcsTaskService.findListByCondition(page,rows,wcsTask);
		//将列表、总数转化为Map
		Map<String, Object> dateMap = new HashMap<>();
		dateMap.put("infoList", resp.getRecords());
		dateMap.put("totalNumber", resp.getTotalCount());
		ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, dateMap);
		return result;
	}

	@GetMapping("queryDetailByTaskId/{taskId}")
	public ResponseResult queryDetailByTaskId(@PathVariable String taskId) {
		List<WcsTaskDetailDTO> list = wcsTaskService.queryDetailByTaskId(taskId);
		return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, list);
	}

	/**
	 * 根据taskId修改任务状态(线体,堆垛机)
	 * @param wcsTaskDetailDTO
	 * @return
	 */
	@PostMapping("changeTaskStatus")
	public ResponseResult changeTaskStatus(WcsTaskDetailDTO wcsTaskDetailDTO) {
		Resp resp =  wcsTaskService.changeTaskStatus(wcsTaskDetailDTO);
		ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
		return result;
	}

	/**
	 * 修改主任务状态
	 * @param wcsTask
	 * @return
	 */
	@PostMapping("changeMain")
	public ResponseResult changeMain(WcsTask wcsTask) {
		Resp resp =  wcsTaskService.changeMain(wcsTask);
		ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
		return result;
	}

	/**
	 * 修改上传wms状态
	 * @param wcsTask
	 * @return
	 */
	@PostMapping("changeReportWms")
	public ResponseResult changeReportWms(WcsTask wcsTask) {
		Resp resp =  wcsTaskService.changeReportWms(wcsTask);
		ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
		return result;
	}


	@PostMapping("delete")
	public ResponseResult deleteTask(@RequestParam Integer id) {
		Resp resp = wcsTaskService.delUselessTask(id);
		ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
		return result;
	}
}
