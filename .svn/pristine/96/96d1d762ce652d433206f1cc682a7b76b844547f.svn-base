package com.penghaisoft.wcs.task.dispatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsAddressService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsAddress;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.operation.constant.DeviceConstant;
import com.penghaisoft.wcs.operation.model.AgvPositionItem;
import com.penghaisoft.wcs.operation.model.AgvTask;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import com.penghaisoft.wcs.operation.service.PalletizingOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskPlaneDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description agv任务调度处理【其它码垛线】
 * @ClassName AgvPlaneDispatchTask
 * @Author luot
 * @Date 2020/7/29 17:43
 **/
@Slf4j
@ConditionalOnProperty(prefix = "jobs.open", name = "agv-other", havingValue = "true")
@Component
public class AgvPlaneDispatchTask {

    @Autowired
    private ITaskPlaneDispatchService taskPlaneDispatchService;

    @Autowired
    private AgvOperationService agvOperationService;

    @Autowired
    private IWcsAddressService wcsAddressService;

    @Autowired
    private IWcsErrorLogService wcsErrorLogService;

    @Autowired
    private IWcsCommonService wcsCommonService;

    @Autowired
    private PalletizingOperationService palletizingOperationService;

    /**
     * 码垛线入库地址
     */
    private static Integer[] IN_PATH_ARR = {6, 7};

    @Value("${wcs.config.agv-task-typ-m67}")
    private String agvTaskTypM67;

    /**
     * agv任务调度，在这里进行任务的下发
     */
    @Scheduled(cron = "${jobs.agv-other-dispatch.cron}")
    public void scheduled() {

//        ----------------处理下线入库----------------
//        理论上agv任务表中，每条码垛线最多只有一个创建状态的任务，
//        当agv把托盘拿走，才会产生第二个创建任务，但这时候第一个创建任务已经变成继续任务状态了
        for (Integer pathId : IN_PATH_ARR) {
//         根据码垛线找到创建状态的任务下发，成功后状态=2,wcs_task=2 下发
            WcsAgvTaskPlane waitStartAgvTask = taskPlaneDispatchService.findWaitingStartAgvTaskByPath(pathId);
            if (null != waitStartAgvTask) {
                handleStartProductInTask(waitStartAgvTask);
            }
        }
//        ----------------处理等待在缓冲区的托盘----------------
        WcsAgvTaskPlane waitBuffAgvTask = taskPlaneDispatchService.findEarlyWaitingInAgvTask();
        if (null != waitBuffAgvTask) {
            Long taskId = waitBuffAgvTask.getTaskId();
            String taskType = wcsCommonService.getTaskTypeByTaskId(taskId);
            Long taskTypeLong = Long.parseLong(taskType);

            if (Constant.TaskType.PRODUCT_IN.getTaskType() == taskTypeLong) {
//            10 生产入库
                handleWaitingContinueProductInTask(waitBuffAgvTask);
            } else {
                log.error("在缓存区等待的不可能的任务类型{},任务id={}", taskType, taskId);
            }
        }

//        ----------------处理刚到达缓冲区的托盘----------------
        List<WcsAgvTaskPlane> inBuffAgvTasks = taskPlaneDispatchService.findAgvTaskByStatus("3");
        for (WcsAgvTaskPlane inBuffAgvTask : inBuffAgvTasks) {
            Long taskId = inBuffAgvTask.getTaskId();
            String taskType = wcsCommonService.getTaskTypeByTaskId(taskId);
            Long taskTypeLong = Long.parseLong(taskType);

//            if(Constant.TaskType.VIRTUAL_PALLET_OUT.getTaskType()==taskTypeLong){
//            25叫托盘,这里的缓冲区实际上就是3001口--叫托盘优先级最高，是不是应该在四向车上报直接掉？
//            }

            if (Constant.TaskType.PRODUCT_IN.getTaskType() == taskTypeLong) {
//            10 生产入库
                handleContinueProductInTask(inBuffAgvTask);
            } else {
                log.error("不可能的任务类型{},任务id={}", taskType, taskId);
            }
        }
    }

