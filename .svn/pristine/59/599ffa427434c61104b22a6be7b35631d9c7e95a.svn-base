package com.penghaisoft.wms.util;


import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 发送请求公共方法
 */
@Slf4j
@Component
public class PostResponseUtil {

    /**
     * post请求 -JSONObject格式
     * @return
     */
    public static String postJson(String url, JSONObject jsonStr){
        String retJson = "";
        System.out.println("post请求，请求数据jsonStr:"+jsonStr);
        try {
            retJson = HttpRequest.post(url).header("Content-Type","application/json")
                    .timeout(3000).body(String.valueOf(jsonStr)).execute().body();
            System.out.println("post请求，返回数据retJson:"+retJson);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return retJson;
    }
    /**
     *功能描述: post请求 Sting格式-list列表形式的
     * @params
     * @return java.lang.String
     */
    public static String postString(String url, String jsonStr){
        String retJson = "";
        try {
            System.out.println("list列表形式的，请求数据jsonStr:"+jsonStr);
            retJson = HttpRequest.post(url).header("Content-Type","application/json")
                    .timeout(30000).body(jsonStr).execute().body();
            System.out.println("list列表形式的，返回数据retJson:"+retJson);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return retJson;
    }

    /**
     * 发送请求并解析JSON
     * @param map 请求体
     * @param url 请求路径
     * @return 返回数据
     */
    public JSONObject selectJsonObject(Map<String, String> map, String url) {
        //post请求 map格式
        ResponseEntity<String> wcsResp= getJsonEntity(url, map);

        System.out.println("post请求 返回数据："+wcsResp);
        String regionString = wcsResp.getBody();//获取请求体
        //解析
        JSONObject jsonObject = JSONObject.parseObject(regionString);//将请求体转化为json格式
        System.out.println("解析后，返回数据："+jsonObject);
        return jsonObject;
    }

    /**
     * post请求 map格式
     * @param map 请求体
     * @return
     */
    public ResponseEntity<String> getJsonEntity(String url,Map<String, String> map) {
        //采用JSONObject或者实体类传递参数
        //创建一个响应头
        HttpHeaders headers = new HttpHeaders();
        //设置请求资源或数据类型：application/json
        headers.setContentType(MediaType.parseMediaType("application/json"));
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        if(map.size()>0){
            for(String key:map.keySet()){
                params.add(key,map.get(key));
            }
        }
        HttpEntity<Map> request = new HttpEntity<Map>(params, headers);
        log.info("调用接口-开始："+request);
        ResponseEntity<String>  str= new RestTemplate().postForEntity(url, request, String.class);
        log.info("调用接口-结束，wcsResp:"+str);

        return str;
    }

}
