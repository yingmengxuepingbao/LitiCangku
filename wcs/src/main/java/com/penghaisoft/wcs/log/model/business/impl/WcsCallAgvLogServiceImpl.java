package com.penghaisoft.wcs.log.model.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.log.model.business.IWcsCallAgvLogService;
import com.penghaisoft.wcs.log.model.dao.WcsCallAgvLogMapper;
import com.penghaisoft.wcs.log.model.entity.WcsCallAgvLog;
import com.penghaisoft.wcs.operation.model.AgvPositionItem;
import com.penghaisoft.wcs.operation.model.AgvTask;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import com.penghaisoft.wcs.taskmanagement.model.dao.WcsManualAgvTaskMapper;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsManualAgvTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName WcsCallAgvLogServiceImpl
 * @Description 调用
 * @Author zhangx
 * @Date 2020/7/25 9:41
 **/
@Service
public class WcsCallAgvLogServiceImpl implements IWcsCallAgvLogService {

    @Autowired
    private WcsCallAgvLogMapper callAgvLogMapper;

    @Autowired
    private AgvOperationService agvOperationService;

    @Resource
    private WcsManualAgvTaskMapper wcsManualAgvTaskMapper;
    /**
     * @Description 申请任务单的日志
     * @Date 2020/7/25 9:54
     * @param agvTask, sendTime, respTime, sendFlag, respData
     * @return void
     **/
    @Override
    public void addGenTaskLog(AgvTask agvTask, Date sendTime, Date respTime, String sendFlag, String respData){
        WcsCallAgvLog callAgvLog = new WcsCallAgvLog();
        callAgvLog.setInterfaceName("genAgvSchedulingTask");
        callAgvLog.setReqCode(agvTask.getReqCode());
        callAgvLog.setTaskCode(Integer.parseInt(agvTask.getTaskCode()));
        callAgvLog.setPositionCode(JSON.toJSONString(agvTask.getPositionCodePath()));
        callAgvLog.setTaskType(agvTask.getTaskTyp());
        callAgvLog.setSendTime(sendTime);
        callAgvLog.setRespTime(respTime);
        callAgvLog.setReqFlag(sendFlag);
        callAgvLog.setRespData(respData);
        callAgvLogMapper.insertSelective(callAgvLog);

    }

