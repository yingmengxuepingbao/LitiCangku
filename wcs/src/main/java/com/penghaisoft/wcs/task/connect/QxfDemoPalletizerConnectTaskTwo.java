package com.penghaisoft.wcs.task.connect;

import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsLocationRealService;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsPathService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsDevice;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.modbus.ModbusUtil;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import com.penghaisoft.wcs.operation.constant.DeviceConstant;
import com.penghaisoft.wcs.operation.model.*;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import com.penghaisoft.wcs.operation.service.BindingService;
import com.penghaisoft.wcs.operation.service.PalletizingOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskDispatchService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskSplitService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
* @Description 巧媳妇的前期功能，不是正式的
* @Date 2020/7/3 17:42
* @param
* @return
**/
@Slf4j
@ConditionalOnProperty(prefix = "jobs.open",name = "early-palletizer",havingValue = "true")
@Component
public class QxfDemoPalletizerConnectTaskTwo extends BaseModbusConnectTask implements InitializingBean{

    @Autowired
    private AgvOperationService agvOperationService;

    @Autowired
    private BindingService bindingService;

    @Autowired
    private ITaskSplitService taskSplitService;

    @Autowired
    private IWcsLocationRealService wcsLocationRealService;

    @Autowired
    private IWcsCommonService wcsCommonService;

    @Autowired
    private IWcsPathService wcsPathService;

    @Autowired
    private ITaskDispatchService taskDispatchService;

    @Value("${wcs.config.agv-task-typ}")
    private String agvTaskTyp;

    @Value("${wcs.config.agv-task-typ-pallet}")
    private String agvPalletTaskTyp;

    @Autowired
    private PalletizingOperationService palletizingOperationService;



    /**
     * 保存错误日志
     * @param stackerId
     * @param msg
     */
    private void saveErrorLog(Integer stackerId,String msg){

        WcsErrorLog errorLog = new WcsErrorLog();
        errorLog.setDeviceId(stackerId.shortValue());
//        码垛机
        errorLog.setDeviceType("1");
//                连接异常
        errorLog.setErrType("3");
        short connectError = 10;
        errorLog.setErrCode(connectError);
//                严重
        errorLog.setLevel(Short.parseShort("2"));
        errorLog.setFaultSource(this.getClass().getSimpleName());
        errorLog.setGmtCreate(new Date());
        errorLog.setDescription(msg);
        wcsErrorLogService.addLog(errorLog);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        1码垛机 2堆垛机 3RGV 4AGV 5四向穿梭车 6线体
        log.info("构建码垛机信息");
        initConn();

    }

