package com.penghaisoft.framework.basics.model.business.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.annotation.OperationLogAspect;
import com.penghaisoft.framework.basics.model.business.IDepartmentBusiness;
import com.penghaisoft.framework.basics.model.dao.IDepartmentDao;
import com.penghaisoft.framework.basics.model.dao.IDepartmentRoleMapper;
import com.penghaisoft.framework.basics.model.entity.Department;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.exception.AggregateException;
import com.penghaisoft.framework.permissionmanagement.model.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Class:部门管理相关业务实现
 *
 * @author 秦超
 * 2018/2/6
 */
@Slf4j
@Service
public class DepartmentBusinessImpl implements IDepartmentBusiness {

    @Resource
    private IDepartmentDao iDepartmentDao;

    @Resource
    private IDepartmentRoleMapper departmentRoleMapper;

    //保存错误码
    private String errorCodes;
    //保存错误描述
    private String errorMessages;

    /**
     * 获取部门结构树
     * @return 部门结构树
     */
    @Override
    public List<Department> getDepartmentStructure() {

        this.errorCodes = Constant.RESULT.SUCCESS.code;
        this.errorMessages = Constant.RESULT.SUCCESS.message;
        try {
            return getNextLevelDepartment(0);
        }catch (Exception e){
            log.error(errorMessages, e);

            return null;
        }
    }

    /**
     * 获取下级所属部门列表
     * @param parentId 上级部门id
     * @return
     */
    private List<Department> getNextLevelDepartment(Integer parentId){
        try {
            //1.查询当前所属部门列表
            Department department = new Department(parentId, 0);
            List<Department> departments = iDepartmentDao.getDepartments(department);

            //2.递归获取查询下属部门列表结构
            List<Department> resultDepartments = new ArrayList<>();

            for (Department d : departments){
                d.setSubDepartment(getNextLevelDepartment(d.getDepartmentId()));
                resultDepartments.add(d);
            }
            if(resultDepartments.isEmpty()){
                return null;
            }
            return resultDepartments;
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;

            throw e;
        }
    }

    /**
     * 保存部门结构树
     * @param departmentTree 部门结构树信息
     * @return
     */
    @Override
    @OperationLogAspect(operationName = "部门结构顺序调整", operationType = "1")
    public boolean saveDepartmentStructure(String departmentTree) {
        //1.转换数据格式
        JSONArray departmentTreeJson = new JSONArray();
        try {
            departmentTreeJson = JSONArray.parseArray(departmentTree);
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATA_FORMAT_ERROR.code;
            this.errorMessages = Constant.RESULT.DATA_FORMAT_ERROR.message;

            log.error(errorMessages, e);
            return false;
        }
        //递归保存部门排序信息
        try {
            resetDepartmentSequence(departmentTreeJson);
        }catch (Exception e){
            log.error(errorMessages, e);

            return false;
        }

        return true;
    }

