package com.penghaisoft.wcs.operation.model;

import com.serotonin.modbus4j.ModbusMaster;
import lombok.Data;

/**
 * @Description BaseModbusDevice
 * @Auther zhangxu
 * @Date 2020/3/3 15:28
 **/
@Data
public class BaseModbusDevice {

    private boolean isConnect = false;

    private int id;

    private ModbusMaster connection;

    /**
     * 没连接上时的重试次数
     */
    private int retries;

    public void addRetries(){
        if (retries == Integer.MAX_VALUE-1){
            retries = 0;
        }
        this.retries++;
    }
}
