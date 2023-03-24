//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.inboundmanagement.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.inboundmanagement.model.business.IWmsOrderInService;
import com.penghaisoft.wms.inboundmanagement.model.entity.WmsOrderIn;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/orderIn"})
public class WmsOrderInController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderInController.class);
    @Autowired
    private IWmsOrderInService wmsOrderInService;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Autowired
    private IBaseDictItemService baseDictItemService;

    public WmsOrderInController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderIn wmsOrderIn) {
        wmsOrderIn.setActiveFlag("1");
        wmsOrderIn.setOrderInId(CommonUtils.getUUID());
        wmsOrderIn.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderIn.setGmtCreate(new Date());
        Resp resp = this.wmsOrderInService.create(wmsOrderIn);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"creatOrderIn"})
    public ResponseResult creatOrderIn(WmsOrderIn wmsOrderIn) {
        String orderType = wmsOrderIn.getOrderType();
        String remark = wmsOrderIn.getRemark();
        String goodsInfo = wmsOrderIn.getUserDefined1();
        String orderNo ;
        if(wmsOrderIn.getOrderNo().isEmpty()) {
            orderNo = this.wmsCommonService.getOrderNoByType("RK");
        }else {
            orderNo = wmsOrderIn.getOrderNo();
        }
        Date now = new Date();
        List<WmsOrderIn> orderInList = new ArrayList();
        if (goodsInfo != null && !"".equals(goodsInfo) && !"[]".equals(goodsInfo)) {
            JSONArray goodsArray = JSONArray.parseArray(goodsInfo);
            int intem = 0;

            for(int i = 0; i < goodsArray.size(); ++i) {
                JSONObject goods = goodsArray.getJSONObject(i);
                String goodsCode = goods.getString("goodsCode");
                String goodsName = goods.getString("goodsName");
                Integer planAmount = goods.getInteger("planAmount");
                WmsOrderIn orderIn = new WmsOrderIn();
                orderIn.setOrderInId(CommonUtils.getUUID());
                orderIn.setOrderNo(orderNo);
                intem += 10;
                orderIn.setOrderItem("00" + intem);
                orderIn.setOrderType(orderType);
                orderIn.setOrderStatus("1");
                orderIn.setRemark(remark);
                orderIn.setGoodsCode(goodsCode);
                orderIn.setGoodsName(goodsName);
                orderIn.setPlanAmount(planAmount);
                orderIn.setRealAmount(0);
                orderIn.setActiveFlag("1");
                orderIn.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
                orderIn.setGmtCreate(now);
                //区分成品原材料
                orderIn.setUserDefined2(wmsOrderIn.getUserDefined2());
                orderInList.add(orderIn);
            }
        }

        Resp resp = this.wmsOrderInService.creatOrderIn(orderInList);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsOrderIn wmsOrderIn) {
        Resp resp = this.wmsOrderInService.delete(wmsOrderIn);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsOrderIn wmsOrderIn) {
        Pager<WmsOrderIn> resp = this.wmsOrderInService.findListByCondition(page, rows, wmsOrderIn);
        List<BaseDictItem> baseDictItemList = this.baseDictItemService.getDictTypeByCode("order_in_type");
        if (resp.getRecords().size() > 0) {
            Iterator var6 = resp.getRecords().iterator();

            while(var6.hasNext()) {
                WmsOrderIn orderIn = (WmsOrderIn)var6.next();
                Iterator var8 = baseDictItemList.iterator();

                while(var8.hasNext()) {
                    BaseDictItem baseDictItem = (BaseDictItem)var8.next();
                    if (orderIn.getOrderType().equals(baseDictItem.getDicItemCode())) {
                        orderIn.setOrderType(baseDictItem.getDicItemName());
                    }
                }
            }
        }

        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsOrderIn wmsOrderIn = this.wmsOrderInService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderIn);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsOrderIn wmsOrderIn) {
        Resp resp = this.wmsOrderInService.update(wmsOrderIn);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
