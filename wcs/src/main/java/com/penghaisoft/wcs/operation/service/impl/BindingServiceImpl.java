package com.penghaisoft.wcs.operation.service.impl;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.operation.model.WcsBindingInfo;
import com.penghaisoft.wcs.operation.model.dao.WcsBindingInfoMapper;
import com.penghaisoft.wcs.operation.service.BindingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName BindingServiceImpl
 * @Description 绑定服务
 * @Author zhangx
 * @Date 2020/7/2 13:32
 **/
@Slf4j
@Service
public class BindingServiceImpl implements BindingService {

    @Autowired
    private WcsBindingInfoMapper bindingInfoMapper;

    /**
     * 写入绑定数据表
     *  如果写成功了返回记录，携带有id
     * @param palletCode
     * @param batchNo
     * @param amount
     * @param goodsCode
     * @param channelId  通道id 一共5个
     * @param operator   操作人
     */
    @Override
    public WcsBindingInfo bindPalletParam(String palletCode, String batchNo, Short amount, String goodsCode, int channelId, String operator) {
        WcsBindingInfo cond = new WcsBindingInfo();
        cond.setPalletCode(palletCode);
        cond.setBatchNo(batchNo);
        cond.setGoodsCode(goodsCode);
        List<WcsBindingInfo> bindingInfos = bindingInfoMapper.selectByCond(cond);
        if (bindingInfos.size() == 0){
            cond.setAddressId(channelId);
            cond.setAmount(amount.intValue());
//            还未请求wms 推荐库位
            cond.setReportWms("0");
            cond.setCreateBy(operator);
            cond.setGmtCreate(new Date());
            bindingInfoMapper.insertSelective(cond);
            return cond;
        }else {
            return null;
        }
    }

    /**
     * 写入本地暂存表
     * 如果已经绑定了，返回之前绑定的数据
     *       pallet_code = #{palletCode,jdbcType=VARCHAR}
     *       goods_code = #{goodsCode,jdbcType=VARCHAR}
     *       batch_no = #{batchNo,jdbcType=VARCHAR}
     * @param bindInfo
     */
    @Override
    public WcsBindingInfo bindPallet(WcsBindingInfo bindInfo) {
        WcsBindingInfo result = null;
        List<WcsBindingInfo> bindingInfos = bindingInfoMapper.selectByCond(bindInfo);
        if (bindingInfos.size() == 0){
//            还未请求wms 推荐库位
            bindInfo.setReportWms("0");
            bindingInfoMapper.insertSelective(bindInfo);
            result =  bindInfo;
        }else if (bindingInfos.size() == 1){
            bindInfo = bindingInfos.get(0);
            result =  bindInfo;
        }

        return result;
    }

    /**
     * @param bindInfo
     * @return boolean 是否做了绑定
     * @Description 如果没绑定托盘就绑定
     * @Date 2020/7/6 16:04
     **/
    @Override
    public boolean bindPalletIfNot(WcsBindingInfo bindInfo) {
        boolean result = false;
        List<WcsBindingInfo> bindingInfos = bindingInfoMapper.selectByPalletCode(bindInfo.getPalletCode());
        if (bindingInfos.size() == 0){
//
            bindInfo.setReportWms("X");
            bindingInfoMapper.insertSelective(bindInfo);
            result =  true;
        }

        return result;
    }
    /**
     * @Description: 绑定日志信息查询
     * @Author: jzh
     * @Date: 2020/7/7
     */
    @Override
    public Pager<WcsBindingInfo> findBindingInfo(int page, int rows, WcsBindingInfo wcsBindingInfo) {
        Pager<WcsBindingInfo> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
        List<WcsBindingInfo> records = bindingInfoMapper.queryList(pager,wcsBindingInfo);
        long size = bindingInfoMapper.queryCount(wcsBindingInfo);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

}
