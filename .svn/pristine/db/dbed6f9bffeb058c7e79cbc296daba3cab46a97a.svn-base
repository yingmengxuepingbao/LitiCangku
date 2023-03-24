package com.penghaisoft.wcs.operation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.log.model.business.IWcsCallFourwaycarLogService;
import com.penghaisoft.wcs.operation.model.FourWayCarTask;
import com.penghaisoft.wcs.operation.model.FourWayCarTaskItem;
import com.penghaisoft.wcs.operation.service.FourwaycarOperationService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

/**
 * @ClassName FourwaycarOperationServiceImpl
 * @Description 四向车接口调用服务
 * @Author zhangx
 * @Date 2020/7/9 16:05
 **/
@Slf4j
@Service
public class FourwaycarOperationServiceImpl implements FourwaycarOperationService {

    @Value("${notice.other-sys-addr.fourwaycar-base-url}")
    private String fourwaycarBaseUrl;

    private static final String TASK_RECEIVE="/taskReceive";

    private static final String LOC_STATUS="/portStatusQuery";

    private static final String LOC_OCCUPY="/agvRequest";

    private static final String PALLET_READY="/agvComplete";

    private static final String CHANGE_PORT_STATUS="/changePortLockStatus";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IWcsCallFourwaycarLogService callFourwaycarLogService;
//    库区编号
    private static final String DISTRICT = "NH";



