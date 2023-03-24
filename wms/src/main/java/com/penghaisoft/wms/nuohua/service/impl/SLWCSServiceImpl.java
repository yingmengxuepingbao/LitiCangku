package com.penghaisoft.wms.nuohua.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.wms.nuohua.service.SLWCSService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import com.penghaisoft.wms.util.PostResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 速锐-接口实现
 */
@Slf4j
@Service("sLWCSService")
public class SLWCSServiceImpl implements SLWCSService {
    //速锐接口地址

    @Value(value = "${surui.surui-url}")
    private String SURUI_URL;
    //private String SURUI_URL="http://172.32.2.19:8082/fromWms";

    @Autowired
    public PostResponseUtil postResponseUtil;
    /**
     * 任务接收接口
     * {
     * "groupId": "id123",//组号
     * "msgTime": "2020-07-14 02:51:33.SSS",//时间
     * "priorityCode": ”0”,//优先级
     * "wareHouse": "A_035",//仓库编码
     * "tasks": [ {
     * "barCode": "XZY000192",//托盘码
     * "endNode": "MX0-12-23",//终点
     * "startNode": " MX0-12-24",//起点
     * "taskId": "ACD3491",//任务单号
     * "taskType": ”0”,//任务类型
     * "order": “0” //顺序
     * },
     * ……
     * {
     * "barCode": "XZY000192",
     * "endNode": "MX0-12-23",
     * "startNode": " MX0-12-24",
     * "taskId": "ACD3491",
     * "taskType": ”0”,
     * "order": 1
     * }]
     * }
     * <p>
     * <p>
     * 响应实例：
     * {
     * " returnStatus": ”1”, //请求结果, 0成功
     * " returnInfo": "失败原因",//请求结果 描述
     * " msgTime": "2020-07-14 02:51:33 "//返回时间
     * }
     */
    @Override
    public JSONObject taskReceive(JSONObject map) {
        System.out.println("任务接收接口 : "+map.toString());
        //任务接收接口-路径
        String url = SURUI_URL + "/taskReceive";
        JSONObject jsonObject = selectJsonObject(map, url);
        return jsonObject;
    }
    /**
     *功能描述: 业务逻辑处理：出库/移库
     * @author zhangxin
     * @date 2022/3/22
     * @params  JSONObject map
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public  JSONObject modifyAndSave(JSONObject map) {

        return map;
    }

    /**
     * 任务目的位置更改
     * {
     * "msgTime": "2020-07-14 02:51:33.SSS",
     * "taskid": "XZY000192",
     * "endNode": "MX0-12-23",
     * }
     * 响应示例：
     * {
     * " returnStatus": ”1”,
     * " returnInfo": "失败原因",
     * " msgTime ": "2020-07-14 02:51:33 "
     * }
     */
    @Override
    public JSONObject taskChange(JSONObject map) {
        //任务接收接口-路径
        String url = SURUI_URL + "/taskChange";
        System.out.println("==================================="+url);
        log.info("-------任务目的位置更改-调wcs开始-------");
        JSONObject jsonObject = selectJsonObject(map, url);
        log.info("-------任务目的位置更改-调wcs结束-------");
        return jsonObject;
    }

    /**
     * 任务状态上报
     *
     * @param map
     * @return {
     * " taskId":" id123",
     * " reportTime": "2020-07-14 02:51:33",
     * " taskStatus": “1”,
     * "reporInfo":”失败原因…”
     * }
     * 相应示例：
     * {
     * " returnStatus": “1”,
     * " returnInfo": "失败原因",
     * " msgTime ": "2020-07-14 02:51:33"
     * }
     */
    @Override
    public JSONObject reportTask(@RequestBody Map map) {
        System.out.println("++++++++++++++++++++====="+map);
        //TODO 速锐WCS任务执行过程，将任务执行状态回传给上位系统,上位系统接收速锐 WCS 需 要返回接收结果。此接口也会返回任务执行的最终结果成功或者失败。
        JSONObject jsonObject = null;
        return jsonObject;
    }

    /**
     * 任务取消接口
     * {
     * " taskId ": "DC202006050060",
     * " msgTime ": "2020-07-14 02:51:33"
     * }
     * 响应示例：
     * {
     * " returnStatus":”1”,
     * " returnInfo": "失败原因",
     * " msgTime ": "2020-07-14 02:51:33"
     * }
     */
    @Override
    public JSONObject taskCancel(JSONObject map) {
        //任务接收接口-路径
        String url = SURUI_URL + "/taskCancel";
        JSONObject jsonObject = selectJsonObject(map, url);
        return jsonObject;
    }

    /**
     * 任务优先级调整
     * {
     * " taskId ":"DC202006050060",
     * "priorityCode":”100”,
     * "msgTime": "2020-07-14 02:51:33"
     * }
     * 响应示例：
     * {
     * " returnStatus":”1”,
     * " returnInfo": "失败原因",
     * " msgTime ": "2020-07-14 02:51:33"
     * }
     */
    @Override
    public JSONObject taskPriority(JSONObject map) {
        //任务接收接口-路径
        String url = SURUI_URL + "/taskPriority";
        JSONObject jsonObject = selectJsonObject(map, url);
        return jsonObject;
    }

