package com.penghaisoft.framework.basics.model.dao;

import com.penghaisoft.framework.basics.model.entity.DepartmentType;

import java.util.List;

/**
 * Interface:部门属性信息数据处理
 *
 * @author 秦超
 * 2018/1/30
 */
public interface IDepartmentTypeDao {
    /**
     * 获取部门属性列表信息
     * @return 部门属性列表
     */
    List<DepartmentType> getDepartmentTypes();

    /**
     * 根据id获取部门属性信息
     * @param id 属性id
     * @return 部门属性对象
     */
    DepartmentType getDepartmentType(int id);

    /**
     * 新增部门属性项
     * @param name 属性名
     */
    boolean addDepartmentTypes(String name);

    /**
     * 编辑修改部门属性信息
     * @param departmentType 属性对象
     */
    boolean editDepartmentTypes(DepartmentType departmentType);

    /**
     * 删除部门属性项
     * @param id 属性id
     */
    boolean deleteDepartmentTypes(int id);

    /**
     * 判断部门属性信息在数据库中是否存在
     * @param departmentType 属性对象（id参数不传则为0）
     * @return （出当前id外）是否存在相同部门属性信息
     */
    boolean checkDepartmentTypeAvailable(DepartmentType departmentType);
}
