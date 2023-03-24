package com.penghaisoft.pda.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description BaseController
 * @Auther zhangxu
 * @Date 2019/12/27 11:10
 **/
@Slf4j
public class BaseController {

    @Autowired
    private HttpServletResponse response;

    /**
     * 允许跨域
     */
    @ModelAttribute
    public void allowCros(){
        log.info("允许跨域");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, api_key, Authorization");
    }

}
