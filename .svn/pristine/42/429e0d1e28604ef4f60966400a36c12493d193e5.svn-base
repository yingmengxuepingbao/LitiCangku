//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.contrller;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wms.basicmanagement.model.business.IBasePdaVersionService;
import com.penghaisoft.wms.basicmanagement.model.entity.BasePdaVersion;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/basePdaVersion"})
public class BasePdaVersionController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BasePdaVersionController.class);
    @Autowired
    private IBasePdaVersionService basePdaVersionService;

    public BasePdaVersionController() {
    }

    @PostMapping({"list"})
    public ResponseResult list(@RequestParam(name = "page",defaultValue = "1") int page, @RequestParam(name = "rows",defaultValue = "10") int rows, BasePdaVersion basePdaVersion) {
        basePdaVersion.setActiveFlag("1");
        Pager<BasePdaVersion> result = this.basePdaVersionService.findListByCondition(page, rows, basePdaVersion);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
        return responseResult;
    }

    @PostMapping({"update"})
    public ResponseResult update(BasePdaVersion basePdaVersion) {
        basePdaVersion.setLastModifiedBy(this.getCurrentUserInfo().getAccount());
        basePdaVersion.setGmtModified(new Date());
        this.basePdaVersionService.update(basePdaVersion);
        ResponseResult responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, (Object)null);
        return responseResult;
    }
}
