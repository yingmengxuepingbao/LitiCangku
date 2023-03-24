package com.penghaisoft.framework.basicdatamanagement.controller;


import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictType;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/dictItem")
@Slf4j
public class BaseDictItemController extends BaseController {
	
		@Autowired
		private IBaseDictItemService baseDictItemService;
        @Autowired
        private IUserBusiness userBusiness;
	  /**
	   * 新增记录
	   * @param baseDictItem
	   * @return
	   */
		@PostMapping("c")
		public ResponseResult create(BaseDictItem baseDictItem) {
			baseDictItem.setDictItemId(CommonUtils.getUUID());
            //获取登录名，插入用
            User user = userBusiness.getCurrentUser();
            baseDictItem.setCreateBy(user.getNickname());
            baseDictItem.setActiveFlag("1");
			baseDictItem.setGmtCreate(new Date());
			Resp resp =  baseDictItemService.create(baseDictItem);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 删除记录（逻辑删除active_flag=0)
	   * @param baseDictItem
	   * @return
	   */
		@PostMapping("d")
		public ResponseResult delete(BaseDictItem baseDictItem) {
			Resp resp =  baseDictItemService.delete(baseDictItem);
			ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}

	  /**
	   * 查询列表
	   * @return
	   */
		@PostMapping("listItem")
		public ResponseResult list(@RequestParam(name = "currentPage", defaultValue = "1") int page,
                            @RequestParam(name = "numberOnePage", defaultValue = "10") int rows,BaseDictItem baseDictItem) {
			Pager<BaseDictItem> resp = baseDictItemService.findListByCondition(page,rows,baseDictItem);
            if (resp.getRecords().size()>0) {
                //特殊校验是否为主表ParentId=1(bu隐藏返回)
                resp.getRecords().get(0).setParentId(resp.getRecords().get(0).getDicTypeCode());
                //设置级别level 1
                for (BaseDictItem baseDictItemLevel : resp.getRecords()) {
                    baseDictItemLevel.setLevel("2");
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
			BaseDictItem  baseDictItem = baseDictItemService.findById(id);
			return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, baseDictItem);
		}

	  /**
	   * 修改记录
	   * @param baseDictItem
	   * @return
	   */
		@PostMapping("u")
		public ResponseResult update(BaseDictItem baseDictItem) {
			//获取登录名，插入用
			User user = userBusiness.getCurrentUser();
			baseDictItem.setLastModifiedBy(user.getNickname());
			baseDictItem.setGmtModified(new Date());
			Resp resp =  baseDictItemService.update(baseDictItem);
	    	ResponseResult result = new ResponseResult(resp.getCode(),resp.getMsg(),null);
			return result;
		}
        /**
         * 物理删除
         * @return
         */
        @RequestMapping(value = "/deleteDictItem", method = RequestMethod.POST)
        @ResponseBody
        public ResponseResult deleteDictItem(String dictItemId) {
            //响应结果对象
            ResponseResult responseResult = null;
            Resp resp = baseDictItemService.deleteDictItem(dictItemId);

            responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), null);
            return responseResult;
        }
	@GetMapping("getDictType/{code}")
	public ResponseResult getDictType(@PathVariable String code) {
		List<BaseDictItem> baseDictItemList = baseDictItemService.getDictTypeByCode(code);
		return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, baseDictItemList);
	}

}
