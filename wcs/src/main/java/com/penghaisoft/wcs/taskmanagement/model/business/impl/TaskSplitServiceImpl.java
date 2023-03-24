package com.penghaisoft.wcs.taskmanagement.model.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsLocationRealService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.expose.dto.PalletInDto;
import com.penghaisoft.wcs.expose.dto.PalletMoveDto;
import com.penghaisoft.wcs.expose.dto.PalletOutDto;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskSplitService;
import com.penghaisoft.wcs.taskmanagement.model.dao.*;
import com.penghaisoft.wcs.taskmanagement.model.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务拆分服务
 * @Description TaskSplitServiceImpl
 * @Auther zhangxu
 * @Date 2020/7/2 17:57
 **/
@Slf4j
@Service
public class TaskSplitServiceImpl implements ITaskSplitService {

    @Autowired
    private IWcsCommonService wcsCommonService;

    @Autowired
    private IWcsLocationRealService wcsLocationRealService;

    @Autowired
    private WcsTaskMapper wcsTaskMapper;

    @Autowired
    private WcsAgvTaskMapper wcsAgvTaskMapper;

    @Autowired
    private WcsAgvTaskPlaneMapper wcsAgvTaskPlaneMapper;

    @Autowired
    private WcsFourwaycarTaskMapper wcsFourwaycarTaskMapper;


    /**
     * 是否重复任务
     *
     * @param taskId
     * @return
     */
    @Override
    public boolean isDuplicateTask(Long taskId) {
        boolean isDuplicate = true;
        List<WcsTask> wcsTasks = wcsTaskMapper.selectByTaskId(taskId);
        if (wcsTasks.size()==0){
            isDuplicate = false;
        }
        return isDuplicate;
    }

    /**
     * 是否重复任务，多个
     *
     * @param taskIds
     * @return
     */
    @Override
    public boolean isDuplicateTasks(List<Long> taskIds) {
        boolean isDuplicate = true;
        List<WcsTask> wcsTasks = new ArrayList<>();
        if (taskIds.size()==1){
            wcsTasks = wcsTaskMapper.selectByTaskId(taskIds.get(0));
        }else {
            wcsTasks = wcsTaskMapper.selectByTaskIds(taskIds);
        }
        if (wcsTasks.size()==0){
            isDuplicate = false;
        }
        return isDuplicate;
    }

