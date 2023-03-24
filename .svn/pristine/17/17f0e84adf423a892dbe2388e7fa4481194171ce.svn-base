package com.penghaisoft.pda.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.model.User;
import com.penghaisoft.pda.common.KanbanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description DemoController
 * @Auther zhangxu
 * @Date 2019/12/27 11:02
 **/
@Slf4j
@RestController
@RequestMapping("demo")
public class DemoController{

    @Autowired
    private KanbanUtil kanbanUtil;

    @GetMapping("/ping")
    public JSONObject ping(){
        JSONObject result = new JSONObject();
        result.put("code",1);
        return result;
    }

    @PostMapping("/login")
    public JSONObject login(@RequestBody User user){
        log.info(JSON.toJSONString(user));
        JSONObject result = new JSONObject();
        if (user.getAccount().equals("admin")&&user.getPassword().equals("1")){
            result.put("code",1);
            result.put("token","aaa");
            JSONObject userInfo = new JSONObject();
            userInfo.put("name","张三");
            result.put("data",userInfo);
        }else {
            result.put("code",0);
        }
        return result;
    }

    @GetMapping("/menuList")
    public JSONObject menuList(@RequestHeader String token){
        log.info(token);
        JSONObject result = new JSONObject();
        result.put("code",1);

        JSONArray data = new JSONArray();

        JSONObject item = new JSONObject();
        item.put("id",1001);
        item.put("image","/static/menu/mould-search.png");
        item.put("text","serverWms1");
        item.put("url","../mould/demo/demo");
        item.put("show",true);
        data.add(item);

        item = new JSONObject();
        item.put("id",1002);
        item.put("image","/static/menu/mould-search.png");
        item.put("text","serverWms2");
        item.put("url","../mould/demo/demo");
        item.put("show",true);
        data.add(item);

        result.put("data",data);

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

    @GetMapping("kanban")
    public String writeKanban(){
        return "";
    }



}
