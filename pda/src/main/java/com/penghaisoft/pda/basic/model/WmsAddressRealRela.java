package com.penghaisoft.pda.basic.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WmsAddressRealRela {
    private Integer id;

    private String addressType;
    private List<String> addressTypeList;

    private String addressCode;

    private String addressName;

    private Integer realAddress;

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