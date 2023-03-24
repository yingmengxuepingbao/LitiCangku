package com.penghaisoft.pda.storage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.pda.basic.model.PalletInDto;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Constant;
import com.penghaisoft.pda.common.IWmsCommonService;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog;
import com.penghaisoft.pda.outwarehouse.service.WmsTaskExecutionLogService;
import com.penghaisoft.pda.storage.dao.WmsGoodsMapper;
import com.penghaisoft.pda.storage.dao.WmsPalletMapper;
import com.penghaisoft.pda.storage.model.WmsGoods;
import com.penghaisoft.pda.storage.model.WmsPallet;
import com.penghaisoft.pda.storage.service.CommonStorageService;
import com.penghaisoft.pda.storage.service.WmsLocationStereoscopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description CommonStorageServiceImpl
 * @Auther zhangxu
 * @Date 2020/2/19 14:38
 **/
@Slf4j
@Service
public class CommonStorageServiceImpl implements CommonStorageService {

    @Autowired
    private WmsGoodsMapper goodsMapper;

    @Autowired
    private WmsPalletMapper palletMapper;

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private WmsLocationStereoscopicService wmsLocationStereoscopicService;

    @Autowired
    private IWmsCommonService wmsCommonService;

    @Autowired
    private WmsTaskExecutionLogService wmsTaskExecutionLogService;

    @Value("${notice.other-sys-addr.pallet-in}")
    private String noticePalletInAddr;

    /**
     * 根据编码查询商品信息
     *
     * @param goodsCode
     * @return
     */
    @Override
    public WmsGoods queryGoodInfoByCode(String goodsCode) {
        WmsGoods goodInfo = null;
        List<WmsGoods> goods = goodsMapper.selectByGoodsCode(goodsCode);
        if (null != goods && goods.size() > 0) {
            goodInfo = goods.get(0);
        }
        return goodInfo;
    }

    /**
     * 查询托盘信息
     *
     * @param palletCode
     * @return
     */
    @Override
    public WmsPallet queryPalletInfoByCode(String palletCode) {
        WmsPallet pallet = null;
        List<WmsPallet> pallets = palletMapper.selectByCode(palletCode);
        if (null != pallets && pallets.size() > 0) {
            pallet = pallets.get(0);
        }
        return pallet;
    }

    /**
     * 是否是空托盘
     *
     * @param pallet
     * @return
     */
    @Override
    public boolean isEmptyPallet(WmsPallet pallet) {
//        放有商品的不是空托盘
        if (null == pallet.getGoodsCode() || "".equals(pallet.getGoodsCode())) {
            return true;
        }
        return false;
    }

    /**
     * 托盘手工入库
     * jzh
     *
     * @param palletLocation
     * @param palletAmount
     * @param account
     * @return
     */
    @Override
    @Transactional
    public Resp handVirtualPalletInSubmit(String palletLocation, Integer palletAmount, String account) {
        Resp resp = new Resp();
        Date now = new Date();
//        1 生成一个虚拟托盘号VP+年月日+8位UUID；
        SimpleDateFormat format = new SimpleDateFormat("YYMMdd");
        String dateFormat = format.format(now).substring(2);
        String palletCode = "VP" + dateFormat + wmsCommonService.getSeqLen4("VP", 1).get(0);
//        2 写入托盘表，托盘的商品号固定为pallet，批次号固定为1
        String palletId = CommonUtil.getUUID();
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setPalletId(CommonUtil.getUUID());
        wmsPallet.setPalletCode(palletCode);
        wmsPallet.setGoodsCode("pallet");
        wmsPallet.setBatchNo("1");
        wmsPallet.setAmount(palletAmount);
        wmsPallet.setCreateBy(account);
        wmsPallet.setGmtCreate(now);
        wmsPallet.setActiveFlag("1");
        int insert = palletMapper.insertSelective(wmsPallet);

//        3 计算立库入库库位，type=15
        String suggestLocation = "";
        Resp result = wmsLocationStereoscopicService.queryRecommendLocationCode(palletCode);
        if ("0".equals(result.getCode())) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resp.setCode("0");
            resp.setMessage("获取推荐库位失败" + result.getMessage());
            return resp;
        } else {
            suggestLocation = (String) result.getData();
        }

