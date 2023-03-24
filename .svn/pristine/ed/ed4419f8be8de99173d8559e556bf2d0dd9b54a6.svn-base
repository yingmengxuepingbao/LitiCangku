//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsPlatformService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsPlatform;
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
@RequestMapping({"/platform"})
public class WmsPlatformController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(WmsPlatformController.class);
    @Autowired
    private IWmsPlatformService wmsPlatformService;
    @Autowired
    private IUserBusiness userBusiness;

    public WmsPlatformController() {
    }

    @PostMapping({"c"})
    public ResponseResult create(WmsPlatform wmsPlatform) {
        wmsPlatform.setActiveFlag("1");
        wmsPlatform.setPlatformId(CommonUtils.getUUID());
        wmsPlatform.setCreateBy(this.userBusiness.getCurrentUser().getNickname());
        wmsPlatform.setGmtCreate(new Date());
        Resp resp = this.wmsPlatformService.create(wmsPlatform);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"delete"})
    public ResponseResult delete(WmsPlatform wmsPlatform) {
        Resp resp = this.wmsPlatformService.delete(wmsPlatform);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "currentPage",defaultValue = "1") int page, @RequestParam(name = "numberOnePage",defaultValue = "10") int rows, WmsPlatform wmsPlatform) {
        Pager<WmsPlatform> resp = this.wmsPlatformService.findListByCondition(page, rows, wmsPlatform);
        Map<String, Object> dateMap = new HashMap();
        dateMap.put("infoList", resp.getRecords());
        dateMap.put("totalNumber", resp.getTotalCount());
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, dateMap);
        return result;
    }

    @GetMapping({"r/{id}"})
    public ResponseResult queryById(@PathVariable String id) {
        WmsPlatform wmsPlatform = this.wmsPlatformService.findById(id);
        return new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, wmsPlatform);
    }

    @PostMapping({"u"})
    public ResponseResult update(WmsPlatform wmsPlatform) {
        wmsPlatform.setLastModifiedBy(this.userBusiness.getCurrentUser().getNickname());
        wmsPlatform.setGmtModified(new Date());
        Resp resp = this.wmsPlatformService.update(wmsPlatform);
        ResponseResult result = new ResponseResult(resp.getCode(), resp.getMsg(), (Object)null);
        return result;
    }
}
