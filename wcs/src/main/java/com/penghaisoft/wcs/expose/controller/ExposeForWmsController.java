package com.penghaisoft.wcs.expose.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsLocationRealService;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsPathService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsPath;
//import com.penghaisoft.wcs.common.service.factory.WcsDifferentCommonServiceFactory;
import com.penghaisoft.wcs.expose.dto.PalletInDto;
import com.penghaisoft.wcs.expose.dto.PalletMoveDto;
import com.penghaisoft.wcs.expose.dto.PalletOutDto;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.jobmanagement.model.business.IJobManagementService;
import com.penghaisoft.wcs.operation.service.FourwaycarOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskSplitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description ExposeForWmsController
 * @Auther zhangxu
 * @Date 2020/7/30 11:18
 **/
@RestController
@RequestMapping(value = "/expose")
@Slf4j
public class ExposeForWmsController {

    @Autowired
    private ITaskSplitService taskSplitService;

    @Autowired
    private IWcsPathService wcsPathService;

    @Autowired
    private IWcsLocationRealService wcsLocationRealService;

    @Autowired
    private IJobManagementService jobManagementService;

    @Autowired
    private IWcsCommonService wcsCommonService;

    @Autowired
    private FourwaycarOperationService fourwaycarOperationService;

//    {
//        "taskId": 200304100001,
//            "palletCode": "AA1001",
//            "fromAddress": 21,
//            "targetLocation": 10131,
//            "operator": "张三(35)"
//    }

    /**
     * 接收入库指令
     * 进行任务拆解，这里实际上是手工入库任务
     * 都是一个托盘一个托盘的
     * @param palletInDto
     * @return
     */
    @PostMapping("pallet/in")
    public ResponseResult receiveInCommand(@RequestBody PalletInDto palletInDto) {
//        TODO 在这里将WCS_task改为2
        ResponseResult result = new ResponseResult("1",null,null);
//        校验任务有没有重复下发
        Long taskId = palletInDto.getTaskId();
        boolean isDuplicate = taskSplitService.isDuplicateTask(taskId);
        if (isDuplicate){
            result.setCode("0");
            result.setMessage("任务重复！");
            return result;
        }
        int fromAddress = palletInDto.getFromAddress();
//        目的地库位
        int targetLocation = palletInDto.getTargetLocation();
        WcsLocationReal locationReal = wcsLocationRealService.findById(targetLocation);
        if (locationReal==null){
            result.setCode("0");
            result.setMessage("库位不存在！");
            return result;
        }
        result = taskSplitService.splitHandInTask(palletInDto);
        return result;
    }

    /**
     * 异常入库的启动
     * @param palletInDto
     * @return
     */
    @PostMapping("pallet/error/in")
    public ResponseResult receiveErrorInCommand(@RequestBody PalletInDto palletInDto) {
        ResponseResult result = new ResponseResult("1",null,null);
        String palletCode = palletInDto.getPalletCode();
        boolean flag = fourwaycarOperationService.palletInReady(palletCode);
        if (!flag){
            result.setCode("0");
        }
        return result;
    }

