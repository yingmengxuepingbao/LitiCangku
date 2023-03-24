package com.penghaisoft.pda.storage.model;

import lombok.Data;

import java.util.Date;

@Data
public class WmsPallet {
    private String palletId;//  palletId
    private String palletCode;//  托盘编码
    private String goodsCode;//  商品编码
    private String goodsCodeNull;
    private String goodsName;//  商品名称
    private Integer amount;//  数量
    private String amountNull;
    private Integer amountDel;
    private String batchNo;//  批次号
    private String batchNoNull;
    private String warehouseCode;//  所属仓库
    private String areaCode;//  所属库区
    private String locationCode;//  库位编码
    private String locationCodeNull;
    private String locationCodeIsNull;
    private String lockBy;//  针对立库：被哪个任务锁定
    private String lockByNull;
    private String lockByIsNull;
    private String channelLocation;//  出库时的途径库位
    private String channelLocationNull;
    private String userDefined1;//  用户自定义1
    private String userDefined2;//  用户自定义2
    private String userDefined3;//  用户自定义3
    private String userDefined4;//  用户自定义4
    private String userDefined5;//  用户自定义5

    private String createBy;

    private Date gmtCreate;

    private String lastModifiedBy;

    private Date gmtModified;

    private String activeFlag;
}