    /**
     * @param waitingBuffAgvTask
     * @return void
     * @Description 处理在缓存区等待入库的任务
     * @Date 2020/7/28 13:08
     **/
    private void handleWaitingContinueProductInTask(WcsAgvTaskPlane waitingBuffAgvTask) {
//        if (!DeviceConstant.PALLETIZER.isConnect()){
//            log.error("堆垛机PLC未连接！");
//            return;
//        }
//        平库的
//        1允许 0禁止
        if (DeviceConstant.PALLETIZER.getH1AllowPallet() == 1 || DeviceConstant.PALLETIZER.getH2AllowPallet() == 1) {
            String targetPosition = "";
//            写plc，占用平库入库口
            if (DeviceConstant.PALLETIZER.getH1AllowPallet() == 1) {
                palletizingOperationService.setInTask("H1");
                targetPosition = "H1";
            } else if (DeviceConstant.PALLETIZER.getH2AllowPallet() == 1) {
                palletizingOperationService.setInTask("H2");
                targetPosition = "H2";
            }

            String operator = this.getClass().getSimpleName();
//        1 调用agv操作类下发任务
            AgvTask agvTask = transContinueTaskModel(waitingBuffAgvTask, targetPosition);
            Resp resp = agvOperationService.sendContinueTask(agvTask);
            String callCode = resp.getCode();
//        2-1下发成功更新状态
            if ("1".equals(callCode)) {//调用接口成功
                //        接口返回demo
/*        {
            "code": “0”,
            "data": "",
            "message": "成功",
            "reqCode": "468513"
        }*/
                JSONObject agvResult = (JSONObject) resp.getData();
                if ("0".equals(agvResult.getString("code"))) {
                    taskPlaneDispatchService.continueAgvTaskSuccess(waitingBuffAgvTask.getAgvTaskId(), operator);

                } else {
//                业务异常
                    taskPlaneDispatchService.continueAgvTaskFail(waitingBuffAgvTask.getAgvTaskId(), agvResult, operator);
                }

//        2-2下发失败记录
            } else if ("0".equals(callCode)) {//接口报错
                taskPlaneDispatchService.continueAgvTaskError(waitingBuffAgvTask.getAgvTaskId(), resp.getMsg(), operator);
                wcsErrorLogService.addAgvLog(4, "21", (short) 500, (short) 2, "agvDispatchTask", JSON.toJSONString(agvTask), "下发AGV继续任务失败-服务端异常");
            } else if ("-1".equals(callCode)) {//未发送成功
                taskPlaneDispatchService.continueAgvTaskError(waitingBuffAgvTask.getAgvTaskId(), resp.getMsg(), operator);
                wcsErrorLogService.addAgvLog(4, "22", (short) -1, (short) 2, "agvDispatchTask", JSON.toJSONString(agvTask), "下发AGV继续任务失败-客户端异常");
            } else {
                log.error("不可能的调用AGV产生任务单结果编码={}", callCode);
            }
        }
    }


    /**
     * 处理等待继续下发的任务
     * 这里是到达X区域的托盘
     *
     * @param willInTask
     */
    private void handleContinueProductInTask(WcsAgvTaskPlane willInTask) {
//        if (!DeviceConstant.PALLETIZER.isConnect()){
//            log.error("堆垛机PLC未连接！");
//            return;
//        }
//        平库的
//        1允许 0禁止
        if (DeviceConstant.PALLETIZER.getH1AllowPallet() == 1 || DeviceConstant.PALLETIZER.getH2AllowPallet() == 1) {
            String targetPosition = "";
//            写plc，占用平库入库口
            if (DeviceConstant.PALLETIZER.getH1AllowPallet() == 1) {
                palletizingOperationService.setInTask("H1");
                targetPosition = "H1";
            } else if (DeviceConstant.PALLETIZER.getH2AllowPallet() == 1) {
                palletizingOperationService.setInTask("H2");
                targetPosition = "H2";
            }

            String operator = this.getClass().getSimpleName();
//        1 调用agv操作类下发任务
            AgvTask agvTask = transContinueTaskModel(willInTask, targetPosition);
            Resp resp = agvOperationService.sendContinueTask(agvTask);
            String callCode = resp.getCode();
//        2-1下发成功更新状态
            if ("1".equals(callCode)) {//调用接口成功
                //        接口返回demo
/*        {
            "code": “0”,
            "data": "",
            "message": "成功",
            "reqCode": "468513"
        }*/
                JSONObject agvResult = (JSONObject) resp.getData();
                if ("0".equals(agvResult.getString("code"))) {
                    taskPlaneDispatchService.continueAgvTaskSuccess(willInTask.getAgvTaskId(), operator);
                } else {
//                业务异常
                    taskPlaneDispatchService.continueAgvTaskFail(willInTask.getAgvTaskId(), agvResult, operator);
                }

//        2-2下发失败记录
            } else if ("0".equals(callCode)) {//接口报错
                taskPlaneDispatchService.continueAgvTaskError(willInTask.getAgvTaskId(), resp.getMsg(), operator);
                wcsErrorLogService.addAgvLog(4, "21", (short) 500, (short) 2, "agvDispatchTask", JSON.toJSONString(agvTask), "下发AGV继续任务失败-服务端异常");
            } else if ("-1".equals(callCode)) {//未发送成功
                taskPlaneDispatchService.continueAgvTaskError(willInTask.getAgvTaskId(), resp.getMsg(), operator);
                wcsErrorLogService.addAgvLog(4, "22", (short) -1, (short) 2, "agvDispatchTask", JSON.toJSONString(agvTask), "下发AGV继续任务失败-客户端异常");
            } else {
                log.error("不可能的调用AGV产生任务单结果编码={}", callCode);
            }
        } else {
//            因为入库口不可用，agv任务设置为等待
            Long priority = System.currentTimeMillis();
            taskPlaneDispatchService.setAgvWaitingBuffStatus(willInTask.getAgvTaskId(), "AgvDispatchTask", Math.abs(priority.intValue()));
        }
    }