    /**
     * 接收出库指令
     * 整单校验与拆分
     * @param palletOutDtoList
     * @return
     */
    @PostMapping("pallet/out")
    public ResponseResult receiveOutCommand(@RequestBody List<PalletOutDto> palletOutDtoList) {

        ResponseResult result = new ResponseResult("0",null,null);
        Map<Integer,WcsLocationReal> locationRealMap = new HashMap<>();
//        校验
        for (PalletOutDto palletOut:palletOutDtoList) {
//            1检查任务号，把出库和出库携带的移库一块做校验
            List<Long> taskIds = new ArrayList<>();
            Long taskId = palletOut.getTaskId();
            taskIds.add(taskId);
            PalletMoveDto moveDto = palletOut.getRelyMoveTask();
            if (null!=moveDto){
                Long moveTaskId = moveDto.getTaskId();
                taskIds.add(moveTaskId);
            }
            boolean isDuplicate = taskSplitService.isDuplicateTasks(taskIds);
            if (isDuplicate){
                result.setCode("0");
                result.setMessage(taskId+"任务重复！");
                return result;
            }

//            2检查库位是否存在，把出库库位和可能携带的移库任务的库位一块校验
            List<Integer> locationIds = new ArrayList<>();
            Integer fromLocation = palletOut.getFromLocation();
            locationIds.add(fromLocation);
            if (null!=moveDto){
                Integer moveFromLocation = moveDto.getFromLocation();
                Integer moveTargetLocation = moveDto.getTargetLocation();
                locationIds.add(moveFromLocation);
                locationIds.add(moveTargetLocation);
            }
            List<WcsLocationReal> locationReals = wcsLocationRealService.findByIds(locationIds);
            if (locationReals.size()!=locationIds.size()){
                result.setCode("0");
                result.setMessage(taskId+"库位不存在！");
                return result;
            }
//            存入库位信息
            for (WcsLocationReal locationReal:locationReals) {
                locationRealMap.put(locationReal.getLocationId(),locationReal);
            }

//            3 根据库位转获取出库线
//            可能是2001,2002,2003
            int target = palletOut.getTargetAddress();
            boolean outCheck = wcsCommonService.isOutLine(target);
            if (!outCheck){
                result.setCode("0");
                result.setMessage(taskId+"的目的地"+target+"不合法！");
                return result;
            }
        }
//            通过校验后开始拆分任务
        result = taskSplitService.splitOutTask(palletOutDtoList,locationRealMap);
        return result;
    }

    /**
     * 接收移库指令
     * 只接受相同货物，或者空巷道
     * @param palletMoveDto
     * @return
     */
    @PostMapping("pallet/move")
    public ResponseResult receiveMoveCommand(@RequestBody PalletMoveDto palletMoveDto) {
        ResponseResult result = new ResponseResult("1",null,null);

//        1库位校验
        Integer fromLocationId = palletMoveDto.getFromLocation();
        Integer toLocationId = palletMoveDto.getTargetLocation();
        List<Integer> locationIds = new ArrayList<>();
        locationIds.add(fromLocationId);
        locationIds.add(toLocationId);
        List<WcsLocationReal> locationReals = wcsLocationRealService.findByIds(locationIds);
        if (locationReals.size() != 2){
            result.setCode("0");
            result.setMessage("库位不存在！");
            return result;
        }

//        2 判断这两个库位是不是同层
        Integer fromLayer = fromLocationId/10000;
        Integer toLayer = toLocationId/10000;
        if (fromLayer!=toLayer){
            result.setCode("0");
            result.setMessage("非同层移库无法操作！");
            return result;
        }

//            存入库位信息
        Map<Integer,WcsLocationReal> locationRealMap = new HashMap<>();
        for (WcsLocationReal locationReal:locationReals) {
            locationRealMap.put(locationReal.getLocationId(),locationReal);
        }
//        3 移库拆解
        result = taskSplitService.splitMoveTask(palletMoveDto,locationRealMap);

        return result;
    }

    /**
     * 托盘移除
     * @param palletInDto
     * @return
     */
    @PostMapping("pallet/remove")
    public ResponseResult receivePalletRemoveCommand(@RequestBody PalletInDto palletInDto) {
        ResponseResult result = new ResponseResult("1",null,null);
        String palletCode = palletInDto.getPalletCode();
        SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
        String nowYMD = sdf.format(new Date());
        nowYMD = nowYMD + Constant.TaskType.PRODUCT_IN.getTaskType();
//        清除wcs任务
        taskSplitService.clearTask(nowYMD,palletCode);
//        四向车清除库口占位状态
        boolean flag = fourwaycarOperationService.unLockInPort();
        if (!flag){
            result.setCode("0");
            result.setMessage("调用四向车解锁库口失败!");
        }
        return result;
    }

}
