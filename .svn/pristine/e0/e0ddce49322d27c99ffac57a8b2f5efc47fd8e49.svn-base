package com.penghaisoft.pda.inwarehouse.service.impl;

import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.inwarehouse.dao.WmsOrderCrossInDeailMapper;
import com.penghaisoft.pda.inwarehouse.dao.WmsOrderCrossInMapper;
import com.penghaisoft.pda.inwarehouse.dao.WmsOrderInMapper;
import com.penghaisoft.pda.inwarehouse.model.WmsOrderCrossIn;
import com.penghaisoft.pda.inwarehouse.model.WmsOrderCrossInDeail;
import com.penghaisoft.pda.inwarehouse.model.WmsOrderIn;
import com.penghaisoft.pda.inwarehouse.service.TemporaryInService;
import com.penghaisoft.pda.storage.dao.WmsSendLogMapper;
import com.penghaisoft.pda.storage.model.WmsSendLog;
import com.penghaisoft.pda.storage.service.TemporaryAreaStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 暂存区入库服务
 * @Description TemporaryInServiceImpl
 * @Auther zhangxu
 * @Date 2020/2/18 10:20
 **/
@Slf4j
@Service
public class TemporaryInServiceImpl implements TemporaryInService {

    @Autowired
    private WmsOrderInMapper wmsOrderInMapper;

    @Autowired
    private TemporaryAreaStorageService temporaryAreaStorageService;

    @Autowired
    private WmsOrderCrossInMapper wmsOrderCrossInMapper;

    @Autowired
    private WmsOrderCrossInDeailMapper wmsOrderCrossInDeailMapper;

    @Autowired
    private WmsSendLogMapper sendLogMapper;

    /**
     * 提单
     *
     * @param orderNo
     * @return
     */
    @Override
    public List<WmsOrderIn> getStartOrderInList(String orderNo,String account) {
//        找到创建或者入库中状态的
        List<String> statusList = Arrays.asList("1","2");
        List<WmsOrderIn> orderInList = wmsOrderInMapper.queryOrderInListByNoAndStatus(orderNo,statusList);
        for (WmsOrderIn orderIn:orderInList) {
//            如果状态为1 直接改成2入库中
            if ("1".equals(orderIn.getOrderStatus())){
                wmsOrderInMapper.updateOrderStatus(orderNo,"2",account);
            }
        }
        return orderInList;
    }

    /**
     * 当前订单状态应该为2
     *
     * @param orderNo
     * @return
     */
    @Override
    public boolean checkSubmitOrderInStatus(String orderNo) {
        List<WmsOrderIn> orderIns = wmsOrderInMapper.queryOrderInListByNo(orderNo);
        for (WmsOrderIn orderIn:orderIns) {
            if (!"2".equals(orderIn.getOrderStatus())){
                return false;
            }
        }
        return true;
    }

    /**
     * 提交订单信息
     *
     * @param orderNo
     * @param account
     * @param orderIns
     * @return
     */
    @Transactional
    @Override
    public boolean submitOrderIn(String orderNo, String account, List<WmsOrderIn> orderIns) {
//        增加收货数量,修改订单状态
        for (WmsOrderIn orderIn:orderIns) {
            orderIn.setOrderStatus("3");
            orderIn.setLastModifiedBy(account);
            wmsOrderInMapper.updateOrderAmount(orderIn);
//          加入库暂存区库存
            temporaryAreaStorageService.addTemporaryInStorage(orderIn.getGoodsCode(),orderIn.getRealAmount(),account);
        }
        return true;
    }

    /**
     * 提单 提取收货越库状态为1或者2的数据
     *
     * @param orderNo
     * @param account
     * @return
     */
    @Override
    public List<WmsOrderCrossInDeail> getStartOrderCrossDetailList(String orderNo, String account) {
        //        找到创建或者入库中状态的
        List<String> statusList = Arrays.asList("1","2");
        List<WmsOrderCrossIn> orderCrossInList = wmsOrderCrossInMapper.selectByOrderNoAndStatus(orderNo,statusList);
        List<WmsOrderCrossInDeail> orderCrossInDeailList = null;
        for (WmsOrderCrossIn orderCrossIn:orderCrossInList) {
//            如果状态为1 直接改成2入库中
            if ("1".equals(orderCrossIn.getOrderStatus())){
                WmsOrderCrossIn update = new WmsOrderCrossIn();
                update.setOrderStatus("2");
                update.setLastModifiedBy(account);
                update.setOrderNo(orderNo);
                update.setGmtModified(new Date());
                wmsOrderCrossInMapper.updateStatusByOrderNo(update);
            }
//            查询明细表
            orderCrossInDeailList = wmsOrderCrossInDeailMapper.selectByOrderNo(orderNo);
        }
        return orderCrossInDeailList;
    }

