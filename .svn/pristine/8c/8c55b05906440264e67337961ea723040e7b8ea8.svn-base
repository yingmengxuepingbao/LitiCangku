package com.penghaisoft.framework.basics.model.dao;

import com.penghaisoft.framework.basics.model.entity.DepartmentRoleKey;
import com.penghaisoft.framework.permissionmanagement.model.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepartmentRoleMapper {
    int deleteByPrimaryKey(DepartmentRoleKey key);

    int insert(DepartmentRoleKey record);

    List<Role> selectRolesUnderDepartment(@Param("departmentId") Integer departmentId);
}