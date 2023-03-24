package com.penghaisoft.pda.basic.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.dao.ResourcesMapper;
import com.penghaisoft.pda.basic.dao.UserMapper;
import com.penghaisoft.pda.basic.model.Resources;
import com.penghaisoft.pda.basic.model.User;
import com.penghaisoft.pda.basic.service.AuthService;
import com.penghaisoft.pda.common.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description AuthServiceImpl
 * @Auther zhangxu
 * @Date 2020/1/16 17:50
 **/
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourcesMapper resourcesMapper;
    /**
     * 登录检查
     *
     * @param user
     * @return
     */
    @Override
    public Resp checkLogin(User user) {
        Resp resp = new Resp();
        resp.setCode("1");
        user = userMapper.selectUser(user);

        if (null==user){
            resp.setCode("0");
        }else {
            JSONObject userJson = new JSONObject();
            userJson.put("nickname",user.getNickname());
            userJson.put("account",user.getAccount());
            userJson.put("userId",user.getId().toString());
            resp.setData(userJson);
        }
        return resp;
    }

    /**
     * 查询用户对用的手持资源
     *
     * @param userId
     * @return
     */
    @Override
    public List<Resources> queryUserHandResources(Integer userId) {
        List<Resources> resources = resourcesMapper.selectHandUserResources(userId);
        return resources;
    }
}