    /**
     * 当前收货越库订单状态应该为2
     *
     * @param orderNo
     * @return
     */
    @Override
    public boolean checkSubmitOrderCrossStatus(String orderNo) {
        List<WmsOrderCrossIn> orderCrossInList = wmsOrderCrossInMapper.queryOrderCrossListByNo(orderNo);
        for (WmsOrderCrossIn orderCrossIn:orderCrossInList) {
            if (!"2".equals(orderCrossIn.getOrderStatus())){
                return false;
            }
        }
        return true;
    }

    /**
     * 提交收货越库订单信息
     *
     * @param orderNo
     * @param account
     * @param orderCrossDetailList
     * @return
     */
    @Transactional
    @Override
    public boolean submitOrderCross(String orderNo, String account, List<WmsOrderCrossInDeail> orderCrossDetailList) {
//        首先校验收货暂存区库存够不够
        boolean checkFlag = false;
        for (WmsOrderCrossInDeail orderCrossInDeail:orderCrossDetailList) {
            String goodsCode = orderCrossInDeail.getGoodsCode();
            Integer realAmount = orderCrossInDeail.getRealAmount();
            checkFlag = temporaryAreaStorageService.canMinusTemporaryInStorage(goodsCode,realAmount);
            if (!checkFlag){
                return checkFlag;
            }
        }
        //        增加收货数量,修改收货暂存区库存
        Date now = new Date();
        for (WmsOrderCrossInDeail orderCrossInDeail:orderCrossDetailList) {
            WmsOrderCrossInDeail update = new WmsOrderCrossInDeail();
            update.setOrderNo(orderNo);
            update.setGoodsCode(orderCrossInDeail.getGoodsCode());
            update.setRealAmount(orderCrossInDeail.getRealAmount());
            if (null!=orderCrossInDeail.getRemark()&&!"".equals(orderCrossInDeail.getRemark())){
                update.setRemark(orderCrossInDeail.getRemark());
            }
            update.setLastModifiedBy(account);
            update.setGmtModified(now);
            wmsOrderCrossInDeailMapper.updateAmountByOrderNoAndGoods(update);
//          扣减入库暂存区库存
            temporaryAreaStorageService.addTemporaryInStorage(orderCrossInDeail.getGoodsCode(),-orderCrossInDeail.getRealAmount(),account);

            // 写入发货日志表wms_send_log；
            WmsSendLog sendLog = new WmsSendLog();
            sendLog.setSendId(CommonUtil.getUUID());
            sendLog.setGoodsCode(orderCrossInDeail.getGoodsCode());
//            sendLog.setGoodsName(outTemporary.getGoodsName());
//            收货区的没绑定，就当作没有箱码
            sendLog.setHasBoxCode("0");
            sendLog.setSendNo(orderNo);
            sendLog.setSendAmount(orderCrossInDeail.getRealAmount());
            sendLog.setCreateBy(account);
            sendLog.setGmtCreate(now);
            sendLog.setActiveFlag("1");
            sendLogMapper.insertSelective(sendLog);
        }
//        修改订单状态
        WmsOrderCrossIn updateWmsOrderCrossIn = new WmsOrderCrossIn();
        updateWmsOrderCrossIn.setOrderNo(orderNo);
        updateWmsOrderCrossIn.setLastModifiedBy(account);
        updateWmsOrderCrossIn.setGmtModified(now);
        updateWmsOrderCrossIn.setOrderStatus("3");
        wmsOrderCrossInMapper.updateStatusByOrderNo(updateWmsOrderCrossIn);
        return true;

    }

}
