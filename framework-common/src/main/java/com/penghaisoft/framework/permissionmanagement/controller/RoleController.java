package com.penghaisoft.framework.permissionmanagement.controller;


import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.datatransfer.DataTransfer;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.permissionmanagement.model.bussiness.IRoleBusiness;
import com.penghaisoft.framework.permissionmanagement.model.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色管理类
 *
 * @author 徐超
 * @Date 2017年9月7日 下午1:16:18
 */
@Controller
@RequestMapping("/permissionManagement")
public class RoleController {

    @Autowired
    private IRoleBusiness roleBusiness;

    /**
     * 查询角色
     *
     * @return
     */
    @RequestMapping(value = "/role/queryRole", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult queryRole(Integer roleId) {
        //响应结果对象
        ResponseResult responseResult = null;
        //查询角色
        Role role = roleBusiness.queryRoleInfo(roleId);

        if (role != null) {
            //返回结果
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, role);
        } else {
            //添加失败,返回对应错误码和错误信息
            responseResult = new ResponseResult(
                    roleBusiness.getLastErrorCode(),
                    roleBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 新增角色
     *
     * @param roleName        角色名称
     * @param roleDescription 角色描述
     * @return
     * @author 徐超
     * @Date 2017年9月7日 下午1:43:07
     */
    @RequestMapping(value = "/role/addRole", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult addRole(String roleName, String roleDescription) {
        //响应结果对象
        ResponseResult responseResult = null;
        //调用添加角色服务
        boolean result = roleBusiness.addRole(roleName, roleDescription);

        if (result) {
            //添加成功
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
        } else {
            //添加失败,返回对应错误码和错误信息
            responseResult = new ResponseResult(
                    roleBusiness.getLastErrorCode(),
                    roleBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 检查角色名称是否不存在
     *
     * @param roleName 角色名称
     * @return
     * @author 徐超
     * @Date 2017年9月7日 下午1:45:16
     */
    /*@HasAllPermissions("retrieveRole")*/
    @RequestMapping(value = "/role/checkRoleNameNotExist", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult checkRoleNameNotExist(String roleName) {
        //响应结果对象
        ResponseResult responseResult = null;

        //调用校验角色名称是否重复服务
        boolean result = roleBusiness.checkRoleNameNotExist(roleName);

        if (result) {
            //表示角色名称不重复
            responseResult = new ResponseResult(
                    RESULT.SUCCESS.code,
                    RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_AVAILABLE.message, null);
        } else {
            //角色名称重复
            responseResult = new ResponseResult(
                    roleBusiness.getLastErrorCode(),
                    roleBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 编辑角色
     *
     * @param roleId          角色ID
     * @param roleDescription 角色描述
     * @return
     * @author 徐超
     * @Date 2017年9月7日 下午1:45:39
     */
    /*@HasAllPermissions("updateRole")*/
    @RequestMapping(value = "/role/editRole", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult editRole(int roleId, String roleDescription) {
        //响应结果对象
        ResponseResult responseResult = null;

        //调用编辑角色服务
        boolean result = roleBusiness.editRole(roleId, roleDescription);

        if (result) {
            //编辑成功
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
        } else {
            //编辑失败,返回错误信息
            responseResult = new ResponseResult(
                    roleBusiness.getLastErrorCode(),
                    roleBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     * @author 徐超
     * @Date 2017年9月7日 下午1:45:57
     */
    /*@HasAllPermissions("deleteRole")*/
    @RequestMapping(value = "/role/deleteRole", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteRole(int roleId) {
        //响应结果对象
        ResponseResult responseResult = null;

        //调用根据权限ID删除权限服务
        boolean result = roleBusiness.deleteRole(roleId);

        if (result) {
            //表示删除成功
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
        } else {
            //删除失败,返回错误提示
            responseResult = new ResponseResult(
                    roleBusiness.getLastErrorCode(),
                    roleBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 获取角色资源
     */
    /*@HasAllPermissions("retrieveRolePermissionRelationships,retrievePermission")*/
    @RequestMapping(value = "/role/getRolesAndResources", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getRolesAndResources() {
        //响应结果对象
        ResponseResult responseResult = null;

        //调用获取角色列表服务
        List<Role> roles = roleBusiness.getRolesAndResources();

        if (!roles.isEmpty()) {
            //角色列表不为空,返回角色列表获取成功提示
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, roles);
        } else {

            //角色列表为空,返回错误提示
            responseResult = new ResponseResult(
                    roleBusiness.getLastErrorCode(),
                    roleBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 保存角色对应的资源
     * <br>示例:
     * <pre>
     * {"roles":[{"roleId":"1","resouceIds":["1","2","3"]},{"roleId":"2","resouceIds":["2","5","7"]}]}
     * </pre>
     *
     * @return
     */
    /*@HasAllPermissions("updateRolePermissionRelationships,deleteRolePermissionRelationships,createRolePermissionRelationships")*/
    @RequestMapping(value = "/role/saveRolesAndResources", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult saveRolesAndResources(String roleId, String resourceIds) {
        //响应结果对象
        ResponseResult responseResult = null;

        //调用保存角色与权限关系业务逻辑
        boolean result = roleBusiness.saveRolesAndResources(roleId, resourceIds);

        if (result) {
            //保存成功,返回保存成功提示
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
        } else {
            //保存失败,返回错误提示
            responseResult = new ResponseResult(
                    roleBusiness.getLastErrorCode(),
                    roleBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }


}
