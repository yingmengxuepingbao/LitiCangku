package com.penghaisoft.wcs.expose.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.expose.dto.AgvCallback;
import com.penghaisoft.wcs.expose.dto.AgvResult;
import com.penghaisoft.wcs.log.model.business.IWcsAgvReportLogService;
import com.penghaisoft.wcs.operation.constant.DeviceConstant;
import com.penghaisoft.wcs.operation.model.AgvPositionItem;
import com.penghaisoft.wcs.operation.model.AgvTask;
import com.penghaisoft.wcs.operation.model.EarlyInLocOccupyInfo;
import com.penghaisoft.wcs.operation.model.Palletizer;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import com.penghaisoft.wcs.operation.service.PalletizingOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskDispatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

/**
 * @Description 暴露给agv rcs系统的前期入库任务接口
 * @Date 2020/7/11 17:14
 * * zhangx
 **/
//@RestController
//@RequestMapping(value = "/agv")
@Slf4j
public class ExposeForAgvEarlyTwoController {

    @Autowired
    private ITaskDispatchService taskDispatchService;

    @Value("${jobs.open.early-palletizer}")
    private Boolean isEarly;

    @Autowired
    private AgvOperationService agvOperationService;

    @Autowired
    private PalletizingOperationService palletizingOperationService;

    @Autowired
    private IWcsAgvReportLogService agvReportLogService;

    @Value("${wcs.config.agv-task-typ}")
    private String agvTaskTyp;

    @Value("${wcs.config.agv-task-typ-pallet}")
    private String agvPalletTaskTyp;

    @GetMapping("palletizer")
    public Palletizer getPalletizerInfo(){
        return DeviceConstant.PALLETIZER;
    }


    @GetMapping("clear")
    public JSONObject clear(){
        JSONObject result = new JSONObject();
        String r = taskDispatchService.clearEarlyData();
        result.put("count",r);
        return result;
    }


    /**
     * 处理agv的回调
     * @param agvCallback
     * @return
     */
    @PostMapping("agvCallbackService/agvCallback")
    public AgvResult handleAgvCallBack(@RequestBody AgvCallback agvCallback){
        agvReportLogService.addReportLog(agvCallback);
//        根据任务号进行状态修改
        AgvResult agvResult = new AgvResult();
        String taskCodeStr = agvCallback.getTaskCode();
        Integer taskCode = Integer.parseInt(taskCodeStr);
        log.info("AGV上报{}任务完成,编号{}的设备位于位置:{}",taskCode,agvCallback.getRobotCode(),agvCallback.getCurrentPositionCode());
        if (null!=agvCallback.getData()&&!"null".equals(agvCallback.getData())){
            log.info("AGV上把data={}",agvCallback.getData());
        }
        agvResult.setReqCode(agvCallback.getReqCode());
        agvResult.setCode("0");

//       当前位置
        String currentPositionCode = agvCallback.getCurrentPositionCode();
//      任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
//            如果目前处于暂存
        if (currentPositionCode.charAt(0)=='X'){
            EarlyInLocOccupyInfo occupyInfo = palletizingOperationService.getEarlyInLocOccupyInfo();
            if (null!=occupyInfo){

                log.info("入库口占位信息={}",JSON.toJSONString(occupyInfo));

                if ("X1".equals(currentPositionCode)){
//                    优先入H1
                    if (occupyInfo.getH1AllowPallet()&&!occupyInfo.getH1HasJob()){
                        Thread inT = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    Thread.sleep(1000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                AgvTask agvTask = transInTaskModel(taskCodeStr,"H1");
                                Resp resp = agvOperationService.sendContinueTask(agvTask);
                                taskDispatchService.setEarlyAgvStatusByTaskNo(taskCode,"5");
                                palletizingOperationService.setInTask("H1");
                                occupyInfo.setH1HasJob(true);
                            }
                        });
                        inT.start();
                        return agvResult;
                    }
//                    次选入H2
                    if (occupyInfo.getH2AllowPallet()&&!occupyInfo.getH2HasJob()){
                        Thread inT = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    Thread.sleep(1000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                AgvTask agvTask = transInTaskModel(taskCodeStr,"H2");
                                Resp resp = agvOperationService.sendContinueTask(agvTask);
                                taskDispatchService.setEarlyAgvStatusByTaskNo(taskCode,"5");
                                palletizingOperationService.setInTask("H2");
                                occupyInfo.setH2HasJob(true);
                            }
                        });
                        inT.start();
                        return agvResult;
                    }
                }

                if ("X2".equals(currentPositionCode)){
                    //                    优先入H2
                    if (occupyInfo.getH2AllowPallet()&&!occupyInfo.getH2HasJob()){
                        Thread inT = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    Thread.sleep(1000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                AgvTask agvTask = transInTaskModel(taskCodeStr,"H2");
                                Resp resp = agvOperationService.sendContinueTask(agvTask);
                                taskDispatchService.setEarlyAgvStatusByTaskNo(taskCode,"5");
                                palletizingOperationService.setInTask("H2");
                                occupyInfo.setH2HasJob(true);
                            }
                        });
                        inT.start();
                        return agvResult;
                    }
