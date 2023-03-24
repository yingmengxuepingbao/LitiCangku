package com.penghaisoft.framework.util.controller;

import com.alibaba.fastjson.JSON;
import com.penghaisoft.framework.distributedsession.IHttpSession;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    public IHttpSession httpSession;

    public User getCurrentUserInfo(){
        User user = null;
        String userString = httpSession.getString(Constant.ConfigInfo.SESSION_KEY_USER);
        if (userString!=null){
            user = JSON.parseObject(userString,User.class);
        }
        return user;
    }

}
