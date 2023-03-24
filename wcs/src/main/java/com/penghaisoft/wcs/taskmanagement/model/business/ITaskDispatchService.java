package com.penghaisoft.wcs.taskmanagement.model.business;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import com.penghaisoft.wcs.taskmanagement.model.entity.*;

import java.util.List;

/**
 * 任务调度服务
 */
public interface ITaskDispatchService {

    /**
     * 根据任务id 查询移库任务的目的地库位
     * @param taskId
     * @return
     */
    WcsLocationReal queryMoveToLocationByTaskId(Long taskId);


    /**
    * @Description 根据不同的码垛线入库路径找到创建状态的任务
    * @Date 2020/7/3 9:53
    * @param pathId 路径id
    * @return
    **/
    WcsAgvTask findWaitingStartAgvTaskByPath(Integer pathId);


    /**
     * zhangx
    * @Description 查找等待入库的任务
    * @Date 2020/7/3 10:21
    * @param
    * @return
    **/
    List<WcsAgvTask> findWaitingContinueAgvTask();


    /**
     * 成功下发agv任务
     * @param agvTaskId
     * @param operator
     */
    void startAgvTaskSuccess(Integer agvTaskId, String operator);


    /**
     * 下发agv任务业务异常
     * @param agvTaskId
     * @param agvResult
     */
    void startAgvTaskFail(Integer agvTaskId, JSONObject agvResult, String operator);


    /**
     * 启动agv任务报错
     * @param agvTaskId
     * @param msg
     * @param operator
     */
    void startAgvTaskError(Integer agvTaskId, String msg, String operator);


    /**
     * agv的回调-结束任务，
     * @param taskNo
     * @param operator
     * @return
     */
    Resp finishAgvTaskByTaskNo(Integer taskNo,String operator);


    /**
     * 继续agv任务下发成功
     * @param agvTaskId
     * @param operator
     */
    void continueAgvTaskSuccess(Integer agvTaskId, String operator);


    /**
     * 继续agv任务业务员异常
     * @param agvTaskId
     * @param agvResult
     * @param operator
     */
    void continueAgvTaskFail(Integer agvTaskId, JSONObject agvResult, String operator);


    /**
     * 继续agv任务错误
     * @param agvTaskId
     * @param msg
     * @param operator
     */
    void continueAgvTaskError(Integer agvTaskId, String msg, String operator);


    /**
     * 根据继续任务号结束agv任务
     * @param agvResultCode
     * @param continueTaskNo
     * @param early
     */
    void finishAgvTaskByContinueTaskNo(String agvResultCode, String continueTaskNo, String early);


    /**
     * 根据任务号找到任务对象
     * @param taskNo
     * @return
     */
    WcsAgvTask findAgvTaskByTaskNo(Integer taskNo);


    /**
     * 结束前期叫托盘任务
     * @param taskNo
     * @param early
     */
    void finishAgvDemoPalletTaskByTaskNo(Integer taskNo, String early);


    /**
     * 查找四向车任务，并将状态改为2-申请
     * @param palletCode
     * @param operator
     * @return
     */
    Resp findAndApplyFourWayCarWaitingInTask(String palletCode, String operator);

    /**
    * @Description 根据托盘号找到四向车任务编号
    * @Date 2020/7/9 14:47
    * @param palletCode
    * @return WcsTask
    **/
    WcsTask findFourWayCarWaitingInTask(String palletCode);


    /**
     * 根据总任务号查找数据
     * @param taskId
     * @return
     */
    WcsFourwaycarTask findFourWayCarTaskByTaskId(Long taskId);


    /**
     * 根据继续任务号查找
     * @param taskNo
     * @return
     */
    WcsAgvTask findAgvTaskByContinueTaskNo(Integer taskNo);

    /**
    * @Description 将agv任务设置为等待
    * @Date 2020/7/11 11:01
    * @param agvTaskId, operator, priority
    * @return void
    **/
    void setAgvWaitingBuffStatus(Integer agvTaskId, String operator, int priority);


    /**
     * 根据入库任务号删除
     * @param inTaskNo
     */
    void deleteFinishAgvTask(Integer inTaskNo);

    /**
    * @Description 根据目的地找到等待入库的agv任务
    * @Date 2020/7/11 11:52
    * @param target
    * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask
    **/
    WcsAgvTask findWaitingInAgvTaskByTarget(String target);

    void finishEarlyAgvTaskById(Integer agvTaskId);


