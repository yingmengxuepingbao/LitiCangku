//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.inboundmanagement.controller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.inboundmanagement.model.business.IWmsOrderCrossInDeailService;
import com.penghaisoft.wms.inboundmanagement.model.entity.WmsOrderCrossInDeail;
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
@RequestMapping({"/orderCrossInDetail"})
public class WmsOrderCrossInDeailController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderCrossInDeailController.class);
    @Autowired
    private IWmsOrderCrossInDeailService wmsOrderCrossInDeailService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsOrderCrossInDeailController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderCrossInDeail wmsOrderCrossInDeail) {
        wmsOrderCrossInDeail.setActiveFlag("1");
        wmsOrderCrossInDeail.setOrderCrossInDetailId(CommonUtils.getUUID());
        wmsOrderCrossInDeail.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderCrossInDeail.setGmtCreate(new Date());
        Resp resp = this.wmsOrderCrossInDeailService.create(wmsOrderCrossInDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsOrderCrossInDeail wmsOrderCrossInDeail) {
        Resp resp = this.wmsOrderCrossInDeailService.delete(wmsOrderCrossInDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsOrderCrossInDeail wmsOrderCrossInDeail) {
        Pager<WmsOrderCrossInDeail> resp = this.wmsOrderCrossInDeailService.findListByCondition(page, rows, wmsOrderCrossInDeail);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsOrderCrossInDeail wmsOrderCrossInDeail = this.wmsOrderCrossInDeailService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderCrossInDeail);
    }

    @GetMapping({"queryByOrderNo/{orderNo}"})
    public ResponseResult queryByOrderNo(@PathVariable String orderNo) {
        List<WmsOrderCrossInDeail> list = this.wmsOrderCrossInDeailService.queryByOrderNo(orderNo);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsOrderCrossInDeail wmsOrderCrossInDeail) {
        Resp resp = this.wmsOrderCrossInDeailService.update(wmsOrderCrossInDeail);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
