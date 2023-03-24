package com.penghaisoft.framework.usermanagement.model.business;

import com.alibaba.fastjson.JSON;
import com.penghaisoft.framework.authorization.SecurityManager;
import com.penghaisoft.framework.authorization.constant.SecurityManagerConstant;
import com.penghaisoft.framework.distributedsession.IHttpSession;
import com.penghaisoft.framework.annotation.OperationLogAspect;
import com.penghaisoft.framework.constant.Constant.ConfigInfo;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.exception.AggregateException;
import com.penghaisoft.framework.log.model.business.ILoginLogBusiness;
import com.penghaisoft.framework.usermanagement.model.dao.UserMapper;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.usermanagement.model.entity.UserSelectCondition;
import com.penghaisoft.framework.util.PageNumberTransfermation;
import com.penghaisoft.framework.util.VerificationCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户管理
 *
 * @author 刘立华
 * @date 2017-08-29
 */
@Slf4j
@Service
public class UserBusiness implements IUserBusiness {

    @Autowired
    private UserMapper userMapper;

    //授权信息管理器
    @Autowired
    private SecurityManager securityManager;

    //Http session
    @Autowired
    private IHttpSession httpSession;

    //登录日志
    @Autowired
    private ILoginLogBusiness loginLogBusiness;

    //保存错误码
    private List<String> errorCodes = new ArrayList<>();

    //保存错误描述
    private List<String> errorMessages = new ArrayList<>();

    /**
     * 生成验证码
     *
     * @param
     * @return VerificationCode 验证码对象
     */
    @Override
    public VerificationCode getVerificationCode() {

        //生成验证码对象
        VerificationCode vCode = new VerificationCode(120, 40, 4, 30);

//		try {
//			//获取当前session
//			session = HttpSession.getCurrentSession();
//		    //session中设置verificationCode属性值
//		    session.setString("verificationCode", vCode.getCode());
//		} catch (Exception e) {
//			logger.error("业务抛错：", e);
//
//			return null;
//		}

        return vCode;
    }

    /**
     * 用户登录
     *
     * @param loginIP               登录IP
     * @param account               账号
     * @param password              密码
     * @param inputVerificationCode 输入验证码
     * @return User 对象
     */
    @Override
    public Map<String, Object> login(String loginIP, String account, String password, String inputVerificationCode) {

        if (account == null || account == "" || password == null || password == "") {
            this.addErrorCode(RESULT.USERMANAGEMENT_PARAMETER.code);
            this.addErrorMessage(RESULT.USERMANAGEMENT_PARAMETER.message);
            return null;
        }
        Map<String, Object> result = null;
        //用户数据实体
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(ConfigInfo.USER_STATUS_ENABLED);
        user = userMapper.selectUser(user);
        if (user != null) {
            result = new HashMap<>();
            String token = securityManager.login(user.getId().toString());
            result.put("userInfo", user);
            result.put("token", token);
            //放入用户信息，baseController中使用
            httpSession.setString(token, ConfigInfo.SESSION_KEY_USER, JSON.toJSONString(user));
            //添加登录日志
            loginLogBusiness.addLoginLog(user.getId(), user.getAccount(), user.getNickname(), loginIP);
        } else {//用户名或密码错误
            this.addErrorCode(RESULT.USERMANAGEMENT_USER_PASSWORD.code);
            this.addErrorMessage(RESULT.USERMANAGEMENT_USER_PASSWORD.message);
        }

        return result;
    }

    /**
     * 用户注销
     *
     * @return void
     */
    @Override
    public void logout() {

        securityManager.logout();

        return;
    }

