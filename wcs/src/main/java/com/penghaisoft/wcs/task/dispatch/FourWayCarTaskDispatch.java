package com.penghaisoft.wcs.task.dispatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.operation.service.FourwaycarOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskSplitService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsFourwaycarTask;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName FourWayCarTaskDispatch
 * @Description 四向车任务调度
 * @Author zhangx
 * @Date 2020/7/29 16:16
 **/
@Slf4j
@ConditionalOnProperty(prefix = "jobs.open",name = "fourwaycar",havingValue = "true")
@Component
public class FourWayCarTaskDispatch {

    @Autowired
    private FourwaycarOperationService fourwaycarOperationService;

    @Autowired
    private ITaskDispatchService taskDispatchService;

    @Autowired
    private ITaskSplitService taskSplitService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IBaseDictItemService baseDictItemService;

    private static final String OUT_LOCK_KEY = "WCS:OUT:LOCK";

    private static final String OUT_LOCK_MOVE = "WCS:OUT:MOVE";

    /**
     * 四向车任务调度，在这里进行任务的下发
     */
    @Scheduled(cron = "${jobs.fourwaycar-dispatch.cron}")
    public void scheduled() {
        String operator = "FourWayCarTaskDispatch";
//        ----------------处理叫托盘任务，优先级最高----------------
        WcsTask doingCallPalletWcsTask = taskSplitService.getDoingCallPalletTask();
//        执行中的叫托盘任务只能有一个，找不到执行中的再找创建的
        if (null == doingCallPalletWcsTask){
//            log.info("没有执行中的叫托盘任务");
            WcsTask createCallPalletWcsTask = taskSplitService.getCreateCallPalletTask();
            if (null!=createCallPalletWcsTask){
                log.info("找到创建状态的叫托盘任务 task_id={},location_id={}",createCallPalletWcsTask.getTaskId(),createCallPalletWcsTask.getFromAddress());
//                下发四向车任务
                Long taskId = createCallPalletWcsTask.getTaskId();
                WcsFourwaycarTask createFourwaycarTask = taskDispatchService.findFourWayCarTaskByTaskId(taskId);
                Integer fourwaycayTaskNo = createFourwaycarTask.getTaskNo();
                Integer from = createFourwaycarTask.getLocationId();
                Integer to = 3001;
                String palletCode = createCallPalletWcsTask.getPalletCode();
                Resp resp = fourwaycarOperationService.sendOutTask(taskId,fourwaycayTaskNo,from,to,palletCode);

                Integer wcsTaskId = createCallPalletWcsTask.getId();
                Integer fourwaycarTaskId = createFourwaycarTask.getFourwaycarTaskId();
                if ("1".equals(resp.getCode())){
                    log.info("调用四向车接口成功");
                    JSONObject data = (JSONObject) resp.getData();
                    if ("0".equals(data.getString("returnStatus"))){
                        log.info("四向车返回成功");
                        taskDispatchService.startCallPalletSuccess(wcsTaskId,fourwaycarTaskId,operator);
                    }else {
                        log.info("四向车返回错误");
                        String errMsg = data.toJSONString();
                        taskDispatchService.startCallPalletFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
                    }
                }else {
                    log.info("调用四向车接口报错");
                    String errMsg = resp.getMsg();
                    taskDispatchService.startCallPalletFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
                }
            }
        }

//        ----------------处理出库任务----------------
        if (stringRedisTemplate.hasKey(OUT_LOCK_KEY)){
            log.info("下发四向车出库任务中");
        }else {
            handleOutTask();
        }

//        ----------------处理移库任务----------------
        if (stringRedisTemplate.hasKey(OUT_LOCK_MOVE)){
            log.info("下发四向车移库任务中");
        }else {
            handleMoveTask();
        }

//        ----------------处理手工入库/虚拟托盘入库任务----------------
//        应该只有一个
        handleInTask();

//      异常口入库 不可能了，异常入库就是手工入
//        handleErrorInTask();

    }

    /**
     * 应该不可能
     */
 /*   private void handleErrorInTask() {
        String operator = "FourWayCarTaskDispatch";
//        手工入
        String type = String.valueOf(Constant.TaskType.ABNORMAL_IN.getTaskType());
        List<WcsTask> handInWcsTasks = taskDispatchService.findCreateTaskByType(type);
        if (handInWcsTasks.size()>0){
            WcsTask handInTask = handInWcsTasks.get(0);
            Integer wcsTaskId = handInTask.getId();
            Integer fourwaycarTaskId = Integer.parseInt(handInTask.getUserDefined2());
            boolean respFlag = fourwaycarOperationService.palletInReady(handInTask.getPalletCode());
            if (respFlag){
//                调用成功

                taskDispatchService.startErrorHandInTaskSuccess(wcsTaskId,fourwaycarTaskId,operator);
            }else {
//                调用失败
                log.info("异常入库失败{}", JSON.toJSONString(handInTask));
//                taskDispatchService.startHandAndVirtualPalletInTaskFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
            }
        }


    }*/


