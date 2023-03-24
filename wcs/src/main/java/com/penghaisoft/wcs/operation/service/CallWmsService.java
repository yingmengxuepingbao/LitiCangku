package com.penghaisoft.wcs.operation.service;

import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.operation.model.RecommendLocResp;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;

import java.util.List;

/**
 * 调用wms 的接口
 */
public interface CallWmsService {

    /**
     * 记录binding_info，上传wms，拿到推荐库位
     * @param bindingInfo  包括 String palletCode, String goodsCode,
     *                             Integer amount, String batchNo
     * @param operator
     * @return
     */
    RecommendLocResp uploadBindingInfo(WcsBindingInfo bindingInfo, String operator);

    /**
     * 调用wms接口计算推荐库位，拿到推荐库位后进行入库任务拆解
     * @param bindingInfo 包括 String palletCode, String goodsCode,
     *                             Integer amount, String batchNo
     * @param palletizerId
     * @param addressId
     * @param operator
     * @return
     */
    boolean recommendAndSplitProductInTask(WcsBindingInfo bindingInfo, Integer palletizerId, Integer addressId, String operator);


    /**
     * 查询report_wms=1
     * @return
     */
    List<WcsBindingInfo> queryMoveBindingInfo() ;
    /**
     * 转移绑定信息
     * @param list
     * @param idList
     * @return
     */
    Resp dealBindingInfo(List<WcsBindingInfo> list, List<Integer> idList) ;

    /**
    * @Description 请求wms 获取推荐库位
    * @Date 2020/7/9 14:38
    * @param palletCode
    * @return WcsTask
    **/
    WcsTask recommendProductInLocation(String palletCode);

    /**
    * @Description 请求wms获取出库的虚拟托盘库位
    * @Date 2020/7/17 10:01
    * @return java.lang.Integer
    **/
    Resp recommendVirtualPalletOutLocation();

    /**
    * @Description LED显示
    * @Date 2020/9/24 14:54
    * @param palletCode
    **/
    Resp showOutPalletInfo(Integer taskNo,String palletCode,String status);

    /**
    * @Description 获取WMS虚拟托盘入库推荐库位
    * @Date 2020/8/13 16:50
    * @param virtualPalletCode
    * @return com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask
    **/
    WcsTask recommendVirtualPalletInLocation(String virtualPalletCode);

    /**
    * @Description 告知WMS托盘即将出库，增加出库托盘数
    * @Date 2020/9/25 14:53
    * @param taskId, palletCode
    * @return void
    **/
    void palletReadyOut(Long taskId, String palletCode);
}