    void setAgvWaitingBuffStatusTwo(String taskCode, int intValue);

    void deleteFinishAgvTaskByTaskCode(Integer taskCode);

    /**
    * @Description 找到等待入库的agv任务-处于暂存区
    * @Date 2020/7/13 14:50
    * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask
    **/
    WcsAgvTask findEarlyWaitingInAgvTask();

    /**
    * @Description 设置AGV为等待状态
    * @Date 2020/7/13 15:04
    * @param taskCode, priority
    * @return void
    **/
    void setEarlyAgvWaitingStatus(Integer taskCode, Integer priority);

    /**
    * @Description 根据任务号更新agv任务状态
    * @Date 2020/7/13 16:11
    * @param taskCode, status
    * @return void
    **/
    void setEarlyAgvStatusByTaskNo(Integer taskCode, String status);

    /**
    * @Description 根据任务状态找到agv任务
    * @Date 2020/7/14 10:51
    * @param
    * @return java.util.List<com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask>
    **/
    List<WcsAgvTask> findAgvTaskByStatus(String status);

    /**
    * @Description agv到达缓存区
    * @Date 2020/7/14 21:13
    * @param taskCode, priority
    * @return void
    **/
    void setAgvArriveBufferStatus(Integer taskCode, int priority);

    /**
    * @Description 结束整体叫托盘任务
    * @Date 2020/7/17 14:40
    * @param taskCode, operator
    * @return com.penghaisoft.framework.util.Resp
    **/
    Resp finishCallPalletTaskByTaskNo(Integer taskCode, String operator);

    /**
    * @Description 根据id删除agv任务
    * @Date 2020/7/20 20:39
    * @param id
    * @return void
    **/
    void deleteFinishAgvTaskById(Integer id);

    /**
    * @Description 清除数据 绑定+agv
    * @Date 2020/7/20 21:15
    * @return java.lang.String
    **/
    String clearEarlyData();

    /**
    * @Description 下线入库时候启动任务成功
    * @Date 2020/7/28 9:16
    * @param taskId, agvTaskId, operator
    * @return void
    **/
    void startProductInTaskSuccess(Long taskId, Integer agvTaskId, String operator);

    /**
    * @Description 下线入库时候启动任务失败，发生业务异常
     * zhangx
    * @Date 2020/7/28 9:22
    * @param taskId, agvTaskId, agvResult, operator
    * @return void
    **/
    void startProductInTaskFail(Long taskId, Integer agvTaskId, JSONObject agvResult, String operator);

    /**
    * @Description 下线入库时候启动任务失败，发生程序报错
    * @Date 2020/7/28 9:25
     * zhangx
    * @param taskId, agvTaskId, msg, operator
    * @return void
    **/
    void startProductInTaskError(Long taskId, Integer agvTaskId, String msg, String operator);

    /**
    * @Description 修改四向车任务状态
    * @Date 2020/7/28 17:16
    * @param status, taskId, operator
    * @return void
    **/
    void setFourwaycarStatusByTaskId(String status, Integer fourwaycarTaskId, String operator,Integer locationCode);

    /**
    * @Description 下发四向车任务成功
    * @Date 2020/7/28 17:28
    * @param fourwaycarTaskId, operator
    * @return void
    **/
    void sendFourwaycarTaskSuccess(Integer fourwaycarTaskId, String operator);

    /**
    * @Description 下发四向车任务失败
    * @Date 2020/7/28 17:32
    * @param fourwaycarTaskId, returnInfo, operator
    * @return void
    **/
    void sendFourwaycarTaskFail(Integer fourwaycarTaskId, String returnInfo, String operator);

    /**
    * @Description 下发四向车任务报错
    * @Date 2020/7/28 17:36
    * @param fourwaycarTaskId, msg, operator
    * @return void
    **/
    void sendFourwaycarTaskError(Integer fourwaycarTaskId, String msg, String operator);

    /**
    * @Description 根据任务号设置agv 状态
    * @Date 2020/7/29 13:16
    * @param taskCode, status, operator
    * @return void
    **/
    void setAgvStatusByTaskNo(Integer taskCode, String status, String operator);

    /**
    * @Description 修改四向车任务状态
    * @Date 2020/7/29 14:33
    * @param taskNo, status, operator
    * @return void
    **/
    void setFourwaycarStatusByTaskNo(Integer taskNo, String status, String operator);

