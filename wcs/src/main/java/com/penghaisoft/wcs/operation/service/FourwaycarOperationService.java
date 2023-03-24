package com.penghaisoft.wcs.operation.service;

import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.operation.model.FourWayCarTask;
import com.penghaisoft.wcs.operation.model.FourwaycarResult;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;

import java.util.List;

/**
* @Description 四向车操作
* @Date 2020/7/8 10:43
* @param
* @return
**/
public interface FourwaycarOperationService {


    /**
     * 给四向车下发入库任务
     * @param taskId
     * @param taskNo
     * @param locationCode
     * @param from
     * @param palletCode
     * @return
     */
    Resp sendInTask(Long taskId, Integer taskNo, Integer locationCode, Integer from, String palletCode);

    /**
     * 给四向车下发出库任务
     * @param taskId
     * @param taskNo
     * @param locationCode
     * @param to
     * @param palletCode
     * @return
     */
    Resp sendOutTask(Long taskId, Integer taskNo, Integer locationCode, Integer to, String palletCode);

    /**
     * 下发一堆出库任务
     * @param wcsTasks
     * @return
     */
    Resp sendOutTasks(List<WcsTask> wcsTasks);


    /**
     * 下发单个移库任务
     * @param taskId
     * @param taskNo
     * @param from
     * @param to
     * @param palletCode
     * @return
     */
    Resp sendMoveTask(Long taskId, Integer taskNo, Integer from, Integer to, String palletCode);
        /**
        * @Description 入库口是否允许放货
        * @Date 2020/7/28 11:26
        * @param
        * @return boolean
        **/
    boolean inLocAllow();

    /**
    * @Description 占用四向车入库口
    * @Date 2020/7/28 11:31
    * @return void
    **/
    boolean occupyInLoc(String palletCode);

    /**
    * @Description 上传入库口AGV到位
    * @Date 2020/7/28 15:33
    * @param palletCode
    * @return boolean
    **/
    boolean palletInReady(String palletCode);
    /**
     * @Description: 调用四向车接口重发
     * @Author: jzh
     * @Date: 2020/7/30
     */
    Resp resendWcsInterface(String url, FourWayCarTask fourWayCarTask, Integer callFourwaycarLogId);

    /**
    * @Description 解锁
    * @Date 2020/9/22 17:08
    * @return boolean
    **/
    boolean unLockInPort();
}
