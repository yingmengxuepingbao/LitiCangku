package com.penghaisoft.wcs.jobmanagement.controller;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.jobmanagement.model.business.IWcsJobExecutionSummaryService;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 定时作业执行汇总表 查询
 * @ClassName JobManagementSummaryController
 * @Author luot
 * @Date 2020/3/19 17:38
 **/
@RestController
@RequestMapping(value = "/wcsJobExecutionSummaryService")
@Slf4j
public class WcsJobExecutionSummaryController {

    @Autowired
    private IWcsJobExecutionSummaryService wcsJobExecutionSummaryService;

    /**
     * @return
     * @Description 查询列表
     * @Author luot
     * @Date 2020/3/19 17:38
     * @Param
     **/
    @PostMapping("list")
    public ResponseResult list(@RequestParam(name = "page", defaultValue = "1") int page,
                               @RequestParam(name = "rows", defaultValue = "10") int rows, WcsJobExecutionSummary wcsJobExecutionSummary) {
        Pager<WcsJobExecutionSummary> pager = wcsJobExecutionSummaryService.findListByCondition(page, rows, wcsJobExecutionSummary);
        return new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, pager);
    }
}
