package com.penghaisoft.pda.outwarehouse.service.impl;

import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.outwarehouse.dao.WmsOrderOutStereoscopicDeailMapper;
import com.penghaisoft.pda.outwarehouse.dao.WmsOrderOutStereoscopicMapper;
import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutStereoscopic;
import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.pda.outwarehouse.service.WmsOrderOutStereoscopicService;
import com.penghaisoft.pda.storage.dao.*;
import com.penghaisoft.pda.storage.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 立库出库--分拣
 * @ClassName WmsOrderOutStereoscopicServiceImpl
 * @Author luot
 * @Date 2020/2/24 16:41
 **/
@Service("wmsOrderOutStereoscopicService")
@ConditionalOnProperty(prefix = "wcs.config",name = "applyfactory",havingValue = "DEFAULT")
public class WmsOrderOutStereoscopicServiceImpl implements WmsOrderOutStereoscopicService {
    @Resource
    private WmsOrderOutStereoscopicMapper wmsOrderOutStereoscopicMapper;

    @Resource
    private WmsOrderOutStereoscopicDeailMapper wmsOrderOutStereoscopicDeailMapper;

    @Autowired
    private WmsPalletMapper wmsPalletMapper;

    @Autowired
    private WmsBoxBarcodeMapper wmsBoxBarcodeMapper;

    @Resource
    private WmsOutTemporaryMapper wmsOutTemporaryMapper;

    @Resource
    private WmsOutTemporaryBoxBarcodeMapper wmsOutTemporaryBoxBarcodeMapper;

    @Resource
    private WmsGoodsMapper wmsGoodsMapper;

    /**
     * @return
     * @Description 扫描托盘码
     * @Author luot
     * @Date 2020/2/24 16:40
     * @Param
     **/
    @Override
    public Resp scanPalletCode(String palletCode) {
        Resp resp = new Resp();
        WmsOrderOutStereoscopicDeail rtnob = new WmsOrderOutStereoscopicDeail();

        List<WmsPallet> pallets = wmsPalletMapper.selectByCode(palletCode);
        if (pallets == null || pallets.isEmpty()) {
            resp.setCode("0");
            resp.setMessage("查询不到该托盘");
            return resp;
        } else {
            WmsPallet pallet = pallets.get(0);
            String lockBy = pallet.getLockBy();
            if (lockBy == null || "".equals(lockBy)) {
//                这里应该是已经提交了，解绑了
                resp.setCode("0");
                resp.setMessage("该托盘锁定信息有误！");
                return resp;
            }

//            是否有箱码 1 有 0 没有
            String hasBoxCode = "0";

            WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
            wmsBoxBarcode.setPalletCode(pallet.getPalletCode());
            List<WmsBoxBarcode> wmsBoxBarcodeList = wmsBoxBarcodeMapper.selectByPalletCode(wmsBoxBarcode);
            if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                hasBoxCode = "1";
            }
//          todo 这里是为了什么？按单出库？？
//            String orderNo = lockBy.replace("sorting-", "");
            String orderNo = pallet.getUserDefined1();

            WmsOrderOutStereoscopicDeail searchOb = new WmsOrderOutStereoscopicDeail();
            searchOb.setOrderNo(orderNo);
            searchOb.setGoodsCode(pallet.getGoodsCode());
            searchOb.setBatchNo(pallet.getBatchNo());
            List<WmsOrderOutStereoscopicDeail> deailList = wmsOrderOutStereoscopicDeailMapper.queryByAny(searchOb);
//            这里想整托出库，如何判断？？
            if(deailList != null && !deailList.isEmpty()){
                rtnob = deailList.get(0);
//                计划数量
                Integer planAmount = rtnob.getPlanAmount() == null ? 0 : rtnob.getPlanAmount();
//                已捡数量
                Integer realAmount = rtnob.getRealAmount() == null ? 0 : rtnob.getRealAmount();
//                当前托盘数量
                Integer amount = pallet.getAmount() == null ? 0 : pallet.getAmount();

//                1 整托分拣 0 拆托
                String isSortingAll = "1";
//                如果当前托盘数量amount<planAmount-realAmount，代表这个托盘需要整托分拣，计算得到isSortingAll
                if(amount > planAmount-realAmount){
                    isSortingAll = "0";
                    rtnob.setPlanSortingAmount(planAmount-realAmount);
                }else {
                    rtnob.setPlanSortingAmount(amount);
                }

                rtnob.setAmount(amount);
                rtnob.setIsSortingAll(isSortingAll);
                rtnob.setHasBoxCode(hasBoxCode);
                rtnob.setPalletCode(palletCode);
            }else{
//                这里是托盘出库？
                rtnob.setOrderNo(orderNo);
                rtnob.setGoodsCode(pallet.getGoodsCode());
                rtnob.setGoodsName(pallet.getGoodsName());
                rtnob.setBatchNo(pallet.getBatchNo());
                rtnob.setAmount(pallet.getAmount());
                rtnob.setPlanSortingAmount(pallet.getAmount());
//                1 整托分拣 0 拆托【托盘出库的分拣全部是拆托】
                rtnob.setIsSortingAll("0");
                rtnob.setHasBoxCode(hasBoxCode);
                rtnob.setPalletCode(palletCode);
            }
        }

