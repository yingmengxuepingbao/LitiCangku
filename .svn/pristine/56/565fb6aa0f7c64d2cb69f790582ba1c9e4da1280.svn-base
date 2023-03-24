package com.penghaisoft.wms.nuohua.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.wms.nuohua.service.FourwaycarForNHService;
import com.penghaisoft.wms.util.PostResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 四向车
 **/
@Slf4j
@Service("fourwaycarForNHService")
public class FourwaycarForNHServiceImpl implements FourwaycarForNHService {
    //发送请求公共方法
    @Autowired
    public PostResponseUtil postResponseUtil;
    @Value("${fourwaycar.sys-url}")
    private String fourwaycarBaseUrl;


    /**
     *功能描述: 入库异常时，将货物送至交接位后上报异常
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject fourwaycarError(String str){
        log.info("==========调入库异常-上报异常: 任务接收接口==============");
        String ret_str= postResponseUtil.postString(fourwaycarBaseUrl+"/Api/Project/Beijing/Novartis/FourwaycarError", str);
        System.out.println("调入库异常-上报异常: 任务接收接口=====返回的数据："+ret_str);
        JSONObject jsonObject =JSONObject.parseObject(ret_str);
        return jsonObject;
    }


}
