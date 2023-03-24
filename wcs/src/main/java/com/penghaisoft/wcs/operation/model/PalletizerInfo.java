package com.penghaisoft.wcs.operation.model;

import lombok.Data;

/**
 * 码垛机数据
 * @Description PalletizerConveyorInfo
 * @Auther zhangxu
 * @Date 2020/3/27 11:25
 **/
@Data
public class PalletizerInfo {

    /**
     * 当可以AGV叉货，PLC会置1
     */
    private Short reqTrans;

    /**
     * AGV叉走托盘后，WCS置1，码垛机会继续干活
     */
    private Short recFinish;

    /**
     * 商品编号
     */
    private String goodsCode;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 托盘号
     */
    private String palletCode;

    /**
     * 数量
     */
    private Short amount;

}
