package com.penghaisoft.wcs.expose.dto;

import lombok.Data;

/**

/**
 *@ClassName AgvResult
 *@Description 返回给agv的数据对象
 *@Author zhangx
 *@Date 2020/7/1 17:16
 *
 **/
@Data
public class AgvResult {

//    返回码 0
    private String code = "0";

    private String message = "成功";
//  请求编号
    private String reqCode;

//    自定义
    private String data;
}
