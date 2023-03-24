package com.penghaisoft.framework.permissionmanagement.model.bussiness;

import com.penghaisoft.framework.basics.model.dao.IDepartmentRoleMapper;
import com.penghaisoft.framework.basics.model.entity.DepartmentRoleKey;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.permissionmanagement.model.dao.RoleMapper;
import com.penghaisoft.framework.permissionmanagement.model.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 岗位管理服务
 */
@Slf4j
@Service
public class PostManagementBusinessImpl implements IPostManagementBusiness{

    //保存错误码
    private List<String> errorCodes = new ArrayList<>();
    //保存错误描述
    private List<String> errorMessages = new ArrayList<>();

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private IRoleBusiness roleBusiness;

    @Autowired
    private IDepartmentRoleMapper departmentRoleMapper;
    /**
     * 新增部门下的岗位
     *
     * @param departmentId 部门ID
     * @param roleId
     * @param postName 岗位名称
     * @param postCode 岗位编码
     * @return
     */
    @Transactional
    @Override
    public boolean addPost(Integer departmentId, Integer roleId, String postName, String postCode) {
        try{
            if (roleId==0){

                //构造角色实体
                Role role = new Role();
                role.setName(postCode);
                role.setDescription(postName);
                role.setCreatedTime(new Date());
                //添加岗位/角色
                roleMapper.insertSelective(role);
                roleId = role.getId();
            }
            //保存岗位和部门关系
            DepartmentRoleKey departmentRoleKey = new DepartmentRoleKey();
            departmentRoleKey.setDepartmentId(departmentId);
            departmentRoleKey.setRoleId(roleId);
            departmentRoleMapper.insert(departmentRoleKey);
            return true;
        }catch (Exception e){
            log.error(Constant.RESULT.POST_ADD_DATABASE_ERROR.message, e);
            this.errorCodes.add(Constant.RESULT.POST_ADD_DATABASE_ERROR.code);
            this.errorMessages.add(Constant.RESULT.POST_ADD_DATABASE_ERROR.message);
            return false;
        }
    }

    /**
     * 删除岗位
     *
     * @param postId
     * @return
     */

    /**
     * 删除岗位
     * @param departmentId 部门ID
     * @param postId
     * @return
     */
    @Transactional
    @Override
    public boolean deletePost(Integer departmentId,Integer postId){
//        删除岗位+岗位关联的资源
        boolean flag = roleBusiness.deleteRole(postId);
        if (flag){
//            删除部门下的岗位
            DepartmentRoleKey departmentRoleKey = new DepartmentRoleKey();
            departmentRoleKey.setDepartmentId(departmentId);
            departmentRoleKey.setRoleId(postId);
            departmentRoleMapper.deleteByPrimaryKey(departmentRoleKey);
        }else {
            this.errorCodes.add(roleBusiness.getLastErrorCode());
            this.errorMessages.add(roleBusiness.getLastErrorMessage());
        }
        return flag;
    }

    /**
     * 获取最近一条错误码
     * @return 角色业务逻辑执行过程中抛出的最后一个错误的错误码
     */
    @Override
    public String getLastErrorCode() {
        if(!errorCodes.isEmpty()){
            return errorCodes.get(errorCodes.size() - 1);
        }else{
            return null;
        }
    }

    /**
     * 获取最近一条错误信息
     * @return 角色业务逻辑执行过程中抛出的最后一个错误的错误信息
     */
    @Override
    public String getLastErrorMessage() {
        if(!errorMessages.isEmpty()){
            return errorMessages.get(errorMessages.size() - 1);
        }else{
            return null;
        }
    }
}
