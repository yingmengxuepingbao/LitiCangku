package com.penghaisoft.pda.basic.model;

import lombok.Data;

@Data
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

}
