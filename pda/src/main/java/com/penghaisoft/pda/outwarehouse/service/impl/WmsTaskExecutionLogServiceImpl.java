package com.penghaisoft.pda.outwarehouse.service.impl;

import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.outwarehouse.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog;
import com.penghaisoft.pda.outwarehouse.service.WmsTaskExecutionLogService;
import com.penghaisoft.pda.storage.dao.WmsGoodsMapper;
import com.penghaisoft.pda.storage.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.pda.storage.dao.WmsPalletMapper;
import com.penghaisoft.pda.storage.model.WmsGoods;
import com.penghaisoft.pda.storage.model.WmsLocationStereoscopic;
import com.penghaisoft.pda.storage.model.WmsPallet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 业务逻辑实现类
 * </p>
 *
 * @author
 * @createDate
 **/
@Service("wmsTaskExecutionLogService")
public class WmsTaskExecutionLogServiceImpl implements WmsTaskExecutionLogService {
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;

    @Resource
    private WmsLocationStereoscopicMapper wmsLocationStereoscopicMapper;

    @Resource
    private WmsPalletMapper wmsPalletMapper;

    @Resource
    private WmsGoodsMapper wmsGoodsMapper;

    @Override
    public void updateByTaskId(WmsTaskExecutionLog wmsTaskExecutionLog) {
        wmsTaskExecutionLogMapper.updateByPrimaryKeySelective(wmsTaskExecutionLog);
    }

    @Override
    public WmsTaskExecutionLog selectByTaskId(String id) {
        List<WmsTaskExecutionLog> wmsTaskExecutionLogs = wmsTaskExecutionLogMapper.selectByTaskId(Long.valueOf(id));
        if(wmsTaskExecutionLogs.size()>0){
            return wmsTaskExecutionLogs.get(0);
        }
        return null;
    }

    /**
     * @return
     * @Description 入立体库库任务创建
     * @Author luot
     * @Date 2020/2/14 16:42
     * @Param
     **/
    @Override
    @Transactional
    public Resp inStereoscopicTaskCreate(WmsTaskExecutionLog condition) {
        Resp resp = new Resp();

//        condition.setLocationCode(locationCode);
        wmsTaskExecutionLogMapper.insertSelective(condition);

//        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
//        updateOb.setLocationCode(condition.getLocationCode());
//        updateOb.setPalletCode(condition.getPalletCode());
////        使用状态 0可用1入库中2出库中3占用4异常，针对立库，只要是出入库开始但未完成都属于使用中
////        updateOb.setUseStatus("1");
//        updateOb.setLastModifiedBy("PDA");
//        updateOb.setGmtModified(condition.getGmtCreate());
//        wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);

//        WmsPallet wmsPalletOb = new WmsPallet();
//        wmsPalletOb.setPalletCode(condition.getPalletCode());
//        wmsPalletOb.setLockBy(String.valueOf(condition.getTaskId()));
//        wmsPalletOb.setLocationCode(condition.getLocationCode());
//        wmsPalletOb.setAreaCode(condition.getAreaCode());
//        wmsPalletOb.setLastModifiedBy(condition.getCreateBy());
//        wmsPalletOb.setGmtModified(condition.getGmtCreate());
//        wmsPalletMapper.updateByCode(wmsPalletOb);

        resp.setCode("1");
        return resp;
    }

    @Override
    public List<WmsTaskExecutionLog> selectByStatus(String status, String type) {
        return wmsTaskExecutionLogMapper.selectByStatus(status,type);
    }

    /**
     * 根据托盘号，找到最近的本托盘出库商品
     *
     * @param palletCode
     * @return
     */
    @Override
    public WmsGoods queryLastOutGoodsByPallet(String palletCode) {
        List<WmsTaskExecutionLog> taskExecutionLogs = wmsTaskExecutionLogMapper.selectLastOutInfoByPallet(palletCode);
        if (taskExecutionLogs.size()>0){
            String goodsCode = taskExecutionLogs.get(0).getGoodsCode();
            String batchNo = taskExecutionLogs.get(0).getBatchNo();
            List<WmsGoods> wmsGoodsList = wmsGoodsMapper.selectByGoodsCode(goodsCode);
            if (wmsGoodsList.size()>0){
                WmsGoods result = wmsGoodsList.get(0);
                result.setUserDefined5(batchNo);
                return result;
            }

        }
        return null;
    }


    /**
     *功能描述:
     * @params
     * @return java.util.List<com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog>
     */
    public List<WmsTaskExecutionLog> selectTaskByPallet(WmsTaskExecutionLog condition){
        return wmsTaskExecutionLogMapper.selectTaskByPallet(condition);
    }
}
