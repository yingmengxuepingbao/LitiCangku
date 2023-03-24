package com.penghaisoft.framework.basics.model.entity;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.exception.AggregateException;

import java.util.List;

/**
 * 部门实体
 * @author 秦超
 * 2018-01-30
 */
public class Department {

    //部门id
    Integer departmentId;
    //部门编码
    String departmentCode;
    //上级部门id
    String departmentName;
    //部门名称
    Integer parentId;
    //部门属性id
    Integer departmentType;
    //部门属性名
    String departmentTypeName;
    //部门排序
    Integer sequence;
    //部门备注描述
    String departmentDescription;
    //下级所属部门列表
    List<Department> subDepartment;

    public Department(){}

    /**
     * 校验部门编号是否可用实体构造方法
     * @param departmentId
     * @param departmentCode
     */
    public Department(Integer departmentId, String departmentCode){
        if(departmentId == null){
            this.departmentId = 0;
        }else {
            this.departmentId = departmentId;
        }
        this.departmentCode = departmentCode;
    }

    /**
     * 查询部门列表条件实体构造方法
     * @param parentId
     * @param departmentType
     */
    public Department(Integer parentId, Integer departmentType){
        if(parentId ==null){
            this.parentId = 0;
        }else {
            this.parentId = parentId;
        }
        if(departmentType == null){
            this.departmentType = 0;
        }else {
            this.departmentType = departmentType;
        }
    }

    /**
     * 新增、修改部门信息实体构造函数
     * @param departmentId 部门id
     * @param departmentCode 部门编码
     * @param departmentName 部门名称
     * @param parentId 上级部门id
     * @param departmentType 部门属性id
     * @param departmentDescription 部门备注
     */
    public Department(Integer departmentId, String departmentCode, String departmentName, Integer parentId, Integer departmentType, String departmentDescription) throws AggregateException {
        //异常聚合对象
        AggregateException aggregateException = new AggregateException();

        if(departmentCode != null){
            this.departmentCode = departmentCode;
        }else {
            aggregateException.addException(new Exception(Constant.RESULT.DEPARTMENT_CODE_EMPTY.message));
        }
        if(departmentName != null){
            this.departmentName = departmentName;
        }else {
            aggregateException.addException(new Exception(Constant.RESULT.DEPARTMENT_NAME_EMPTY.message));
        }
        if(parentId != null){
            this.parentId = parentId;
        }else {
            aggregateException.addException(new Exception(Constant.RESULT.DEPARTMENT_PARENT_ID_EMPTY.message));
        }
        if(departmentType != null){
            this.departmentType = departmentType;
        }else {
            aggregateException.addException(new Exception(Constant.RESULT.DEPARTMENT_TYPE_EMPTY.message));
        }

        if(0 == departmentId){
            this.sequence = Integer.parseInt(String.valueOf(System.currentTimeMillis()/10000));
        }

        this.departmentId = departmentId;
        this.departmentDescription = departmentDescription;

        //给调用者抛出异常信息
        if(aggregateException.count() > 0)
            throw aggregateException;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(Integer departmentType) {
        this.departmentType = departmentType;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getDepartmentDescription() {
        return departmentDescription;
    }

    public void setDepartmentDescription(String departmentDescription) {
        this.departmentDescription = departmentDescription;
    }

    public List<Department> getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(List<Department> subDepartment) {
        this.subDepartment = subDepartment;
    }

    public String getDepartmentTypeName() {
        return departmentTypeName;
    }

    public void setDepartmentTypeName(String departmentTypeName) {
        this.departmentTypeName = departmentTypeName;
    }
}
