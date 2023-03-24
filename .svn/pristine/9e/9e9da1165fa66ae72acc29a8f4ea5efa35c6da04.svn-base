package com.penghaisoft.wcs.operation.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName AgvTask
 * @Description agv任务单参数
 * @Author zhangx
 * @Date 2020/7/1 17:19
 **/
@Data
public class AgvTask {

    /**
     * 最大长度32 必填
     * 请求编号，每个请求都要一个唯一
     * 编号， 同一个请求重复提交， 使
     * 用同一编号
     */
    private String reqCode;

    /**
     * 最大长度32 不填系统自动生
     * 成，必须为 32 位 UUID
     * 当前任务单号
     */
    private String taskCode;


    /**
     * 正常入库
     * agv-task-typ: QXF1
     * 叫托盘
     * agv-task-typ-pallet: QXF2
     * 老线qxf3
     * agv-task-typ-m67: QXF3
     */
    private String taskTyp;


    /**
     * 最大长度20
     * yyyy-MM-dd HH:mm:ss
     */
    private String reqTime;

    /**
     * 最大长度16
     * 客户端编号，如 PDA，HCWMS 等。
     * 由 RCS-2000 告知上层系统
     */
    private String clientCode;

    /**
     * 64
     * 令 牌 号 , 由 调 度 系 统 颁 发 。 由
     * RCS-2000 告知上层系统
     */
    private String tokenCode;

    /**
     * 最大长度32
     * 工作位，与 RCS-2000 端配置的位置名称一致
     */
    private String wbCode;


    /**
     * positionCode:位置编号
     * 位置类型说明:
     * 00 表示：位置编号
     * 01 表示：物料批次号
     * 02 表示：策略编号（含多个区域）
     * 如：第一个区域放不下, 可以放第二
     * 个区域
     * 03 表示：货架编号，通过货架编号
     * 找到货架所在位置
     * 04 表示：区域编号，在区域中查找
     * 可用位置
     */
    private List<AgvPositionItem> positionCodePath;

    /**
     * positionCode:位置编号
     * 对象类型定义:
     * 00, 代表 nextPositionCode 是一个
     * 位置
     * 04: 代表 nextPositionCode 是一个区
     * 域
     */
    private AgvPositionItem nextPositionCode;

    /**
     * 最大长度16 非必填
     * 货架编号
     */
    private String podCode;
    /**
     * 最大长度4必填
     * “180”,”0”,”90”,”-90” 分别对应地图
     * 的”左”,”右”,”上”,”下”
     */
    private String podDir;


    /**
     * 16
     * 货架类型, 找满货架时传空, 找空
     * 货架时必传
     * -1: 代表不关心货架类型, 找到空货
     * 架即可.
     * -2: 代表从工作位获取关联货架类
     * 型, 如果未配置, 只找空货架.
     * 货架类型编号: 只找该货架类型的
     * 空货架.
     */
    private String podTyp;


    /**
     * 32
     * 物料批次或货架上的物料唯一编码,
     * 生成任务单时,货架与物料直接绑定
     * 时使用. （通过同时传 podCode 和
     * materialLot 来 绑 定 或 通 过
     * positionCode 找到位置上的货架和
     * materialLot 来绑定）
     */
    private String materialLot;

    /**
     * 1~5
     */
    private String priority;


    /**
     * AGV 编号，填写表示指定某一编号
     * 的 AGV 执行该任务
     */
    private String agvCode;


    /**
     * 最大长度2000 非必填
     * 自定义字段，不超过 2000 个字符
     */
    private String data;



}
