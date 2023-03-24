package com.penghaisoft.wcs.expose.dto;

import lombok.Data;

/**
 * @ClassName FourWayCarTaskApply
 * @Description 四向车任务申请对象
 * @Author zhangx
 * @Date 2020/7/8 10:47
 **/
@Data
public class FourWayCarTaskApply {

    /**
     * 申请时间
     */
    private Long applyTime;

    /**
     * 入库口编号
     */
    private String fromPort;


    /**
     * 托盘编号
     */
    private String barCode;

    /**
     * 货物高度 1，2，3 三个等级
     */
    private Integer cargoHeight;

    /**
     * 货物重量
     */
    private String cargoWeight;
}
