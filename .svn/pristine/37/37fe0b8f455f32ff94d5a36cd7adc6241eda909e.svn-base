package com.penghaisoft.framework.basics.model.business.impl;

import com.penghaisoft.framework.annotation.OperationLogAspect;
import com.penghaisoft.framework.basics.model.business.IDepartmentTypeBusiness;
import com.penghaisoft.framework.basics.model.dao.IDepartmentTypeDao;
import com.penghaisoft.framework.basics.model.entity.DepartmentType;
import com.penghaisoft.framework.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class:部门属性信息业务逻辑
 *
 * @author 秦超
 * 2018/1/30
 */
@Slf4j
@Service
public class DepartmentTypeBusinessImpl implements IDepartmentTypeBusiness {

    @Autowired
    private IDepartmentTypeDao iDepartmentTypeDao;

    //保存错误码
    private String errorCodes;
    //保存错误描述
    private String errorMessages;

    /**
     * 获取部门属性列表信息业务
     * @return 部门属性列表
     * @author 秦超
     * 2018-01-30
     */
    @Override
    public List<DepartmentType> getDepartmentTypes() {
        this.errorCodes = Constant.RESULT.SUCCESS.code;
        this.errorMessages = Constant.RESULT.SUCCESS.message;
        try {
            return iDepartmentTypeDao.getDepartmentTypes();
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return null;
        }
    }

    /**
     * 根据id获取部门属性信息业务
     * @param typeId 部门属性id
     * @return 部门属性列表
     * @author 秦超
     * 2018-01-30
     */
    @Override
    public DepartmentType getDepartmentType(Integer typeId) {
        this.errorCodes = Constant.RESULT.SUCCESS.code;
        this.errorMessages = Constant.RESULT.SUCCESS.message;
        try {
            return iDepartmentTypeDao.getDepartmentType(typeId);
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return null;
        }
    }

    /**
     * 新增部门属性信息接口
     * @param typeName 部门
     * @return
     * @author 秦超
     * 2018-01-30
     */
    @Override
    @OperationLogAspect(operationName = "新增部门属性", operationType = "4")
    public boolean addDepartmentTypes(String typeName) {
        try {
            DepartmentType departmentType = new DepartmentType(0, typeName);
            //校验部门属性名是否可用
            if(iDepartmentTypeDao.checkDepartmentTypeAvailable(departmentType)){
                this.errorCodes = Constant.RESULT.DEPARTMENT_TYPE_NAME_EXIST.code;
                this.errorMessages = Constant.RESULT.DEPARTMENT_TYPE_NAME_EXIST.message;

                return false;
            }else {
                //新增部门属性
                return iDepartmentTypeDao.addDepartmentTypes(typeName);
            }
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return false;
        }
    }

    /**
     * 编辑修改部门属性信息接口
     * @param typeId 部门属性id
     * @param typeName 部门
     * @return
     * @author 秦超
     * 2018-01-30
     */
    @Override
    @OperationLogAspect(operationName = "修改部门属性", operationType = "4")
    public boolean editDepartmentTypes(Integer typeId, String typeName) {
        try {
            DepartmentType departmentType = new DepartmentType(typeId, typeName);
            //部门属性是否已存在
            if(iDepartmentTypeDao.checkDepartmentTypeAvailable(departmentType)){
                this.errorCodes = Constant.RESULT.DEPARTMENT_TYPE_NAME_EXIST.code;
                this.errorMessages = Constant.RESULT.DEPARTMENT_TYPE_NAME_EXIST.message;

                return false;
            }else {
                //修改部门属性
                return iDepartmentTypeDao.editDepartmentTypes(departmentType);
            }
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return false;
        }
    }

    /**
     * 删除部门属性信息
     * @param typeId 部门属性id
     * @return
     * @author 秦超
     * 2018-01-30
     */
    @Override
    @OperationLogAspect(operationName = "删除部门属性", operationType = "4")
    public boolean deleteDepartmentTypes(Integer typeId) {
        try {
            return iDepartmentTypeDao.deleteDepartmentTypes(typeId);
        }catch (Exception e){
            this.errorCodes = Constant.RESULT.DATABASE_ERROR.code;
            this.errorMessages = Constant.RESULT.DATABASE_ERROR.message;
            log.error(errorMessages, e);

            return false;
        }
    }

    /**
     * 查询部门名称是否存在
     * @param typeName 部门名称
     * @return
     * @author 秦超
     * 2018-01-30
     */
    @Override
    public boolean checkDepartmentTypeAvailable(Integer typeId, String typeName) {

        try {
            DepartmentType departmentType = new DepartmentType(typeId, typeName);
            //部门属性是否已存在
            boolean result = iDepartmentTypeDao.checkDepartmentTypeAvailable(departmentType);
            //存在、则不可用
            if(result){
                this.errorCodes = Constant.RESULT.DEPARTMENT_TYPE_NAME_EXIST.code;
                this.errorMessages = Constant.RESULT.DEPARTMENT_TYPE_NAME_EXIST.message;
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
}
