package com.penghaisoft.framework.basics.model.business;

import com.penghaisoft.framework.basics.model.entity.Department;
import com.penghaisoft.framework.permissionmanagement.model.entity.Role;
import java.util.List;

/**
 * Interface:部门管理相关业务接口
 *
 * @author 秦超
 * 2018/2/6
 */
public interface IDepartmentBusiness {

    /**
     * 获取部门结构树
     * @return 部门结构树
     */
    List<Department> getDepartmentStructure();

    /**
     * 保存部门结构树
     * @param departmentTree 部门结构树信息
     * @return
     */
    boolean saveDepartmentStructure(String departmentTree);

    /**
     * 新增部门信息
     * @param departmentCode 部门编码
     * @param departmentName 部门名称
     * @param departmentType 部门属性
     * @param parentId 上级所属部门id
     * @param departmentDescription 部门备注信息
     * @return
     */
    boolean addDepartment(String departmentCode, String departmentName, Integer departmentType, Integer parentId, String departmentDescription);

    /**
     * 获取部门信息列表
     * @param parentId 上级所属部门id
     * @param departmentType 部门属性
     * @return
     */
    List<Department> getDepartments(Integer parentId, Integer departmentType);

    /**
     * 校验部门编码是否可用
     * @param departmentId 部门id
     * @param departmentCode 部门编码
     * @return
     */
    boolean checkDepartmentCodeAvailable(Integer departmentId, String departmentCode);

    /**
     * 获取部门详细信息
     * @param departmentId 部门id
     * @return
     */
    Department getDepartmentInfo(Integer departmentId);

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
    boolean editDepartment(Integer departmentId, String departmentCode, String departmentName, Integer departmentType, Integer parentId, String departmentDescription);

    /**
     * 删除部门
     * @param departmentId 部门id
     * @return
     */
    boolean deleteDepartment(Integer departmentId);

    /**
     * 获取错误码
     * @author 秦超
     * 2017-09-01 15:23:56
     */
    String getErrorCode();
    /**
     * 获取错误信息
     * @author 秦超
     * 2017-09-01 15:24:04
     */
    String getErrorMessage();


    /**
     * 获取部门下的岗位/角色
     * @param departmentId
     * @return
     */
    List<Role> getPostsUnderDepartment(Integer departmentId);
}
