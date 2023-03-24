package com.penghaisoft.pda.nuohua.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.nuohua.service.SRService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * @Author hym
 * @create 2022/10/9 11:35
 */
@Service("srService")
@Slf4j
public class SRServiceImpl implements SRService {

    /**
     * 请求地址
     */
    @Value("${notice.other-sys-addr.pallet-hand-in}")
    private String SR_SEND_TASK;
    @Override
    public JSONObject sendTask(JSONObject map) {
        JSONObject backJson=null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            log.info("生成速锐任务单，参数={}",map.toJSONString());
            HttpEntity<JSONObject> request = new HttpEntity<JSONObject>(map, headers);
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(3000);
            factory.setReadTimeout(3000);
            ResponseEntity<JSONObject> wcsResp = new RestTemplate(factory).postForEntity(SR_SEND_TASK, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("调速锐接口{}下发初始任务服务端返回异常！",SR_SEND_TASK);
            } else {
                backJson=new JSONObject();
                backJson = wcsResp.getBody();
                log.info("速锐返回结果={}",backJson);
            }
        }catch (Exception e) {
            e.printStackTrace();
            log.error("速锐下发初始任务接口{}客户端异常！",SR_SEND_TASK);
            String errorMsg = e.getMessage();
            if (errorMsg.length()>200){
                errorMsg = errorMsg.substring(0,200);
            }
        }
        return backJson;
    }
}
