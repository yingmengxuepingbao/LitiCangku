package com.penghaisoft.framework.permissionmanagement.controller;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.permissionmanagement.model.bussiness.IPostManagementBusiness;
import com.penghaisoft.framework.permissionmanagement.model.bussiness.IRoleBusiness;
import com.penghaisoft.framework.permissionmanagement.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 岗位管理
 */
@RestController
@RequestMapping(value="/postManagement")
public class PostController {

    @Autowired
    private IPostManagementBusiness postManagementBusiness;

    @Autowired
    private IRoleBusiness roleBusiness;

//    获取组织树/basicInfoManagement/departmentManagement/getDepartmentStructure

//    根据一个部门id显示下面的岗位

    /**
     * 添加岗位
     * @param departmentId 部门ID
     * @param postName 岗位名称
     * @param postCode 岗位编码
     */
    @RequestMapping(value = "/addPost",method = RequestMethod.POST)
    public ResponseResult addPost(Integer departmentId, @RequestParam(required = false,name = "roleId",defaultValue = "0") String roleId, @RequestParam(required = false) String postName, @RequestParam(required = false)String postCode){
        boolean result = true;
        //响应结果对象
        ResponseResult responseResult;
        Integer rid = Integer.valueOf(roleId);
        if (rid==0){
//        检查编码是否重复
            result = roleBusiness.checkRoleNameNotExist(postCode);
        }
        if (result){
            result = postManagementBusiness.addPost(departmentId, rid,postName, postCode);

            if(result){
                responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
            }else{
                responseResult = new ResponseResult(
                        postManagementBusiness.getLastErrorCode(),
                        postManagementBusiness.getLastErrorMessage(),
                        null);
            }
        }else {
            responseResult = new ResponseResult(
                    roleBusiness.getLastErrorCode(),
                    roleBusiness.getLastErrorMessage(),
                    null);

        }

        return responseResult;
    }

    /**
     * 编辑岗位信息
     * @param id 岗位ID
     * @param postName 岗位名称
     * @return
     */
    @RequestMapping("/editPostInfo")
    public ResponseResult editPostInfo(Integer id, String postName){

        //响应结果对象
        ResponseResult responseResult;

        boolean result = roleBusiness.editRole(id,postName);;

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        }else{
            responseResult = new ResponseResult(
                    roleBusiness.getLastErrorCode(),
                    roleBusiness.getLastErrorMessage(),
                    null);
        }

        return responseResult;
    }

    /**
     * 删除岗位
     * @param departmentId 部门ID
     * @param postId 岗位ID
     * @return
     */
    @RequestMapping("/deletePost")
    public ResponseResult deletePost(Integer departmentId,Integer postId){
        //响应结果对象
        ResponseResult responseResult;

        boolean result = postManagementBusiness.deletePost(departmentId,postId);

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        }else{
            responseResult = new ResponseResult(
                    postManagementBusiness.getLastErrorCode(),
                    postManagementBusiness.getLastErrorMessage(),
                    null);
        }

        return responseResult;
    }

    /**
     * 根据岗位ID获取岗位详情
     * @param id 岗位ID
     * @return
     */
    @RequestMapping("/getPostInfo")
    public ResponseResult getPostInfo(Integer id){

        //响应结果对象
        ResponseResult responseResult;

        Role role = roleBusiness.queryRoleInfo(id);

        responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, role);

        return responseResult;
    }

}
