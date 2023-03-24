package com.penghaisoft.wcs.taskmanagement.model.business;

import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.expose.dto.PalletInDto;
import com.penghaisoft.wcs.expose.dto.PalletMoveDto;
import com.penghaisoft.wcs.expose.dto.PalletOutDto;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;

import java.util.List;
import java.util.Map;

public interface ITaskSplitService {

    /**
     * 是否重复任务
     * @param taskId
     * @return
     */
    boolean isDuplicateTask(Long taskId);

    /**
     * 是否重复任务，多个
     * @param taskIds
     * @return
     */
    boolean isDuplicateTasks(List<Long> taskIds);



    /**
     * 入库任务拆分
     * @param inBufferAddress 堆垛机入库线
     * @param pathId 路径id
     * @param palletInDto wms下达的任务对象
     * @return
     */
    ResponseResult splitInTask(Integer inBufferAddress, Integer pathId, PalletInDto palletInDto, WcsLocationReal locationReal);

    ResponseResult splitInTaskPlane(Integer inBufferAddress, Integer pathId, PalletInDto palletInDto, WcsLocationReal locationReal);


    /**
     * 拆解出库任务
     * @param palletOutDtoList
     * @param locationRealMap 使用到的实际库位数据
     * @return
     */
    ResponseResult splitOutTask(List<PalletOutDto> palletOutDtoList, Map<Integer,WcsLocationReal> locationRealMap);


    /**
     * 拆解移库任务
     * 1 将任务写入wcs_task
     * 2 生成一条wcs_stacker_task，堆垛机直接执行移库操作
     * @param palletMoveDto
     * @param locationRealMap
     * @return
     */
    ResponseResult splitMoveTask(PalletMoveDto palletMoveDto, Map<Integer, WcsLocationReal> locationRealMap);

    /**
     * 巧媳妇前期功能，生成AGV入库任务
     * @param bindInfo
     * @param startTaskNo
     * @param continueTaskNo
     * @param inTaskNo
     */
    void splitDemoInTask(WcsBindingInfo bindInfo,Integer startTaskNo,Integer continueTaskNo,Integer inTaskNo);


    /**
     * 生成叫托盘的任务
     * @return
     */
    void splitCallPalletTask(Long taskId,String operator,Integer locationCode,String palletCode,Integer pathId);


    /**
    * @Description 巧媳妇前期功能生成入库任务
    * @Date 2020/7/13 14:32
    * @param palletCode, lineId, taskCode
    * @return void
    **/
    void splitEarlyInTask(String palletCode, Integer lineId, Integer taskCode);

    /**
    * @Description 获取系统中创建状态的叫托盘任务
    * @Date 2020/7/17 9:47
    * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask
    **/
    WcsTask getCreateCallPalletTask();

    /**
    * @Description 拆分手工入库任务
    * @Date 2020/7/17 13:19
    * @param palletInDto
    * @return com.penghaisoft.framework.entity.ResponseResult
    **/
    ResponseResult splitHandInTask(PalletInDto palletInDto);

    /**
    * @Description 是否有agv的托盘任务
    * @Date 2020/7/20 20:25
    * @param palletCode
    * @return boolean
    **/
    boolean hasEarlyCallPalletTask(String palletCode);

    /**
    * @Description 生成agv任务
    * @Date 2020/7/20 20:30
    * @param palletCode, taskCode
    * @return void
    **/
    void splitEarlyCallPalletTask(String palletCode, Integer taskCode);

    /**
    * @Description 查找进行中的叫托盘任务
    * @Date 2020/7/28 11:00
    * @param
    * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask
    **/
    WcsTask getDoingCallPalletTask();

    /**
    * @Description 删除指定日期的某托盘的任务
    * @Date 2020/9/22 16:33
    * @param nowYMD, palletCode
    * @return void
    **/
    void clearTask(String nowYMD, String palletCode);
}
