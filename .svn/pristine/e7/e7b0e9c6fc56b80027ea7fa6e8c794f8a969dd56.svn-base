package com.penghaisoft.wcs.expose.dto;

import lombok.Data;

/**
 * @ClassName AgvCallback
 * @Description agv回调WCS的入参
 * @Author zhangx
 * @Date 2020/7/1 17:19
 **/
@Data
public class AgvCallback {

    /**
     * 最大长度32 必填
     * 请求编号，每个请求都要一个唯一
     * 编号， 同一个请求重复提交， 使
     * 用同一编号
     */
    private String reqCode;

    /**
     * 最大长度20 必填
     * yyyy-MM-dd HH:mm:ss
     */
    private String reqTime;

    /**
     * 最大长度16 必填
     * 由 RCS-2000 任务模板配置后并告知
     * 上层系统
     * 默认使用方式:
     * start : 任务开始
     * outbin : 走出储位
     * end : 任务结束
     * cancel : 任务单取消
     */
    private String method;

    /**
     * 最大长度4必填
     * “180”,”0”,”90”,”-90” 分别对应地图
     * 的”左”,”右”,”上”,”下”
     */
    private String podDir;


    /**
     * 最大长度16 必填
     * AGV 编号（同 agvCode ）
     */
    private String robotCode;


    /**
     * 最大长度32 必填
     * 当前任务单号
     */
    private String taskCode;


    /**
     * 最大长度32 必填
     * 工作位，与 RCS-2000 端配置的位置名称一致
     */
    private String wbCode;


    /**
     * 最大长度64 tcp协议必传 http不需要
     */
    private String interfaceName;

    /**
     * 最大长度8 非必填
     * 地码 X 坐标(mm)
     */
    private String cooX;

    /**
     * 最大长度8 非必填
     * 地码 Y 坐标(mm)
     */
    private String cooY;


    /**
     * 最大长度32 非必填
     * 当前位置编号
     */
    private String currentPositionCode;

    /**
     * 最大长度2000 非必填
     * 自定义字段，不超过 2000 个字符
     */
    private String data;

    /**
     * 最大长度16 非必填
     * 地图编号
     */
    private String mapCode;

    /**
     * 最大长度32 非必填
     * 地码编号，唯一标识
     */
    private String mapDataCode;

    /**
     * 最大长度16 非必填
     * 货架编号
     */
    private String podCode;
}
