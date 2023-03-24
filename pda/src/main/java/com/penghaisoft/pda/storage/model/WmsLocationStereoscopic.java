package com.penghaisoft.pda.storage.model;

import lombok.Data;

import java.util.Date;

@Data
public class WmsLocationStereoscopic {
    private String deepLocationFlag = "2";//	深库位标志

    private String shallowLocationFlag = "1";//	浅库位标志

    private String locationId;

    private String goodsCode;//  商品编码
    private String batchNo;//  批次号

    private String warehouseCode;

    private String areaCode;

    private String locationCode;

    private String locationAttr;

    private String locationDesc;

    private String palletCode;

    private Integer inSeq;

    private Integer outSeq;

    private Integer floorNumber;//  楼层

    private Integer shelvesNumber;

    private Integer columnNumber;

    private Integer layerNumber;

    private String useStatus;

    private String allowMix;

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