package com.penghaisoft.wms.nuohua.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.wms.nuohua.service.WcsForNHService;
import com.penghaisoft.wms.util.PostResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author zhangxin
 * @Date 2022
 **/
@Slf4j
@Service("wcsForHBService")
public class WcsForNHServiceImpl implements WcsForNHService {
    //发送请求公共方法
    @Autowired
    public PostResponseUtil postResponseUtil;
    @Value("${wcs.sys-url}")
    private String wcsBaseUrl;


    public JSONObject taskReceive(String str){
        log.info("==========调WCS - 启动 : 任务接收接口==============");
        String ret_str= postResponseUtil.postString(wcsBaseUrl+"/fromWms/taskReceive", str);
        System.out.println("调WCS - 启动 : 任务接收接口=====返回的数据："+ret_str);
        JSONObject jsonObject =JSONObject.parseObject(ret_str);
        return jsonObject;
    }


}
