package com.penghaisoft.framework.permissionmanagement.model.dao;

import com.penghaisoft.framework.permissionmanagement.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 插入新的角色信息
     *
     * @param name(角色名称) , String description(角色描述)
     * @return int(大于0为操作成功)
     */
    int insertRole(@Param("name") String name, @Param("description") String description);

    /**
     * 根据角色id删除角色
     *
     * @param roleId
     * @return
     */
    int deleteRoleByRoleId(Integer roleId);

    /**
     * 根据角色id修改角色描述
     *
     * @param roleId
     * @param roleDescription
     * @return
     */
    int editRoleDescriptionByRoleId(@Param("id") int roleId, @Param("description") String roleDescription);

    /**
     * 获取所有角色及其资源
     *
     * @return
     */
    List<Role> getAllRoles();
}