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
import com.penghaisoft.wms.basicmanagement.model.business.IWmsCustomerService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsCustomer;
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
@RequestMapping({"/customer"})
public class WmsCustomerController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsCustomerController.class);
    @Autowired
    private IWmsCustomerService wmsCustomerService;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private IBaseDictItemService baseDictItemService;

    public WmsCustomerController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsCustomer wmsCustomer) {
        wmsCustomer.setActiveFlag("1");
        wmsCustomer.setCustomerId(CommonUtils.getUUID());
        wmsCustomer.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsCustomer.setGmtCreate(new Date());
        wmsCustomer.setRegistrationDate(new Date());
        Resp resp = this.wmsCustomerService.create(wmsCustomer);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsCustomer wmsCustomer) {
        Resp resp = this.wmsCustomerService.delete(wmsCustomer);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsCustomer wmsCustomer) {
        Pager<WmsCustomer> resp = this.wmsCustomerService.findListByCondition(page, rows, wmsCustomer);
        List<BaseDictItem> baseDictItemList = this.baseDictItemService.getDictTypeByCode("customer_type");
        if (resp.getRecords().size() > 0) {
            Iterator var6 = resp.getRecords().iterator();

            while(var6.hasNext()) {
                WmsCustomer customer = (WmsCustomer)var6.next();
                Iterator var8 = baseDictItemList.iterator();

                while(var8.hasNext()) {
                    BaseDictItem baseDictItem = (BaseDictItem)var8.next();
                    if (customer.getCustomerType().equals(baseDictItem.getDicItemCode())) {
                        customer.setCustomerType(baseDictItem.getDicItemName());
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
        WmsCustomer wmsCustomer = this.wmsCustomerService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsCustomer);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsCustomer wmsCustomer) {
        wmsCustomer.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsCustomer.setGmtModified(new Date());
        Resp resp = this.wmsCustomerService.update(wmsCustomer);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
