package com.penghaisoft.wcs.operation.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName FourWayCarTask
 * @Description 四向车下发任务对象
 * @Author zhangx
 * @Date 2020/7/9 15:31
 **/
@Data
public class FourWayCarTask {

//    组号,同组任务顺序执行
    private String groupId;

    private Long msgTime;

//    非必填
    private Integer priorityCode;


    private List<FourWayCarTaskItem> tasks;
}
