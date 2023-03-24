//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.controller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsBoxBarcodeService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsBoxBarcode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/wmsBoxBarcode"})
public class WmsBoxBarcodeController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsBoxBarcodeController.class);
    @Autowired
    private IWmsBoxBarcodeService wmsBoxBarcodeService;

    public WmsBoxBarcodeController() {
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsBoxBarcode wmsBoxBarcode) {
        Pager<WmsBoxBarcode> pager = this.wmsBoxBarcodeService.findListByCondition(page, rows, wmsBoxBarcode);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager);
        return result;
    }
}
