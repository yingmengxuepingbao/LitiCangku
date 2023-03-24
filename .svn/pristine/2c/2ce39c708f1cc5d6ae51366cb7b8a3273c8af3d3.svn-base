package com.penghaisoft.framework.usermanagement.model.dao;

import com.penghaisoft.framework.usermanagement.model.entity.UserRoleKey;

public interface UserRoleMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);

    /**
     * 根据角色id删除用户角色关系
     * @param roleId
     * @return
     */
    int deleteByRoleId(Integer roleId);
}