package com.penghaisoft.wcs.task.dispatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsAddressService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsAddress;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.operation.model.AgvPositionItem;
import com.penghaisoft.wcs.operation.model.AgvTask;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import com.penghaisoft.wcs.operation.service.FourwaycarOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskSplitService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
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
 * @ClassName AgvDispatchTask
 * @Description agv任务调度处理
 * @Author zhangx
 * @Date 2020/7/28 13:38
 **/
@Slf4j
@ConditionalOnProperty(prefix = "jobs.open",name = "agv",havingValue = "true")
@Component
public class AgvDispatchTask {

    @Autowired
    private ITaskSplitService taskSplitService;

    @Autowired
    private ITaskDispatchService taskDispatchService;

    @Autowired
    private AgvOperationService agvOperationService;

    @Autowired
    private FourwaycarOperationService fourwaycarOperationService;

    @Autowired
    private IWcsAddressService wcsAddressService;

    @Autowired
    private IWcsErrorLogService wcsErrorLogService;

    @Autowired
    private IWcsCommonService wcsCommonService;

    /**
     * 码垛线入库地址
     */
    private static Integer[] IN_PATH_ARR = {1,2,4,5};

    @Value("${wcs.config.agv-task-typ}")
    private String agvTaskTyp;

    /**
     * agv任务调度，在这里进行任务的下发
     */
    @Scheduled(cron = "${jobs.agv-dispatch.cron}")
    public void scheduled() {

//        log.info("agv 调度");
//        ----------------处理下线入库----------------
//        理论上agv任务表中，每条码垛线最多只有一个创建状态的任务，
//        当agv把托盘拿走，才会产生第二个创建任务，但这时候第一个创建任务已经变成继续任务状态了
        for (Integer pathId:IN_PATH_ARR) {
//         根据码垛线找到创建状态的任务下发，成功后状态=2,wcs_task=2 下发
            WcsAgvTask waitStartAgvTask = taskDispatchService.findWaitingStartAgvTaskByPath(pathId);
            if (null!=waitStartAgvTask){
                log.info("调度AGV到缓冲区{}",waitStartAgvTask.getPalletCode());
                handleStartProductInTask(waitStartAgvTask);
            }
        }
//        ----------------处理等待在缓冲区的托盘----------------
        WcsAgvTask waitBuffAgvTask = taskDispatchService.findEarlyWaitingInAgvTask();
        if (null!=waitBuffAgvTask) {
            log.info("处理已经到达缓冲区的托盘{}",waitBuffAgvTask.getPalletCode());
            Long taskId = waitBuffAgvTask.getTaskId();
            String taskType = wcsCommonService.getTaskTypeByTaskId(taskId);
            Long taskTypeLong = Long.parseLong(taskType);

            if(Constant.TaskType.PRODUCT_IN.getTaskType()==taskTypeLong){
//            10 生产入库
                log.info("下发继续任务{}",waitBuffAgvTask.getPalletCode());
                handleWaitingContinueProductInTask(waitBuffAgvTask);
            }else {
                log.error("在缓存区等待的不可能的任务类型{},任务id={}",taskType,taskId);
            }

        }

//        ----------------处理刚到达缓冲区的托盘----------------
        List<WcsAgvTask> inBuffAgvTasks = taskDispatchService.findAgvTaskByStatus("3");
        for (WcsAgvTask inBuffAgvTask:inBuffAgvTasks) {
            log.info("处理刚刚到达缓冲区的托盘{}",inBuffAgvTask.getPalletCode());
            Long taskId = inBuffAgvTask.getTaskId();
            String taskType = wcsCommonService.getTaskTypeByTaskId(taskId);
            Long taskTypeLong = Long.parseLong(taskType);

//            if(Constant.TaskType.VIRTUAL_PALLET_OUT.getTaskType()==taskTypeLong){
//            25叫托盘,这里的缓冲区实际上就是3001口--叫托盘优先级最高，是不是应该在四向车上报直接掉？
//            }

            if(Constant.TaskType.PRODUCT_IN.getTaskType()==taskTypeLong){
//            10 生产入库
                log.info("下发继续任务{}",inBuffAgvTask.getPalletCode());
                handleContinueProductInTask(inBuffAgvTask);
            }else {
                log.error("不可能的任务类型{},任务id={}",taskType,taskId);
            }

        }

    }

