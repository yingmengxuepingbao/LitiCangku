//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.controller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsInTemporaryService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsInTemporary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/wmsInTemporary"})
public class WmsInTemporaryController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsInTemporaryController.class);
    @Autowired
    private IWmsInTemporaryService wmsInTemporaryService;

    public WmsInTemporaryController() {
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsInTemporary wmsInTemporary) {
        wmsInTemporary.setActiveFlag("1");
        Pager<WmsInTemporary> result = this.wmsInTemporaryService.findListByCondition(page, rows, wmsInTemporary);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }
}
