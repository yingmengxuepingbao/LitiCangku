package com.penghaisoft.wcs.taskmanagement.model.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.dao.WcsLocationRealMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.monitormanagement.model.dao.WcsErrorLogMapper;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import com.penghaisoft.wcs.operation.model.dao.WcsBindingInfoMapper;
import com.penghaisoft.wcs.operation.service.PalletizingOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.dao.*;
import com.penghaisoft.wcs.taskmanagement.model.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description TaskDispatchServiceImpl
 * @Auther zhangxu
 * @Date 2020/3/19 15:39
 **/
@Slf4j
@Service
public class TaskDispatchServiceImpl implements ITaskDispatchService {

    @Autowired
    private WcsAgvTaskMapper wcsAgvTaskMapper;

    @Autowired
    private WcsFourwaycarTaskMapper fourwaycarTaskMapper;

    @Autowired
    private WcsTaskMapper taskMapper;

    @Autowired
    private WcsErrorLogMapper errorLogMapper;

    @Autowired
    private WcsLocationRealMapper locationRealMapper;


    @Autowired
    private WcsBindingInfoMapper wcsBindingInfoMapper;

    /**
     * 根据任务id 查询移库任务的目的地库位
     *
     * @param taskId
     * @return
     */
    @Override
    public WcsLocationReal queryMoveToLocationByTaskId(Long taskId) {
        List<WcsTask> tasks = taskMapper.selectByTaskId(taskId);
        WcsTask moveTask = tasks.get(0);
        Integer locationId = moveTask.getToAddress();
        WcsLocationReal toLocation = locationRealMapper.queryById(locationId);
        return toLocation;
    }

