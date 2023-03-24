package com.penghaisoft.wms.nuohua.service;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.wms.expose.WcsTransOb;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description
 * @Author zhangxin
 * @Date 2022-07-14
 **/
public interface InterfaceForNHService {

    /**
     *功能描述: 入库推荐-生成推荐库位（一个）
     * @author zhangxin
     * @date 2022/7/14
     * @params
     * @return java.lang.String
     */
    ResponseResult queryLocationCode_HB(WcsTransOb wcsTransOb);
    /**
     *功能描述: 生产入库
     * @author zhangxin
     * @date 2022/7/14
     * @params
     * @return java.lang.String
     */
    ResponseResult reportInStereoscopicTask_HB(WcsTransOb wcsTransOb);
    /**
     *功能描述: 普通移库
     * @author zhangxin
     * @date 2022/7/14
     * @params
     * @return java.lang.String
     */
    ResponseResult reportNormalYkTask_HB(WcsTransOb wcsTransOb);
    /**
     *功能描述: 出库（库内）移库
     * @author zhangxin
     * @date 2022/7/14
     * @params
     * @return java.lang.String
     */
    ResponseResult reportOutwarehouseYkTask_HB(WcsTransOb wcsTransOb);
    /**
     *功能描述: 直发出库
     * @author zhangxin
     * @date 2022/7/14
     * @params
     * @return java.lang.String
     */
    ResponseResult reportOutStraightTask_HB(WcsTransOb wcsTransOb);
    /**
     *功能描述: 接收出库任务状态-盘点口
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
     ResponseResult reportOutCheckTask_HB(@RequestBody WcsTransOb wcsTransOb);
    /**
     *功能描述: 确认对账 - 修改任务的状态
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
     ResponseResult taskStatusUpdate(@RequestBody WmsTaskExecutionLog wmsTaskExecutionLog);
    /**
     *功能描述: 入库确认对账 - 修改任务的状态，并且修改库位的状态。
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
     ResponseResult stereoscopicStatusUpdate(@RequestBody WmsTaskExecutionLog wmsTaskExecutionLog);

    /**
     *功能描述: AGV /PDA 请求出库 -业务处理
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
     JSONObject requestDelivery(@RequestBody JSONObject jsonObject);

    //===========================================现场接口修改====================================
    /**
     *功能描述: 出入库单据下发 -（AGV）用户在操作手持，出入库单据，下发到冷库四项车WMS中
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    ResponseResult documentDistribution(@RequestBody JSONObject jsonObject);
    /**
     * 功能描述: wcs 入库请求 只给个TaskID 任务号，托盘号，物料号
     * agv先请求 wms
     * wms 调agv下任务
     * wcs请求入库，根据任务ID 查询相关数据
     * 如果是原材料，接受托盘号，解析物料号（因为物料号里是一长串数据）
     * 如果是成品，校验托盘号即可
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    ResponseResult requestWarehousing(WcsTransOb wcsTransOb);
    /**
     *功能描述: wcs调wms异常请求接口
     * 根据任务号，查询，入库，是原材料还是成品
     * 原材料手动pda入库
     * 成品呼叫agv
     * @params
     * @return
     */
    ResponseResult abnormal(@RequestBody JSONObject jsonObject);

    /**
     *功能描述: AGV 异常取货 安全驶离 - 上报  wms / wms更改任务状态。
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    ResponseResult agvDriveAwaySafely(@RequestBody JSONObject jsonObject);
}
