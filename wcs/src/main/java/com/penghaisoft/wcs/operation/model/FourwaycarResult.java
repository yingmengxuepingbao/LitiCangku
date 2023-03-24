package com.penghaisoft.wcs.operation.model;

import lombok.Data;

/**
 * @ClassName FourwaycarResult
 * @Description 速锐四向车返回值
 * @Author zhangx
 * @Date 2020/7/9 16:02
 **/
@Data
public class FourwaycarResult {

    //    组号,同组任务顺序执行
    private String groupId;

//    0- 成功
//    1- 失败
    private Integer returnStatus;

    private String returnInfo;

    private Long msgTime;

}
