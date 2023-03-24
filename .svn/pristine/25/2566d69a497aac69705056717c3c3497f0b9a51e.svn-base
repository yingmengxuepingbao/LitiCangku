//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsAddressRealRelaService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressRealRela;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
@RequestMapping({"/wmsAddressRealRela"})
public class WmsAddressRealRelaController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsAddressRealRelaController.class);
    @Autowired
    private IWmsAddressRealRelaService wmsAddressRealRelaService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsAddressRealRelaController() {
    }

    @GetMapping({"getAddressType"})
    public ResponseResult getAddressType(WmsAddressRealRela wmsAddressRealRela) {
        Map<String, String> map = TaskType.getAllTaskType();
        List<WmsAddressRealRela> list = new ArrayList();
        if (map != null) {
            Iterator entries = map.entrySet().iterator();

            while(entries.hasNext()) {
                Entry<String, String> entry = (Entry)entries.next();
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                WmsAddressRealRela ob = new WmsAddressRealRela();
                ob.setAddressType(key);
                ob.setAddressTypeDesc(value);
                list.add(ob);
            }
        }

        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
        return result;
    }

    @GetMapping({"getStereoscopicOutAddress"})
    public ResponseResult getStereoscopicOutAddress() {
        List<String> addressTypeList = new ArrayList();
        addressTypeList.add(String.valueOf(TaskType.STRAIGHT_OUT.getTaskType()));
        addressTypeList.add(String.valueOf(TaskType.SORT_OUT.getTaskType()));
        WmsAddressRealRela wmsAddressRealRela = new WmsAddressRealRela();
        wmsAddressRealRela.setAddressTypeList(addressTypeList);
        List<WmsAddressRealRela> list = this.wmsAddressRealRelaService.queryOutAddress(wmsAddressRealRela);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
        return result;
    }

    /**
     *功能描述: 获取入库口
     * @author zhangxin
     * @date 2022/8/1
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @GetMapping({"getHBStereoscopicOutAddress"})
    public ResponseResult getHBStereoscopicOutAddress() {
        List<String> addressTypeList = new ArrayList();
        //直发出库
        addressTypeList.add(String.valueOf(TaskType.STRAIGHT_OUT.getTaskType()));
        WmsAddressRealRela wmsAddressRealRela = new WmsAddressRealRela();
        wmsAddressRealRela.setAddressTypeList(addressTypeList);
        List<WmsAddressRealRela> list = this.wmsAddressRealRelaService.queryHBOutAddress(wmsAddressRealRela);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
        return result;
    }

    @GetMapping({"getCheckOutAddress"})
    public ResponseResult getCheckOutAddress() {
        List<String> addressTypeList = new ArrayList();
        addressTypeList.add(String.valueOf(TaskType.CHECK_OUT.getTaskType()));
        WmsAddressRealRela wmsAddressRealRela = new WmsAddressRealRela();
        wmsAddressRealRela.setAddressTypeList(addressTypeList);
        List<WmsAddressRealRela> list = this.wmsAddressRealRelaService.queryOutAddress(wmsAddressRealRela);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
        return result;
    }

    @GetMapping({"getStereoscopicOutAddressAll"})
    public ResponseResult getStereoscopicOutAddressAll() {
        List<String> addressTypeList = new ArrayList();
        addressTypeList.add(String.valueOf(TaskType.STRAIGHT_OUT.getTaskType()));
        addressTypeList.add(String.valueOf(TaskType.SORT_OUT.getTaskType()));
        addressTypeList.add(String.valueOf(TaskType.HAND_OUT.getTaskType()));
        addressTypeList.add(String.valueOf(TaskType.VIRTUAL_PALLET_OUT.getTaskType()));
        WmsAddressRealRela wmsAddressRealRela = new WmsAddressRealRela();
        wmsAddressRealRela.setAddressTypeList(addressTypeList);
        List<WmsAddressRealRela> list = this.wmsAddressRealRelaService.queryOutAddress(wmsAddressRealRela);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, list);
        return result;
    }

    @PostMapping({"create/edit"})
    public ResponseResult createEdit(WmsAddressRealRela wmsAddressRealRela) {
        wmsAddressRealRela.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsAddressRealRela.setGmtCreate(new Date());
        wmsAddressRealRela.setActiveFlag("1");
        Resp resp = this.wmsAddressRealRelaService.create(wmsAddressRealRela);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"d"})
    public ResponseResult delete(WmsAddressRealRela wmsAddressRealRela) {
        wmsAddressRealRela.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsAddressRealRela.setGmtModified(new Date());
        Resp resp = this.wmsAddressRealRelaService.delete(wmsAddressRealRela);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsAddressRealRela wmsAddressRealRela) {
        wmsAddressRealRela.setActiveFlag("1");
        Pager<WmsAddressRealRela> pager = this.wmsAddressRealRelaService.findListByCondition(page, rows, wmsAddressRealRela);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsAddressRealRela wmsAddressRealRela = this.wmsAddressRealRelaService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsAddressRealRela);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsAddressRealRela wmsAddressRealRela) {
        Resp resp = this.wmsAddressRealRelaService.update(wmsAddressRealRela);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
