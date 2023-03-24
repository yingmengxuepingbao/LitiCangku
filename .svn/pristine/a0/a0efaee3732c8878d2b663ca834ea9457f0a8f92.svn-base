package com.penghaisoft.pda.storage.service.impl;

import com.penghaisoft.pda.basic.dao.WmsPlaneLocationMapper;
import com.penghaisoft.pda.basic.dao.WmsWarehouseAreaMapper;
import com.penghaisoft.pda.basic.model.WmsPlaneLocation;
import com.penghaisoft.pda.basic.model.WmsWarehouseArea;
import com.penghaisoft.pda.common.CommonUtil;
import com.penghaisoft.pda.common.Resp;
import com.penghaisoft.pda.storage.dao.*;
import com.penghaisoft.pda.storage.model.*;
import com.penghaisoft.pda.storage.service.PlaneStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlaneStorageServiceImpl implements PlaneStorageService {

    @Autowired
    private WmsPalletMapper palletMapper;

    @Autowired
    private WmsWarehouseAreaMapper warehouseAreaMapper;

    @Autowired
    private WmsStoragePlaneMapper storagePlaneMapper;

    @Autowired
    private WmsMoveLogMapper wmsMoveLogMapper;

    @Autowired
    private WmsGoodsMapper goodsMapper;

    @Autowired
    private WmsBoxBarcodeMapper wmsBoxBarcodeMapper;

    @Autowired
    private WmsCombineLogMapper wmsCombineLogMapper;

    @Autowired
    private WmsOrderCheckPalletMapper wmsOrderCheckPalletMapper;

    @Autowired
    private WmsOrderCheckPalletBoxBarcodeMapper wmsOrderCheckPalletBoxBarcodeMapper;

    @Resource
    private WmsOrderCheckMapper wmsOrderCheckMapper;

    @Resource
    private WmsPlaneLocationMapper wmsPlaneLocationMapper;

    /**
     * 平库入库提交
     * @param palletCode
     * @param locationCode
     * @param areaCode
     * @param goodsCode
     * @param batchNo
     * @param amount
     * @param account
     * @return
     */
    @Override
    @Transactional
    public Resp submitInbound(String palletCode, String locationCode, String areaCode, String goodsCode, String batchNo, Integer amount, String account) {
        Resp resp = new Resp();
        //根据库区查询查库信息
        List<WmsWarehouseArea> areaList = warehouseAreaMapper.queryAreaInfoByCode(areaCode);
        if(areaList.size()<=0){
            resp.setCode("1");
            resp.setMessage("查询不到此库区");
            return resp;
        }
        //获取到仓库编码
        String whCode = areaList.get(0).getWarehouseCode();

        //first 增加平库库存
        WmsStoragePlane storagePlane  =  new WmsStoragePlane();
        storagePlane.setGoodsCode(goodsCode);
        storagePlane.setBatchNo(batchNo);
        storagePlane.setLocationCode(locationCode);
        storagePlane.setActiveFlag("1");
        List<WmsStoragePlane> planeList = storagePlaneMapper.queryByAny(storagePlane);
        if(planeList.size()>0){
            //平库库存中可以查询到
            WmsStoragePlane plane = planeList.get(0);
            //获取可用数量
            Integer availableAmount = plane.getAvailableAmount();
            //增加数量
            plane.setAvailableAmount(availableAmount+amount);
            plane.setLastModifiedBy(account);
            plane.setGmtModified(new Date());
            int updateAmount =  storagePlaneMapper.updateByPrimaryKeySelective(plane);

        }else {
            //创建平库库存
            WmsStoragePlane newPlane = new WmsStoragePlane();
            newPlane.setStoragePlaneId(CommonUtil.getUUID());

            newPlane.setWarehouseCode(whCode);
            newPlane.setAreaCode(areaCode);
            newPlane.setLocationCode(locationCode);
            newPlane.setGoodsCode(goodsCode);
            newPlane.setBatchNo(batchNo);
            newPlane.setAvailableAmount(amount);
            newPlane.setLockAmount(0);
            newPlane.setActiveFlag("1");
            newPlane.setCreateBy(account);
            newPlane.setGmtCreate(new Date());
            int insertNum = storagePlaneMapper.insertSelective(newPlane);
        }
        //第二步 修改托盘表
        WmsPallet newPallet = new WmsPallet();
        newPallet.setWarehouseCode(whCode);
        newPallet.setAreaCode(areaCode);
        newPallet.setLocationCode(locationCode);
        newPallet.setPalletCode(palletCode);
        newPallet.setLastModifiedBy(account);
        newPallet.setGmtModified(new Date());
        int updatePallet = palletMapper.updateByCode(newPallet);

        resp.setCode("0");
        resp.setMessage("完成");
        return resp;
    }

    /**
     * 平库移库
     * @param palletCode
     * @param fromLocationCode
     * @param toLocationCode
     * @param areaCode
     * @param account
     * @param pallet
     * @return
     */
    @Override
    @Transactional
    public Resp submitMove(String palletCode, String fromLocationCode, String toLocationCode, String areaCode, String account, WmsPallet pallet) {
        Resp resp = new Resp();
        Date now = new Date();
        //1.通过校验后在wms_pallet中将locationCode改为toLocationCode；
        WmsPallet newPallet = new WmsPallet();

        newPallet.setLocationCode(toLocationCode);
        newPallet.setPalletCode(palletCode);
        newPallet.setLastModifiedBy(account);
        newPallet.setGmtModified(now);
        int updatePallet = palletMapper.updateByCode(newPallet);
        //2.在wms_storage_plane中根据商品号，批次，库位扣减fromLocationCode的库存可用数量，增加toLocationCode的库存可用数量；
        WmsStoragePlane storagePlane = new WmsStoragePlane();
        storagePlane.setGoodsCode(pallet.getGoodsCode());
        storagePlane.setBatchNo(pallet.getBatchNo());
        storagePlane.setLocationCode(fromLocationCode);
        List<WmsStoragePlane> list = storagePlaneMapper.queryByAny(storagePlane);
        if (list.size()<=0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resp.setCode("0");
            resp.setMessage("异常,查询托盘不到库存");
            return resp;
        }
        if(pallet.getAmount()>list.get(0).getAvailableAmount()){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            resp.setCode("0");
            resp.setMessage("异常,库存数量不足");
            return resp;
        }
        int updateQty = list.get(0).getAvailableAmount()-pallet.getAmount();
        //可用调整
        storagePlane.setAvailableAmount(updateQty);
        storagePlane.setLastModifiedBy(account);
        storagePlane.setGmtModified(now);
        storagePlane.setStoragePlaneId(list.get(0).getStoragePlaneId());
        int updateAmount =  storagePlaneMapper.updateByPrimaryKeySelective(storagePlane);

        //增加新库位库存
        //查询库存记录存在
        WmsStoragePlane storage = new WmsStoragePlane();
        storage.setGoodsCode(pallet.getGoodsCode());
        storage.setBatchNo(pallet.getBatchNo());
        storage.setLocationCode(toLocationCode);
        List<WmsStoragePlane> storageList = storagePlaneMapper.queryByAny(storage);
        //获取到仓库编码
        String whCode ="";
        //根据库区查询查库信息
        List<WmsWarehouseArea> areaList = warehouseAreaMapper.queryAreaInfoByCode(areaCode);
        if(areaList.size()>0){
            whCode = areaList.get(0).getWarehouseCode();
        }
        if (storageList.size()>0){
            //更新库存
            int addQty = storageList.get(0).getAvailableAmount() + pallet.getAmount();
            storage.setAvailableAmount(addQty);
            storage.setLastModifiedBy(account);
            storage.setGmtModified(now);
            storage.setStoragePlaneId(storageList.get(0).getStoragePlaneId());
            int updateStorage =  storagePlaneMapper.updateByPrimaryKeySelective(storage);

        }else {
            //新增库存
            storage.setStoragePlaneId(CommonUtil.getUUID());

            storage.setWarehouseCode(whCode);
            storage.setAreaCode(areaCode);
            storage.setAvailableAmount(pallet.getAmount());
            storage.setLockAmount(0);
            storage.setCreateBy(account);
            storage.setGmtCreate(now);
            storage.setActiveFlag("1");
            int insertNum = storagePlaneMapper.insert(storage);

        }

        //3.记录日志到wms_move_log;
        WmsMoveLog wmsMoveLog = new WmsMoveLog();
        wmsMoveLog.setMoveLogId(CommonUtil.getUUID());
        wmsMoveLog.setPalletCode(palletCode);
        wmsMoveLog.setWarehouseCode(whCode);
        wmsMoveLog.setAreaCode(areaCode);
        wmsMoveLog.setFromLocationCode(fromLocationCode);
        wmsMoveLog.setToLocationCode(toLocationCode);
        wmsMoveLog.setMoveResult("1");
        wmsMoveLog.setCreateBy(account);
        wmsMoveLog.setGmtCreate(now);
        wmsMoveLog.setActiveFlag("1");
        wmsMoveLog.setGoodsCode(pallet.getGoodsCode());
        wmsMoveLog.setBatchNo(pallet.getBatchNo());
        wmsMoveLog.setAmount(pallet.getAmount());
        int intMove = wmsMoveLogMapper.insert(wmsMoveLog);

        resp.setCode("1");
        resp.setMessage("移库成功");
        return resp;
    }

    /**
     * 扫描目的托盘
     * @param palletCode
     * @param account
     * @param areaCode
     * @param locationCode
     * @return
     */
    @Override
    public Resp scanPallet(String palletCode, String account, String areaCode, String locationCode) {
        Resp resp = new Resp();
        List<WmsPallet> list = palletMapper.selectByCode(palletCode);
        if (list.size()<=0){
            resp.setCode("0");
            resp.setMessage("查询不到此托盘");
            return resp;
        }
        WmsPallet pallet = list.get(0);
        String palletLocation = pallet.getLocationCode();
        //目标库位校验
        if (!locationCode.equals(palletLocation)){
            resp.setCode("0");
            resp.setMessage("此托盘不属于该目标库位");
            return resp;
        }
        //库区校验
        if (!areaCode.equals(pallet.getAreaCode())){
            resp.setCode("0");
            resp.setMessage("此托盘不属于当前库区");
            return resp;
        }
        if(pallet.getGoodsCode()==null||"".equals(pallet.getGoodsCode())){
            resp.setCode("0");
            resp.setMessage("该托盘未绑定商品");
            return resp;
        }
        if(pallet.getLockBy()!=null){
            resp.setCode("0");
            resp.setMessage("该托盘被锁定");
            return resp;
        }
        String goodsCode = pallet.getGoodsCode();
        List<WmsGoods> wmsGoodsList = goodsMapper.selectByGoodsCode(goodsCode);
        String goodsName = "";
        if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
            goodsName = wmsGoodsList.get(0).getGoodsName();
        }
        pallet.setGoodsName(goodsName);
        resp.setCode("1");
        resp.setData(pallet);
        return resp;
    }

    @Override
    public Resp scanFromPalletCode(String goodsCode, String batchNo, String account, String areaCode, String fromPalletCode) {
        Resp resp = new Resp();
        List<WmsPallet> pallets = palletMapper.selectByCode(fromPalletCode);
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
            resp.setMessage("该托盘绑定商品不符合");
            return resp;
        }
        if(!batchNo.equals(pallet.getBatchNo())){
            resp.setCode("0");
            resp.setMessage("该托盘绑定批次不符合");
            return resp;
        }
        if(!areaCode.equals(pallet.getAreaCode())){
            resp.setCode("0");
            resp.setMessage("该托盘库区不符合");
            return resp;
        }
        resp.setCode("1");
        resp.setMessage("成功");
        resp.setData(pallet);
        return resp;
    }


    /**
     * 合并确认
     * jzh
     * @param toPalletCodeText
     * @param palletList
     * @param account
     * @return
     */
    @Override
    @Transactional
    public Resp combineSubmit(String toPalletCodeText, List<String> palletList, String account) {
        Resp resp = new Resp();
        Date now = new Date();
        //1.查询目标托盘基础信息
        List<WmsPallet> toPallets = palletMapper.selectByCode(toPalletCodeText);
        if(toPallets.size()<=0){
            resp.setCode("0");
            resp.setMessage("无目标托盘");
            return resp;
        }
        //
        WmsPallet toPallet = toPallets.get(0);
        String toLocationCode = toPallet.getLocationCode();
        String toGoodsCode = toPallet.getGoodsCode();
        String toBatchNo = toPallet.getBatchNo();
        String toPalletCode = toPallet.getPalletCode();
        String areaCode = toPallet.getAreaCode();
        String whCode = toPallet.getWarehouseCode();
        Integer toAmount = toPallet.getAmount();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateFormat = format.format(now);
        String orderNo = "COMBINE"+dateFormat+CommonUtil.generateShortUuid();
        //循环处理待合并托盘
        for (String fromPalletCode :palletList){
            //查询待合并托盘
            List<WmsPallet> fromPallets = palletMapper.selectByCode(fromPalletCode);
            WmsPallet fromPallet =fromPallets.get(0);
            String fromLocationCode = fromPallet.getLocationCode();
            String fromGoodsCode = fromPallet.getGoodsCode();
            String fromBatchNo = fromPallet.getBatchNo();
            Integer fromAmount = fromPallet.getAmount();
//        根据待合并托盘上的商品，库位清空，解绑，
            int cleanNum = palletMapper.palletCleanByCode(fromPalletCode);
//        如果有箱码要将箱码计入目标托盘里面，要修改wms_box_barcode的pallet_code
            WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
            wmsBoxBarcode.setPalletCode(fromPalletCode);
            List<WmsBoxBarcode> wmsBoxBarcodeList = wmsBoxBarcodeMapper.selectByPalletCode(wmsBoxBarcode);
            if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                //如果存在箱码 (更换托盘号)
                int changeNum = wmsBoxBarcodeMapper.changePalletCode(fromPalletCode,toPalletCode);
            }
//        根据待合并托盘上的商品，批次，库位，扣减库存；
            WmsStoragePlane storagePlane  =  new WmsStoragePlane();
            storagePlane.setGoodsCode(fromGoodsCode);
            storagePlane.setBatchNo(fromBatchNo);
            storagePlane.setLocationCode(fromLocationCode);
            storagePlane.setActiveFlag("1");
            List<WmsStoragePlane> planeList = storagePlaneMapper.queryByAny(storagePlane);
            if(planeList.size()<=0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resp.setCode("0");
                resp.setMessage("异常 查询不到平库库存");
                return resp;
            }
            int availableAmount = planeList.get(0).getAvailableAmount();
            if(availableAmount<fromAmount){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resp.setCode("0");
                resp.setMessage("异常 托盘"+fromPalletCode+"库存数量不足");
                return resp;
            }
            WmsStoragePlane updatePlane = new WmsStoragePlane();
            updatePlane.setStoragePlaneId(planeList.get(0).getStoragePlaneId());
            updatePlane.setAvailableAmount(availableAmount-fromAmount);
            updatePlane.setLastModifiedBy(account);
            updatePlane.setGmtModified(now);
            int upAmount = storagePlaneMapper.updateByPrimaryKeySelective(updatePlane);
//        增加目标托盘的amount（将待合并托盘的amount加到目标托盘上）
            // TODO: 2020/2/28
            WmsPallet toPalletUp = new WmsPallet();
            toPalletUp.setPalletCode(toPalletCode);
            toPalletUp.setAmount(toAmount+fromAmount);
            toPalletUp.setLastModifiedBy(account);
            toPalletUp.setGmtModified(now);
            int upToAmount = palletMapper.updateByCode(toPalletUp);

            
//        根据目标托盘上的商品，批次，库位，增加库存

            WmsStoragePlane storageCheck  =  new WmsStoragePlane();
            storageCheck.setGoodsCode(toGoodsCode);
            storageCheck.setBatchNo(toBatchNo);
            storageCheck.setLocationCode(toLocationCode);
            storageCheck.setActiveFlag("1");
            List<WmsStoragePlane> toPlaneList = storagePlaneMapper.queryByAny(storageCheck);
            if(planeList.size()>0){
                //平库库存中可以查询到
                WmsStoragePlane toPlane = toPlaneList.get(0);
                //获取可用数量
                Integer toAvailableAmount = toPlane.getAvailableAmount();
                //增加数量
                toPlane.setAvailableAmount(toAvailableAmount+fromAmount);
                int updateAmount =  storagePlaneMapper.updateByPrimaryKey(toPlane);

            }else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resp.setCode("0");
                resp.setMessage("异常 查询不到平库库存");
                return resp;
            }
            //        4 写入合并日志表wms_combine_log：生成一个合并任务编号，COMBINE+8位UUID
