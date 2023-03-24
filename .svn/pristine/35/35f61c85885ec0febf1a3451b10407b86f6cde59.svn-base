package com.penghaisoft.wcs.modbus;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.locator.NumericLocator;
import com.serotonin.modbus4j.msg.*;
import com.serotonin.modbus4j.sero.util.queue.ByteQueue;

import java.util.Arrays;

/**
 * @Description ModbusUtil
 * @Auther zhangxu
 * @Date 2019/12/6 16:46
 **/
public class ModbusUtil {

    public static Integer readHoldingRegisterInt(ModbusMaster modbusMaster, int deviceId, int start) throws ModbusTransportException, ErrorResponseException {
        Integer result = -1;
        long startT = System.currentTimeMillis();
        NumericLocator int1 = new NumericLocator(deviceId, RegisterRange.HOLDING_REGISTER, start, DataType.TWO_BYTE_INT_UNSIGNED);
        result = modbusMaster.getValue(int1).intValue();
        long endE = System.currentTimeMillis();
        System.out.println("消耗时间："+(endE-startT));
        return result;
    }

    /**
     *
     * 从寄存器区域读取数据
     * 用仿真软件发现至少要读取12个int 才行，要不全是0 bug???
     * 真实的硬件当时试着应该不是这样
     * @param deviceId 设备id
     * @param start 起始位置
     * @param readLenth 长度,每个返回值是一个int,是PLC里面2个偏移量，是模拟器里面的一个数
     */
    public static short[] readHoldingRegister(ModbusMaster modbusMaster, int deviceId, int start, int readLenth) throws ModbusTransportException {
        long startT = System.currentTimeMillis();
        short[] result = null;
            //功能码03   读取保持寄存器的值
        ModbusRequest modbusRequest = new ReadHoldingRegistersRequest(deviceId, start, readLenth);
//        设置读取最大长度默认125，似乎改了没大用
//        modbusMaster.setMaxReadRegisterCount(1000);
        ReadHoldingRegistersResponse modbusResponse = (ReadHoldingRegistersResponse)modbusMaster.send(modbusRequest);
//            这样可以直接返回数据
        if (modbusResponse.isException()){
            System.out.println("Exception response: message=" + modbusResponse.getExceptionMessage());
        }else{
//            System.out.println(Arrays.toString(modbusResponse.getShortData()));
            result = modbusResponse.getShortData();
        }
//        ByteQueue byteQueue = new ByteQueue(1024);
//        modbusResponse.write(byteQueue);
//        System.out.println("功能码:" + modbusRequest.getFunctionCode());
//        System.out.println("从站地址:" + modbusRequest.getSlaveId());
//        System.out.println("收到的响应信息大小:" + byteQueue.size());
//        System.out.println("收到的响应信息值:" + byteQueue);

        long endE = System.currentTimeMillis();
//        System.out.println("消耗时间："+(endE-startT));
        return result;
    }


    /**
     * 向modbus寄存区写入数据,单个
     * @param modbusMaster
     * @param slaveId
     * @param offset
     * @param value
     * @param dataType
     * @throws ModbusTransportException
     * @throws ErrorResponseException
     */
    public static void writeHoldingRegister(ModbusMaster modbusMaster,int slaveId, int offset, Number value, int dataType)
            throws ModbusTransportException, ErrorResponseException{
        // 类型
        BaseLocator<Number> locator = BaseLocator.holdingRegister(slaveId, offset, dataType);
        modbusMaster.setValue(locator, value);
    }

    /**
     * 向寄存区写入多个值
     * @param modbusMaster
     * @param slaveId
     * @param startOffset
     * @param sdata
     * @return
     * @throws ModbusTransportException
     * @throws ModbusInitException
     */
    public static boolean writeRegisters(ModbusMaster modbusMaster,int slaveId, int startOffset, short[] sdata)
            throws ModbusTransportException, ModbusInitException {
        // 创建请求对象
        WriteRegistersRequest request = new WriteRegistersRequest(slaveId, startOffset, sdata);
        // 发送请求并获取响应对象
        ModbusResponse response = modbusMaster.send(request);
        return true;
//        if (response.isException()) {
//            System.out.println(response.getExceptionMessage());
//            return false;
//        } else {
//            return true;
//        }
    }