        long taskId = wmsCommonService.getTaskIds(Constant.TaskType.VIRTUAL_PALLET_IN, 1)[0];
//        4 调用WCS接口下发入库指令
        try {
            PalletInDto palletInDto = new PalletInDto();
            palletInDto.setTaskId(taskId);
            palletInDto.setTaskType(String.valueOf(Constant.TaskType.VIRTUAL_PALLET_IN.getTaskType()));
//            目标库位
            palletInDto.setTargetLocation(Integer.parseInt(suggestLocation));
//            入口path
            palletInDto.setFromAddress(Integer.parseInt(palletLocation));
            palletInDto.setPalletCode(palletCode);
            palletInDto.setOperator(account);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PalletInDto> request = new HttpEntity<PalletInDto>(palletInDto, headers);

//                          调用wcs接收出库指令接口
            ResponseEntity<JSONObject> wcsResp = restTemplate.postForEntity(noticePalletInAddr, request, JSONObject.class);
            if (wcsResp.getStatusCodeValue() != 200) {
                log.error("调wcs接口失败！");
//                库位状态回滚成初始状态0可用
                wmsLocationStereoscopicService.revertLocationStatus0New(suggestLocation, palletId);
                resp.setCode("0");
                resp.setMessage("启动失败，获取推荐库位成功，调用WCS入库接口失败;");
                return resp;
            } else {
                JSONObject noticeResult = wcsResp.getBody();
//                              状态码：1成功 0 本次下达失败
                if (noticeResult.getString("code").equals("1")) {
                    log.info("调wcs接口成功！");
                } else {
                    log.error("调wcs接口失败：" + noticeResult.getString("message"));
//                    库位状态回滚成初始状态0可用
                    wmsLocationStereoscopicService.revertLocationStatus0New(suggestLocation, palletId);
                    resp.setCode("0");
                    resp.setMessage("启动失败，获取推荐库位成功，调用WCS入库接口失败;" + noticeResult.getString("message"));
                    return resp;
                }
            }

            format = new SimpleDateFormat("yyyyMMdd");

            WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
//        wmsTaskExecutionLog.setAreaCode(areaCode);
//            任务类型 1 生产入库 2 分拣入库 3 移库 4 出库 5越库 6托盘入库
            wmsTaskExecutionLog.setTaskType(String.valueOf(Constant.TaskType.VIRTUAL_PALLET_IN.getTaskType()));
            wmsTaskExecutionLog.setPalletCode(palletCode);
//            入库口地址
            wmsTaskExecutionLog.setInAddress(palletLocation);
//            任务状态1创建2执行3完成4异常5取消6创建失败
            wmsTaskExecutionLog.setTaskStatus("1");
            List<WmsPallet> palletList = wmsLocationStereoscopicService.queryWmsPallet(palletCode);
            if (palletList != null && !palletList.isEmpty()) {
                wmsTaskExecutionLog.setGoodsCode(palletList.get(0).getGoodsCode());
                wmsTaskExecutionLog.setBatchNo(palletList.get(0).getBatchNo());
            }
            wmsTaskExecutionLog.setCreateBy(account);
            wmsTaskExecutionLog.setGmtCreate(now);
            wmsTaskExecutionLog.setActiveFlag("1");
            wmsTaskExecutionLog.setTaskId(taskId);
//        wmsTaskExecutionLog.setOrderNo(orderNo);
            wmsTaskExecutionLog.setLocationCode(suggestLocation);

//            创建入立体库的指令任务、更新托盘状态
            wmsTaskExecutionLogService.inStereoscopicTaskCreate(wmsTaskExecutionLog);
        } catch (Exception e) {
//            库位状态回滚成初始状态0可用
            wmsLocationStereoscopicService.revertLocationStatus0New(suggestLocation, palletId);
            resp.setCode("0");
            resp.setMessage("启动失败，获取推荐库位成功，调用WCS入库接口失败;");
            return resp;
        }

        resp.setCode("1");
        resp.setMessage("success");
        return resp;
    }
}
