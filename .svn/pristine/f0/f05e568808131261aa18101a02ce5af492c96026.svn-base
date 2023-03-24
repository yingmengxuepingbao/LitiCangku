package com.penghaisoft.framework.usermanagement.model.entity;

import com.penghaisoft.framework.basics.model.entity.Department;
import com.penghaisoft.framework.constant.Constant.ConfigInfo;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.exception.AggregateException;
import com.penghaisoft.framework.permissionmanagement.model.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * User model
 *
 * @author 刘立华
 * @date 2017-08-25
 */
public class User {
    private Integer id; //用户id
    private String account; //账号
    private String password; //密码
    private String nickname; //昵称
    private String phone; //手机号
    private String officePhone; //办公电话
    private String email; //邮箱
    private Integer status; //用户状态,0：正常，1：停用
    private String entryDate; //入职日期
    private String description; //描述
    private List<Integer> departmentIds; //部门Id
    private List<Integer> roleIds; //角色Id（岗位）
    private List<Department> departmentList; //部门
    private List<Role> roleList; //部门
    private String creatDate;//创建时间-更新时间
    public User() {
        this.id = 0;
        this.account = "";
        this.password = "";
        this.nickname = "";
        this.phone = "";
        this.officePhone = "";
        this.email = "";
        this.status = null;
        this.entryDate = null;
        this.description = "";
        this.creatDate = null;
    }

    public User(Integer id, String account, String password, String nickname, String email, Integer status) throws AggregateException {

        AggregateException aggregateException = new AggregateException();

        if (id != null && id >= 0) {
            this.id = id;
        } else {
            aggregateException.addException(new Exception(RESULT.USERMANAGEMENT_USER_CONSTRUCTOR_ID.message));
        }

        if (account != null && account != "") {
            this.account = account;
        } else {
            aggregateException.addException(new Exception(RESULT.USERMANAGEMENT_USER_CONSTRUCTOR_USERNAME.message));
        }

        if (password != null && password != "") {
            this.password = password;
        } else {
            aggregateException.addException(new Exception(RESULT.USERMANAGEMENT_USER_CONSTRUCTOR_PASSWORD.message));
        }

        if (nickname != null && nickname != "") {
            this.nickname = nickname;
        } else {
            aggregateException.addException(new Exception(RESULT.USERMANAGEMENT_USER_CONSTRUCTOR_NICKNAME.message));
        }

        if (status != null) {
            this.status = status;
        } else {
            aggregateException.addException(new Exception(RESULT.USERMANAGEMENT_USER_CONSTRUCTOR_STATUS.message));
        }

        if (email == null) {
            email = "";
        }
        this.email = email;

        if (aggregateException.count() > 0) {
            throw aggregateException;
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(List<Integer> departmentIds) {
        this.departmentIds = departmentIds;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }
}