    /**
     * 手工与虚拟托盘入库
     */
    private void handleInTask() {
        String operator = "FourWayCarTaskDispatch";
//        手工入
        String type = String.valueOf(Constant.TaskType.HAND_IN.getTaskType());
        List<WcsTask> handInWcsTasks = taskDispatchService.findCreateTaskByType(type);
        if (handInWcsTasks.size()>0){
            WcsTask handInTask = handInWcsTasks.get(0);
            Integer wcsTaskId = handInTask.getId();
            Integer fourwaycarTaskId = Integer.parseInt(handInTask.getUserDefined2());
            Resp resp = fourwaycarOperationService.sendInTask(handInTask.getTaskId(),Integer.parseInt(handInTask.getUserDefined1()),
                    handInTask.getToAddress(),handInTask.getFromAddress(),handInTask.getPalletCode());
            if ("1".equals(resp.getCode())){
//                调用成功
                JSONObject data = (JSONObject) resp.getData();
                if ("0".equals(data.getString("returnStatus"))){
//                    业务成功
                    taskDispatchService.startHandAndVirtualPalletInTaskSuccess(wcsTaskId,fourwaycarTaskId,operator);
                }else {
//                    业务失败
                    String errMsg = data.toJSONString();
                    taskDispatchService.startHandAndVirtualPalletInTaskFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
                }
            }else {
//                调用失败
                String errMsg = resp.getMsg();
                taskDispatchService.startHandAndVirtualPalletInTaskFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
            }
        }

//        虚拟托盘入库--这块应该没了0813会议去掉
/*        type = String.valueOf(Constant.TaskType.VIRTUAL_PALLET_IN.getTaskType());
        List<WcsTask> virtualPalletInWcsTasks = taskDispatchService.findCreateTaskByType(type);
        if (virtualPalletInWcsTasks.size()>0){
            WcsTask virtualPalletInWcsTask = virtualPalletInWcsTasks.get(0);
            Integer wcsTaskId = virtualPalletInWcsTask.getId();
            Integer fourwaycarTaskId = Integer.parseInt(virtualPalletInWcsTask.getUserDefined2());
            Resp resp = fourwaycarOperationService.sendInTask(virtualPalletInWcsTask.getTaskId(),Integer.parseInt(virtualPalletInWcsTask.getUserDefined1()),
                    virtualPalletInWcsTask.getToAddress(),virtualPalletInWcsTask.getFromAddress(),operator);
            if ("1".equals(resp.getCode())){
//                调用成功
                JSONObject data = (JSONObject) resp.getData();
                if ("0".equals(data.getString("returnStatus"))){
//                    业务成功
                    taskDispatchService.startHandAndVirtualPalletInTaskSuccess(wcsTaskId,fourwaycarTaskId,operator);
                }else {
//                    业务失败
                    String errMsg = data.toJSONString();
                    taskDispatchService.startHandAndVirtualPalletInTaskFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
                }
            }else {
//                调用失败
                String errMsg = resp.getMsg();
                taskDispatchService.startHandAndVirtualPalletInTaskFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
            }
        }*/

    }

