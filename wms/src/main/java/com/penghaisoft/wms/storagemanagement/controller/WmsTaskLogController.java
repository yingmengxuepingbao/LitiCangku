//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.controller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsTaskExecutionLogService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;

import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/taskLog"})
public class WmsTaskLogController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsTaskLogController.class);
    @Autowired
    private IWmsTaskExecutionLogService wmsTaskExecutionLogService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsTaskLogController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsTaskExecutionLog wmsTaskExecutionLog) {
        wmsTaskExecutionLog.setActiveFlag("1");
        wmsTaskExecutionLog.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsTaskExecutionLog.setGmtCreate(new Date());
        Resp resp = this.wmsTaskExecutionLogService.create(wmsTaskExecutionLog);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsTaskExecutionLog wmsTaskExecutionLog) {
        Resp resp = this.wmsTaskExecutionLogService.delete(wmsTaskExecutionLog);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
    //现场添加-物理删除
    @PostMapping({"deleteWuli"})
    public ResponseResult deleteWuli(WmsTaskExecutionLog wmsTaskExecutionLog) {
        Resp resp = this.wmsTaskExecutionLogService.deleteWuli(wmsTaskExecutionLog);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsTaskExecutionLog wmsTaskExecutionLog) {
        if (wmsTaskExecutionLog.getOrderBy() == null) {
            wmsTaskExecutionLog.setOrderBy("gmt_create desc");
        }
        Pager<WmsTaskExecutionLog> resp = this.wmsTaskExecutionLogService.findListByCondition(page, rows, wmsTaskExecutionLog);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsTaskExecutionLog wmsTaskExecutionLog = this.wmsTaskExecutionLogService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsTaskExecutionLog);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsTaskExecutionLog wmsTaskExecutionLog) {
        Resp resp = this.wmsTaskExecutionLogService.update(wmsTaskExecutionLog);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"listCount"})
    public ResponseResult listCount(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsTaskExecutionLog wmsTaskExecutionLog) {
        /*if (wmsTaskExecutionLog.getGmtCreateMax() == null && wmsTaskExecutionLog.getGmtCreateMin() == null) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(calendar1.get(1), calendar1.get(2), calendar1.get(5), 0, 0, 0);
            Date beginOfDate = calendar1.getTime();
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginOfDate));
            wmsTaskExecutionLog.setGmtCreateMin1(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginOfDate));
            Calendar calendar2 = Calendar.getInstance();
            calendar1.set(calendar2.get(1), calendar2.get(2), calendar2.get(5), 23, 59, 59);
            Date endOfDate = calendar1.getTime();
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endOfDate));
            wmsTaskExecutionLog.setGmtCreateMax1(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endOfDate));
        }*/
        String  beginOfDate ="" ;
        String  endOfDate ="" ;
        if(wmsTaskExecutionLog.getGmtCreateMax()!=null &&!"".equals(wmsTaskExecutionLog.getGmtCreateMax())){
            beginOfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(wmsTaskExecutionLog.getGmtCreateMax());
        }
        if(wmsTaskExecutionLog.getGmtCreateMin()!=null &&!"".equals(wmsTaskExecutionLog.getGmtCreateMin())){
            endOfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(wmsTaskExecutionLog.getGmtCreateMin());
        }
        wmsTaskExecutionLog.setGmtCreateMin1(beginOfDate);
        wmsTaskExecutionLog.setGmtCreateMax1(endOfDate);
        Pager<WmsTaskExecutionLog> resp = this.wmsTaskExecutionLogService.countInAndOut(page, rows, wmsTaskExecutionLog);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalCount", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

}
