package com.penghaisoft.framework.menumanagement.controller;

import com.alibaba.fastjson.JSONArray;
import com.penghaisoft.framework.authorization.SecurityManager;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * 菜单管理controller
 * @author 秦超
 * 2017-09-06 15:01:46
 */
@Controller
@RequestMapping("/menuManagement")
public class MenuManagementController {
    //权限组件注入
    @Autowired
    private SecurityManager securityManager;
	

	/**
	 * 获取用户访问菜单结构
	 * @author 秦超
	 * 2017-09-06 16:18:49
	 */
	@RequestMapping(value = "/getUserMenuList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult getUserMenuList(){
		//声明返回对象
		ResponseResult responseResult = null;
//		todo 没有根据系统区分
		String menuStr = securityManager.getUserMenuList();
		if(menuStr!=null&&!menuStr.equals("")){
			JSONArray menuArr = JSONArray.parseArray(menuStr);
			responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message,menuArr);
		}else{
			responseResult = new ResponseResult(RESULT.MENUMANAGEMENT_MENU_TABLE_EMPTY.code,RESULT.MENUMANAGEMENT_MENU_TABLE_EMPTY.message,null);
		}
		
		return responseResult;
	}
	

}

