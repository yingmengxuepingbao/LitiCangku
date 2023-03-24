//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.controller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutStereoscopicDeailService;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/wmsOrderOutStereoscopicDeail"})
public class WmsOrderOutStereoscopicDeailController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderOutStereoscopicDeailController.class);
    @Autowired
    private IWmsOrderOutStereoscopicDeailService wmsOrderOutStereoscopicDeailService;

    public WmsOrderOutStereoscopicDeailController() {
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail) {
        Pager<WmsOrderOutStereoscopicDeail> pager = this.wmsOrderOutStereoscopicDeailService.findListByCondition(page, rows, wmsOrderOutStereoscopicDeail);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager);
        return result;
    }

    @GetMapping({"queryByAny"})
    public ResponseResult queryByAny(WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail) {
        wmsOrderOutStereoscopicDeail.setActiveFlag("1");
        List<WmsOrderOutStereoscopicDeail> list = this.wmsOrderOutStereoscopicDeailService.queryByAny(wmsOrderOutStereoscopicDeail);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }
}
