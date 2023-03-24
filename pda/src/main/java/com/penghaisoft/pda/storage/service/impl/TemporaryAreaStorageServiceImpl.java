package com.penghaisoft.pda.storage.service.impl;

import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.dao.*;
import com.penghaisoft.pda.storage.model.*;
import com.penghaisoft.pda.storage.service.TemporaryAreaStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 暂存区库存服务,包括入库，出库暂存区
 * @Description TemporaryAreaStorageServiceImpl
 * @Auther zhangxu
 * @Date 2020/2/19 9:13
 **/
@Slf4j
@Service
public class TemporaryAreaStorageServiceImpl implements TemporaryAreaStorageService {

    @Autowired
    private WmsInTemporaryMapper inTemporaryMapper;

    @Autowired
    private WmsOutTemporaryMapper outTemporaryMapper;

    @Autowired
    private WmsOutTemporaryBoxBarcodeMapper outTemporaryBoxBarcodeMapper;

    @Autowired
    private WmsSendLogMapper sendLogMapper;

    @Autowired
    private WmsGoodsMapper goodsMapper;

    @Autowired
    private WmsPalletMapper palletMapper;

    @Autowired
    private WmsBoxBarcodeMapper boxBarcodeMapper;

    /**
     * 变化入库暂存区商品数量
     *
     * @param goodsCode
     * @param amount 可以为负数，代表扣除
     * @param account
     */
    @Override
    public void addTemporaryInStorage(String goodsCode, Integer amount, String account) {
//        1 商品是否在入库缓存区中存在
        List<WmsInTemporary> inTemporaryList = inTemporaryMapper.selectByGoods(goodsCode);
        if (null!=inTemporaryList && inTemporaryList.size()>0){
//            变化数量
            WmsInTemporary cond = new WmsInTemporary();
            cond.setGoodsCode(goodsCode);
            cond.setAmount(amount);
            cond.setLastModifiedBy(account);
            inTemporaryMapper.updateAmountByGoods(cond);
        }else {
//            理论上制单时候选择的商品表数据，不可能出现商品表中本goodsCode无记录情况，
//            除非制单完成后给删除了，这属于人为事故。

//            插入新数量的数据
//            先查找商品
            Date date = new Date();
            List<WmsGoods> goods = goodsMapper.selectByGoodsCode(goodsCode);
            if (null!=goods && goods.size()>0){
                WmsGoods wmsGoods = goods.get(0);
                WmsInTemporary cond = new WmsInTemporary();
                cond.setId(CommonUtil.getUUID());
                cond.setGoodsCode(goodsCode);
                cond.setGoodsName(wmsGoods.getGoodsName());
                cond.setAmount(amount);
                cond.setGmtCreate(date);
                cond.setCreateBy(account);
                cond.setActiveFlag("1");
                inTemporaryMapper.insertSelective(cond);
            }else {
                log.error("收货入暂存区时，商品表【"+goodsCode+"】不存在！！！");
            }
        }
    }

    /**
     * 托盘商品绑定，这是在收货暂存区绑定的
     *
     * @param palletCode
     * @param goodsCode
     * @param amount
     * @param batchNo
     * @param account
     * @param boxCodes
     */
    @Transactional
    @Override
    public void bindPalletGoods(String palletCode, String goodsCode, Integer amount, String batchNo, String account, List<String> boxCodes,String user3) {
        Date now = new Date();
//        1 将goodsCode，batchNo，amount写入托盘表当前托盘号信息中，
        WmsPallet pallet = new WmsPallet();
        pallet.setPalletCode(palletCode);
        pallet.setGoodsCode(goodsCode);
        pallet.setAmount(amount);
        pallet.setBatchNo(batchNo);
        pallet.setLastModifiedBy(account);
        pallet.setGmtModified(now);
        pallet.setUserDefined3(user3);
        palletMapper.updateByCode(pallet);

//        2 如果存在箱码，则将以逗号分割的boxBarcode以及托盘号写入wms_box_barcode
        if (null!=boxCodes && boxCodes.size()>0){
            List<WmsBoxBarcode> boxBarcodeList = new ArrayList<>();
            for (String boxCode:boxCodes) {
                WmsBoxBarcode boxBarcode = new WmsBoxBarcode();
                boxBarcode.setPalletCode(palletCode);
                boxBarcode.setBoxBarcode(boxCode);
                boxBarcode.setGmtCreate(now);
                boxBarcode.setCreateBy(account);
                boxBarcode.setActiveFlag("1");
                boxBarcodeList.add(boxBarcode);
            }
            boxBarcodeMapper.batchInsert(boxBarcodeList);
        }

//        3 扣减wms_in_temporary入库暂存库存数量，根据商品号即可(不能扣减成负数！！！)
//        WmsInTemporary cond = new WmsInTemporary();
//        cond.setGoodsCode(goodsCode);
////        这里是扣减
//        cond.setAmount(-amount);
//        cond.setLastModifiedBy(account);
//        inTemporaryMapper.updateAmountByGoods(cond);
    }