    /**
     * 将两个short转换为一个数
     * @param h 高字节
     * @param l 低字节
     */
    public static int short2Int(short h,short l){
        String hStr = "";
//        10转16
        if (h < 0){
            int hi = Short.toUnsignedInt(h);
            hStr = Integer.toHexString(hi);
        }else {
            hStr = Integer.toHexString(h);
        }
        String lStr = "";
        if (l < 0){
            int li = Short.toUnsignedInt(l);
            lStr = Integer.toHexString(li);
        }else {
            lStr = Integer.toHexString(l);
        }
        while (lStr.length()<4){
            lStr = "0" + lStr;
        }
//        高低位合并
        String str = hStr + lStr;
//        转为10进制一个数
        int val = Integer.parseInt(str,16);
        return val;
    }

    /**
     * 一个int 转2个short
     * @param intVal
     * @return
     */
    public static short[] int2Short(Integer intVal){
        short[] result = new short[2];
//        转16进制
        String hexIntVal = Integer.toHexString(intVal);
//        高位
        String h4 = "";
//        低位
        String l4 = "";
        if (hexIntVal.length()<=4){
//            高位为0
            result[0] = 0;
//            只有低位
            l4 = hexIntVal;
        }else{
            int diff = 8 - hexIntVal.length();
            h4 = hexIntVal.substring(0,4-diff);
//            转换高位
            result[0] = Short.parseShort(h4,16);
            l4 = hexIntVal.substring(4-diff,hexIntVal.length());
        }

//        如果越界>32767，转为有符号数(补码，负数)
        result[1] = (short)Integer.parseInt(l4,16);
        return result;
    }

    /**
     * 读取的一个short 转为两个short,高低位
     * 读取出来的一个short实际是plc里面偏移量为1的2个数据
     * @param s
     * @return
     */
    public static short[] shortTo2short(short s){
        short[] shortArr = new short[2];
//        最长4个的6进制字符串
        String hexStr = Integer.toHexString(s);
        int len = hexStr.length();
//      截取前两位和后两位强转
        if (len <= 2){
            shortArr[0] = 0;
            shortArr[1] = Short.parseShort(hexStr);
        }/*else if(len == 3){
            String h = hexStr.substring(0,1);
            charArr[0] = (char)Short.parseShort(h);
            String l = hexStr.substring(1,len);
            charArr[1] = (char)Short.parseShort(l);
        }else if(len == 4){
            String h = hexStr.substring(0,2);
            charArr[0] = (char)Short.parseShort(h);
            String l = hexStr.substring(2,len);
            charArr[1] = (char)Short.parseShort(l);
        }*/else{
            int diff = len - 2;
            String h = hexStr.substring(0,diff);
            shortArr[0] = Short.parseShort(h,10);
            String l = hexStr.substring(diff,len);
            shortArr[1] = Short.parseShort(l,10);
        }

        return shortArr;
    }

    /**
     * 把一个short 转为两个字符
     *
     * @param s 10进制
     * @return
     */
    public static char[] short2char(short s){
        char[] charArr = new char[2];
//        最长4个的6进制字符串
        String hexStr = Integer.toHexString(s);
        int len = hexStr.length();
//      截取前两位和后两位强转
        if (len <= 2){
            charArr[0] = (char)0;
            charArr[1] = (char)Short.parseShort(hexStr,16);
        }else{
            int diff = len - 2;
            String h = hexStr.substring(0,diff);
            charArr[0] = (char)Short.parseShort(h,16);
            String l = hexStr.substring(diff,len);
            charArr[1] = (char)Short.parseShort(l,16);
        }

        return charArr;
    }

    /**
     * 两个byte 转short
     * @param h
     * @param l
     * @return
     */
    public static short twoBytes2Short(Byte h,Byte l){
        String hStr = dec2hexByte(h);
        String lStr = dec2hexByte(l);
//        高低位合并
        String str = hStr + lStr;
//        转为10进制一个数
        Integer val = Integer.parseInt(str,16);
        return val.shortValue();
    }

    /**
     * 4个byte 转为 int
     * @param a
     * @param b
     * @param c
     * @param d
     * @return
     */
    public static Integer fourBytes2Int(Byte a, Byte b, Byte c, Byte d) {
        String aStr = dec2hexByte(a);
        String bStr = dec2hexByte(b);
        String cStr = dec2hexByte(c);
        String dStr = dec2hexByte(d);
        String allStr = aStr + bStr + cStr + dStr;
        return Integer.parseInt(allStr,16);
    }

    /**
     * byte 的10转16
     * @param bt
     * @return
     */
    private static String dec2hexByte(Byte bt){
        String str = "";
//        无符号数转换
        if (bt < 0){
            int hi = Byte.toUnsignedInt(bt);
            str = Integer.toHexString(hi);
        }else if(bt==0) {
            str = "0";
        }else {
            str = Integer.toHexString(bt);
        }
        if (str.length()<2){
            str = "0"+str;
        }
        return str;
    }