    /**
    * @Description 处理在缓存区等待入库的任务
    * @Date 2020/7/28 13:08
    * @param waitingBuffAgvTask
    * @return void
    **/
    private void handleWaitingContinueProductInTask(WcsAgvTask waitingBuffAgvTask) {
        log.info("下发等待区的agv任务{}",waitingBuffAgvTask.getPalletCode());
        if (fourwaycarOperationService.inLocAllow()){
            log.info("立库入库口可用");
            //                    占用入库口
            if (fourwaycarOperationService.occupyInLoc(waitingBuffAgvTask.getPalletCode())){
                log.info("立库占位成功");
                String operator = this.getClass().getSimpleName();
//        1 调用agv操作类下发任务
                AgvTask agvTask = transContinueTaskModel(waitingBuffAgvTask);
                Resp resp = agvOperationService.sendContinueTask(agvTask);
                String callCode = resp.getCode();
//        2-1下发成功更新状态
                if ("1".equals(callCode)){//调用接口成功
                    //        接口返回demo
/*        {
            "code": “0”,
            "data": "",
            "message": "成功",
            "reqCode": "468513"
        }*/
                    JSONObject agvResult = (JSONObject)resp.getData();
                    if ("0".equals(agvResult.getString("code"))){
                        taskDispatchService.continueAgvTaskSuccess(waitingBuffAgvTask.getAgvTaskId(),operator);

                    }else{
//                业务异常
                        taskDispatchService.continueAgvTaskFail(waitingBuffAgvTask.getAgvTaskId(),agvResult,operator);
                    }

//        2-2下发失败记录
                }else if("0".equals(callCode)){//接口报错
                    taskDispatchService.continueAgvTaskError(waitingBuffAgvTask.getAgvTaskId(),resp.getMsg(),operator);
                    wcsErrorLogService.addAgvLog(4,"21",(short)500,(short)2,"agvDispatchTask", JSON.toJSONString(agvTask),"下发AGV继续任务失败-服务端异常");
                }else if("-1".equals(callCode)){//未发送成功
                    taskDispatchService.continueAgvTaskError(waitingBuffAgvTask.getAgvTaskId(),resp.getMsg(),operator);
                    wcsErrorLogService.addAgvLog(4,"22",(short)-1,(short)2,"agvDispatchTask", JSON.toJSONString(agvTask),"下发AGV继续任务失败-客户端异常");
                }else {
                    log.error("不可能的调用AGV产生任务单结果编码={}",callCode);
                }
            }


        }


    }


    /**
     * 处理等待继续下发的任务
     * 这里是到达X区域的托盘
     * @param willInTask
     */
    private void handleContinueProductInTask(WcsAgvTask willInTask) {
        if (fourwaycarOperationService.inLocAllow()){

            if (fourwaycarOperationService.occupyInLoc(willInTask.getPalletCode())){

                String operator = this.getClass().getSimpleName();
//        1 调用agv操作类下发任务
                AgvTask agvTask = transContinueTaskModel(willInTask);
                Resp resp = agvOperationService.sendContinueTask(agvTask);
                String callCode = resp.getCode();
//        2-1下发成功更新状态
                if ("1".equals(callCode)){//调用接口成功
                    //        接口返回demo
/*        {
            "code": “0”,
            "data": "",
            "message": "成功",
            "reqCode": "468513"
        }*/
                    JSONObject agvResult = (JSONObject)resp.getData();
                    if ("0".equals(agvResult.getString("code"))){
                        taskDispatchService.continueAgvTaskSuccess(willInTask.getAgvTaskId(),operator);
                    }else{
//                业务异常
                        taskDispatchService.continueAgvTaskFail(willInTask.getAgvTaskId(),agvResult,operator);
                    }

//        2-2下发失败记录
                }else if("0".equals(callCode)){//接口报错
                    taskDispatchService.continueAgvTaskError(willInTask.getAgvTaskId(),resp.getMsg(),operator);
                    wcsErrorLogService.addAgvLog(4,"21",(short)500,(short)2,"agvDispatchTask", JSON.toJSONString(agvTask),"下发AGV继续任务失败-服务端异常");
                }else if("-1".equals(callCode)){//未发送成功
                    taskDispatchService.continueAgvTaskError(willInTask.getAgvTaskId(),resp.getMsg(),operator);
                    wcsErrorLogService.addAgvLog(4,"22",(short)-1,(short)2,"agvDispatchTask", JSON.toJSONString(agvTask),"下发AGV继续任务失败-客户端异常");
                }else {
                    log.error("不可能的调用AGV产生任务单结果编码={}",callCode);
                }
            }


        }else {
//            因为入库口不可用，agv任务设置为等待
            Long priority = System.currentTimeMillis();
            taskDispatchService.setAgvWaitingBuffStatus(willInTask.getAgvTaskId(),"AgvDispatchTask",Math.abs(priority.intValue()));
        }


    }

