package com.penghaisoft.pda.storage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.storage.service.StereoscopicHandInService;
import com.penghaisoft.pda.sys.controller.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 发送WCS接收接口
 * @author
 */
@Service
@Slf4j
public class StereoscopicHandInServiceImpl implements StereoscopicHandInService {

    @Value("${notice.other-sys-addr.pallet-in}")
    private String stereoscopicPath;

    @Value("${notice.other-sys-addr.pallet-out}")
    private String outPath;

    private String CHANGE="{ip}:{port}/fromWms/portModel";
    /**
     * 下发速锐入库任务接口功能
     * @param map
     * @return
     */
    @Override
    public JSONObject startTask(JSONObject map) {
        //添加固定信息。
        map.put("taskID", Constants.UUid());
        map.put("reportTime",Constants.date_log());
        map.put("taskStat","0");
        JSONObject backJson=null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            log.info("PDA下发入库任务，参数={}",map.toJSONString());
            HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(map, headers);
            //设置延迟时间10s
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setReadTimeout(5000);
            ResponseEntity<JSONObject> wcsResp = new RestTemplate(factory).postForEntity(stereoscopicPath, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("PDA入库任务{}下发初始任务服务端返回异常！",stereoscopicPath);
                map.put("errorMsg",String.valueOf(wcsResp.getStatusCodeValue()));
            } else {
                backJson = wcsResp.getBody();
                log.info("PDA入库任务={}",backJson);
                //接口记录
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("PDA入库任务{}客户端异常！",stereoscopicPath);
            String errorMsg = e.getMessage();
            if (errorMsg.length()>200){
                errorMsg = errorMsg.substring(0,200);
            }
            //接口记录
        }
        return backJson;
    }

    /**
     * PDA下发WMS出库任务
     * @param map
     * @return
     */
    @Override
    public JSONObject outTask(JSONObject map) {
        //添加固定信息。
        map.put("outboundID", Constants.UUid());
        map.put("reportTime",Constants.date_log());
        map.put("taskStat","0");
        map.put("endLocation","");
        JSONObject backJson=null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            log.info("PDA下发出库任务，参数={}",map.toJSONString());
            HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(map, headers);
            //设置延迟时间10s
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setReadTimeout(10000);
            ResponseEntity<JSONObject> wcsResp = new RestTemplate(factory).postForEntity(outPath, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("PDA出库任务{}下发初始任务服务端返回异常！",outPath);
                map.put("errorMsg",String.valueOf(wcsResp.getStatusCodeValue()));
                /**
                 * 接口记录
                 */
            } else {
                backJson = wcsResp.getBody();
                log.info("PDA出库任务={}",backJson);
                //接口记录
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("PDA出库任务{}客户端异常！",outPath);
            String errorMsg = e.getMessage();
            if (errorMsg.length()>200){
                errorMsg = errorMsg.substring(0,200);
            }
            //接口记录
        }
        return backJson;
    }

    /**
     * 发送速锐切换库口通知
     * @param map
     * @return
     */
    @Override
    public JSONObject changeWarehouse(JSONObject map) {
        //添加固定信息。
        map.put("warehouse", "仓库编码");
        map.put("portCode", "库口编码");
        JSONObject backJson=null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            log.info("PDA下发入库任务，参数={}",map.toJSONString());
            HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(map, headers);
            //设置延迟时间10s
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setReadTimeout(5000);
            ResponseEntity<JSONObject> wcsResp = new RestTemplate(factory).postForEntity(CHANGE, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("PDA入库任务{}下发初始任务服务端返回异常！",CHANGE);
                map.put("errorMsg",String.valueOf(wcsResp.getStatusCodeValue()));
            } else {
                backJson = wcsResp.getBody();
                log.info("PDA入库任务={}",backJson);
                //接口记录
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("PDA入库任务{}客户端异常！",CHANGE);
            String errorMsg = e.getMessage();
            if (errorMsg.length()>200){
                errorMsg = errorMsg.substring(0,200);
            }
            //接口记录
        }
        return backJson;
    }


}
