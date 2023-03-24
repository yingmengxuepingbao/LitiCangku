//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.controller;

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
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutPlaneService;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutPlane;
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
@RequestMapping({"/orderOut"})
public class WmsOrderOutPlaneController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderOutPlaneController.class);
    @Autowired
    private IWmsOrderOutPlaneService wmsOrderOutPlaneService;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private IBaseDictItemService baseDictItemService;
    @Autowired
    private IWmsCommonService wmsCommonService;

    public WmsOrderOutPlaneController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderOutPlane wmsOrderOutPlane) {
        wmsOrderOutPlane.setActiveFlag("1");
        wmsOrderOutPlane.setOrderOutId(CommonUtils.getUUID());
        wmsOrderOutPlane.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderOutPlane.setGmtCreate(new Date());
        wmsOrderOutPlane.setCancelFlag("0");
        String orderNo = this.wmsCommonService.getOrderNoByType("PO");
        wmsOrderOutPlane.setOrderNo(orderNo);
        Resp resp = this.wmsOrderOutPlaneService.create(wmsOrderOutPlane);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsOrderOutPlane wmsOrderOutPlane) {
        Resp resp = this.wmsOrderOutPlaneService.delete(wmsOrderOutPlane);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsOrderOutPlane wmsOrderOutPlane) {
        wmsOrderOutPlane.setActiveFlag("1");
        if (wmsOrderOutPlane.getOrderBy() == null) {
            wmsOrderOutPlane.setOrderBy("gmt_create desc");
        }

        Pager<WmsOrderOutPlane> resp = this.wmsOrderOutPlaneService.findListByCondition(page, rows, wmsOrderOutPlane);
        List<BaseDictItem> baseDictItemList = this.baseDictItemService.getDictTypeByCode("order_out_type");
        if (resp.getRecords().size() > 0) {
            Iterator var6 = resp.getRecords().iterator();

            label31:
            while(true) {
                WmsOrderOutPlane orderOutPlane;
                do {
                    if (!var6.hasNext()) {
                        break label31;
                    }

                    orderOutPlane = (WmsOrderOutPlane)var6.next();
                } while(orderOutPlane.getOrderType() == null);

                Iterator var8 = baseDictItemList.iterator();

                while(var8.hasNext()) {
                    BaseDictItem baseDictItem = (BaseDictItem)var8.next();
                    if (orderOutPlane.getOrderType().equals(baseDictItem.getDicItemCode())) {
                        orderOutPlane.setOrderType(baseDictItem.getDicItemName());
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
        WmsOrderOutPlane wmsOrderOutPlane = this.wmsOrderOutPlaneService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderOutPlane);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsOrderOutPlane wmsOrderOutPlane) {
        Resp resp = this.wmsOrderOutPlaneService.update(wmsOrderOutPlane);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