    /**
     * short 数组转int数组
     * @param shortArr
     * @return
     */
    public static int[] transShortArr2IntArr(short[] shortArr){
        int[] intArr = new int[shortArr.length/2];
        for (int i = 0; i < shortArr.length; i++) {
            if (i%2==0){
                short h = shortArr[i];
                short l = shortArr[i+1];
                int locX = ModbusUtil.short2Int(h,l);
                intArr[i/2] = locX;
            }
        }
        return intArr;
    }


    public static void main(String[] args) {
//        short a = 12339;
//        ModbusUtil.short2char(a);
//        if (true){
//            return;
//        }
        IpParameters ipParameters = new IpParameters();
//        ipParameters.setHost("192.168.0.1");
        ipParameters.setHost("127.0.0.1");
        ipParameters.setPort(501);
        ModbusFactory modbusFactory = new ModbusFactory();
        // ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, true);
        ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, false);
        master.setTimeout(8000);
        master.setRetries(0);
        try {
            master.init();

//            写入单个数据
            try {
//                ModbusUtil.writeHoldingRegister(master,0,0,9, DataType.TWO_BYTE_INT_UNSIGNED);
//                short[] input = new short[]{
//                        9999,//手自动模式 int2
//                        2,//int2
//                        3,//int2
//                        5401,//uint1+uint2,这里放入了两个数
//                        5,//dint4 高八位
//                        1991//dint4 低八位
//                };

//                short[] input = ModbusUtil.int2Short(931700008);
//                ModbusUtil.writeRegisters(master,0,0,input);

                short result[] = ModbusUtil.readHoldingRegister(master,1,0,35);


                short test = result[32];

                byte[] testByte = short2byte(test);

                String shortBit = new String();
                for (byte x : testByte){
                    String testX = getBit(x);
                    shortBit = shortBit + testX;
                }
                System.out.println("shortBit"+shortBit);


                String hex = Integer.toBinaryString(115);
                Long testBit = Short.toUnsignedLong(test);
                StringBuffer goodsCodeBuf = new StringBuffer();
                for (int i = 0; i < 8; i++) {
                    char[] goodsCodeChar = ModbusUtil.short2char(result[i]);
                    goodsCodeBuf.append(goodsCodeChar[0]);
                    goodsCodeBuf.append(goodsCodeChar[1]);
                }
                System.out.println(goodsCodeBuf);

                StringBuffer batchNoBuf = new StringBuffer();
                for (int i = 8; i < 16; i++) {
                    char[] batchNoBufChar = ModbusUtil.short2char(result[i]);
                    batchNoBuf.append(batchNoBufChar[0]);
                    batchNoBuf.append(batchNoBufChar[1]);
                }
                System.out.println(batchNoBuf);
                StringBuffer palletCodeBuf = new StringBuffer();
                for (int i = 16; i < 19; i++) {
                    char[] palletCodeBufChar = ModbusUtil.short2char(result[i]);
                    palletCodeBuf.append(palletCodeBufChar[0]);
                    palletCodeBuf.append(palletCodeBufChar[1]);
                }
                System.out.println(palletCodeBuf);
            } catch (ModbusTransportException e) {
                e.printStackTrace();
            } /*catch (ErrorResponseException e) {
                e.printStackTrace();
            }*/
            //            读取数据
//            ModbusUtil.readHoldingRegister(master,0,0,37);
        } catch (ModbusInitException e) {
            e.printStackTrace();
        }
//        catch (ModbusTransportException e) {
//            e.printStackTrace();
//        }


        master.destroy();


    }

    public static byte[] short2byte(short s){
        byte[] b = new byte[2];
        for(int i = 0; i < 2; i++){
            int offset = 16 - (i+1)*8; //因为byte占4个字节，所以要计算偏移量
            b[i] = (byte)((s >> offset)&0xff); //把16位分为2个8位进行分别存储
        }
        return b;
    }

    public static String getBit(byte by){
        StringBuffer sb = new StringBuffer();
        sb.append((by>>7)&0x1)
        .append((by>>6)&0x1)
        .append((by>>5)&0x1)
        .append((by>>4)&0x1)
        .append((by>>3)&0x1)
        .append((by>>2)&0x1)
        .append((by>>1)&0x1)
        .append((by>>0)&0x1);
        return sb.toString();
    }


}
