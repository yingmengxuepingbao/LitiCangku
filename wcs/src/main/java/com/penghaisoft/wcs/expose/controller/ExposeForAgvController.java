package com.penghaisoft.wcs.expose.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.expose.dto.AgvCallback;
import com.penghaisoft.wcs.expose.dto.AgvResult;
import com.penghaisoft.wcs.log.model.business.IWcsAgvReportLogService;
import com.penghaisoft.wcs.operation.model.AgvPositionItem;
import com.penghaisoft.wcs.operation.model.AgvTask;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import com.penghaisoft.wcs.operation.service.FourwaycarOperationService;
import com.penghaisoft.wcs.operation.service.PalletizingOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskPlaneDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @Description 暴露给agv rcs系统的接口
 * @Date 2020/7/17 17:14
 * * zhangx
 **/
@RestController
@RequestMapping(value = "/agv")
@Slf4j
public class ExposeForAgvController {

    @Autowired
    private ITaskDispatchService taskDispatchService;

    @Autowired
    private ITaskPlaneDispatchService taskPlaneDispatchService;

    @Autowired
    private IWcsAgvReportLogService agvReportLogService;

    @Autowired
    private FourwaycarOperationService fourwaycarOperationService;

    @Autowired
    private AgvOperationService agvOperationService;

    @Autowired
    private PalletizingOperationService palletizingOperationService;

    @Value("${wcs.config.agv-task-typ-pallet}")
    private String agvPalletTaskTyp;


    /**
     * 处理agv的回调
     *
     * @param agvCallback
     * @return
     */
    @PostMapping("agvCallbackService/agvCallback")
    public AgvResult handleAgvCallBack(@RequestBody AgvCallback agvCallback) {
        agvReportLogService.addReportLog(agvCallback);
        String reqCode = agvCallback.getReqCode();
//        根据任务号进行状态修改
        AgvResult agvResult = new AgvResult();
        String taskCodeStr = agvCallback.getTaskCode();
        Integer taskCode = Integer.parseInt(taskCodeStr);

        agvResult.setReqCode(reqCode);
        agvResult.setCode("0");

        String operator = "agvCallBack";
//       当前位置
        String currentPositionCode = agvCallback.getCurrentPositionCode();

        WcsAgvTask wcsAgvTask = taskDispatchService.findAgvTaskByTaskNo(taskCode);
        log.info("AGV上报{}任务完成,编号{}的设备位于位置:{}", taskCode, agvCallback.getRobotCode(), currentPositionCode);
//      --------------------------处理立库AGV回调-----------------------
        if (wcsAgvTask != null) {
//            任务状态1创建；2下发；3到达缓冲区/到达托盘出库口；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
            Long priority = 0L;
            if ("X1".equals(currentPositionCode)) {
//                到达立库入库缓存区,修改状态2->3,写入优先级0
                taskDispatchService.setAgvArriveBufferStatus(taskCode, priority.intValue());
            } else if (("H1").equals(currentPositionCode)) {
//                到达立库入库口
                Resp resp = taskDispatchService.finishAgvTaskByTaskNo(taskCode, operator);
                if (null == resp) {
                    agvResult.setCode("1");
                    agvResult.setReqCode(reqCode);
                    agvResult.setMessage(reqCode + " 不存在或已完成！");
                } else {
//                上报速锐WCS,入库口托盘已经放置到位
                    fourwaycarOperationService.palletInReady(wcsAgvTask.getPalletCode());
                }
            } else if (("E").equals(currentPositionCode)) {
//                叫托盘-立库出库口，E
                taskDispatchService.setAgvArriveBufferStatus(taskCode, priority.intValue());
                sendCallPalletCoutinueTaskAsync(taskCode);
            } else if (("m3").equals(currentPositionCode)) {
//                叫托盘-码垛线，结束整体叫托盘任务
//                码垛机接受完成 置2
                Resp resp = taskDispatchService.finishCallPalletTaskByTaskNo(taskCode, operator);
                if (null == resp) {
                    agvResult.setCode("1");
                    agvResult.setReqCode(reqCode);
                    agvResult.setMessage(reqCode + " 不存在或已完成！");
                } else {
//                    AGV把货物放到码垛线上了
                    palletizingOperationService.setPalletReceiveFinish((short) 2);
                }
            } else {
                log.error("地址{}不存在！！", currentPositionCode);
            }
            return agvResult;
        }

//      --------------------------处理m6,m7入平库AGV回调-----------------------
        WcsAgvTaskPlane wcsAgvTaskPlane = taskPlaneDispatchService.findAgvTaskByTaskNo(taskCode);
        if (wcsAgvTaskPlane != null) {
//            任务状态1创建；2下发；3到达缓冲区/到达托盘出库口；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
            Long priority = 0L;
            if ("Y".equals(currentPositionCode)) {
//                到达立库入库缓存区,修改状态2->3,写入优先级0
                taskPlaneDispatchService.setAgvArriveBufferStatus(taskCode, priority.intValue());
            } else if (("1006").equals(currentPositionCode)) {
//                到达立库入库口
                Resp resp = taskPlaneDispatchService.finishAgvTaskByTaskNo(taskCode, operator);
                if (null == resp) {
                    agvResult.setCode("1");
                    agvResult.setReqCode(reqCode);
                    agvResult.setMessage(reqCode + " 不存在或已完成！");
                }
            } else {
                log.error("地址{}不存在！！", currentPositionCode);
            }
        } else {
            log.info("m6,m7 {}不存在", taskCode);
            agvResult.setMessage(taskCode + "不存在!");
            agvResult.setCode("1");
            return agvResult;
        }

        return agvResult;
    }

    /**
     * 开启新线程下发叫托盘的AGV任务
     *
     * @param taskCode
     */
    public void sendCallPalletCoutinueTaskAsync(Integer taskCode) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AgvTask agvTask = new AgvTask();
                agvTask.setReqCode(UUID.randomUUID().toString().replace("-", ""));
                agvTask.setTaskCode(taskCode.toString());
                agvTask.setTaskTyp(agvPalletTaskTyp);
                AgvPositionItem nextAddr = new AgvPositionItem();
                nextAddr.setPositionCode("m3");
                nextAddr.setType("00");
                agvTask.setNextPositionCode(nextAddr);

                Resp resp = agvOperationService.sendContinueTask(agvTask);
                if ("1".equals(resp.getCode())) {
                    JSONObject data = (JSONObject) resp.getData();
                    if ("0".equals(data.getString("code"))) {
//           //        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
                        taskDispatchService.setAgvStatusByTaskNo(taskCode, "5", "sendCallPalletCoutinueTaskAsync");
                    }
                }
            }
        });

        thread.start();
    }


}
