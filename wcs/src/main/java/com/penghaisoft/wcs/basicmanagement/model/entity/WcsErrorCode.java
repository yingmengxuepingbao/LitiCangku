package com.penghaisoft.wcs.basicmanagement.model.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Data
public class WcsErrorCode {

    private List<String> errIdList;

    private Integer errId;//  errId
    private String areaCode;//  所属库区
    private Integer errCode;//  故障码
    private String description;//  描述
    private String deviceType;//  1码垛机 2堆垛机 3RGV 4AGV 5四向穿梭车 6线体
    private String userDefined1;//  用户自定义1
    private String userDefined2;//  用户自定义2
    private String userDefined3;//  用户自定义3
    private String userDefined4;//  用户自定义4
    private String userDefined5;//  用户自定义5

    String orderBy;// 排序

    private String createBy;
    private String remark;
    private Date gmtCreate;
    private Date gmtCreateMin;
    private Date gmtCreateMax;
    private String lastModifiedBy;// 最后更新人
    private Date gmtModified;
    private Date gmtModifiedMax;
    private Date gmtModifiedMin;
    private String activeFlag;
}

