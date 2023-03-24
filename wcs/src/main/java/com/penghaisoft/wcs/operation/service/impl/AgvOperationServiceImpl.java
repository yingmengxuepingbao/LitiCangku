package com.penghaisoft.wcs.operation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.log.model.business.IWcsCallAgvLogService;
import com.penghaisoft.wcs.operation.model.AgvTask;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Date;

/**
 * @ClassName AgvOperationServiceImpl
 * @Description 生成agv 任务单
 * @Author zhangx
 * @Date 2020/7/3 11:27
 **/
@Slf4j
@Service
public class AgvOperationServiceImpl implements AgvOperationService {

    @Value("${notice.other-sys-addr.agv-base-url}")
    private String agvBaseUrl;

    private static final String GEN_AGV_SCHEDULING_TASK_URL="/services/rest/hikRpcService/genAgvSchedulingTask";

    private static final String CONTINUE_TASK_URL="/services/rest/hikRpcService/continueTask";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IWcsCallAgvLogService wcsCallAgvLogService;

    /**
     * 调用agv 生成任务单
     * 0 服务端异常 1正常（是否业务正常要看返回值）-1客户端异常-请求没法出去或者服务端关闭
     * @param agvTask
     * @return
     */
    @Override
    public Resp sendTask(AgvTask agvTask) {
//        请求数据demo
/*        {
            "reqCode": "468513",
                "reqTime":"",
                "clientCode": "",
                "tokenCode":"",
                "interfaceName":"genAgvSchedulingTask",
                "taskTyp": "F01",
                "wbCode": "",
                "positionCodePath": [
            {
                "positionCode":"p01",
                    "type":"00"
            },
            {
                "positionCode":"x02",
                    "type":"02"
            }
            ],
            "podCode": "100001",
                "podDir": "0",
                "priority": "1",
                "agvCode": "",
                "taskCode": "",
                "data": ""
        }*/

//        接口返回
/*        {
            "code": “0”,
            "data": "F01169C808C317111G",
            "message": "成功",
            "reqCode": "468513"
        }*/
        Resp resp = new Resp();
//              下发agv任务
        Date sendTime = new Date();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject param = (JSONObject)JSONObject.toJSON(agvTask);
            log.info("生成AGV任务单，参数={}",param.toJSONString());
            HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(param, headers);
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(agvBaseUrl + GEN_AGV_SCHEDULING_TASK_URL, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("调AGV接口{}下发初始任务服务端返回异常！",GEN_AGV_SCHEDULING_TASK_URL);
                resp.setCode("0");
                resp.setMsg("调AGV下发初始任务接口服务端返回异,http请求返回码="+wcsResp.getStatusCodeValue());
//                1成功 2业务异常 3服务端异常 4客户端异常
                wcsCallAgvLogService.addGenTaskLog(agvTask,sendTime,new Date(),"3",String.valueOf(wcsResp.getStatusCodeValue()));
            } else {
                JSONObject noticeResult = wcsResp.getBody();
                log.info("agv返回结果={}",noticeResult);
                resp.setCode("1");
                resp.setData(noticeResult);
                //                1成功 2业务异常 3服务端异常 4客户端异常
                if ("0".equals(noticeResult.getString("code"))){
                    wcsCallAgvLogService.addGenTaskLog(agvTask,sendTime,new Date(),"1",noticeResult.getString("message"));
                }else{
                    wcsCallAgvLogService.addGenTaskLog(agvTask,sendTime,new Date(),"2",noticeResult.getString("message"));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("调AGV下发初始任务接口{}客户端异常！",GEN_AGV_SCHEDULING_TASK_URL);
            String errorMsg = e.getMessage();
            if (errorMsg.length()>200){
                errorMsg = errorMsg.substring(0,200);
            }
            resp.setCode("-1");
            resp.setMsg(errorMsg);
            //                1成功 2业务异常 3服务端异常 4客户端异常
            wcsCallAgvLogService.addGenTaskLog(agvTask,sendTime,new Date(),"4",errorMsg);
        }finally {

            return resp;
        }
    }

    /**
     * 调用agv 继续执行任务
     *
     * @param agvTask
     * @return
     */
    @Override
    public Resp sendContinueTask(AgvTask agvTask) {
/*
        {
            "reqCode": "123",
            "reqTime":"",
            "clientCode": "",
            "tokenCode":"",
            "interfaceName":"continueTask",
            "wbCode": "",
            "podCode": "",
            "agvCode": "",
            "taskCode": "123456",
            "taskSeq": "",
            "nextPositionCode": {"positionCode":"p02","type":"00"},
            "data": ""
        }
        */
/*
        {
            "code": “0”,
            "data": "",
            "message": "成功",
            "reqCode": "123"
        }
        */
        Resp resp = new Resp();
        //              下发agv任务
        Date sendTime = new Date();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject param = (JSONObject)JSONObject.toJSON(agvTask);
            log.info("AGV继续任务,参数={}",param.toJSONString());
            HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(param, headers);
//              上传wms
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(agvBaseUrl + CONTINUE_TASK_URL, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("调AGV接口{}继续任务服务端返回异常！",CONTINUE_TASK_URL);
                resp.setCode("0");
                resp.setMsg("调AGV下发继续任务接口服务端返回异,http请求返回码="+wcsResp.getStatusCodeValue());
                //                1成功 2业务异常 3服务端异常 4客户端异常
                wcsCallAgvLogService.addContinueTaskLog(agvTask,sendTime,new Date(),"3",String.valueOf(wcsResp.getStatusCodeValue()));
            } else {

                JSONObject noticeResult = wcsResp.getBody();
                log.info(noticeResult.toJSONString());
                resp.setCode("1");
                resp.setData(noticeResult);
                //                1成功 2业务异常 3服务端异常 4客户端异常
                if ("0".equals(noticeResult.getString("code"))){
                    wcsCallAgvLogService.addContinueTaskLog(agvTask,sendTime,new Date(),"1",noticeResult.getString("message"));
                }else{
                    wcsCallAgvLogService.addContinueTaskLog(agvTask,sendTime,new Date(),"2",noticeResult.getString("message"));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("调AGV继续任务接口{}客户端异常！",CONTINUE_TASK_URL);
            String errorMsg = e.getMessage();
            if (errorMsg.length()>200){
                errorMsg = errorMsg.substring(0,200);
            }
            resp.setCode("-1");
            resp.setMsg(errorMsg);
            //                1成功 2业务异常 3服务端异常 4客户端异常
            wcsCallAgvLogService.addContinueTaskLog(agvTask,sendTime,new Date(),"4",errorMsg);
        }finally {

            return resp;
        }
    }
}
