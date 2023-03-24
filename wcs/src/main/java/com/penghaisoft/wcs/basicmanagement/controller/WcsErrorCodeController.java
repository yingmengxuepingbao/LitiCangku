package com.penghaisoft.wcs.basicmanagement.controller;

import com.penghaisoft.framework.basicdatamanagement.model.business.IBaseDictItemService;
import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.framework.util.controller.BaseController;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsErrorCodeService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/wcsErrorCodeService")
@Slf4j
public class WcsErrorCodeController extends BaseController {

    @Autowired
    private IWcsErrorCodeService wcsErrorCodeService;

    @Autowired
    private IBaseDictItemService baseDictItemService;

    /**
     * 新增记录
     *
     * @param wcsErrorCode
     * @return
     */
    @PostMapping("c")
    public ResponseResult create(WcsErrorCode wcsErrorCode) {
        if (wcsErrorCode.getErrId() != null && !"".equals(wcsErrorCode.getErrId())) {
            wcsErrorCode.setLastModifiedBy(getCurrentUserInfo().getNickname());
            wcsErrorCode.setGmtModified(new Date());
        } else {
            wcsErrorCode.setCreateBy(getCurrentUserInfo().getNickname());
            wcsErrorCode.setGmtCreate(new Date());
        }
        wcsErrorCode.setActiveFlag("1");

        Resp resp = wcsErrorCodeService.create(wcsErrorCode);
        return new ResponseResult(resp.getCode(), resp.getMsg(), null);
    }

    /**
     * 删除记录（逻辑删除active_flag=0)
     *
     * @param wcsErrorCode
     * @return
     */
    @PostMapping("d")
    public ResponseResult delete(WcsErrorCode wcsErrorCode) {
        Resp resp = wcsErrorCodeService.delete(wcsErrorCode);
        return new ResponseResult(resp.getCode(), resp.getMsg(), null);
    }

    /**
     * 查询列表
     *
     * @param pager
     * @return
     */
    @PostMapping("list")
    public ResponseResult list(@RequestParam(name = "page", defaultValue = "1") int page,
                               @RequestParam(name = "rows", defaultValue = "10") int rows, WcsErrorCode wcsErrorCode) {
        wcsErrorCode.setActiveFlag("1");
        Pager<WcsErrorCode> pager = wcsErrorCodeService.findListByCondition(page, rows, wcsErrorCode);
        List<BaseDictItem> baseDictItemList = baseDictItemService.getDictTypeByCode("device_type");
        if (pager.getRecords() != null && !pager.getRecords().isEmpty() && baseDictItemList != null && !baseDictItemList.isEmpty()) {
            for (WcsErrorCode tmp : pager.getRecords()) {
                for (BaseDictItem baseDictItem : baseDictItemList) {
                    if (tmp.getDeviceType().equals(baseDictItem.getDicItemCode())) {
                        tmp.setDeviceType(baseDictItem.getDicItemName());
                    }
                }
            }
        }
        return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, pager);
    }

    /**
     * 查询单条
     *
     * @param id
     * @return
     */
    @GetMapping("r/{id}")
    public ResponseResult queryById(@PathVariable String id) {
        WcsErrorCode wcsErrorCode = wcsErrorCodeService.findById(id);
        return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, wcsErrorCode);
    }

    /**
     * 修改记录
     *
     * @param wcsErrorCode
     * @return
     */
    @PostMapping("u")
    public ResponseResult update(WcsErrorCode wcsErrorCode) {
        Resp resp = wcsErrorCodeService.update(wcsErrorCode);
        return new ResponseResult(resp.getCode(), resp.getMsg(), null);
    }

}
