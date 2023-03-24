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
import com.penghaisoft.wms.logmanagement.model.business.IWmsMoveLogService;
import com.penghaisoft.wms.logmanagement.model.entity.WmsMoveLog;
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
@RequestMapping({"/moveLog"})
public class WmsMoveLogController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsMoveLogController.class);
    @Autowired
    private IWmsMoveLogService wmsMoveLogService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsMoveLogController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsMoveLog wmsMoveLog) {
        wmsMoveLog.setActiveFlag("1");
        wmsMoveLog.setMoveLogId(CommonUtils.getUUID());
        wmsMoveLog.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsMoveLog.setGmtCreate(new Date());
        Resp resp = this.wmsMoveLogService.create(wmsMoveLog);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsMoveLog wmsMoveLog) {
        if (wmsMoveLog.getOrderBy() == null) {
            wmsMoveLog.setOrderBy("gmt_create desc");
        }

        Pager<WmsMoveLog> resp = this.wmsMoveLogService.findListByCondition(page, rows, wmsMoveLog);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsMoveLog wmsMoveLog = this.wmsMoveLogService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsMoveLog);
    }
}
