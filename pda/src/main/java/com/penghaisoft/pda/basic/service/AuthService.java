package com.penghaisoft.pda.basic.service;

import com.penghaisoft.pda.basic.model.Resources;
import com.penghaisoft.pda.basic.model.User;
import com.penghaisoft.pda.common.Resp;

import java.util.List;

public interface AuthService {

    /**
     * 登录检查
     * @param user
     * @return
     */
    public Resp checkLogin(User user);

    /**
     * 查询用户对用的手持资源
     * @param userId
     * @return
     */
    public List<Resources> queryUserHandResources(Integer userId);
}