    /**
     * 统一封装
     * @param url
     * @param fourWayCarTask
     * @return
     */
    private Resp callWcsInterface(String url, FourWayCarTask fourWayCarTask) {
        Date sendTime = new Date();
        Resp resp = new Resp();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String,Object> param = new HashMap<>();
            param.put("groupId",fourWayCarTask.getGroupId());
            param.put("msgTime", System.currentTimeMillis());
//            param.put("priorityCode", 0);
            param.put("tasks",fourWayCarTask.getTasks());
            HttpEntity<Map> request = new HttpEntity<Map>(param, headers);
//              上传wms
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(fourwaycarBaseUrl + url, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("调速锐WCS接口{}服务端返回异常！", url);
                resp.setCode("0");
                resp.setMsg("调速锐WCS接口服务端返回异,http请求返回码=" + wcsResp.getStatusCodeValue());
                callFourwaycarLogService.addTaskReceiveLog(fourWayCarTask,"3",resp.getMsg(),sendTime);
            } else {
                JSONObject noticeResult = wcsResp.getBody();
                resp.setCode("1");
                resp.setData(noticeResult);
                if ("0".equals(noticeResult.getString("returnStatus"))){
//                    成功
//                    1成功 2业务异常 3服务端异常 4客户端异常
                    callFourwaycarLogService.addTaskReceiveLog(fourWayCarTask,"1",noticeResult.toJSONString(),sendTime);
                }else {
                    callFourwaycarLogService.addTaskReceiveLog(fourWayCarTask,"2",noticeResult.toJSONString(),sendTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调速锐WCS接口{}客户端异常！", url);
            String errorMsg = e.getMessage();
            if (errorMsg.length() > 200) {
                errorMsg = errorMsg.substring(0, 200);
            }
            resp.setCode("-1");
            resp.setMsg(errorMsg);
            callFourwaycarLogService.addTaskReceiveLog(fourWayCarTask,"4",errorMsg,sendTime);
        } finally {

            return resp;
        }
    }

    /**
     * 简单接口的封装
     * @param url
     * @param param
     * @return
     */
    private Resp callWcsSimpleInterface(String url, Map<String,Object> param) {
        Resp resp = new Resp();
        Date sendTime = new Date();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map> request = new HttpEntity<Map>(param, headers);
//              上传wms
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(fourwaycarBaseUrl + url, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("调速锐WCS接口{}服务端返回异常！", url);
                resp.setCode("0");
                resp.setMsg("调速锐WCS接口服务端返回异,http请求返回码=" + wcsResp.getStatusCodeValue());
//                1成功 2业务异常 3服务端异常 4客户端异常
                callFourwaycarLogService.addSimpleLog(url,param,"3",resp.getMsg(),sendTime);
            } else {
                JSONObject noticeResult = wcsResp.getBody();
                resp.setCode("1");
                resp.setData(noticeResult);

                if (noticeResult.containsKey("returnStatus")){
//                TASK_RECEIVE
//                /agvRequest
//                /agvComplete
//                /changePortLockStatus
                    if ("0".equals(noticeResult.getString("returnStatus"))){
//                    成功
                        //                1成功 2业务异常 3服务端异常 4客户端异常
//                    因为portStatusQuery调用很多次，只记录错误的信息
                        callFourwaycarLogService.addSimpleLog(url,param,"1",noticeResult.toJSONString(),sendTime);
                    }else {
                        //                1成功 2业务异常 3服务端异常 4客户端异常
                        callFourwaycarLogService.addSimpleLog(url,param,"2",noticeResult.toJSONString(),sendTime);
                    }
                }
                if (noticeResult.containsKey("status")){
//                /portStatusQuery  	{"status":0}  0可放货 1不可放货
                    if (!"0".equals(noticeResult.getString("status"))){
//                    因为portStatusQuery调用很多次，只记录错误的信息
                        callFourwaycarLogService.addSimpleLog(url,param,"2",noticeResult.toJSONString(),sendTime);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调速锐WCS接口{}客户端异常！", url);
            String errorMsg = e.getMessage();
            if (errorMsg.length() > 200) {
                errorMsg = errorMsg.substring(0, 200);
            }
            resp.setCode("-1");
            resp.setMsg(errorMsg);
            //                1成功 2业务异常 3服务端异常 4客户端异常
            callFourwaycarLogService.addSimpleLog(url,param,"4",errorMsg,sendTime);
        } finally {

            return resp;
        }
    }
    /**
     * 给四向车下发入库任务
     *
     * @param taskId
     * @param taskNo
     * @param locationCode
     * @param from
     * @param palletCode
     * @return
     */
    @Override
    public Resp sendInTask(Long taskId, Integer taskNo, Integer locationCode, Integer from, String palletCode) {

        FourWayCarTask fourWayCarTask = new FourWayCarTask();
        fourWayCarTask.setGroupId(taskId.toString());

        List<FourWayCarTaskItem> items = new ArrayList<>();
        FourWayCarTaskItem item = new FourWayCarTaskItem();
        item.setTaskId(taskNo.toString());
        item.setTaskType(0);
        item.setStartNode(from.toString());
        item.setEndNode(locationCode.toString());
        item.setDistrict(DISTRICT);
//        非必填
        item.setBarCode(palletCode);
        items.add(item);

        fourWayCarTask.setTasks(items);
        log.info("下发入库任务");
        Resp resp = callWcsInterface(TASK_RECEIVE,fourWayCarTask);

        return resp;
    }

    /**
     * 给四向车下发出库任务
     *
     * @param taskId
     * @param taskNo
     * @param locationCode
     * @param to
     * @param palletCode
     * @return
     */
    @Override
    public Resp sendOutTask(Long taskId, Integer taskNo, Integer locationCode, Integer to, String palletCode) {

        FourWayCarTask fourWayCarTask = new FourWayCarTask();

        fourWayCarTask.setGroupId(taskId.toString());

        List<FourWayCarTaskItem> items = new ArrayList<>();
        FourWayCarTaskItem item = new FourWayCarTaskItem();
        item.setTaskId(taskNo.toString());
        item.setTaskType(1);
        item.setStartNode(locationCode.toString());
        item.setEndNode(to.toString());
        item.setDistrict(DISTRICT);
//        托盘出库也必填
        item.setBarCode(palletCode);
        items.add(item);

        fourWayCarTask.setTasks(items);
        log.info("下发四向车出库任务");
        Resp resp = callWcsInterface(TASK_RECEIVE,fourWayCarTask);

        return resp;
    }

    /**
     * @return boolean
     * @Description 入库口是否允许放货
     * @Date 2020/7/28 11:26
     **/
    @Override
    public boolean inLocAllow() {
        boolean allow = false;
        Map<String,Object> param = new HashMap<>();
        param.put("district",DISTRICT);
        param.put("cargoLocationId", "1001");
        Resp resp = callWcsSimpleInterface(LOC_STATUS,param);
        if ("1".equals(resp.getCode())){
            JSONObject respData = (JSONObject) resp.getData();
            Integer status = respData.getInteger("status");
//            0. 可放货
//            1. 不可放货
            if (0==status){
                allow = true;
            }
        }
        return allow;
    }

    /**
     * @return void
     * @Description 占用四向车入库口
     * @Date 2020/7/28 11:31
     **/
    @Override
    public boolean occupyInLoc(String palletCode) {
        Map<String,Object> param = new HashMap<>();
        param.put("applyTime",System.currentTimeMillis());
        param.put("cargoLocationId", "1001");
        param.put("barCode", palletCode);
        Resp resp = callWcsSimpleInterface(LOC_OCCUPY,param);
        if ("1".equals(resp.getCode())){
            JSONObject data = (JSONObject) resp.getData();
            if (0==data.getInteger("returnStatus")){
                return true;
            }else {
                log.error("调用四向车占用入库口接口失败，{}",data.getString("returnInfo"));

            }
        }
        return false;
    }

    /**
     * @param palletCode
     * @return boolean
     * @Description 上传入库口AGV到位
     * @Date 2020/7/28 15:33
     **/
    @Override
    public boolean palletInReady(String palletCode) {
        Map<String,Object> param = new HashMap<>();
        param.put("applyTime",System.currentTimeMillis());
        param.put("cargoLocationId", "1001");
        param.put("barCode", palletCode);
        Resp resp = callWcsSimpleInterface(PALLET_READY,param);
        if ("1".equals(resp.getCode())){
            JSONObject data = (JSONObject) resp.getData();
            if (0==data.getInteger("returnStatus")){
                return true;
            }else {
                log.error("调用四向车入库口AGV放货完成失败，{}",data.getString("returnInfo"));

            }
        }
        return false;
    }
    /**
     * @Description: 调用四向车接口重发
     * @Author: jzh
     * @Date: 2020/7/30
     */
    @Override
    public Resp resendWcsInterface(String url, FourWayCarTask fourWayCarTask, Integer callFourwaycarLogId) {
        url = TASK_RECEIVE;
        Resp resp = new Resp();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String,Object> param = new HashMap<>();
            param.put("groupId",fourWayCarTask.getGroupId());
            param.put("msgTime", System.currentTimeMillis());
            param.put("tasks",fourWayCarTask.getTasks());
            HttpEntity<Map> request = new HttpEntity<Map>(param, headers);
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(fourwaycarBaseUrl + url, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("调速锐WCS接口{}服务端返回异常！", url);
                resp.setCode("0");
                resp.setMsg("调速锐WCS接口服务端返回异,http请求返回码=" + wcsResp.getStatusCodeValue());
//                callFourwaycarLogService.addTaskReceiveLog(fourWayCarTask,"3",resp.getMsg(),sendTime);
            } else {
                JSONObject noticeResult = wcsResp.getBody();

                resp.setData(noticeResult);
                if ("0".equals(noticeResult.getString("returnStatus"))){
//                    1成功 2业务异常 3服务端异常 4客户端异常
                    callFourwaycarLogService.updateTaskReceiveLog("1",callFourwaycarLogId,noticeResult.toJSONString());
                    resp.setCode("1");
                }else {
                    callFourwaycarLogService.updateTaskReceiveLog("2",callFourwaycarLogId,noticeResult.toJSONString());
                    resp.setCode("0");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调速锐WCS接口{}客户端异常！", url);
            String errorMsg = e.getMessage();
            if (errorMsg.length() > 200) {
                errorMsg = errorMsg.substring(0, 200);
            }
            resp.setCode("-1");
            resp.setMsg(errorMsg);
            callFourwaycarLogService.updateTaskReceiveLog("4",callFourwaycarLogId,errorMsg);
        } finally {

            return resp;
        }
    }

    /**
     * @return boolean
     * @Description 解锁
     * @Date 2020/9/22 17:08
     **/
    @Override
    public boolean unLockInPort() {
        Map<String,Object> param = new HashMap<>();
        param.put("district",DISTRICT);
        param.put("cargoLocationId", "1001");
        param.put("status", "0");
        Resp resp = callWcsSimpleInterface(CHANGE_PORT_STATUS,param);
        if ("1".equals(resp.getCode())){
            JSONObject data = (JSONObject) resp.getData();
            if (0==data.getInteger("returnStatus")){
                return true;
            }else {
                log.error("调用四向车解锁入库口失败，{}",data.getString("returnInfo"));
            }
        }
        return false;
    }

    /**
     * 下发一堆出库任务
     *
     * @param wcsTasks
     * @return
     */
    @Override
    public Resp sendOutTasks(List<WcsTask> wcsTasks) {

        FourWayCarTask fourWayCarTask = new FourWayCarTask();

        fourWayCarTask.setGroupId(String.valueOf(System.currentTimeMillis()));

        List<FourWayCarTaskItem> items = new ArrayList<>();
        for (WcsTask task:wcsTasks) {
//         b.task_no as user_defined1,
//      b.fourwaycar_task_id as user_defined2
            FourWayCarTaskItem item = new FourWayCarTaskItem();
            item.setTaskId(task.getUserDefined1());
            item.setTaskType(1);
            item.setStartNode(task.getFromAddress().toString());
            item.setEndNode(task.getToAddress().toString());
            item.setDistrict(DISTRICT);
//          下发直发出库任务必填
            item.setBarCode(task.getPalletCode());
            items.add(item);
        }

        fourWayCarTask.setTasks(items);
        log.info("发送批量出库任务");
        Resp resp = callWcsInterface(TASK_RECEIVE,fourWayCarTask);

        return resp;
    }

    /**
     * 下发单个移库任务
     *
     * @param taskId
     * @param taskNo
     * @param from
     * @param to
     * @param palletCode
     * @return
     */
    @Override
    public Resp sendMoveTask(Long taskId, Integer taskNo, Integer from, Integer to, String palletCode) {
        FourWayCarTask fourWayCarTask = new FourWayCarTask();

        fourWayCarTask.setGroupId(taskId.toString());

        List<FourWayCarTaskItem> items = new ArrayList<>();
        FourWayCarTaskItem item = new FourWayCarTaskItem();
        item.setTaskId(taskNo.toString());
        item.setTaskType(2);
        item.setStartNode(from.toString());
        item.setEndNode(to.toString());
        item.setDistrict(DISTRICT);
//        非必填
        item.setBarCode(palletCode);
        items.add(item);

        fourWayCarTask.setTasks(items);

        Resp resp = callWcsInterface(TASK_RECEIVE,fourWayCarTask);

        return resp;
    }


}
