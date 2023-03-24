package com.penghaisoft.wcs.expose.controller;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.monitormanagement.model.business.IDynamicViewService;
import com.penghaisoft.wcs.monitormanagement.model.business.IWcsErrorLogService;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 立库动态视图服务--巧媳妇
 * @ClassName ExposeQXFViewController
 * @Author luot
 * @Date 2020/7/30 17:16
 **/
@RestController
@RequestMapping(value = "/expose/view")
@Slf4j
public class ExposeQXFViewController {

    @Value("${wcs.config.ngx-path}")
    private String ngxPath;

    @Autowired
    private IDynamicViewService dynamicViewService;

    @Autowired
    private IWcsErrorLogService errorLogService;

    @Autowired
    private HttpServletResponse response;

    /**
     * 允许跨域
     */
    @ModelAttribute
    public void allowCros() {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    /**
     * 获取用于展示图片的ngxPath
     *
     * @return
     */
    @GetMapping("ngxpath")
    public ResponseResult getNgxContextPath() {
        ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, null);
        result.setData(ngxPath);
        return result;
    }

    /**
     * @param
     * @return
     * @Description 获取堆垛机，传送线的最近状态
     * 堆垛机只有一个在执行的任务，但是线体会有好几个
     * @Date 2020/6/4 15:12
     **/
    @GetMapping("deviceInfo")
    public ResponseResult getDeviceInfo() {
        Map rtnMap = dynamicViewService.queryDeviceView();
        ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, rtnMap);
        return result;
    }

    /**
     * @param
     * @return
     * @Description 获取堆垛机，传送线的最近状态
     * 堆垛机只有一个在执行的任务，但是线体会有好几个
     * @Date 2020/6/4 15:12
     **/
    @GetMapping("errorlog")
    public ResponseResult getErrorlog() {
        Map<String, Object> data = new HashMap<>();


        WcsErrorLog cond = new WcsErrorLog();
//        '1码垛机 2堆垛机 3RGV 4AGV 5四向穿梭车 6线体',
        cond.setDeviceType("4");
        Pager<WcsErrorLog> pager = errorLogService.findListByCondition(1, 3, cond);
        List<WcsErrorLog> agvErrorLogs = pager.getRecords();
        data.put("agvCount", pager.getTotalCount());
        data.put("agv", agvErrorLogs);

        cond.setDeviceType("5");
        pager = errorLogService.findListByCondition(1, 3, cond);
        List<WcsErrorLog> fourcarErrorLogs = pager.getRecords();
        data.put("fourcarCount", pager.getTotalCount());
        data.put("fourcar", fourcarErrorLogs);

        ResponseResult result = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, data);
        return result;
    }


}
