package com.penghaisoft.framework.basics.controller;

import com.penghaisoft.framework.basics.model.business.IDepartmentBusiness;
import com.penghaisoft.framework.basics.model.business.IDepartmentTypeBusiness;
import com.penghaisoft.framework.basics.model.entity.Department;
import com.penghaisoft.framework.basics.model.entity.DepartmentType;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.permissionmanagement.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 部门管理请求控制
 * @author 秦超
 * 2018-01-30
 */
@RestController
@RequestMapping("/basicInfoManagement/departmentManagement")
public class DepartmentController {

    @Autowired
    private IDepartmentBusiness iDepartmentBusiness;
    @Autowired
    private IDepartmentTypeBusiness iDepartmentTypeBusiness;

    /**
     * 获取部门结构树
     * @return 部门结构树
     */
    @RequestMapping(value = "/getDepartmentStructure", method = RequestMethod.GET)
    public ResponseResult getDepartmentStructure(){
        //声明返回对象
        ResponseResult responseResult = null;

        List<Department> result = iDepartmentBusiness.getDepartmentStructure();

        if(result != null){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentBusiness.getErrorCode(), iDepartmentBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 保存部门结构树
     * @param departmentTree 部门结构树信息
     * @return
     */
    @RequestMapping(value = "/saveDepartmentStructure", method = RequestMethod.POST)
    public ResponseResult saveDepartmentStructure(String departmentTree){
        //声明返回对象
        ResponseResult responseResult = null;

        boolean result = iDepartmentBusiness.saveDepartmentStructure(departmentTree);

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentBusiness.getErrorCode(), iDepartmentBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 新增部门信息
     * @param departmentCode 部门编码
     * @param departmentName 部门名称
     * @param departmentType 部门属性
     * @param parentId 上级所属部门id
     * @param departmentDescription 部门备注信息
     * @return
     */
    @RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
    public ResponseResult addDepartment(String departmentCode, String departmentName, Integer departmentType, Integer parentId, @RequestParam(required=false)String departmentDescription){
        //声明返回对象
        ResponseResult responseResult = null;

        boolean result = iDepartmentBusiness.addDepartment(departmentCode, departmentName, departmentType, parentId, departmentDescription);

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentBusiness.getErrorCode(), iDepartmentBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 获取部门信息列表
     * @param parentId 上级所属部门id
     * @param departmentType 部门属性
     * @return
     */
    @RequestMapping(value = "/getDepartments", method = RequestMethod.GET)
    public ResponseResult getDepartments(Integer parentId, @RequestParam(required=false)Integer departmentType){
        //声明返回对象
        ResponseResult responseResult = null;

        List<Department> result = iDepartmentBusiness.getDepartments(parentId, departmentType);

        if(result != null){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentBusiness.getErrorCode(), iDepartmentBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 校验部门编码是否可用
     * @param departmentId 部门id
     * @param departmentCode 部门编码
     * @return
     */
    @RequestMapping(value = "/checkDepartmentCodeAvailable", method = RequestMethod.POST)
    public ResponseResult checkDepartmentCodeAvailable(@RequestParam(required=false)Integer departmentId, String departmentCode){
        //声明返回对象
        ResponseResult responseResult = null;

        boolean result = iDepartmentBusiness.checkDepartmentCodeAvailable(departmentId, departmentCode);

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentBusiness.getErrorCode(), iDepartmentBusiness.getErrorMessage(), result);
        }

        return responseResult;
    }

    /**
     * 获取部门详细信息
     * @param departmentId 部门id
     * @return
     */
    @RequestMapping(value = "/getDepartmentInfo", method = RequestMethod.POST)
    public ResponseResult getDepartmentInfo(Integer departmentId){
        //声明返回对象
        ResponseResult responseResult = null;

        Department result = iDepartmentBusiness.getDepartmentInfo(departmentId);

        if(result != null){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentBusiness.getErrorCode(), iDepartmentBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 编辑修改部门信息
     * @param departmentId 部门id
     * @param departmentCode 部门编码
     * @param departmentName 部门名称
     * @param departmentType 部门属性
     * @param parentId 上级所属部门id
     * @param departmentDescription 部门备注信息
     * @return
     */
    @RequestMapping(value = "/editDepartment", method = RequestMethod.POST)
    public ResponseResult editDepartment(Integer departmentId, String departmentCode, String departmentName, Integer departmentType, Integer parentId, @RequestParam(required=false)String departmentDescription){
        //声明返回对象
        ResponseResult responseResult = null;

        boolean result = iDepartmentBusiness.editDepartment(departmentId, departmentCode, departmentName, departmentType, parentId, departmentDescription);

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentBusiness.getErrorCode(), iDepartmentBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 删除部门
     * @param departmentId 部门id
     * @return
     */
    @RequestMapping(value = "/deleteDepartment", method = RequestMethod.POST)
    public ResponseResult deleteDepartment(Integer departmentId){
        //声明返回对象
        ResponseResult responseResult = null;

        boolean result = iDepartmentBusiness.deleteDepartment(departmentId);

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentBusiness.getErrorCode(), iDepartmentBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 获取部门属性列表信息
     * @return 部门属性列表
     * @author 秦超
     * 2018-01-30
     */
    @RequestMapping(value = "/getDepartmentTypes", method = RequestMethod.GET)
    public ResponseResult getDepartmentTypes(){
        //声明返回对象
        ResponseResult responseResult = null;

        List<DepartmentType> result = iDepartmentTypeBusiness.getDepartmentTypes();

        if(result != null){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentTypeBusiness.getErrorCode(), iDepartmentTypeBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 新增部门属性信息
     * @param typeName 部门属性名称
     * @return
     * @author 秦超
     * 2018-01-30
     */
    @RequestMapping(value = "/addDepartmentType", method = RequestMethod.POST)
    public ResponseResult addDepartmentTypes(String typeName){
        //声明返回对象
        ResponseResult responseResult = null;

        boolean result = iDepartmentTypeBusiness.addDepartmentTypes(typeName);

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentTypeBusiness.getErrorCode(), iDepartmentTypeBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 编辑修改部门属性信息
     * @param typeId 部门属性id
     * @param typeName 部门属性名称
     * @return
     * @author 秦超
     * 2018-01-30
     */
    @RequestMapping(value = "/editDepartmentTypes", method = RequestMethod.POST)
    public ResponseResult editDepartmentTypes(Integer typeId, String typeName){
        //声明返回对象
        ResponseResult responseResult = null;

        boolean result = iDepartmentTypeBusiness.editDepartmentTypes(typeId, typeName);

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentTypeBusiness.getErrorCode(), iDepartmentTypeBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 删除部门属性信息
     * @param typeId 部门属性id
     * @return
     * @author 秦超
     * 2018-01-30
     */
    @RequestMapping(value = "/deleteDepartmentTypes", method = RequestMethod.POST)
    public ResponseResult deleteDepartmentTypes(Integer typeId){
        //声明返回对象
        ResponseResult responseResult = null;

        boolean result = iDepartmentTypeBusiness.deleteDepartmentTypes(typeId);

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentTypeBusiness.getErrorCode(), iDepartmentTypeBusiness.getErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 查询部门名称是否可用
     * @param typeName 部门属性名称
     * @return
     * @author 秦超
     * 2018-01-30
     */
    @RequestMapping(value = "/checkDepartmentTypeAvailable", method = RequestMethod.POST)
    public ResponseResult checkDepartmentTypeAvailable(@RequestParam(required=false)Integer typeId, String typeName){
        //声明返回对象
        ResponseResult responseResult = null;

        boolean result = iDepartmentTypeBusiness.checkDepartmentTypeAvailable(typeId, typeName);

        if(result){
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,result);
        }else {
            responseResult = new ResponseResult(iDepartmentTypeBusiness.getErrorCode(), iDepartmentTypeBusiness.getErrorMessage(), result);
        }

        return responseResult;
    }

    /**
     * 根据部门id 获取其下的岗位/角色
     * @param departmentId
     * @return
     */
    @RequestMapping(value = "/getPostsUnderDepartment", method = RequestMethod.GET)
    public ResponseResult getPostsUnderDepartment(Integer departmentId){
        List<Role> posts = iDepartmentBusiness.getPostsUnderDepartment(departmentId);
        ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message,posts);
        return result;
    }
}
