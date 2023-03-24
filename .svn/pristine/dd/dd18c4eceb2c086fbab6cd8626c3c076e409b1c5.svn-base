package com.penghaisoft.wcs.task.connect;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsLocationRealService;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsPathService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsDevice;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsPath;
import com.penghaisoft.wcs.common.service.IWcsCommonService;
import com.penghaisoft.wcs.expose.dto.PalletInDto;
import com.penghaisoft.wcs.modbus.ModbusUtil;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import com.penghaisoft.wcs.operation.constant.DeviceConstant;
import com.penghaisoft.wcs.operation.model.*;
import com.penghaisoft.wcs.operation.service.BindingService;
import com.penghaisoft.wcs.operation.service.CallWmsService;
import com.penghaisoft.wcs.operation.service.PalletizingOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.ITaskSplitService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;

/**
* @Description 码垛机连接类
 * 需要处理下线绑定1,2,4,5以及叫托盘3
* @Date 2020/7/17 9:12
**/
@Slf4j
@ConditionalOnProperty(prefix = "jobs.open",name = "palletizer",havingValue = "true")
@Component
public class PalletizerConnectTask extends BaseModbusConnectTask implements InitializingBean{

    @Autowired
    private CallWmsService callWmsService;

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
    private PalletizingOperationService palletizingOperationService;

    @Autowired
    private IBaseDictItemService baseDictItemService;


    /**
     * 根据入库口转换读取码垛线PLC的传送链（一个PLC存了所有的堆垛机信息）
     * @param addressIn
     * @return
     */
    private int transProductAddressInOffset(Short addressIn){
        int readPlcStart = -1;
//        todo 根据码垛线地址编号计算读取PLC时候的偏移量
        if (addressIn==1){
            readPlcStart = 0;
        }else if(addressIn==2){
            readPlcStart = 22;
        }
        return readPlcStart;
    }

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

//        log.info("与码垛机连接-保持状态");
//            主控PLC就一个
        Palletizer palletizer = DeviceConstant.PALLETIZER;

