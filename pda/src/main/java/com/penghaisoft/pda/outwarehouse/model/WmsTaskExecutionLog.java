package com.penghaisoft.pda.outwarehouse.model;

import lombok.Data;

import java.util.Date;

@Data
public class WmsTaskExecutionLog {
    private Integer id;

    private long taskId;
    private String orderNo;//  单据编号

    private String taskType;

    private String taskStatus;

    private String errorMsg;

    private String inAddress;

    private String outAddress;

    private String goodsCode;

    private String batchNo;

    private String warehouseCode;

    private String areaCode;

    private String locationCode;

    private String palletCode;

    private String userDefined1;

    private String userDefined2;

    private String userDefined3;

    private String userDefined4;

    private String userDefined5;

    private String createBy;

    private Date gmtCreate;

    private String lastModifiedBy;

    private Date gmtModified;

    private String activeFlag;
}