    /**
     * 处理出库任务,一次限制最多出3拖
     */
    private void handleOutTask(){
        int limit = 2;
        List<BaseDictItem> dictItems = baseDictItemService.getDictTypeByCode("out_limit");
        if (!dictItems.isEmpty()){
            String limitStr = dictItems.get(0).getDicItemCode();
            limit = Integer.parseInt(limitStr);
        }

        String operator = "FourWayCarTaskDispatch";
        String type = String.valueOf(Constant.TaskType.STRAIGHT_OUT.getTaskType());

//        直发出库的任务
//         b.task_no as user_defined1,
//      b.fourwaycar_task_id as user_defined2
        List<WcsTask> straightWcsTasks = taskDispatchService.findCreateTaskByTypeLimit(type,limit);
        if (straightWcsTasks.size()>0){
            stringRedisTemplate.opsForValue().set(OUT_LOCK_KEY,"FourWayCarTaskDispatch",30, TimeUnit.SECONDS);
            List<Integer> wcsTaskIds = new ArrayList<>();
            List<Integer> fourwaycarTaskIds = new ArrayList<>();
            for (int i = 0; i < straightWcsTasks.size(); i++) {
                WcsTask tmp = straightWcsTasks.get(i);
                wcsTaskIds.add(tmp.getId());
                fourwaycarTaskIds.add(Integer.parseInt(tmp.getUserDefined2()));
            }
//todo 一个口如果出多个订单，轮流出，
            Resp resp = fourwaycarOperationService.sendOutTasks(straightWcsTasks);
            if ("1".equals(resp.getCode())){
//                调用成功
                JSONObject data = (JSONObject) resp.getData();
                if ("0".equals(data.getString("returnStatus"))){
//                    业务成功
                    taskDispatchService.startStraightOutSuccess(wcsTaskIds,fourwaycarTaskIds,operator);
                    stringRedisTemplate.delete(OUT_LOCK_KEY);
                }else {
//                    业务失败
                    String errMsg = data.toJSONString();
                    taskDispatchService.startStraightOutFail(wcsTaskIds,fourwaycarTaskIds,operator,errMsg);
                    stringRedisTemplate.delete(OUT_LOCK_KEY);
                }
            }else {
//                调用失败
                String errMsg = resp.getMsg();
                taskDispatchService.startStraightOutFail(wcsTaskIds,fourwaycarTaskIds,operator,errMsg);
                stringRedisTemplate.delete(OUT_LOCK_KEY);
            }
        }

        type = String.valueOf(Constant.TaskType.HAND_OUT.getTaskType());
//        手工出库的任务,一个一个的
        List<WcsTask> handWcsTasks = taskDispatchService.findCreateTaskByType(type);
        if (handWcsTasks.size()>0){
            stringRedisTemplate.opsForValue().set(OUT_LOCK_KEY,"FourWayCarTaskDispatch",30, TimeUnit.SECONDS);
            WcsTask handOutTask = handWcsTasks.get(0);
            Integer wcsTaskId = handOutTask.getId();
            Integer fourwaycarTaskId = Integer.parseInt(handOutTask.getUserDefined2());
            Resp resp = fourwaycarOperationService.sendOutTask(handOutTask.getTaskId(),Integer.parseInt(handOutTask.getUserDefined1()),
                    handOutTask.getFromAddress(),handOutTask.getToAddress(),handOutTask.getPalletCode());
            if ("1".equals(resp.getCode())){
//                调用成功
                JSONObject data = (JSONObject) resp.getData();
                if ("0".equals(data.getString("returnStatus"))){
//                    业务成功
                    taskDispatchService.startHandOutSuccess(wcsTaskId,fourwaycarTaskId,operator);
                    stringRedisTemplate.delete(OUT_LOCK_KEY);
                }else {
//                    业务失败
                    String errMsg = data.toJSONString();
                    taskDispatchService.startHandOutFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
                    stringRedisTemplate.delete(OUT_LOCK_KEY);
                }
            }else {
//                调用失败
                String errMsg = resp.getMsg();
                taskDispatchService.startHandOutFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
                stringRedisTemplate.delete(OUT_LOCK_KEY);
            }
        }
    }


    /**
     * 处理移库任务
     */
    private void handleMoveTask() {
        String operator = "FourWayCarTaskDispatch";
        String type = String.valueOf(Constant.TaskType.NORMAL_MOVE.getTaskType());

        List<WcsTask> moveWcsTasks = taskDispatchService.findCreateTaskByType(type);
        if (moveWcsTasks.size()>0){
            stringRedisTemplate.opsForValue().set(OUT_LOCK_MOVE,"FourWayCarTaskDispatch",30, TimeUnit.SECONDS);
            WcsTask moveTask = moveWcsTasks.get(0);
            Integer wcsTaskId = moveTask.getId();
            Integer fourwaycarTaskId = Integer.parseInt(moveTask.getUserDefined2());
            Integer fourwaycarTaskNo = Integer.parseInt(moveTask.getUserDefined1());
            Resp resp = fourwaycarOperationService.sendMoveTask(moveTask.getTaskId(),fourwaycarTaskNo,
                    moveTask.getFromAddress(),moveTask.getToAddress(),moveTask.getPalletCode());
            if ("1".equals(resp.getCode())){
//                调用成功
                JSONObject data = (JSONObject) resp.getData();
                if ("0".equals(data.getString("returnStatus"))){
//                    业务成功
                    taskDispatchService.startMoveTaskSuccess(wcsTaskId,fourwaycarTaskId,operator);
                    stringRedisTemplate.delete(OUT_LOCK_MOVE);
                }else {
//                    业务失败
                    String errMsg = data.toJSONString();
                    taskDispatchService.startMoveTaskFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
                    stringRedisTemplate.delete(OUT_LOCK_MOVE);
                }
            }else {
//                调用失败
                String errMsg = resp.getMsg();
                taskDispatchService.startMoveTaskFail(wcsTaskId,fourwaycarTaskId,operator,errMsg);
                stringRedisTemplate.delete(OUT_LOCK_MOVE);
            }
        }

    }
}
