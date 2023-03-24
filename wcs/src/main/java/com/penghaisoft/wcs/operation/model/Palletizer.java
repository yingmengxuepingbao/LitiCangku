package com.penghaisoft.wcs.operation.model;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 码垛机+码垛线对象
 * @Description Palletizer
 * @Auther zhangxu
 * @Date 2020/3/3 15:41
 **/
@Data
public class Palletizer extends BaseModbusDevice{

    /**
     * 码垛机信息
     * key 通道Id,巧媳妇的一共5个通道，3是托盘的
     */
    Map<Short,PalletizerInfo> palletizerInfoMap = new HashMap<>();

//    平库的
//    1允许 0禁止
    Short h1AllowPallet = 1;

    Short h2AllowPallet = 0;

    Short hasPallet = 0;

}
