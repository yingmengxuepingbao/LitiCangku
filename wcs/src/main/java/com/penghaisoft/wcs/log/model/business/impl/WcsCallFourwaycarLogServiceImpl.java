package com.penghaisoft.wcs.log.model.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.log.model.business.IWcsCallFourwaycarLogService;
import com.penghaisoft.wcs.log.model.dao.WcsCallFourwaycarLogMapper;
import com.penghaisoft.wcs.log.model.entity.WcsCallFourwaycarLog;
import com.penghaisoft.wcs.operation.model.AgvPositionItem;
import com.penghaisoft.wcs.operation.model.FourWayCarTask;
import com.penghaisoft.wcs.operation.model.FourWayCarTaskItem;
import com.penghaisoft.wcs.operation.service.FourwaycarOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName WcsCallFourwaycarLogServiceImpl
 * @Description 记录调用四向车接口的日志
 * @Author zhangx
 * @Date 2020/7/29 18:16
 **/
@Service
public class WcsCallFourwaycarLogServiceImpl implements IWcsCallFourwaycarLogService {

    @Autowired
    private WcsCallFourwaycarLogMapper callFourwaycarLogMapper;
    @Autowired
    private FourwaycarOperationService fourwaycarOperationService;

    /**
     * 下发四向车指令日志
     *
     * @param fourWayCarTask
     * @param reqFlag        1成功 2业务异常 3服务端异常 4客户端异常
     * @param respData       返回数据
     * @param sendTime       发送时间
     */
    @Override
    public void addTaskReceiveLog(FourWayCarTask fourWayCarTask, String reqFlag, String respData, Date sendTime) {
        WcsCallFourwaycarLog callFourwaycarLog = new WcsCallFourwaycarLog();
        callFourwaycarLog.setInterfaceName("TASK_RECEIVE");
        callFourwaycarLog.setGroupId(fourWayCarTask.getGroupId());
        callFourwaycarLog.setReqParam(JSON.toJSONString(fourWayCarTask.getTasks()));
        callFourwaycarLog.setReqFlag(reqFlag);
        callFourwaycarLog.setRespData(respData);
        callFourwaycarLog.setSendTime(sendTime);
        callFourwaycarLog.setRespTime(new Date());
        callFourwaycarLogMapper.insertSelective(callFourwaycarLog);
    }

