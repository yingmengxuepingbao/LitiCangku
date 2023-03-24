//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.RecommendLocationEnum;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IDifferentBusinessService;
import com.penghaisoft.wms.basicmanagement.model.business.impl.DifferentBusinessFactory;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCheckPalletBoxBarcodeService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCheckPalletService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCheckService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheck;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheckPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheckPalletBoxBarcode;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping({"/wmsOrderCheck"})
public class WmsOrderCheckController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsOrderCheckController.class);
    @Autowired
    private IWmsOrderCheckService wmsOrderCheckService;
    @Autowired
    private IWmsOrderCheckPalletService wmsOrderCheckPalletService;
    @Autowired
    private IWmsOrderCheckPalletBoxBarcodeService wmsOrderCheckPalletBoxBarcodeService;
    @Autowired
    private DifferentBusinessFactory differentBusinessFactory;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${notice.other-sys-addr.pallet-out}")
    private String noticePalletOutAddr;
    @Value("${applyfactory}")
    private String applyfactory;

    public WmsOrderCheckController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsOrderCheck wmsOrderCheck) {
        wmsOrderCheck.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        Resp resp = this.wmsOrderCheckService.create(wmsOrderCheck);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, "创建成功！", resp);
        return responseResult;
    }

    @PostMapping({"start/check"})
    public ResponseResult startCheck(WmsOrderCheck wmsOrderCheck) {
        WmsOrderCheck ob = new WmsOrderCheck();
        ob.setOrderNo(wmsOrderCheck.getOrderNo());
        ob.setActiveFlag("1");
        List<WmsOrderCheck> list = this.wmsOrderCheckService.queryByAny(ob);
        if (list != null && !list.isEmpty()) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                WmsOrderCheck tmp = (WmsOrderCheck)var4.next();
                if (tmp.getAreaType() != null && !"1".equals(tmp.getAreaType())) {
                    return new ResponseResult(RESULT.FAILED.code, "当前盘点单非立库盘点，不能启动！", (Object)null);
                }

                if (tmp.getOrderStatus() != null && !"1".equals(tmp.getOrderStatus())) {
                    return new ResponseResult(RESULT.FAILED.code, "当前盘点单包含状态非创建的记录，不能启动！", (Object)null);
                }
            }
        }

        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
    }

    @PostMapping({"start"})
    public ResponseResult start(WmsOrderCheck wmsOrderCheck) {
        new LinkedList();
        User currentUser = this.userBusiness.getCurrentUser();
        String loginName = currentUser.getNickname();
        String loginNameWithAccount = currentUser.getNickname() + "(" + currentUser.getAccount() + ")";
        wmsOrderCheck.setCreateBy(loginNameWithAccount);
        IDifferentBusinessService differentBusinessService = this.differentBusinessFactory.getIDifferentBusinessService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
        Resp resp = differentBusinessService.lockCheckLocationCode(wmsOrderCheck);
        if (resp != null && RESULT.FAILED.code.equals(resp.getCode())) {
            return new ResponseResult(RESULT.FAILED.code, "立库盘点启动失败：" + resp.getMsg(), resp);
        } else {
            List<WmsOutTask> wmsOutTaskList = (List)resp.getData();
            if (wmsOutTaskList != null && !wmsOutTaskList.isEmpty()) {
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<List<WmsOutTask>> request = new HttpEntity(wmsOutTaskList, headers);
                    ResponseEntity<JSONObject> wcsResp = this.restTemplate.postForEntity(this.noticePalletOutAddr, request, JSONObject.class, new Object[0]);
                    if (wcsResp.getStatusCodeValue() != 200) {
                        log.error("调wcs接口失败！");
                        differentBusinessService.revertOut((List)null, wmsOutTaskList, (String)null);
                        return new ResponseResult(RESULT.FAILED.code, "锁定盘点库位成功，调用WCS出库接口失败！", (Object)null);
                    }

                    JSONObject noticeResult = (JSONObject)wcsResp.getBody();
                    if (!noticeResult.getString("code").equals("1")) {
                        log.error("调wcs接口失败：" + noticeResult.getString("message"));
                        differentBusinessService.revertOut((List)null, wmsOutTaskList, (String)null);
                        return new ResponseResult(RESULT.FAILED.code, "锁定盘点库位成功，调用WCS出库接口失败:" + noticeResult.getString("message"), (Object)null);
                    }

                    log.info("调wcs接口成功！");
                    this.wmsOrderCheckService.updateBusiness(wmsOutTaskList, resp.getMsg(), wmsOrderCheck.getOrderNo(), loginName);
                } catch (Exception var12) {
                    differentBusinessService.revertOut((List)null, wmsOutTaskList, (String)null);
                    return new ResponseResult(RESULT.FAILED.code, "锁定盘点库位成功，调用WCS出库接口失败！", (Object)null);
                }

                return new ResponseResult(RESULT.SUCCESS.code, "立库盘点启动成功！", resp);
            } else {
                return new ResponseResult(RESULT.FAILED.code, "立库盘点启动失败： 没有待盘点的托盘需要出库！", resp);
            }
        }
    }

    @PostMapping({"deleteByCheckId"})
    public ResponseResult deleteByCheckId(WmsOrderCheck wmsOrderCheck) {
        Resp resp = this.wmsOrderCheckService.deleteByCheckId(wmsOrderCheck);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, resp);
        return responseResult;
    }

    @PostMapping({"deleteByOrderNo"})
    public ResponseResult deleteByOrderNo(WmsOrderCheck wmsOrderCheck) {
        Resp resp = this.wmsOrderCheckService.deleteByOrderNo(wmsOrderCheck);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, resp);
        return responseResult;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsOrderCheck wmsOrderCheck) {
        wmsOrderCheck.setActiveFlag("1");
        Pager<WmsOrderCheck> result = this.wmsOrderCheckService.findListByCondition(page, rows, wmsOrderCheck);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }

    @PostMapping({"pallet/list"})
    public ResponseResult palletList(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsOrderCheckPallet wmsOrderCheckPallet) {
        wmsOrderCheckPallet.setActiveFlag("1");
        Pager<WmsOrderCheckPallet> result = this.wmsOrderCheckPalletService.findListByCondition(page, rows, wmsOrderCheckPallet);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }

    @PostMapping({"pallet/boxBarcode/list"})
    public ResponseResult palletBoxBarcodeList(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsOrderCheckPalletBoxBarcode wmsOrderCheckPalletBoxBarcode) {
        wmsOrderCheckPalletBoxBarcode.setActiveFlag("1");
        Pager<WmsOrderCheckPalletBoxBarcode> result = this.wmsOrderCheckPalletBoxBarcodeService.findListByCondition(page, rows, wmsOrderCheckPalletBoxBarcode);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsOrderCheck wmsOrderCheck = this.wmsOrderCheckService.findById(id);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsOrderCheck);
        return responseResult;
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsOrderCheck wmsOrderCheck) {
        Resp resp = this.wmsOrderCheckService.update(wmsOrderCheck);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, resp);
        return responseResult;
    }
}
