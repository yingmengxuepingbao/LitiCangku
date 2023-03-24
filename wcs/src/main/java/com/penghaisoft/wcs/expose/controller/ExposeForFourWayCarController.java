package com.penghaisoft.wcs.expose.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.expose.dto.FourWayCarTaskApply;
import com.penghaisoft.wcs.expose.dto.FourWayCarTaskReport;
import com.penghaisoft.wcs.log.model.business.IWcsFourwaycarReportLogService;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.operation.model.AgvPositionItem;
import com.penghaisoft.wcs.operation.model.AgvTask;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import com.penghaisoft.wcs.operation.service.CallWmsService;
import com.penghaisoft.wcs.operation.service.FourwaycarOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskSplitService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsFourwaycarTask;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName ExposeForFourWayCarController
 * @Description 暴露给四向车的接口
 * 1	任务开始
 * 4	取货完成
 * 9	即将完成
 * 8	任务结束
 * @Author zhangx
 * @Date 2020/7/8 10:44
 **/
@RestController
@RequestMapping(value = "/fourwaycar")
@Slf4j
public class ExposeForFourWayCarController {

    @Autowired
    private ITaskSplitService taskSplitService;

    @Autowired
    private ITaskDispatchService taskDispatchService;

    @Autowired
    private CallWmsService callWmsService;

    @Autowired
    private FourwaycarOperationService fourwaycarOperationService;

    @Autowired
    private IWcsErrorLogService wcsErrorLogService;

    @Autowired
    private IWcsFourwaycarReportLogService wcsFourwaycarReportLogService;

    @Autowired
    private IWcsCommonService wcsCommonService;

    @Autowired
    private AgvOperationService agvOperationService;

    @Value("${wcs.config.agv-task-typ-pallet}")
    private String agvPalletTaskTyp;

    /**
     * 四向车任务申请-4.1
     * @param taskApply
     * @return
     */
    @PostMapping("task/apply")
    public JSONObject taskApply(@RequestBody FourWayCarTaskApply taskApply){
        String operator = "ExposeForFourWayCarController";
        log.info("四向车WCS申请任务，参数={}", JSON.toJSONString(taskApply));
        JSONObject result = new JSONObject();
        String palletCode = taskApply.getBarCode();

        Integer returnStatus = 0;
        String returnInfo = "";
        if ("AB1234".equals(palletCode)){
//            虚拟托盘
//          空托盘入库申请
            Date now = new Date();
//            转换为虚拟推盘码
            SimpleDateFormat format = new SimpleDateFormat("YYMMdd");
            String dateFormat = format.format(now).substring(2);
            String virtualPalletCode = "VP" + dateFormat + wcsCommonService.getSeqLen4("VP", 1).get(0);
//          获取推荐库位&&  生成入库任务
            WcsTask virtualPalletInTask = callWmsService.recommendVirtualPalletInLocation(virtualPalletCode);
            if (null!=virtualPalletInTask){

                Integer wcsTaskId = virtualPalletInTask.getId();
                Integer fourwaycarTaskId = Integer.parseInt(virtualPalletInTask.getUserDefined1());
                Integer fourwaycarTaskNo = Integer.parseInt(virtualPalletInTask.getUserDefined2());
                Integer from = 1003;
                Integer locationCode = Integer.parseInt(virtualPalletInTask.getUserDefined3());
                Long taskId = Long.parseLong(virtualPalletInTask.getUserDefined4());
//                todo 异步下发任务
                sendVpInTaskAsync(taskId,wcsTaskId,fourwaycarTaskId,fourwaycarTaskNo,locationCode,from,virtualPalletCode);
            }else {
                returnStatus = 1;
                returnInfo = "推荐虚拟推盘入库库位失败！";
            }

        }else{
//            下线入库
            WcsTask inWcsTask = callWmsService.recommendProductInLocation(palletCode);

            if (null!=inWcsTask){
//        拿到原来绑定时候的任务号
                Long taskId = inWcsTask.getTaskId();
//        计算得到的推荐库位
                Integer locationCode = inWcsTask.getToAddress();
                Integer fourwaycarTaskNo = Integer.parseInt(inWcsTask.getUserDefined1());
                Integer fourwaycarTaskId = Integer.parseInt(inWcsTask.getUserDefined2());
                Integer from = Integer.parseInt(taskApply.getFromPort());
                log.info("请求推荐库位成功 taskId={},locationCode={}",taskId,locationCode);
                //        0-成功

                result.put("returnInfo","");
//            任务状态 1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败
                taskDispatchService.setFourwaycarStatusByTaskId("2",fourwaycarTaskId,operator,locationCode);
                sendInTaskAsync(fourwaycarTaskId,taskId,fourwaycarTaskNo,locationCode,from,palletCode);
            }else {
                //        1-失败
                returnStatus = 1;
                returnInfo = "推荐下线入库库位失败！";
            }
        }
        result.put("returnStatus",returnStatus);
        result.put("returnInfo",returnInfo);
        result.put("msgTime",System.currentTimeMillis());
        wcsFourwaycarReportLogService.addTaskApplyReportLog(taskApply,returnStatus.toString(),returnInfo);
        return result;
    }