    /**
     * 调用四向车时候的简单接口
     *
     * @param url
     * @param param
     * @param reqFlag
     * @param respData
     * @param sendTime
     */
    @Override
    public void addSimpleLog(String url, Map<String, Object> param, String reqFlag, String respData, Date sendTime) {
        WcsCallFourwaycarLog callFourwaycarLog = new WcsCallFourwaycarLog();
        callFourwaycarLog.setInterfaceName(url);
        callFourwaycarLog.setReqParam(JSON.toJSONString(param));
        callFourwaycarLog.setReqFlag(reqFlag);
        callFourwaycarLog.setRespData(respData);
        callFourwaycarLog.setSendTime(sendTime);
        callFourwaycarLog.setRespTime(new Date());
        callFourwaycarLogMapper.insertSelective(callFourwaycarLog);
    }
    /**
     * @Description: 调用四向车日志查询
     * @Author: jzh
     * @Date: 2020/7/30
     */
    @Override
    public Pager<WcsCallFourwaycarLog> findCallFourwaycarLog(int page, int rows, WcsCallFourwaycarLog condition) {
        Pager<WcsCallFourwaycarLog> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
        List<WcsCallFourwaycarLog> records = callFourwaycarLogMapper.queryList(pager,condition);
        long size = callFourwaycarLogMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }
    /**
     * @Description: 四向车日志重发
     * @Author: jzh
     * @Date: 2020/7/30
     */
    @Override
    public Resp resend(WcsCallFourwaycarLog wcsCallFourwaycarLog, String loginName) {
        Resp resp = new Resp();
        //查询日志 信息 判断状态
        WcsCallFourwaycarLog callFourwaycarLog = callFourwaycarLogMapper.selectByPrimaryKey(wcsCallFourwaycarLog.getId());
        if (callFourwaycarLog==null){
            resp.setCode("1");
            resp.setMsg("该指令查询不到");
            return resp;
        }
        if ("1".equals(callFourwaycarLog.getReqFlag())){
            resp.setCode("1");
            resp.setMsg("该指令状态不可重发");
            return resp;
        }else {
            if ("重传成功".equals(callFourwaycarLog.getUserDefined2())){
                resp.setCode("1");
                resp.setMsg("该指令已经重传成功");
                return resp;
            }

            //判断完毕 执行重传
            String url = callFourwaycarLog.getInterfaceName();


            FourWayCarTask fourWayCarTask = new FourWayCarTask();
            fourWayCarTask.setGroupId(callFourwaycarLog.getGroupId());
            String reqParam = callFourwaycarLog.getReqParam();

            JSONArray jsonArray = JSONArray.parseArray(reqParam);
            List<FourWayCarTaskItem> items = new ArrayList<>();

            for (int i=0 ; i<jsonArray.size();i++){
                JSONObject job = jsonArray.getJSONObject(i);
                String taskId = job.getString("taskId");
                String barCode = job.getString("barCode");
                String taskType = job.getString("taskType");
                String startNode = job.getString("startNode");
                String endNode = job.getString("endNode");
                String district = job.getString("district");
                FourWayCarTaskItem item = new FourWayCarTaskItem();
                item.setTaskId(taskId);
                item.setBarCode(barCode);
                item.setTaskType(Integer.valueOf(taskType));
                item.setStartNode(startNode);
                item.setEndNode(endNode);
                item.setDistrict(district);
                items.add(item);
            }
            fourWayCarTask.setTasks(items);
            Integer callFourwaycarLogId = wcsCallFourwaycarLog.getId();

            Resp respSend =  fourwaycarOperationService.resendWcsInterface(url,fourWayCarTask,callFourwaycarLogId);
            if ("1".equals(respSend.getCode())){
                resp.setCode("0");
                resp.setMsg("重传成功");
            }else {
                resp.setCode("1");
                resp.setMsg(respSend.getMsg());
                return resp;
            }
        }
        resp.setCode("0");
        resp.setMsg("重传成功");
        return resp;
    }
    /**
     * @Description: 重传修改日志（根据id）
     * reqFlag 1成功 2业务异常 3服务端异常 4客户端异常
     * @Author: jzh
     * @Date: 2020/7/30
     */ 
    @Override
    public void updateTaskReceiveLog(String reqFlag, Integer callFourwaycarLogId, String message) {
//        reqFlag 1成功 2业务异常 3服务端异常 4客户端异常
        //查询日志信息
        WcsCallFourwaycarLog callFourwaycarLog = callFourwaycarLogMapper.selectByPrimaryKey(callFourwaycarLogId);
        int resendAmount = Integer.parseInt(callFourwaycarLog.getUserDefined1());
        resendAmount = resendAmount +1;
        WcsCallFourwaycarLog fourwaycarLog = new WcsCallFourwaycarLog();
        fourwaycarLog.setId(callFourwaycarLogId);
        fourwaycarLog.setUserDefined1(String.valueOf(resendAmount));
        if ("1".equals(reqFlag)){
            fourwaycarLog.setUserDefined2("重传成功");
        }else if ("2".equals(reqFlag)){
            fourwaycarLog.setUserDefined2("业务异常:"+message);
        }else if ("3".equals(reqFlag)){
            fourwaycarLog.setUserDefined2("服务端异常:"+message);
        }else if ("4".equals(reqFlag)){
            fourwaycarLog.setUserDefined2("客户端异常:"+message);
        }
        int updateNum = callFourwaycarLogMapper.updateByPrimaryKeySelective(fourwaycarLog);

    }
}
