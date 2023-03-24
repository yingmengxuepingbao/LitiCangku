package com.penghaisoft.wcs.taskmanagement.model.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.dao.WcsLocationRealMapper;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.monitormanagement.model.dao.WcsErrorLogMapper;
import com.penghaisoft.wcs.operation.model.dao.WcsBindingInfoMapper;
import com.penghaisoft.wcs.operation.model.dao.WcsBindingInfoPlaneMapper;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskPlaneDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.dao.WcsAgvTaskPlaneMapper;
import com.penghaisoft.wcs.taskmanagement.model.dao.WcsTaskMapper;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @ClassName TaskPlaneDispatchServiceImpl
 * @Author luot
 * @Date 2020/7/29 17:59
 **/
@Slf4j
@Service
public class TaskPlaneDispatchServiceImpl implements ITaskPlaneDispatchService {

    @Autowired
    private WcsAgvTaskPlaneMapper WcsAgvTaskPlaneMapper;

    @Autowired
    private WcsTaskMapper taskMapper;

    @Autowired
    private WcsErrorLogMapper errorLogMapper;

    @Autowired
    private WcsLocationRealMapper locationRealMapper;


    @Autowired
    private WcsBindingInfoMapper wcsBindingInfoMapper;

    @Autowired
    private WcsBindingInfoPlaneMapper wcsBindingInfoPlaneMapper;


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
    public WcsAgvTaskPlane findWaitingStartAgvTaskByPath(Integer pathId) {
        WcsAgvTaskPlane waitTask = null;
        WcsAgvTaskPlane cond = new WcsAgvTaskPlane();
        cond.setPathId(pathId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        cond.setTaskStatus("1");
        List<WcsAgvTaskPlane> agvTasks = WcsAgvTaskPlaneMapper.selectByCond(cond);
        if (agvTasks.size() > 0) {
            waitTask = agvTasks.get(0);
        }
        return waitTask;
    }

    /**
     * zhangx
     *
     * @return
     * @Description 查找等待入库的任务
     * @Date 2020/7/3 10:21
     **/
    @Override
    public List<WcsAgvTaskPlane> findWaitingContinueAgvTask() {
        WcsAgvTaskPlane cond = new WcsAgvTaskPlane();
//        任务状态1创建；2下发；3到达接货点；4继续任务；5任务完成；21下发失败；41继续任务失败'
        cond.setTaskStatus("3");
        List<WcsAgvTaskPlane> continueTasks = WcsAgvTaskPlaneMapper.selectByCond(cond);
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("2");
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("21");
        String errorMsg = agvResult.toJSONString();
        if (errorMsg.length() > 255) {
            errorMsg = errorMsg.substring(0, 254);
        }
        upd.setErrorMsg(errorMsg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("21");
        upd.setErrorMsg(msg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * agv的回调-结束任务，
     *
     * @param taskNo
     * @param operator
     * @return
     */
    @Override
    public Resp finishAgvTaskByTaskNo(Integer taskNo, String operator) {
//        todo 如果上传的是失败怎么办？
        Resp resp = new Resp();
        resp.setCode("0");//这里是成功的意思
        Date now = new Date();

//        1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消
        WcsAgvTaskPlane startCond = new WcsAgvTaskPlane();
        startCond.setTaskStatus("5");
        startCond.setTaskNo(taskNo);
        List<WcsAgvTaskPlane> inTasks = WcsAgvTaskPlaneMapper.selectByCond(startCond);
//        证明这是起始任务的结束
        if (inTasks.size() == 1) {
            WcsAgvTaskPlane currentTask = inTasks.get(0);

            WcsAgvTaskPlane updInTask = new WcsAgvTaskPlane();
            updInTask.setAgvTaskId(currentTask.getAgvTaskId());
            updInTask.setTaskStatus("6");
            updInTask.setGmtModified(now);
            updInTask.setGmtEnd(now);
            updInTask.setLastModifiedBy(operator);
            WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(updInTask);

            wcsBindingInfoPlaneMapper.deleteByPalletCode(currentTask.getPalletCode());
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setAgvTaskId(agvTaskId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("5");
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("51");
        String errorMsg = agvResult.toJSONString();
        if (errorMsg.length() > 255) {
            errorMsg = errorMsg.substring(0, 254);
        }
        upd.setErrorMsg(errorMsg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("51");
        upd.setErrorMsg(msg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
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
        WcsAgvTaskPlane continueCond = new WcsAgvTaskPlane();
        continueCond.setUserDefined1(continueTaskNo);
        List<WcsAgvTaskPlane> continueTasks = WcsAgvTaskPlaneMapper.selectByCond(continueCond);
        if (continueTasks.size() == 1) {
            WcsAgvTaskPlane currentTask = continueTasks.get(0);

            WcsAgvTaskPlane updContinueTask = new WcsAgvTaskPlane();
            updContinueTask.setAgvTaskId(currentTask.getAgvTaskId());
            //         '任务状态1创建；2下发；3到达接货点；4继续任务；5任务完成；21下发失败；41继续任务失败',
            updContinueTask.setTaskStatus("5");
            Date now = new Date();
            updContinueTask.setGmtModified(now);
            updContinueTask.setLastModifiedBy(early);
            updContinueTask.setGmtEnd(now);
            WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(updContinueTask);
        }
    }

    /**
     * 根据任务号找到任务对象
     *
     * @param taskNo
     * @return
     */
    @Override
    public WcsAgvTaskPlane findAgvTaskByTaskNo(Integer taskNo) {
        WcsAgvTaskPlane agvTask = null;
        //        这里是继续任务的结束
        WcsAgvTaskPlane continueCond = new WcsAgvTaskPlane();
        continueCond.setTaskNo(taskNo);
        List<WcsAgvTaskPlane> continueTasks = WcsAgvTaskPlaneMapper.selectByCond(continueCond);
        if (continueTasks.size() == 1) {
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
        WcsAgvTaskPlane continueCond = new WcsAgvTaskPlane();
        continueCond.setTaskNo(taskNo);
        List<WcsAgvTaskPlane> continueTasks = WcsAgvTaskPlaneMapper.selectByCond(continueCond);
        if (continueTasks.size() == 1) {
            WcsAgvTaskPlane currentTask = continueTasks.get(0);

            WcsAgvTaskPlane updContinueTask = new WcsAgvTaskPlane();
            updContinueTask.setAgvTaskId(currentTask.getAgvTaskId());
            //         '任务状态1创建；2下发；3到达接货点；4继续任务；5任务完成；21下发失败；41继续任务失败',
            updContinueTask.setTaskStatus("5");
            Date now = new Date();
            updContinueTask.setGmtModified(now);
            updContinueTask.setLastModifiedBy(early);
            updContinueTask.setGmtEnd(now);
            WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(updContinueTask);
        }
    }

    /**
     * 根据继续任务号查找
     *
     * @param taskNo
     * @return
     */
    @Override
    public WcsAgvTaskPlane findAgvTaskByContinueTaskNo(Integer taskNo) {
        WcsAgvTaskPlane agvTask = null;
        //        这里是继续任务的结束
        WcsAgvTaskPlane continueCond = new WcsAgvTaskPlane();
        continueCond.setUserDefined2(taskNo.toString());
        List<WcsAgvTaskPlane> continueTasks = WcsAgvTaskPlaneMapper.selectByCond(continueCond);
        if (continueTasks.size() == 1) {
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setTaskStatus("4");
        upd.setAgvTaskId(agvTaskId);
        upd.setPriority(priority);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(new Date());
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
    }

    /**
     * 根据入库任务号删除
     *
     * @param inTaskNo
     */
    @Override
    public void deleteFinishAgvTask(Integer inTaskNo) {
        WcsAgvTaskPlaneMapper.deleteByUserDefined3(inTaskNo.toString());
    }

    /**
     * @param target
     * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane
     * @Description 根据目的地找到等待入库的agv任务
     * @Date 2020/7/11 11:52
     **/
    @Override
    public WcsAgvTaskPlane findWaitingInAgvTaskByTarget(String target) {
        List<WcsAgvTaskPlane> tasks = WcsAgvTaskPlaneMapper.selectEarlyWaitingInTaskByCond(null);
        if (tasks.size() > 0) {
            return tasks.get(0);
        }
        return null;
    }

    @Override
    public void finishEarlyAgvTaskById(Integer agvTaskId) {
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setAgvTaskId(agvTaskId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("6");
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
    }


    @Override
    public void setAgvWaitingBuffStatusTwo(String taskCode, int priority) {
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setUserDefined5(taskCode);
        upd.setPriority(priority);
        upd.setUserDefined4("wait");
        WcsAgvTaskPlaneMapper.updateByTaskCode(upd);
    }

    @Override
    public void deleteFinishAgvTaskByTaskCode(Integer taskCode) {
        WcsAgvTaskPlaneMapper.deleteByTaskCode(taskCode);
    }

    /**
     * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane
     * @Description 找到等待入库的agv任务-处于暂存区
     * @Date 2020/7/13 14:50
     **/
    @Override
    public WcsAgvTaskPlane findEarlyWaitingInAgvTask() {
        WcsAgvTaskPlane cond = new WcsAgvTaskPlane();
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        cond.setTaskStatus("4");
        List<WcsAgvTaskPlane> tasks = WcsAgvTaskPlaneMapper.selectEarlyWaitingInTaskByCond(cond);
        if (tasks.size() > 0) {
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("4");
        upd.setTaskNo(taskCode);
        upd.setPriority(priority);
        upd.setLastModifiedBy("early");
        upd.setGmtModified(new Date());
        WcsAgvTaskPlaneMapper.updateByTaskCode(upd);
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus(status);
        upd.setTaskNo(taskCode);
        upd.setGmtModified(new Date());
        WcsAgvTaskPlaneMapper.updateByTaskCode(upd);
    }

    /**
     * @param status
     * @return java.util.List<com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane>
     * @Description 根据任务状态找到agv任务
     * @Date 2020/7/14 10:51
     **/
    @Override
    public List<WcsAgvTaskPlane> findAgvTaskByStatus(String status) {
        WcsAgvTaskPlane cond = new WcsAgvTaskPlane();
        cond.setTaskStatus(status);
        List<WcsAgvTaskPlane> result = WcsAgvTaskPlaneMapper.selectByCond(cond);
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
//        任务状态1创建；2下发；3到达缓冲区/托盘出库口；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("3");
        upd.setTaskNo(taskCode);
        upd.setPriority(priority);
        upd.setGmtModified(new Date());
        WcsAgvTaskPlaneMapper.updateByTaskCode(upd);
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
        WcsAgvTaskPlane startCond = new WcsAgvTaskPlane();
        startCond.setTaskStatus("5");
        startCond.setTaskNo(taskCode);
        List<WcsAgvTaskPlane> inTasks = WcsAgvTaskPlaneMapper.selectByCond(startCond);
//        证明这是起始任务的结束
        if (inTasks.size() == 1) {
            WcsAgvTaskPlane currentTask = inTasks.get(0);

            WcsAgvTaskPlane updInTask = new WcsAgvTaskPlane();
            updInTask.setAgvTaskId(currentTask.getAgvTaskId());
            updInTask.setTaskStatus("6");
            updInTask.setGmtEnd(now);
            updInTask.setGmtModified(now);
            updInTask.setLastModifiedBy(operator);
            WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(updInTask);

            Long taskId = currentTask.getTaskId();
            WcsTask updWcsTask = new WcsTask();
            updWcsTask.setTaskId(taskId);
//            任务状态1创建2执行中3完成4异常5取消
            updWcsTask.setTaskStatus("3");
            updWcsTask.setGmtEnd(now);
            updWcsTask.setEndBy(operator);
            int count = taskMapper.updateByTaskIdSelective(updWcsTask);
            log.info("结束叫托盘任务，wcs_task记录数={}", count);
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
        WcsAgvTaskPlaneMapper.deleteByPrimaryKey(id);
    }

    /**
     * @return java.lang.String
     * @Description 清除数据 绑定+agv
     * @Date 2020/7/20 21:15
     **/
    @Override
    public String clearEarlyData() {
        String result = "删除agv任务数=";
        int c1 = WcsAgvTaskPlaneMapper.deleteAll();
        result = result + c1 + ";删除绑定任务数=";
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("2");
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        upd.setGmtStart(now);
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
//        2 修改WCS_TASK表
        List<WcsTask> wcsTasks = taskMapper.selectByTaskId(taskId);
        if (wcsTasks.size() == 1) {
            WcsTask wcsTask = wcsTasks.get(0);
//            任务状态1创建2执行中3完成4异常5取消
            if ("1".equals(wcsTask.getTaskStatus())) {
                WcsTask updTask = new WcsTask();
                updTask.setId(wcsTask.getId());
                updTask.setTaskStatus("2");
                updTask.setGmtModified(now);
                updTask.setLastModifiedBy(operator);
                taskMapper.updateByPrimaryKeySelective(updTask);
            }
        } else {
            log.error("启动下线任务时，{}的WCS_TASK记录数={}", taskId, wcsTasks.size());
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("21");
        String errorMsg = agvResult.toJSONString();
        if (errorMsg.length() > 255) {
            errorMsg = errorMsg.substring(0, 254);
        }
        upd.setErrorMsg(errorMsg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
        //        2 修改WCS_TASK表
        List<WcsTask> wcsTasks = taskMapper.selectByTaskId(taskId);
        if (wcsTasks.size() == 1) {
            WcsTask wcsTask = wcsTasks.get(0);
//            任务状态1创建2执行中3完成4异常5取消
            if ("1".equals(wcsTask.getTaskStatus())) {
                WcsTask updTask = new WcsTask();
                updTask.setId(wcsTask.getId());
                updTask.setTaskStatus("4");
                updTask.setErrorMsg("AGV业务异常");
                updTask.setGmtModified(now);
                updTask.setLastModifiedBy(operator);
                taskMapper.updateByPrimaryKeySelective(updTask);
            }
        } else {
            log.error("修改WCS_TASK时，{}的WCS_TASK记录数={}", taskId, wcsTasks.size());
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
        upd.setAgvTaskId(agvTaskId);
//         任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus("21");
        upd.setErrorMsg(msg);
        upd.setLastModifiedBy(operator);
        upd.setGmtModified(now);
        WcsAgvTaskPlaneMapper.updateByPrimaryKeySelective(upd);
        //        2 修改WCS_TASK表
        List<WcsTask> wcsTasks = taskMapper.selectByTaskId(taskId);
        if (wcsTasks.size() == 1) {
            WcsTask wcsTask = wcsTasks.get(0);
//            任务状态1创建2执行中3完成4异常5取消
            if ("1".equals(wcsTask.getTaskStatus())) {
                WcsTask updTask = new WcsTask();
                updTask.setId(wcsTask.getId());
                updTask.setTaskStatus("4");
                updTask.setErrorMsg("AGV服务端或本客户端异常");
                updTask.setGmtModified(now);
                updTask.setLastModifiedBy(operator);
                taskMapper.updateByPrimaryKeySelective(updTask);
            }
        } else {
            log.error("修改WCS_TASK时，{}的WCS_TASK记录数={}", taskId, wcsTasks.size());
        }
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
        WcsAgvTaskPlane upd = new WcsAgvTaskPlane();
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        upd.setTaskStatus(status);
        upd.setTaskNo(taskCode);
        upd.setGmtModified(new Date());
        upd.setLastModifiedBy(operator);
        WcsAgvTaskPlaneMapper.updateByTaskCode(upd);
    }


}