//                    次入H1
                    if (occupyInfo.getH1AllowPallet()&&!occupyInfo.getH1HasJob()){
                        Thread inT = new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    Thread.sleep(1000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                AgvTask agvTask = transInTaskModel(taskCodeStr,"H1");
                                Resp resp = agvOperationService.sendContinueTask(agvTask);
                                taskDispatchService.setEarlyAgvStatusByTaskNo(taskCode,"5");
                                palletizingOperationService.setInTask("H1");
                                occupyInfo.setH1HasJob(true);
                            }
                        });
                        inT.start();
                        return agvResult;
                    }

                }

            }

//            剩下的没法入库，等着，需要修改agvtask状态
            //                    将当前agv任务设置为等待
            Long priority = System.currentTimeMillis();
            taskDispatchService.setEarlyAgvWaitingStatus(taskCode,Math.abs(priority.intValue()));

        }

//        当AGV到达H点，结束入库任务
        if (currentPositionCode.charAt(0)=='H'){
            log.info("AGV到达{}点，清除任务{}",currentPositionCode,taskCode);
            taskDispatchService.deleteFinishAgvTaskByTaskCode(taskCode);
        }

//        当AGV到达E点，是叫托盘任务的开始
        if (currentPositionCode.charAt(0)=='E'){
            Thread continueP = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AgvTask agvTask = transPalletInTaskModel(taskCodeStr,"m3");
                    Resp resp = agvOperationService.sendContinueTask(agvTask);
                    taskDispatchService.setEarlyAgvStatusByTaskNo(taskCode,"5");
                }
            });
            continueP.start();
            return agvResult;
        }

//        当AGV到达m点，是叫托盘任务结束
        if (currentPositionCode.charAt(0)=='m'){
            log.info("AGV到达{}点，清除任务{}",currentPositionCode,taskCode);
            taskDispatchService.deleteFinishAgvTaskByTaskCode(taskCode);
            if (DeviceConstant.PALLETIZER.isConnect()){

                palletizingOperationService.setPalletReceiveFinish((short)2);
            }else {
                log.error("AGV到达码盘线后写PLC接受完成=2失败,间隔1秒重试10次");

                Thread restartWrite = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (DeviceConstant.PALLETIZER.isConnect()){
                                log.info("AGV到达码盘线后写PLC接受完成=2");
                                palletizingOperationService.setPalletReceiveFinish((short)2);
                                break;
                            }
                        }
                    }
                });
                restartWrite.start();
            }
        }
        log.info("---agv 返回----");
        return agvResult;
    }

    private AgvTask transPalletInTaskModel(String taskCode, String target) {
        AgvTask agvTask = new AgvTask();
        agvTask.setReqCode(UUID.randomUUID().toString().replace("-",""));
        agvTask.setTaskCode(taskCode);
        agvTask.setTaskTyp(agvPalletTaskTyp);
        AgvPositionItem nextAddr = new AgvPositionItem();
        nextAddr.setPositionCode(target);
        nextAddr.setType("00");
        agvTask.setNextPositionCode(nextAddr);
        return agvTask;
    }

    /**
     * 生成入库任务
     * @param taskCode
     * @param target
     * @return
     */
    private AgvTask transInTaskModel(String taskCode, String target) {
        AgvTask agvTask = new AgvTask();
        agvTask.setReqCode(UUID.randomUUID().toString().replace("-",""));
        agvTask.setTaskCode(taskCode);
        agvTask.setTaskTyp(agvTaskTyp);
        AgvPositionItem nextAddr = new AgvPositionItem();
        nextAddr.setPositionCode(target);
        nextAddr.setType("00");
        agvTask.setNextPositionCode(nextAddr);
        return agvTask;
    }



}
