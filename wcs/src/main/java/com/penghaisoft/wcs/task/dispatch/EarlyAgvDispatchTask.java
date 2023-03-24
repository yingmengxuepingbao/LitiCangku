package com.penghaisoft.wcs.task.dispatch;

import com.alibaba.fastjson.JSON;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.operation.constant.DeviceConstant;
import com.penghaisoft.wcs.operation.model.AgvPositionItem;
import com.penghaisoft.wcs.operation.model.AgvTask;
import com.penghaisoft.wcs.operation.model.EarlyInLocOccupyInfo;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import com.penghaisoft.wcs.operation.service.PalletizingOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.UUID;

/**
 * @ClassName EarlyAgvDispatchTask
 * @Description 前期入库的agv补偿任务
 * 找到在缓冲区等待的agv进行入库
 * @Author zhangx
 * @Date 2020/7/11 11:13
 **/
@Slf4j
@ConditionalOnProperty(prefix = "jobs.open",name = "early-agv",havingValue = "true")
@Component
public class EarlyAgvDispatchTask {

    @Autowired
    private ITaskDispatchService taskDispatchService;

    @Autowired
    private AgvOperationService agvOperationService;

    @Autowired
    private PalletizingOperationService palletizingOperationService;

    @Value("${wcs.config.agv-task-typ}")
    private String agvTaskTyp;

    /**
     *  找到在缓冲区等待状态的agv任务,如果入库口没货就放
     */
    @Scheduled(cron = "${jobs.agv-dispatch.cron}")
    public void scheduled() {

        EarlyInLocOccupyInfo occupyInfo = palletizingOperationService.getEarlyInLocOccupyInfo();
        if (null!=occupyInfo){

            if (occupyInfo.getH1AllowPallet()&&!occupyInfo.getH1HasJob()){
                //          缓冲区等待入库的任务
                WcsAgvTask wcsAgvTask = taskDispatchService.findEarlyWaitingInAgvTask();
                if (null!=wcsAgvTask){
                    log.info("将在缓存区的任务{}下发入库给RCS 入H1",wcsAgvTask.getTaskNo());
                    Integer taskCode = wcsAgvTask.getTaskNo();
                    Resp resp = agvOperationService.sendContinueTask(transContinueTaskModel(taskCode,"H1"));
                    if ("1".equals(resp.getCode())){
                        taskDispatchService.setEarlyAgvStatusByTaskNo(taskCode,"5");

                        palletizingOperationService.setInTask("H1");
                        occupyInfo.setH1HasJob(true);
                    }
                }
            }

            if (occupyInfo.getH2AllowPallet()&&!occupyInfo.getH2HasJob()){
                WcsAgvTask wcsAgvTask = taskDispatchService.findEarlyWaitingInAgvTask();
                if (null!=wcsAgvTask){
                    log.info("将在缓存区的任务{}下发入库给RCS 入H2",wcsAgvTask.getTaskNo());
                    Integer taskCode = wcsAgvTask.getTaskNo();
                    Resp resp = agvOperationService.sendContinueTask(transContinueTaskModel(taskCode,"H2"));
                    if ("1".equals(resp.getCode())){
                        taskDispatchService.setEarlyAgvStatusByTaskNo(taskCode,"5");

                        palletizingOperationService.setInTask("H2");
                        occupyInfo.setH2HasJob(true);
                    }
                }
            }

        }


    }

    /**
     * 进一步减少与PLC的交互
     */
    public void scheduled2() {
        WcsAgvTask wcsAgvTask = taskDispatchService.findEarlyWaitingInAgvTask();
        if (null!=wcsAgvTask){
//            查询占位信息
            EarlyInLocOccupyInfo occupyInfo = palletizingOperationService.getEarlyInLocOccupyInfo();
            if (null!=occupyInfo){
//              先入H1
                if (occupyInfo.getH1AllowPallet()&&!occupyInfo.getH1HasJob()){
                    //          缓冲区等待入库的任务
                    log.info("将在缓存区的任务{}下发入库给RCS 入H1",wcsAgvTask.getTaskNo());
                    Integer taskCode = wcsAgvTask.getTaskNo();
                    Resp resp = agvOperationService.sendContinueTask(transContinueTaskModel(taskCode,"H1"));
                    if ("1".equals(resp.getCode())){
                        taskDispatchService.setEarlyAgvStatusByTaskNo(taskCode,"5");

                        palletizingOperationService.setInTask("H1");
                        occupyInfo.setH1HasJob(true);
//                        任务清空
                        wcsAgvTask = null;
                    }
                }

//                如果没入H1入H2
                if (null != wcsAgvTask){

                    if (occupyInfo.getH2AllowPallet()&&!occupyInfo.getH2HasJob()){
                        log.info("将在缓存区的任务{}下发入库给RCS 入H2",wcsAgvTask.getTaskNo());
                        Integer taskCode = wcsAgvTask.getTaskNo();
                        Resp resp = agvOperationService.sendContinueTask(transContinueTaskModel(taskCode,"H2"));
                        if ("1".equals(resp.getCode())){
                            taskDispatchService.setEarlyAgvStatusByTaskNo(taskCode,"5");

                            palletizingOperationService.setInTask("H2");
                            occupyInfo.setH2HasJob(true);
                        }
                    }
                }else{
//                    在查找一个等待入库的任务
                    wcsAgvTask = taskDispatchService.findEarlyWaitingInAgvTask();
                    if (null != wcsAgvTask){
                        if (occupyInfo.getH2AllowPallet()&&!occupyInfo.getH2HasJob()){
                            log.info("将在缓存区的任务{}下发入库给RCS 入H2",wcsAgvTask.getTaskNo());
                            Integer taskCode = wcsAgvTask.getTaskNo();
                            Resp resp = agvOperationService.sendContinueTask(transContinueTaskModel(taskCode,"H2"));
                            if ("1".equals(resp.getCode())){
                                taskDispatchService.setEarlyAgvStatusByTaskNo(taskCode,"5");

                                palletizingOperationService.setInTask("H2");
                                occupyInfo.setH2HasJob(true);
                            }
                        }
                    }
                }

            }

        }



    }


    /**
     * 生成继续任务对象
     * @param taskCode
     * @param target
     * @return
     */
    private AgvTask transContinueTaskModel(Integer taskCode, String target) {
        String newReqCode = UUID.randomUUID().toString().replace("-","");
        AgvTask agvTask = new AgvTask();
        agvTask.setReqCode(newReqCode);
        agvTask.setTaskCode(taskCode.toString());
        agvTask.setTaskTyp(agvTaskTyp);
        AgvPositionItem nextAddr = new AgvPositionItem();
        nextAddr.setPositionCode(target);
        nextAddr.setType("00");
        agvTask.setNextPositionCode(nextAddr);
        return agvTask;
    }
}
