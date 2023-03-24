package com.penghaisoft.pda.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.model.Resources;
import com.penghaisoft.pda.basic.model.User;
import com.penghaisoft.pda.basic.service.AuthService;
import com.penghaisoft.pda.basic.service.BasicDataService;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description DemoController
 * @Auther zhangxu
 * @Date 2019/12/27 11:02
 **/
@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private BasicDataService basicDataService;

    /**
     * 获取系统的库区信息，以仓库为单位
     * @return
     */
    @GetMapping("/areas")
    public JSONObject getAreaList(){
        List<String> areas = basicDataService.getRootAreaCodeByWarehouse();
        JSONObject result = null;
        if (null==areas){
            result = CommonUtil.genErrorResult("库区配置错误");
        }else {
            result = CommonUtil.genSucessResultWithData(areas);
        }
        return result;
    }

    @PostMapping("/login")
    public JSONObject login(@RequestBody User user){
        Resp resp = authService.checkLogin(user);
        return CommonUtil.genResultFromResp(resp);
    }

    /**
     * 获取手持菜单
     * @param userId
     * @return
     */
    @GetMapping("/menuList/{userId}")
    public JSONObject menuList(@PathVariable("userId") Integer userId){
        List<Resources> resources = authService.queryUserHandResources(userId);
        JSONObject result = null;
        if (null!=resources && resources.size()>0){
            JSONArray data = new JSONArray();
            for (int i = 0; i < resources.size(); i++) {
                Resources menu = resources.get(i);
                JSONObject item = new JSONObject();
                item.put("id",menu.getId());
                item.put("image",menu.getIcon());
                item.put("text",menu.getTitle());
                item.put("url",menu.getUrl());
                item.put("show",true);
                data.add(item);
            }
            result = CommonUtil.genSucessResultWithData(data);
        }else {
            result = CommonUtil.genErrorResult("当前用户未配置手持权限!");
        }
//        result.put("code",1);
//
//        JSONArray data = new JSONArray();
//
//        JSONObject item = new JSONObject();
//        item.put("id",1001);
//        item.put("image","/static/menu/mould-search.png");
//        item.put("text","serverWms1");
//        item.put("url","../mould/demo/demo");
//        item.put("show",true);
//        data.add(item);
//
//        item = new JSONObject();
//        item.put("id",1002);
//        item.put("image","/static/menu/mould-search.png");
//        item.put("text","serverWms2");
//        item.put("url","../mould/demo/demo");
//        item.put("show",true);
//        data.add(item);
//
//        result.put("data",data);

//        [
//        {
//            id:1001,
//                    image: '/static/menu/mould-search.png',
//                text: 'onload-WMS1',
//                url: '../mould/demo/demo',
//                show: true
//        },
//        {
//            id:1002,
//                    image: '/static/menu/mould-ruku.png',
//                text: 'onload-WMS2',
//                url: '/pages/mould/mould-ruku/mould-ruku',
//                show: true
//        },
//        {
//            id:1003,
//                    image: '/static/menu/mould-ruku.png',
//                text: 'onload-WMS3',
//                url: '/pages/mould/mould-ruku/mould-ruku',
//                show: true
//        }
//			]
        return result;
    }

    @GetMapping("/dictype/{typeCode}")
    public JSONObject getDicType(@PathVariable String typeCode){
        List<String> dicTypes = basicDataService.getdicType(typeCode);
        JSONObject result = null;
        if (null==dicTypes){
            result = CommonUtil.genErrorResult("字典查询错误");
        }else {
            result = CommonUtil.genSucessResultWithData(dicTypes);
        }
        return result;
    }
}