    /**
     * 异步下发四向车入库任务
     * &更新状态
     * @param taskId
     * @param wcsTaskId
     * @param fourwaycarTaskId
     * @param fourwaycarTaskNo
     * @param locationCode
     * @param from
     * @param virtualPalletCode
     */
    private void sendVpInTaskAsync(Long taskId, Integer wcsTaskId, Integer fourwaycarTaskId, Integer fourwaycarTaskNo,
                                   Integer locationCode, Integer from, String virtualPalletCode) {
        Thread t = new Thread(()->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String operator = "sendVpInTaskAsync";
            log.info("下发给速锐WCS任务taskId={},locationCode={}",taskId,locationCode);
            Resp resp = fourwaycarOperationService.sendInTask(taskId,fourwaycarTaskNo,locationCode,from,virtualPalletCode);
            String callCode = resp.getCode();
            if ("1".equals(callCode)){//调用接口成功
                JSONObject fourwaycarResult = (JSONObject)resp.getData();
                if ("0".equals(fourwaycarResult.getString("returnStatus"))){
//                    wcs_task 1->2 fourwatcar_task 2->3
                    taskDispatchService.startHandAndVirtualPalletInTaskSuccess(wcsTaskId,fourwaycarTaskId,operator);
                }else{
//                业务异常
                    taskDispatchService.startHandAndVirtualPalletInTaskFail(wcsTaskId,fourwaycarTaskId,operator,fourwaycarResult.getString("returnInfo"));
                }

//        2-2下发失败记录
            }else if("0".equals(callCode)){//接口报错

                taskDispatchService.startHandAndVirtualPalletInTaskFail(wcsTaskId,fourwaycarTaskId,operator,resp.getMsg());
                String ins = "from:"+from+";to:"+locationCode+";fourwaycarTaskNo="+fourwaycarTaskNo;
                wcsErrorLogService.addFourwaycarLog("21",(short)500,(short)2,"sendVpInTaskAsync", ins,"虚拟托盘入库-服务端错误");
            }else if("-1".equals(callCode)){//未发送成功
                taskDispatchService.startHandAndVirtualPalletInTaskFail(wcsTaskId,fourwaycarTaskId,operator,resp.getMsg());
                String ins = "from:"+from+";to:"+locationCode+";fourwaycarTaskNo="+fourwaycarTaskNo;
                wcsErrorLogService.addFourwaycarLog("21",(short)500,(short)2,"sendVpInTaskAsync", ins,"虚拟托盘入库-客户端错误");
            }else {
                log.error("不可能的调用四向车产生任务单结果编码={}",callCode);
            }
        });
        t.start();
    }


    /**
     *      * 0	已接收
     *      * 1	任务开始	 A
     *      * 2	开始进提升机
     *      * 3	已出提升机
     *      * 4	取货完成  B
     *      * 5	搬运中
     *      * 6	放货完成
     *      * 7	任务中断
     *      * 8	任务结束  D
     *      * 9	即将完成	预完成信号，用于看板提前显示  C
     *      顺序上应该是A-B-C-D
     *       1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成
     * 四向车任务上报
     * @param taskReport
     * @return
     */
    @PostMapping("task/report")
    public JSONObject taskReport(@RequestBody FourWayCarTaskReport taskReport){
        String operator = "ExposeForFourWayCarController";
        log.info("四向车WCS上报任务状态，参数={}", JSON.toJSONString(taskReport));
        JSONObject result = new JSONObject();
        String status = taskReport.getTaskStatus();
        Integer returnStatus = 0;
        String returnInfo = "";
        Integer taskNo = taskReport.getTaskId();
        if ("1".equals(status)){
//            四向车状态改为 执行
            taskDispatchService.setFourwaycarStatusByTaskNo(taskNo,"4",operator);
        }else if("4".equals(status)){
//            四向车状态改为 取货完成
            taskDispatchService.setFourwaycarStatusByTaskNo(taskNo,"5",operator);

        }else if("9".equals(status)){
//            上报9的时候要告诉wms这坨已经出库了，wms 增加本订单的出库托盘数量
            WcsFourwaycarTask fourwaycarTask = taskDispatchService.findFourWayCarTaskByTaskNo(taskNo);
            if (null!=fourwaycarTask){
                Long taskId = fourwaycarTask.getTaskId();
                String taskType = wcsCommonService.getTaskTypeByTaskId(taskId);
                long taskTypeLong = Long.parseLong(taskType);
                if(Constant.TaskType.STRAIGHT_OUT.getTaskType()==taskTypeLong||Constant.TaskType.HAND_OUT.getTaskType()==taskTypeLong){
                    String palletCode = fourwaycarTask.getPalletCode();

                    callWmsService.palletReadyOut(taskId,palletCode);
                }
            }
        }else if("8".equals(status)){
//            四向车任务结束
            WcsFourwaycarTask fourwaycarTask = taskDispatchService.findFourWayCarTaskByTaskNo(taskNo);
            Long taskId = fourwaycarTask.getTaskId();
            String taskType = wcsCommonService.getTaskTypeByTaskId(taskId);
            long taskTypeLong = Long.parseLong(taskType);
            if (Constant.TaskType.PRODUCT_IN.getTaskType()==taskTypeLong||
                    Constant.TaskType.HAND_IN.getTaskType()==taskTypeLong||
                    Constant.TaskType.VIRTUAL_PALLET_IN.getTaskType()==taskTypeLong||
                    Constant.TaskType.NORMAL_MOVE.getTaskType()==taskTypeLong){
//                四向车加整体任务完成
                taskDispatchService.finishFourwaycarAndWholeTask(taskId,operator);
            }else if(Constant.TaskType.VIRTUAL_PALLET_OUT.getTaskType()==taskTypeLong){
//                四向车任务完成
                taskDispatchService.setFourwaycarStatusByTaskNo(taskNo,"6",operator);
//                下发AGV任务
                sendAgvStartTaskAsync(taskId);
            }else if(Constant.TaskType.STRAIGHT_OUT.getTaskType()==taskTypeLong||Constant.TaskType.HAND_OUT.getTaskType()==taskTypeLong){
                taskDispatchService.finishFourwaycarAndWholeTask(taskId,operator);
//                  直发出库调用wms LED显示
                String palletCode = fourwaycarTask.getPalletCode();
                callWmsService.showOutPalletInfo(taskNo,palletCode,status);
            }

        }else {
            log.info("四向车上报任务状态={},此状态未处理",status);
        }
        result.put("taskId",taskReport.getTaskId());
        result.put("returnStatus",returnStatus);
        result.put("returnInfo",returnInfo);
        result.put("msgTime",System.currentTimeMillis());
        wcsFourwaycarReportLogService.addTaskReportLog(taskReport,returnStatus.toString(),returnInfo);
        return result;
    }

    /**
     * @Description 下发AGV任务
     * @Date 2020/7/29 15:22
     * @param taskId
     * @return void
     **/
    private void sendAgvStartTaskAsync(Long taskId) {
        WcsAgvTask wcsAgvTask = taskDispatchService.findAgvTaskByTaskId(taskId);
        Integer taskNo = wcsAgvTask.getTaskNo();
        if (null!=wcsAgvTask){
            Thread t = new Thread(()-> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                    从3001到m3
                String reqCode = UUID.randomUUID().toString().replace("-","");
                AgvTask agvTask = new AgvTask();
                agvTask.setReqCode(reqCode);
                agvTask.setTaskCode(taskNo.toString());
                agvTask.setTaskTyp(agvPalletTaskTyp);
                List<AgvPositionItem> agvPositionItemList = new ArrayList<>();
                AgvPositionItem item = new AgvPositionItem();
                item.setPositionCode("3001");
                item.setType("00");
                agvPositionItemList.add(item);
                item = new AgvPositionItem();
                item.setPositionCode("m3");
                item.setType("00");
                agvPositionItemList.add(item);
                agvTask.setPositionCodePath(agvPositionItemList);

                Resp resp = agvOperationService.sendTask(agvTask);
                if ("1".equals(resp.getCode())){
                    JSONObject data = (JSONObject) resp.getData();
                    if ("0".equals(data.getString("code"))){
//                            AGV任务状态=2 下发
                        taskDispatchService.setAgvStatusByTaskNo(taskNo,"2","ExposeForFourWayCarController-sendAgvStartTaskAsync");
                    }
                }
            });

            t.start();
        }
    }

    /**
     * 异步下发任务
     * @param taskId
     * @param fourwaycarTaskNo
     * @param locationCode
     * @param from
     * @param palletCode
     */
    private void sendInTaskAsync(Integer fourwaycarTaskId,Long taskId,Integer fourwaycarTaskNo,Integer locationCode,Integer from ,String palletCode){
//        todo 下发失败或者异常的考虑定时任务补偿
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("下发给速锐WCS任务taskId={},locationCode={}",taskId,locationCode);
//                    下发给
                Resp resp = fourwaycarOperationService.sendInTask(taskId,fourwaycarTaskNo,locationCode,from,palletCode);
                String callCode = resp.getCode();
//        2-1下发成功更新状态
                if ("1".equals(callCode)){//调用接口成功
                    JSONObject fourwaycarResult = (JSONObject)resp.getData();
                    if ("0".equals(fourwaycarResult.getString("returnStatus"))){
//                            任务状态改为3
//                            任务状态 1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败
                        taskDispatchService.sendFourwaycarTaskSuccess(fourwaycarTaskId,"sendInTaskSync");
                    }else{
//                业务异常
                        taskDispatchService.sendFourwaycarTaskFail(fourwaycarTaskId,fourwaycarResult.getString("returnInfo"),"sendInTaskSync");
                    }

//        2-2下发失败记录
                }else if("0".equals(callCode)){//接口报错
                    taskDispatchService.sendFourwaycarTaskError(fourwaycarTaskId,resp.getMsg(),"sendInTaskSync");
                    String ins = "from:"+from+";to:"+locationCode+";fourwaycarTaskNo="+fourwaycarTaskNo;
                    wcsErrorLogService.addFourwaycarLog("21",(short)500,(short)2,"sendInTaskSync", ins,"下线入库-服务端错误");
                }else if("-1".equals(callCode)){//未发送成功
                    taskDispatchService.sendFourwaycarTaskError(fourwaycarTaskId,resp.getMsg(),"sendInTaskSync");
                    String ins = "from:"+from+";to:"+locationCode+";fourwaycarTaskNo="+fourwaycarTaskNo;
                    wcsErrorLogService.addFourwaycarLog("21",(short)500,(short)2,"sendInTaskSync", ins,"下线入库-客户端错误");
                }else {
                    log.error("不可能的调用四向车产生任务单结果编码={}",callCode);
                }
            }
        });
        t.start();
    }

}
