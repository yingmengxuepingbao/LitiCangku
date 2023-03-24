package com.penghaisoft.pda.storage.model;

import lombok.Data;

import java.util.Date;

@Data
public class WmsOrderCheck {
    private String areaType;

    private String checkId;

    private String orderNo;

    private String orderType;

    private String orderStatus;

    private String areaCode;

    private String diffFlag;

    private String goodsName;

    private String goodsCode;

    private String locationCode;

    private String remark;

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