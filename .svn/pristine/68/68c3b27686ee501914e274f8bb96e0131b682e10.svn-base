package com.penghaisoft.wcs.expose.dto;

import lombok.Data;

/**
* @Description 四向车任务上报对象
* @Date 2020/7/29 9:09
 * zhangx
**/
@Data
public class FourWayCarTaskReport {

    /**
     * WMS任务编号
     */
    private Integer taskId;

    /**
     * 上报时间
     */
    private Long reportTime;

    /**
     * 状态
     * 0	已接收
     * 1	任务开始	 A
     * 2	开始进提升机
     * 3	已出提升机
     * 4	取货完成  B
     * 5	搬运中
     * 6	放货完成
     * 7	任务中断
     * 8	任务结束  D
     * 9	即将完成	预完成信号，用于看板提前显示  C
     */
    private String taskStatus;


    /**
     * taskStatus为失败时填入失败原因
     */
    private String returnInfo;

}