        if (palletizer.isConnect()&&null!=palletizer.getConnection()){
//                一次读取所有数据
            try {
//                这时候拿到的已经是请求传送的数据了
                List<WcsBindingInfo> waitBindingInfoList = readPalletizerConveyorInfo();
                if (waitBindingInfoList.size() == 0){
//                    log.info("本次没有需要绑定/叫托盘的数据");
                }else {
//                    只处理接管线体
                    List<BaseDictItem> dictItems = baseDictItemService.getDictTypeByCode("active_product_line");
                    Set<Integer> activeLine = new HashSet<>();
                    for (BaseDictItem item:dictItems) {
                        String lineId = item.getDicItemCode();
                        activeLine.add(Integer.parseInt(lineId));
                    }
//                    1,2,4,5通道的等待绑定
                    List<WcsBindingInfo> willBindingList = new ArrayList<>();
                    for (WcsBindingInfo bindInfo:waitBindingInfoList) {
                        Integer tmpLine = bindInfo.getAddressId();
                        if (activeLine.contains(bindInfo.getAddressId())){
                            if (bindInfo.getAddressId()==3){
                                handleCallPallet(bindInfo,operator);
                            }else {
                                willBindingList.add(bindInfo);
                            }
                        }else {
                            log.info("{}线体略过",tmpLine);
                        }
                    }
//                    处理下线入库
                    handleProductIn(willBindingList,operator);
                }
            } catch (ModbusTransportException e) {
                success = false;
                palletizer.setConnect(false);
                saveErrorLog(palletizer.getId(),"操作码垛机失败："+e.getMessage());
            }
        }else {
            palletizer.addRetries();
//              如果连续10次还没连上初始化连接
            if (palletizer.getRetries()%10==0){
                try {
                    reconnect(palletizer);
                } catch (ModbusInitException e) {
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
     * 处理叫托盘
     * @param bindInfo
     */
    private synchronized void handleCallPallet(WcsBindingInfo bindInfo,String operator) {
//        从3001 走到3
        Resp pathResult = wcsPathService.isPathLegal(3001,3);
        if ("0".equals(pathResult.getCode())){
            log.info(pathResult.getMsg());
            return;
        }
        WcsPath wcsPath = (WcsPath) pathResult.getData();
        Integer pathId = wcsPath.getPathId();
//        校验当前系统是否有创建或者进行中的叫托盘任务
        WcsTask callPalletWcsTask = taskSplitService.getCreateCallPalletTask();
        WcsTask callPalletIngWcsTask = taskSplitService.getDoingCallPalletTask();
        if (null == callPalletWcsTask && null == callPalletIngWcsTask){
//            可以生成叫托盘任务
//            1 请求出库库位
            Resp recommendResp = callWmsService.recommendVirtualPalletOutLocation();
            if (null!=recommendResp){
                JSONObject recommendData = (JSONObject)recommendResp.getData();
                Integer locationCode = recommendData.getInteger("locationCode");
                String palletCode = recommendData.getString("palletCode");
                Long taskId = recommendData.getLong("taskId");
//            生成叫托盘任务-四向车+AGV。
                taskSplitService.splitCallPalletTask(taskId,operator,locationCode,palletCode,pathId);
//              修改码垛机PLC 接受完成=1
                palletizingOperationService.setPalletReceiveFinish((short)1);
                log.info("生成叫托盘任务{}",taskId);
            }
        }else {
            if (null!=callPalletWcsTask){

                Integer befRecommendLoc = callPalletWcsTask.getFromAddress();
//                log.info("已经创建的叫托盘任务，推荐库位={},本次不拆分任务",befRecommendLoc);
            }
            if (null!=callPalletIngWcsTask){

                Integer befRecommendLoc = callPalletIngWcsTask.getFromAddress();
//                log.info("已经有进行中的叫托盘任务，推荐库位={},本次不拆分任务",befRecommendLoc);
            }
        }

    }

    /**
     * 处理码垛线-下线入库
     * @param willBindingList
     */
    private void handleProductIn(List<WcsBindingInfo> willBindingList,String operator){
        for (WcsBindingInfo bindInfo:willBindingList) {
            log.info("处理下线托盘{}",bindInfo.getPalletCode());

            //                        有查询，没有则绑定
            WcsBindingInfo havaBindInfo = bindingService.bindPallet(bindInfo);
//                        是否上传wms 1是 0 否 2失败
            String reportWms = havaBindInfo.getReportWms();
//                        未上传或者上传失败的重新做
            if ("0".equals(reportWms)||"2".equals(reportWms)){
//                          调用wms将托盘绑定
                RecommendLocResp recommendLocResp = callWmsService.uploadBindingInfo(havaBindInfo,operator);
                if (null!=recommendLocResp){
//                            拆分任务-这里实际上没有推荐库位，只有任务号
//                    任务类型应该是  10
                    Long taskId = recommendLocResp.getTaskId();
                    splitInTaskWithoutLoc(bindInfo,taskId,operator);
//                                boolean recommendAndSplitSuccess = callWmsService.recommendAndSplitProductInTask(bindingInfo,palletizer.getId(),(int)productInAddress,operator);
                }
            }
        }
    }

    /**
     * 入库任务拆分
     * @param havaBindInfo
     * @param taskId
     * @param operator
     */
    private void splitInTaskWithoutLoc(WcsBindingInfo havaBindInfo,Long taskId,String operator){
        Integer channelId = havaBindInfo.getAddressId();
        //        校验任务有没有重复下发
        boolean isDuplicate = taskSplitService.isDuplicateTask(taskId);
        if (isDuplicate){
            log.info("任务{}重复！",taskId);
            return;
        }

//        根据库位转获取入库线
        Integer inBufferAddress = wcsCommonService.getInBufferAddressByLocation(0);

//      判断地址是否合法,从码垛线（绑定时获取）到立库入库口（写死） 理论上这不可能不合法
        Resp pathResult = wcsPathService.isPathLegal(channelId,inBufferAddress);
        if ("0".equals(pathResult.getCode())){
            log.info(pathResult.getMsg());
            return;
        }
        WcsPath wcsPath = (WcsPath) pathResult.getData();
        Integer pathId = wcsPath.getPathId();

//        拆分
        PalletInDto palletInDto = new PalletInDto();
        palletInDto.setTaskId(taskId);
        palletInDto.setPalletCode(havaBindInfo.getPalletCode());
        palletInDto.setFromAddress(channelId);
        palletInDto.setOperator(operator);
//        现在还没有目的地库位
        taskSplitService.splitInTask(inBufferAddress,pathId,palletInDto,null);
    }


    /**
     * 写入plc 商品托盘已经绑定
     * @param productInAddress
     * @param palletizer
     * @throws ModbusTransportException
     */
    private boolean writeFinishBind(Short productInAddress,Palletizer palletizer) throws ModbusTransportException, ErrorResponseException {
        int readPlcStart = transProductAddressInOffset(productInAddress);
        if (readPlcStart == -1){
            log.info("根据码垛线地址编号计算读取PLC时候的偏移量错误");
            return false;
        }
        ModbusMaster connection = palletizer.getConnection();
        ModbusUtil.writeHoldingRegister(connection,1,readPlcStart+21,1, DataType.TWO_BYTE_INT_UNSIGNED);
        return true;
    }

    /**
     * 读取码垛线状态，决定是否需要绑定以及叫托盘
     * @return
     */
    private List<WcsBindingInfo> readPalletizerConveyorInfo() throws ModbusTransportException {
//        等待绑定的数据
        List<WcsBindingInfo> waitBindInfoList = new ArrayList<>();

        ModbusMaster connection = DeviceConstant.PALLETIZER.getConnection();
        short[] result = ModbusUtil.readHoldingRegister(connection,1,1,115);
        int channelCount = 5;
        for (int i = 0; i < channelCount; i++) {
            PalletizerInfo palletizerInfo = new PalletizerInfo();
            int start = i * 23;
//            log.info("解析{}号通道数据",i+1);
            short reqTrans = result[start];
            palletizerInfo.setReqTrans(reqTrans);
            short recFinish = result[start+1];
            palletizerInfo.setRecFinish(recFinish);
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
//        取消空格
            String goodsCode = goodsCodeBuf.toString();
            if (goodsCode.length()>10){
                goodsCode = goodsCode.substring(0,10);
            }
            goodsCode = goodsCode.toUpperCase();
            palletizerInfo.setGoodsCode(goodsCode);
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
            batchNo = batchNo.toUpperCase();
            palletizerInfo.setBatchNo(batchNo);
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
            String palletCode = palletCodeBuf.toString();
//                palletCode = palletCode.replace(" ","");
            palletizerInfo.setPalletCode(palletCode);
            short amount = result[start+22];
            palletizerInfo.setAmount(amount);
            Short channelId = (short)(i+1);
            DeviceConstant.PALLETIZER.getPalletizerInfoMap().put(channelId,palletizerInfo);
//           码垛机请求传送，这时候进行绑定，不能是空托盘
            if (reqTrans==1 && amount>0){
                WcsBindingInfo bindingInfo = new WcsBindingInfo();
                bindingInfo.setPalletCode(palletCode);
                bindingInfo.setGoodsCode(goodsCode);
                bindingInfo.setBatchNo(batchNo);
                bindingInfo.setAmount((int)amount);
                bindingInfo.setAddressId(channelId.intValue());
                bindingInfo.setCreateBy("码垛连接任务");
                bindingInfo.setGmtCreate(new Date());
                waitBindInfoList.add(bindingInfo);
            }
        }

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
