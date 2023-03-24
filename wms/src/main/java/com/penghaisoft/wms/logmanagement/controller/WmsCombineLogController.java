//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.logmanagement.controller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.logmanagement.model.business.IWmsCombineLogService;
import com.penghaisoft.wms.logmanagement.model.entity.WmsCombineLog;
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
@RequestMapping({"/combineLog"})
public class WmsCombineLogController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsCombineLogController.class);
    @Autowired
    private IWmsCombineLogService wmsCombineLogService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsCombineLogController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsCombineLog wmsCombineLog) {
        wmsCombineLog.setActiveFlag("1");
        wmsCombineLog.setCombineLogId(CommonUtils.getUUID());
        wmsCombineLog.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsCombineLog.setGmtCreate(new Date());
        Resp resp = this.wmsCombineLogService.create(wmsCombineLog);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsCombineLog wmsCombineLog) {
        if (wmsCombineLog.getOrderBy() == null) {
            wmsCombineLog.setOrderBy("gmt_create desc");
        }

        Pager<WmsCombineLog> resp = this.wmsCombineLogService.findListByCondition(page, rows, wmsCombineLog);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsCombineLog wmsCombineLog = this.wmsCombineLogService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsCombineLog);
    }
}