    /**
     * 部门结构重排序
     * @param departmentTreeJson 部门结构树
     */
    private void resetDepartmentSequence(JSONArray departmentTreeJson){

        if(departmentTreeJson == null) return;
        //循环保存部门排序信息
        try{
            for (int i = 0; i < departmentTreeJson.size(); i++){
                JSONObject departmentJson = departmentTreeJson.getJSONObject(i);

                //1.保存当前部门排序码
                Department department = new Department();
                department.setDepartmentId(departmentJson.getInteger("id"));
                department.setSequence(i+1);

                iDepartmentDao.updateSequence(department);

                //2.保存下属部门排列顺序
                resetDepartmentSequence(departmentJson.getJSONArray("subDepartment"));
            }
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATA_FORMAT_ERROR.code;
            this.errorMessages = Constant.RESULT.DATA_FORMAT_ERROR.message;

            throw e;
        }
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
    @Override
    @OperationLogAspect(operationName = "新增部门", operationType = "4")
    public boolean addDepartment(String departmentCode, String departmentName, Integer departmentType, Integer parentId, String departmentDescription) {
        try {
            //构造部门实体
            Department department = new Department(0,departmentCode,departmentName,parentId,departmentType,departmentDescription);
            //查询部门编码是否可用
            if(iDepartmentDao.checkDepartmentCodeAvailable(department)){
                this.errorCodes = Constant.RESULT.DEPARTMENT_CODE_EXIST.code;
                this.errorMessages = Constant.RESULT.DEPARTMENT_CODE_EXIST.message;
                return false;
            }else {
                //新增部门信息
                return iDepartmentDao.addDepartment(department);
            }
        }catch (AggregateException ex){
            //构造方法错误信息处理
            List<Exception> exceptionList = ex.getInnerExceptions();
            StringBuilder errorList = new StringBuilder();
            for(Exception e : exceptionList){
                errorList.append(e.getMessage()).append(",");
            }
            errorCodes = Constant.RESULT.DATA_FORMAT_ERROR.code;
            errorMessages = errorList.substring(0, errorList.length()-1);
            return false;
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return false;
        }

    }

    /**
     * 获取部门信息列表
     * @param parentId 上级所属部门id
     * @param departmentType 部门属性
     * @return
     */
    @Override
    @OperationLogAspect(operationName = "查看部门列表", operationType = "4")
    public List<Department> getDepartments(Integer parentId, Integer departmentType) {
        this.errorCodes = Constant.RESULT.SUCCESS.code;
        this.errorMessages = Constant.RESULT.SUCCESS.message;
        try {
            //如果参数为空,则返回所有部门信息
            if(null==parentId && null==departmentType){
                return iDepartmentDao.getAllDepartments();
            }else{
                Department department = new Department(parentId, departmentType);
                return iDepartmentDao.getDepartments(department);
            }
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return null;
        }
    }

    /**
     * 校验部门编码是否可用
     * @param departmentId 部门id
     * @param departmentCode 部门编码
     * @return
     */
    @Override
    public boolean checkDepartmentCodeAvailable(Integer departmentId, String departmentCode) {
        Department department = new Department(departmentId, departmentCode);

        try {
            boolean result = iDepartmentDao.checkDepartmentCodeAvailable(department);
            if(result){
                this.errorCodes = Constant.RESULT.DEPARTMENT_CODE_EXIST.code;
                this.errorMessages = Constant.RESULT.DEPARTMENT_CODE_EXIST.message;
                return false;
            }else {
                return true;
            }
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return false;
        }

    }

    /**
     * 获取部门详细信息
     * @param departmentId 部门id
     * @return
     */
    @Override
    public Department getDepartmentInfo(Integer departmentId) {
        this.errorCodes = Constant.RESULT.SUCCESS.code;
        this.errorMessages = Constant.RESULT.SUCCESS.message;
        try {
            return iDepartmentDao.getDepartmentInfo(departmentId);
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return null;
        }
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
    @Override
    @OperationLogAspect(operationName = "修改部门信息", operationType = "4")
    public boolean editDepartment(Integer departmentId, String departmentCode, String departmentName, Integer departmentType, Integer parentId, String departmentDescription) {
        try {
            //构造修改部门信息实体
            Department department = new Department(departmentId, departmentCode, departmentName, parentId, departmentType, departmentDescription);
            //查询部门编码是否可用
            if(iDepartmentDao.checkDepartmentCodeAvailable(department)){

                this.errorCodes = Constant.RESULT.DEPARTMENT_CODE_EXIST.code;
                this.errorMessages = Constant.RESULT.DEPARTMENT_CODE_EXIST.message;
                return false;
            }else {
                //修改部门信息
                iDepartmentDao.editDepartment(department);
            }

        }catch (AggregateException ex){
            //构造方法错误信息处理
            List<Exception> exceptionList = ex.getInnerExceptions();
            StringBuilder errorList = new StringBuilder();
            for(Exception e : exceptionList){
                errorList.append(e.getMessage()).append(",");
            }
            errorCodes = Constant.RESULT.DATA_FORMAT_ERROR.code;
            errorMessages = errorList.substring(0, errorList.length()-1);

            return false;
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return false;
        }
        return true;
    }

    /**
     * 删除部门
     * @param departmentId 部门id
     * @return
     */
    @Override
    @OperationLogAspect(operationName = "删除部门", operationType = "4")
    public boolean deleteDepartment(Integer departmentId) {
        try {
            //校验是否有下属部门
            if(iDepartmentDao.checkDepartmentHasChildren(departmentId)){
                this.errorCodes = Constant.RESULT.DEPARTMENT_HAS_CHILDREN.code;
                this.errorMessages = Constant.RESULT.DEPARTMENT_HAS_CHILDREN.message;

                return false;
            }
//            else if(iDepartmentDao.checkDepartmentPost(departmentId)) {//校验是否有岗位
//                this.errorCodes = Constant.RESULT.DEPARTMENT_HAS_POST.code;
//                this.errorMessages = Constant.RESULT.DEPARTMENT_HAS_POST.message;
//
//                return false;
//            }
            else {
                //删除部门数据
                iDepartmentDao.deleteDepartment(departmentId);
                return true;
            }
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return false;
        }
    }

    /**
     * 获取错误码
     * @return
     */
    @Override
    public String getErrorCode(){
        return errorCodes;
    }

    /**
     * 获取错误信息
     * @return
     */
    @Override
    public String getErrorMessage(){
        return errorMessages;
    }

    /**
     * 获取部门下的岗位/角色
     *
     * @param departmentId
     * @return
     */
    @Override
    public List<Role> getPostsUnderDepartment(Integer departmentId) {
        List<Role> roles = departmentRoleMapper.selectRolesUnderDepartment(departmentId);
        return roles;
    }
}
