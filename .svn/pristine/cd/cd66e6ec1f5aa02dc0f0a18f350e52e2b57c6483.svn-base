package com.penghaisoft.wcs.monitormanagement.model.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Data
public class WcsManualOperationLog {

    private List<String> manualOperationLogIdList;

    private Integer manualOperationLogId;//  manualOperationLogId
    private String operationType;//  人工处理类型：0：堆垛机处理 1：线体处理
    private String operationName;//  人工处理描述：101：根据任务号新建线体任务 102：根据任务号清除线体任务 103：根据线体号清除线体任务 104：根据线体号清除警报 201：堆垛机切换模式 202：堆垛机急停 203：堆垛机无货行驶：不出叉，只是跑到指定货位 204：只取不放：空载堆垛机到指定位置拿货，需要切换到手动模式 指令为5 取货站台编码是0 外加取货排列层 205：有货行驶：不出叉，只是跑到指定货位 需要切换到手动模式 指令为6 要设置放货排列层，其余是0 206：只放不取：有货堆垛机到指定位置放货，需要切换到手动模式 指令为7 放货信息 207：回站台 指令码8
    private String data;//  指令内容
    private String status;//  状态： 0：执行成功 1：执行失败
    private String userDefined1;//  用户自定义1
    private String userDefined2;//  用户自定义2
    private String userDefined3;//  用户自定义3
    private String userDefined4;//  用户自定义4
    private String userDefined5;//  用户自定义5
    private Date gmtCreate;
    private Date gmtEnd;//  完成时间
    private String createBy;

    private int startNumber; //查询起始位置
    private int numberOnePage; //每页数据条数
    private String orderBy;// 排序
}

