//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.expose;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsLocationStereoscopicService;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsPalletService;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/expose/view"})
public class ExposeQXFViewController {
    private static final Logger log = LoggerFactory.getLogger(ExposeQXFViewController.class);
    @Autowired
    private IWmsLocationStereoscopicService wmsLocationStereoscopicService;
    @Autowired
    private IWmsPalletService wmsPalletService;
    @Autowired
    private HttpServletResponse response;

    public ExposeQXFViewController() {
    }

    @ModelAttribute
    public void allowCros() {
        this.response.setHeader("Access-Control-Allow-Origin", "*");
    }

    @GetMapping({"locationOccupyInfo"})
    public ResponseResult getDeviceInfo() {
        WmsLocationStereoscopic cond = new WmsLocationStereoscopic();
        cond.setActiveFlag("1");
        Map<String, String> statusCountData = this.wmsLocationStereoscopicService.groupByUseStatus(cond);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, statusCountData);
        return result;
    }

    @GetMapping({"goodsInfo"})
    public ResponseResult getGoodsInfo() {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setActiveFlag("1");
        wmsPallet.setLockByIsNull("notnull");
        Pager<WmsPallet> pager = this.wmsPalletService.findStereoscopicListHz(1, 3, wmsPallet);
        ResponseResult result = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, pager.getRecords());
        return result;
    }
}
