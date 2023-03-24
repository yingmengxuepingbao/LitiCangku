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
import com.penghaisoft.wms.logmanagement.model.business.IWmsSendLogService;
import com.penghaisoft.wms.logmanagement.model.entity.WmsSendLog;
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
@RequestMapping({"/sendlog"})
public class WmsSendLogController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsSendLogController.class);
    @Autowired
    private IWmsSendLogService wmsSendLogService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsSendLogController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsSendLog wmsSendLog) {
        wmsSendLog.setActiveFlag("1");
        wmsSendLog.setSendId(CommonUtils.getUUID());
        wmsSendLog.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsSendLog.setGmtCreate(new Date());
        Resp resp = this.wmsSendLogService.create(wmsSendLog);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsSendLog wmsSendLog) {
        if (wmsSendLog.getOrderBy() == null) {
            wmsSendLog.setOrderBy("gmt_create desc");
        }

        Pager<WmsSendLog> resp = this.wmsSendLogService.findListByCondition(page, rows, wmsSendLog);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsSendLog wmsSendLog = this.wmsSendLogService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsSendLog);
    }
}
