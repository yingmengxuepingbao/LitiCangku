//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.controller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCrossProductDeailService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCrossProductDeail;
import java.util.Date;
import java.util.HashMap;
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
@RequestMapping({"/orderCrossProductDetail"})
public class WmsOrderCrossProductDeailController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderCrossProductDeailController.class);
    @Autowired
    private IWmsOrderCrossProductDeailService wmsOrderCrossProductDeailService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsOrderCrossProductDeailController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderCrossProductDeail wmsOrderCrossProductDeail) {
        wmsOrderCrossProductDeail.setActiveFlag("1");
        wmsOrderCrossProductDeail.setOrderCrossProductDetailId(CommonUtils.getUUID());
        wmsOrderCrossProductDeail.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderCrossProductDeail.setGmtCreate(new Date());
        Resp resp = this.wmsOrderCrossProductDeailService.create(wmsOrderCrossProductDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsOrderCrossProductDeail wmsOrderCrossProductDeail) {
        Resp resp = this.wmsOrderCrossProductDeailService.delete(wmsOrderCrossProductDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsOrderCrossProductDeail wmsOrderCrossProductDeail) {
        Pager<WmsOrderCrossProductDeail> resp = this.wmsOrderCrossProductDeailService.findListByCondition(page, rows, wmsOrderCrossProductDeail);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsOrderCrossProductDeail wmsOrderCrossProductDeail = this.wmsOrderCrossProductDeailService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderCrossProductDeail);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsOrderCrossProductDeail wmsOrderCrossProductDeail) {
        Resp resp = this.wmsOrderCrossProductDeailService.update(wmsOrderCrossProductDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @GetMapping({"queryByOrderNo/{orderNo}"})
    public ResponseResult queryByOrderNo(@PathVariable String orderNo) {
        List<WmsOrderCrossProductDeail> list = this.wmsOrderCrossProductDeailService.queryByOrderNo(orderNo);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }
}
