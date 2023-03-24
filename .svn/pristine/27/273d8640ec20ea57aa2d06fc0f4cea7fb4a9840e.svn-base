package com.penghaisoft.pda.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.model.BasePdaVersion;
import com.penghaisoft.pda.basic.service.BasicDataService;
import com.penghaisoft.pda.common.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description SystemController
 * @Auther zhangxu
 * @Date 2020/1/16 17:36
 **/
@RestController
@RequestMapping("sys")
public class SystemController {

    @Autowired
    private BasicDataService basicDataService;

    @GetMapping("/ping")
    public JSONObject ping(){
        return CommonUtil.genSucessResult();
    }

    /**
      * @Description 获取pda版本信息
      * @Author luot
      * @Date 2020/8/6 18:21
      * @Param
      * @return
      **/
    @GetMapping("/get/pda/version")
    public JSONObject versionCheck(){
        List<BasePdaVersion> list = basicDataService.getPdaVersionInfo();
        BasePdaVersion rtnData = new BasePdaVersion();
        if(list != null && !list.isEmpty()){
            rtnData = list.get(0);
        }

        return CommonUtil.genSucessResultWithData(rtnData);
    }
}
