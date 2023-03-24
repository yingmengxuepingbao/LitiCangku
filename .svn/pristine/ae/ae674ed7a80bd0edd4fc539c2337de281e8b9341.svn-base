package com.penghaisoft.wms.nuohua.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description
 * 调WCS的接口
 **/
public interface WcsForNHService {

    /**
     *功能描述:
     * 速锐 WCS 系统，通过该接口接收上位系统发送的出入库移库任务，此接口支持单个或
     * 者多个任务同时下发。同组任务是有前后关系的一组任务。所有的移库都需要上位系统触发。
     * 每组多个任务的情况下按照每组集合的先后顺序执行
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
     JSONObject taskReceive(String str);


}
