package com.penghaisoft.wcs.log.model.entity;

import lombok.Data;

import java.util.Date;
/**
 * @Description 调用AGV日志
 * @Date 2020/7/25 9:12
 * zhangx
 * @param
 * @return
 **/
@Data
public class WcsCallAgvLog {
    private Integer id;

    private Integer taskCode;

    private String reqCode;

    private String interfaceName;

    private String taskType;

    private String positionCode;

    private String reqFlag;

    private String respData;

    private String userDefined1;

    private String userDefined2;

    private String userDefined3;

    private String userDefined4;

    private String userDefined5;

    private Date sendTime;

    private Date respTime;

    private String orderBy;// 排序
}