package com.penghaisoft.wcs.operation.service.impl;

import com.alibaba.fastjson.JSON;
import com.penghaisoft.wcs.modbus.ModbusUtil;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.operation.constant.DeviceConstant;
import com.penghaisoft.wcs.operation.model.EarlyInLocOccupyInfo;
import com.penghaisoft.wcs.operation.model.Palletizer;
import com.penghaisoft.wcs.operation.service.PalletizingOperationService;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 码垛机器人
 * 按道理不该wcs 控制 我们只是读数存数
 * @Description PalletizingOperationServiceImpl
 * @Auther zhangxu
 * @Date 2020/3/3 13:39
 **/
@Slf4j
@Service
public class PalletizingOperationServiceImpl implements PalletizingOperationService {

    @Autowired
    IWcsErrorLogService wcsErrorLogService;

    @Override
    public void setPalletReceiveFinish(Short status) {
        Integer lineId = 3;
        Palletizer palletizer = DeviceConstant.PALLETIZER;
        log.info("======修改码垛机【接受完成】=====，线体id={}",lineId);
        if (palletizer.isConnect()) {
            //记录错误日志
            short connectError = 10;
//                致命
            short level = 3;
            String faultSource = "下发码垛机指令异常";

//            ModbusUtil.writeHoldingRegister();
            ModbusMaster modbusMaster = palletizer.getConnection();
            int offset = (lineId-1)*23 + 2;
//        任务启动后，改为1；AGV放上托盘后，改为2
            short data = status;
            try {
//                给指定的线体置1
                ModbusUtil.writeHoldingRegister(modbusMaster,0,offset,data, DataType.TWO_BYTE_INT_UNSIGNED);
            } catch (ModbusTransportException e) {
                log.error("下发码垛机接受完成指令异常：{}",e.getMessage());
                e.printStackTrace();
                //记录错误日志
                wcsErrorLogService.addPalletizingLog((short)palletizer.getId(),"3",connectError,level,
                        faultSource, lineId.toString(),e.getMessage());
                palletizer.setConnect(false);
            }catch (ErrorResponseException e) {
                log.error("下发码垛机接受完成指令异常：{}",e.getMessage());
                e.printStackTrace();
                //记录错误日志
                wcsErrorLogService.addPalletizingLog((short)palletizer.getId(),"3",connectError,level,
                        faultSource,lineId.toString(),e.getMessage());
                palletizer.setConnect(false);
            }
        } else {
            log.info("下发码垛机接受完成指令时，发现码垛机未连接");
        }
    }

    /**
     * @param lineId
     * @return void
     * @Description 当agv取完货物以后，将相应的码垛线请求完成位置1
     * @Date 2020/7/6 10:11
     **/
    @Override
    public boolean setReceiveFinish(Integer lineId) {
        Palletizer palletizer = DeviceConstant.PALLETIZER;
        log.info("======修改码垛机【接受完成】=====，线体id={}",lineId);
        boolean flag = true;
        if (palletizer.isConnect()) {
            //记录错误日志
            short connectError = 10;
//                致命
            short level = 3;
            String faultSource = "下发码垛机指令异常";

//            ModbusUtil.writeHoldingRegister();
            ModbusMaster modbusMaster = palletizer.getConnection();
            int offset = (lineId-1)*23 + 2;
//        下发指令时候改成1
            short data = 1;
            try {
//                给指定的线体置1
                ModbusUtil.writeHoldingRegister(modbusMaster,0,offset,data, DataType.TWO_BYTE_INT_UNSIGNED);
            } catch (ModbusTransportException e) {
                log.error("下发码垛机接受完成指令异常：{}",e.getMessage());
                e.printStackTrace();
                //记录错误日志
                wcsErrorLogService.addPalletizingLog((short)palletizer.getId(),"3",connectError,level,
                        faultSource, lineId.toString(),e.getMessage());
                palletizer.setConnect(false);
                return false;
            }catch (ErrorResponseException e) {
                log.error("下发码垛机接受完成指令异常：{}",e.getMessage());
                e.printStackTrace();
                //记录错误日志
                wcsErrorLogService.addPalletizingLog((short)palletizer.getId(),"3",connectError,level,
                        faultSource,lineId.toString(),e.getMessage());
                palletizer.setConnect(false);
                return false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    @Override
    public void setInTask(String target) {
        Palletizer palletizer = DeviceConstant.PALLETIZER;
        log.info("======修改码垛机【任务下达】=====，入库口={}",target);
        if (palletizer.isConnect()) {
            //记录错误日志
            short connectError = 10;
//                致命
            short level = 3;
            String faultSource = "修改码垛机 任务下达异常";

            ModbusMaster modbusMaster = palletizer.getConnection();
            int offset = 0;
            if ("H1".equals(target)){
                offset = 117;
            }else if("H2".equals(target)){
                offset = 119;
            }
//        下发指令时候改成1
            short data = 1;
            try {
//                给指定的线体置1
                ModbusUtil.writeHoldingRegister(modbusMaster,0,offset,data, DataType.TWO_BYTE_INT_UNSIGNED);
            } catch (ModbusTransportException e) {
                log.error("下发码垛机入库口占位指令异常：{}",e.getMessage());
                e.printStackTrace();
                //记录错误日志
                wcsErrorLogService.addPalletizingLog((short)palletizer.getId(),"3",connectError,level,
                        faultSource, target,e.getMessage());
                palletizer.setConnect(false);
            }catch (ErrorResponseException e) {
                log.error("下发码垛机入库口占位指令异常：{}",e.getMessage());
                e.printStackTrace();
                //记录错误日志
                wcsErrorLogService.addPalletizingLog((short)palletizer.getId(),"3",connectError,level,
                        faultSource,target,e.getMessage());
                palletizer.setConnect(false);
            }
        }

    }

    /**
     *
    * @Description 读取前期入库时候入库口占用信息
    * @Date 2020/8/6 14:08
    * @return com.penghaisoft.wcs.operation.model.EarlyInLocOccupyInfo
    **/
    @Override
    public EarlyInLocOccupyInfo getEarlyInLocOccupyInfo() {
        Palletizer palletizer = DeviceConstant.PALLETIZER;
        if (palletizer.isConnect()) {
            //记录错误日志
            short connectError = 10;
//                致命
            short level = 3;
            String faultSource = "读取前期入库时候入库口占用信息异常";

            ModbusMaster modbusMaster = palletizer.getConnection();
            int start = 116;
            try {
//                给指定的线体置1
                short[] data = ModbusUtil.readHoldingRegister(modbusMaster,1,start,4);
                EarlyInLocOccupyInfo result = new EarlyInLocOccupyInfo();
                result.setH1AllowPallet( data[0]==1?true:false);
                result.setH1HasJob(data[1]==1?true:false);
                result.setH2AllowPallet( data[2]==1?true:false);
                result.setH2HasJob(data[3]==1?true:false);
                return result;
            } catch (ModbusTransportException e) {
                log.error("下发码垛机入库口占位指令异常：{}",e.getMessage());
                e.printStackTrace();
                //记录错误日志
                wcsErrorLogService.addPalletizingLog((short)palletizer.getId(),"3",connectError,level,
                        faultSource, "read 占位信息",e.getMessage());
                palletizer.setConnect(false);
                return null;
            }
        }else {
            log.info("读取前期入库-入库口占用信息时 发现码垛机未连接！");
        }

        return null;

    }
}