    /**
     * 根据用户id查询用户
     *
     * @param userId
     * @return
     */
    public User selectByPrimaryKey(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 获取用户列表
     *
     * @param currentPage：当前页号
     * @return 用户列表
     */
    @Override
    @OperationLogAspect(operationName = "查看用户列表", operationType = "1")
    public List<User> getUserList(int currentPage,int numberOnePage, String account, String nickname, String phone, Integer status, Integer departmentId, Integer roleId) {
        //返回值
        List<User> userList = null;

        //初始化查询起始条数为-1：表示查询所有用户列表
        int startNumber = -1;
        if (currentPage != -1) {
            //获取sql查询起始条数：按照起始条数查询
            startNumber = PageNumberTransfermation.pageNumberToSelectStartNumber(currentPage,numberOnePage);
        }

        UserSelectCondition userSelectCondition = new UserSelectCondition(startNumber, numberOnePage, account, nickname, phone, status, departmentId, roleId);

        //返回符合条件的所有用户列表，此时没有分页
        userList = userMapper.selectUsers(userSelectCondition);

        //按照查询的条数返回
        if(numberOnePage*currentPage>userList.size()){
            userList = userList.subList(startNumber,userList.size());
        }else {
            userList = userList.subList(startNumber,numberOnePage*currentPage);
        }

        return userList;
    }

    /**
     * 获取用户总数
     *
     * @return Integer：用户总数
     */
    @Override
    @OperationLogAspect(operationName = "获取用户总数", operationType = "1")
    public Integer getUserTotalCount(String account, String nickname, String phone, Integer status, Integer departmentId, Integer roleId) {
        UserSelectCondition userSelectCondition = new UserSelectCondition(-1, -1, account, nickname, phone, status, departmentId, roleId);

        return userMapper.selectUserTotalCount(userSelectCondition);
    }

    /**
     * 修改用户信息
     *
     * @param userId   用户Id
     * @param nickname 昵称
     * @param email    邮箱
     * @param phone    手机号
     * @return void
     */
    @Override
    @OperationLogAspect(operationName = "修改用户信息", operationType = "1")
    public boolean updateUser(Integer userId, String nickname, String email, String phone, String officePhone, String entryDate, String description, String departmentIds, String roleIds) {
        boolean result = false;

        if (userId != null && userId <= 0 || nickname == null || nickname == "") {
            this.addErrorCode(RESULT.USERMANAGEMENT_PARAMETER.code);
            this.addErrorMessage(RESULT.USERMANAGEMENT_PARAMETER.message);
        } else {
            User user = new User();
            user.setId(userId);
            user = userMapper.selectUser(user);
            if (user == null) {//用户名不存在
                this.addErrorCode(RESULT.USERMANAGEMENT_PARAMETER.code);
                this.addErrorMessage(RESULT.USERMANAGEMENT_PARAMETER.message);
            } else {//更新用户信息
                user.setNickname(nickname);
                user.setEmail(email);
                user.setPhone(phone);
                user.setOfficePhone(officePhone);
                user.setEntryDate(entryDate);
                user.setDescription(description);
                userMapper.updateUser(user);

                //如果部门不为空，则删除旧关联关系，插入新关联关系
                if(!StringUtils.isEmpty(departmentIds)) {
                    List<Integer> departmentIdList = new ArrayList<>();
                    for (String str : departmentIds.split(",")) {
                        int i = Integer.valueOf(str);
                        departmentIdList.add(i);
                    }
                    user.setDepartmentIds(departmentIdList);
                    userMapper.deleteDepartmentsByUserId(userId);
                    userMapper.insertDepartmentsForUser(user);
                }else{
                    //部门为空，则删除旧关联关系
                    userMapper.deleteDepartmentsByUserId(userId);
                }

                //如果岗位不为空，则删除旧关联关系，插入新关联关系
                if(!StringUtils.isEmpty(roleIds)) {
                    List<Integer> roleIdList = new ArrayList<>();
                    for (String str : roleIds.split(",")) {
                        int i = Integer.valueOf(str);
                        roleIdList.add(i);
                    }
                    user.setRoleIds(roleIdList);
                    userMapper.deleteRolesByUserId(userId);
                    userMapper.insertRolesForUser(user);
                }else {
                    userMapper.deleteRolesByUserId(userId);
                }
                result = true;
            }
        }

        return result;
    }

    /**
     * 修改密码
     *
     * @param userId      用户Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return void
     */
    @Override
    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
        boolean result = false;

        if (userId == null || userId <= 0 || oldPassword == null || oldPassword == "" || newPassword == null || newPassword == "") {
            this.addErrorCode(RESULT.USERMANAGEMENT_PARAMETER.code);
            this.addErrorMessage(RESULT.USERMANAGEMENT_PARAMETER.message);
        } else {
            User user = new User();
            user.setId(userId);
            user.setPassword(oldPassword);
            user = userMapper.selectUser(user);
            if (user == null) {//用户名或密码不正确
                this.addErrorCode(RESULT.USERMANAGEMENT_USER_PASSWORD.code);
                this.addErrorMessage(RESULT.USERMANAGEMENT_USER_PASSWORD.message);
            } else {//设置新密码
                user.setPassword(newPassword);
                user.setCreatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                userMapper.updateUser(user);
                result = true;
            }
        }

        return result;
    }

