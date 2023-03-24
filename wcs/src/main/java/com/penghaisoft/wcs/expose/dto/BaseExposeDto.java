package com.penghaisoft.wcs.expose.dto;

import lombok.Data;

/**
 * @Description BaseExposeDto
 * @Auther zhangxu
 * @Date 2020/3/4 11:33
 **/
@Data
public class BaseExposeDto {

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
}
