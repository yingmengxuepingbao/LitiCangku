//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.RecommendLocationEnum;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IDifferentBusinessService;
import com.penghaisoft.wms.basicmanagement.model.business.impl.DifferentBusinessFactory;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsMoveTask;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsMoveStereoscopicService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsMoveStereoscopic;
import java.util.Date;
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
@RequestMapping({"/wmsMoveStereoscopic"})
public class WmsMoveStereoscopicController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsMoveStereoscopicController.class);
    @Autowired
    private IWmsMoveStereoscopicService wmsMoveStereoscopicService;
    @Autowired
    private DifferentBusinessFactory differentBusinessFactory;
    @Autowired
    private IUserBusiness userBusiness;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${notice.other-sys-addr.pallet-move}")
    private String noticePalletMoveAddr;
    @Value("${applyfactory}")
    private String applyfactory;

    public WmsMoveStereoscopicController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsMoveStereoscopic wmsMoveStereoscopic) {
        Resp resp = this.wmsMoveStereoscopicService.create(wmsMoveStereoscopic);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, resp);
        return responseResult;
    }

    @PostMapping({"d"})
    public ResponseResult delete(WmsMoveStereoscopic wmsMoveStereoscopic) {
        wmsMoveStereoscopic.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsMoveStereoscopic.setGmtModified(new Date());
        Resp resp = this.wmsMoveStereoscopicService.delete(wmsMoveStereoscopic);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, resp);
        return responseResult;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, WmsMoveStereoscopic wmsMoveStereoscopic) {
        wmsMoveStereoscopic.setActiveFlag("1");
        Pager<WmsMoveStereoscopic> result = this.wmsMoveStereoscopicService.findListByCondition(page, rows, wmsMoveStereoscopic);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }

    @PostMapping({"create/edit"})
    public ResponseResult createOrEditYkTask(WmsMoveStereoscopic wmsMoveStereoscopic) {
        wmsMoveStereoscopic.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        IDifferentBusinessService differentBusinessService = this.differentBusinessFactory.getIDifferentBusinessService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
        Resp resp = differentBusinessService.createOrEditYk(wmsMoveStereoscopic);
        ResponseResult responseResult = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return responseResult;
    }

    @PostMapping({"start"})
    public ResponseResult startYkTask(WmsMoveStereoscopic wmsMoveStereoscopic) {
        User currentUser = this.userBusiness.getCurrentUser();
        String loginName = currentUser.getNickname();
        String loginNameWithAccount = currentUser.getNickname() + "(" + currentUser.getAccount() + ")";
        wmsMoveStereoscopic.setCreateBy(loginName);
        IDifferentBusinessService differentBusinessService = this.differentBusinessFactory.getIDifferentBusinessService(RecommendLocationEnum.getServiceNameByCode(this.applyfactory));
        Resp resp = differentBusinessService.locationLockYk(wmsMoveStereoscopic);
        ResponseResult result;
        if (RESULT.FAILED.code.equals(resp.getCode())) {
            result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
            return result;
        } else {
            WmsMoveStereoscopic wmsMoveStereoscopicNew = (WmsMoveStereoscopic)resp.getData();
            long taskId = this.wmsCommonService.getTaskIds(TaskType.NORMAL_MOVE, 1)[0];
            wmsMoveStereoscopicNew.setTaskId(taskId);
            WmsMoveTask wmsMoveTask = new WmsMoveTask();
            wmsMoveTask.setTaskId(taskId);
            wmsMoveTask.setTaskType(String.valueOf(TaskType.NORMAL_MOVE));
            wmsMoveTask.setFromLocation(wmsMoveStereoscopicNew.getFromLocationCode());
            wmsMoveTask.setTargetLocation(wmsMoveStereoscopicNew.getToLocationCode());
            wmsMoveTask.setPalletCode(wmsMoveStereoscopicNew.getPalletCode());
            wmsMoveTask.setOperator(loginNameWithAccount);

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<WmsMoveTask> request = new HttpEntity(wmsMoveTask, headers);
                ResponseEntity<JSONObject> wcsResp = this.restTemplate.postForEntity(this.noticePalletMoveAddr, request, JSONObject.class, new Object[0]);
                if (wcsResp.getStatusCodeValue() != 200) {
                    log.error("调wcs接口失败！");
                    differentBusinessService.reverseYk(wmsMoveStereoscopicNew);
                    return new ResponseResult(RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
                }

                JSONObject noticeResult = (JSONObject)wcsResp.getBody();
                if (!noticeResult.getString("code").equals("1")) {
                    log.error("调wcs接口失败：" + noticeResult.getString("message"));
                    differentBusinessService.reverseYk(wmsMoveStereoscopicNew);
                    return new ResponseResult(RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
                }

                log.info("调wcs接口成功！");
                this.wmsMoveStereoscopicService.startYkTask(wmsMoveStereoscopicNew);
            } catch (Exception var16) {
                differentBusinessService.reverseYk(wmsMoveStereoscopicNew);
                return new ResponseResult(RESULT.FAILED.code, "移库单号(" + wmsMoveStereoscopicNew.getMoveNo() + ")启动失败，调用WCS出库接口失败;", (Object)null);
            }

            result = new ResponseResult(RESULT.SUCCESS.code, "WCS启动成功", (Object)null);
            return result;
        }
    }
}
