package com.penghaisoft.wcs.expose.dto;

import lombok.Data;

/**
 * 出库对象
 * @Description PalletOutDto
 * @Auther zhangxu
 * @Date 2020/3/4 12:22
 **/
@Data
public class PalletOutDto extends BaseExposeDto{


    /**
     * 出库库位
     *      * eg:1|01|3|1代表一个货位，10131
     *      * 1代表货架，01代表列，3代表层，1代表浅库位
     */
    private int fromLocation;

    /**
     * 目标出库口
     */
    private int targetAddress;

    /**
     * 路径id
     */
    private int pathId;

    /**
     * 依赖的移库对象
     */
    private PalletMoveDto relyMoveTask;

}
