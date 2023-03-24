package com.penghaisoft.wcs.expose.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.modbus.ModbusUtil;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

/**
 * 上线初始化接口
 */
@RestController
@RequestMapping(value = "/expose/init")
@Slf4j
public class ExposeInitController {

    /**
     * 根据对象转换为写入sql
     * @param locationReals
     * @return
     */
    private String doGenInsertSql(List<WcsLocationReal> locationReals){
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO wcs_location_real(location_id,x,z,row,col,is_deep,stacker_id,shelf_id,direction) VALUES");
        for (int i = 0; i < locationReals.size(); i++) {
            WcsLocationReal locReal = locationReals.get(i);
            sql.append("(");
            sql.append(locReal.getLocationId()).append(",");
            sql.append(locReal.getX()).append(",");
            sql.append(locReal.getZ()).append(",");
            sql.append(locReal.getRow()).append(",");
            sql.append(locReal.getCol()).append(",");
            sql.append("'").append(locReal.getIsDeep()).append("',");
            sql.append(locReal.getStackerId()).append(",");
            sql.append(locReal.getShelfId()).append(",");
            sql.append("'").append(locReal.getDirection()).append("'");
            if (i == locationReals.size()-1){
                sql.append(");");
            }else {
                sql.append("),");
            }
        }
        return sql.toString();
    }

    /**
     * 根据plc 数据组装对象
     * @param stackerId
     * @param shelfId
     * @param xArr
     * @param zArr
     * @param direction
     * @param deep
     * @return
     */
    private List<WcsLocationReal> genLocs(int stackerId,int shelfId,int[] xArr,int[] zArr,int direction,int deep){
        List<WcsLocationReal> locationReals = new ArrayList<>();

        for (int i = 0; i < xArr.length; i++) {
            int x = xArr[i];
//            列号
            int colInt = i + 1;
            String col = "";
            if (colInt > 9){
                col = String.valueOf(colInt);
            }else {
                col = "0" + colInt;
            }
            for (int j = 0; j < zArr.length; j++) {
                int z = zArr[j];
                int rowInt = j+1;
                String row = String.valueOf(rowInt);
                for (int k = 1; k <= deep; k++) {
                    String locId = shelfId + col + row + k;
                    WcsLocationReal locationReal = new WcsLocationReal();
                    locationReal.setLocationId(Integer.parseInt(locId));
                    locationReal.setShelfId(shelfId);
                    locationReal.setCol(Integer.parseInt(col));
                    locationReal.setRow(rowInt);
                    if (k==2){
                        locationReal.setIsDeep("1");
                    }else {
                        locationReal.setIsDeep("0");
                    }
                    locationReal.setDirection(String.valueOf(direction));
                    locationReal.setX(x);
                    locationReal.setZ(z);
                    locationReal.setStackerId(stackerId);
                    locationReals.add(locationReal);
                }

            }
        }
        return locationReals;
    }

    /**
     * @param stackerId
     * @param ip
     * @param port
     * @param plusFlag plc的数据是否多加了1，多加了要减去
     * @param deep 深浅 1 2
     * @param leftShelfId
     * @param rightShelfId
     * @return
     */
    private String genInsertSql(int stackerId,String ip,int port,int plusFlag,int deep,int leftShelfId,int rightShelfId){
        String sql = "";
        ModbusFactory modbusFactory = new ModbusFactory();
        IpParameters params = new IpParameters();
        params.setHost(ip);
        params.setPort(port);
        ModbusMaster tcpMaster = modbusFactory.createTcpMaster(params, true);

        try {
            tcpMaster.init();

//            读取货位计数
            short[] countResult = ModbusUtil.readHoldingRegister(tcpMaster,1,41,4);
//            82 83
            int leftXCount = countResult[0];
//            84 85
            int rightXCount = countResult[1];
//            86 87
            int leftZCount = countResult[2];
//            88 89
            int rightZCount = countResult[3];
//            读取左货位X数据
            short[] leftLocX = ModbusUtil.readHoldingRegister(tcpMaster,1,45,leftXCount*2);
            int[] leftX = ModbusUtil.transShortArr2IntArr(leftLocX);
//            读取左货位Z数据
            short[] leftLocZ = ModbusUtil.readHoldingRegister(tcpMaster,1,445,leftZCount*2);
            int[] leftZ = ModbusUtil.transShortArr2IntArr(leftLocZ);
            List<WcsLocationReal> leftLocationReals = genLocs(stackerId,leftShelfId,leftX,leftZ,1,deep);
            String sql1 = doGenInsertSql(leftLocationReals);

            short[] rightLocX = ModbusUtil.readHoldingRegister(tcpMaster,1,245,rightXCount*2);
            int[] rightX = ModbusUtil.transShortArr2IntArr(rightLocX);
            short[] rightLocZ = ModbusUtil.readHoldingRegister(tcpMaster,1,645,rightZCount*2);
            int[] rightZ = ModbusUtil.transShortArr2IntArr(rightLocZ);
            List<WcsLocationReal> rightLocationReals = genLocs(stackerId,rightShelfId,rightX,rightZ,2,deep);
            String sql2 = doGenInsertSql(rightLocationReals);

            sql = sql1+sql2;
            return sql;
        } catch (ModbusInitException e) {
            e.printStackTrace();
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        }
        return sql;
    }


    /**
     * 上线初始化库位
     * 每个堆垛机调用一次
     * @return
     */
    @PostMapping("stacker/location")
    public JSONObject initLocation(@RequestBody JSONObject param) {
        int stackerId = param.getIntValue("stackerId");
        String ip = param.getString("ip");
        int port = param.getIntValue("port");
//      读取货位数时，是否+1，如果加了就要减去
        int plusFlag = param.getIntValue("plusFlag");
        int deep = param.getIntValue("deep");
        int leftShelfId = param.getIntValue("leftShelfId");
        int rightShelfId = param.getIntValue("rightShelfId");

        String sql = genInsertSql(stackerId,ip,port,plusFlag,deep,leftShelfId,rightShelfId);
        JSONObject result = new JSONObject();
        result.put("sql",sql);
        return result;
    }

}
