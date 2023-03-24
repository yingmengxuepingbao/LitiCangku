package com.penghaisoft.pda.plane.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutPlaneDeailDTO;
import com.penghaisoft.pda.outwarehouse.service.PlaneOutWarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平库出库任务
 * @Auther jzh
 * @Date 2020/2/20 19:33
 **/
@Slf4j
@RestController
@RequestMapping("planeout")
public class PlaneOutController {

    @Autowired
    private PlaneOutWarehouseService planeOutWarehouseService;


    /**
     * 出库提单
     * @param account
     * @param param
     * @return
     */
    @PostMapping("lading")
    public JSONObject lading(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        //获取单号
        String orderNo = param.getString("orderNo");
        String areaCode = param.getString("areaCode");
        if(areaCode==null||"".equals(areaCode)){
            result = CommonUtil.genErrorResult("请选择库区");
            return result;
        }
        //判断出库单状态 （获取出库明细）
        Resp resp = planeOutWarehouseService.lading(orderNo,account,areaCode);
        if ("0".equals(resp.getCode())){
            result = CommonUtil.genErrorResult(resp.getMessage());
        }else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }

    /**
     * 扫描托盘
     * jzh
     * @param account
     * @param param
     * @return
     */
    @PostMapping("scanpalletNo")
    public JSONObject scanPalletNo(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        //获取单号
        String palletNo = param.getString("palletNo");
        String areaCode = param.getString("areaCode");
        String goodsCode = param.getString("goodsCode");
        String batchNo = param.getString("batchNo");
        String locationCode = param.getString("locationCode");
        if(areaCode==null||"".equals(areaCode)){
            result = CommonUtil.genErrorResult("请选择库区");
            return result;
        }
        if(palletNo==null||"".equals(palletNo)){
            result = CommonUtil.genErrorResult("输入托盘号");
            return result;
        }
        if(goodsCode==null||"".equals(goodsCode)){
            result = CommonUtil.genErrorResult("无商品");
            return result;
        }
        if(batchNo==null||"".equals(batchNo)){
            result = CommonUtil.genErrorResult("无批次");
            return result;
        }
        if(locationCode==null||"".equals(locationCode)){
            result = CommonUtil.genErrorResult("无库位");
            return result;
        }
        //判断出库单状态 （获取出库明细）
        Resp resp = planeOutWarehouseService.scanPalletNo(palletNo,account,areaCode,goodsCode,batchNo,locationCode);
        if ("0".equals(resp.getCode())){
            result = CommonUtil.genErrorResult(resp.getMessage());
        }else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }

    /**
     * 扫描箱码
     * jzh
     * @param account
     * @param param
     * @return
     */
    @PostMapping("scanbarcode")
    public JSONObject scanBarcode(@RequestHeader("account") String account,@RequestBody JSONObject param){
        JSONObject result = null;
        //获取单号
        String palletCode = param.getString("palletCode");

        String boxBarcode = param.getString("boxBarcode");

        if(palletCode==null||"".equals(palletCode)){
            result = CommonUtil.genErrorResult("输入托盘号");
            return result;
        }
        if(boxBarcode==null||"".equals(boxBarcode)){
            result = CommonUtil.genErrorResult("无箱码");
            return result;
        }

        //判断出库单状态 （获取出库明细）
        Resp resp = planeOutWarehouseService.scanBarcode(palletCode,boxBarcode);
        if ("0".equals(resp.getCode())){
            result = CommonUtil.genErrorResult(resp.getMessage());
        }else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }

    /**
     * 确认完成
     * @param orderNo
     * @param account
     * @param orderInfos
     * @return
     */
    @PostMapping("confirm/out/{orderNo}")
    public JSONObject confirmOrderOut(@PathVariable("orderNo") String orderNo, @RequestHeader("account") String account,@RequestBody JSONObject orderInfos){

        JSONObject result = null;

        JSONArray orderInfoArray = orderInfos.getJSONArray("orderInfos");
        List<WmsOrderOutPlaneDeailDTO> wmsOrderInfos = orderInfoArray.toJavaList(WmsOrderOutPlaneDeailDTO.class);
        System.out.println(wmsOrderInfos);
        Resp resp = planeOutWarehouseService.confirmOrderOut(orderNo,wmsOrderInfos,account);
        if ("0".equals(resp.getCode())){
            result = CommonUtil.genErrorResult(resp.getMessage());
        }else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }

}
