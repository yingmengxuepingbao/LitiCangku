package com.penghaisoft.framework.basics.model.dao;

import com.penghaisoft.framework.basics.model.entity.Department;

import java.util.List;

/**
 * Interface:部门管理相关数据接口
 *
 * @author 秦超
 * 2018/2/6
 */
public interface IDepartmentDao {
    /**
     * 获取部门信息列表
     * @param department 查询条件实体
     * @return
     */
    List<Department> getDepartments(Department department);

    /**
     * 获取部门详细信息
     * @param departmentId 部门id
     * @return
     */
    Department getDepartmentInfo(int departmentId);

    /**
     * 校验部门编码是否可用
     * @param department 部门编码信息实体
     * @return
     */
    boolean checkDepartmentCodeAvailable(Department department);

    /**
     * 新增部门信息
     * @param department 新增部门信息实体
     * @return
     */
    boolean addDepartment(Department department);

    /**
     * 编辑修改部门信息
     * @param department 编辑修改部门信息实体
     * @return
     */
    boolean editDepartment(Department department);

    /**
     * 修改部门排序号
     * @param department 部门排序信息实体
     * @return
     */
    boolean updateSequence(Department department);

    /**
     * 删除部门
     * @param departmentId 部门id
     * @return
     */
    boolean deleteDepartment(int departmentId);

    /**
     * 查看当前部门是否有岗位
     * @param departmentId
     * @return
     */
    boolean checkDepartmentHasChildren(int departmentId);

    /**
     * 查看当前部门是否有岗位
     * @param departmentId
     * @return
     */
    boolean checkDepartmentPost(int departmentId);

    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartments();
}