//                每一个待合并托盘计入一条记录
//        如果是待合并托盘，记录combine_task_id，from_pallet_code，to_pallet_code，goods_code，batch_no，from_amount，warehouse_code，area_code，from_location_code，to_location_code
            WmsCombineLog wmsCombineLog = new WmsCombineLog();
            wmsCombineLog.setCombineLogId(CommonUtil.getUUID());
            wmsCombineLog.setCombineTaskId(orderNo);
            wmsCombineLog.setFromPalletCode(fromPalletCode);
            wmsCombineLog.setToPalletCode(toPalletCode);
            wmsCombineLog.setGoodsCode(toGoodsCode);
            wmsCombineLog.setFromAmount(fromAmount);
            wmsCombineLog.setBatchNo(toBatchNo);
            wmsCombineLog.setWarehouseCode(whCode);
            wmsCombineLog.setAreaCode(areaCode);
            wmsCombineLog.setFromLocationCode(fromLocationCode);
            wmsCombineLog.setToLocationCode(toLocationCode);
            wmsCombineLog.setMoveResult("1");
            wmsCombineLog.setCreateBy(account);
            wmsCombineLog.setGmtCreate(now);
            wmsCombineLog.setActiveFlag("1");
            int createNum = wmsCombineLogMapper.insertSelective(wmsCombineLog);
        }
        resp.setCode("1");
        resp.setMessage("合并成功");
        return resp;
    }

    /**
     * 盘点提单
     * @param orderNo
     * @param account
     * @return
     */
    @Override
    public Resp checkLadingBill(String orderNo, String account) {
        Resp resp = new Resp();
        List<WmsOrderCheck> checkList = wmsOrderCheckMapper.selectByOrderNo(orderNo);
        if(checkList == null || checkList.isEmpty()){
            resp.setCode("0");
            resp.setMessage("查无数据！");
            return resp;
        }else{
            WmsOrderCheck tmp = checkList.get(0);
//            库区类型，平面库0，立体库1，2暂存区
            if(tmp.getAreaType() != null && !"0".equals(tmp.getAreaType())){
                resp.setCode("0");
                resp.setMessage("当前盘点单非平库盘点单！");
                return resp;
            }
            if(tmp.getAreaType() != null && "3".equals(tmp.getOrderStatus())){
                resp.setCode("0");
                resp.setMessage("当前盘点单已完成！");
                return resp;
            }
        }


        //汇总查询，需盘点信息
        WmsOrderCheckPallet orderCheckPallet = new WmsOrderCheckPallet();
        orderCheckPallet.setOrderNo(orderNo);
        orderCheckPallet.setActiveFlag("1");
        List<WmsOrderCheckPallet> list = wmsOrderCheckPalletMapper.collectByOrderNo(orderCheckPallet);
        if (list.size()<=0){
            resp.setCode("0");
            resp.setMessage("查无数据");
            return resp;
        }
        //提单后修改状态为盘点中
        WmsOrderCheck orderCheck = new WmsOrderCheck();
        orderCheck.setCheckId(checkList.get(0).getCheckId());
        orderCheck.setOrderStatus("2");
        orderCheck.setLastModifiedBy(account);
        orderCheck.setGmtModified(new Date());
        int upStatus = wmsOrderCheckMapper.updateByPrimaryKeySelective(orderCheck);

        resp.setCode("1");
        resp.setData(list);
        return resp;
    }

    /**
     * 扫描托盘号
     * @param orderNo
     * @param palletCode
     * @param account
     * @return
     */
    @Override
    public Resp checkScanPallet(String orderNo, String palletCode, String account) {
        Resp resp =new Resp();

        WmsOrderCheckPallet orderCheckPallet = new WmsOrderCheckPallet();
        orderCheckPallet.setOrderNo(orderNo);
        orderCheckPallet.setPalletCode(palletCode);
        orderCheckPallet.setActiveFlag("1");
        List<WmsOrderCheckPallet> list = wmsOrderCheckPalletMapper.queryByAny(orderCheckPallet);
        if (list.size()<=0){
            //去托盘表继续校验是否未绑定
            List<WmsPallet> pallets = palletMapper.selectByCode(palletCode);
            if (pallets.size()<=0){
                resp.setCode("0");
                resp.setMessage("查询不到此托盘，请绑定");
                return resp;
            }
            resp.setCode("0");
            resp.setMessage("此托盘不属于盘点范围");
            return resp;
        }
        String ifCheck = list.get(0).getUserDefined1();
        if (ifCheck.equals("1")){
            resp.setCode("0");
            resp.setMessage("该托盘已盘点");
            return resp;
        }
        List<WmsGoods> wmsGoodsList = goodsMapper.selectByGoodsCode(list.get(0).getGoodsCode());
        String hasBoxCode = "";
        if (wmsGoodsList != null && !wmsGoodsList.isEmpty()) {
            hasBoxCode = wmsGoodsList.get(0).getHasBoxCode();
        }else{
//            是否有箱码 1有0 无【商品查不到,就默认没有箱码】
            hasBoxCode = "0";
        }
        list.get(0).setHasBoxCode(hasBoxCode);
        resp.setCode("1");
        resp.setData(list.get(0));
        return resp;
    }

    /**
     * 托盘确认
     *
     * @param account
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Resp confirmPallet(String account, WmsOrderCheckPallet param) {
        Resp resp = new Resp();
        Date now = new Date();
        String orderNo = param.getOrderNo();
        String palletCode = param.getPalletCode();
        WmsOrderCheckPallet rtnOb = new WmsOrderCheckPallet();
        //查询是否已经盘点过
        WmsOrderCheckPallet orderCheckPallet = new WmsOrderCheckPallet();
        orderCheckPallet.setOrderNo(orderNo);
        orderCheckPallet.setPalletCode(palletCode);
        orderCheckPallet.setActiveFlag("1");
        List<WmsOrderCheckPallet> list = wmsOrderCheckPalletMapper.queryByAny(orderCheckPallet);
        if(list != null && !list.isEmpty()){
            String ifCheck = list.get(0).getUserDefined1();
            if (ifCheck.equals("1")){
                resp.setCode("0");
                resp.setMessage("该托盘已盘点");
                return resp;
            }
            rtnOb = list.get(0);
        }

        //查看是否有无箱码,根据托盘号到wms_order_check_pallet_box_barcode
        WmsOrderCheckPalletBoxBarcode palletBoxBarcode = new WmsOrderCheckPalletBoxBarcode();
        palletBoxBarcode.setOrderNo(orderNo);
        palletBoxBarcode.setPalletCode(palletCode);
        palletBoxBarcode.setActiveFlag("1");
        List<WmsOrderCheckPalletBoxBarcode> listBarcode = wmsOrderCheckPalletBoxBarcodeMapper.queryByAny(palletBoxBarcode);
        if (listBarcode.size()>0){
            //有箱码信息需要比对
            List<String> barcodeList = param.getBarcodeList();
            for(String barcode : barcodeList){

                WmsOrderCheckPalletBoxBarcode boxBarcode = new WmsOrderCheckPalletBoxBarcode();
                boxBarcode.setOrderNo(orderNo);
                boxBarcode.setPalletCode(palletCode);
                boxBarcode.setActiveFlag("1");
                boxBarcode.setBoxBarcode(barcode);
                List<WmsOrderCheckPalletBoxBarcode> boxBarcodeList = wmsOrderCheckPalletBoxBarcodeMapper.queryByAny(boxBarcode);
                //存在修改，不存在新增
                if (boxBarcodeList.size()>0){
                    //如果有箱码barcodes，根据托盘号到wms_order_check_pallet_box_barcode填充real_box_barcode，
                    WmsOrderCheckPalletBoxBarcode updateBarcode = new WmsOrderCheckPalletBoxBarcode();
                    updateBarcode.setCheckPalletBoxBarcodeId(boxBarcodeList.get(0).getCheckPalletBoxBarcodeId());
                    updateBarcode.setRealBoxBarcode(barcode);
                    //差异状态0无差异
                    updateBarcode.setDiffFlag("0");
                    updateBarcode.setLastModifiedBy(account);
                    updateBarcode.setGmtModified(now);
                    int changeNum = wmsOrderCheckPalletBoxBarcodeMapper.updateByPrimaryKeySelective(updateBarcode);
                }else {
                    // 这时候要插入一条数据，box_barcode=空，real_box_barcode=实际箱码，diff_flag=1;
                    WmsOrderCheckPalletBoxBarcode createBarcode = new WmsOrderCheckPalletBoxBarcode();
//                    createBarcode.setCheckPalletBoxBarcodeId(CommonUtil.getUUID());
                    createBarcode.setOrderNo(orderNo);
                    createBarcode.setDiffFlag("1");
                    createBarcode.setPalletCode(palletCode);
                    createBarcode.setRealBoxBarcode(barcode);
                    createBarcode.setCreateBy(account);
                    createBarcode.setGmtCreate(now);
                    createBarcode.setActiveFlag("1");
                    int createNum = wmsOrderCheckPalletBoxBarcodeMapper.insertSelective(createBarcode);
                }
            }
        }
        //如果少了某个箱码，原数据的real_box_barcode应该为空,diff_flag插入时候=1，所以这条数据也是差异数据。


        //更改盘点托盘实际数量wms_order_check_pallet
        WmsOrderCheckPallet checkPallet = new WmsOrderCheckPallet();
        checkPallet.setCheckPalletId(list.get(0).getCheckPalletId());
        checkPallet.setRealAmount(param.getRealAmount());
        checkPallet.setUserDefined1("1");
        //校验托盘数量
        if (list.get(0).getAmount()==param.getRealAmount()){
            //校验箱码是否一样
            WmsOrderCheckPalletBoxBarcode checkBoxBarcode = new WmsOrderCheckPalletBoxBarcode();
            checkBoxBarcode.setOrderNo(orderNo);
            checkBoxBarcode.setPalletCode(palletCode);
            checkBoxBarcode.setActiveFlag("1");
            checkBoxBarcode.setDiffFlag("1");
            List<WmsOrderCheckPalletBoxBarcode> checkBarcodeList = wmsOrderCheckPalletBoxBarcodeMapper.queryByAny(checkBoxBarcode);
            if(checkBarcodeList.size()>0){
                //存在差异条码
                checkPallet.setDiffFlag("1");
            }else {
                checkPallet.setDiffFlag("0");
            }
        }else {
            checkPallet.setDiffFlag("1");
        }
        checkPallet.setLastModifiedBy(account);
        checkPallet.setGmtModified(now);
        int upMain = wmsOrderCheckPalletMapper.updateByPrimaryKeySelective(checkPallet);

        resp.setCode("1");
        resp.setMessage("该托盘盘点完成");
        resp.setData(rtnOb);
        return resp;
    }

    /**
     * 盘点确认校验
     * @param account
     * @param orderNo
     * @return
     */
    @Override
    public Resp orderSubmit(String account, String orderNo) {
        Resp resp = new Resp();

        //根据传入的订单号，查找wms_order_check_pallet中是不是所有托盘都盘点了
        //如果有未盘点的给用户提示；
        WmsOrderCheckPallet checkPallet = new WmsOrderCheckPallet();
        checkPallet.setOrderNo(orderNo);
        //未扫描
        checkPallet.setUserDefined1("0");
        checkPallet.setActiveFlag("1");
        List<WmsOrderCheckPallet> list = wmsOrderCheckPalletMapper.queryByAny(checkPallet);
        if (list.size()>0) {
            //存在未盘点信息
            List<String> listA = new ArrayList<String>();
            for(WmsOrderCheckPallet tmp : list){
                listA.add(tmp.getPalletCode());
            }
            resp.setCode("0");
            resp.setMessage("存在托盘（" + String.join(",", listA) + "）未盘点,");
            return resp;
        }
        resp.setCode("1");
        resp.setMessage("全部托盘已盘点");
        return resp;
    }

    /**
     * 最终确认
     * @param account
     * @param orderNo
     * @return
     */
    @Override
    public Resp orderFinalSubmit(String account, String orderNo) {
        Resp resp = new Resp();
        List<WmsOrderCheck> checkList = wmsOrderCheckMapper.selectByOrderNo(orderNo);
        if(checkList == null || checkList.isEmpty()){
            resp.setCode("0");
            resp.setMessage("查无数据！");
            return resp;
        }
        WmsOrderCheck tmp = checkList.get(0);
        if (tmp.getOrderStatus() != null && "3".equals(tmp.getOrderStatus())) {
            resp.setCode("0");
            resp.setMessage("当前盘点单已完成！");
            return resp;
        }

//        根据传入的订单号，将盘点状态改为完成；
        WmsOrderCheck orderCheck = new WmsOrderCheck();
        orderCheck.setCheckId(tmp.getCheckId());
        //已完成
        orderCheck.setOrderStatus("3");
        orderCheck.setLastModifiedBy(account);
        orderCheck.setGmtModified(new Date());
        int updateCheck = wmsOrderCheckMapper.updateByPrimaryKeySelective(orderCheck);
        resp.setCode("1");
        resp.setMessage("当前盘点单最终确认成功！");
        return resp;
    }

    /**
     * 托盘校验提示
     * @param account
     * @param param
     * @return
     */
    @Override
    public Resp checkPallet(String account, WmsOrderCheckPallet param) {
        Resp resp = new Resp();
        Date now = new Date();
        String orderNo = param.getOrderNo();
        String palletCode = param.getPalletCode();
        //查询是否已经盘点过
        WmsOrderCheckPallet orderCheckPallet = new WmsOrderCheckPallet();
        orderCheckPallet.setOrderNo(orderNo);
        orderCheckPallet.setPalletCode(palletCode);
        orderCheckPallet.setActiveFlag("1");
        List<WmsOrderCheckPallet> list = wmsOrderCheckPalletMapper.queryByAny(orderCheckPallet);

        //有箱码信息需要比对
        List<String> barcodeList = param.getBarcodeList();

        //查看是否有无箱码,根据托盘号到wms_order_check_pallet_box_barcode
        WmsOrderCheckPalletBoxBarcode palletBoxBarcode = new WmsOrderCheckPalletBoxBarcode();
        palletBoxBarcode.setOrderNo(orderNo);
        palletBoxBarcode.setPalletCode(palletCode);
        palletBoxBarcode.setActiveFlag("1");
        List<WmsOrderCheckPalletBoxBarcode> listBarcode = wmsOrderCheckPalletBoxBarcodeMapper.queryByAny(palletBoxBarcode);
        List<String> checkBarcodeList = new ArrayList<>();
        if (listBarcode.size()>0){
            //大于0说明存在箱码需要比对
            for(WmsOrderCheckPalletBoxBarcode  checkPalletBoxBarcode :listBarcode){
                checkBarcodeList.add(checkPalletBoxBarcode.getBoxBarcode());
            }
            List<String> listA = new ArrayList<String>(barcodeList);//构建list1的副本

            listA.removeAll(checkBarcodeList);// 去除相同元素
            List<String> listB = new ArrayList<String>(checkBarcodeList);//构建list2的副本

            listB.removeAll(barcodeList);// 去除相同元素

            StringBuffer sb = new StringBuffer("");
            if (listB.size()>0){
                sb.append("托盘箱码（" + String.join(",", listB) + "）未扫码,");
            }
            if (listA.size()>0){
                sb.append("托盘箱码（" + String.join(",", listA) + "）系统中不存在,");
            }
            if(!sb.toString().equals("")){
                resp.setCode("0");
                resp.setMessage(sb.toString());
                return resp;
            }
        }

        //盘点数量比对
        if (list.get(0).getAmount()!=param.getRealAmount()){
            resp.setCode("0");
            resp.setMessage("该托盘数量与实际数量不一致,");
            return resp;
        }

        resp.setCode("1");
        resp.setMessage("该托盘无差异");
        return resp;
    }

    /**
     * 校验库位是否存在
     * @param locationCode
     * @return
     */
    @Override
    public Resp checkPlaneLocation(String locationCode) {
        Resp resp = new Resp();

        List<WmsPlaneLocation> list = wmsPlaneLocationMapper.selectByCode(locationCode);

        if (list.size()<=0){
            resp.setCode("0");
            resp.setMessage("不存在此库位");
            return resp;
        }

        resp.setCode("1");
        resp.setMessage("存在该库位");
        return resp;
    }
}
