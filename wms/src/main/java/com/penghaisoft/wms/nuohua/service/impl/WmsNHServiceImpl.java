package com.penghaisoft.wms.nuohua.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHService;
import com.penghaisoft.wms.nuohua.service.WcsForNHService;
import com.penghaisoft.wms.nuohua.service.WmsNHService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsMoveStereoscopicService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsTaskExecutionLogService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description
 * 出库 、移库、盘点 相关的业务逻辑
 * @Author zhangxin
 * @Date 2022-08-11
 **/
@Slf4j
@Service("wmsHBService")
public class WmsNHServiceImpl implements WmsNHService {
    @Autowired
    private IWmsMoveStereoscopicService wmsMoveStereoscopicService;
    @Autowired
    private IWmsTaskExecutionLogService wmsTaskExecutionLogService;

    //调WCS接口
    @Autowired
    private WcsForNHService wcsForNHService;
    /**
     *功能描述: 出库相关业务
     * 同层移库
     * @author zhangxin
     * @date 2022/8/11
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @Override
    public ResponseResult getResponseResultCK(Resp resp, DifferentBusinessNHService differentBusinessNHService, StringBuffer sb, String orderNo, LinkedList wmsOutTaskList) {
        //调用wcs接口
        try {
            JSONObject jSONObject = new JSONObject();
            if (wmsOutTaskList!=null && wmsOutTaskList.size()>0) {
                //出库任务下发，
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("groupId","");
                jsonObject.put("msgTime","");
                jsonObject.put("priorityCode",0);
                jsonObject.put("warehouse","NH_WAREHOUSE");
                List listTasks =new ArrayList();
                for (int i = 0; i < wmsOutTaskList.size(); i++) {
                    WmsOutTask wmsOutTask = (WmsOutTask) wmsOutTaskList.get(i);
                    //检查此库位是否可出
                    //根据指定的托盘号，检查数据
                    WmsLocationStereoscopic wmsLocationStereoscopic = differentBusinessNHService.checkByPalletCode(wmsOutTask.getPalletCode());
                    //不为空，即可正常出库
                    if (wmsLocationStereoscopic != null) {
                        Map map =new HashMap<>();
                        map.put("taskId", wmsOutTask.getTaskId());
                        map.put("taskType", "2");
                        map.put("startNode", wmsLocationStereoscopic.getLocationCode());
                        map.put("endNode", "2001");
                        map.put("barCode", wmsOutTask.getPalletCode());
                        map.put("order", 0);
                        listTasks.add(map);
                    }
                }
                jsonObject.put("tasks",listTasks);
                //TODO 下发出库任务，调wcs接口，接收出库指令
                jSONObject = wcsForNHService.taskReceive(jsonObject.toString());
                // 模拟返回
//                String str = "{ \"ret\": true, \"msg\": \"操作成功\"}";
//                jSONObject = JSONObject.parseObject(str);

                if (jSONObject != null) {
                    //如果调用成功
                    if (Boolean.valueOf(jSONObject.get("ret").toString())) {
                        log.info("出库任务 - 调wcs接口成功！");
                        for (int i = 0; i < wmsOutTaskList.size(); i++) {
                            WmsOutTask wmsOutTask = (WmsOutTask) wmsOutTaskList.get(i);
                            WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
                            wmsTaskExecutionLog.setTaskId(wmsOutTask.getTaskId());
                            wmsTaskExecutionLog.setTaskStatus("2");
                            // 如果调用成功，根据任务单号，修改任务表状态 2：执行中
                            wmsTaskExecutionLogService.updateByTaskId(wmsTaskExecutionLog);
                        }
                        log.info("出库单(" + orderNo + ")启动成功;");
                        sb.append("出库单(" + orderNo + ")启动成功;");

                    } else {
                        differentBusinessNHService.revertOut((List) null, wmsOutTaskList, orderNo);
                        log.info("出库单(" + orderNo + ")启动失败，获取推荐出库库位成功，调用WCS出库接口失败!");
                        sb.append("出库单(" + orderNo + ")启动失败，获取推荐出库库位成功，调用WCS出库接口失败!");
                        return new ResponseResult(Constant.RESULT.FAILED.code, sb.toString(), (Object) null);
                    }
                } else {
                    differentBusinessNHService.revertOut((List) null, wmsOutTaskList, orderNo);
                    log.info("出库单(" + orderNo + ")启动失败，获取推荐出库库位成功，调用WCS出库接口失败!");
                    sb.append("出库单(" + orderNo + ")启动失败，获取推荐出库库位成功，调用WCS出库接口失败!");
                    return new ResponseResult(Constant.RESULT.FAILED.code, sb.toString(), (Object) null);
                }
            }else{
                sb.append("出库单(" + orderNo + ")启动失败，数据缺失！");
                return new ResponseResult(Constant.RESULT.FAILED.code, sb.toString(), (Object) null);
            }

            } catch(Exception var20){
                var20.printStackTrace();
                differentBusinessNHService.revertOut((List) null, wmsOutTaskList, orderNo);
                log.info("出库单(" + orderNo + ")启动失败，获取推荐出库库位成功，调用WCS出库接口失败;");
                sb.append("出库单(" + orderNo + ")启动失败，获取推荐出库库位成功，调用WCS出库接口失败;");
                return new ResponseResult(Constant.RESULT.FAILED.code, sb.toString(), (Object) null);
            }
            return new ResponseResult(Constant.RESULT.SUCCESS.code, "立库出库启动成功！", resp);

    }

    /**
     *功能描述: 移库相关业务
     * @author zhangxin
     * @date 2022/8/11
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @Override
    public ResponseResult getResponseResultYK(WmsMoveStereoscopic wmsMoveStereoscopic, DifferentBusinessNHService differentBusinessNHService, WmsMoveStereoscopic wmsMoveStereoscopicNew, long taskId) {
        try {
            JSONObject jSONObject =new JSONObject();
            //根据指定的托盘号，检查
            WmsLocationStereoscopic wmsLocationStereoscopic = differentBusinessNHService.checkByPalletCode(wmsMoveStereoscopicNew.getPalletCode());
            //检查此库位是否可移动
            String fromFloor = wmsMoveStereoscopic.getFromLocationCode().substring(1, 2);
            String toFloor = wmsMoveStereoscopic.getToLocationCode().substring(1, 2);
            if(wmsLocationStereoscopic != null ) {
                JSONObject jsonObject = new JSONObject();
                if(fromFloor.equals(toFloor)) {
                    //同层 移库任务下发
                    jsonObject.put("groupId","");
                    jsonObject.put("msgTime","");
                    jsonObject.put("priorityCode",0);
                    jsonObject.put("warehouse","NH_WAREHOUSE");
                    List listTasks =new ArrayList();
                    Map map =new HashMap<>();
                    map.put("taskId", taskId);
                    map.put("taskType", "3");
                    map.put("startNode", wmsMoveStereoscopic.getFromLocationCode());
                    map.put("endNode", wmsMoveStereoscopic.getToLocationCode());
                    map.put("barCode", wmsMoveStereoscopicNew.getPalletCode());
                    map.put("order", 0);
                    listTasks.add(map);
                    jsonObject.put("tasks",listTasks);
                    log.info("同层 移库任务下发! -"+jsonObject +"--开始");
                    //TODO 下发移库任务，调wcs接口，接收移库指令
                    jSONObject = wcsForNHService.taskReceive(jsonObject.toString());
                    // 模拟返回
//                    String str = "{ \"ret\": true, \"msg\": \"操作成功\"}";
//                    jSONObject = JSONObject.parseObject(str);
                    log.info("同层 移库任务下发! -"+jSONObject +"--结束");
                    if(jSONObject!=null ) {
                        //如果调用成功
                        if(Boolean.valueOf(jSONObject.get("ret").toString())) {
                            log.info("调wcs接口成功！");
                            this.wmsMoveStereoscopicService.startYkTask(wmsMoveStereoscopicNew);
                        }else{
                            differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
                            return new ResponseResult(Constant.RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
                        }
                    }else{
                        differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
                        return new ResponseResult(Constant.RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
                    }
                }else{
                    //TODO  非同层 下发移库任务，调wcs接口，接收移库指令
                    jsonObject.put("groupId","");
                    jsonObject.put("msgTime","");
                    jsonObject.put("priorityCode",0);
                    jsonObject.put("warehouse","NH_WAREHOUSE");
                    List listTasks =new ArrayList();
                    Map map =new HashMap<>();
                    map.put("taskId", taskId);
                    map.put("taskType", "3");
                    map.put("startNode", wmsMoveStereoscopic.getFromLocationCode());
                    map.put("endNode", wmsMoveStereoscopic.getToLocationCode());
                    map.put("barCode", wmsMoveStereoscopicNew.getPalletCode());
                    map.put("order", 0);
                    listTasks.add(map);
                    jsonObject.put("tasks",listTasks);

                    log.info("非同层  移库任务下发! -"+jsonObject +"--开始");
                    //TODO 下发移库任务，调wcs接口，接收移库指令
                    jSONObject = wcsForNHService.taskReceive(jsonObject.toString());
                    // 模拟返回
//                    String str = "{ \"ret\": true, \"msg\": \"操作成功\"}";
//                    jSONObject = JSONObject.parseObject(str);
                    log.info("同层 移库任务下发! -"+jSONObject +"--结束");
                    if(jSONObject!=null ) {
                        //如果调用成功
                        if(Boolean.valueOf(jSONObject.get("ret").toString())) {
                            log.info("调wcs接口成功！");
                            this.wmsMoveStereoscopicService.startYkTask(wmsMoveStereoscopicNew);
                        }else{
                            differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
                            return new ResponseResult(Constant.RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
                        }
                    }else{
                        differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
                        return new ResponseResult(Constant.RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
                    }
                }
            }else {
                differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
                return new ResponseResult(Constant.RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，此库位不可移动！", (Object)null);
            }

 /*     try {
            wmsMoveStereoscopicNew.setTaskId(taskId);
            WmsMoveTask wmsMoveTask = new WmsMoveTask();
            wmsMoveTask.setTaskId(taskId);
            wmsMoveTask.setTaskType(String.valueOf(TaskType.NORMAL_MOVE));
            wmsMoveTask.setFromLocation(wmsMoveStereoscopicNew.getFromLocationCode());
            wmsMoveTask.setTargetLocation(wmsMoveStereoscopicNew.getToLocationCode());
            wmsMoveTask.setPalletCode(wmsMoveStereoscopicNew.getPalletCode());
            wmsMoveTask.setOperator(loginNameWithAccount);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<WmsMoveTask> request = new HttpEntity(wmsMoveTask, headers);
            ResponseEntity<JSONObject> wcsResp = this.restTemplate.postForEntity(this.noticePalletMoveAddr, request, JSONObject.class, new Object[0]);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("调wcs接口失败！");
                differentBusinessHBService.reverseYk(wmsMoveStereoscopicNew);
                return new ResponseResult(RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
            }

            JSONObject noticeResult = (JSONObject)wcsResp.getBody();
            if (!noticeResult.getString("code").equals("1")) {
                log.error("调wcs接口失败：" + noticeResult.getString("message"));
                differentBusinessHBService.reverseYk(wmsMoveStereoscopicNew);
                return new ResponseResult(RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
            }
             log.info("调wcs接口成功！");
            this.wmsMoveStereoscopicService.startYkTask(wmsMoveStereoscopicNew);
*/

        } catch (Exception var16) {
            differentBusinessNHService.reverseYk(wmsMoveStereoscopicNew);
            return new ResponseResult(Constant.RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
        }
        return null;
    }
}
