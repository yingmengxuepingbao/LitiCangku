package com.penghaisoft.framework.permissionmanagement.controller;

import com.penghaisoft.framework.authorization.SecurityManager;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.permissionmanagement.model.bussiness.IResourceBusiness;
import com.penghaisoft.framework.permissionmanagement.model.entity.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/resourceManagement")
public class ResourceController {

    @Autowired
    IResourceBusiness resourceBusiness;

    @Autowired
    private SecurityManager securityManager;

    /**
     * 获取资源树
     */
    /*@HasAllPermissions("retrieveMenu")*/
    @RequestMapping(value = "/getAllResourceList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getAllResourcesList() {
        //声明返回对象
        ResponseResult responseResult = null;

        //获取资源
        List<Resources> allMenuStructure = resourceBusiness.getAllResourcesStructure();

        if (!allMenuStructure.isEmpty()) {
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, allMenuStructure);
        } else {
            responseResult = new ResponseResult(resourceBusiness.getLastErrorCode(), resourceBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 查询资源
     */
    /*@HasAllPermissions("retrieveMenu")*/
    @RequestMapping(value = "/queryResource", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult queryResource(Integer resourceId) {
        //声明返回对象
        ResponseResult responseResult = null;

        //获取资源
        Resources resources = resourceBusiness.selectByPrimaryKey(resourceId);

        if (resources != null) {
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, resources);
        } else {
            responseResult = new ResponseResult(resourceBusiness.getLastErrorCode(), resourceBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 修改资源
     */
    /* @HasAllPermissions("updateMenu,deleteMenu")*/
    @RequestMapping(value = "/updateResource", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateResource(Integer id, Integer parentId, Integer level, Integer sequence, String title, String resCode, String description, String icon, String resType, String url) {
        //声明返回对象
        ResponseResult responseResult = null;

        boolean updateMenuResult = resourceBusiness.updateResource(id, parentId, level, sequence, title, resCode, description, icon, resType, url);

        if (updateMenuResult) {
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        } else {
            responseResult = new ResponseResult(resourceBusiness.getLastErrorCode(), resourceBusiness.getLastErrorMessage(), null);
        }
//  todo 修改资源后刷新缓存
        securityManager.updateSysAuthorizationInfo();
        return responseResult;
    }

    /**
     * 添加资源
     *
     * @return
     */
    @RequestMapping(value = "/addResource", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult addResource(Integer parentId, Integer level, Integer sequence, String title, String resCode, String description, String icon, String resType, String url) {
        //声明返回对象
        ResponseResult responseResult = null;

        int result = resourceBusiness.addResource(parentId, level, sequence, title, resCode, description, icon, resType, url);

        if (result > 0) {
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        } else {
            responseResult = new ResponseResult(resourceBusiness.getLastErrorCode(), resourceBusiness.getLastErrorMessage(), null);
        }

        securityManager.updateSysAuthorizationInfo();
        return responseResult;

    }

    /**
     * 删除资源
     *
     * @param resourceId 资源id
     * @return
     */
    @RequestMapping(value = "/deleteResource", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteMenu(Integer resourceId) {
        //声明返回对象
        ResponseResult responseResult = null;

        boolean updateMenuResult = resourceBusiness.deleteResource(resourceId);

        if (updateMenuResult) {
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        } else {
            responseResult = new ResponseResult(resourceBusiness.getLastErrorCode(), resourceBusiness.getLastErrorMessage(), null);
        }

        securityManager.updateSysAuthorizationInfo();
        return responseResult;

    }

    /**
     * 获取所有图标
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAllMenuIcons", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getAllMenuIcons(HttpServletRequest request){
        //声明返回对象
        ResponseResult responseResult = null;

        String projectName = request.getContextPath();

        List<String> allMenuIcons = resourceBusiness.getAllMenuIcons(projectName);

        if(allMenuIcons != null){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,allMenuIcons);
        }else{
            responseResult = new ResponseResult(resourceBusiness.getLastErrorCode(),resourceBusiness.getLastErrorMessage(),null);
        }

        return responseResult;
    }

}