    /**
     * 货位信息查询
     * {
     * " wareHouse ": "A-100",
     * "layer":”2”,
     * "cargoLocationId": "A-01-0201"
     * }
     * 响应示例：
     * {
     * " returnStatus wareHouse ": "0",
     * " returnInfo":”” ,
     * “msgTime”: "2020-07-14 02:51:33"
     * "data": [ {
     * " cargoLocationIdbarCode": " A-01-0201",
     * " cargoLocationStatusendNode": "Y",
     * "startNode": "A20220110",
     * }]
     * }
     */
    @Override
    public JSONObject cargoLocationStatus(JSONObject map) {
        System.out.println("货位信息查询map 入参："+map);
        //任务接收接口-路径
        String url = SURUI_URL + "/cargoLocationStatus";
        JSONObject jsonObject = selectJsonObject(map, url);
        return jsonObject;
    }

    /**
     * 货位信息同步
     * warehouse 仓库编码 String 是 仓库编码
     * cargoLocationId 货位编号 String 是 上位系统提供的编号
     * cargoLocationStatus 货位状态 String 是 Y. 有 货N. 无 货
     * [
     * {
     * " warehouse ":"A-100",
     * "cargoLocationId":"A-01-0201",
     * "cargoLocationStatus": "Y"
     * }
     * ]
     */
    @Override
    public String  cargoLocationSyn(JSONArray map) {
        //任务接收接口-路径
        String url = SURUI_URL + "/cargoLocationSyn";
        System.out.println("货位信息同步："+map.toJSONString());
        String str= postResponseUtil.postString(url, map.toJSONString());
        return str;
    }

    /**
     * 货位编码同步
     */
    @Override
    public String cargoNoSyn(JSONArray map) {
        //任务接收接口-路径
        String url = SURUI_URL + "/cargoNoSyn";
        System.out.println("货位信息同步:请求数据："+map.toJSONString());
        String jsonObject = postResponseUtil.postString(url,map.toString());
        return jsonObject;
    }

    /**
     * 设备状态查询接口
     * {
     * "deviceNo": ”1”,
     * "deviceType": “2”
     * }
     * 响应示例：
     * [{
     * "deviceNo":”1”,
     * "deviceInfo": "info",
     * "deviceStatus":”1”,
     * "deviceType":”2”,
     * "location": "location"
     * }, ......
     * {
     * "deviceNo":”2”,
     * "deviceInfo": "info",
     * "deviceStatus":”1”,
     * "deviceType":”2”,
     * "location": "location"
     * } ]
     */
    @Override
    public String deviceStatus(JSONObject map) {
        //任务接收接口-路径
        String url = SURUI_URL + "/deviceStatus";
        String str= postResponseUtil.postJson(url, map);;
        return str;
    }
//================现场增加接口============================
    /**
     *功能描述: 任务队列同步 -废弃掉
     * WMS调度AGV向接驳位行驶时，预先告知WCS该任务号属于入库还是出库。
     * @params
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject informationSynchronization(WmsTaskExecutionLog wmsTaskExecutionLog){
        JSONObject map =new JSONObject();
        map.put("taskId",wmsTaskExecutionLog.getTaskId());
        if(String.valueOf(Constant.TaskType.STRAIGHT_OUT.getTaskType()).equals(wmsTaskExecutionLog.getTaskType())) {
            //出库
            map.put("billType", "2");
            //原材料
            if(String.valueOf(Constant.TaskType.NH_PRODUCT_IN_YCL.getTaskType()).equals(wmsTaskExecutionLog.getUserDefined2())) {
                map.put("productType", "1");
            }else if(String.valueOf(Constant.TaskType.NH_PRODUCT_IN.getTaskType()).equals(wmsTaskExecutionLog.getUserDefined2())){
                map.put("productType", "2");
            }
        }else{
            //入库
            map.put("billType", "1");
            //原材料
            if(String.valueOf(Constant.TaskType.NH_PRODUCT_IN_YCL.getTaskType()).equals(wmsTaskExecutionLog.getTaskType())) {
                map.put("productType", "1");
            }else if(String.valueOf(Constant.TaskType.NH_PRODUCT_IN.getTaskType()).equals(wmsTaskExecutionLog.getTaskType())){
                map.put("productType", "2");
            }
        }

        map.put("productCode",wmsTaskExecutionLog.getGoodsCode());
        map.put("batchNo",wmsTaskExecutionLog.getBatchNo());
        map.put("quantity",wmsTaskExecutionLog.getUserDefined1());
        map.put("palletNo",wmsTaskExecutionLog.getPalletCode());
        map.put("sapOrderNo",wmsTaskExecutionLog.getOrderNo());
        log.info("任务队列同步 -开始！");
        //TODO
        String url = SURUI_URL + "";
        JSONObject jsonObject = selectJsonObject(map, url);
        log.info("任务队列同步 -结束！");
        return jsonObject;
    }
    /**
     * 发送请求并解析JSON
     * @return 返回数据  JSONObject
     * ResponseEntity<net.sf.json.JSONObject> resout= postResponseUtil.getJsonObjectEntity(url, str);
     * JSONObject jsonObject = postResponseUtil.jieXiWmsReturn(resout);
     */
    public JSONObject selectJsonObject(JSONObject map, String url) {
        JSONObject jSONObject=new JSONObject();
        //post请求
        try {
            System.out.println("发送请求并解析JSON:" + url + "字符串：" + map);
            String data = postResponseUtil.postJson(url, map);
            //String data ="{returnStatus:0,returnInfo:\"\",msgTime:\"2022-09-09 11:11:11\",data:\"\"}";
            System.out.println("返回数据：" + data);
            jSONObject = JSONObject.parseObject(data);
        }catch (Exception e){
            jSONObject.put("returnStatus",2);
        }
        return jSONObject;
    }

}
