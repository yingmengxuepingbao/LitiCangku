package com.penghaisoft.wcs.operation.model;

import lombok.Data;

/**
 * 码垛线数据
 * @Description PalletizerConveyorInfo
 * @Auther zhangxu
 * @Date 2020/3/27 11:25
 **/
@Data
public class PalletizerConveyorInfo {

    /**
     * 商品编号
     */
    private String goodsCode;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     *
     */
    private String palletCode;

    /**
     * 数量
     */
    private Short amount;

    /**
     * 码垛机定时任务绑定完成后写入
     * 是否绑定 1是0否
     */
    private Short isBind;


    /**
     * 入库任务下发后改为2
     * 1 码垛完成 2 上位机完成
     */
    private Short canClear;
}
