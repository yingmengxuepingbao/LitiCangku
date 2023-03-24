package com.penghaisoft.wcs.log.model.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class WcsFourwaycarReportLog {
    private Integer id;

    private String reportType;

    private Integer taskId;

    private String inParam;

    private String returnStatus;

    private String returnInfo;

    private String userDefined1;

    private String userDefined2;

    private String userDefined3;

    private String userDefined4;

    private String userDefined5;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date receiveTimeMin;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date receiveTimeMax;

    private Date respTime;

    private String orderBy;// 排序
}