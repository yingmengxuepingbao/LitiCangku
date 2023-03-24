//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.controller;

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
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCrossProductService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCrossProduct;
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
@RequestMapping({"/orderCrossProduct"})
public class WmsOrderCrossProductController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderCrossProductController.class);
    @Autowired
    private IWmsOrderCrossProductService wmsOrderCrossProductService;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Autowired
    private IBaseDictItemService baseDictItemService;

    public WmsOrderCrossProductController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderCrossProduct wmsOrderCrossProduct) {
        wmsOrderCrossProduct.setActiveFlag("1");
        wmsOrderCrossProduct.setOrderCrossProductId(CommonUtils.getUUID());
        wmsOrderCrossProduct.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderCrossProduct.setGmtCreate(new Date());
        String orderNo = this.wmsCommonService.getOrderNoByType("PC");
        wmsOrderCrossProduct.setOrderNo(orderNo);
        Resp resp = this.wmsOrderCrossProductService.create(wmsOrderCrossProduct);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsOrderCrossProduct wmsOrderCrossProduct) {
        Resp resp = this.wmsOrderCrossProductService.delete(wmsOrderCrossProduct);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsOrderCrossProduct wmsOrderCrossProduct) {
        if (wmsOrderCrossProduct.getOrderBy() == null) {
            wmsOrderCrossProduct.setOrderBy("gmt_create desc");
        }

        Pager<WmsOrderCrossProduct> resp = this.wmsOrderCrossProductService.findListByCondition(page, rows, wmsOrderCrossProduct);
        List<BaseDictItem> baseDictItemList = this.baseDictItemService.getDictTypeByCode("platform");
        if (resp.getRecords().size() > 0) {
            Iterator var6 = resp.getRecords().iterator();

            label31:
            while(true) {
                WmsOrderCrossProduct orderCrossProduct;
                do {
                    if (!var6.hasNext()) {
                        break label31;
                    }

                    orderCrossProduct = (WmsOrderCrossProduct)var6.next();
                } while(orderCrossProduct.getOutAddress() == null);

                Iterator var8 = baseDictItemList.iterator();

                while(var8.hasNext()) {
                    BaseDictItem baseDictItem = (BaseDictItem)var8.next();
                    if (baseDictItem.getDicItemCode().equals(orderCrossProduct.getOutAddress())) {
                        orderCrossProduct.setOutAddress(baseDictItem.getDicItemName());
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
        WmsOrderCrossProduct wmsOrderCrossProduct = this.wmsOrderCrossProductService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderCrossProduct);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsOrderCrossProduct wmsOrderCrossProduct) {
        wmsOrderCrossProduct.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderCrossProduct.setGmtModified(new Date());
        Resp resp = this.wmsOrderCrossProductService.update(wmsOrderCrossProduct);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"start"})
    public ResponseResult start(WmsOrderCrossProduct wmsOrderCrossProduct) {
        Resp resp = this.wmsOrderCrossProductService.start(wmsOrderCrossProduct);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
