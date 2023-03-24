package com.penghaisoft.framework.usermanagement.model.entity;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户模块查询条件实体
 *
 * @author 刘立华
 * @date 2017-09-26
 */
public class UserSelectCondition {

    private int startNumber; //查询起始位置
    private int numberOnePage; //每页数据条数
    private int id; //用户ID
    private String account; //账号
    private String password; //密码
    private String nickname; //昵称
    private String officePhone; //办公电话
    private String phone; //手机号
    private String email; //邮箱
    private Integer status; //用户状态,0：正常，1：停用
    private String entryDate; //入职日期
    private String description; //描述
    /*private List<Integer> departmentIds; //部门
    private List<Integer> roleIds; //角色（岗位）*/
    private Integer departmentId; //部门（查询用）
    private Integer roleId; //角色（查询用）


    /**
     * 构建用户查询条件实体对象
     *
     * @author 刘立华
     * @date 2017-09-26
     */
    public UserSelectCondition(int startNumber, int numberOnePage, String account, String nickname, String phone, Integer status, Integer departmentId, Integer roleId) {
        this.startNumber = startNumber;
        this.numberOnePage = numberOnePage;
        this.account = account;
        this.nickname = nickname;
        this.phone = phone;
        this.status = status;
        this.departmentId = departmentId;
        this.roleId = roleId;
		/*List<Integer> departmentIdList = new ArrayList<>();
		List<Integer> roleIdList = new ArrayList<>();
		if(!StringUtils.isEmpty(departmentIds )){
			for(String str :departmentIds.split(",") ) {
				int i = Integer.valueOf(str);
				departmentIdList.add(i);
			}}
		if (!StringUtils.isEmpty(roleIds )){
			for(String str : roleIds.split(",")) {
				int i = Integer.valueOf(str);
				roleIdList.add(i);
			}}
		this.departmentIds = departmentIdList;
		this.roleIds = roleIdList;*/
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public int getNumberOnePage() {
        return numberOnePage;
    }

    public void setNumberOnePage(int numberOnePage) {
        this.numberOnePage = numberOnePage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
