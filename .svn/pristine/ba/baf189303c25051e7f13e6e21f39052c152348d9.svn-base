package com.penghaisoft.wms.nuohua.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @Description 速锐-接口
 * @Author zx
 * @Date 2011-01-27
 @Configuration
 @ConditionalOnProperty(prefix = "surui",name = "state",havingValue = "true")
 */

public interface SLWCSService {

    /**
     * 任务接收接口
     */
    public JSONObject taskReceive(JSONObject content);

    /**
     * 任务目的位置更改
     */
    public JSONObject taskChange(JSONObject content);
    /**
     * 任务状态上报
     * @param map
     * @return
     */
    public JSONObject reportTask(@RequestBody Map map);

    /**
     * 任务取消接口
     */
    public  JSONObject taskCancel(JSONObject content);
    /**
     * 任务优先级调整
     */
    public  JSONObject taskPriority(JSONObject content);
    /**
     * 货位信息查询
     */
    public  JSONObject cargoLocationStatus(JSONObject content);
    /**
     * 货位信息同步
     */
    public  String  cargoLocationSyn(JSONArray content);
    /**
     * 货位编码同步
     */
    public  String cargoNoSyn(JSONArray content);
    /**
     * 设备状态查询接口
     */
    public  String deviceStatus(JSONObject content);
    /**
     *功能描述: 业务逻辑处理：出库/移库-将欧尚的数据入库，并修改速锐终点位置
     */
    public  JSONObject modifyAndSave(JSONObject map);

    //================现场增加接口============================
    /**
     *功能描述: 任务队列同步 -未使用
     * WMS调度AGV向接驳位行驶时，预先告知WCS该任务号属于入库还是出库。
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject informationSynchronization(WmsTaskExecutionLog wmsTaskExecutionLog);

}
