package com.penghaisoft.framework.permissionmanagement.model.bussiness;


import com.penghaisoft.framework.permissionmanagement.model.entity.Role;

import java.util.List;

/**
 * 角色管理接口类
 *
 * @author 徐超
 * @Date 2017年9月7日 下午1:52:10
 */
public interface IRoleBusiness {

    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    Role queryRoleInfo(Integer roleId);

    /**
     * 新增角色
     *
     * @param roleName        角色名称
     * @param roleDescription 角色描述
     * @return true--保存成功  false--保存失败
     * @author 徐超
     * @Date 2017年9月7日 下午1:43:07
     */
    boolean addRole(String roleName, String roleDescription);

    /**
     * 检查角色名称是否不存在
     *
     * @param roleName 角色名称
     * @return true--角色名称不存在  false--角色名称存在
     * @author 徐超
     * @Date 2017年9月7日 下午1:45:16
     */
    boolean checkRoleNameNotExist(String roleName);

    /**
     * 编辑角色
     *
     * @param roleId          角色ID
     * @param roleDescription 角色描述
     * @return true--编辑成功 false--编辑失败
     * @author 徐超
     * @Date 2017年9月7日 下午1:45:39
     */
    boolean editRole(int roleId, String roleDescription);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return true--删除成功 false--删除失败
     * @author 徐超
     * @Date 2017年9月7日 下午1:45:57
     */
    boolean deleteRole(int roleId);

    /**
     * 获取角色资源列表
     */
    List<Role> getRolesAndResources();

    /**
     * 保存角色权限关系
     *
     * @return true--保存成功 false--保存失败
     */
    boolean saveRolesAndResources(String roleId, String resourceIds);


    /**
     * 获取最近一条错误码
     *
     * @return 角色业务逻辑执行过程中抛出的最后一个错误的错误码
     * @author 徐超
     * @Date 2017年9月7日 下午2:34:17
     */
    String getLastErrorCode();

    /**
     * 获取最近一条错误信息
     *
     * @return 角色业务逻辑执行过程中抛出的最后一个错误的错误信息
     * @author 徐超
     * @Date 2017年9月7日 下午2:34:22
     */
    String getLastErrorMessage();


}
