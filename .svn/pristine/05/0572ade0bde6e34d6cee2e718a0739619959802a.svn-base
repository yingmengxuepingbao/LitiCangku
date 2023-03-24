package com.penghaisoft.wcs.basicmanagement.controller;

import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/wcsErrorLogService")
@Slf4j
public class WcsErrorLogController extends BaseController {

    @Autowired
    private IWcsErrorLogService wcsErrorLogService;

    @Autowired
    private IBaseDictItemService baseDictItemService;

    /**
     * 查询列表
     *
     * @param pager
     * @return
     */
    @PostMapping("list")
    public ResponseResult list(@RequestParam(name = "page", defaultValue = "1") int page,
                               @RequestParam(name = "rows", defaultValue = "10") int rows, WcsErrorLog wcsErrorLog) {
        Pager<WcsErrorLog> pager = wcsErrorLogService.findListByCondition(page, rows, wcsErrorLog);

        List<BaseDictItem> baseDictItemList = baseDictItemService.getDictTypeByCode("device_type");
        if (pager.getRecords() != null && !pager.getRecords().isEmpty() && baseDictItemList != null && !baseDictItemList.isEmpty()) {
            for (WcsErrorLog tmp : pager.getRecords()) {
                for (BaseDictItem baseDictItem : baseDictItemList) {
                    if (tmp.getDeviceType().equals(baseDictItem.getDicItemCode())) {
                        tmp.setDeviceType(baseDictItem.getDicItemName());
                    }
                }
            }
        }

        baseDictItemList = baseDictItemService.getDictTypeByCode("err_type");
        if (pager.getRecords() != null && !pager.getRecords().isEmpty() && baseDictItemList != null && !baseDictItemList.isEmpty()) {
            for (WcsErrorLog tmp : pager.getRecords()) {
                for (BaseDictItem baseDictItem : baseDictItemList) {
                    if (tmp.getErrType().equals(baseDictItem.getDicItemCode())) {
                        tmp.setErrType(baseDictItem.getDicItemName());
                    }
                }
            }
        }

        baseDictItemList = baseDictItemService.getDictTypeByCode("level");
        if (pager.getRecords() != null && !pager.getRecords().isEmpty() && baseDictItemList != null && !baseDictItemList.isEmpty()) {
            for (WcsErrorLog tmp : pager.getRecords()) {
                for (BaseDictItem baseDictItem : baseDictItemList) {
                    if (String.valueOf(tmp.getLevel()).equals(baseDictItem.getDicItemCode())) {
                        tmp.setLevelStr(baseDictItem.getDicItemName());
                    }
                }
            }
        }
        return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, pager);
    }
}
