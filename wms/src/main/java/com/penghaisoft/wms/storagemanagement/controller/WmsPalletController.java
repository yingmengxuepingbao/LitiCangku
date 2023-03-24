//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.controller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsPalletService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/pallet"})
public class WmsPalletController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsPalletController.class);
    @Autowired
    private IWmsPalletService wmsPalletService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsPalletController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(@RequestBody WmsPallet wmsPallet) {
        wmsPallet.setActiveFlag("1");
        wmsPallet.setPalletId(CommonUtils.getUUID());
        wmsPallet.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsPallet.setGmtCreate(new Date());
        wmsPallet.setWarehouseCode("NH_WAREHOUSE");
        wmsPallet.setAreaCode("L-NH01");
        Resp resp = this.wmsPalletService.create(wmsPallet);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsPallet wmsPallet) {
        Resp resp = this.wmsPalletService.delete(wmsPallet);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsPallet wmsPallet) {
        Pager<WmsPallet> resp = this.wmsPalletService.findListByCondition(page, rows, wmsPallet);
        if (resp.getRecords().size() > 0) {
            Iterator var5 = resp.getRecords().iterator();

            while(var5.hasNext()) {
                WmsPallet pallet = (WmsPallet)var5.next();
                if (pallet.getHasBoxCode() != null) {
                    if (pallet.getHasBoxCode().equals("1")) {
                        pallet.setHasBoxCode("有");
                    } else if (pallet.getHasBoxCode().equals("0")) {
                        pallet.setHasBoxCode("无");
                    }
                }
            }
        }

        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @PostMapping({"list1"})
    public ResponseResult listInfo(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsPallet wmsPallet) {
        Pager<WmsPallet> pager = this.wmsPalletService.findListByCondition(page, rows, wmsPallet);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsPallet wmsPallet = this.wmsPalletService.findById(id);
        return wmsPallet != null && wmsPallet.getGoodsCode() != null && !wmsPallet.getGoodsCode().equals("") ? new ResponseResult(RESULT.FAILED.code, "该托盘已经绑定了商品", (Object)null) : new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsPallet);
    }

    @PostMapping({"u"})
    public ResponseResult update(@RequestBody WmsPallet wmsPallet) {
        wmsPallet.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsPallet.setGmtModified(new Date());
        Resp resp = this.wmsPalletService.update(wmsPallet);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @GetMapping({"queryByLocationCode/{locationCode}"})
    public ResponseResult queryByLocationCode(@PathVariable String locationCode) {
        List<WmsPallet> list = this.wmsPalletService.queryByLocationCode(locationCode);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
    }

    @PostMapping({"/unshelves/list"})
    public ResponseResult unshelvesList(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsPallet wmsPallet) {
        wmsPallet.setActiveFlag("1");
        wmsPallet.setLocationCodeIsNull("null");
        wmsPallet.setGoodsCodeIsNotNull("notnull");
        Pager<WmsPallet> pager = this.wmsPalletService.findListByCondition(page, rows, wmsPallet);
        if (pager.getRecords().size() > 0) {
            Iterator var5 = pager.getRecords().iterator();

            while(var5.hasNext()) {
                WmsPallet pallet = (WmsPallet)var5.next();
                if (pallet.getHasBoxCode() != null) {
                    if (pallet.getHasBoxCode().equals("1")) {
                        pallet.setHasBoxCode("有");
                    } else if (pallet.getHasBoxCode().equals("0")) {
                        pallet.setHasBoxCode("无");
                    }
                }
            }
        }

        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager);
        return result;
    }

    @PostMapping({"/stereoscopic/list"})
    public ResponseResult stereoscopicList(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsPallet wmsPallet) {
        wmsPallet.setActiveFlag("1");
        wmsPallet.setLockByIsNull("notnull");
        Pager<WmsPallet> pager = this.wmsPalletService.findStereoscopicList(page, rows, wmsPallet);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager);
        return result;
    }

    @PostMapping({"/stereoscopic/list/hz"})
    public ResponseResult stereoscopicListHz(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsPallet wmsPallet) {
        wmsPallet.setActiveFlag("1");
        wmsPallet.setLockByIsNull("notnull");
        Pager<WmsPallet> pager = this.wmsPalletService.findStereoscopicListHz(page, rows, wmsPallet);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager);
        return result;
    }

    @PostMapping({"relieve"})
    public ResponseResult relieve(WmsPallet wmsPallet) {
        wmsPallet.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsPallet.setGmtModified(new Date());
        Resp resp = this.wmsPalletService.relieve(wmsPallet);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