    /**
     * 转换继续任务对象
     * @param waitContinueAgvTask
     * @return
     */
    private AgvTask transContinueTaskModel(WcsAgvTask waitContinueAgvTask) {
        String newReqCode = UUID.randomUUID().toString().replace("-","");
        AgvTask agvTask = new AgvTask();
        agvTask.setReqCode(newReqCode);
        agvTask.setTaskTyp(agvTaskTyp);
        agvTask.setTaskCode(waitContinueAgvTask.getTaskNo().toString());
////        路径id
//        Integer pathId = waitContinueAgvTask.getPathId();
////        通过path id 找到路径列表
//        WcsAddress endAddress = wcsAddressService.findEndAddressByPathId(pathId);
//        写死
        AgvPositionItem nextAddr = new AgvPositionItem();
        nextAddr.setPositionCode("H1");
        nextAddr.setType("00");
        agvTask.setNextPositionCode(nextAddr);
        return agvTask;
    }


    /**
     * 下发agv任务-申请任务单-下线入库使用
     * @param waitStartAgvTask
     */
    private void handleStartProductInTask(WcsAgvTask waitStartAgvTask){
        String operator = this.getClass().getSimpleName();
//        1 调用agv操作类下发任务
        AgvTask agvTask = transSendTaskModel(waitStartAgvTask);
        Resp resp = agvOperationService.sendTask(agvTask);
        String callCode = resp.getCode();
//        2-1下发成功更新状态
        if ("1".equals(callCode)){//调用接口成功
            //        接口返回demo
/*        {
            "code": “0”,
            "data": "F01169C808C317111G",
            "message": "成功",
            "reqCode": "468513"
        }*/
            JSONObject agvResult = (JSONObject)resp.getData();
            if ("0".equals(agvResult.getString("code"))){
//                taskDispatchService.startAgvTaskSuccess(waitStartAgvTask.getAgvTaskId(),operator);
                taskDispatchService.startProductInTaskSuccess(waitStartAgvTask.getTaskId(),waitStartAgvTask.getAgvTaskId(),operator);
            }else{
//                业务异常
//                taskDispatchService.startAgvTaskFail(waitStartAgvTask.getAgvTaskId(),agvResult,operator);
                taskDispatchService.startProductInTaskFail(waitStartAgvTask.getTaskId(),waitStartAgvTask.getAgvTaskId(),agvResult,operator);
            }

//        2-2下发失败记录
        }else if("0".equals(callCode)){//接口报错
//            taskDispatchService.startAgvTaskError(waitStartAgvTask.getAgvTaskId(),resp.getMsg(),operator);
            taskDispatchService.startProductInTaskError(waitStartAgvTask.getTaskId(),waitStartAgvTask.getAgvTaskId(),resp.getMsg(),operator);
            wcsErrorLogService.addAgvLog(4,"21",(short)500,(short)2,"agvDispatchTask", JSON.toJSONString(agvTask),"下线入库申请AGV任务单服务端异常");
        }else if("-1".equals(callCode)){//未发送成功
//            taskDispatchService.startAgvTaskError(waitStartAgvTask.getAgvTaskId(),resp.getMsg(),operator);
            taskDispatchService.startProductInTaskError(waitStartAgvTask.getTaskId(),waitStartAgvTask.getAgvTaskId(),resp.getMsg(),operator);
            wcsErrorLogService.addAgvLog(4,"22",(short)-1,(short)2,"agvDispatchTask", JSON.toJSONString(agvTask),"下线入库申请AGV任务单客户端异常");
        }else {
            log.error("不可能的调用AGV产生任务单结果编码={}",callCode);
        }

    }


    /**
     * 将数据库对象转换为任务对象
     * @param waitStartAgvTask
     * @return
     */
    private AgvTask transSendTaskModel(WcsAgvTask waitStartAgvTask) {
        String reqCode = UUID.randomUUID().toString().replace("-","");
        AgvTask agvTask = new AgvTask();
        agvTask.setReqCode(reqCode);
        agvTask.setTaskCode(waitStartAgvTask.getTaskNo().toString());
        agvTask.setTaskTyp(agvTaskTyp);
//        路径id
        Integer pathId = waitStartAgvTask.getPathId();
//        通过path id 找到路径列表
        List<WcsAddress> addressList = wcsAddressService.findByPathId(pathId);
//        根据规则生成位置编号数据
        List<AgvPositionItem> agvPositionItemList = new ArrayList<>();
        for (WcsAddress address:addressList) {
            AgvPositionItem item = new AgvPositionItem();
            item.setPositionCode(address.getUserDefined1());
            item.setType(address.getUserDefined2());
            agvPositionItemList.add(item);
        }
        agvTask.setPositionCodePath(agvPositionItemList);
        return agvTask;
    }


}