    @Scheduled(cron = "${jobs.palletizer-connect.cron}")
    public void scheduled(){
        boolean success = true;
        Date startTime = new Date();

        String operator = this.getClass().getSimpleName();

//            主控PLC就一个
        Palletizer palletizer = DeviceConstant.PALLETIZER;

        if (palletizer.isConnect()&&null!=palletizer.getConnection()){
//                一次读取所有数据
            try {
                List<WcsBindingInfo> waitBindingInfoList = readPalletizerConveyorInfo();
                if (waitBindingInfoList.size() == 0){
//                    log.info("本次没有需要绑定的数据");
                }else {
                    for (int i = 0; i < waitBindingInfoList.size(); i++) {
                        WcsBindingInfo bindInfo = waitBindingInfoList.get(i);
//                        todo 跳过某条线体的处理，为了9/10入库
                        if(bindInfo.getAddressId()==4){
                            continue;
                        }
                        if (bindInfo.getAddressId()==3){
                            if (palletizer.getHasPallet()==1){
//                                传入的实际上托盘编码=pallet
                                boolean flag = taskSplitService.hasEarlyCallPalletTask(bindInfo.getPalletCode());
                                if (!flag){
                                    if (palletizer.isConnect()) {
//                                        只有连接时下发任务
                                        Integer taskCode = wcsCommonService.getTaskNo(1)[0];
                                        AgvTask agvTask = genAgvPalletTaskModel(3,taskCode);
                                        //                            直接下发给AGV
                                        Resp resp = agvOperationService.sendTask(agvTask);
                                        if ("1".equals(resp.getCode())){
                                            taskSplitService.splitEarlyCallPalletTask(bindInfo.getPalletCode(),taskCode);
//                                        修改码垛机PLC 接受完成=1
                                            palletizingOperationService.setPalletReceiveFinish((short)1);
                                        }
                                    }else {
                                        log.info("下发码垛机传送标志连接异常");
                                    }
                                }

                            }
                        }else {
//                          生产线
                            boolean flag = bindingService.bindPalletIfNot(bindInfo);
                            if (flag){
//                                请求单号
                                Integer taskCode = wcsCommonService.getTaskNo(1)[0];
                                Integer lineId = bindInfo.getAddressId();
//                        S代表起始任务
                                log.info("下发AGV起始任务号={}",taskCode);
//                                AgvTask agvTask = genSendTaskModel(totalTaskNo,bindInfo.getAddressId(),startTaskNoStr,taskCode);
                                AgvTask agvTask = genAgvSendTaskModel(lineId,taskCode);
//                            记录目的地
                                String target = agvTask.getPositionCodePath().get(1).getPositionCode();
                                bindInfo.setUserDefined1(target);
//                            还是得拆分任务
//                                taskSplitService.splitDemoInTask(bindInfo,totalTaskNo,continueTaskNo,inTaskNo);
                                String palletCode = bindInfo.getPalletCode();
//                            直接下发给AGV
                                Resp resp = agvOperationService.sendTask(agvTask);
                                if ("1".equals(resp.getCode())){
                                    taskSplitService.splitEarlyInTask(palletCode,lineId,taskCode);
                                }
                            }
                        }
                    }
                }
            } catch (ModbusTransportException e) {
                e.printStackTrace();
                log.error("操作码垛机失败,{}",e.getMessage());
                success = false;
                palletizer.setConnect(false);
                saveErrorLog(palletizer.getId(),"操作码垛机失败："+e.getMessage());
            }
        }else {
            palletizer.addRetries();
//              如果连续5次还没连上初始化连接
            if (palletizer.getRetries()%5==0){
                try {
                    reconnect(palletizer);
                } catch (ModbusInitException e) {
                    log.error("重连失败：{}",e.getMessage());
                    success = false;
                    palletizer.setConnect(false);
//                重连失败
                    saveErrorLog(palletizer.getId(),"重连失败："+e.getMessage());
                }
            }
        }

        Date endTime = new Date();
//        记录wcs_job_execution_log与wcs_job_execution_summary
        if (success){
            jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"2",startTime,endTime,
                    null,2);
        }else {
//            当抓到异常才算失败
            jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(),null,"3",startTime,endTime,
                    "连接失败",2);
        }
    }

    /**
     * 生产AGV下发任务
     * @param lineId
     * @param taskCode
     * @return
     */
    private AgvTask genAgvSendTaskModel(Integer lineId, Integer taskCode) {
        String reqCode = UUID.randomUUID().toString().replace("-","");
        AgvTask agvTask = new AgvTask();
        agvTask.setReqCode(reqCode);
        agvTask.setTaskCode(taskCode.toString());
        agvTask.setTaskTyp(agvTaskTyp);
        List<AgvPositionItem> agvPositionItemList = new ArrayList<>();
        AgvPositionItem item = new AgvPositionItem();
        item.setPositionCode("m"+lineId);
        item.setType("00");
        agvPositionItemList.add(item);

        if(DeviceConstant.PALLETIZER.getH1AllowPallet()==1){
            item = new AgvPositionItem();
            item.setPositionCode("X1");
            item.setType("00");
            agvPositionItemList.add(item);
        }else if(DeviceConstant.PALLETIZER.getH2AllowPallet()==1){
            item = new AgvPositionItem();
            item.setPositionCode("X2");
            item.setType("00");
            agvPositionItemList.add(item);
        }else {
//        H0和H1是两个平库地点，轮询
            Double random = Math.random()*100;
            Integer which = random.intValue()%2+1;

            item = new AgvPositionItem();
            item.setPositionCode("X"+which);
            item.setType("00");
            agvPositionItemList.add(item);
//            都有货的话轮训
            log.info("H1，H2都有货，随机一个地址{}","H"+which);
        }

        agvTask.setPositionCodePath(agvPositionItemList);
        return agvTask;
    }

    /**
    * @Description 生成叫托盘的任务
    * @Date 2020/7/20 20:14
    * @param lineId, taskCode
    * @return com.penghaisoft.wcs.operation.model.AgvTask
    **/
    private AgvTask genAgvPalletTaskModel(Integer lineId, Integer taskCode) {
        String reqCode = UUID.randomUUID().toString().replace("-","");
        AgvTask agvTask = new AgvTask();
        agvTask.setReqCode(reqCode);
        agvTask.setTaskCode(taskCode.toString());
        agvTask.setTaskTyp(agvPalletTaskTyp);
//        从E点到m3
        List<AgvPositionItem> agvPositionItemList = new ArrayList<>();
        AgvPositionItem item = new AgvPositionItem();
        item.setPositionCode("E");
        item.setType("00");
        agvPositionItemList.add(item);

        item = new AgvPositionItem();
        item.setPositionCode("m"+lineId);
        item.setType("00");
        agvPositionItemList.add(item);


        agvTask.setPositionCodePath(agvPositionItemList);
        return agvTask;
    }


    /**
     * 读取码垛线状态，决定是否需要绑定
     * @return
     */
    private List<WcsBindingInfo> readPalletizerConveyorInfo() throws ModbusTransportException {
//        等待绑定的数据
        List<WcsBindingInfo> waitBindInfoList = new ArrayList<>();

        ModbusMaster connection = DeviceConstant.PALLETIZER.getConnection();
        short[] result = ModbusUtil.readHoldingRegister(connection,1,1,120);
        int channelCount = 5;
        for (int i = 0; i < channelCount; i++) {
            boolean isPallet = false;
            if (i == 2){
                isPallet = true;
            }
            PalletizerInfo palletizerInfo = new PalletizerInfo();
            int start = i * 23;
//            log.info("解析{}号通道数据",i+1);
            short reqTrans = result[start];
//            log.info("请求传送={}",reqTrans);
            palletizerInfo.setReqTrans(reqTrans);
            short recFinish = result[start+1];
            palletizerInfo.setRecFinish(recFinish);

            String palletCode = "pallet";
            //                解析商品信息
            StringBuffer goodsCodeBuf = new StringBuffer();
            for (int j = 0; j < 8; j++) {
                char[] goodsCodeChar = ModbusUtil.short2char(result[j+start+2]);
                if (goodsCodeChar[0]!=Character.MIN_VALUE){
                    goodsCodeBuf.append(goodsCodeChar[0]);
                }
                if (goodsCodeChar[1]!=Character.MIN_VALUE){
                    goodsCodeBuf.append(goodsCodeChar[1]);
                }
            }
            String goodsCode = goodsCodeBuf.toString();
//              批次信息
            StringBuffer batchNoBuf = new StringBuffer();
            for (int j = 0; j < 8; j++) {
                char[] batchNoBufChar = ModbusUtil.short2char(result[j+start+10]);
                if (batchNoBufChar[0]!=Character.MIN_VALUE){
                    batchNoBuf.append(batchNoBufChar[0]);
                }
                if (batchNoBufChar[1]!=Character.MIN_VALUE){
                    batchNoBuf.append(batchNoBufChar[1]);
                }
            }
            String batchNo = batchNoBuf.toString();

//                托盘号
            StringBuffer palletCodeBuf = new StringBuffer();
            for (int j = 0; j < 4; j++) {
                char[] palletCodeBufChar = ModbusUtil.short2char(result[j+start+18]);
                if (palletCodeBufChar[0]!=Character.MIN_VALUE){
                    palletCodeBuf.append(palletCodeBufChar[0]);
                }
                if (palletCodeBufChar[1]!=Character.MIN_VALUE){
                    palletCodeBuf.append(palletCodeBufChar[1]);
                }
            }
            short amount = result[start+22];
            Short channelId = (short)(i+1);
            if (!isPallet){
//                设置为实验性质
                palletCode = "TT"+palletCodeBuf.toString()+channelId.toString();
//            控制台输出的是空格，\u0000 表示的是Unicode值
//                palletCode = palletCode.replace("\u0000","");
            }
            palletizerInfo.setPalletCode(palletCode);
            DeviceConstant.PALLETIZER.getPalletizerInfoMap().put(channelId,palletizerInfo);
//           码垛机请求传送，这时候进行绑定
            if (reqTrans==1){
//                log.info("托盘号={}",palletCode);
                WcsBindingInfo bindingInfo = new WcsBindingInfo();
                bindingInfo.setPalletCode(palletCode);
                bindingInfo.setGoodsCode(goodsCode);
                bindingInfo.setBatchNo(batchNo);
                bindingInfo.setAmount((int) amount);
                bindingInfo.setAddressId(channelId.intValue());
                bindingInfo.setCreateBy("码垛连接任务");
                bindingInfo.setGmtCreate(new Date());
                waitBindInfoList.add(bindingInfo);
            }
        }

        Short h1 = result[115];
        DeviceConstant.PALLETIZER.setH1AllowPallet(h1);
        Short h2 = result[117];
        DeviceConstant.PALLETIZER.setH2AllowPallet(h2);
        Short hasPallet = result[119];
        DeviceConstant.PALLETIZER.setHasPallet(hasPallet);
//        5号口优先
        Collections.sort(waitBindInfoList,new Comparator<WcsBindingInfo>(){
            @Override
            public int compare(WcsBindingInfo o1, WcsBindingInfo o2) {
                return o2.getAddressId()-o1.getAddressId();
            }
        });
        return waitBindInfoList;
    }


    @Override
    public List<WcsDevice> getDeviceList() {
//        码垛机
        List<WcsDevice> palletizerList = wcsDeviceService.queryByDeviceType("1");
        if (palletizerList.size()==0){
            log.info("-- 系统中未维护码垛机设备 --");
        }
        return palletizerList;
    }

    /**
     * 将连接保存在对象中
     *
     * @param wcsDevice
     * @param tcpMaster
     * @param isConnect
     */
    @Override
    public void saveConstantInfo(WcsDevice wcsDevice, ModbusMaster tcpMaster, boolean isConnect) {
        DeviceConstant.PALLETIZER.setConnect(isConnect);
        DeviceConstant.PALLETIZER.setConnection(tcpMaster);
        Map<Short, PalletizerInfo> palletizerInfoMap = DeviceConstant.PALLETIZER.getPalletizerInfoMap();
//      一共5个通道
        for (Short channelId = 1; channelId <=5 ; channelId++) {
            palletizerInfoMap.put(channelId,new PalletizerInfo());
        }
    }

}
