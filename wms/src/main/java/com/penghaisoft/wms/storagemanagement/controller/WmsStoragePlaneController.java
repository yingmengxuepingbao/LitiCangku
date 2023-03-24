//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.controller;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsSupplierService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsStoragePlaneService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsStoragePlane;
import java.util.List;
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
@RequestMapping({"/wmsStoragePlane"})
public class WmsStoragePlaneController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsStoragePlaneController.class);
    @Autowired
    private IWmsStoragePlaneService wmsStoragePlaneService;
    @Autowired
    private IWmsSupplierService wmsSupplierService;

    public WmsStoragePlaneController() {
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsStoragePlane wmsStoragePlane) {
        wmsStoragePlane.setActiveFlag("1");
        Pager<WmsStoragePlane> result = this.wmsStoragePlaneService.findListByCondition(page, rows, wmsStoragePlane);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }

    @GetMapping({"getAvailableGoods"})
    public ResponseResult getAvailableGoods() {
        List<BaseDictItem> baseDictItemList = this.wmsStoragePlaneService.getAvailableGoods();
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, baseDictItemList);
    }

    @GetMapping({"findBatchNo/{goodsCode}"})
    public ResponseResult findBatchNo(@PathVariable String goodsCode) {
        List<BaseDictItem> baseDictItemList = this.wmsStoragePlaneService.findBatchNo(goodsCode);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, baseDictItemList);
    }

    @PostMapping({"findLocation"})
    public ResponseResult findLocation(WmsStoragePlane wmsStoragePlane) {
        List<BaseDictItem> baseDictItemList = this.wmsStoragePlaneService.findLocation(wmsStoragePlane);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, baseDictItemList);
    }

    @PostMapping({"findAvaAmount"})
    public ResponseResult findAvaAmount(WmsStoragePlane wmsStoragePlane) {
        wmsStoragePlane.setActiveFlag("1");
        List<WmsStoragePlane> list = this.wmsStoragePlaneService.findAvaAmount(wmsStoragePlane);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }
}
