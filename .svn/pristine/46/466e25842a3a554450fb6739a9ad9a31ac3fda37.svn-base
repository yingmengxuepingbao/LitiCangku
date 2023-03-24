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
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.inboundmanagement.model.business.IWmsOrderCrossInService;
import com.penghaisoft.wms.inboundmanagement.model.entity.WmsOrderCrossIn;
import java.util.Date;
import java.util.HashMap;
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
@RequestMapping({"/orderCrossIn"})
public class WmsOrderCrossInController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderCrossInController.class);
    @Autowired
    private IWmsOrderCrossInService wmsOrderCrossInService;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private IWmsCommonService wmsCommonService;

    public WmsOrderCrossInController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderCrossIn wmsOrderCrossIn) {
        wmsOrderCrossIn.setActiveFlag("1");
        wmsOrderCrossIn.setOrderCrossInId(CommonUtils.getUUID());
        wmsOrderCrossIn.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsOrderCrossIn.setGmtCreate(new Date());
        String orderNo = this.wmsCommonService.getOrderNoByType("IC");
        wmsOrderCrossIn.setOrderNo(orderNo);
        Resp resp = this.wmsOrderCrossInService.create(wmsOrderCrossIn);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsOrderCrossIn wmsOrderCrossIn) {
        Resp resp = this.wmsOrderCrossInService.delete(wmsOrderCrossIn);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsOrderCrossIn wmsOrderCrossIn) {
        if (wmsOrderCrossIn.getOrderBy() == null) {
            wmsOrderCrossIn.setOrderBy("gmt_create desc");
        }

        Pager<WmsOrderCrossIn> resp = this.wmsOrderCrossInService.findListByCondition(page, rows, wmsOrderCrossIn);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsOrderCrossIn wmsOrderCrossIn = this.wmsOrderCrossInService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderCrossIn);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsOrderCrossIn wmsOrderCrossIn) {
        Resp resp = this.wmsOrderCrossInService.update(wmsOrderCrossIn);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
