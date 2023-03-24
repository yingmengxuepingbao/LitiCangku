package com.penghaisoft.framework.permissionmanagement.model.bussiness;

import com.penghaisoft.framework.authorization.SecurityManager;
import com.penghaisoft.framework.annotation.OperationLogAspect;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.permissionmanagement.model.dao.RoleMapper;
import com.penghaisoft.framework.permissionmanagement.model.dao.RoleResMapper;
import com.penghaisoft.framework.permissionmanagement.model.entity.Role;
import com.penghaisoft.framework.permissionmanagement.model.entity.RoleResKey;
import com.penghaisoft.framework.usermanagement.model.dao.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 角色管理接口实现类
 *
 * @author 徐超
 * @Date 2017年9月7日 下午2:48:24
 */
@Service
public class RoleBusinessImpl implements IRoleBusiness {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleResMapper roleResMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    //保存错误码
    private List<String> errorCodes = new ArrayList<>();

    //保存错误描述
    private List<String> errorMessages = new ArrayList<>();


    /**
     * 获取角色信息
     *
     * @param roleId
     * @return
     */
    @Override
    public Role queryRoleInfo(Integer roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
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
    @Override
    @OperationLogAspect(operationName = "新增角色信息", operationType = "3")
    public boolean addRole(String roleName, String roleDescription) {

        int resultValue = 0;

        if ("".equals(roleName)) {
            //判断角色名称是否为空,若为空则添加错误信息并返回false
            errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_NULL_ERROR.code);
            errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_NULL_ERROR.message);
            return false;
        } else if ("".equals(roleDescription)) {
            //判断角色描述是否为空,若为空则添加错误信息并返回false
            errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_DESCRIPTION_NULL_ERROR.code);
            errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_DESCRIPTION_NULL_ERROR.message);
            return false;
        }
        //添加角色服务
        resultValue = roleMapper.insertRole(roleName, roleDescription);
        if (resultValue < 0) {
            //返回错误表示权限名称重复,保存对应错误码和错误信息
            errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_DUPLICATE.code);
            errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_DUPLICATE.message);
        }
        return resultValue > 0 ? true : false;
    }

    /**
     * 检查角色名称是否不存在
     *
     * @param roleName 角色名称
     * @return
     * @author 徐超
     * @Date 2017年9月7日 下午1:45:16
     */
    @Override
    public boolean checkRoleNameNotExist(String roleName) {

        boolean result = true;

        //1 先校验角色名称合法性
        if (!"".equals(roleName) && null != roleName) {
            //1.1 校验角色名称是否由英文字符组成
//			if(FormatCheck.checkStringIsMadeUpOfLetter(roleName)){
            //2 如果合法,调用权限组件获取所有角色列表
            List<Role> roleList = roleMapper.getAllRoles();
            //2.1 遍历所有角色列表,比对是否存在相同的角色名称
            for (Role role : roleList) {
                //2.2 如果角色名称已存在则跳出循环
                if (roleName.equals(role.getName())) {
                    //角色名称已存在,保存对应错误码和错误信息
                    errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_DUPLICATE.code);
                    errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_DUPLICATE.message);
                    result = false;
                    break;
                }
            }
//			}else{
//				//角色名称不是由英文字符组成,保存对应错误码和错误信息
//				errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_NOT_ALL_LETTER_ERROR.code);
//				errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_NOT_ALL_LETTER_ERROR.message);
//				result = false;
//			}
        } else {
            //角色名称为空,保存对应错误码和错误信息
            errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_NULL_ERROR.code);
            errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_NULL_ERROR.message);
            result = false;
        }

        return result;
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
    @Override
    @OperationLogAspect(operationName = "修改角色信息", operationType = "3")
    public boolean editRole(int roleId, String roleDescription) {

        int resultValue = 0;

        if ("".equals(roleDescription)) {
            //判断权限描述是否为空,若为空则添加错误信息并返回false
            errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_DESCRIPTION_NULL_ERROR.code);
            errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_DESCRIPTION_NULL_ERROR.message);
            return false;
        }
        //调用权限组件编辑角色描述
        resultValue = roleMapper.editRoleDescriptionByRoleId(roleId, roleDescription);
        if (resultValue < 0) {
            //编辑失败,保存错误信息
            errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_EDIT_FAILED.code);
            errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_EDIT_FAILED.message);
        }

        return resultValue > 0 ? true : false;
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return
     * @author 徐超
     * @Date 2017年9月7日 下午1:45:57
     */
    @Override
    @OperationLogAspect(operationName = "删除角色信息", operationType = "3")
    public boolean deleteRole(int roleId) {

        int resultValue = 0;

        //删除角色资源关系
        roleResMapper.deleteByRoleIdOrResourceId(roleId, null);
        userRoleMapper.deleteByRoleId(roleId);
        resultValue = roleMapper.deleteRoleByRoleId(roleId);

        if (resultValue < 0) {
            //删除失败,保存错误信息
            errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_DELETE_FAILED.code);
            errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_DELETE_FAILED.message);
        }

        return resultValue > 0 ? true : false;
    }

    /**
     * 获取角色资源列表
     */
    @Override
    @OperationLogAspect(operationName = "查看角色权限信息列表", operationType = "2")
    public List<Role> getRolesAndResources() {

        //调用权限组件获取所有角色列表
        List<Role> roleList = roleMapper.getAllRoles();

        //若角色列表返回为空,给出相应提示
        if (roleList.isEmpty()) {
            errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_LIST_NULL.code);
            errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_LIST_NULL.message);
            return Collections.emptyList();
        }

        return roleList;
    }

    /**
     * 保存角色资源关系
     *
     * @param roleId 角色id,resourceIds 资源ids
     * @return
     */
    @Override
    @OperationLogAspect(operationName = "修改角色权限信息", operationType = "2")
    public boolean saveRolesAndResources(String roleId, String resourceIds) {

        int resultValue = 0;
        List<RoleResKey> roleResKeyList = new ArrayList<>();
        if (!StringUtils.isEmpty(resourceIds) && !StringUtils.isEmpty(roleId)) {
            for (String resourceId : resourceIds.split(",")) {
                RoleResKey roleResKey = new RoleResKey();
                roleResKey.setRoleId(Integer.parseInt(roleId));
                roleResKey.setResId(Integer.parseInt(resourceId));
                roleResKeyList.add(roleResKey);
            }
            roleResMapper.deleteByRoleIdOrResourceId(Integer.valueOf(roleId), null);
            resultValue = roleResMapper.insertRoleResourceList(roleResKeyList);
        }

        if (resultValue < 0) {
            //如果更新过程中有错误,保存错误信息
            errorCodes.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_SAVE_ERROR.code);
            errorMessages.add(RESULT.PERMISSIONMANAGEMENT_ROLE_ROLE_SAVE_ERROR.message);
        }
        return resultValue > 0 ? true : false;
    }


    /**
     * 获取最近一条错误码
     *
     * @return 角色业务逻辑执行过程中抛出的最后一个错误的错误码
     * @author 徐超
     * @Date 2017年9月7日 下午2:34:17
     */
    @Override
    public String getLastErrorCode() {
        if (!errorCodes.isEmpty()) {
            return errorCodes.get(errorCodes.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 获取最近一条错误信息
     *
     * @return 角色业务逻辑执行过程中抛出的最后一个错误的错误信息
     * @author 徐超
     * @Date 2017年9月7日 下午2:34:17
     */
    @Override
    public String getLastErrorMessage() {
        if (!errorMessages.isEmpty()) {
            return errorMessages.get(errorMessages.size() - 1);
        } else {
            return null;
        }
    }

}
