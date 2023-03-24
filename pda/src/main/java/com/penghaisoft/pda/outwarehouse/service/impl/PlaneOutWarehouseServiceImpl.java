package com.penghaisoft.pda.outwarehouse.service.impl;

import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.outwarehouse.dao.WmsOrderOutPlaneDeailMapper;
import com.penghaisoft.pda.outwarehouse.dao.WmsOrderOutPlaneMapper;
import com.penghaisoft.pda.outwarehouse.model.PalletDTO;
import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutPlane;
import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutPlaneDeail;
import com.penghaisoft.pda.outwarehouse.model.WmsOrderOutPlaneDeailDTO;
import com.penghaisoft.pda.outwarehouse.service.PlaneOutWarehouseService;
import com.penghaisoft.pda.storage.dao.*;
import com.penghaisoft.pda.storage.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class PlaneOutWarehouseServiceImpl implements PlaneOutWarehouseService {

    @Autowired
    private WmsOrderOutPlaneMapper orderOutPlaneMapper;

    @Autowired
    private WmsOrderOutPlaneDeailMapper orderOutPlaneDeailMapper;

    @Autowired
    private WmsPalletMapper palletMapper;

    @Autowired
    private WmsBoxBarcodeMapper wmsBoxBarcodeMapper;

    @Resource
    private WmsStoragePlaneMapper storagePlaneMapper;

    @Resource
    private WmsOutTemporaryMapper wmsOutTemporaryMapper;

    @Autowired
    private WmsGoodsMapper goodsMapper;

    @Resource
    private WmsOutTemporaryBoxBarcodeMapper wmsOutTemporaryBoxBarcodeMapper;


    /**
     * 判断出库单状态 （获取出库明细）
     * @param orderNo
     * @param account
     * @param areaCode
     * @return
     */
    @Override
    public Resp lading(String orderNo, String account, String areaCode) {
        Resp resp = new Resp();
        if(orderNo==null){
            resp.setCode("0");
            resp.setMessage("请输入拣配单号");
            return resp;
        }
        //查询出库单（校验状态）
        WmsOrderOutPlane orderOutPlane = new WmsOrderOutPlane();
        orderOutPlane.setOrderNo(orderNo);
        orderOutPlane.setActiveFlag("1");
        List<WmsOrderOutPlane> listOrderMain = orderOutPlaneMapper.queryByAny(orderOutPlane);
        if(listOrderMain.size()<=0){
            resp.setCode("0");
            resp.setMessage("查询不到此拣配单");
            return resp;
        }
        if(!("2".equals(listOrderMain.get(0).getOrderStatus())||"3".equals(listOrderMain.get(0).getOrderStatus()))){
            resp.setCode("0");
            resp.setMessage("此拣配单状态不为已分配或入库中");
            return resp;
        }
        //更改出库单状态
        orderOutPlane.setOrderOutId(listOrderMain.get(0).getOrderOutId());
        //入库中
        orderOutPlane.setOrderStatus("3");
        Integer updateStatus = orderOutPlaneMapper.updateByPrimaryKeySelective(orderOutPlane);
        //状态合格 （查询明细）
        WmsOrderOutPlaneDeail orderOutPlaneDeail = new WmsOrderOutPlaneDeail();
        orderOutPlaneDeail.setOrderNo(orderNo);
        orderOutPlaneDeail.setActiveFlag("1");
        orderOutPlaneDeail.setAreaCode(areaCode);
        List<WmsOrderOutPlaneDeail> detailList = orderOutPlaneDeailMapper.querByAny(orderOutPlaneDeail);
        resp.setCode("1");
        resp.setMessage("成功");
        resp.setData(detailList);
        return resp;
    }

    /**
     * 扫描托盘,获取托盘信息
     * @param palletNo
     * @param account
     * @param areaCode
     * @param goodsCode
     * @param batchNo
     * @param locationCode
     * @return
     */
    @Override
    public Resp scanPalletNo(String palletNo, String account, String areaCode, String goodsCode, String batchNo, String locationCode) {
        Resp resp = new Resp();

        List<WmsPallet> pallets = palletMapper.selectByCode(palletNo);
        if(pallets.size()<=0){
            resp.setCode("0");
            resp.setMessage("查询不到该托盘");
            return resp;
        }
        WmsPallet pallet = pallets.get(0);
        if(pallet.getLockBy()!=null){
            resp.setCode("0");
            resp.setMessage("该托盘被锁定");
            return resp;
        }
        if(pallet.getGoodsCode()==null||"".equals(pallet.getGoodsCode())){
            resp.setCode("0");
            resp.setMessage("该托盘未绑定商品");
            return resp;
        }
        if(!goodsCode.equals(pallet.getGoodsCode())){
            resp.setCode("0");
            resp.setMessage("该托盘绑定商品不符合出库商品");
            return resp;
        }
        if(!batchNo.equals(pallet.getBatchNo())){
            resp.setCode("0");
            resp.setMessage("该托盘绑定批次不符合出库批次");
            return resp;
        }
        if(!locationCode.equals(pallet.getLocationCode())){
            resp.setCode("0");
            resp.setMessage("该托盘绑定库位不符合出库库位");
            return resp;
        }
        if(!areaCode.equals(pallet.getAreaCode())){
            resp.setCode("0");
            resp.setMessage("该托盘库区不符合出库库区");
            return resp;
        }

        resp.setCode("1");
        resp.setData(pallet);
        return resp;
    }

    @Override
    public Resp scanBarcode(String palletCode, String boxBarcode) {
        Resp resp = new Resp();
        WmsBoxBarcodeKey  boxBarcodeKey= new WmsBoxBarcodeKey();
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

    @Override
    @Transactional
    public Resp confirmOrderOut(String orderNo, List<WmsOrderOutPlaneDeailDTO> wmsOrderInfos,String account) {
        Resp resp = new Resp();
        //查询订单状态
        WmsOrderOutPlane orderOutPlane = new WmsOrderOutPlane();
        orderOutPlane.setOrderNo(orderNo);
        orderOutPlane.setActiveFlag("1");
        List<WmsOrderOutPlane> listOrderMain = orderOutPlaneMapper.queryByAny(orderOutPlane);
        if(listOrderMain.size()<=0){
            resp.setCode("0");
            resp.setMessage("查询不到此拣配单");
            return resp;
        }
        if(!("2".equals(listOrderMain.get(0).getOrderStatus())||"3".equals(listOrderMain.get(0).getOrderStatus()))){
            resp.setCode("0");
            resp.setMessage("此拣配单状态不为已分配或入库中");
            return resp;
        }


        Date now =new Date();
        //循环处理 明细数组
        for(WmsOrderOutPlaneDeailDTO orderOutPlaneDetailDTO : wmsOrderInfos){

            String goodsCode = orderOutPlaneDetailDTO.getGoodsCode();
            List<WmsGoods> wmsGoodsList = goodsMapper.selectByGoodsCode(goodsCode);
            String goodsName = "";
            if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                goodsName = wmsGoodsList.get(0).getGoodsName();
            }

            String batchNo = orderOutPlaneDetailDTO.getBatchNo();
            Integer realAmount = orderOutPlaneDetailDTO.getCurrentRealAmount();
            String orderOutDetailId = orderOutPlaneDetailDTO.getOrderOutDetailId();
            String hasBoxCode = orderOutPlaneDetailDTO.getHasBoxCode();
            String locationCode = orderOutPlaneDetailDTO.getLocationCode();


            //1.修改 wms_order_out_plane_deail 出库数real_amount
            WmsOrderOutPlaneDeail planeDeail = new WmsOrderOutPlaneDeail();
            planeDeail.setRealAmount(realAmount);
            planeDeail.setOrderOutDetailId(orderOutDetailId);
            planeDeail.setLastModifiedBy(account);
            planeDeail.setGmtModified(now);
            Integer updateDetail = orderOutPlaneDeailMapper.updateByPrimaryKeySelective(planeDeail);
            //2.修改托盘数据及箱码数据（是否有箱码）
            //循环托盘===================================================================
            List<PalletDTO> palletDTOList = orderOutPlaneDetailDTO.getPallets();
            for (PalletDTO pallet : palletDTOList){
                //查询托盘信息
//                WmsPallet palletNew = new WmsPallet();
//                palletNew.setPalletCode(pallet.getPalletCode());
                List<String> boxBarcodeList = pallet.getBarcodeList();
                Integer palletScanAmount = pallet.getPalletScanAmount();
                List<WmsPallet> pallets = palletMapper.selectByCode(pallet.getPalletCode());
                if(pallets.size()>0){
                    WmsPallet wmsPallet = pallets.get(0);
                    //判断是全部分拣还是部分分拣
                    if(palletScanAmount==wmsPallet.getAmount()){
                        //全部分拣
                        //如果当前托盘全部分拣完，将托盘库位，商品号批次都清空，若有箱码将箱码从wms_box_barcode删除，写入wms_out_temporary_box_barcode
                        Integer deleteNum = palletMapper.deleteByPrimaryKey(wmsPallet.getPalletId());
                        //是否有箱码
                        if("1".equals(hasBoxCode)){
//                            List<String> barcodeList = pallet.getBarcodeList();
                            //查询出全部箱码根据托盘号
//                            List<WmsBoxBarcodeKey> list =  wmsBoxBarcodeMapper.queryByCode(pallet.getPalletCode());

                            //根据托盘号全部删除，转移箱码
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
                                    wmsOutTemporaryBoxBarcode.setGoodsCode(goodsCode);
                                    if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
                                        wmsOutTemporaryBoxBarcode.setGoodsName(goodsName);
                                    }
                                    wmsOutTemporaryBoxBarcode.setBatchNo(batchNo);
                                    wmsOutTemporaryBoxBarcode.setBoxBarcode(tmp.getBoxBarcode());
                                    wmsOutTemporaryBoxBarcode.setCreateBy(account);
                                    wmsOutTemporaryBoxBarcode.setGmtCreate(now);
                                    wmsOutTemporaryBoxBarcode.setActiveFlag("1");
                                    wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                                }
                                wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
                            }
                        }


                    }else {
                        //部分分拣
                        //如果部分分拣，修改托盘表记录的数量amount=amount-real_amount(分拣数)，若有箱码将扫码的箱码从wms_box_barcode删除，写入wms_out_temporary_box_barcode
                        //修改托盘表数量
                        Integer amount = wmsPallet.getAmount();
                        Integer kAmount =amount-realAmount;
                        WmsPallet newPallet = new WmsPallet();
                        newPallet.setAmount(kAmount);
                        newPallet.setPalletCode(pallet.getPalletCode());
                        newPallet.setLastModifiedBy(account);
                        newPallet.setGmtModified(now);
                        int updatePallet = palletMapper.updateByCode(newPallet);
                        //转移箱码
                        //是否有箱码
                        if("1".equals(hasBoxCode)) {
                            if (boxBarcodeList != null && !boxBarcodeList.isEmpty()) {
                                wmsBoxBarcodeMapper.deleteByBoxBarcodeBatch(boxBarcodeList);

                                List<WmsOutTemporaryBoxBarcode> wmsOutTemporaryBoxBarcodeList = new ArrayList<WmsOutTemporaryBoxBarcode>();
                                for (String boxBarcode : boxBarcodeList) {
                                    WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode = new WmsOutTemporaryBoxBarcode();
                                    wmsOutTemporaryBoxBarcode.setId(CommonUtil.getUUID());
                                    wmsOutTemporaryBoxBarcode.setOrderNo(orderNo);
                                    wmsOutTemporaryBoxBarcode.setGoodsCode(goodsCode);
                                    wmsOutTemporaryBoxBarcode.setGoodsName(goodsName);
                                    wmsOutTemporaryBoxBarcode.setBatchNo(batchNo);
                                    wmsOutTemporaryBoxBarcode.setBoxBarcode(boxBarcode);
                                    wmsOutTemporaryBoxBarcode.setCreateBy(account);
                                    wmsOutTemporaryBoxBarcode.setGmtCreate(now);
                                    wmsOutTemporaryBoxBarcode.setActiveFlag("1");
                                    wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                                }
                                wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
                            }
                        }
                    }
                }else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    resp.setCode("0");
                    resp.setMessage("查询不到该托盘信息");
                    return resp;
                }

            }
            //循环托盘====完结==============================================================
            //3.扣减平库冻结库存：根据分拣的商品 数量批次 扣减wms_storage_plane的lock_amount（之前分配库存时冻结的）
            //根据分拣的商品 批次 查询平库库存
            WmsStoragePlane storagePlane  =  new WmsStoragePlane();
            storagePlane.setGoodsCode(goodsCode);
            storagePlane.setBatchNo(batchNo);
            storagePlane.setLocationCode(locationCode);
            storagePlane.setActiveFlag("1");
            List<WmsStoragePlane> planeList = storagePlaneMapper.queryByAny(storagePlane);
            if(planeList.size()<=0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resp.setCode("0");
                resp.setMessage("查询不到平库库存");
                return resp;
            }
            Integer lockAmount = planeList.get(0).getLockAmount();
            if (lockAmount<realAmount){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resp.setCode("0");
                resp.setMessage("冻结数量不够扣减");
                return resp;
            }
            WmsStoragePlane updatePlane = new WmsStoragePlane();
            updatePlane.setStoragePlaneId(planeList.get(0).getStoragePlaneId());
            updatePlane.setLockAmount(lockAmount-realAmount);
            updatePlane.setLastModifiedBy(account);
            updatePlane.setGmtModified(now);
            int upAmount = storagePlaneMapper.updateByPrimaryKeySelective(updatePlane);
            if (upAmount<=0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resp.setCode("0");
                resp.setMessage("扣减冻结数量错误");
                return resp;
            }
            //4.增加出库暂存区库存：根据商品号，累加wms_out_temporary的amount
            WmsOutTemporary wmsOutTemporary = new WmsOutTemporary();
            wmsOutTemporary.setGoodsCode(goodsCode);
            List<WmsOutTemporary> wmsOutTemporaryList = wmsOutTemporaryMapper.selectByGoodsCode(goodsCode);
            if (wmsOutTemporaryList.size()>0){
                //更新数量
                wmsOutTemporary.setAmount(realAmount);
                wmsOutTemporary.setLastModifiedBy(account);
                wmsOutTemporary.setGmtModified(now);
                wmsOutTemporaryMapper.addAmountByGoodsCode(wmsOutTemporary);
            }else {
                //新增
                wmsOutTemporary.setGoodsName(goodsName);
                wmsOutTemporary.setId(CommonUtil.getUUID());
                wmsOutTemporary.setAmount(realAmount);
                wmsOutTemporary.setCreateBy(account);
                wmsOutTemporary.setGmtCreate(now);
                wmsOutTemporaryMapper.insertSelective(wmsOutTemporary);
            }

        }
        //修改出库主表状态为完成
        //更改出库单状态
        orderOutPlane.setOrderOutId(listOrderMain.get(0).getOrderOutId());
        //已完成
        orderOutPlane.setOrderStatus("4");
        Integer updateStatus = orderOutPlaneMapper.updateByPrimaryKeySelective(orderOutPlane);
        resp.setCode("1");
        resp.setMessage("确认完成");
        return resp;
    }
}