    /**
     * 根据商品号查询出库缓存区信息
     *
     * @param goodsCode
     * @return
     */
    @Override
    public WmsOutTemporary getTemporaryOutInfo(String goodsCode) {
        WmsOutTemporary result = null;
        List<WmsOutTemporary> outTemporaryList = outTemporaryMapper.selectByGoodsCode(goodsCode);
//        商品编号是唯一索引有且只有0或1条记录
        if (null!=outTemporaryList && outTemporaryList.size()>0){
            result = outTemporaryList.get(0);
        }
        return result;
    }

    /**
     * 发货暂存区发货
     *
     * @param account
     * @param orderNo
     * @param goodsCode
     * @param amount
     * @param boxCodes
     * @return
     */
    @Transactional
    @Override
    public Resp temporaryOutSend(String account, String orderNo, String goodsCode, Integer amount, List<String> boxCodes) {
        Resp resp = new Resp();
        resp.setCode("1");
        String haxBoxCode = "0";
        //      1 根据商品编号，查询wms_out_temporary中库存信息，sendAmount不能大于amount，否则给出提示；
        List<WmsOutTemporary> outTemporaryList = outTemporaryMapper.selectByGoodsCode(goodsCode);
        WmsOutTemporary outTemporary = null;
        if (outTemporaryList.size()>0){
            outTemporary = outTemporaryList.get(0);
            if (amount > outTemporary.getAmount()){
                resp.setCode("0");
                resp.setMessage("发货数大于库存数！");
            }
        }else{
            resp.setCode("0");
            resp.setMessage("发货区无此商品！");
        }
//        校验失败返回
        if ("0".equals(resp.getCode())){
            return resp;
        }

        Date now = new Date();

//      2 如果有箱码，要将wms_out_temporary_box_barcode中active_flag=0，send_no=发货单号
        if (boxCodes.size() > 0){
            for (String barcode:boxCodes) {
                List<WmsOutTemporaryBoxBarcode> outTemporaryBoxBarcodes = outTemporaryBoxBarcodeMapper.selectByBoxBarcode(barcode);
                if (outTemporaryBoxBarcodes.size()!=1){
                    resp.setCode("0");
                    resp.setMessage("发货区无此箱码！");
                    return resp;
                }
            }
            for (String barcode:boxCodes) {
                WmsOutTemporaryBoxBarcode deleteFake = new WmsOutTemporaryBoxBarcode();
                deleteFake.setBoxBarcode(barcode);
                deleteFake.setSendNo(orderNo);
                deleteFake.setLastModifiedBy(account);
                deleteFake.setGmtModified(now);
                outTemporaryBoxBarcodeMapper.deleteByBoxBarcodeFake(deleteFake);
            }
            haxBoxCode = "1";
        }


//      3 根据商品编号扣减wms_out_temporary中库存；
        WmsOutTemporary updateOutTemporary = new WmsOutTemporary();
        updateOutTemporary.setGoodsCode(goodsCode);
        updateOutTemporary.setAmount(-amount);
        updateOutTemporary.setLastModifiedBy(account);
        updateOutTemporary.setGmtModified(now);
        outTemporaryMapper.addAmountByGoodsCode(updateOutTemporary);

//      4 写入发货日志表wms_send_log；
        WmsSendLog sendLog = new WmsSendLog();
        sendLog.setSendId(CommonUtil.getUUID());
        sendLog.setGoodsCode(goodsCode);
        sendLog.setGoodsName(outTemporary.getGoodsName());
        sendLog.setHasBoxCode(haxBoxCode);
        sendLog.setSendNo(orderNo);
        sendLog.setSendAmount(amount);
        sendLog.setCreateBy(account);
        sendLog.setGmtCreate(now);
        sendLog.setActiveFlag("1");
        sendLogMapper.insertSelective(sendLog);
        return resp;
    }

    /**
     * 能否扣减入库暂存区库存（看库存够不够）
     *
     * @param goodsCode
     * @param amount 要扣减数量
     * @return
     */
    @Override
    public boolean canMinusTemporaryInStorage(String goodsCode,Integer amount) {
        boolean flag = false;
        List<WmsInTemporary> inTemporaryList = inTemporaryMapper.selectByGoods(goodsCode);
        if (inTemporaryList!=null && inTemporaryList.size()==1){
            WmsInTemporary inTemporary = inTemporaryList.get(0);
            Integer realAmount = inTemporary.getAmount();
            if (realAmount >= amount){
                flag = true;
            }
        }
        return flag;
    }
}
