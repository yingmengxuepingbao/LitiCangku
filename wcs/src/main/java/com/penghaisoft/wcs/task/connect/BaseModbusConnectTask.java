package com.penghaisoft.wcs.task.connect;

import com.penghaisoft.wcs.basicmanagement.model.business.IWcsDeviceService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsDevice;
import com.penghaisoft.wcs.jobmanagement.model.business.IJobManagementService;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import com.penghaisoft.wcs.operation.model.BaseModbusDevice;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

/**
 * @Description BaseModbusConnectTask
 * @Auther zhangxu
 * @Date 2020/3/3 14:59
 **/
@Slf4j
public abstract class BaseModbusConnectTask {

    ModbusFactory modbusFactory;

    @Autowired
    public IWcsDeviceService wcsDeviceService;

    @Autowired
    public IWcsErrorLogService wcsErrorLogService;

    @Autowired
    public IJobManagementService jobManagementService;


    public BaseModbusConnectTask(){
        log.info("========定时任务构造函数-初始化modbusFactory实例========");
        modbusFactory = new ModbusFactory();
    }

    public abstract List<WcsDevice> getDeviceList();

    /**
     * 初始化连接
     * 将初始状态保存到内存中
     */
    public void initConn(){
        // 从DB查询要连接上几个modbus设备
        List<WcsDevice> deviceList = getDeviceList();
        for (int i = 0; i < deviceList.size(); i++) {

            WcsDevice wcsDevice = deviceList.get(i);
            boolean isConnect = true;

            IpParameters params = new IpParameters();
//        params.isEncapsulated()
            params.setHost(wcsDevice.getIp());
//        设置端口，默认502
            params.setPort(wcsDevice.getPort());
            ModbusMaster tcpMaster = modbusFactory.createTcpMaster(params, true);
            try {
                tcpMaster.init();

            } catch (ModbusInitException e) {
                isConnect = false;
//                记录错误日志
                WcsErrorLog errorLog = new WcsErrorLog();
                errorLog.setDeviceId(wcsDevice.getDeviceId().shortValue());
                errorLog.setDeviceType(wcsDevice.getDeviceType());
//                连接异常
                errorLog.setErrType("3");
                short connectError = 10;
                errorLog.setErrCode(connectError);
//                严重
                errorLog.setLevel(Short.parseShort("2"));
                errorLog.setFaultSource(this.getClass().getSimpleName());
                errorLog.setGmtCreate(new Date());
                errorLog.setDescription("初始化连接失败："+e.getMessage());
                wcsErrorLogService.addLog(errorLog);
            }
            saveConstantInfo(wcsDevice,tcpMaster,isConnect);
        }

    }

    /**
     * 将连接保存在对象中--连接类初始化时候调用一次
     * @param wcsDevice
     * @param tcpMaster
     * @param isConnect
     */
    public abstract void saveConstantInfo(WcsDevice wcsDevice,ModbusMaster tcpMaster,boolean isConnect);

    /**
     * 尝试重连
     * @param modbusDevice
     */
    public void reconnect(BaseModbusDevice modbusDevice) throws ModbusInitException {
        log.info("开始尝试重连 "+modbusDevice.getRetries());
        modbusDevice.getConnection().init();
//      在这里不用更新DB状态为可用，没什么意义，页面显示设备状态直接读内存就行了
        modbusDevice.setConnect(true);
        modbusDevice.setRetries(0);
    }

}
