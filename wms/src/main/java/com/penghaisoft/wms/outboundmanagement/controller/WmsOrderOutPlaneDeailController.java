//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.controller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutPlaneDeailService;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutPlaneDeail;
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
@RequestMapping({"/orderOutDetail"})
public class WmsOrderOutPlaneDeailController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderOutPlaneDeailController.class);
    @Autowired
    private IWmsOrderOutPlaneDeailService wmsOrderOutPlaneDeailService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsOrderOutPlaneDeailController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        wmsOrderOutPlaneDeail.setActiveFlag("1");
        wmsOrderOutPlaneDeail.setOrderOutDetailId(CommonUtils.getUUID());
        wmsOrderOutPlaneDeail.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderOutPlaneDeail.setGmtCreate(new Date());
        wmsOrderOutPlaneDeail.setRealAmount(0);
        Resp resp = this.wmsOrderOutPlaneDeailService.create(wmsOrderOutPlaneDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        Resp resp = this.wmsOrderOutPlaneDeailService.delete(wmsOrderOutPlaneDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        wmsOrderOutPlaneDeail.setActiveFlag("1");
        Pager<WmsOrderOutPlaneDeail> resp = this.wmsOrderOutPlaneDeailService.findListByCondition(page, rows, wmsOrderOutPlaneDeail);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail = this.wmsOrderOutPlaneDeailService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderOutPlaneDeail);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        Resp resp = this.wmsOrderOutPlaneDeailService.update(wmsOrderOutPlaneDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @GetMapping({"queryByOrderNo/{orderNo}"})
    public ResponseResult queryByOrderNo(@PathVariable String orderNo) {
        List<WmsOrderOutPlaneDeail> list = this.wmsOrderOutPlaneDeailService.queryByOrderNo(orderNo);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }

    @PostMapping({"distribution"})
    public ResponseResult distribution(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        Resp resp = this.wmsOrderOutPlaneDeailService.distribution(wmsOrderOutPlaneDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"cancel"})
    public ResponseResult cancel(WmsOrderOutPlaneDeail wmsOrderOutPlaneDeail) {
        Resp resp = this.wmsOrderOutPlaneDeailService.cancel(wmsOrderOutPlaneDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"combineOrder"})
    public ResponseResult combineOrder(String ids) {
        Resp resp = this.wmsOrderOutPlaneDeailService.combineOrder(ids);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