    /**
     * 重置密码
     *
     * @param userId 用户Id
     * @return void
     */
    @Override
    @OperationLogAspect(operationName = "重置用户密码", operationType = "1")
    public boolean resetPassword(Integer userId) {
        boolean result = false;

        if (userId == null || userId <= 0) {
            this.addErrorCode(RESULT.USERMANAGEMENT_PARAMETER.code);
            this.addErrorMessage(RESULT.USERMANAGEMENT_PARAMETER.message);
        } else {
            User user = new User();
            user.setId(userId);
            user = userMapper.selectUser(user);
            if (user == null) {//用户名不存在
                this.addErrorCode(RESULT.USERMANAGEMENT_USER_NOT_EXIST.code);
                this.addErrorMessage(RESULT.USERMANAGEMENT_USER_NOT_EXIST.message);
            } else {//重置密码
                user.setPassword(ConfigInfo.INITIAL_PASSWORD);
                user.setCreatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                userMapper.updateUser(user);
                result = true;
            }
        }

        return result;
    }

    /**
     * 添加用户
     *
     * @param account  账号
     * @param nickname 昵称
     * @param email    邮箱
     * @param phone    手机号
     * @return void
     */
    @Override
    @OperationLogAspect(operationName = "新增用户", operationType = "1")
    public int addUser(String account, String nickname, String email, String phone, String officePhone, String entryDate, String description, String departmentIds, String roleIds) {
        int result = 0;

        User user = null;
        try {
            String password = ConfigInfo.INITIAL_PASSWORD;
            user = new User(1, account, password, nickname, email, ConfigInfo.USER_STATUS_ENABLED);
        } catch (AggregateException e) {
            this.addErrorCode(RESULT.USERMANAGEMENT_PARAMETER.code);
            this.addErrorMessage(RESULT.USERMANAGEMENT_PARAMETER.message);

            log.error("业务抛错：" + e);
            return 0;
        }

        user.setId(null);
        user.setPassword(null);
        user.setNickname(null);
        user.setEmail(null);
        user.setPhone(null);
        user.setStatus(ConfigInfo.USER_STATUS_ENABLED);
        user.setOfficePhone(null);
        user.setEntryDate(null);
        user.setDescription(null);
        user = userMapper.selectUser(user);
        if (user != null) {//用户名存在
            this.addErrorCode(RESULT.USERMANAGEMENT_USER_EXIST.code);
            this.addErrorMessage(RESULT.USERMANAGEMENT_USER_EXIST.message);
        } else {//添加用户
            user = new User();
            user.setId(null);
            user.setAccount(account);
            user.setPassword(ConfigInfo.INITIAL_PASSWORD);
            user.setNickname(nickname);
            user.setEmail(email);
            user.setPhone(phone);
            user.setStatus(ConfigInfo.USER_STATUS_ENABLED);
            user.setOfficePhone(officePhone);
            user.setEntryDate(entryDate);
            user.setDescription(description);
            user.setCreatDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            userMapper.insertUser(user);
            result = user.getId();
            if (!StringUtils.isEmpty(departmentIds)) {
                List<Integer> departmentIdList = new ArrayList<>();
                for (String str : departmentIds.split(",")) {
                    int i = Integer.valueOf(str);
                    departmentIdList.add(i);
                }
                user.setDepartmentIds(departmentIdList);
                userMapper.insertDepartmentsForUser(user);
            }
            if (!StringUtils.isEmpty(roleIds)) {
                List<Integer> roleIdList = new ArrayList<>();
                for (String str : roleIds.split(",")) {
                    int i = Integer.valueOf(str);
                    roleIdList.add(i);
                }
                user.setRoleIds(roleIdList);
                userMapper.insertRolesForUser(user);
            }
        }

        return result;
    }