    /**
     * @param pathId 路径id
     * @return
     * @Description 根据不同的码垛线入库路径找到创建状态的任务
     * @Date 2020/7/3 9:53
     **/
    @Override
    public WcsAgvTask findWaitingStartAgvTaskByPath(Integer pathId) {
        WcsAgvTask waitTask = null;
        WcsAgvTask cond = new WcsAgvTask();
        cond.setPathId(pathId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        cond.setTaskStatus("1");
        List<WcsAgvTask> agvTasks = wcsAgvTaskMapper.selectByCond(cond);
        if (agvTasks.size()>0){
            waitTask = agvTasks.get(0);
        }
        return waitTask;
    }

    /**
     * zhangx
     * @return
     * @Description 查找等待入库的任务
     * @Date 2020/7/3 10:21
     **/
    @Override
    public List<WcsAgvTask> findWaitingContinueAgvTask() {
        WcsAgvTask cond = new WcsAgvTask();
//        任务状态1创建；2下发；3到达接货点；4继续任务；5任务完成；21下发失败；41继续任务失败'
        cond.setTaskStatus("3");
        List<WcsAgvTask> continueTasks = wcsAgvTaskMapper.selectByCond(cond);
        return continueTasks;
    }

    /**
     * 成功下发agv任务
     *
     * @param agvTaskId
     * @param operator
     */
    @Override
    public void startAgvTaskSuccess(Integer agvTaskId, String operator) {
        Date now = new Date();
        WcsAgvTask upd = new WcsAgvTask();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("2");
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * 下发agv任务业务异常
     *
     * @param agvTaskId
     * @param agvResult
     * @param operator
     */
    @Override
    public void startAgvTaskFail(Integer agvTaskId, JSONObject agvResult, String operator) {
        Date now = new Date();
        WcsAgvTask upd = new WcsAgvTask();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("21");
        String errorMsg = agvResult.toJSONString();
        if (errorMsg.length()>255){
            errorMsg = errorMsg.substring(0,254);
        }
        upd.setErrorMsg(errorMsg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * 启动agv任务报错
     *
     * @param agvTaskId
     * @param msg
     * @param operator
     */
    @Override
    public void startAgvTaskError(Integer agvTaskId, String msg, String operator) {
        Date now = new Date();
        WcsAgvTask upd = new WcsAgvTask();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("21");
        upd.setErrorMsg(msg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * agv的回调-结束任务，
     * @param taskNo
     * @param operator
     * @return
     */
    @Override
    public Resp finishAgvTaskByTaskNo(Integer taskNo,String operator) {
//        todo 如果上传的是失败怎么办？
        Resp resp = new Resp();
        resp.setCode("0");//这里是成功的意思
        Date now = new Date();

//        1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消
        WcsAgvTask startCond = new WcsAgvTask();
        startCond.setTaskStatus("5");
        startCond.setTaskNo(taskNo);
        List<WcsAgvTask> inTasks = wcsAgvTaskMapper.selectByCond(startCond);
//        证明这是起始任务的结束
        if (inTasks.size()==1){
            WcsAgvTask currentTask = inTasks.get(0);

            WcsAgvTask updInTask = new WcsAgvTask();
            updInTask.setAgvTaskId(currentTask.getAgvTaskId());
            updInTask.setTaskStatus("6");
            updInTask.setGmtModified(now);
            updInTask.setGmtEnd(now);
            updInTask.setLastModifiedBy(operator);
            wcsAgvTaskMapper.updateByPrimaryKeySelective(updInTask);
            return resp;
//            查询出总任务信息，起始是为了修改码垛机接受完成标志位
//            Long taskId = currentTask.getTaskId();
//            List<WcsTask> wcsTaskList = taskMapper.selectByTaskId(taskId);
//            resp.setMsg("start");
//            resp.setData(wcsTaskList.get(0));
//            return resp;
        }
        return null;
    }

    /**
     * 继续agv任务下发成功
     *
     * @param agvTaskId
     * @param operator
     */
    @Override
    public void continueAgvTaskSuccess(Integer agvTaskId, String operator) {
        Date now = new Date();
        WcsAgvTask upd = new WcsAgvTask();
        upd.setAgvTaskId(agvTaskId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("5");
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * 继续agv任务业务员异常
     *
     * @param agvTaskId
     * @param agvResult
     * @param operator
     */
    @Override
    public void continueAgvTaskFail(Integer agvTaskId, JSONObject agvResult, String operator) {
        Date now = new Date();
        WcsAgvTask upd = new WcsAgvTask();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("51");
        String errorMsg = agvResult.toJSONString();
        if (errorMsg.length()>255){
            errorMsg = errorMsg.substring(0,254);
        }
        upd.setErrorMsg(errorMsg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * 继续agv任务错误
     *
     * @param agvTaskId
     * @param msg
     * @param operator
     */
    @Override
    public void continueAgvTaskError(Integer agvTaskId, String msg, String operator) {
        Date now = new Date();
        WcsAgvTask upd = new WcsAgvTask();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("51");
        upd.setErrorMsg(msg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * 根据继续任务号结束agv任务
     *
     * @param agvResultCode
     * @param continueTaskNo
     * @param early
     */
    @Override
    public void finishAgvTaskByContinueTaskNo(String agvResultCode, String continueTaskNo, String early) {
        //        这里是继续任务的结束
        WcsAgvTask continueCond = new WcsAgvTask();
        continueCond.setUserDefined1(continueTaskNo);
        List<WcsAgvTask> continueTasks = wcsAgvTaskMapper.selectByCond(continueCond);
        if (continueTasks.size()==1){
            WcsAgvTask currentTask = continueTasks.get(0);

            WcsAgvTask updContinueTask = new WcsAgvTask();
            updContinueTask.setAgvTaskId(currentTask.getAgvTaskId());
            //         '任务状态1创建；2下发；3到达接货点；4继续任务；5任务完成；21下发失败；41继续任务失败',
            updContinueTask.setTaskStatus("5");
            Date now = new Date();
            updContinueTask.setGmtModified(now);
            updContinueTask.setLastModifiedBy(early);
            updContinueTask.setGmtEnd(now);
            wcsAgvTaskMapper.updateByPrimaryKeySelective(updContinueTask);
        }
    }

    /**
     * 根据任务号找到任务对象
     *
     * @param taskNo
     * @return
     */
    @Override
    public WcsAgvTask findAgvTaskByTaskNo(Integer taskNo) {
        WcsAgvTask agvTask = null;
        //        这里是继续任务的结束
        WcsAgvTask continueCond = new WcsAgvTask();
        continueCond.setTaskNo(taskNo);
        List<WcsAgvTask> continueTasks = wcsAgvTaskMapper.selectByCond(continueCond);
        if (continueTasks.size()==1){
            agvTask = continueTasks.get(0);

        }
        return agvTask;
    }

    /**
     * 结束前期叫托盘任务
     *
     * @param taskNo
     * @param early
     */
    @Override
    public void finishAgvDemoPalletTaskByTaskNo(Integer taskNo, String early) {
        WcsAgvTask continueCond = new WcsAgvTask();
        continueCond.setTaskNo(taskNo);
        List<WcsAgvTask> continueTasks = wcsAgvTaskMapper.selectByCond(continueCond);
        if (continueTasks.size()==1){
            WcsAgvTask currentTask = continueTasks.get(0);

            WcsAgvTask updContinueTask = new WcsAgvTask();
            updContinueTask.setAgvTaskId(currentTask.getAgvTaskId());
            //         '任务状态1创建；2下发；3到达接货点；4继续任务；5任务完成；21下发失败；41继续任务失败',
            updContinueTask.setTaskStatus("5");
            Date now = new Date();
            updContinueTask.setGmtModified(now);
            updContinueTask.setLastModifiedBy(early);
            updContinueTask.setGmtEnd(now);
            wcsAgvTaskMapper.updateByPrimaryKeySelective(updContinueTask);
        }
    }

    /**
     * 查找四向车任务，并将状态改为2-申请
     * zhangx
     * @param palletCode
     * @param operator
     * @return
     */
    @Override
    public Resp findAndApplyFourWayCarWaitingInTask(String palletCode, String operator) {
        Resp result = new Resp();

        String ingStatus = "2";
        String taskType = String.valueOf(Constant.TaskType.PRODUCT_IN.getTaskType());
//        找到进行中的入库任务,理论上只可能存在一个
        List<WcsTask> ingWcsTasks = taskMapper.selectOneInTaskByByPalletAndStatus(palletCode,ingStatus,taskType);
        if (ingWcsTasks.size()==0){
            result.setCode("401");
            result.setMsg("wcs_task 未找到进行中的托盘"+palletCode+"入库任务");
        }else if(ingWcsTasks.size()>1){
            result.setCode("402");
            result.setMsg("wcs_task 找到进行中的托盘入库任务数="+ingWcsTasks.size());
        }else {
            WcsTask inWcsTask = ingWcsTasks.get(0);
            Long taskId = inWcsTask.getTaskId();
//            todo 是否需要校验agv状态？
            List<WcsFourwaycarTask> fourwaycarTasks = fourwaycarTaskMapper.selectByTaskId(taskId);
            if (fourwaycarTasks.size()==1){
                WcsFourwaycarTask nowFourwaycarTask = fourwaycarTasks.get(0);
                if (nowFourwaycarTask.getTaskStatus().equals("1")){
                    result.setCode("1");
                    WcsFourwaycarTask upd = new WcsFourwaycarTask();
                    upd.setFourwaycarTaskId(nowFourwaycarTask.getFourwaycarTaskId());
                    upd.setTaskStatus("2");
                    upd.setGmtModified(new Date());
                    upd.setLastModifiedBy(operator);
                    fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
                }else if(nowFourwaycarTask.getTaskStatus().equals("2")){
                    result.setCode("1");
                }else{
                    result.setCode("403");
                    result.setMsg("wcs_fourwaycar_task 找到任务 "+taskId + "状态="+nowFourwaycarTask.getTaskStatus());
                }

            }else {
                result.setCode("404");
                result.setMsg("wcs_fourwaycar_task 找到任务 "+taskId + "数量="+fourwaycarTasks.size());
            }
        }
        return result;
    }

    /**
     * @param palletCode
     * @return java.lang.Long
     * @Description 根据托盘号找到四向车任务编号
     * @Date 2020/7/9 14:47
     **/
    @Override
    public WcsTask findFourWayCarWaitingInTask(String palletCode) {

        String ingStatus = "2";
        String taskType = String.valueOf(Constant.TaskType.PRODUCT_IN.getTaskType());
//        找到进行中的入库任务,理论上只可能存在一个
        List<WcsTask> ingWcsTasks = taskMapper.selectOneInTaskByByPalletAndStatus(palletCode, ingStatus, taskType);
        if (ingWcsTasks.size() == 0) {
            log.error("wcs_task 未找到进行中的托盘" + palletCode + "入库任务");
            return null;
        } else if (ingWcsTasks.size() > 1) {
            log.error("wcs_task 找到进行中的托盘入库任务数=" + ingWcsTasks.size());
            return null;
        } else {
            WcsTask inWcsTask = ingWcsTasks.get(0);
            if (inWcsTask.getToAddress() == null) {
                return inWcsTask;
            } else {
                log.info("总任务推荐库位={}", inWcsTask.getToAddress());
                return inWcsTask;
            }
        }
    }

    /**
     * 根据总任务号查找数据
     *
     * @param taskId
     * @return
     */
    @Override
    public WcsFourwaycarTask findFourWayCarTaskByTaskId(Long taskId) {
        List<WcsFourwaycarTask> fourwaycarTasks = fourwaycarTaskMapper.selectByTaskId(taskId);
        if (fourwaycarTasks.size()==1){
            return fourwaycarTasks.get(0);
        }
        return null;
    }

    /**
     * 根据继续任务号查找
     *
     * @param taskNo
     * @return
     */
    @Override
    public WcsAgvTask findAgvTaskByContinueTaskNo(Integer taskNo) {
        WcsAgvTask agvTask = null;
        //        这里是继续任务的结束
        WcsAgvTask continueCond = new WcsAgvTask();
        continueCond.setUserDefined2(taskNo.toString());
        List<WcsAgvTask> continueTasks = wcsAgvTaskMapper.selectByCond(continueCond);
        if (continueTasks.size()==1){
            agvTask = continueTasks.get(0);

        }
        return agvTask;
    }

    /**
     * @param agvTaskId
     * @param operator
     * @param priority
     * @return void
     * @Description 将agv任务设置为等待
     * @Date 2020/7/11 11:01
     **/
    @Override
    public void setAgvWaitingBuffStatus(Integer agvTaskId, String operator, int priority) {
        WcsAgvTask upd = new WcsAgvTask();
        upd.setTaskStatus("4");
        upd.setAgvTaskId(agvTaskId);
        upd.setPriority(priority);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(new Date());
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * 根据入库任务号删除
     *
     * @param inTaskNo
     */
    @Override
    public void deleteFinishAgvTask(Integer inTaskNo) {
        wcsAgvTaskMapper.deleteByUserDefined3(inTaskNo.toString());
    }

    /**
     * @param target
     * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask
     * @Description 根据目的地找到等待入库的agv任务
     * @Date 2020/7/11 11:52
     **/
    @Override
    public WcsAgvTask findWaitingInAgvTaskByTarget(String target) {
        List<WcsAgvTask> tasks = wcsAgvTaskMapper.selectEarlyWaitingInTaskByCond(null);
        if (tasks.size()>0){
            return tasks.get(0);
        }
        return null;
    }

    @Override
    public void finishEarlyAgvTaskById(Integer agvTaskId) {
        WcsAgvTask upd = new WcsAgvTask();
        upd.setAgvTaskId(agvTaskId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("6");
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
    }


    @Override
    public void setAgvWaitingBuffStatusTwo(String taskCode, int priority) {
        WcsAgvTask upd = new WcsAgvTask();
        upd.setUserDefined5(taskCode);
        upd.setPriority(priority);
        upd.setUserDefined4("wait");
        wcsAgvTaskMapper.updateByTaskCode(upd);
    }

    @Override
    public void deleteFinishAgvTaskByTaskCode(Integer taskCode) {
        wcsAgvTaskMapper.deleteByTaskCode(taskCode);
    }

    /**
     * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask
     * @Description 找到等待入库的agv任务-处于暂存区
     * @Date 2020/7/13 14:50
     **/
    @Override
    public WcsAgvTask findEarlyWaitingInAgvTask() {
        WcsAgvTask cond = new WcsAgvTask();
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        cond.setTaskStatus("4");
        List<WcsAgvTask> tasks = wcsAgvTaskMapper.selectEarlyWaitingInTaskByCond(cond);
        if (tasks.size()>0){
            return tasks.get(0);
        }
        return null;
    }

    /**
     * @param taskCode
     * @param priority
     * @return void
     * @Description 设置AGV为等待状态
     * @Date 2020/7/13 15:04
     **/
    @Override
    public void setEarlyAgvWaitingStatus(Integer taskCode, Integer priority) {
        WcsAgvTask upd = new WcsAgvTask();
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("4");
        upd.setTaskNo(taskCode);
        upd.setPriority(priority);
        upd.setLastModifiedBy("early");
        upd.setGmtModified(new Date());
        wcsAgvTaskMapper.updateByTaskCode(upd);
    }

    /**
     * @param taskCode
     * @param status
     * @return void
     * @Description 根据任务号更新agv任务状态
     * @Date 2020/7/13 16:11
     **/
    @Override
    public void setEarlyAgvStatusByTaskNo(Integer taskCode, String status) {
        WcsAgvTask upd = new WcsAgvTask();
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus(status);
        upd.setTaskNo(taskCode);
        upd.setGmtModified(new Date());
        wcsAgvTaskMapper.updateByTaskCode(upd);
    }

    /**
     * @param status
     * @return java.util.List<com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask>
     * @Description 根据任务状态找到agv任务
     * @Date 2020/7/14 10:51
     **/
    @Override
    public List<WcsAgvTask> findAgvTaskByStatus(String status) {
        WcsAgvTask cond = new WcsAgvTask();
        cond.setTaskStatus(status);
        List<WcsAgvTask> result = wcsAgvTaskMapper.selectByCond(cond);
        return result;
    }

    /**
     * @param taskCode
     * @param priority
     * @return void
     * @Description agv到达缓存区
     * @Date 2020/7/14 21:13
     **/
    @Override
    public void setAgvArriveBufferStatus(Integer taskCode, int priority) {
        WcsAgvTask upd = new WcsAgvTask();
//        任务状态1创建；2下发；3到达缓冲区/托盘出库口；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("3");
        upd.setTaskNo(taskCode);
        upd.setPriority(priority);
        upd.setGmtModified(new Date());
        wcsAgvTaskMapper.updateByTaskCode(upd);
    }

    /**
     * @param taskCode
     * @param operator
     * @return com.penghaisoft.framework.util.Resp
     * @Description 结束整体叫托盘任务
     * @Date 2020/7/17 14:40
     **/
    @Transactional
    @Override
    public Resp finishCallPalletTaskByTaskNo(Integer taskCode, String operator) {
        Resp resp = new Resp();
        resp.setCode("0");//这里是成功的意思
        Date now = new Date();
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        WcsAgvTask startCond = new WcsAgvTask();
        startCond.setTaskStatus("5");
        startCond.setTaskNo(taskCode);
        List<WcsAgvTask> inTasks = wcsAgvTaskMapper.selectByCond(startCond);
//        证明这是起始任务的结束
        if (inTasks.size()==1){
            WcsAgvTask currentTask = inTasks.get(0);

            WcsAgvTask updInTask = new WcsAgvTask();
            updInTask.setAgvTaskId(currentTask.getAgvTaskId());
            updInTask.setTaskStatus("6");
            updInTask.setGmtEnd(now);
            updInTask.setGmtModified(now);
            updInTask.setLastModifiedBy(operator);
            wcsAgvTaskMapper.updateByPrimaryKeySelective(updInTask);

            Long taskId = currentTask.getTaskId();
            WcsTask updWcsTask = new WcsTask();
            updWcsTask.setTaskId(taskId);
//            任务状态1创建2执行中3完成4异常5取消
            updWcsTask.setTaskStatus("3");
            updWcsTask.setGmtEnd(now);
            updWcsTask.setEndBy(operator);
            int count = taskMapper.updateByTaskIdSelective(updWcsTask);
            log.info("结束叫托盘任务，wcs_task记录数={}",count);
            return resp;
        }



        return null;
    }

    /**
     * @param id
     * @return void
     * @Description 根据id删除agv任务
     * @Date 2020/7/20 20:39
     **/
    @Override
    public void deleteFinishAgvTaskById(Integer id) {
        wcsAgvTaskMapper.deleteByPrimaryKey(id);
    }

    /**
     * @return java.lang.String
     * @Description 清除数据 绑定+agv
     * @Date 2020/7/20 21:15
     **/
    @Override
    public String clearEarlyData() {
        String result = "删除agv任务数=";
        int c1 = wcsAgvTaskMapper.deleteAll();
        result = result + c1 +";删除绑定任务数=";
        int c2 = wcsBindingInfoMapper.deleteAll();
        result = result + c2;
        return result;
    }

    /**
     * @param taskId
     * @param agvTaskId
     * @param operator
     * @return void
     * @Description 下线入库时候启动任务成功
     * @Date 2020/7/28 9:16
     **/
    @Transactional
    @Override
    public void startProductInTaskSuccess(Long taskId, Integer agvTaskId, String operator) {
//        1修改AGV任务表
        Date now = new Date();
        WcsAgvTask upd = new WcsAgvTask();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("2");
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
//        2 修改WCS_TASK表
        List<WcsTask> wcsTasks = taskMapper.selectByTaskId(taskId);
        if (wcsTasks.size()==1){
            WcsTask wcsTask = wcsTasks.get(0);
//            任务状态1创建2执行中3完成4异常5取消
            if ("1".equals(wcsTask.getTaskStatus())){
                WcsTask updTask = new WcsTask();
                updTask.setId(wcsTask.getId());
                updTask.setTaskStatus("2");
                updTask.setGmtModified(now);
                updTask.setLastModifiedBy(operator);
                taskMapper.updateByPrimaryKeySelective(updTask);
            }
        }else {
            log.error("启动下线任务时，{}的WCS_TASK记录数={}",taskId,wcsTasks.size());
        }
    }

    /**
     * @param taskId
     * @param agvTaskId
     * @param agvResult
     * @param operator
     * @return void
     * @Description 下线入库时候启动任务失败，发生业务异常
     * zhangx
     * @Date 2020/7/28 9:22
     **/
    @Transactional
    @Override
    public void startProductInTaskFail(Long taskId, Integer agvTaskId, JSONObject agvResult, String operator) {
//        1修改AGV任务表
        Date now = new Date();
        WcsAgvTask upd = new WcsAgvTask();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("21");
        String errorMsg = agvResult.toJSONString();
        if (errorMsg.length()>255){
            errorMsg = errorMsg.substring(0,254);
        }
        upd.setErrorMsg(errorMsg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
        //        2 修改WCS_TASK表
        List<WcsTask> wcsTasks = taskMapper.selectByTaskId(taskId);
        if (wcsTasks.size()==1){
            WcsTask wcsTask = wcsTasks.get(0);
//            任务状态1创建2执行中3完成4异常5取消
            if ("1".equals(wcsTask.getTaskStatus())){
                WcsTask updTask = new WcsTask();
                updTask.setId(wcsTask.getId());
                updTask.setTaskStatus("4");
                updTask.setErrorMsg("AGV业务异常");
                updTask.setGmtModified(now);
                updTask.setLastModifiedBy(operator);
                taskMapper.updateByPrimaryKeySelective(updTask);
            }
        }else {
            log.error("修改WCS_TASK时，{}的WCS_TASK记录数={}",taskId,wcsTasks.size());
        }
    }

    /**
     * @param taskId
     * @param agvTaskId
     * @param msg
     * @param operator
     * @return void
     * @Description 下线入库时候启动任务失败，发生程序报错
     * @Date 2020/7/28 9:25
     * zhangx
     **/
    @Transactional
    @Override
    public void startProductInTaskError(Long taskId, Integer agvTaskId, String msg, String operator) {
//        1修改AGV任务表
        Date now = new Date();
        WcsAgvTask upd = new WcsAgvTask();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("21");
        upd.setErrorMsg(msg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        wcsAgvTaskMapper.updateByPrimaryKeySelective(upd);
        //        2 修改WCS_TASK表
        List<WcsTask> wcsTasks = taskMapper.selectByTaskId(taskId);
        if (wcsTasks.size()==1){
            WcsTask wcsTask = wcsTasks.get(0);
//            任务状态1创建2执行中3完成4异常5取消
            if ("1".equals(wcsTask.getTaskStatus())){
                WcsTask updTask = new WcsTask();
                updTask.setId(wcsTask.getId());
                updTask.setTaskStatus("4");
                updTask.setErrorMsg("AGV服务端或本客户端异常");
                updTask.setGmtModified(now);
                updTask.setLastModifiedBy(operator);
                taskMapper.updateByPrimaryKeySelective(updTask);
            }
        }else {
            log.error("修改WCS_TASK时，{}的WCS_TASK记录数={}",taskId,wcsTasks.size());
        }
    }

    /**
     * @param status
     * @param fourwaycarTaskId
     * @param operator
     * @return void
     * @Description 修改四向车任务状态
     * @Date 2020/7/28 17:16
     **/
    @Override
    public void setFourwaycarStatusByTaskId(String status, Integer fourwaycarTaskId, String operator,Integer locationCode) {
        WcsFourwaycarTask upd = new WcsFourwaycarTask();
        upd.setFourwaycarTaskId(fourwaycarTaskId);
        upd.setTaskStatus(status);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(new Date());
        if (null!=locationCode){
            upd.setLocationId(locationCode);
        }
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param fourwaycarTaskId
     * @param operator
     * @return void
     * @Description 下发四向车任务成功
     * @Date 2020/7/28 17:28
     **/
    @Override
    public void sendFourwaycarTaskSuccess(Integer fourwaycarTaskId, String operator) {
        WcsFourwaycarTask upd = new WcsFourwaycarTask();
        upd.setFourwaycarTaskId(fourwaycarTaskId);
//        任务状态 1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败
        upd.setTaskStatus("3");
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(new Date());
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param fourwaycarTaskId
     * @param returnInfo
     * @param operator
     * @return void
     * @Description 下发四向车任务失败
     * @Date 2020/7/28 17:32
     **/
    @Override
    public void sendFourwaycarTaskFail(Integer fourwaycarTaskId, String returnInfo, String operator) {
        if (returnInfo.length()>255){
            returnInfo = returnInfo.substring(0,254);
        }
        WcsFourwaycarTask upd = new WcsFourwaycarTask();
        upd.setFourwaycarTaskId(fourwaycarTaskId);
//        任务状态 1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败
        upd.setTaskStatus("31");
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(new Date());
        upd.setErrorMsg(returnInfo);
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param fourwaycarTaskId
     * @param msg
     * @param operator
     * @return void
     * @Description 下发四向车任务报错
     * @Date 2020/7/28 17:36
     **/
    @Override
    public void sendFourwaycarTaskError(Integer fourwaycarTaskId, String msg, String operator) {
        if (msg.length()>255){
            msg = msg.substring(0,254);
        }
        WcsFourwaycarTask upd = new WcsFourwaycarTask();
        upd.setFourwaycarTaskId(fourwaycarTaskId);
//        任务状态 1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败
        upd.setTaskStatus("31");
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(new Date());
        upd.setErrorMsg(msg);
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param taskCode
     * @param status
     * @param operator
     * @return void
     * @Description 根据任务号设置agv 状态
     * @Date 2020/7/29 13:16
     **/
    @Override
    public void setAgvStatusByTaskNo(Integer taskCode, String status, String operator) {
        WcsAgvTask upd = new WcsAgvTask();
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus(status);
        upd.setTaskNo(taskCode);
        upd.setGmtModified(new Date());
        upd.setLastModifiedBy(operator);
        wcsAgvTaskMapper.updateByTaskCode(upd);
    }

    /**
     * @param taskNo
     * @param status
     * @param operator
     * @return void
     * @Description 修改四向车任务状态
     * @Date 2020/7/29 14:33
     **/
    @Override
    public void setFourwaycarStatusByTaskNo(Integer taskNo, String status, String operator) {
        WcsFourwaycarTask upd = new WcsFourwaycarTask();
        upd.setTaskNo(taskNo);
        upd.setTaskStatus(status);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(new Date());
        fourwaycarTaskMapper.updateByTaskNoSelective(upd);
    }

    /**
     * @param taskNo
     * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsFourwaycarTask
     * @Description 根据任务号获取四向车记录
     * @Date 2020/7/29 14:45
     **/
    @Override
    public WcsFourwaycarTask findFourWayCarTaskByTaskNo(Integer taskNo) {
        List<WcsFourwaycarTask> fourwaycarTasks = fourwaycarTaskMapper.selectByTaskNo(taskNo);
        if (fourwaycarTasks.size()==1){
            return fourwaycarTasks.get(0);
        }
        return null;
    }

    /**
     * @param taskId
     * @return void
     * @Description 结束四向车和整体任务
     * @Date 2020/7/29 15:07
     **/
    @Override
    public void finishFourwaycarAndWholeTask(Long taskId,String operator) {
        List<WcsTask> wcsTasks = taskMapper.selectByTaskId(taskId);
        if (wcsTasks.size()==1){
            Date now = new Date();
            WcsTask updWcsTask = new WcsTask();
//            任务状态1创建2执行中3完成4异常5取消
            updWcsTask.setTaskStatus("3");
            updWcsTask.setId(wcsTasks.get(0).getId());
            updWcsTask.setLastModifiedBy(operator);
            updWcsTask.setGmtModified(now);
            updWcsTask.setGmtEnd(now);
            taskMapper.updateByPrimaryKeySelective(updWcsTask);

            List<WcsFourwaycarTask> fourwaycarTasks = fourwaycarTaskMapper.selectByTaskId(taskId);
            if (fourwaycarTasks.size()==1){
                WcsFourwaycarTask updFourwaycarTask = new WcsFourwaycarTask();
                updFourwaycarTask.setFourwaycarTaskId(fourwaycarTasks.get(0).getFourwaycarTaskId());
//                1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成
                updFourwaycarTask.setTaskStatus("6");
                updFourwaycarTask.setLastModifiedBy(operator);
                updFourwaycarTask.setGmtModified(now);
                updFourwaycarTask.setGmtEnd(now);
                fourwaycarTaskMapper.updateByPrimaryKeySelective(updFourwaycarTask);
            }else {
                log.error("结束整体任务时{}fourwaycar_task记录数={}",taskId,fourwaycarTasks.size());
            }
        }else {
            log.error("结束整体任务时{}wcs_task记录数={}",taskId,wcsTasks.size());
        }
    }

    /**
     * @param taskId
     * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask
     * @Description 根据taskId查找AGV任务
     * @Date 2020/7/29 15:24
     **/
    @Override
    public WcsAgvTask findAgvTaskByTaskId(Long taskId) {
        List<WcsAgvTask> agvTasks = wcsAgvTaskMapper.selectByTaskId(taskId);
        if (agvTasks.size()==1){
            return agvTasks.get(0);
        }
        return null;
    }

    /**
     * @return java.util.List<com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask>
     * @Description 找到创建状态的任务
     * @Date 2020/7/29 17:06
     **/
    @Override
    public List<WcsTask> findCreateTaskByType(String type) {
        WcsTask cond = new WcsTask();
        cond.setTaskStatus("1");
        cond.setTaskType(type);
//        b.task_no as user_defined1 四向车任务号,
//        b.fourwaycar_task_id as user_defined2
        List<WcsTask> wcsTasks = taskMapper.selectWithFourwaycarTaskNoByStatusOrderBy(cond);
        return wcsTasks;
    }

    /**
     * @param wcsTaskId
     * @param fourwaycarTaskId
     * @param operator
     * @return void
     * @Description 成功启动叫托盘任务
     * @Date 2020/7/29 17:38
     **/
    @Transactional
    @Override
    public void startCallPalletSuccess(Integer wcsTaskId, Integer fourwaycarTaskId, String operator) {
        Date now = new Date();

        WcsTask updTask = new WcsTask();
        updTask.setId(wcsTaskId);
//        任务状态1创建2执行中3完成4异常5取消
        updTask.setTaskStatus("2");
        updTask.setGmtModified(now);
        updTask.setGmtStart(now);
        updTask.setLastModifiedBy(operator);
        taskMapper.updateByPrimaryKeySelective(updTask);

        WcsFourwaycarTask upd = new WcsFourwaycarTask();
        upd.setFourwaycarTaskId(fourwaycarTaskId);
//        1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败'
        upd.setTaskStatus("3");
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        upd.setLastModifiedBy(operator);
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param wcsTaskId
     * @param fourwaycarTaskId
     * @param operator
     * @param errMsg
     * @return void
     * @Description 启动叫托盘任务业务异常
     * @Date 2020/7/29 17:44
     **/
    @Override
    public void startCallPalletFail(Integer wcsTaskId, Integer fourwaycarTaskId, String operator, String errMsg) {
        Date now = new Date();

        WcsTask updTask = new WcsTask();
        updTask.setId(wcsTaskId);
//        任务状态1创建2执行中3完成4异常5取消
        updTask.setTaskStatus("4");
        updTask.setGmtModified(now);
        updTask.setLastModifiedBy(operator);
        updTask.setGmtStart(now);
        taskMapper.updateByPrimaryKeySelective(updTask);

        WcsFourwaycarTask upd = new WcsFourwaycarTask();
        upd.setFourwaycarTaskId(fourwaycarTaskId);
//        1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败'
        upd.setTaskStatus("31");
        if (errMsg.length()>255){
            errMsg = errMsg.substring(0,254);
        }
        upd.setErrorMsg(errMsg);
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        upd.setLastModifiedBy(operator);
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param wcsTaskIds
     * @param fourwaycarTaskIds
     * @param operator
     * @return void
     * @Description 下发批量出库任务成功
     * @Date 2020/7/30 11:47
     **/
    @Transactional
    @Override
    public void startStraightOutSuccess(List<Integer> wcsTaskIds, List<Integer> fourwaycarTaskIds, String operator) {
        Date now = new Date();

        WcsTask updTask = new WcsTask();
//        任务状态1创建2执行中3完成4异常5取消
        updTask.setTaskStatus("2");
        updTask.setGmtModified(now);
        updTask.setLastModifiedBy(operator);
        updTask.setIdList(wcsTaskIds);
        updTask.setGmtStart(now);
        taskMapper.updateByPrimaryKeyList(updTask);

        WcsFourwaycarTask upd = new WcsFourwaycarTask();
//        1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败'
        upd.setTaskStatus("3");
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        upd.setLastModifiedBy(operator);
        upd.setIdList(fourwaycarTaskIds);
        fourwaycarTaskMapper.updateByPrimaryKeyList(upd);
    }

    /**
     * @param wcsTaskIds
     * @param fourwaycarTaskIds
     * @param operator
     * @param errMsg
     * @return void
     * @Description 下发批量出库任务业务异常
     * @Date 2020/7/30 11:55
     **/
    @Transactional
    @Override
    public void startStraightOutFail(List<Integer> wcsTaskIds, List<Integer> fourwaycarTaskIds, String operator, String errMsg) {
        Date now = new Date();

        WcsTask updTask = new WcsTask();
//        任务状态1创建2执行中3完成4异常5取消
        updTask.setTaskStatus("4");
        updTask.setGmtModified(now);
        updTask.setLastModifiedBy(operator);
        updTask.setIdList(wcsTaskIds);
        updTask.setGmtStart(now);
        taskMapper.updateByPrimaryKeyList(updTask);

        WcsFourwaycarTask upd = new WcsFourwaycarTask();
//        1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败'
        upd.setTaskStatus("31");
        if (errMsg.length()>255){
            errMsg = errMsg.substring(0,254);
        }
        upd.setErrorMsg(errMsg);
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        upd.setLastModifiedBy(operator);
        upd.setIdList(fourwaycarTaskIds);
        fourwaycarTaskMapper.updateByPrimaryKeyList(upd);
    }

    /**
     * @param wcsTaskId
     * @param fourwaycarTaskId
     * @param operator
     * @return void
     * @Description 下发出库任务成功
     * @Date 2020/7/30 13:17
     **/
    @Override
    public void startHandOutSuccess(Integer wcsTaskId, Integer fourwaycarTaskId, String operator) {
        Date now = new Date();

        WcsTask updTask = new WcsTask();
//        任务状态1创建2执行中3完成4异常5取消
        updTask.setTaskStatus("2");
        updTask.setGmtModified(now);
        updTask.setLastModifiedBy(operator);
        updTask.setId(wcsTaskId);
        updTask.setGmtStart(now);
        taskMapper.updateByPrimaryKeySelective(updTask);

        WcsFourwaycarTask upd = new WcsFourwaycarTask();
//        1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败'
        upd.setTaskStatus("3");
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        upd.setLastModifiedBy(operator);
        upd.setFourwaycarTaskId(fourwaycarTaskId);
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param wcsTaskId
     * @param fourwaycarTaskId
     * @param operator
     * @param errMsg
     * @return void
     * @Description 下发手工出库任务异常
     * @Date 2020/7/30 13:18
     **/
    @Override
    public void startHandOutFail(Integer wcsTaskId, Integer fourwaycarTaskId, String operator, String errMsg) {
        Date now = new Date();

        WcsTask updTask = new WcsTask();
//        任务状态1创建2执行中3完成4异常5取消
        updTask.setTaskStatus("4");
        updTask.setGmtModified(now);
        updTask.setLastModifiedBy(operator);
        updTask.setId(wcsTaskId);
        updTask.setGmtStart(now);
        taskMapper.updateByPrimaryKeySelective(updTask);

        WcsFourwaycarTask upd = new WcsFourwaycarTask();
//        1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败'
        upd.setTaskStatus("31");
        if (errMsg.length()>255){
            errMsg = errMsg.substring(0,254);
        }
        upd.setErrorMsg(errMsg);
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        upd.setLastModifiedBy(operator);
        upd.setFourwaycarTaskId(fourwaycarTaskId);
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param wcsTaskId
     * @param fourwaycarTaskId
     * @param operator
     * @return void
     * @Description 下发移库任务成功
     * @Date 2020/7/30 14:36
     **/
    @Override
    public void startMoveTaskSuccess(Integer wcsTaskId, Integer fourwaycarTaskId, String operator) {
        Date now = new Date();

        WcsTask updTask = new WcsTask();
//        任务状态1创建2执行中3完成4异常5取消
        updTask.setTaskStatus("2");
        updTask.setGmtModified(now);
        updTask.setLastModifiedBy(operator);
        updTask.setId(wcsTaskId);
        updTask.setGmtStart(now);
        taskMapper.updateByPrimaryKeySelective(updTask);

        WcsFourwaycarTask upd = new WcsFourwaycarTask();
//        1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败'
        upd.setTaskStatus("3");
        upd.setGmtModified(now);
        upd.setLastModifiedBy(operator);
        upd.setGmtStart(now);
        upd.setFourwaycarTaskId(fourwaycarTaskId);
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param wcsTaskId
     * @param fourwaycarTaskId
     * @param operator
     * @param errMsg
     * @return void
     * @Description 下发移库任务失败
     * @Date 2020/7/30 14:38
     **/
    @Override
    public void startMoveTaskFail(Integer wcsTaskId, Integer fourwaycarTaskId, String operator, String errMsg) {
        Date now = new Date();

        WcsTask updTask = new WcsTask();
//        任务状态1创建2执行中3完成4异常5取消
        updTask.setTaskStatus("4");
        updTask.setGmtModified(now);
        updTask.setLastModifiedBy(operator);
        updTask.setId(wcsTaskId);
        updTask.setGmtStart(now);
        taskMapper.updateByPrimaryKeySelective(updTask);

        WcsFourwaycarTask upd = new WcsFourwaycarTask();
//        1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败'
        upd.setTaskStatus("31");
        if (errMsg.length()>255){
            errMsg = errMsg.substring(0,254);
        }
        upd.setErrorMsg(errMsg);
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        upd.setLastModifiedBy(operator);
        upd.setFourwaycarTaskId(fourwaycarTaskId);
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param wcsTaskId
     * @param fourwaycarTaskId
     * @param operator
     * @return void
     * @Description 启动入库成功
     * @Date 2020/7/30 17:19
     **/
    @Override
    public void startHandAndVirtualPalletInTaskSuccess(Integer wcsTaskId, Integer fourwaycarTaskId, String operator) {
        Date now = new Date();
//            任务状态1创建2执行中3完成4异常5取消
        WcsTask updTask = new WcsTask();
        updTask.setId(wcsTaskId);
        updTask.setTaskStatus("2");
        updTask.setGmtModified(now);
        updTask.setLastModifiedBy(operator);
        updTask.setGmtStart(now);
        taskMapper.updateByPrimaryKeySelective(updTask);

        WcsFourwaycarTask upd = new WcsFourwaycarTask();
//        1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败'
        upd.setTaskStatus("3");
        upd.setGmtModified(now);
        upd.setLastModifiedBy(operator);
        upd.setGmtStart(now);
        upd.setFourwaycarTaskId(fourwaycarTaskId);
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param wcsTaskId
     * @param fourwaycarTaskId
     * @param operator
     * @param errMsg
     * @return void
     * @Description 启动入库失败
     * @Date 2020/7/30 17:21
     **/
    @Override
    public void startHandAndVirtualPalletInTaskFail(Integer wcsTaskId, Integer fourwaycarTaskId, String operator, String errMsg) {
        Date now = new Date();

        WcsTask updTask = new WcsTask();
//        任务状态1创建2执行中3完成4异常5取消
        updTask.setTaskStatus("4");
        updTask.setGmtModified(now);
        updTask.setLastModifiedBy(operator);
        updTask.setId(wcsTaskId);
        updTask.setGmtStart(now);
        taskMapper.updateByPrimaryKeySelective(updTask);

        WcsFourwaycarTask upd = new WcsFourwaycarTask();
//        1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败'
        upd.setTaskStatus("31");
        if (errMsg.length()>255){
            errMsg = errMsg.substring(0,254);
        }
        upd.setErrorMsg(errMsg);
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        upd.setLastModifiedBy(operator);
        upd.setFourwaycarTaskId(fourwaycarTaskId);
        fourwaycarTaskMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * @param wcsTaskId
     * @param fourwaycarTaskId
     * @param operator
     * @return void
     * @Description 异常口入库
     * @Date 2020/9/20 20:46
     **/
    @Override
    public void startErrorHandInTaskSuccess(Integer wcsTaskId, Integer fourwaycarTaskId, String operator) {
        Date now = new Date();
//            任务状态1创建2执行中3完成4异常5取消
        WcsTask updTask = new WcsTask();
        updTask.setId(wcsTaskId);
        updTask.setTaskStatus("2");
        updTask.setGmtModified(now);
        updTask.setLastModifiedBy(operator);
        updTask.setGmtStart(now);
        taskMapper.updateByPrimaryKeySelective(updTask);
    }

    /**
     * @param type
     * @param limit
     * @return java.util.List<com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask>
     * @Description 有限制数的查找任务
     * @Date 2020/9/23 16:44
     */
    @Override
    public List<WcsTask> findCreateTaskByTypeLimit(String type, int limit) {
        WcsTask cond = new WcsTask();
        cond.setTaskStatus("1");
        cond.setTaskType(type);
//        b.task_no as user_defined1 四向车任务号,
//        b.fourwaycar_task_id as user_defined2
//        List<WcsTask> wcsTasks = taskMapper.selectWithFourwaycarTaskNoByStatusOrderBy(cond);
//        if (wcsTasks.size()>limit){
//            List<WcsTask> limitTasks = new ArrayList<>();
//            for (int j = 0; j < limit; j++) {
//                limitTasks.add(wcsTasks.get(j));
//                return limitTasks;
//            }
//        }

        //        找到创建状态的任务
        List<WcsTask> resultTasks = new ArrayList<>();
        List<WcsTask> createTasks = taskMapper.selectWithFourwaycarTaskNoByStatusOrderBy(cond);
        if (createTasks.size()>0){
            cond.setTaskStatus("2");
//            找到未结束的出库任务
            List<WcsTask> ingTasks = taskMapper.selectWithFourwaycarTaskNoByStatusOrderBy(cond);
            int layerOneCount = 0;
            int layerTwoCount = 0;
            int layerThreeCount = 0;
            int layerFourCount = 0;

            if (ingTasks.size()>0) {
                for (WcsTask ingTask:ingTasks){
                    Integer layer = ingTask.getFromAddress()/10000;
                    if (layer==1){
                        layerOneCount++;
                    }else if(layer==2){
                        layerTwoCount++;
                    }else if(layer==3){
                        layerThreeCount++;
                    }else if(layer==4){
                        layerFourCount++;
                    }
                }
            }

            for (WcsTask createTask:createTasks) {
                Integer layer = createTask.getFromAddress()/10000;
                if (layer==3){
                    layerThreeCount++;
                    if (layerThreeCount<=limit){
                        resultTasks.add(createTask);
                    }
                }else if(layer==4){
                    layerFourCount++;
                    if (layerFourCount<=limit){
                        resultTasks.add(createTask);
                    }
                }else if(layer==1){
                    layerOneCount++;
                    if (layerOneCount<=limit){
                        resultTasks.add(createTask);
                    }
                }else if(layer==2){
                    layerTwoCount++;
                    if (layerTwoCount<=limit){
                        resultTasks.add(createTask);
                    }
                }else {
                    log.error("不可能的层数{}",layer);
                }
            }
        }
//        for (WcsTask t:resultTasks) {
//            log.info(t.getFromAddress().toString());
//        }
        return resultTasks;
    }


}
