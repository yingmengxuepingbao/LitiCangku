package com.penghaisoft.pda.stereoscopic.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.common.*;
import com.penghaisoft.pda.outwarehouse.service.WmsOrderOutStereoscopicService;
import com.penghaisoft.pda.outwarehouse.service.WmsTaskExecutionLogService;
import com.penghaisoft.pda.storage.service.WmsLocationStereoscopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 立库出库分拣
 * @ClassName StereoscopicOutSortController
 * @Author luot
 * @Date 2020/2/24 15:27
 **/
@Slf4j
@RestController
@RequestMapping("stereoscopic/outwarehouse/sorting")
public class StereoscopicOutSortController {

    @Autowired
    private WmsOrderOutStereoscopicService wmsOrderOutStereoscopicService;

    @Autowired
    private WmsLocationStereoscopicService wmsLocationStereoscopicService;

    @Autowired
    private WmsTaskExecutionLogService wmsTaskExecutionLogService;



    @Autowired
    private IWmsCommonService wmsCommonService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private KanbanUtil kanbanUtil;

    /**
     * @return
     * @Description 扫描托盘码
     * @Author luot
     * @Date 2020/2/24 15:27
     * @Param
     **/
    @PostMapping("pllet/scan")
    public JSONObject scanPalletNo(@RequestHeader("account") String account, @RequestBody JSONObject param) {
        JSONObject result = null;
        //获取单号
        String palletCode = param.getString("palletCode");
        if (palletCode == null || "".equals(palletCode)) {
            result = CommonUtil.genErrorResult("输入托盘号");
            return result;
        }

        Resp resp = wmsOrderOutStereoscopicService.scanPalletCode(palletCode);
        if ("0".equals(resp.getCode())) {
            result = CommonUtil.genErrorResult(resp.getMessage());
        } else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }

    /**
     * @return
     * @Description 整托分拣
     * @Author luot
     * @Date 2020/2/24 17:34
     * @Param
     **/
    @PostMapping("pllet/allsubmit")
    public JSONObject allsubmit(@RequestHeader("account") String account, @RequestBody JSONObject param) {
        JSONObject result = null;
//        托盘码
        String palletCode = param.getString("palletCode");
        if (palletCode == null || "".equals(palletCode)) {
            result = CommonUtil.genErrorResult("输入托盘号");
            return result;
        }

        Resp resp = wmsOrderOutStereoscopicService.allsubmit(palletCode, account);
        if ("0".equals(resp.getCode())) {
            result = CommonUtil.genErrorResult(resp.getMessage());
        } else {
            stringRedisTemplate.delete("WMS:HANDSORT:INFO");
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }

    /**
     * @return
     * @Description 拆托分拣
     * @Author luot
     * @Date 2020/2/24 17:34
     * @Param
     **/
    @PostMapping("pllet/splitsubmit")
    public JSONObject splitsubmit(@RequestHeader("account") String account, @RequestHeader("areaCode") String areaCode, @RequestBody JSONObject param) {
        JSONObject result = null;

//        托盘码
        String palletCode = param.getString("palletCode");
//        出库单号
        String orderNo = param.getString("orderNo");
        String goodsCode = param.getString("goodsCode");
        String batchNo = param.getString("batchNo");
//        实际分拣数量
        String realAmount = param.getString("realAmount");
//        托盘是否返库【0：返库 1：入暂存区】
        String isPalletBack = param.getString("isPalletBack");
//        箱码
        List<String> boxBarcodeList = param.getJSONArray("boxCodes").toJavaList(String.class);

        if (palletCode == null || "".equals(palletCode)) {
            result = CommonUtil.genErrorResult("输入托盘号");
            return result;
        }
//        校验扫描的箱码是否是当前托盘内的
        boolean checkFlag = wmsOrderOutStereoscopicService.checkBarcodesBelongPallet(palletCode,boxBarcodeList);
        if (!checkFlag){
            result = CommonUtil.genErrorResult("您输入的箱码不属于当前托盘！");
            return result;
        }

        String key = Constant.ConfigInfo.INTERFACE_FOR_WCS_REDIS + palletCode;
        if (stringRedisTemplate.hasKey(key)) {
            result = CommonUtil.genErrorResult("当前托盘码正在处理中");
            return result;
        } else {
            stringRedisTemplate.opsForValue().set(key, "1", Constant.ConfigInfo.INTERFACE_FOR_WCS_REDIS_LOST_MINUTES, TimeUnit.MINUTES);//写入对象，并设置失效时间
        }

        Resp resp = wmsOrderOutStereoscopicService.splitsubmit(palletCode, Integer.parseInt(realAmount), isPalletBack, boxBarcodeList, account);
        if ("0".equals(resp.getCode())) {
            stringRedisTemplate.delete(key);//删除对象
            result = CommonUtil.genErrorResult(resp.getMessage());
            return result;
        } else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }

        stringRedisTemplate.delete(key);//删除对象
//      删除电子看板提示信息
//        stringRedisTemplate.delete("WMS:HANDSORT:INFO");
//        将LED显示内容更换成欢迎屏


        return result;
    }

    /**
     * @return
     * @Description 扫描箱码
     * @Author luot
     * @Date 2020/2/24 17:32
     * @Param
     **/
    @PostMapping("pllet/scanBoxBarcode")
    public JSONObject scanBarcode(@RequestHeader("account") String account, @RequestBody JSONObject param) {
        JSONObject result = null;

        String palletCode = param.getString("palletCode");

        String boxBarcode = param.getString("boxBarcode");

        if (palletCode == null || "".equals(palletCode)) {
            result = CommonUtil.genErrorResult("输入托盘号");
            return result;
        }
        if (boxBarcode == null || "".equals(boxBarcode)) {
            result = CommonUtil.genErrorResult("无箱码");
            return result;
        }

        Resp resp = wmsOrderOutStereoscopicService.scanBoxBarcode(palletCode, boxBarcode);
        if ("0".equals(resp.getCode())) {
            result = CommonUtil.genErrorResult(resp.getMessage());
        } else {
            result = CommonUtil.genSucessResultWithData(resp.getData());
        }
        return result;
    }

}