    /**
     * 入库任务拆分
     *
     * @param inBufferAddress 入库口
     * @param pathId          路径id
     * @param palletInDto     wms下达的任务对象
     * @return
     */
    @Transactional
    @Override
    public ResponseResult splitInTask(Integer inBufferAddress, Integer pathId, PalletInDto palletInDto, WcsLocationReal locationReal) {
        Date now = new Date();
//        1 将任务写入wcs_task
        WcsTask wcsTask = new WcsTask();
//        wms下达的,年月日(6位)+task_type(2位)+5位序列号
        Long taskId = palletInDto.getTaskId();
        String palletCode = palletInDto.getPalletCode();
        String operator = palletInDto.getOperator();
        wcsTask.setTaskId(taskId);
//        截取6 7位位taskType
        String taskType = taskId.toString().substring(6,8);
        wcsTask.setTaskType(taskType);
//        创建
//          '任务状态1创建2执行中3完成4异常5取消',
        wcsTask.setTaskStatus("1");
        wcsTask.setPalletCode(palletCode);
        wcsTask.setFromAddress(palletInDto.getFromAddress());
//        现在还没有推荐库位
//        wcsTask.setToAddress(palletInDto.getTargetLocation());
        wcsTask.setReportWms("0");
        wcsTask.setCreateBy(operator);
        wcsTask.setGmtCreate(now);
        wcsTaskMapper.insertSelective(wcsTask);

//        生成2个任务号，1个给agv，1个给四向车
        int[] taskNos = wcsCommonService.getTaskNo(2);
        int agvTaskNo = taskNos[0];
        int fourwaycarTaskNo = taskNos[1];

//        2 生成一条wcs_agv_task，记录的是路径id，从码垛线到入库口
        WcsAgvTask agvTask = new WcsAgvTask();
        agvTask.setTaskNo(agvTaskNo);
        agvTask.setTaskId(taskId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        agvTask.setTaskStatus("1");
        agvTask.setPalletCode(palletCode);
        agvTask.setPathId(pathId);
        agvTask.setCreateBy(operator);
        agvTask.setGmtCreate(now);
        wcsAgvTaskMapper.insertSelective(agvTask);

//        3 生成一条wcs_fourwaycar_task，直接从入库口到达库位
        WcsFourwaycarTask fourwaycarTask = new WcsFourwaycarTask();
        fourwaycarTask.setTaskNo(fourwaycarTaskNo);
        fourwaycarTask.setTaskId(taskId);
        fourwaycarTask.setTaskStatus("1");
//        入库
        fourwaycarTask.setFourwaycarTaskType("1");
//        fourwaycarTask.setLocationId(locationReal.getLocationId());
//        任务类型 1 取货 2 放货 4移动
        fourwaycarTask.setActionType("2");
        fourwaycarTask.setPalletCode(palletCode);
        fourwaycarTask.setCreateBy(operator);
        fourwaycarTask.setGmtCreate(now);
        wcsFourwaycarTaskMapper.insertSelective(fourwaycarTask);

        return new ResponseResult("1","",null);
    }

    /**
     * 入库任务拆分【其它码垛线】
     *
     * @param inBufferAddress 入库口
     * @param pathId          路径id
     * @param palletInDto     wms下达的任务对象
     * @return
     */
    @Transactional
    @Override
    public ResponseResult splitInTaskPlane(Integer inBufferAddress, Integer pathId, PalletInDto palletInDto, WcsLocationReal locationReal) {
        Date now = new Date();
//        wms下达的,年月日(6位)+task_type(2位)+5位序列号
        Long taskId = palletInDto.getTaskId();
        String palletCode = palletInDto.getPalletCode();
        String operator = palletInDto.getOperator();

//        生成2个任务号，1个给agv，1个给四向车
        int[] taskNos = wcsCommonService.getTaskNo(2);
        int agvTaskNo = taskNos[0];

//        2 生成一条wcs_agv_task，记录的是路径id，从码垛线到入库口
        WcsAgvTaskPlane wcsAgvTaskPlane = new WcsAgvTaskPlane();
        wcsAgvTaskPlane.setTaskNo(agvTaskNo);
        wcsAgvTaskPlane.setTaskId(taskId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        wcsAgvTaskPlane.setTaskStatus("1");
        wcsAgvTaskPlane.setPalletCode(palletCode);
        wcsAgvTaskPlane.setPathId(pathId);
        wcsAgvTaskPlane.setCreateBy(operator);
        wcsAgvTaskPlane.setGmtCreate(now);
        wcsAgvTaskPlaneMapper.insertSelective(wcsAgvTaskPlane);

        return new ResponseResult("1","",null);
    }

    /**
     * 拆解出库任务
     * 巧媳妇理论上是不可能有移库的
     * @param palletOutDtoList
     * @param locationRealMap 使用到的实际库位数据
     * @return
     */
    @Transactional
    @Override
    public ResponseResult splitOutTask(List<PalletOutDto> palletOutDtoList, Map<Integer,WcsLocationReal> locationRealMap) {
        Date now = new Date();
        for (PalletOutDto palletOutDto:palletOutDtoList) {
            boolean haveMoveTask = false;
//            生成wcs 任务号
            int taskNoSize = 1;
            PalletMoveDto palletMoveDto = palletOutDto.getRelyMoveTask();
            if (null != palletMoveDto){
                haveMoveTask = true;
                taskNoSize = 2;
            }
            int[] taskNos = wcsCommonService.getTaskNo(taskNoSize);
            int fourwaycarTaskNo = taskNos[0];
            int fourwaycarMoveTaskNo = 0;
            if (haveMoveTask){
                fourwaycarMoveTaskNo = taskNos[1];
            }
    //            如果有依赖的移库要多产生一个四向车移库任务(wcs_task与wcs_fourwaycar_task)
//            if (haveMoveTask){
//                saveMovTaskRelyOnOut(palletMoveDto,locationRealMap,fourwaycarMoveTaskNo,palletOutDto.getOperator());
//            }

    //        1 将出库任务写入wcs_task
            WcsTask wcsOutTask = new WcsTask();
//        wms下达的,年月日(6位)+task_type(2位)+5位序列号
            Long taskId = palletOutDto.getTaskId();
            String palletCode = palletOutDto.getPalletCode();
            String operator = palletOutDto.getOperator();
            wcsOutTask.setTaskId(taskId);
//        截取6 7位位taskType,理论上是20
            String taskType = taskId.toString().substring(6,8);
            wcsOutTask.setTaskType(taskType);
//        创建
            wcsOutTask.setTaskStatus("1");
            wcsOutTask.setPalletCode(palletCode);
            wcsOutTask.setFromAddress(palletOutDto.getFromLocation());
            wcsOutTask.setToAddress(palletOutDto.getTargetAddress());
            wcsOutTask.setReportWms("0");
            wcsOutTask.setCreateBy(operator);
            wcsOutTask.setGmtCreate(now);
//            放入移库任务依赖，必须依赖的先执行完才能执行本任务
//            if (haveMoveTask){
//                wcsOutTask.setRelyTaskId(palletMoveDto.getTaskId());
//            }
            wcsTaskMapper.insertSelective(wcsOutTask);

//          2 生成一条wcs_fourwaycar_task，堆垛机从出库库位取货送到出库缓冲区
//            这个表不用记录出到哪个缓冲区，下指令时候转换的，都是固定的
//            出库库位
            WcsFourwaycarTask fourwaycarTask = new WcsFourwaycarTask();
            fourwaycarTask.setTaskNo(fourwaycarTaskNo);
            fourwaycarTask.setTaskId(taskId);
            fourwaycarTask.setTaskStatus("1");
//        任务类型 1 入库 2 出库 3 移库
            fourwaycarTask.setFourwaycarTaskType("2");
            fourwaycarTask.setLocationId(palletOutDto.getFromLocation());
//        任务类型 1 取货 2 放货 4移动
            fourwaycarTask.setActionType("1");
            fourwaycarTask.setPalletCode(palletCode);
            fourwaycarTask.setCreateBy(operator);
            fourwaycarTask.setGmtCreate(now);
            wcsFourwaycarTaskMapper.insertSelective(fourwaycarTask);

        }

        return new ResponseResult("1","",null);
    }

    /**
     * 拆解移库任务
     * 1 将任务写入wcs_task
     * 2 生成一条wcs_stacker_task，堆垛机直接执行移库操作
     *
     * @param palletMoveDto
     * @param locationRealMap
     * @return
     */
    @Transactional
    @Override
    public ResponseResult splitMoveTask(PalletMoveDto palletMoveDto, Map<Integer, WcsLocationReal> locationRealMap) {
        Date now = new Date();
//        1 写入总任务表
        WcsTask wcsOutTask = new WcsTask();
//        wms下达的,年月日(6位)+task_type(2位)+5位序列号
        Long taskId = palletMoveDto.getTaskId();
        Integer fromLocationId = palletMoveDto.getFromLocation();
        Integer toLocationId = palletMoveDto.getTargetLocation();
        String palletCode = palletMoveDto.getPalletCode();
        String operator = palletMoveDto.getOperator();
        wcsOutTask.setTaskId(taskId);
//        截取6 7位位taskType 应该是30
        String taskType = taskId.toString().substring(6,8);
        wcsOutTask.setTaskType(taskType);
//        创建
        wcsOutTask.setTaskStatus("1");
        wcsOutTask.setPalletCode(palletCode);
        wcsOutTask.setFromAddress(fromLocationId);
        wcsOutTask.setToAddress(toLocationId);
        wcsOutTask.setReportWms("0");
        wcsOutTask.setCreateBy(operator);
        wcsOutTask.setGmtCreate(now);
        wcsTaskMapper.insertSelective(wcsOutTask);

//        写入四向车任务
        Integer moveTaskNo = wcsCommonService.getTaskNo(1)[0];
//          2 生成一条wcs_fourwaycar_task，
        WcsFourwaycarTask fourwaycarTask = new WcsFourwaycarTask();
        fourwaycarTask.setTaskNo(moveTaskNo);
        fourwaycarTask.setTaskId(taskId);
        fourwaycarTask.setTaskStatus("1");
//        任务类型 1 入库 2 出库 3 移库
        fourwaycarTask.setFourwaycarTaskType("3");
        fourwaycarTask.setLocationId(fromLocationId);
        fourwaycarTask.setUserDefined1(toLocationId.toString());
//        任务类型 1 取货 2 放货 4移动
        fourwaycarTask.setActionType("1");
        fourwaycarTask.setPalletCode(palletCode);
        fourwaycarTask.setCreateBy(operator);
        fourwaycarTask.setGmtCreate(now);
        wcsFourwaycarTaskMapper.insertSelective(fourwaycarTask);

        return new ResponseResult("1","",null);
    }

    /**
     * 巧媳妇前期功能，生成AGV入库任务
     *
     * @param bindInfo
     * @param startTaskNo
     * @param continueTaskNo
     * @param inTaskNo
     */
    @Override
    public void splitDemoInTask(WcsBindingInfo bindInfo, Integer startTaskNo, Integer continueTaskNo,Integer inTaskNo) {
        Date now = new Date();

//        2 生成一条wcs_agv_task，记录的是路径id，从码垛线到入库口
        WcsAgvTask agvTask = new WcsAgvTask();
        agvTask.setTaskNo(startTaskNo);
//        agvTask.setTaskId(taskId);
        agvTask.setTaskStatus("1");
        agvTask.setPalletCode(bindInfo.getPalletCode());
        agvTask.setPathId(bindInfo.getAddressId());
        agvTask.setCreateBy("early");
        agvTask.setGmtCreate(now);
//        存放了目的地
        String target = bindInfo.getUserDefined1();
        agvTask.setUserDefined1(target);
//        agv继续任务号
        agvTask.setUserDefined2(continueTaskNo.toString());
//        agv入库任务号
        agvTask.setUserDefined3(inTaskNo.toString());
        wcsAgvTaskMapper.insertSelective(agvTask);

    }

    /**
     * 生成叫托盘任务
     *
     * @return
     */
    @Override
    public void splitCallPalletTask(Long taskId,String operator,Integer locationCode,String palletCode,Integer pathId) {
        Date now = new Date();
//        1 将任务写入wcs_task
        WcsTask wcsTask = new WcsTask();
//        wms下达的,年月日(6位)+task_type(2位)+5位序列号
        wcsTask.setTaskId(taskId);
//        截取6 7位位taskType,应该是25
        String taskType = taskId.toString().substring(6,8);
        wcsTask.setTaskType(taskType);
//        创建
//          '任务状态1创建2执行中3完成4异常5取消',
        wcsTask.setTaskStatus("1");
        wcsTask.setPalletCode(palletCode);
        wcsTask.setFromAddress(locationCode);
//        传到3号码垛线
        wcsTask.setToAddress(3);
        wcsTask.setReportWms("0");
        wcsTask.setCreateBy(operator);
        wcsTask.setGmtCreate(now);
        wcsTaskMapper.insertSelective(wcsTask);

//        生成2个任务号，1个给agv，1个给四向车
        int[] taskNos = wcsCommonService.getTaskNo(2);
        int agvTaskNo = taskNos[0];
        int fourwaycarTaskNo = taskNos[1];

//        2 生成一条wcs_fourwaycar_task，直接从库位到3001
        WcsFourwaycarTask fourwaycarTask = new WcsFourwaycarTask();
        fourwaycarTask.setTaskNo(fourwaycarTaskNo);
        fourwaycarTask.setTaskId(taskId);
        fourwaycarTask.setTaskStatus("1");
//        任务类型 1 入库 2 出库 3 移库
        fourwaycarTask.setFourwaycarTaskType("2");
        fourwaycarTask.setLocationId(locationCode);
//        任务类型 1 取货 2 放货 4移动
        fourwaycarTask.setActionType("1");
        fourwaycarTask.setPalletCode(palletCode);
        fourwaycarTask.setCreateBy(operator);
        fourwaycarTask.setGmtCreate(now);
        wcsFourwaycarTaskMapper.insertSelective(fourwaycarTask);

//        3 生成一条wcs_agv_task，记录的是路径id，从码垛线到入库口
        WcsAgvTask agvTask = new WcsAgvTask();
        agvTask.setTaskNo(agvTaskNo);
        agvTask.setTaskId(taskId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        agvTask.setTaskStatus("1");
        agvTask.setPalletCode(palletCode);
//        从3001移动到3
        agvTask.setPathId(pathId);
        agvTask.setCreateBy(operator);
        agvTask.setGmtCreate(now);
        wcsAgvTaskMapper.insertSelective(agvTask);

    }


    /**
     * @param palletCode
     * @param lineId
     * @param taskCode
     * @return void
     * @Description 巧媳妇前期功能生成入库任务
     * @Date 2020/7/13 14:32
     **/
    @Override
    public void splitEarlyInTask(String palletCode, Integer lineId, Integer taskCode) {

        Date now = new Date();

//        2 生成一条wcs_agv_task，记录的是路径id，从码垛线到入库口
        WcsAgvTask agvTask = new WcsAgvTask();
//        agvTask.setTaskId(taskId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        agvTask.setTaskStatus("2");
        agvTask.setPalletCode(palletCode);
        agvTask.setPathId(lineId);
        agvTask.setCreateBy("early");
        agvTask.setGmtCreate(now);
        agvTask.setTaskNo(taskCode);
        wcsAgvTaskMapper.insertSelective(agvTask);

    }

    /**
     * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask
     * @Description 获取系统中创建状态的叫托盘任务
     * @Date 2020/7/17 9:47
     **/
    @Override
    public WcsTask getCreateCallPalletTask() {
        WcsTask result = null;
        WcsTask cond = new WcsTask();
//        任务状态1创建2执行中3完成4异常5取消
        cond.setTaskStatus("1");
        cond.setTaskType("25");
        List<WcsTask> wcsTasks = wcsTaskMapper.selectByCond(cond);
        if (wcsTasks.size()==1){
            result = wcsTasks.get(0);
        }else {
//            log.info("当前系统存在创建状态叫托盘任务数量={}",wcsTasks.size());
        }
        return result;
    }

    /**
     * @param palletInDto
     * @return com.penghaisoft.framework.entity.ResponseResult
     * @Description 拆分手工入库任务
     * @Date 2020/7/17 13:19
     **/
    @Transactional
    @Override
    public ResponseResult splitHandInTask(PalletInDto palletInDto) {
        Date now = new Date();
//        1 将任务写入wcs_task
        WcsTask wcsTask = new WcsTask();
//        wms下达的,年月日(6位)+task_type(2位)+5位序列号
        Long taskId = palletInDto.getTaskId();
        String palletCode = palletInDto.getPalletCode();
        String operator = palletInDto.getOperator();
        wcsTask.setTaskId(taskId);
//        截取6 7位位taskType,应该是16或者14
        String taskType = taskId.toString().substring(6,8);
        wcsTask.setTaskType(taskType);
//        创建
//          '任务状态1创建2执行中3完成4异常5取消',
        wcsTask.setTaskStatus("1");
        wcsTask.setPalletCode(palletCode);
        wcsTask.setFromAddress(palletInDto.getFromAddress());
        wcsTask.setToAddress(palletInDto.getTargetLocation());
        wcsTask.setReportWms("0");
        wcsTask.setCreateBy(operator);
        wcsTask.setGmtCreate(now);
        wcsTaskMapper.insertSelective(wcsTask);

//        生成1个任务号，给四向车
        int[] taskNos = wcsCommonService.getTaskNo(1);
        int fourwaycarTaskNo = taskNos[0];


//        2 生成一条wcs_fourwaycar_task，直接从入库口到达库位
        WcsFourwaycarTask fourwaycarTask = new WcsFourwaycarTask();
        fourwaycarTask.setTaskNo(fourwaycarTaskNo);
        fourwaycarTask.setTaskId(taskId);
        fourwaycarTask.setTaskStatus("1");
//        入库
        fourwaycarTask.setFourwaycarTaskType("1");
        fourwaycarTask.setLocationId(palletInDto.getTargetLocation());
//        任务类型 1 取货 2 放货 4移动
        fourwaycarTask.setActionType("2");
        fourwaycarTask.setPalletCode(palletCode);
        fourwaycarTask.setCreateBy(operator);
        fourwaycarTask.setGmtCreate(now);
        wcsFourwaycarTaskMapper.insertSelective(fourwaycarTask);

        JSONObject data = new JSONObject();
        data.put("wcsTaskId",wcsTask.getId());
        data.put("fourwaycarTaskId",fourwaycarTask.getFourwaycarTaskId());
        data.put("fourwaycarTaskNo",fourwaycarTaskNo);
        return new ResponseResult("1","",data);
    }

    /**
     * @param palletCode
     * @return boolean
     * @Description 是否有agv的托盘任务
     * @Date 2020/7/20 20:25
     **/
    @Override
    public boolean hasEarlyCallPalletTask(String palletCode) {
        WcsAgvTask cond = new WcsAgvTask();
        cond.setPalletCode(palletCode);
        List<WcsAgvTask> agvTasks = wcsAgvTaskMapper.selectByCond(cond);
        if (agvTasks.size() >0 ){
            return true;
        }
        return false;
    }

    /**
     * @param palletCode
     * @param taskCode
     * @return void
     * @Description 生成agv任务
     * @Date 2020/7/20 20:30
     **/
    @Override
    public void splitEarlyCallPalletTask(String palletCode, Integer taskCode) {
        Date now = new Date();

//        2 生成一条wcs_agv_task，记录的是路径id，从码垛线到入库口
        WcsAgvTask agvTask = new WcsAgvTask();
//        agvTask.setTaskId(taskId);
//        任务状态1创建；2下发；3到达缓冲区；4等待；5继续入库；6 完成；7取消；21下发失败；51继续入库任务失败
        agvTask.setTaskStatus("2");
        agvTask.setPalletCode(palletCode);
        agvTask.setPathId(3);
        agvTask.setCreateBy("early");
        agvTask.setGmtCreate(now);
        agvTask.setTaskNo(taskCode);
        wcsAgvTaskMapper.insertSelective(agvTask);
    }

    /**
     * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask
     * @Description 查找进行中的叫托盘任务
     * @Date 2020/7/28 11:00
     **/
    @Override
    public WcsTask getDoingCallPalletTask() {
        WcsTask result = null;
        WcsTask cond = new WcsTask();
//        任务状态1创建2执行中3完成4异常5取消
        cond.setTaskStatus("2");
        cond.setTaskType("25");
        List<WcsTask> wcsTasks = wcsTaskMapper.selectByCond(cond);
        if (wcsTasks.size()>0){
            result = wcsTasks.get(0);
        }else {
//            log.info("当前系统存在运行状态叫托盘任务数量={}",wcsTasks.size());
        }
        return result;
    }

    /**
     * @param nowYMD
     * @param palletCode
     * @return void
     * @Description 删除指定日期的某托盘的任务
     * @Date 2020/9/22 16:33
     **/
    @Override
    public void clearTask(String nowYMD, String palletCode) {
        List<WcsTask> wcsTasks = wcsTaskMapper.selectByPalletAndTaskIdPrefix(palletCode,nowYMD);
        if (wcsTasks.size()==1){
            WcsTask wcsTask = wcsTasks.get(0);
            Long taskId = wcsTask.getTaskId();
            List<Long> delTaskIds = new ArrayList<>();
            delTaskIds.add(taskId);
            wcsTaskMapper.deleteByPrimaryKey(wcsTask.getId());
            wcsAgvTaskMapper.deleteAgvTaskByIdList(delTaskIds);
            wcsFourwaycarTaskMapper.deleteFourwaycarTaskByIdList(delTaskIds);
        }else {
            log.info("清除任务时任务前缀为{}的{}记录数={}",nowYMD,palletCode,wcsTasks.size());
        }
    }

}