        resp.setCode("1");
        resp.setData(rtnob);
        return resp;
    }

    /**
      * @Description 整托分拣
      * @Author luot
      * @Date 2020/2/24 17:40
      * @Param 
      * @return 
      **/
    @Transactional
    @Override
    public Resp allsubmit(String palletCode, String account){
        Resp resp = new Resp();
        Date now = new Date();

        List<WmsPallet> pallets = wmsPalletMapper.selectByCode(palletCode);
        if (pallets == null || pallets.isEmpty()) {
            resp.setCode("0");
            resp.setMessage("查询不到该托盘");
            return resp;
        } else {
            WmsPallet pallet = pallets.get(0);
            String lockBy = pallet.getLockBy();
            if (lockBy == null || "".equals(lockBy)) {
                resp.setCode("0");
                resp.setMessage("该托盘锁定信息有误！");
                return resp;
            }

//            托盘解绑
            WmsPallet updateOb = new WmsPallet();
            updateOb.setPalletCode(pallet.getPalletCode());
            updateOb.setLockByNull("null");
            updateOb.setLocationCodeNull("null");
//                    途径库位可能存在，清掉
            updateOb.setChannelLocationNull("null");
            updateOb.setGoodsCodeNull("null");
            updateOb.setBatchNoNull("null");
            updateOb.setAmountNull("null");
//            清空订单号
            updateOb.setUserDefined1("");
            updateOb.setLastModifiedBy(account);
            updateOb.setGmtModified(now);
            wmsPalletMapper.updateByCode(updateOb);

//            String orderNo = lockBy.replace("sorting-", "");
            String orderNo = pallet.getUserDefined1();

            if(!orderNo.contains("PalletOutVirtualOrderNo")){
                WmsOrderOutStereoscopicDeail searchOb = new WmsOrderOutStereoscopicDeail();
                searchOb.setOrderNo(orderNo);
                searchOb.setGoodsCode(pallet.getGoodsCode());
                searchOb.setBatchNo(pallet.getBatchNo());
                List<WmsOrderOutStereoscopicDeail> deailList = wmsOrderOutStereoscopicDeailMapper.queryByAny(searchOb);
                if(deailList != null && !deailList.isEmpty()){
                    WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail = deailList.get(0);
                    WmsOrderOutStereoscopicDeail detailOb = new WmsOrderOutStereoscopicDeail();
                    detailOb.setOrderOutDetailId(wmsOrderOutStereoscopicDeail.getOrderOutDetailId());
                    detailOb.setRealAmountAdd(pallet.getAmount());
                    detailOb.setLastModifiedBy(account);
                    detailOb.setGmtModified(now);
//                  将立库出库明细表当前商品+批次的实际数量增加
                    wmsOrderOutStereoscopicDeailMapper.updateByPrimaryKeySelective(detailOb);
                }

                WmsOrderOutStereoscopicDeail completeOb = new WmsOrderOutStereoscopicDeail();
                completeOb.setOrderNo(orderNo);
                completeOb.setPlanAmountMoreRealAmount("PlanAmountMoreRealAmount");
                completeOb.setActiveFlag("1");
                List<WmsOrderOutStereoscopicDeail> completeList= wmsOrderOutStereoscopicDeailMapper.queryByAny(completeOb);
//                  发货完成，计划数量>实际数量的记录不存在
                if(completeList == null || completeList.isEmpty()){
                    WmsOrderOutStereoscopic record = new WmsOrderOutStereoscopic();
                    record.setOrderNo(orderNo);
//                      订单状态1创建2启动3完成4异常
                    record.setOrderStatus("3");
                    record.setLastModifiedBy(account);
                    record.setGmtModified(now);
                    wmsOrderOutStereoscopicMapper.updateByOrderNo(record);
                }
            }

            List<WmsGoods> wmsGoodsList = wmsGoodsMapper.selectByGoodsCode(pallet.getGoodsCode());

            List<WmsOutTemporary> wmsOutTemporaryList = wmsOutTemporaryMapper.selectByGoodsCode(pallet.getGoodsCode());
            if (wmsOutTemporaryList != null && !wmsOutTemporaryList.isEmpty()) {
//                增加出库暂存区库存
                WmsOutTemporary updateOutTemporary = new WmsOutTemporary();
                updateOutTemporary.setGoodsCode(pallet.getGoodsCode());
                updateOutTemporary.setAmount(pallet.getAmount());
                updateOutTemporary.setLastModifiedBy(account);
                updateOutTemporary.setGmtModified(now);
                wmsOutTemporaryMapper.addAmountByGoodsCode(updateOutTemporary);
            } else {
//                如果出库暂存区记录不存在就创建一条记录
                WmsOutTemporary wmsOutTemporary = new WmsOutTemporary();
                if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                    wmsOutTemporary.setGoodsName(wmsGoodsList.get(0).getGoodsName());
                }
                wmsOutTemporary.setId(CommonUtil.getUUID());
                wmsOutTemporary.setGoodsCode(pallet.getGoodsCode());
                wmsOutTemporary.setAmount(pallet.getAmount());
                wmsOutTemporary.setCreateBy(account);
                wmsOutTemporary.setGmtCreate(now);
                wmsOutTemporaryMapper.insertSelective(wmsOutTemporary);
            }

            WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
            wmsBoxBarcode.setPalletCode(pallet.getPalletCode());
            List<WmsBoxBarcode> wmsBoxBarcodeList = wmsBoxBarcodeMapper.selectByPalletCode(wmsBoxBarcode);
//            将托盘的箱码从wmsBoxBarcode迁移到wmsOutTemporaryBoxBarcode
            if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                wmsBoxBarcodeMapper.deleteByPalletCode(wmsBoxBarcode);
                List<WmsOutTemporaryBoxBarcode> wmsOutTemporaryBoxBarcodeList = new ArrayList<WmsOutTemporaryBoxBarcode>();
                for (WmsBoxBarcode tmp : wmsBoxBarcodeList) {
                    WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode = new WmsOutTemporaryBoxBarcode();
                    wmsOutTemporaryBoxBarcode.setId(CommonUtil.getUUID());
                    wmsOutTemporaryBoxBarcode.setOrderNo(orderNo);
                    wmsOutTemporaryBoxBarcode.setGoodsCode(pallet.getGoodsCode());
                    if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                        wmsOutTemporaryBoxBarcode.setGoodsName(wmsGoodsList.get(0).getGoodsName());
                    }
                    wmsOutTemporaryBoxBarcode.setBatchNo(pallet.getBatchNo());
                    wmsOutTemporaryBoxBarcode.setBoxBarcode(tmp.getBoxBarcode());
                    wmsOutTemporaryBoxBarcode.setCreateBy(account);
                    wmsOutTemporaryBoxBarcode.setGmtCreate(now);
                    wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                }
                wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
            }
        }

        resp.setCode("1");
        resp.setData(null);
        return resp;
    }

    /**
      * @Description 拆托分拣
      * @Author luot
      * @Date 2020/2/24 17:40
      * @Param
      * @return
      **/
    @Transactional
    @Override
    public Resp splitsubmit(String palletCode, Integer realAmount, String isPalletBack, List<String> boxBarcodeList, String account){
        Resp resp = new Resp();
        Date now = new Date();

        List<WmsPallet> pallets = wmsPalletMapper.selectByCode(palletCode);
        if (pallets == null || pallets.isEmpty()) {
            resp.setCode("0");
            resp.setMessage("查询不到该托盘");
            return resp;
        } else {
            WmsPallet pallet = pallets.get(0);
            String lockBy = pallet.getLockBy();
            if (lockBy == null || "".equals(lockBy)) {
                resp.setCode("0");
                resp.setMessage("该托盘锁定信息有误！");
                return resp;
            }

            WmsPallet updateOb = new WmsPallet();
            updateOb.setPalletCode(pallet.getPalletCode());
            updateOb.setLockByNull("null");
            updateOb.setLocationCodeNull("null");
//                    途径库位可能存在，清掉
            updateOb.setChannelLocationNull("null");
//            托盘是否返库【0：返库 1：入暂存区】
            if(isPalletBack != null && "0".equals(isPalletBack)){
                updateOb.setAmountDel(realAmount);
            }else{
                updateOb.setGoodsCodeNull("null");
                updateOb.setBatchNoNull("null");
                updateOb.setAmountNull("null");
            }
            updateOb.setLastModifiedBy(account);
            updateOb.setGmtModified(now);
            wmsPalletMapper.updateByCode(updateOb);

            String orderNo = lockBy.replace("sorting-", "");

            if(!orderNo.contains("PalletOutVirtualOrderNo")){
                WmsOrderOutStereoscopicDeail searchOb = new WmsOrderOutStereoscopicDeail();
                searchOb.setOrderNo(orderNo);
                searchOb.setGoodsCode(pallet.getGoodsCode());
                searchOb.setBatchNo(pallet.getBatchNo());
                List<WmsOrderOutStereoscopicDeail> deailList = wmsOrderOutStereoscopicDeailMapper.queryByAny(searchOb);
                if(deailList != null && !deailList.isEmpty()){
                    WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail = deailList.get(0);
                    WmsOrderOutStereoscopicDeail detailOb = new WmsOrderOutStereoscopicDeail();
                    detailOb.setOrderOutDetailId(wmsOrderOutStereoscopicDeail.getOrderOutDetailId());
                    detailOb.setRealAmountAdd(realAmount);
                    detailOb.setLastModifiedBy(account);
                    detailOb.setGmtModified(now);
//                      将立库出库明细表当前商品+批次的实际数量增加
                    wmsOrderOutStereoscopicDeailMapper.updateByPrimaryKeySelective(detailOb);
                }

                WmsOrderOutStereoscopicDeail completeOb = new WmsOrderOutStereoscopicDeail();
                completeOb.setOrderNo(orderNo);
                completeOb.setPlanAmountMoreRealAmount("PlanAmountMoreRealAmount");
                completeOb.setActiveFlag("1");
                List<WmsOrderOutStereoscopicDeail> completeList= wmsOrderOutStereoscopicDeailMapper.queryByAny(completeOb);
//                  发货完成
                if(completeList == null || completeList.isEmpty()){
                    WmsOrderOutStereoscopic record = new WmsOrderOutStereoscopic();
                    record.setOrderNo(orderNo);
//                      订单状态1创建2启动3完成4异常
                    record.setOrderStatus("3");
                    record.setLastModifiedBy(account);
                    record.setGmtModified(now);
                    wmsOrderOutStereoscopicMapper.updateByOrderNo(record);
                }
            }


            List<WmsGoods> wmsGoodsList = wmsGoodsMapper.selectByGoodsCode(pallet.getGoodsCode());

            List<WmsOutTemporary> wmsOutTemporaryList = wmsOutTemporaryMapper.selectByGoodsCode(pallet.getGoodsCode());
            if (wmsOutTemporaryList != null && !wmsOutTemporaryList.isEmpty()) {
                WmsOutTemporary updateOutTemporary = new WmsOutTemporary();
                updateOutTemporary.setGoodsCode(pallet.getGoodsCode());
//                托盘是否返库【0：返库 1：入暂存区】
                if(isPalletBack != null && "0".equals(isPalletBack)){
                    updateOutTemporary.setAmount(realAmount);
                }else{
                    updateOutTemporary.setAmount(pallet.getAmount());
                }
                updateOutTemporary.setLastModifiedBy(account);
                updateOutTemporary.setGmtModified(now);
                wmsOutTemporaryMapper.addAmountByGoodsCode(updateOutTemporary);
            } else {
                WmsOutTemporary wmsOutTemporary = new WmsOutTemporary();
                if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                    wmsOutTemporary.setGoodsName(wmsGoodsList.get(0).getGoodsName());
                }
                wmsOutTemporary.setId(CommonUtil.getUUID());
                wmsOutTemporary.setGoodsCode(pallet.getGoodsCode());
//                托盘是否返库【0：返库 1：入暂存区】
                if(isPalletBack != null && "0".equals(isPalletBack)){
                    wmsOutTemporary.setAmount(realAmount);
                }else{
                    wmsOutTemporary.setAmount(pallet.getAmount());
                }
                wmsOutTemporary.setCreateBy(account);
                wmsOutTemporary.setGmtCreate(now);
                wmsOutTemporaryMapper.insertSelective(wmsOutTemporary);
            }

//            托盘是否返库【0：返库 1：入暂存区】
            if(isPalletBack != null && "0".equals(isPalletBack)){
                if(boxBarcodeList != null && !boxBarcodeList.isEmpty()){
                    wmsBoxBarcodeMapper.deleteByBoxBarcodeBatch(boxBarcodeList);

                    List<WmsOutTemporaryBoxBarcode> wmsOutTemporaryBoxBarcodeList = new ArrayList<WmsOutTemporaryBoxBarcode>();
                    for (String boxBarcode : boxBarcodeList) {
                        WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode = new WmsOutTemporaryBoxBarcode();
                        wmsOutTemporaryBoxBarcode.setId(CommonUtil.getUUID());
                        wmsOutTemporaryBoxBarcode.setOrderNo(orderNo);
                        wmsOutTemporaryBoxBarcode.setGoodsCode(pallet.getGoodsCode());
                        if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                            wmsOutTemporaryBoxBarcode.setGoodsName(wmsGoodsList.get(0).getGoodsName());
                        }
                        wmsOutTemporaryBoxBarcode.setBatchNo(pallet.getBatchNo());
                        wmsOutTemporaryBoxBarcode.setBoxBarcode(boxBarcode);
                        wmsOutTemporaryBoxBarcode.setCreateBy(account);
                        wmsOutTemporaryBoxBarcode.setGmtCreate(now);
                        wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                    }
                    wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
                }
            }else{
                WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
                wmsBoxBarcode.setPalletCode(pallet.getPalletCode());
                List<WmsBoxBarcode> wmsBoxBarcodeList = wmsBoxBarcodeMapper.selectByPalletCode(wmsBoxBarcode);
                if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                    wmsBoxBarcodeMapper.deleteByPalletCode(wmsBoxBarcode);
                    List<WmsOutTemporaryBoxBarcode> wmsOutTemporaryBoxBarcodeList = new ArrayList<WmsOutTemporaryBoxBarcode>();
                    for (WmsBoxBarcode tmp : wmsBoxBarcodeList) {
                        WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode = new WmsOutTemporaryBoxBarcode();
                        wmsOutTemporaryBoxBarcode.setId(CommonUtil.getUUID());
                        wmsOutTemporaryBoxBarcode.setOrderNo(orderNo);
                        wmsOutTemporaryBoxBarcode.setGoodsCode(pallet.getGoodsCode());
                        if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                            wmsOutTemporaryBoxBarcode.setGoodsName(wmsGoodsList.get(0).getGoodsName());
                        }
                        wmsOutTemporaryBoxBarcode.setBatchNo(pallet.getBatchNo());
                        wmsOutTemporaryBoxBarcode.setBoxBarcode(tmp.getBoxBarcode());
                        wmsOutTemporaryBoxBarcode.setCreateBy(account);
                        wmsOutTemporaryBoxBarcode.setGmtCreate(now);
                        wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                    }
                    wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
                }
            }
        }

        resp.setCode("1");
        resp.setData(null);
        return resp;
    }

    /**
      * @Description 扫描箱码
      * @Author luot
      * @Date 2020/2/24 17:30
      * @Param
      * @return
      **/
    @Override
    public Resp scanBoxBarcode(String palletCode, String boxBarcode) {
        Resp resp = new Resp();
        WmsBoxBarcodeKey boxBarcodeKey= new WmsBoxBarcodeKey();
        boxBarcodeKey.setBoxBarcode(boxBarcode);
        boxBarcodeKey.setPalletCode(palletCode);
        WmsBoxBarcodeKey key = wmsBoxBarcodeMapper.selectByPrimaryKey(boxBarcodeKey);
        if(key == null ){
            resp.setCode("0");
            resp.setMessage("查询不到该箱码");
            return resp;
        }
        resp.setCode("1");
        resp.setData(key);
        return resp;
    }

    /**
     * 校验箱码是否在托盘内
     *
     * @param palletCode
     * @param boxBarcodeList
     * @return
     */
    @Override
    public boolean checkBarcodesBelongPallet(String palletCode, List<String> boxBarcodeList) {
        return false;
    }
}
