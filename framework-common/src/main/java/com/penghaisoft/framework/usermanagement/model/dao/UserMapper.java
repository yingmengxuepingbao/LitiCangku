package com.penghaisoft.framework.usermanagement.model.dao;

import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.usermanagement.model.entity.UserSelectCondition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据访问Mapper
 *
 * @author 刘立华
 * @Date 2017-08-25
 */
@Repository
//@Mapper
public interface UserMapper {

    /**
     * 查询一条用户信息
     *
     * @param user：User实体
     * @return 用户信息
     */
    User selectUser(User user);

    /**
     * 查询用户列表
     *
     * @param userSelectCondition: 查询条件实体
     * @return 用户信息列表
     */
    List<User> selectUsers(UserSelectCondition userSelectCondition);

    /**
     * 根据用户id查询用户
     *
     * @param userId: 用户id
     * @return 用户信息列表
     */
    User selectByPrimaryKey(Integer userId);

    /**
     * 查询用户总数
     *
     * @param userSelectCondition: 查询条件实体
     * @return Integer：用户总数
     */
    Integer selectUserTotalCount(UserSelectCondition userSelectCondition);

    /**
     * 更新用户信息
     *
     * @param user 用户model
     * @return 无
     */
    void updateUser(User user);

    /**
     * 添加用户
     *
     * @param user 用户model
     * @return 添加用户
     */
    int insertUser(User user);

    /**
     * 删除用户
     *
     * @param userId 用户Id
     * @return 删除用户
     */
    void deleteUser(Integer userId);

    /**
     * 插入用户对应部门
     *
     * @param user
     * @return
     */
    int insertDepartmentsForUser(User user);

    /**
     * 插入用户对应角色
     *
     * @param user
     * @return
     */
    int insertRolesForUser(User user);

    /**
     * 删除用户对应部门
     *
     * @param userId
     * @return
     */
    int deleteDepartmentsByUserId(Integer userId);

    /**
     * 删除用户对应角色
     *
     * @param userId
     * @return
     */
    int deleteRolesByUserId(Integer userId);


}
