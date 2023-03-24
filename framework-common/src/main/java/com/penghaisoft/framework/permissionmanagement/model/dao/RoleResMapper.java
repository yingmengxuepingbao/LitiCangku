package com.penghaisoft.framework.permissionmanagement.model.dao;

import com.penghaisoft.framework.permissionmanagement.model.entity.RoleResKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleResMapper {
    int deleteByPrimaryKey(RoleResKey key);

    int insert(RoleResKey record);

    int insertSelective(RoleResKey record);

    //插入角色资源关系
    int insertRoleResourceList(@Param("list") List<RoleResKey> roleResourceList);

    //按照角色或者资源删除关联关系
    int deleteByRoleIdOrResourceId(@Param("roleId") Integer roleId, @Param("resourceId") Integer resourceId);

}