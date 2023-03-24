//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsSupplierService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsSupplier;
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
@RequestMapping({"/supplier"})
public class WmsSupplierController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsSupplierController.class);
    @Autowired
    private IWmsSupplierService wmsSupplierService;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private IBaseDictItemService baseDictItemService;

    public WmsSupplierController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsSupplier wmsSupplier) {
        wmsSupplier.setActiveFlag("1");
        wmsSupplier.setSupplierId(CommonUtils.getUUID());
        wmsSupplier.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsSupplier.setGmtCreate(new Date());
        wmsSupplier.setRegistrationDate(new Date());
        Resp resp = this.wmsSupplierService.create(wmsSupplier);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsSupplier wmsSupplier) {
        Resp resp = this.wmsSupplierService.delete(wmsSupplier);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsSupplier wmsSupplier) {
        Pager<WmsSupplier> resp = this.wmsSupplierService.findListByCondition(page, rows, wmsSupplier);
        List<BaseDictItem> baseDictItemList = this.baseDictItemService.getDictTypeByCode("supplier_type");
        if (resp.getRecords().size() > 0) {
            Iterator var6 = resp.getRecords().iterator();

            while(var6.hasNext()) {
                WmsSupplier supplier = (WmsSupplier)var6.next();
                Iterator var8 = baseDictItemList.iterator();

                while(var8.hasNext()) {
                    BaseDictItem baseDictItem = (BaseDictItem)var8.next();
                    if (supplier.getSupplierType().equals(baseDictItem.getDicItemCode())) {
                        supplier.setSupplierType(baseDictItem.getDicItemName());
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
        WmsSupplier wmsSupplier = this.wmsSupplierService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsSupplier);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsSupplier wmsSupplier) {
        wmsSupplier.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsSupplier.setGmtModified(new Date());
        Resp resp = this.wmsSupplierService.update(wmsSupplier);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
