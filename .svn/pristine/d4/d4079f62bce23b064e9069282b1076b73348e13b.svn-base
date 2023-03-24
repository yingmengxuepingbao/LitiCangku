//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.controller;

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
import com.penghaisoft.wms.basicmanagement.model.business.IWmsLocationStereoscopicService;
import com.penghaisoft.wms.basicmanagement.model.business.impl.DifferentBusinessFactory;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping({"/wmsPalletOutStereoscopic"})
public class WmsPalletOutStereoscopicController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsPalletOutStereoscopicController.class);
    @Autowired
    private DifferentBusinessFactory differentBusinessFactory;
    @Autowired
    private IWmsLocationStereoscopicService wmsLocationStereoscopicService;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${notice.other-sys-addr.pallet-out}")
    private String noticePalletOutAddr;
    @Value("${applyfactory}")
    private String applyfactory;

    public WmsPalletOutStereoscopicController() {
    }

    @PostMapping({"list"})
    public ResponseResult batchStart(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsLocationStereoscopic wmsLocationStereoscopic) {
        Pager<WmsLocationStereoscopic> pager = this.wmsLocationStereoscopicService.findListByCondition(page, rows, wmsLocationStereoscopic);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager);
        return result;
    }

    @PostMapping({"out/start"})
    public ResponseResult batchStart(WmsLocationStereoscopic wmsLocationStereoscopic) {
        User currentUser = this.userBusiness.getCurrentUser();
        String loginName = currentUser.getNickname();
        String loginNameWithAccount = currentUser.getNickname() + "(" + currentUser.getAccount() + ")";
        IDifferentBusinessService differentBusinessService = this.differentBusinessFactory.getIDifferentBusinessService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
        Resp resp = differentBusinessService.palletOutLocationCheck(wmsLocationStereoscopic.getLocationCode(), loginNameWithAccount, wmsLocationStereoscopic.getOutAddress());
        if (RESULT.FAILED.code.equals(resp.getCode())) {
            return new ResponseResult(RESULT.FAILED.code, "启动失败，" + resp.getMsg(), (Object)null);
        } else {
            List wmsOutTaskList = (List)resp.getData();

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<List<WmsOutTask>> request = new HttpEntity(wmsOutTaskList, headers);
                ResponseEntity<JSONObject> wcsResp = this.restTemplate.postForEntity(this.noticePalletOutAddr, request, JSONObject.class, new Object[0]);
                if (wcsResp.getStatusCodeValue() != 200) {
                    log.error("调wcs接口失败！");
                    differentBusinessService.revertOut((List)null, wmsOutTaskList, (String)null);
                    return new ResponseResult(RESULT.FAILED.code, "启动失败，调用WCS出库接口失败", (Object)null);
                }

                JSONObject noticeResult = (JSONObject)wcsResp.getBody();
                if (!noticeResult.getString("code").equals("1")) {
                    log.error("调wcs接口失败：" + noticeResult.getString("message"));
                    differentBusinessService.revertOut((List)null, wmsOutTaskList, (String)null);
                    return new ResponseResult(RESULT.FAILED.code, "启动失败，调用WCS出库接口失败", (Object)null);
                }

                log.info("调wcs接口成功！");
                differentBusinessService.startPalletOut(wmsOutTaskList, loginName);
            } catch (Exception var13) {
                var13.printStackTrace();
                differentBusinessService.revertOut((List)null, wmsOutTaskList, (String)null);
                return new ResponseResult(RESULT.FAILED.code, "启动失败，调用WCS出库接口失败", (Object)null);
            }

            ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, "启动成功", (Object)null);
            return result;
        }
    }
}