    /**
    * @Description 根据任务号获取四向车记录
    * @Date 2020/7/29 14:45
    * @param taskNo
    * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsFourwaycarTask
    **/
    WcsFourwaycarTask findFourWayCarTaskByTaskNo(Integer taskNo);

    /**
    * @Description 结束四向车和整体任务
    * @Date 2020/7/29 15:07
    * @param taskId
    * @return void
    **/
    void finishFourwaycarAndWholeTask(Long taskId,String operator);

    /**
    * @Description 根据taskId查找AGV任务
    * @Date 2020/7/29 15:24
    * @param taskId
    * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask
    **/
    WcsAgvTask findAgvTaskByTaskId(Long taskId);

    /**
    * @Description 找到创建状态的任务-根据任务类型
     * @param type
    * @Date 2020/7/29 17:06
    * @return java.util.List<com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask>
    **/
    List<WcsTask> findCreateTaskByType(String type);

    /**
    * @Description 成功启动叫托盘任务
    * @Date 2020/7/29 17:38
    * @param wcsTaskId, fourwaycarTaskId, operator
    * @return void
    **/
    void startCallPalletSuccess(Integer wcsTaskId, Integer fourwaycarTaskId, String operator);

    /**
    * @Description 启动叫托盘任务业务异常
    * @Date 2020/7/29 17:44
    * @param wcsTaskId, fourwaycarTaskId, operator, errMsg
    * @return void
    **/
    void startCallPalletFail(Integer wcsTaskId, Integer fourwaycarTaskId, String operator, String errMsg);

    /**
    * @Description 下发批量出库任务成功
    * @Date 2020/7/30 11:47
    * @param wcsTaskIds, fourwaycarTaskIds, operator
    * @return void
    **/
    void startStraightOutSuccess(List<Integer> wcsTaskIds, List<Integer> fourwaycarTaskIds, String operator);

    /**
    * @Description 下发批量出库任务业务异常
    * @Date 2020/7/30 11:55
    * @param wcsTaskIds, fourwaycarTaskIds, operator, errMsg
    * @return void
    **/
    void startStraightOutFail(List<Integer> wcsTaskIds, List<Integer> fourwaycarTaskIds, String operator, String errMsg);

    /**
    * @Description 下发手工出库任务成功
    * @Date 2020/7/30 13:17
    * @param wcsTaskId, fourwaycarTaskId, operator
    * @return void
    **/
    void startHandOutSuccess(Integer wcsTaskId, Integer fourwaycarTaskId, String operator);

    /**
    * @Description 下发手工出库任务异常
    * @Date 2020/7/30 13:18
    * @param wcsTaskId, fourwaycarTaskId, operator, errMsg
    * @return void
    **/
    void startHandOutFail(Integer wcsTaskId, Integer fourwaycarTaskId, String operator, String errMsg);

    /**
    * @Description 下发移库任务成功
    * @Date 2020/7/30 14:36
    * @param wcsTaskId, fourwaycarTaskId, operator
    * @return void
    **/
    void startMoveTaskSuccess(Integer wcsTaskId, Integer fourwaycarTaskId, String operator);

    /**
    * @Description 下发移库任务失败
    * @Date 2020/7/30 14:38
    * @param wcsTaskId, fourwaycarTaskId, operator, errMsg
    * @return void
    **/
    void startMoveTaskFail(Integer wcsTaskId, Integer fourwaycarTaskId, String operator, String errMsg);

    /**
    * @Description 启动入库成功
    * @Date 2020/7/30 17:19
    * @param wcsTaskId, fourwaycarTaskId, operator
    * @return void
    **/
    void startHandAndVirtualPalletInTaskSuccess(Integer wcsTaskId, Integer fourwaycarTaskId, String operator);

    /**
    * @Description 启动入库失败
    * @Date 2020/7/30 17:21
    * @param wcsTaskId, fourwaycarTaskId, operator, errMsg
    * @return void
    **/
    void startHandAndVirtualPalletInTaskFail(Integer wcsTaskId, Integer fourwaycarTaskId, String operator, String errMsg);

    /**
    * @Description 异常口入库
    * @Date 2020/9/20 20:46
    * @param wcsTaskId, fourwaycarTaskId, operator
    * @return void
    **/
    void startErrorHandInTaskSuccess(Integer wcsTaskId, Integer fourwaycarTaskId, String operator);

    /**
    * @Description 有限制数的查找任务
    * @Date 2020/9/23 16:44
    * @return java.util.List<com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask>
    **/
    List<WcsTask> findCreateTaskByTypeLimit(String type, int i);
}
