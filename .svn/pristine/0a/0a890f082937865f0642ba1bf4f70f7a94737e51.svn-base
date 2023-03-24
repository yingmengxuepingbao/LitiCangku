package com.penghaisoft.wms.nuohua.service;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;

import java.util.LinkedList;

/**
 * AGV 请求出库后状态上报
 **/

public interface WmsNHAgvService {

    /**
     * 任务接收接口
     */
    public JSONObject noVerifyBucketMove(JSONObject content);

    /**
     *功能描述: 入库异常时，将货物送至交接位后上报异常
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    public JSONObject fourwaycarError(String str);

    //-----------------现场添加接口----------------------------------------
    /**
     *功能描述: 调agv-执行下发
     * 当用户操作PDA将出入库单据推送至WMS中，WMS对任务进行管理，优先下发入库单据，暂停后续的出库单据，该接口由WMS主动下发控制执行任务
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    public JSONObject executeIssue(JSONObject map);
    /**
     *功能描述: 入库异常调agv取货
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    public JSONObject carError(JSONObject map);
}
