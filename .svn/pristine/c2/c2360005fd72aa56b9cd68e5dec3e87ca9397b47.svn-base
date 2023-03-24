package com.penghaisoft.wcs.modbus;

import com.alibaba.fastjson.JSON;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import com.penghaisoft.wcs.operation.constant.DeviceConstant;
import com.penghaisoft.wcs.operation.model.EarlyInLocOccupyInfo;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName ModbusTest
 * @Description 本地写的测试类
 * @Author zhangx
 * @Date 2020/5/29 11:04
 **/
@Slf4j
public class ModbusTest {

    private ModbusMaster createMaster(boolean keepAlive){
        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost("127.0.0.1");
        ipParameters.setPort(505);
        ModbusFactory modbusFactory = new ModbusFactory();
        ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, keepAlive);
        master.setTimeout(8000);
        master.setRetries(0);
        try {
            master.init();

        } catch (ModbusInitException e) {
            e.printStackTrace();
            log.error("连接失败");
        }
        return master;
    }

    private void testNotKeepAlive(ModbusMaster master){
        try {
//            一次性读取5个通道
            short[] result =  ModbusUtil.readHoldingRegister(master,1,1,120);
            int channelCount = 5;
            for (int i = 0; i < channelCount; i++) {
                int start = i * 23;
                log.info("解析{}号通道数据",i+1);
                short reqTrans = result[start];
                log.info("请求传送={}",reqTrans);
                short recFinish = result[start+1];
                log.info("接受完成={}",recFinish);
//                解析商品信息
                StringBuffer goodsCodeBuf = new StringBuffer();
                for (int j = 0; j < 8; j++) {
                    char[] goodsCodeChar = ModbusUtil.short2char(result[j+start+2]);
                    goodsCodeBuf.append(goodsCodeChar[0]);
                    goodsCodeBuf.append(goodsCodeChar[1]);
                }
//        取消空格
                String goodsCode = goodsCodeBuf.toString();
//                goodsCode = goodsCode.replace(" ","");
                log.info("商品编码={}",goodsCode);
//              批次信息
                StringBuffer batchNoBuf = new StringBuffer();
                for (int j = 0; j < 8; j++) {
                    char[] batchNoBufChar = ModbusUtil.short2char(result[j+start+10]);
                    batchNoBuf.append(batchNoBufChar[0]);
                    batchNoBuf.append(batchNoBufChar[1]);
                }
                String batchNo = batchNoBuf.toString();
//                batchNo = batchNo.replace(" ","");
                log.info("批次号={}",batchNo);

//                托盘号
                StringBuffer palletCodeBuf = new StringBuffer();
                for (int j = 0; j < 4; j++) {
                    char[] palletCodeBufChar = ModbusUtil.short2char(result[j+start+18]);
                    palletCodeBuf.append(palletCodeBufChar[0]);
                    palletCodeBuf.append(palletCodeBufChar[1]);
                }
                String palletCode = palletCodeBuf.toString();
//                palletCode = palletCode.replace(" ","");
                log.info("托盘号={}",palletCode);

                short amount = result[start+22];
                log.info("数量={}",amount);
            }
            Short h1 = result[115];
            log.info("h1允许放货={}",h1);
            Short h2 = result[117];
            log.info("h2允许放货={}",h2);
            Short hasPallet = result[119];
            log.info("有托盘={}",hasPallet);
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }




    /**
     * 巧媳妇数据读取
     */
    private void testQiaoxfPalletizerRead(){
        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost("10.1.1.190");
        ipParameters.setPort(502);
//        ipParameters.setHost("127.0.0.1");
//        ipParameters.setPort(505);
        ModbusFactory modbusFactory = new ModbusFactory();
        // ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, true);
        ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, false);
        master.setTimeout(8000);
        master.setRetries(0);
        try {
            master.init();

        } catch (ModbusInitException e) {
            e.printStackTrace();
            log.error("连接失败");
        }
        try {
//            一次性读取5个通道
            short[] result =  ModbusUtil.readHoldingRegister(master,1,1,120);
            int channelCount = 5;
            for (int i = 0; i < channelCount; i++) {
                int start = i * 23;
                log.info("解析{}号通道数据",i+1);
                short reqTrans = result[start];
                log.info("请求传送={}",reqTrans);
                short recFinish = result[start+1];
                log.info("接受完成={}",recFinish);
//                解析商品信息
                StringBuffer goodsCodeBuf = new StringBuffer();
                for (int j = 0; j < 8; j++) {
                    char[] goodsCodeChar = ModbusUtil.short2char(result[j+start+2]);
                    goodsCodeBuf.append(goodsCodeChar[0]);
                    goodsCodeBuf.append(goodsCodeChar[1]);
                }
//        取消空格
                String goodsCode = goodsCodeBuf.toString();
//                goodsCode = goodsCode.replace(" ","");
                log.info("商品编码={}",goodsCode);
//              批次信息
                StringBuffer batchNoBuf = new StringBuffer();
                for (int j = 0; j < 8; j++) {
                    char[] batchNoBufChar = ModbusUtil.short2char(result[j+start+10]);
                    batchNoBuf.append(batchNoBufChar[0]);
                    batchNoBuf.append(batchNoBufChar[1]);
                }
                String batchNo = batchNoBuf.toString();
//                batchNo = batchNo.replace(" ","");
                log.info("批次号={}",batchNo);

//                托盘号
                StringBuffer palletCodeBuf = new StringBuffer();
                for (int j = 0; j < 4; j++) {
                    char[] palletCodeBufChar = ModbusUtil.short2char(result[j+start+18]);
                    palletCodeBuf.append(palletCodeBufChar[0]);
                    palletCodeBuf.append(palletCodeBufChar[1]);
                }
                String palletCode = palletCodeBuf.toString();
//                palletCode = palletCode.replace(" ","");
                log.info("托盘号={}",palletCode);

                short amount = result[start+22];
                log.info("数量={}",amount);
            }
            Short h1 = result[115];
            log.info("h1允许放货={}",h1);
            Short h2 = result[117];
            log.info("h2允许放货={}",h2);
            Short hasPallet = result[119];
            log.info("有托盘={}",hasPallet);
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    private void testQiaoxfPalletizerReadEarlyOccupyInfo(){
        IpParameters ipParameters = new IpParameters();
//        ipParameters.setHost("10.1.1.190");
//        ipParameters.setPort(502);
        ipParameters.setHost("127.0.0.1");
        ipParameters.setPort(505);
        ModbusFactory modbusFactory = new ModbusFactory();
        // ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, true);
        ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, false);
        master.setTimeout(8000);
        master.setRetries(0);
        try {
            master.init();

        } catch (ModbusInitException e) {
            e.printStackTrace();
            log.error("连接失败");
        }
        try {
            short[] data = ModbusUtil.readHoldingRegister(master,1,116,4);
            EarlyInLocOccupyInfo result = new EarlyInLocOccupyInfo();
            result.setH1AllowPallet( data[0]==1?true:false);
            result.setH1HasJob(data[1]==1?true:false);
            result.setH2AllowPallet( data[2]==1?true:false);
            result.setH2HasJob(data[3]==1?true:false);
            log.info(JSON.toJSONString(result));
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    private void testQxfPalletizerWrite(Integer lineId){
        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost("10.1.1.190");
        ipParameters.setPort(502);
        ModbusFactory modbusFactory = new ModbusFactory();
        // ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, true);
        ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, false);
        master.setTimeout(8000);
        master.setRetries(0);
        try {
            master.init();

        } catch (ModbusInitException e) {
            e.printStackTrace();
            log.error("连接失败");
        }

        int offset = (lineId-1)*23 + 2;
//        下发指令时候改成1
        short data = 1;

        try {
//            ModbusUtil.writeRegisters(master,1,0,data);
            ModbusUtil.writeHoldingRegister(master,0,offset,data, DataType.TWO_BYTE_INT_UNSIGNED);
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } /*catch (ModbusInitException e) {
            e.printStackTrace();
        }*/
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testQxfPalletizerWrite2(String target){
        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost("10.1.1.190");
        ipParameters.setPort(502);
        ModbusFactory modbusFactory = new ModbusFactory();
        // ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, true);
        ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, false);
        master.setTimeout(8000);
        master.setRetries(0);
        try {
            master.init();

        } catch (ModbusInitException e) {
            e.printStackTrace();
            log.error("连接失败");
        }
        int offset = 0;
        if ("H1".equals(target)){
            offset = 117;
        }else if("H2".equals(target)){
            offset = 119;
        }
//        下发指令时候改成1
        short data = 1;

        try {
//            ModbusUtil.writeRegisters(master,1,0,data);
            ModbusUtil.writeHoldingRegister(master,0,offset,data, DataType.TWO_BYTE_INT_UNSIGNED);
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } /*catch (ModbusInitException e) {
            e.printStackTrace();
        }*/
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ModbusTest test = new ModbusTest();
//        log.info("测试宝博堆垛机读取数据");
//        test.testBaoboStackerRead();

//        log.info("测试宝博堆垛机下发命令");
//        test.testBaoboStackerWrite2();
//        test.testQxfPalletizerWrite2("H1");

//        ModbusMaster master = test.createMaster(false);
//        test.testNotKeepAlive(master);
//        System.out.println("---------------------");
//        test.testNotKeepAlive(master);
//        System.out.println("---------------------");
//        test.testNotKeepAlive(master);
        test.testQiaoxfPalletizerReadEarlyOccupyInfo();
    }
}