    /**
     * 转换继续任务对象
     *
     * @param waitContinueAgvTask
     * @return
     */
    private AgvTask transContinueTaskModel(WcsAgvTaskPlane waitContinueAgvTask, String targetPosition) {
        String newReqCode = UUID.randomUUID().toString().replace("-", "");
        AgvTask agvTask = new AgvTask();
        agvTask.setReqCode(newReqCode);
        agvTask.setTaskTyp(agvTaskTypM67);
        agvTask.setTaskCode(waitContinueAgvTask.getTaskNo().toString());
        AgvPositionItem nextAddr = new AgvPositionItem();
        nextAddr.setPositionCode(targetPosition);
        nextAddr.setType("00");
        agvTask.setNextPositionCode(nextAddr);
        return agvTask;
    }


    /**
     * 下发agv任务-申请任务单-下线入库使用
     *
     * @param waitStartAgvTask
     */
    private void handleStartProductInTask(WcsAgvTaskPlane waitStartAgvTask) {
        String operator = this.getClass().getSimpleName();
//        1 调用agv操作类下发任务
        AgvTask agvTask = transSendTaskModel(waitStartAgvTask);
        Resp resp = agvOperationService.sendTask(agvTask);
        String callCode = resp.getCode();
//        2-1下发成功更新状态
        if ("1".equals(callCode)) {//调用接口成功
            //        接口返回demo
/*        {
            "code": “0”,
            "data": "F01169C808C317111G",
            "message": "成功",
            "reqCode": "468513"
        }*/
            JSONObject agvResult = (JSONObject) resp.getData();
            if ("0".equals(agvResult.getString("code"))) {
//                taskPlaneDispatchService.startAgvTaskSuccess(waitStartAgvTask.getAgvTaskId(),operator);
                taskPlaneDispatchService.startProductInTaskSuccess(waitStartAgvTask.getTaskId(), waitStartAgvTask.getAgvTaskId(), operator);
            } else {
//                业务异常
//                taskPlaneDispatchService.startAgvTaskFail(waitStartAgvTask.getAgvTaskId(),agvResult,operator);
                taskPlaneDispatchService.startProductInTaskFail(waitStartAgvTask.getTaskId(), waitStartAgvTask.getAgvTaskId(), agvResult, operator);
            }

//        2-2下发失败记录
        } else if ("0".equals(callCode)) {//接口报错
//            taskPlaneDispatchService.startAgvTaskError(waitStartAgvTask.getAgvTaskId(),resp.getMsg(),operator);
            taskPlaneDispatchService.startProductInTaskError(waitStartAgvTask.getTaskId(), waitStartAgvTask.getAgvTaskId(), resp.getMsg(), operator);
            wcsErrorLogService.addAgvLog(4, "21", (short) 500, (short) 2, "agvDispatchTask", JSON.toJSONString(agvTask), "下线入库申请AGV任务单服务端异常");
        } else if ("-1".equals(callCode)) {//未发送成功
//            taskPlaneDispatchService.startAgvTaskError(waitStartAgvTask.getAgvTaskId(),resp.getMsg(),operator);
            taskPlaneDispatchService.startProductInTaskError(waitStartAgvTask.getTaskId(), waitStartAgvTask.getAgvTaskId(), resp.getMsg(), operator);
            wcsErrorLogService.addAgvLog(4, "22", (short) -1, (short) 2, "agvDispatchTask", JSON.toJSONString(agvTask), "下线入库申请AGV任务单客户端异常");
        } else {
            log.error("不可能的调用AGV产生任务单结果编码={}", callCode);
        }

    }


    /**
     * 将数据库对象转换为任务对象
     *
     * @param waitStartAgvTask
     * @return
     */
    private AgvTask transSendTaskModel(WcsAgvTaskPlane waitStartAgvTask) {
        String reqCode = UUID.randomUUID().toString().replace("-", "");
        AgvTask agvTask = new AgvTask();
        agvTask.setReqCode(reqCode);
        agvTask.setTaskCode(waitStartAgvTask.getTaskNo().toString());
        agvTask.setTaskTyp(agvTaskTypM67);
//        路径id
        Integer pathId = waitStartAgvTask.getPathId();
//        通过path id 找到路径列表
        List<WcsAddress> addressList = wcsAddressService.findByPathId(pathId);
//        根据规则生成位置编号数据
        List<AgvPositionItem> agvPositionItemList = new ArrayList<>();
        for (WcsAddress address : addressList) {
            AgvPositionItem item = new AgvPositionItem();
            item.setPositionCode(address.getUserDefined1());
            item.setType(address.getUserDefined2());
            agvPositionItemList.add(item);
        }
        agvTask.setPositionCodePath(agvPositionItemList);
        return agvTask;
    }


}
