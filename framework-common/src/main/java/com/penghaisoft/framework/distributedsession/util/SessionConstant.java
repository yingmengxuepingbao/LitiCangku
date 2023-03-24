package com.penghaisoft.framework.distributedsession.util;

public class SessionConstant {

    //定义sessionName常量
    public static final String SESSION_NAME = "com.penghaisoft.distributedSession";
    //定义租户存储常量
    public static final String TENANCY_NAME = "com.penghaisoft.tenancyCode";
    //session长度,长度为32位
    public static final int SESSIONID_LEN = 32;
    //默认expiredTime,值为20,单位为分钟
    public static final int DEFAULT_EXPIRED_TIME = 20;
    //redis中创建hashset时的key值
    public static final String HSET_FIRST_KEY = "HttpSession.createdTime";
    //日期格式
    public static final String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    //定义是否登录
    public static final String LOGIN_FLAG = "login";
}
