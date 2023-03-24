package com.penghaisoft.pda.stereoscopic.controller;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.model.PalletInDto;
import com.penghaisoft.pda.basic.service.IWmsAddressRealRelaService;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Constant;
import com.penghaisoft.pda.common.IWmsCommonService;
import com.penghaisoft.pda.outwarehouse.service.WmsOrderOutStereoscopicService;
import com.penghaisoft.pda.outwarehouse.service.WmsTaskExecutionLogService;
import com.penghaisoft.pda.storage.model.WmsPallet;
import com.penghaisoft.pda.storage.service.CommonStorageService;
import com.penghaisoft.pda.storage.service.WmsLocationStereoscopicService;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 立库手动入库
 * @ClassName StereoscopicHandInController
 * @Author luot
 * @Date 2020/3/11 17:42
 **/
@Slf4j
@RestController
@RequestMapping("stereoscopic/error/hand")
public class StereoscopicErrorHandInController {

    @Autowired
    private WmsOrderOutStereoscopicService wmsOrderOutStereoscopicService;

    @Autowired
    private WmsLocationStereoscopicService wmsLocationStereoscopicService;

    @Autowired
    private WmsTaskExecutionLogService wmsTaskExecutionLogService;

    @Autowired
    private CommonStorageService commonStorageService;

    @Autowired
    private IWmsCommonService wmsCommonService;

    @Autowired
    private IWmsAddressRealRelaService wmsAddressRealRelaService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${notice.other-sys-addr.pallet-error-in}")
    private String noticeErrorPalletInAddr;

    /**
     * @return
     * @Description 立库手动入库【异常口入库】
     * @Author luot
     * @Date 2020/3/11 17:42
     * @Param
     **/
    @PostMapping("inwarehouse")
    public JSONObject handInSubmit(@RequestHeader("account") String account, @RequestHeader("areaCode") String areaCode, @RequestBody JSONObject param) {
        JSONObject result = null;
//        托盘码
        String palletCode = param.getString("palletCode");
//        入库口地址【当前位置】
        Integer realAddress = 1001;

        if (palletCode == null || "".equals(palletCode)) {
            result = CommonUtil.genErrorResult("请输入/扫描托盘码");
            return result;
        }

        List<WmsPallet> palletList = wmsLocationStereoscopicService.queryWmsPallet(palletCode);
        if(palletList != null && !palletList.isEmpty()){
            WmsPallet wmsPallet = palletList.get(0);
            if(wmsPallet.getGoodsCode() == null || "".equals(wmsPallet.getGoodsCode())){
                result = CommonUtil.genErrorResult("当前托盘未绑定");
                return result;
            }
        }else {
            result = CommonUtil.genErrorResult("托盘不存在！");
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
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(noticeErrorPalletInAddr, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue()!=200){
                log.error("调wcs异常入库接口失败！");
                result = CommonUtil.genErrorResult("异常入库调用WCS失败;");
                stringRedisTemplate.delete(key);//删除对象
                return result;
            }else {
                JSONObject noticeResult = wcsResp.getBody();
//                              状态码：1成功 0 本次下达失败
                if (noticeResult.getString("code").equals("1")){
                    log.info("调wcs异常入库接口成功！");
                }else {
                    log.error("调wcs异常入库接口失败：" + noticeResult.getString("message"));
//                    库位状态回滚成初始状态0可用
                    result = CommonUtil.genErrorResult("调wcs异常入库接口失败;" + noticeResult.getString("message"));
                    stringRedisTemplate.delete(key);//删除对象
                    return result;
                }
            }
        } catch (Exception e) {
//            库位状态回滚成初始状态0可用
            result = CommonUtil.genErrorResult("调wcs异常入库接口失败;");
            stringRedisTemplate.delete(key);//删除对象
            return result;
        }

        stringRedisTemplate.delete(key);//删除对象
        result = CommonUtil.genSucessResultWithData(null);
        return result;
    }


}