    /**
     * @param agvTask
     * @param sendTime
     * @param respTime
     * @param sendFlag
     * @param respData
     * @return void
     * @Description 继续任务的日志
     * @Date 2020/7/25 9:54
     **/
    @Override
    public void addContinueTaskLog(AgvTask agvTask, Date sendTime, Date respTime, String sendFlag, String respData) {
        WcsCallAgvLog callAgvLog = new WcsCallAgvLog();
        callAgvLog.setInterfaceName("continueTask");
        callAgvLog.setReqCode(agvTask.getReqCode());
        callAgvLog.setTaskCode(Integer.parseInt(agvTask.getTaskCode()));
        callAgvLog.setPositionCode(JSON.toJSONString(agvTask.getNextPositionCode()));
        callAgvLog.setTaskType(agvTask.getTaskTyp());
        callAgvLog.setSendTime(sendTime);
        callAgvLog.setRespTime(respTime);
        callAgvLog.setReqFlag(sendFlag);
        callAgvLog.setRespData(respData);
        callAgvLogMapper.insertSelective(callAgvLog);
    }
    /**
     * @Description: 下发AGV指令查询
     * @Author: jzh
     * @Date: 2020/7/27
     */
    @Override
    public Pager<WcsCallAgvLog> findCallAgvLog(int page, int rows, WcsCallAgvLog condition) {
        Pager<WcsCallAgvLog> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
        List<WcsCallAgvLog> records = callAgvLogMapper.queryList(pager,condition);
        long size = callAgvLogMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }
    /**
     * @Description: AGV指令重发
     * @Author: jzh
     * @Date: 2020/7/28
     */ 
    @Override
    public Resp callAgvTask(WcsCallAgvLog wcsCallAgvLog, String loginName) {
        Resp resp = new Resp();
        //获取指令记录信息
        WcsCallAgvLog callAgvLog = callAgvLogMapper.selectByPrimaryKey(wcsCallAgvLog.getId());
        //不存在判断
        if (callAgvLog == null){
            resp.setCode("1");
            resp.setMsg("该指令查询不到");
            return resp;
        }
        //判断指令状态
        if (!"1".equals(callAgvLog.getReqFlag())){
            if ("重传成功".equals(callAgvLog.getUserDefined2())){
                resp.setCode("1");
                resp.setMsg("该指令已经重传成功");
                return resp;
            }
            //处理地址
            String positionCodeList = callAgvLog.getPositionCode();
            //拼接AGV生成任务单对象
            AgvTask agvTask = new AgvTask();
            String reqCode = UUID.randomUUID().toString().replace("-","");
            agvTask.setReqCode(reqCode);
            agvTask.setTaskCode(callAgvLog.getTaskCode().toString());
            agvTask.setTaskTyp(callAgvLog.getTaskType());
//            //手工AGV记录
//            WcsManualAgvTask wcsManualAgvTask = new WcsManualAgvTask();
//            wcsManualAgvTask.setTaskNo(String.valueOf(callAgvLog.getTaskCode()));
//            wcsManualAgvTask.setAgvTaskType(callAgvLog.getTaskType());
//            wcsManualAgvTask.setActionType("2");//移动
//            wcsManualAgvTask.setCreateBy(loginName);
//            wcsManualAgvTask.setGmtCreate(new Date());
//            wcsManualAgvTask.setToAddressId(0);
//            wcsManualAgvTask.setFromAddressId(0);
            //判断指令类型（genAgvSchedulingTask 、 continueTask）
            if ("genAgvSchedulingTask".equals(callAgvLog.getInterfaceName())) {
                //生成任务单

                JSONArray jsonArray = JSONArray.parseArray(positionCodeList);
                List<AgvPositionItem> agvPositionItemList = new ArrayList<>();

                for (int i=0 ; i<jsonArray.size();i++){
                    JSONObject job = jsonArray.getJSONObject(i);
                    String positionCode = job.getString("positionCode");
                    String type = job.getString("type");
                    AgvPositionItem item = new AgvPositionItem();
                    item.setPositionCode(positionCode);
                    item.setType(type);
                    agvPositionItemList.add(item);
//                    if (i==0){
//                        wcsManualAgvTask.setFromAddressName(positionCode);
//                    }
//                    if (i==1){
//                        wcsManualAgvTask.setToAddressName(positionCode);
//                    }
                }
                //放置任务
                agvTask.setPositionCodePath(agvPositionItemList);
                //调用AGV任务下发接口
                Resp respAgv = agvOperationService.sendTask(agvTask);
                //2-1下发成功更新状态
                if ("1".equals(respAgv.getCode())){//调用接口成功
                    //更新日志表 调用次数 重传结果
                    int count = Integer.parseInt(callAgvLog.getUserDefined1())+1;
                    wcsCallAgvLog.setUserDefined1(String.valueOf(count));
                    wcsCallAgvLog.setUserDefined2("重传成功");
                    int callAgvNum = callAgvLogMapper.updateByPrimaryKeySelective(wcsCallAgvLog);
//                    //创建手工调用agv日志
//                    wcsManualAgvTask.setTaskStatus("2");//下发
//                    wcsManualAgvTaskMapper.insertBySelect(wcsManualAgvTask);

                // 2-2下发失败记录
                } else {
                    // 21下发失败
                    String msg = "";
                    if (respAgv.getMsg()!=null){
                        if (respAgv.getMsg().length()>50){
                            msg = respAgv.getMsg().substring(0,50);
                        }else {
                            msg = respAgv.getMsg();
                        }
                    }
                    //更新日志表 调用次数 重传结果
                    int count = Integer.parseInt(callAgvLog.getUserDefined1())+1;
                    wcsCallAgvLog.setUserDefined1(String.valueOf(count));
                    wcsCallAgvLog.setUserDefined2("重传失败："+msg);
                    int callAgvNum = callAgvLogMapper.updateByPrimaryKeySelective(wcsCallAgvLog);
//                    //创建手工调用agv日志
//                    wcsManualAgvTask.setTaskStatus("21");//下发失败
//                    wcsManualAgvTaskMapper.insertBySelect(wcsManualAgvTask);
                    resp.setCode("1");
                    resp.setMsg("重传失败："+msg);
                    return resp;
                }

            }else if ("continueTask".equals(callAgvLog.getInterfaceName())){

                JSONObject jsonObject = JSONObject.parseObject(positionCodeList);
                String positionCode = jsonObject.getString("positionCode");
                AgvPositionItem nextAddr = new AgvPositionItem();
                nextAddr.setPositionCode(positionCode);
                nextAddr.setType("00");
                agvTask.setNextPositionCode(nextAddr);
                Resp respContinue = agvOperationService.sendContinueTask(agvTask);
//                wcsManualAgvTask.setToAddressName(positionCode);
                if ("1".equals(respContinue.getCode())){//调用接口成功
                    //更新日志表 调用次数 重传结果
                    int count = Integer.parseInt(callAgvLog.getUserDefined1())+1;
                    wcsCallAgvLog.setUserDefined1(String.valueOf(count));
                    wcsCallAgvLog.setUserDefined2("重传成功");
                    int callAgvNum = callAgvLogMapper.updateByPrimaryKeySelective(wcsCallAgvLog);
//                    //创建手工调用agv日志
//
//                    wcsManualAgvTask.setTaskStatus("2");//下发
//
//                    wcsManualAgvTaskMapper.insertBySelect(wcsManualAgvTask);
                } else {
                    // 21下发失败
                    String msg = "";
                    if (respContinue.getMsg()!=null){
                        if (respContinue.getMsg().length()>50){
                            msg = respContinue.getMsg().substring(0,50);
                        }else {
                            msg = respContinue.getMsg();
                        }
                    }
                    //更新日志表 调用次数 重传结果
                    int count = Integer.parseInt(callAgvLog.getUserDefined1())+1;
                    wcsCallAgvLog.setUserDefined1(String.valueOf(count));
                    wcsCallAgvLog.setUserDefined2("重传失败："+msg);
                    int callAgvNum = callAgvLogMapper.updateByPrimaryKeySelective(wcsCallAgvLog);
//                    //创建手工调用agv日志
//                    wcsManualAgvTask.setTaskStatus("21");//下发失败
//                    wcsManualAgvTaskMapper.insertBySelect(wcsManualAgvTask);
                    resp.setCode("1");
                    resp.setMsg("重传失败："+msg);
                    return resp;
                }

            }else {
                resp.setCode("1");
                resp.setMsg("该指令未知任务类型");
                return resp;
            }

        }else {
            resp.setCode("1");
            resp.setMsg("该指令状态不可重发");
            return resp;
        }
        resp.setCode("0");
        resp.setMsg("重发成功");
        return resp;
    }

}
