package com.penghaisoft.pda.basic.model;

import lombok.Data;

/**
 * 入库对象
 * @Description PalletInDto
 * @Auther zhangxu
 * @Date 2020/3/4 11:22
 **/
@Data
public class PalletInDto{
    /**
     * 任务id， wms生成
     * 格式为年月日+task_type+5位序列号，共12位
     */
    private long taskId;

    /**
     * 托盘编码
     */
    private String palletCode;

    /**
     * 操作人账号(昵称)，例如 张三(35)
     */
    private String operator;

    /**
     * 入口path
     */
    private int fromAddress;

    /**
     * 目标库位
     * eg:1|01|3|1代表一个货位，10131
     * 1代表货架，01代表列，3代表层，1代表浅库位
     */
    private int targetLocation;

    private String taskType;

}
