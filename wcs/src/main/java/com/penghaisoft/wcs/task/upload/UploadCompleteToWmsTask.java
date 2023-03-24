package com.penghaisoft.wcs.task.upload;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.wcs.jobmanagement.model.business.IJobManagementService;
import com.penghaisoft.wcs.taskmanagement.model.business.IWcsTaskService;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * 将完成的任务上报给wms
 *
 * @Description UploadCompleteToWmsTask
 * @Auther zhangxu
 * @Date 2020/3/3 16:15
 **/
@Slf4j
@ConditionalOnProperty(prefix = "jobs.open", name = "upload", havingValue = "true")
@Component
public class UploadCompleteToWmsTask {

    @Autowired
    private IWcsTaskService wcsTaskService;

    @Autowired
    private IJobManagementService jobManagementService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${notice.other-sys-addr.wms-base-url}")
    private String wmsBaseUrl;

    @Scheduled(cron = "${jobs.uploadCompleteToWms.cron}")
    public void scheduled() {
        List<WcsTask> list = wcsTaskService.selectCompleteTask();
        if (list != null && !list.isEmpty()) {
            for (WcsTask tmp : list) {
                Long taskType = Long.valueOf(tmp.getTaskType());
                String postWmsUrl = Constant.TaskType.getPostWmsUrl(taskType);
                log.info("业务类型【{}】的任务{}进行上传，此任务任务状态={}，上传标记={}",Constant.TaskType.getTaskTypeDesc(taskType),tmp.getTaskId(),tmp.getTaskStatus(),tmp.getReportWms());
                deal(tmp, postWmsUrl);
            }
        }
    }

    /**
     * @return
     * @Description 回传任务状态
     * @Author luot
     * @Date 2020/3/23 16:54
     * @Param
     **/
    public void deal(WcsTask wcsTask, String postWmsUrl) {
        WcsTask updateOb = new WcsTask();
        updateOb.setId(wcsTask.getId());
        updateOb.setLastModifiedBy("upload-to-wms");
        updateOb.setGmtModified(new Date());

        Date startTime = new Date();
        String wmsRtnMsg = "";
//        任务状态1执行2完成3异常
        String jobStatus = "2";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> map = new HashMap<String, String>();
//            map.put("areaCode", "");
            map.put("taskId", String.valueOf(wcsTask.getTaskId()));
            map.put("taskType", wcsTask.getTaskType());
            map.put("palletCode", wcsTask.getPalletCode());
            map.put("taskStatus", wcsTask.getTaskStatus());
            map.put("msg", wcsTask.getErrorMsg());
            HttpEntity<Map> request = new HttpEntity<Map>(map, headers);

//            通知wms任务成功
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(wmsBaseUrl + postWmsUrl, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("调WMS接口失败！");
                wmsRtnMsg = "WMS接口异常";
                jobStatus = "3";
//                上报wms结果标志  1成功上传0未上传2业务异常3上传失败
                updateOb.setReportWms("3");
                updateOb.setErrorMsg(wmsRtnMsg);

            } else {
                JSONObject noticeResult = wcsResp.getBody();
//                状态码：1成功 0 本次下达失败
                if (noticeResult.getString("code").equals(Constant.RESULT.SUCCESS.code)) {
                    log.info("调WMS接口成功！");
                    wmsRtnMsg = "成功";
                    jobStatus = "2";
//                    上报wms结果标志  1成功上传0未上传2业务异常3上传失败
                    updateOb.setReportWms("1");
                } else {
                    log.error("调WMS接口失败：" + noticeResult.getString("message"));
                    wmsRtnMsg = noticeResult.getString("message");
                    jobStatus = "3";
//                    上报wms结果标志  1成功上传0未上传2业务异常3上传失败
                    updateOb.setReportWms("2");
                    updateOb.setErrorMsg(noticeResult.getString("message"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("调WMS接口失败！");
            wmsRtnMsg = "WMS接口异常";
            jobStatus = "3";
//            上报wms结果标志  1成功上传0未上传2业务异常3上传失败
            updateOb.setReportWms("3");
            updateOb.setErrorMsg("WMS接口异常");
        }

        Date endTime = new Date();

//        更新wcstask 上报wms结果标志  1成功上传0未上传2业务异常3上传失败
        wcsTaskService.updateByPrimaryKeySelective(updateOb);

//        写入定时任务执行日志、分析汇总
        List<Long> taskIds = new ArrayList<>();
        taskIds.add(wcsTask.getTaskId());
        jobManagementService.addExecutionAndSummaryLog(this.getClass().getName(), taskIds, jobStatus,
                startTime, endTime, wmsRtnMsg, 10);
    }

}
