package com.penghaisoft.wms.nuohua.controller;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouse;
import com.penghaisoft.wms.nuohua.model.business.WmsInOutTacticsService;
import com.penghaisoft.wms.nuohua.model.entity.WmsInOutTactics;
import com.penghaisoft.wms.nuohua.service.DifferentBusinessNHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 出入库策略
 * @Date 2022-08-22
 **/
@RestController
@RequestMapping({"/wmsInOutTactics"})
public class WmsInOutTacticsController {
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private WmsInOutTacticsService wmsInOutTacticsService;


    @PostMapping({"c"})
    public ResponseResult create(WmsInOutTactics wmsMoveStereoscopic) {
        wmsMoveStereoscopic.setActiveFlag("1");
        wmsMoveStereoscopic.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsMoveStereoscopic.setGmtCreate(new Date());
        this.wmsInOutTacticsService.insert(wmsMoveStereoscopic);
        ResponseResult responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        return responseResult;
    }

    @PostMapping({"d"})
    public ResponseResult delete(WmsInOutTactics wmsMoveStereoscopic) {
       this.wmsInOutTacticsService.delete(wmsMoveStereoscopic);
        ResponseResult responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        return responseResult;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsInOutTactics wmsMoveStereoscopic) {
        wmsMoveStereoscopic.setActiveFlag("1");
        Pager<WmsInOutTactics> resp = this.wmsInOutTacticsService.findListByCondition(page, rows, wmsMoveStereoscopic);
         Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @PostMapping({"update"})
    public ResponseResult update(WmsInOutTactics wmsInOutTactics) {
        WmsInOutTactics wmsInOutTacticsNew = this.wmsInOutTacticsService.findById(wmsInOutTactics.getId().toString());
        wmsInOutTacticsNew.setActiveFlag("1");
        wmsInOutTacticsNew.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsInOutTacticsNew.setGmtModified(new Date());
        this.wmsInOutTacticsService.update(wmsInOutTacticsNew);
        ResponseResult responseResult = new  ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        return responseResult;
    }
    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsInOutTactics wmsInOutTactics = this.wmsInOutTacticsService.findById(id);
        return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, wmsInOutTactics);
    }
}
