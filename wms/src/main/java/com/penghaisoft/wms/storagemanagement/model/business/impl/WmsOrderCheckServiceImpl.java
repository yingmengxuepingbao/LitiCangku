//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsGoodsMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsWarehouseAreaMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsOutTask;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouseArea;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCheckService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCheckMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCheckPalletBoxBarcodeMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCheckPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheck;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheckPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheckPalletBoxBarcode;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("wmsOrderCheckService")
public class WmsOrderCheckServiceImpl extends BaseService implements IWmsOrderCheckService {
    @Resource
    private WmsOrderCheckMapper wmsOrderCheckMapper;
    @Resource
    private WmsOrderCheckPalletMapper wmsOrderCheckPalletMapper;
    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsOrderCheckPalletBoxBarcodeMapper wmsOrderCheckPalletBoxBarcodeMapper;
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;
    @Resource
    private WmsWarehouseAreaMapper wmsWarehouseAreaMapper;
    @Autowired
    private IWmsCommonService wmsCommonService;
    @Resource
    private WmsGoodsMapper wmsGoodsMapper;

    public WmsOrderCheckServiceImpl() {
    }

    @Transactional
    public Resp create(WmsOrderCheck wmsOrderCheck) {
        String orderNo = "";
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        if (wmsOrderCheck.getAreaCode() != null && !"".equals(wmsOrderCheck.getAreaCode())) {
            WmsWarehouseArea wmsWarehouseArea = new WmsWarehouseArea();
            wmsWarehouseArea.setAreaCode(wmsOrderCheck.getAreaCode());
            wmsWarehouseArea.setActiveFlag("1");
            List<WmsWarehouseArea> areaList = this.wmsWarehouseAreaMapper.queryByAny(wmsWarehouseArea);
            if (areaList != null && !areaList.isEmpty()) {
                String areaType = ((WmsWarehouseArea)areaList.get(0)).getAreaType();
                if ("1".equals(areaType)) {
                    orderNo = this.wmsCommonService.getOrderNoByType("PDS");
                } else {
                    orderNo = this.wmsCommonService.getOrderNoByType("PDP");
                }
            }
        }

        if (wmsOrderCheck.getTime() != null && !"".equals(wmsOrderCheck.getTime())) {
            c.add(2, -Integer.parseInt(wmsOrderCheck.getTime()));
        }

        List<WmsOrderCheck> wmsOrderCheckList = new ArrayList();
        Iterator var8;
        String tmp;
        WmsOrderCheck checkInsertOb;
        String goodsCode;
        List goodsCodeList;
        if (wmsOrderCheck.getOrderType() != null && "1".equals(wmsOrderCheck.getOrderType())) {
            goodsCode = wmsOrderCheck.getLocationCode() == null ? "" : wmsOrderCheck.getLocationCode();
            new ArrayList();
            goodsCodeList = Arrays.asList(goodsCode.split(","));
            var8 = goodsCodeList.iterator();

            while(var8.hasNext()) {
                tmp = (String)var8.next();
                checkInsertOb = new WmsOrderCheck();
                checkInsertOb.setCheckId(CommonUtils.getUUID());
                checkInsertOb.setOrderNo(orderNo);
                checkInsertOb.setOrderType(wmsOrderCheck.getOrderType());
                checkInsertOb.setOrderStatus("1");
                checkInsertOb.setAreaCode(wmsOrderCheck.getAreaCode());
                checkInsertOb.setDiffFlag("1");
                checkInsertOb.setLocationCode(tmp);
                checkInsertOb.setActiveFlag("1");
                checkInsertOb.setCreateBy(wmsOrderCheck.getCreateBy());
                checkInsertOb.setGmtCreate(now);
                checkInsertOb.setUserDefined2(wmsOrderCheck.getUserDefined2());
                wmsOrderCheckList.add(checkInsertOb);
                WmsOrderCheckPallet checkPallet = new WmsOrderCheckPallet();
                checkPallet.setCheckId(checkInsertOb.getCheckId());
                checkPallet.setOrderNo(orderNo);
                checkPallet.setRemark(wmsOrderCheck.getRemark());
                checkPallet.setCreateBy(wmsOrderCheck.getCreateBy());
                checkPallet.setGmtCreate(now);
                if (wmsOrderCheck.getTime() != null && !"".equals(wmsOrderCheck.getTime())) {
                    checkPallet.setGmtModified(c.getTime());
                }

                checkPallet.setLocationCode(tmp);
                checkPallet.setAreaCode(wmsOrderCheck.getAreaCode());
                checkPallet.setDiffFlag("1");
                checkPallet.setUserDefined1("0");
                //添加-1：线上 2：线下
                checkPallet.setUserDefined2(wmsOrderCheck.getUserDefined2());
                checkPallet.setActiveFlag("1");
                this.wmsOrderCheckPalletMapper.batchInsertByCheck(checkPallet);
                WmsOrderCheckPalletBoxBarcode palletBoxBarcode = new WmsOrderCheckPalletBoxBarcode();
                palletBoxBarcode.setOrderNo(orderNo);
                palletBoxBarcode.setRemark(wmsOrderCheck.getRemark());
                palletBoxBarcode.setCreateBy(wmsOrderCheck.getCreateBy());
                palletBoxBarcode.setGmtCreate(now);
                if (wmsOrderCheck.getTime() != null && !"".equals(wmsOrderCheck.getTime())) {
                    palletBoxBarcode.setGmtModified(c.getTime());
                }

                palletBoxBarcode.setLocationCode(tmp);
                palletBoxBarcode.setAreaCode(wmsOrderCheck.getAreaCode());
                palletBoxBarcode.setDiffFlag("1");
                palletBoxBarcode.setActiveFlag("1");
                this.wmsOrderCheckPalletBoxBarcodeMapper.batchInsertByCheck(palletBoxBarcode);
            }

            this.wmsOrderCheckMapper.batchInsert(wmsOrderCheckList);
        } else {
            goodsCode = wmsOrderCheck.getGoodsCode() == null ? "" : wmsOrderCheck.getGoodsCode();
            new ArrayList();
            goodsCodeList = Arrays.asList(goodsCode.split(","));
            var8 = goodsCodeList.iterator();

            while(var8.hasNext()) {
                tmp = (String)var8.next();
                checkInsertOb = new WmsOrderCheck();
                checkInsertOb.setCheckId(CommonUtils.getUUID());
                checkInsertOb.setOrderNo(orderNo);
                checkInsertOb.setOrderType(wmsOrderCheck.getOrderType());
                checkInsertOb.setOrderStatus("1");
                checkInsertOb.setAreaCode(wmsOrderCheck.getAreaCode());
                checkInsertOb.setDiffFlag("1");
                checkInsertOb.setGoodsCode(tmp);
                WmsGoods goods = this.wmsGoodsMapper.queryByCode(tmp);
                String goodsName = "";
                if (goods != null) {
                    goodsName = goods.getGoodsName();
                }

                checkInsertOb.setGoodsName(goodsName);
                checkInsertOb.setActiveFlag("1");
                checkInsertOb.setCreateBy(wmsOrderCheck.getCreateBy());
                checkInsertOb.setGmtCreate(now);
                checkInsertOb.setUserDefined2(wmsOrderCheck.getUserDefined2());
                wmsOrderCheckList.add(checkInsertOb);
                WmsOrderCheckPallet checkPallet = new WmsOrderCheckPallet();
                checkPallet.setCheckId(checkInsertOb.getCheckId());
                checkPallet.setOrderNo(orderNo);
                checkPallet.setRemark(wmsOrderCheck.getRemark());
                checkPallet.setCreateBy(wmsOrderCheck.getCreateBy());
                checkPallet.setGmtCreate(now);
                if (wmsOrderCheck.getTime() != null && !"".equals(wmsOrderCheck.getTime())) {
                    checkPallet.setGmtModified(c.getTime());
                }

                checkPallet.setGoodsCode(tmp);
                checkPallet.setAreaCode(wmsOrderCheck.getAreaCode());
                checkPallet.setDiffFlag("1");
                checkPallet.setUserDefined1("0");
                //添加-1：线上 2：线下
                checkPallet.setUserDefined2(wmsOrderCheck.getUserDefined2());
                checkPallet.setActiveFlag("1");
                this.wmsOrderCheckPalletMapper.batchInsertByCheck(checkPallet);
                WmsOrderCheckPalletBoxBarcode palletBoxBarcode = new WmsOrderCheckPalletBoxBarcode();
                palletBoxBarcode.setOrderNo(orderNo);
                palletBoxBarcode.setRemark(wmsOrderCheck.getRemark());
                palletBoxBarcode.setCreateBy(wmsOrderCheck.getCreateBy());
                palletBoxBarcode.setGmtCreate(now);
                palletBoxBarcode.setUserDefined2(wmsOrderCheck.getUserDefined2());
                if (wmsOrderCheck.getTime() != null && !"".equals(wmsOrderCheck.getTime())) {
                    palletBoxBarcode.setGmtModified(c.getTime());
                }

                palletBoxBarcode.setGoodsCode(tmp);
                palletBoxBarcode.setAreaCode(wmsOrderCheck.getAreaCode());
                palletBoxBarcode.setDiffFlag("1");
                palletBoxBarcode.setActiveFlag("1");
                this.wmsOrderCheckPalletBoxBarcodeMapper.batchInsertByCheck(palletBoxBarcode);
            }

            this.wmsOrderCheckMapper.batchInsert(wmsOrderCheckList);
        }

        return this.success();
    }
    /**
     *功能描述: 盘点 -启动
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Transactional
    public Resp updateBusiness(List<WmsOutTask> wmsOutTaskList, String unDoPalletCodes, String orderNo, String loginName) {
        Resp resp = new Resp();
        resp.setCode(RESULT.SUCCESS.code);
        List<WmsPallet> wmsPalletList = new ArrayList();
        List<WmsTaskExecutionLog> wmsTaskExecutionLogList = new ArrayList();
        Date now = new Date();
        Iterator iter = wmsOutTaskList.iterator();

        while(iter.hasNext()) {
            WmsOutTask tmp = (WmsOutTask)iter.next();
            WmsPallet wmsPallet = new WmsPallet();
            wmsPallet.setPalletCode(tmp.getPalletCode());
            wmsPallet.setLockBy(String.valueOf(tmp.getTaskId()));
            wmsPallet.setLastModifiedBy(loginName);
            wmsPallet.setGmtModified(now);
            wmsPallet.setUserDefined1(orderNo);
            wmsPalletList.add(wmsPallet);
            WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
            wmsTaskExecutionLog.setTaskId(tmp.getTaskId());
            wmsTaskExecutionLog.setOrderNo(orderNo);
            wmsTaskExecutionLog.setTaskType(String.valueOf(TaskType.CHECK_OUT.getTaskType()));
            wmsTaskExecutionLog.setTaskStatus("2");
            wmsTaskExecutionLog.setOutAddress("2001");
            wmsTaskExecutionLog.setWarehouseCode("NH_WAREHOUSE");
            wmsTaskExecutionLog.setAreaCode("L-NH01");
            wmsTaskExecutionLog.setPalletCode(tmp.getPalletCode());
            wmsTaskExecutionLog.setGoodsCode(tmp.getGoodsCode());
            wmsTaskExecutionLog.setBatchNo(tmp.getBatchNo());
            wmsTaskExecutionLog.setLocationCode(tmp.getFromLocation());
            wmsTaskExecutionLog.setCreateBy(loginName);
            wmsTaskExecutionLog.setGmtCreate(now);
            wmsTaskExecutionLog.setActiveFlag("1");
            wmsTaskExecutionLogList.add(wmsTaskExecutionLog);
        }

        this.wmsPalletMapper.updateByBatch(wmsPalletList);
        this.wmsTaskExecutionLogMapper.batchInsert(wmsTaskExecutionLogList);
        WmsOrderCheck up = new WmsOrderCheck();
        up.setOrderNo(orderNo);
        if (!"".equals(unDoPalletCodes)) {
            up.setUserDefined1(unDoPalletCodes);
        }

        up.setOrderStatus("2");
        this.wmsOrderCheckMapper.updateByOrderNo(up);
        return resp;
    }

    public Resp deleteByCheckId(WmsOrderCheck wmsOrderCheck) {
        WmsOrderCheck delOb = new WmsOrderCheck();
        delOb.setCheckId(wmsOrderCheck.getCheckId());
        this.wmsOrderCheckMapper.delete(delOb);
        WmsOrderCheckPallet t = new WmsOrderCheckPallet();
        t.setCheckId(wmsOrderCheck.getCheckId());
        this.wmsOrderCheckPalletMapper.deleteByCheckId(t);
        WmsOrderCheckPalletBoxBarcode s = new WmsOrderCheckPalletBoxBarcode();
        s.setCheckId(wmsOrderCheck.getCheckId());
        this.wmsOrderCheckPalletBoxBarcodeMapper.deleteByCheckId(s);
        return this.success();
    }

    public Resp deleteByOrderNo(WmsOrderCheck wmsOrderCheck) {
        WmsOrderCheck delOb = new WmsOrderCheck();
        delOb.setOrderNo(wmsOrderCheck.getOrderNo());
        this.wmsOrderCheckMapper.deleteByOrderNo(delOb);
        WmsOrderCheckPallet t = new WmsOrderCheckPallet();
        t.setOrderNo(wmsOrderCheck.getOrderNo());
        this.wmsOrderCheckPalletMapper.deleteByOrderNo(t);
        WmsOrderCheckPalletBoxBarcode s = new WmsOrderCheckPalletBoxBarcode();
        s.setOrderNo(wmsOrderCheck.getOrderNo());
        this.wmsOrderCheckPalletBoxBarcodeMapper.deleteByOrderNo(s);
        return this.success();
    }

    public Pager<WmsOrderCheck> findListByCondition(int page, int rows, WmsOrderCheck condition) {
        Pager<WmsOrderCheck> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        //long size = this.wmsOrderCheckMapper.queryCount1(condition);
        //-解决SQL server中，字段无效，因为该列没有包含在聚合函数或 GROUP BY 子句中，的问题
        long size = this.wmsOrderCheckMapper.queryCount_sqlserver(condition);
        List<WmsOrderCheck> records = new ArrayList();
        if (size > 0L) {
           // records = this.wmsOrderCheckMapper.queryList1(pager, condition);
            records = this.wmsOrderCheckMapper.queryListToSqlServer(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderCheck findById(String id) {
        return (WmsOrderCheck)this.wmsOrderCheckMapper.queryById(id);
    }

    public Resp update(WmsOrderCheck wmsOrderCheck) {
        this.wmsOrderCheckMapper.updateBySelect(wmsOrderCheck);
        return this.success();
    }

    public List<WmsOrderCheck> queryByAny(WmsOrderCheck condition) {
        return this.wmsOrderCheckMapper.queryByAny(condition);
    }
}
