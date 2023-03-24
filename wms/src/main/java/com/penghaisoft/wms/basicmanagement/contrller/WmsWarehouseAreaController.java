//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsWarehouseAreaService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouseArea;
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
@RequestMapping({"/area"})
public class WmsWarehouseAreaController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsWarehouseAreaController.class);
    @Autowired
    private IWmsWarehouseAreaService wmsWarehouseAreaService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsWarehouseAreaController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsWarehouseArea wmsWarehouseArea) {
        wmsWarehouseArea.setActiveFlag("1");
        wmsWarehouseArea.setAreaId(CommonUtils.getUUID());
        wmsWarehouseArea.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsWarehouseArea.setGmtCreate(new Date());
        Resp resp = this.wmsWarehouseAreaService.create(wmsWarehouseArea);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsWarehouseArea wmsWarehouseArea) {
        Resp resp = this.wmsWarehouseAreaService.delete(wmsWarehouseArea);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsWarehouseArea wmsWarehouseArea) {
        Pager<WmsWarehouseArea> resp = this.wmsWarehouseAreaService.findListByCondition(page, rows, wmsWarehouseArea);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsWarehouseArea wmsWarehouseArea = this.wmsWarehouseAreaService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsWarehouseArea);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsWarehouseArea wmsWarehouseArea) {
        wmsWarehouseArea.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsWarehouseArea.setGmtModified(new Date());
        Resp resp = this.wmsWarehouseAreaService.update(wmsWarehouseArea);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @GetMapping({"getAreaCodeAll"})
    public ResponseResult getAreaCodeAll() {
        List<BaseDictItem> baseDictItemList = this.wmsWarehouseAreaService.getAreaCodeAll();
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, baseDictItemList);
    }

    @PostMapping({"findareacode"})
    public ResponseResult findAreaCode(WmsWarehouseArea wmsWarehouseArea) {
        List<BaseDictItem> baseDictItemList = this.wmsWarehouseAreaService.findAreaCode(wmsWarehouseArea);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, baseDictItemList);
    }
}
