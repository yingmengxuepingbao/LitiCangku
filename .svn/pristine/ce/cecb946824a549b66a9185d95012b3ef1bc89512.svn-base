package com.penghaisoft.framework.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统接口
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Value("${framework.isOpenMail:0}")
    private String isOpenMail;

    @RequestMapping(value = "/conf", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getSystemConf(){
        JSONObject data = new JSONObject();
        data.put("isOpenMail",isOpenMail);
        ResponseResult responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, data);
        //清空当前用户的权限信息
        return responseResult;
    }
}