    /**
     * 删除用户
     *
     * @param userId 用户Id
     * @return void
     */
    @Override
    @OperationLogAspect(operationName = "删除用户", operationType = "1")
    public boolean deleteUser(Integer userId) {
        boolean result = false;

        if (userId == null || userId <= 0) {
            this.addErrorCode(RESULT.USERMANAGEMENT_PARAMETER.code);
            this.addErrorMessage(RESULT.USERMANAGEMENT_PARAMETER.message);
        } else {
            User user = new User();
            user.setId(userId);
            user = userMapper.selectUser(user);
            if (user == null) {//用户不存在
                this.addErrorCode(RESULT.USERMANAGEMENT_USER_NOT_EXIST.code);
                this.addErrorMessage(RESULT.USERMANAGEMENT_USER_NOT_EXIST.message);
            } else {//删除用户
                userMapper.deleteUser(userId);
                userMapper.deleteDepartmentsByUserId(userId);
                userMapper.deleteRolesByUserId(userId);
                result = true;
            }
        }

        return result;
    }

    /**
     * 停用用户
     *
     * @param userId 用户Id
     * @return void
     */
    @Override
    @OperationLogAspect(operationName = "停用用户", operationType = "1")
    public boolean disableUser(Integer userId) {
        boolean result = false;

        if (userId == null || userId <= 0) {
            this.addErrorCode(RESULT.USERMANAGEMENT_PARAMETER.code);
            this.addErrorMessage(RESULT.USERMANAGEMENT_PARAMETER.message);
        } else {
            User user = new User();
            user.setId(userId);
            user = userMapper.selectUser(user);
            if (user == null) {//用户不存在
                this.addErrorCode(RESULT.USERMANAGEMENT_USER_NOT_EXIST.code);
                this.addErrorMessage(RESULT.USERMANAGEMENT_USER_NOT_EXIST.message);
            } else {//停用用户
                user.setStatus(ConfigInfo.USER_STATUS_DISABLED);
                userMapper.updateUser(user);
                result = true;
            }
        }

        return result;
    }

    /**
     * 启用用户
     *
     * @param userId 用户Id
     * @return void
     */
    @Override
    @OperationLogAspect(operationName = "启用用户", operationType = "1")
    public boolean enableUser(Integer userId) {
        boolean result = false;

        if (userId == null || userId <= 0) {
            this.addErrorCode(RESULT.USERMANAGEMENT_PARAMETER.code);
            this.addErrorMessage(RESULT.USERMANAGEMENT_PARAMETER.message);
        } else {
            User user = new User();
            user.setId(userId);
            user = userMapper.selectUser(user);
            if (user == null) {//用户不存在
                this.addErrorCode(RESULT.USERMANAGEMENT_USER_NOT_EXIST.code);
                this.addErrorMessage(RESULT.USERMANAGEMENT_USER_NOT_EXIST.message);
            } else {//停用用户
                user.setStatus(ConfigInfo.USER_STATUS_ENABLED);
                userMapper.updateUser(user);
                result = true;
            }
        }

        return result;
    }

    /**
     * 添加错误码
     *
     * @param errorCode 错误码
     * @return
     */
    public void addErrorCode(String errorCode) {
        errorCodes.add(errorCode);
    }

    /**
     * 添加错误信息
     *
     * @param errorMessage 错误信息
     * @return
     */
    public void addErrorMessage(String errorMessage) {
        errorMessages.add(errorMessage);
    }

    /**
     * 获取最后一个错误码
     *
     * @return 错误码
     */
    public String getLastErrorCode() {
        if (!errorCodes.isEmpty()) {
            return errorCodes.get(errorCodes.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 获取最后一条错误信息
     *
     * @return 错误信息
     */
    public String getLastErrorMessage() {
        if (!errorMessages.isEmpty()) {
            return errorMessages.get(errorMessages.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 获取当前登录用户信息
     */
    @Override
    public User getCurrentUser() {
        User currentUser = new User();

        try {
            String userIdString = httpSession.getString(SecurityManagerConstant.USER_ID);
            if (userIdString != null && userIdString != "null") {
                int userId = Integer.parseInt(userIdString);
                currentUser.setId(userId);
                currentUser = userMapper.selectUser(currentUser);
            } else {
                errorCodes.add(RESULT.USERMANAGEMENT_CURRENT_NOT_EXIST.code);
                errorMessages.add(RESULT.USERMANAGEMENT_CURRENT_NOT_EXIST.message);

                return null;
            }
        } catch (Exception e) {
            errorCodes.add(RESULT.USERMANAGEMENT_CURRENT_NOT_EXIST.code);
            errorMessages.add(RESULT.USERMANAGEMENT_CURRENT_NOT_EXIST.message);

            log.error("业务抛错：", e);
            return null;
        }
        //3.记录登录用户操作日志
        return currentUser;
    }

}
