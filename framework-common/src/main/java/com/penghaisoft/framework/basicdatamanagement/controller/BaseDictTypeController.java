package com.penghaisoft.framework.basicdatamanagement.controller;


import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictTypeService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictType;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/dictType")
@Slf4j
public class BaseDictTypeController  {
	
		@Autowired
		private IBaseDictTypeService baseDictTypeService;
		@Autowired
		private IUserBusiness userBusiness;
	  /**
	   * 新增记录
	   * @param baseDictType
	   * @return
	   */
		@PostMapping("c")
		public ResponseResult create(BaseDictType baseDictType) {
			baseDictType.setDictTypeId(CommonUtils.getUUID());
			//获取登录名，插入用
			User user = userBusiness.getCurrentUser();
			String loginName = user.getNickname();
			baseDictType.setCreateBy(loginName);
			baseDictType.setActiveFlag("1");
			baseDictType.setGmtCreate(new Date());
			Resp resp =  baseDictTypeService.create(baseDictType);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 删除记录（逻辑删除active_flag=0)
	   * @param baseDictType
	   * @return
	   */
		@PostMapping("d")
		public ResponseResult delete(BaseDictType baseDictType) {
			Resp resp =  baseDictTypeService.delete(baseDictType);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 查询列表
	   *
	   * @return
	   */
		@PostMapping("list")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
                            @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,BaseDictType baseDictType) {
			Pager<BaseDictType> resp = baseDictTypeService.findListByCondition(page,rows,baseDictType);
			if (resp.getRecords().size()>0) {
                //特殊校验是否为主表ParentId=0(隐藏返回)
                resp.getRecords().get(0).setParentId("0");
                //设置级别level 1
                for (BaseDictType baseDictTypeLevel : resp.getRecords()) {
                    baseDictTypeLevel.setLevel("1");
                }
            }
			//将单位列、总单位数转化为Map
			Map<String, Object> unitMap = new HashMap<>();
			unitMap.put("infoList", resp.getRecords());
			unitMap.put("totalNumber", resp.getTotalCount());
			ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, unitMap);
			return result;
		}

	  /**
	   * 查询单条
	   * @param id
	   * @return
	   */
		@GetMapping("r/{id}")
		public ResponseResult queryById(@PathVariable String id) {
			BaseDictType  baseDictType = baseDictTypeService.findById(id);
			return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, baseDictType);
		}

	  /**
	   * 修改记录
	   * @param baseDictType
	   * @return
	   */
		@PostMapping("u")
		public ResponseResult update(BaseDictType baseDictType) {
            User user = userBusiness.getCurrentUser();
            baseDictType.setLastModifiedBy(user.getNickname());
            baseDictType.setGmtModified(new Date());
			Resp resp =  baseDictTypeService.update(baseDictType);
	    	ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}
		/**
		 * 物理删除
		 * @return
		 */
		@RequestMapping(value = "/deleteDictType", method = RequestMethod.POST)
		@ResponseBody
		public ResponseResult deleteDictType(String dictTypeId) {
			//响应结果对象
			ResponseResult responseResult = null;
			Resp resp = baseDictTypeService.deleteDictType(dictTypeId);

			responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), null);
			return responseResult;
		}
}
