//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsPlaneLocationService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsPlaneLocation;
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
@RequestMapping({"/planelocation"})
public class WmsPlaneLocationController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsPlaneLocationController.class);
    @Autowired
    private IWmsPlaneLocationService wmsPlaneLocationService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsPlaneLocationController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsPlaneLocation wmsPlaneLocation) {
        wmsPlaneLocation.setActiveFlag("1");
        wmsPlaneLocation.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsPlaneLocation.setGmtCreate(new Date());
        Resp resp = this.wmsPlaneLocationService.create(wmsPlaneLocation);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsPlaneLocation wmsPlaneLocation) {
        Resp resp = this.wmsPlaneLocationService.delete(wmsPlaneLocation);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsPlaneLocation wmsPlaneLocation) {
        Pager<WmsPlaneLocation> resp = this.wmsPlaneLocationService.findListByCondition(page, rows, wmsPlaneLocation);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsPlaneLocation wmsPlaneLocation = this.wmsPlaneLocationService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsPlaneLocation);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsPlaneLocation wmsPlaneLocation) {
        wmsPlaneLocation.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsPlaneLocation.setGmtModified(new Date());
        Resp resp = this.wmsPlaneLocationService.update(wmsPlaneLocation);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
