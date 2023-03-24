package com.penghaisoft.wcs.taskmanagement.model.business;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane;

import java.util.List;

/**
 * 任务调度服务
 */
public interface ITaskPlaneDispatchService {




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
    WcsAgvTaskPlane findWaitingStartAgvTaskByPath(Integer pathId);


    /**
     * zhangx
    * @Description 查找等待入库的任务
    * @Date 2020/7/3 10:21
    * @param
    * @return
    **/
    List<WcsAgvTaskPlane> findWaitingContinueAgvTask();


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
    Resp finishAgvTaskByTaskNo(Integer taskNo, String operator);


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
    WcsAgvTaskPlane findAgvTaskByTaskNo(Integer taskNo);


    /**
     * 结束前期叫托盘任务
     * @param taskNo
     * @param early
     */
    void finishAgvDemoPalletTaskByTaskNo(Integer taskNo, String early);

    /**
     * 根据继续任务号查找
     * @param taskNo
     * @return
     */
    WcsAgvTaskPlane findAgvTaskByContinueTaskNo(Integer taskNo);

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
    * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane
    **/
    WcsAgvTaskPlane findWaitingInAgvTaskByTarget(String target);

    void finishEarlyAgvTaskById(Integer agvTaskId);


    void setAgvWaitingBuffStatusTwo(String taskCode, int intValue);

    void deleteFinishAgvTaskByTaskCode(Integer taskCode);

    /**
    * @Description 找到等待入库的agv任务-处于暂存区
    * @Date 2020/7/13 14:50
    * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane
    **/
    WcsAgvTaskPlane findEarlyWaitingInAgvTask();

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
    * @return java.util.List<com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane>
    **/
    List<WcsAgvTaskPlane> findAgvTaskByStatus(String status);

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
    * @Description 根据任务号设置agv 状态
    * @Date 2020/7/29 13:16
    * @param taskCode, status, operator
    * @return void
    **/
    void setAgvStatusByTaskNo(Integer taskCode, String status, String operator);
}
