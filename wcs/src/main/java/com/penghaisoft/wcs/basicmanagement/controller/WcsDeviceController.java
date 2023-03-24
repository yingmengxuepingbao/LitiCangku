package com.penghaisoft.wcs.basicmanagement.controller;


import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsDeviceService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsDevice;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/device")
@Slf4j
public class WcsDeviceController extends BaseController {

	@Autowired
	private IWcsDeviceService wcsDeviceService;
	@Autowired
	private IUserBusiness userBusiness;

	/**
	 * 新增记录
	 * @return
	 */
	@PostMapping("c")
	public ResponseResult create(WcsDevice wcsDevice) {
		wcsDevice.setActiveFlag("1");
//			wcsDevice.setDeviceId(CommonUtils.getUUID());
		wcsDevice.setCreateBy(userBusiness.getCurrentUser().getNickname());
		wcsDevice.setGmtCreate(new Date());
		Resp resp =  wcsDeviceService.create(wcsDevice);
		ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
		return result;
	}

	/**
	 * 删除记录
	 * @return
	 */
	@PostMapping("delete")
	public ResponseResult delete(WcsDevice wcsDevice) {
		Resp resp =  wcsDeviceService.delete(wcsDevice);
		ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
		return result;
	}

	/**
	 * 查询列表
	 * @return
	 */
	@PostMapping("list")
	public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
							   @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,WcsDevice wcsDevice) {
		Pager<WcsDevice> resp = wcsDeviceService.findListByCondition(page,rows,wcsDevice);
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
		WcsDevice  wcsDevice = wcsDeviceService.findById(id);
		return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, wcsDevice);
	}

	/**
	 * 修改记录
	 * @return
	 */
	@PostMapping("u")
	public ResponseResult update(WcsDevice wcsDevice) {
		wcsDevice.setLastModifiedBy(userBusiness.getCurrentUser().getNickname());
		wcsDevice.setGmtModified(new Date());
		Resp resp =  wcsDeviceService.update(wcsDevice);
		ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
		return result;
	}

	/**
	 * 下拉框（设备）
	 * @param wcsDevice
	 * @return
	 */
	@PostMapping("finddeviceby")
	public ResponseResult findDeviceBy(WcsDevice wcsDevice) {
		wcsDevice.setActiveFlag("1");
		List<BaseDictItem> baseDictItemList = wcsDeviceService.findDeviceBy(wcsDevice);
		return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, baseDictItemList);
	}

	/**
	 * 加type
	 * @param wcsDevice
	 * @return
	 */
	@PostMapping("finddevicetype")
	public ResponseResult findDeviceType(WcsDevice wcsDevice) {
		List<BaseDictItem> baseDictItemList = wcsDeviceService.findDeviceType(wcsDevice);
		return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, baseDictItemList);
	}

}
