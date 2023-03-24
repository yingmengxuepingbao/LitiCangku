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
import com.penghaisoft.wms.basicmanagement.model.business.IWmsWarehouseService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouse;
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
@RequestMapping({"/warehouse"})
public class WmsWarehouseController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsWarehouseController.class);
    @Autowired
    private IWmsWarehouseService wmsWarehouseService;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private IBaseDictItemService baseDictItemService;

    public WmsWarehouseController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsWarehouse wmsWarehouse) {
        wmsWarehouse.setActiveFlag("1");
        wmsWarehouse.setWarehouseId(CommonUtils.getUUID());
        wmsWarehouse.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsWarehouse.setGmtCreate(new Date());
        Resp resp = this.wmsWarehouseService.create(wmsWarehouse);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsWarehouse wmsWarehouse) {
        Resp resp = this.wmsWarehouseService.delete(wmsWarehouse);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsWarehouse wmsWarehouse) {
        Pager<WmsWarehouse> resp = this.wmsWarehouseService.findListByCondition(page, rows, wmsWarehouse);
        List<BaseDictItem> baseDictItemList = this.baseDictItemService.getDictTypeByCode("wh_type");
        if (resp.getRecords().size() > 0) {
            Iterator var6 = resp.getRecords().iterator();

            while(var6.hasNext()) {
                WmsWarehouse warehouse = (WmsWarehouse)var6.next();
                Iterator var8 = baseDictItemList.iterator();

                while(var8.hasNext()) {
                    BaseDictItem baseDictItem = (BaseDictItem)var8.next();
                    if (warehouse.getWarehouseAttr().equals(baseDictItem.getDicItemCode())) {
                        warehouse.setWarehouseAttr(baseDictItem.getDicItemName());
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
        WmsWarehouse wmsWarehouse = this.wmsWarehouseService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsWarehouse);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsWarehouse wmsWarehouse) {
        wmsWarehouse.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsWarehouse.setGmtModified(new Date());
        Resp resp = this.wmsWarehouseService.update(wmsWarehouse);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @GetMapping({"getWarehouseCode/{code}"})
    public ResponseResult getWarehouseCode(@PathVariable String code) {
        List<BaseDictItem> baseDictItemList = this.wmsWarehouseService.getWarehouseCode();
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, baseDictItemList);
    }
}
