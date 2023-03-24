package com.penghaisoft.framework.usermanagement.model.business;

import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.VerificationCode;

import java.util.List;
import java.util.Map;

/**
 * 用户管理接口类
 *
 * @author 刘立华
 * @date 2017-08-25
 */
public interface IUserBusiness {

    //获取验证码
    VerificationCode getVerificationCode();

    //登录
    Map<String, Object> login(String loginIP, String userName, String password, String inputVerificationCode);

    /**
     * 根据用户id获取用户
     *
     * @param userId
     * @return
     */
    User selectByPrimaryKey(Integer userId);

    //注销
    void logout();

    //获取用户列表
    List<User> getUserList(int currentPage,int numberOnePage, String account, String nickname, String phone, Integer status, Integer departmentId, Integer roleId);

    //获取用户总数
    public Integer getUserTotalCount(String account, String nickname, String phone, Integer status, Integer departmentId, Integer roleId);

    //修改用户信息
    boolean updateUser(Integer userId, String nickname, String email, String phone, String officePhone, String entryDate, String description, String departmentIds, String roleIds);

    //修改密码
    boolean changePassword(Integer userId, String oldPassword, String newPassword);

    //重置密码
    boolean resetPassword(Integer userId);

    //添加用户
    int addUser(String userName, String nickname, String email, String phone, String officePhone, String entryDate, String description, String departmentIds, String roleIds);

    //删除用户
    boolean deleteUser(Integer userId);

    //停用用户
    boolean disableUser(Integer userId);

    //启用用户
    boolean enableUser(Integer userId);

    //添加错误码
    void addErrorCode(String errorCode);

    //添加错误信息
    void addErrorMessage(String errorCode);

    //获取最近一条错误码
    String getLastErrorCode();

    //获取最近一条错误信息
    String getLastErrorMessage();

    //获取当前登录用户信息
    User getCurrentUser();
}
