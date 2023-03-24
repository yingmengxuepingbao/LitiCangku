package com.penghaisoft.pda.stereoscopic.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.model.PalletInDto;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName PalletRemoveController
 * @Description 托盘移除 已经放货在入库口了，但是以为某些原因无法入立库
 *
 * @Author zhangx
 * @Date 2020/9/22 16:06
 **/
@Slf4j
@RestController
@RequestMapping("stereoscopic/pallet/remove")
public class PalletRemoveController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${notice.other-sys-addr.pallet-remove}")
    private String noticePalletRemoveAddr;

    /**
     * 托盘移除
     * @param account
     * @param areaCode
     * @param param
     * @return
     */
    @PostMapping("submit")
    public JSONObject handleSubmit(@RequestHeader("account") String account, @RequestHeader("areaCode") String areaCode, @RequestBody JSONObject param) {
        JSONObject result = null;
//        托盘码
        String palletCode = param.getString("palletCode");
//        入库口地址【当前位置】
        Integer realAddress = 1001;

        if (palletCode == null || "".equals(palletCode)) {
            result = CommonUtil.genErrorResult("请输入/扫描托盘码");
            return result;
        }

        String key = Constant.ConfigInfo.INTERFACE_FOR_WCS_REDIS + palletCode;
        if (stringRedisTemplate.hasKey(key)) {
            result = CommonUtil.genErrorResult("当前托盘码正在处理中");
            return result;
        } else {
            stringRedisTemplate.opsForValue().set(key, "1", Constant.ConfigInfo.INTERFACE_FOR_WCS_REDIS_LOST_MINUTES, TimeUnit.MINUTES);//写入对象，并设置失效时间
        }


        try {
            PalletInDto palletInDto = new PalletInDto();
//            入口path
            palletInDto.setFromAddress(realAddress);
            palletInDto.setPalletCode(palletCode);
            palletInDto.setOperator(account);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PalletInDto> request = new HttpEntity<PalletInDto>(palletInDto, headers);
//                          调用wcs接收入库指令接口
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(noticePalletRemoveAddr, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue()!=200){
                log.error("调wcs托盘移除接口失败！");
                result = CommonUtil.genErrorResult("托盘移除调用WCS失败;");
                stringRedisTemplate.delete(key);//删除对象
                return result;
            }else {
                JSONObject noticeResult = wcsResp.getBody();
//                              状态码：1成功 0 本次下达失败
                if (noticeResult.getString("code").equals("1")){
                    log.info("调wcs托盘移除接口成功！");
                }else {
                    log.error("调wcs托盘移除接口失败：" + noticeResult.getString("message"));
//                    库位状态回滚成初始状态0可用
                    result = CommonUtil.genErrorResult("调wcs托盘移除接口失败;" + noticeResult.getString("message"));
                    stringRedisTemplate.delete(key);//删除对象
                    return result;
                }
            }
        } catch (Exception e) {
//            库位状态回滚成初始状态0可用
            result = CommonUtil.genErrorResult("调wcs托盘移除接口失败;");
            stringRedisTemplate.delete(key);//删除对象
            return result;
        }

        stringRedisTemplate.delete(key);//删除对象
        result = CommonUtil.genSucessResultWithData(null);
        return result;
    }
}
