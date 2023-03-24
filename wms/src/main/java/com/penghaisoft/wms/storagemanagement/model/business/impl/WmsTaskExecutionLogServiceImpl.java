//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsGoodsMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.expose.WcsTransOb;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicDeailMapper;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsTaskExecutionLogService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsBoxBarcodeMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCheckMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCrossProductMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOutTemporaryBoxBarcodeMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOutTemporaryMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("wmsTaskExecutionLogService")
public class WmsTaskExecutionLogServiceImpl extends BaseService implements IWmsTaskExecutionLogService {
    private static final Logger log = LoggerFactory.getLogger(WmsTaskExecutionLogServiceImpl.class);
    @Resource
    private WmsTaskExecutionLogMapper wmsTaskExecutionLogMapper;
    @Resource
    private WmsLocationStereoscopicMapper wmsLocationStereoscopicMapper;
    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsOrderOutStereoscopicMapper wmsOrderOutStereoscopicMapper;
    @Resource
    private WmsOrderOutStereoscopicDeailMapper wmsOrderOutStereoscopicDeailMapper;
    @Resource
    private WmsOutTemporaryMapper wmsOutTemporaryMapper;
    @Resource
    private WmsBoxBarcodeMapper wmsBoxBarcodeMapper;
    @Resource
    private WmsOutTemporaryBoxBarcodeMapper wmsOutTemporaryBoxBarcodeMapper;
    @Resource
    private WmsGoodsMapper wmsGoodsMapper;
    @Resource
    private WmsOrderCheckMapper wmsOrderCheckMapper;
    @Resource
    private WmsOrderCrossProductMapper wmsOrderCrossProductMapper;

    public WmsTaskExecutionLogServiceImpl() {
    }

    public Resp create(WmsTaskExecutionLog wmsTaskExecutionLog) {
        this.wmsTaskExecutionLogMapper.create(wmsTaskExecutionLog);
        return this.success();
    }

    public Resp delete(WmsTaskExecutionLog wmsTaskExecutionLog) {
        this.wmsTaskExecutionLogMapper.delete(wmsTaskExecutionLog);
        return this.success();
    }
    public Resp deleteWuli(WmsTaskExecutionLog wmsTaskExecutionLog) {
        this.wmsTaskExecutionLogMapper.deleteWuli(wmsTaskExecutionLog);
        return this.success();
    }

    public Pager<WmsTaskExecutionLog> findListByCondition(int page, int rows, WmsTaskExecutionLog condition) {
        Pager<WmsTaskExecutionLog> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsTaskExecutionLogMapper.queryCount(condition);
        List<WmsTaskExecutionLog> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsTaskExecutionLogMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsTaskExecutionLog findById(String id) {
        return (WmsTaskExecutionLog)this.wmsTaskExecutionLogMapper.queryById(id);
    }
    /**
     *功能描述: 根据任务号查询数据
     * @date 2022/9/17
     * @params
     * @return com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog
     */
    public WmsTaskExecutionLog queryByTask(WmsTaskExecutionLog condition) {
        return this.wmsTaskExecutionLogMapper.queryByTask(condition);
    }

    public Resp update(WmsTaskExecutionLog wmsTaskExecutionLog) {
        this.wmsTaskExecutionLogMapper.updateBySelect(wmsTaskExecutionLog);
        return this.success();
    }

    public Resp updateByTaskId(WmsTaskExecutionLog wmsTaskExecutionLog) {
        this.wmsTaskExecutionLogMapper.updateByTaskId(wmsTaskExecutionLog);
        return this.success();
    }

    public List<WmsTaskExecutionLog> queryByAny(WmsTaskExecutionLog condition) {
        return this.wmsTaskExecutionLogMapper.queryByAny(condition);
    }

    @Transactional
    public Resp inStereoscopicTaskCreate(WmsTaskExecutionLog condition) {
        new Resp();
        //任务执行日志表
        WmsTaskExecutionLog wmsTaskExecutionLog = wmsTaskExecutionLogMapper.queryByTask(condition);
        if(wmsTaskExecutionLog == null) {
            this.wmsTaskExecutionLogMapper.create(condition);
        }else {
            this.wmsTaskExecutionLogMapper.updateByTaskId(condition);
        }
        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        //库位编码（第一、二位是横行，第三、四位是竖列，第五、六位是层） 例：010201 （第一行第二列一层）
        updateOb.setLocationCode(condition.getLocationCode());
        //托盘编码
        updateOb.setPalletCode(condition.getPalletCode());
        //最后修改人
        updateOb.setLastModifiedBy("wsc");
        //最后修改时间
        updateOb.setGmtModified(condition.getGmtCreate());
        //根据库位编码和激活标记，修改立库库位信息表
        this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
        WmsPallet wmsPalletOb = new WmsPallet();
        wmsPalletOb.setPalletCode(condition.getPalletCode());
        wmsPalletOb.setLockBy(String.valueOf(condition.getTaskId()));
        wmsPalletOb.setLocationCode(condition.getLocationCode());
        wmsPalletOb.setAreaCode(condition.getAreaCode());
        wmsPalletOb.setLastModifiedBy("wsc");
        wmsPalletOb.setGmtModified(condition.getGmtCreate());
        //托盘信息表
        this.wmsPalletMapper.updateByPalletCode(wmsPalletOb);
        return this.success();
    }

    @Transactional
    public Resp inStereoscopicTaskCreateVP(WmsTaskExecutionLog condition) {
        new Resp();
        this.wmsTaskExecutionLogMapper.create(condition);
        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        updateOb.setLocationCode(condition.getLocationCode());
        updateOb.setPalletCode(condition.getPalletCode());
        updateOb.setLastModifiedBy("wsc");
        updateOb.setGmtModified(condition.getGmtCreate());
        this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
        WmsPallet wmsPalletOb = new WmsPallet();
        wmsPalletOb.setPalletId(CommonUtils.getUUID());
        wmsPalletOb.setPalletCode(condition.getPalletCode());
        wmsPalletOb.setGoodsCode(condition.getGoodsCode());
        wmsPalletOb.setAmount(condition.getAmount());
        wmsPalletOb.setLockBy(String.valueOf(condition.getTaskId()));
        wmsPalletOb.setLocationCode(condition.getLocationCode());
        wmsPalletOb.setAreaCode(condition.getAreaCode());
        wmsPalletOb.setCreateBy(condition.getCreateBy());
        wmsPalletOb.setGmtCreate(condition.getGmtCreate());
        wmsPalletOb.setActiveFlag("1");
        this.wmsPalletMapper.create(wmsPalletOb);
        return this.success();
    }
    /**
     *功能描述: 生产入库 - 接收入库任务状态,更改状态
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Transactional
    public Resp reportInStereoscopicTask(WmsTaskExecutionLog condition) {
        condition.setLastModifiedBy("surui_wcs");
        //更改状态的操作时间
        condition.setGmtModified(new Date());
        WmsPallet wmsPalletOb = new WmsPallet();
        wmsPalletOb.setPalletCode(condition.getPalletCode());
        wmsPalletOb.setActiveFlag("1");
        List<WmsPallet> wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPalletOb);
        if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
            WmsPallet pallet = (WmsPallet)wmsPalletList.get(0);
            if (pallet.getLockBy() == null || "".equals(pallet.getLockBy())) {
                WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                List<WmsLocationStereoscopic> list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list != null && !list.isEmpty() && condition.getLocationCode() != null && condition.getLocationCode().equals(((WmsLocationStereoscopic)list.get(0)).getLocationCode())) {
                    String rtnMSg = "第一次上传处理过，第二次不用处理，直接返回成功！";
                    condition.setUserDefined1(rtnMSg);
                    return this.success(rtnMSg);
                }
            }
            //condition.setUserDefined1(Integer.toString(pallet.getAmount()));
        }

        WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
        updateOb.setLocationCode(condition.getLocationCode());
        updateOb.setPalletCode(condition.getPalletCode());
        if (condition.getTaskStatus() != null && "3".equals(condition.getTaskStatus())) {
            updateOb.setUseStatus("5");
            condition.setErrorMsg("入库完成，未过账");
        } else if (condition.getTaskStatus() != null && "4".equals(condition.getTaskStatus())) {
            updateOb.setUseStatus("4");
        }

        updateOb.setLastModifiedBy("wsc");
        updateOb.setGmtModified(new Date());
        this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
        if (condition.getTaskStatus() != null && "3".equals(condition.getTaskStatus())) {
            wmsPalletOb.setLockByNull("null");
        } else if (condition.getTaskStatus() != null && "4".equals(condition.getTaskStatus())) {
            wmsPalletOb.setLockBy("error");
        }

        wmsPalletOb.setLastModifiedBy("wsc");
        wmsPalletOb.setGmtModified(condition.getGmtCreate());
        this.wmsPalletMapper.updateByPalletCode(wmsPalletOb);


        this.wmsTaskExecutionLogMapper.updateByTaskId(condition);
        return this.success();
    }

    @Transactional
    public Resp reportOutYkTask(WmsTaskExecutionLog condition) {
        WmsTaskExecutionLog serachOb = new WmsTaskExecutionLog();
        serachOb.setTaskId(condition.getTaskId());
        serachOb.setPalletCode(condition.getPalletCode());
        serachOb.setTaskStatus("1");
        serachOb.setActiveFlag("1");
        List<WmsTaskExecutionLog> wmsTaskExecutionLogList = this.wmsTaskExecutionLogMapper.queryByAny(serachOb);
        if (wmsTaskExecutionLogList != null && !wmsTaskExecutionLogList.isEmpty()) {
            WmsTaskExecutionLog tmp = (WmsTaskExecutionLog)wmsTaskExecutionLogList.get(0);
            WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
            WmsPallet wmsPallet = new WmsPallet();
            wmsPallet.setPalletCode(condition.getPalletCode());
            wmsPallet.setActiveFlag("1");
            List<WmsPallet> wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            WmsPallet wmsPalletOb;
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                wmsPalletOb = (WmsPallet)wmsPalletList.get(0);
                if (wmsPalletOb.getChannelLocation() != null && !"".equals(wmsPalletOb.getChannelLocation())) {
                    updateOb.setLocationCode(wmsPalletOb.getChannelLocation());
                    updateOb.setUseStatus("0");
                    updateOb.setLastModifiedBy("wsc");
                    updateOb.setGmtModified(condition.getGmtCreate());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                }
            }

            if (tmp.getOutAddress() != null && !"".equals(tmp.getOutAddress())) {
                updateOb.setLocationCode(tmp.getOutAddress());
                updateOb.setPalletCode("null");
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
            }

            if (tmp.getLocationCode() != null && !"".equals(tmp.getLocationCode())) {
                updateOb.setLocationCode(tmp.getLocationCode());
                updateOb.setUseStatus("3");
                updateOb.setPalletCode(condition.getPalletCode());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
            }

            wmsPalletOb = new WmsPallet();
            wmsPalletOb.setPalletCode(condition.getPalletCode());
            wmsPalletOb.setLockByNull("null");
            wmsPalletOb.setChannelLocationNull("null");
            wmsPalletOb.setLocationCode(tmp.getLocationCode());
            wmsPalletOb.setLastModifiedBy("wsc");
            wmsPalletOb.setGmtModified(condition.getGmtCreate());
            this.wmsPalletMapper.updateByPalletCode(wmsPalletOb);
        }

        this.wmsTaskExecutionLogMapper.create(condition);
        return this.success();
    }

    @Transactional
    public Resp reportOutStraightTask(WmsTaskExecutionLog condition) {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setPalletCode(condition.getPalletCode());
        wmsPallet.setActiveFlag("1");
        List wmsPalletList;
        WmsPallet pallet;
        WmsLocationStereoscopic searchOb;
        List list;
        WmsLocationStereoscopic updateOb1;
        if (condition.getTaskStatus() != null && "3".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                condition.setUserDefined1(Integer.toString(pallet.getAmount()));
                if (pallet.getLockBy() == null || "".equals(pallet.getLockBy())) {
                    return this.fail("该托盘码未被锁定！");
                }

                searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list != null && !list.isEmpty()) {
                    if (list.size() != 1) {
                        return this.fail("当前托盘对应库位表记录大于1条！");
                    }

                    updateOb1 = new WmsLocationStereoscopic();
                    updateOb1.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                    updateOb1.setPalletCodeNull("null");
                    updateOb1.setUseStatus("0");
                    updateOb1.setLastModifiedBy("wcs");
                    updateOb1.setGmtModified(new Date());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb1);
                    condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                } else if (null == pallet.getLockBy() || "".equals(pallet.getLockBy())) {
                    String rtnMSg = "第一次上传处理过，第二次不用处理，直接返回成功！";
                    condition.setUserDefined1(rtnMSg);
                    this.wmsTaskExecutionLogMapper.create(condition);
                    return this.success(rtnMSg);
                }

                if (pallet.getPalletCode().equals(pallet.getGoodsCode())) {
                    this.wmsPalletMapper.delete(pallet);
                } else {
                    WmsPallet updateOb = new WmsPallet();
                    updateOb.setPalletId(pallet.getPalletId());
                    updateOb.setLockByNull("null");
                    updateOb.setLocationCodeNull("null");
                    updateOb.setChannelLocationNull("null");
                    updateOb.setGoodsCodeNull("null");
                    updateOb.setBatchNoNull("null");
                    updateOb.setAmountNull("null");
                    updateOb.setUserDefined3Null("null");
                    updateOb.setLastModifiedBy("wcs");
                    updateOb.setGmtModified(new Date());
                    this.wmsPalletMapper.updateBySelect(updateOb);
                }

                if (pallet.getChannelLocation() != null && !"".equals(pallet.getChannelLocation())) {
                    updateOb1 = new WmsLocationStereoscopic();
                    updateOb1.setLocationCode(pallet.getChannelLocation());
                    updateOb1.setUseStatus("0");
                    updateOb1.setLastModifiedBy("wsc");
                    updateOb1.setGmtModified(new Date());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb1);
                }

                WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail = new WmsOrderOutStereoscopicDeail();
                wmsOrderOutStereoscopicDeail.setOrderNo(condition.getOrderNo());
                wmsOrderOutStereoscopicDeail.setGoodsCode(pallet.getGoodsCode());
                wmsOrderOutStereoscopicDeail.setBatchNo(pallet.getBatchNo());
                wmsOrderOutStereoscopicDeail.setRealAmountAdd(pallet.getAmount());
                wmsOrderOutStereoscopicDeail.setLastModifiedBy("wcs");
                wmsOrderOutStereoscopicDeail.setGmtModified(new Date());
                this.wmsOrderOutStereoscopicDeailMapper.updateByOrderNoGoodCodeBatchNo(wmsOrderOutStereoscopicDeail);
                WmsOrderOutStereoscopicDeail completeOb = new WmsOrderOutStereoscopicDeail();
                completeOb.setOrderNo(condition.getOrderNo());
                completeOb.setPlanAmountMoreRealAmount("PlanAmountMoreRealAmount");
                completeOb.setActiveFlag("1");
                List<WmsOrderOutStereoscopicDeail> completeList = this.wmsOrderOutStereoscopicDeailMapper.queryByAny(completeOb);
                if (completeList == null || completeList.isEmpty()) {
                    WmsOrderOutStereoscopic record = new WmsOrderOutStereoscopic();
                    record.setOrderNo(condition.getOrderNo());
                    record.setOrderStatus("3");
                    record.setLastModifiedBy("wcs");
                    record.setGmtModified(new Date());
                    this.wmsOrderOutStereoscopicMapper.updateByOrderNo(record);
                }

                WmsGoods wmsGoods = this.wmsGoodsMapper.queryByCode(pallet.getGoodsCode());
                WmsOutTemporary wmsOutTemporary = new WmsOutTemporary();
                wmsOutTemporary.setGoodsCode(pallet.getGoodsCode());
                wmsOutTemporary.setActiveFlag("1");
                List<WmsOutTemporary> wmsOutTemporaryList = this.wmsOutTemporaryMapper.queryByAny(wmsOutTemporary);
                if (wmsOutTemporaryList != null && !wmsOutTemporaryList.isEmpty()) {
                    WmsOutTemporary updateOb = new WmsOutTemporary();
                    updateOb.setGoodsCode(pallet.getGoodsCode());
                    updateOb.setAmountAdd(pallet.getAmount());
                    updateOb.setLastModifiedBy("wcs");
                    updateOb.setGmtModified(new Date());
                    this.wmsOutTemporaryMapper.updateByGoodsCode(updateOb);
                } else {
                    if (wmsGoods != null) {
                        wmsOutTemporary.setGoodsName(wmsGoods.getGoodsName());
                    }

                    wmsOutTemporary.setId(CommonUtils.getUUID());
                    wmsOutTemporary.setAmount(pallet.getAmount());
                    wmsOutTemporary.setCreateBy("wcs");
                    wmsOutTemporary.setGmtCreate(condition.getGmtCreate());
                    this.wmsOutTemporaryMapper.create(wmsOutTemporary);
                }

                WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
                wmsBoxBarcode.setPalletCode(pallet.getPalletCode());
                wmsBoxBarcode.setActiveFlag("1");
                List<WmsBoxBarcode> wmsBoxBarcodeList = this.wmsBoxBarcodeMapper.queryByAny(wmsBoxBarcode);
                if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                    this.wmsBoxBarcodeMapper.delete(wmsBoxBarcode);
                    List<WmsOutTemporaryBoxBarcode> wmsOutTemporaryBoxBarcodeList = new ArrayList();
                    Iterator var16 = wmsBoxBarcodeList.iterator();

                    while(var16.hasNext()) {
                        WmsBoxBarcode tmp = (WmsBoxBarcode)var16.next();
                        WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode = new WmsOutTemporaryBoxBarcode();
                        wmsOutTemporaryBoxBarcode.setId(CommonUtils.getUUID());
                        wmsOutTemporaryBoxBarcode.setOrderNo(condition.getOrderNo());
                        wmsOutTemporaryBoxBarcode.setGoodsCode(pallet.getGoodsCode());
                        if (wmsGoods != null) {
                            wmsOutTemporaryBoxBarcode.setGoodsName(wmsGoods.getGoodsName());
                        }

                        wmsOutTemporaryBoxBarcode.setBatchNo(pallet.getBatchNo());
                        wmsOutTemporaryBoxBarcode.setBoxBarcode(tmp.getBoxBarcode());
                        wmsOutTemporaryBoxBarcode.setCreateBy("wcs");
                        wmsOutTemporaryBoxBarcode.setGmtCreate(condition.getGmtCreate());
                        wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                    }

                    this.wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
                }
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        } else if (condition.getTaskStatus() != null && "4".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                if (pallet.getChannelLocation() != null && !"".equals(pallet.getChannelLocation())) {
                    searchOb = new WmsLocationStereoscopic();
                    searchOb.setLocationCode(pallet.getChannelLocation());
                    searchOb.setUseStatus("4");
                    searchOb.setLastModifiedBy("wsc");
                    searchOb.setGmtModified(new Date());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(searchOb);
                }

                searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list == null || list.isEmpty()) {
                    return this.fail("当前托盘对应库位表无记录！");
                }

                if (list.size() != 1) {
                    return this.fail("当前托盘对应库位表记录大于1条！");
                }

                updateOb1 = new WmsLocationStereoscopic();
                updateOb1.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                updateOb1.setUseStatus("4");
                updateOb1.setLastModifiedBy("wcs");
                updateOb1.setGmtModified(new Date());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb1);
                condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                WmsOrderOutStereoscopic wmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
                wmsOrderOutStereoscopic.setOrderNo(condition.getOrderNo());
                wmsOrderOutStereoscopic.setOrderStatus("4");
                wmsOrderOutStereoscopic.setLastModifiedBy("wcs");
                wmsOrderOutStereoscopic.setGmtModified(new Date());
                this.wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        }

        return this.success();
    }
    /**
     *功能描述: wcs任务上报，更新任务表。
     * @author zhangxin
     * @date 2022/8/11
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Transactional
    public Resp reportUpOutStraightTask(WmsTaskExecutionLog condition) {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setPalletCode(condition.getPalletCode());
        wmsPallet.setActiveFlag("1");
        List wmsPalletList;
        WmsPallet pallet;
        WmsLocationStereoscopic searchOb;
        List list;
        WmsLocationStereoscopic updateOb1;
        if (condition.getTaskStatus() != null && "3".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                condition.setUserDefined1(Integer.toString(pallet.getAmount()));
                if (pallet.getLockBy() == null || "".equals(pallet.getLockBy())) {
                    return this.fail("该托盘码未被锁定！");
                }

                searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list != null && !list.isEmpty()) {
                    if (list.size() != 1) {
                        return this.fail("当前托盘对应库位表记录大于1条！");
                    }

                    updateOb1 = new WmsLocationStereoscopic();
                    updateOb1.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                    updateOb1.setPalletCodeNull("null");
                    updateOb1.setUseStatus("0");
                    updateOb1.setLastModifiedBy("wcs");
                    updateOb1.setGmtModified(new Date());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb1);
                    condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                } else if (null == pallet.getLockBy() || "".equals(pallet.getLockBy())) {
                    String rtnMSg = "第一次上传处理过，第二次不用处理，直接返回成功！";
                    condition.setUserDefined1(rtnMSg);
                    this.wmsTaskExecutionLogMapper.create(condition);
                    return this.success(rtnMSg);
                }

                if (pallet.getPalletCode().equals(pallet.getGoodsCode())) {
                    this.wmsPalletMapper.delete(pallet);
                } else {
                    WmsPallet updateOb = new WmsPallet();
                    updateOb.setPalletId(pallet.getPalletId());
                    updateOb.setLockByNull("null");
                    updateOb.setLocationCodeNull("null");
                    updateOb.setChannelLocationNull("null");
                    updateOb.setGoodsCodeNull("null");
                    updateOb.setBatchNoNull("null");
                    updateOb.setAmountNull("null");
                    updateOb.setLastModifiedBy("wcs");
                    updateOb.setUserDefined3Null("null");
                    updateOb.setGmtModified(new Date());
                    this.wmsPalletMapper.updateBySelect(updateOb);
                }

                if (pallet.getChannelLocation() != null && !"".equals(pallet.getChannelLocation())) {
                    updateOb1 = new WmsLocationStereoscopic();
                    updateOb1.setLocationCode(pallet.getChannelLocation());
                    updateOb1.setUseStatus("0");
                    updateOb1.setLastModifiedBy("wsc");
                    updateOb1.setGmtModified(new Date());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb1);
                }

                WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail = new WmsOrderOutStereoscopicDeail();
                wmsOrderOutStereoscopicDeail.setOrderNo(condition.getOrderNo());
                wmsOrderOutStereoscopicDeail.setGoodsCode(pallet.getGoodsCode());
                wmsOrderOutStereoscopicDeail.setBatchNo(pallet.getBatchNo());
                wmsOrderOutStereoscopicDeail.setRealAmount(pallet.getAmount());
                wmsOrderOutStereoscopicDeail.setLastModifiedBy("wcs");
                wmsOrderOutStereoscopicDeail.setGmtModified(new Date());
                this.wmsOrderOutStereoscopicDeailMapper.updateByOrderNo_HB(wmsOrderOutStereoscopicDeail);
                WmsOrderOutStereoscopicDeail completeOb = new WmsOrderOutStereoscopicDeail();
                completeOb.setOrderNo(condition.getOrderNo());
                completeOb.setPlanAmountMoreRealAmount("PlanAmountMoreRealAmount");
                completeOb.setActiveFlag("1");
                List<WmsOrderOutStereoscopicDeail> completeList = this.wmsOrderOutStereoscopicDeailMapper.queryByAny(completeOb);
                if (completeList == null || completeList.isEmpty()) {
                    WmsOrderOutStereoscopic record = new WmsOrderOutStereoscopic();
                    record.setOrderNo(condition.getOrderNo());
                    record.setOrderStatus("3");
                    record.setLastModifiedBy("wcs");
                    record.setGmtModified(new Date());
                    this.wmsOrderOutStereoscopicMapper.updateByOrderNo(record);
                }

                WmsGoods wmsGoods = this.wmsGoodsMapper.queryByCode(pallet.getGoodsCode());
                WmsOutTemporary wmsOutTemporary = new WmsOutTemporary();
                wmsOutTemporary.setGoodsCode(pallet.getGoodsCode());
                wmsOutTemporary.setActiveFlag("1");
                List<WmsOutTemporary> wmsOutTemporaryList = this.wmsOutTemporaryMapper.queryByAny(wmsOutTemporary);
                if (wmsOutTemporaryList != null && !wmsOutTemporaryList.isEmpty()) {
                    WmsOutTemporary updateOb = new WmsOutTemporary();
                    updateOb.setGoodsCode(pallet.getGoodsCode());
                    updateOb.setAmountAdd(pallet.getAmount());
                    updateOb.setLastModifiedBy("wcs");
                    updateOb.setGmtModified(new Date());
                    this.wmsOutTemporaryMapper.updateByGoodsCode(updateOb);
                } else {
                    if (wmsGoods != null) {
                        wmsOutTemporary.setGoodsName(wmsGoods.getGoodsName());
                    }

                    wmsOutTemporary.setId(CommonUtils.getUUID());
                    wmsOutTemporary.setAmount(pallet.getAmount());
                    wmsOutTemporary.setCreateBy("wcs");
                    wmsOutTemporary.setGmtCreate(condition.getGmtCreate());
                    this.wmsOutTemporaryMapper.create(wmsOutTemporary);
                }

                WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
                wmsBoxBarcode.setPalletCode(pallet.getPalletCode());
                wmsBoxBarcode.setActiveFlag("1");
                List<WmsBoxBarcode> wmsBoxBarcodeList = this.wmsBoxBarcodeMapper.queryByAny(wmsBoxBarcode);
                if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                    this.wmsBoxBarcodeMapper.delete(wmsBoxBarcode);
                    List<WmsOutTemporaryBoxBarcode> wmsOutTemporaryBoxBarcodeList = new ArrayList();
                    Iterator var16 = wmsBoxBarcodeList.iterator();

                    while(var16.hasNext()) {
                        WmsBoxBarcode tmp = (WmsBoxBarcode)var16.next();
                        WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode = new WmsOutTemporaryBoxBarcode();
                        wmsOutTemporaryBoxBarcode.setId(CommonUtils.getUUID());
                        wmsOutTemporaryBoxBarcode.setOrderNo(condition.getOrderNo());
                        wmsOutTemporaryBoxBarcode.setGoodsCode(pallet.getGoodsCode());
                        if (wmsGoods != null) {
                            wmsOutTemporaryBoxBarcode.setGoodsName(wmsGoods.getGoodsName());
                        }

                        wmsOutTemporaryBoxBarcode.setBatchNo(pallet.getBatchNo());
                        wmsOutTemporaryBoxBarcode.setBoxBarcode(tmp.getBoxBarcode());
                        wmsOutTemporaryBoxBarcode.setCreateBy("wcs");
                        wmsOutTemporaryBoxBarcode.setGmtCreate(condition.getGmtCreate());
                        wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                    }

                    this.wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
                }
            }
            //将之前的异常信息清空
            condition.setErrorMsg("完成");
            this.wmsTaskExecutionLogMapper.updateByTaskId(condition);
        } else if (condition.getTaskStatus() != null && "4".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                if (pallet.getChannelLocation() != null && !"".equals(pallet.getChannelLocation())) {
                    searchOb = new WmsLocationStereoscopic();
                    searchOb.setLocationCode(pallet.getChannelLocation());
                    searchOb.setUseStatus("4");
                    searchOb.setLastModifiedBy("wsc");
                    searchOb.setGmtModified(new Date());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(searchOb);
                }

                searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list == null || list.isEmpty()) {
                    return this.fail("当前托盘对应库位表无记录！");
                }

                if (list.size() != 1) {
                    return this.fail("当前托盘对应库位表记录大于1条！");
                }

                updateOb1 = new WmsLocationStereoscopic();
                updateOb1.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                updateOb1.setUseStatus("4");
                updateOb1.setLastModifiedBy("wcs");
                updateOb1.setGmtModified(new Date());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb1);
                condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                WmsOrderOutStereoscopic wmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
                wmsOrderOutStereoscopic.setOrderNo(condition.getOrderNo());
                //wmsOrderOutStereoscopic.setOrderStatus("4");
                wmsOrderOutStereoscopic.setLastModifiedBy("wcs");
                wmsOrderOutStereoscopic.setGmtModified(new Date());
                this.wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);
            }

            this.wmsTaskExecutionLogMapper.updateByTaskId(condition);
        }else if (condition.getTaskStatus() != null && "2".equals(condition.getTaskStatus())) {
            //将之前的异常信息清空
            condition.setErrorMsg("执行中");
            this.wmsTaskExecutionLogMapper.updateByTaskId(condition);
        }

        return this.success();
    }

    @Transactional
    public Resp reportOutSortingTask(WmsTaskExecutionLog condition) {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setPalletCode(condition.getPalletCode());
        wmsPallet.setActiveFlag("1");
        List wmsPalletList;
        WmsPallet pallet;
        if (condition.getTaskStatus() != null && "3".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                WmsPallet wmsPalletUpdateOb = new WmsPallet();
                wmsPalletUpdateOb.setPalletId(pallet.getPalletId());
                wmsPalletUpdateOb.setLockBy("sorting-" + condition.getOrderNo());
                wmsPalletUpdateOb.setLocationCodeNull("null");
                wmsPalletUpdateOb.setChannelLocationNull("null");
                wmsPalletUpdateOb.setLastModifiedBy("wcs");
                wmsPalletUpdateOb.setGmtModified(condition.getGmtCreate());
                this.wmsPalletMapper.updateBySelect(wmsPalletUpdateOb);
                WmsLocationStereoscopic searchOb;
                if (pallet.getChannelLocation() != null && !"".equals(pallet.getChannelLocation())) {
                    searchOb = new WmsLocationStereoscopic();
                    searchOb.setLocationCode(pallet.getChannelLocation());
                    searchOb.setUseStatus("0");
                    searchOb.setLastModifiedBy("wsc");
                    searchOb.setGmtModified(condition.getGmtCreate());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(searchOb);
                }

                searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                List<WmsLocationStereoscopic> list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list == null || list.isEmpty()) {
                    return this.fail("当前托盘对应库位表无记录！");
                }

                if (list.size() != 1) {
                    return this.fail("当前托盘对应库位表记录大于1条！");
                }

                WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
                updateOb.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                updateOb.setPalletCodeNull("null");
                updateOb.setUseStatus("0");
                updateOb.setLastModifiedBy("wcs");
                updateOb.setGmtModified(condition.getGmtCreate());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        } else if (condition.getTaskStatus() != null && "4".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                WmsLocationStereoscopic searchOb;
                if (pallet.getChannelLocation() != null && !"".equals(pallet.getChannelLocation())) {
                    searchOb = new WmsLocationStereoscopic();
                    searchOb.setLocationCode(pallet.getChannelLocation());
                    searchOb.setUseStatus("4");
                    searchOb.setLastModifiedBy("wsc");
                    searchOb.setGmtModified(condition.getGmtCreate());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(searchOb);
                }

                searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                List<WmsLocationStereoscopic> list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list == null || list.isEmpty()) {
                    return this.fail("当前托盘对应库位表无记录！");
                }

                if (list.size() != 1) {
                    return this.fail("当前托盘对应库位表记录大于1条！");
                }

                WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
                updateOb.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                updateOb.setUseStatus("4");
                updateOb.setLastModifiedBy("wcs");
                updateOb.setGmtModified(condition.getGmtCreate());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                WmsOrderOutStereoscopic wmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
                wmsOrderOutStereoscopic.setOrderNo(condition.getOrderNo());
                wmsOrderOutStereoscopic.setOrderStatus("4");
                wmsOrderOutStereoscopic.setLastModifiedBy("wcs");
                wmsOrderOutStereoscopic.setGmtModified(condition.getGmtCreate());
                this.wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        }

        return this.success();
    }

    @Transactional
    public Resp reportOutCheckTask(WmsTaskExecutionLog condition) {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setPalletCode(condition.getPalletCode());
        wmsPallet.setActiveFlag("1");
        List wmsPalletList;
        WmsPallet pallet;
        WmsLocationStereoscopic searchOb;
        List list;
        WmsLocationStereoscopic updateOb;
        if (condition.getTaskStatus() != null && "3".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list != null && !list.isEmpty()) {
                    if (list.size() != 1) {
                        return this.fail("当前托盘对应库位表记录大于1条！");
                    }

                    updateOb = new WmsLocationStereoscopic();
                    updateOb.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                    updateOb.setPalletCodeNull("null");
                    updateOb.setUseStatus("0");
                    updateOb.setLastModifiedBy("wcs");
                    updateOb.setGmtModified(condition.getGmtCreate());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                    condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                } else if (null == pallet.getLockBy() || "".equals(pallet.getLockBy())) {
                    String rtnMSg = "第一次上传处理过，第二次不用处理，直接返回成功！";
                    condition.setUserDefined1(rtnMSg);
                    this.wmsTaskExecutionLogMapper.create(condition);
                    return this.success(rtnMSg);
                }

                WmsPallet wmsPalletUpdateOb = new WmsPallet();
                wmsPalletUpdateOb.setPalletId(pallet.getPalletId());
                wmsPalletUpdateOb.setLockByNull("null");
                wmsPalletUpdateOb.setLocationCodeNull("null");
                wmsPalletUpdateOb.setChannelLocationNull("null");
                wmsPalletUpdateOb.setLastModifiedBy("wcs");
                wmsPalletUpdateOb.setGmtModified(condition.getGmtCreate());
                this.wmsPalletMapper.updateBySelect(wmsPalletUpdateOb);
                if (pallet.getChannelLocation() != null && !"".equals(pallet.getChannelLocation())) {
                    WmsLocationStereoscopic wmsLocationStereoscopic = new WmsLocationStereoscopic();
                    wmsLocationStereoscopic.setLocationCode(pallet.getChannelLocation());
                    wmsLocationStereoscopic.setUseStatus("0");
                    wmsLocationStereoscopic.setLastModifiedBy("wsc");
                    wmsLocationStereoscopic.setGmtModified(condition.getGmtCreate());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(wmsLocationStereoscopic);
                }
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        } else if (condition.getTaskStatus() != null && "4".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                if (pallet.getChannelLocation() != null && !"".equals(pallet.getChannelLocation())) {
                    searchOb = new WmsLocationStereoscopic();
                    searchOb.setLocationCode(pallet.getChannelLocation());
                    searchOb.setUseStatus("4");
                    searchOb.setLastModifiedBy("wsc");
                    searchOb.setGmtModified(condition.getGmtCreate());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(searchOb);
                }

                searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list == null || list.isEmpty()) {
                    return this.fail("当前托盘对应库位表无记录！");
                }

                if (list.size() != 1) {
                    return this.fail("当前托盘对应库位表记录大于1条！");
                }

                updateOb = new WmsLocationStereoscopic();
                updateOb.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                updateOb.setUseStatus("4");
                updateOb.setLastModifiedBy("wcs");
                updateOb.setGmtModified(condition.getGmtCreate());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                WmsOrderCheck wmsOrderCheck = new WmsOrderCheck();
                wmsOrderCheck.setOrderNo(condition.getOrderNo());
                wmsOrderCheck.setOrderStatus("4");
                wmsOrderCheck.setLastModifiedBy("wcs");
                wmsOrderCheck.setGmtModified(condition.getGmtCreate());
                this.wmsOrderCheckMapper.updateByOrderNo(wmsOrderCheck);
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        }

        return this.success();
    }

    @Transactional
    public Resp reportOutCrossTask(WmsTaskExecutionLog condition) {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setPalletCode(condition.getPalletCode());
        wmsPallet.setActiveFlag("1");
        List wmsPalletList;
        WmsPallet pallet;
        if (condition.getTaskStatus() != null && "3".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                if (pallet.getLockBy() != null && !"".equals(pallet.getLockBy())) {
                    condition.setGoodsCode(pallet.getGoodsCode());
                    condition.setBatchNo(pallet.getBatchNo());
                    WmsPallet wmsPalletUpdateOb = new WmsPallet();
                    wmsPalletUpdateOb.setPalletId(pallet.getPalletId());
                    wmsPalletUpdateOb.setLockByNull("null");
                    wmsPalletUpdateOb.setLocationCodeNull("null");
                    wmsPalletUpdateOb.setGoodsCodeNull("null");
                    wmsPalletUpdateOb.setBatchNoNull("null");
                    wmsPalletUpdateOb.setAmountNull("null");
                    wmsPalletUpdateOb.setLastModifiedBy("wcs");
                    wmsPalletUpdateOb.setGmtModified(condition.getGmtCreate());
                    this.wmsPalletMapper.updateBySelect(wmsPalletUpdateOb);
                    WmsGoods wmsGoods = this.wmsGoodsMapper.queryByCode(pallet.getGoodsCode());
                    WmsOutTemporary wmsOutTemporary = new WmsOutTemporary();
                    wmsOutTemporary.setGoodsCode(pallet.getGoodsCode());
                    wmsOutTemporary.setActiveFlag("1");
                    List<WmsOutTemporary> wmsOutTemporaryList = this.wmsOutTemporaryMapper.queryByAny(wmsOutTemporary);
                    if (wmsOutTemporaryList != null && !wmsOutTemporaryList.isEmpty()) {
                        WmsOutTemporary updateOb = new WmsOutTemporary();
                        updateOb.setGoodsCode(pallet.getGoodsCode());
                        updateOb.setAmountAdd(pallet.getAmount());
                        updateOb.setLastModifiedBy("wcs");
                        updateOb.setGmtModified(condition.getGmtCreate());
                        this.wmsOutTemporaryMapper.updateByGoodsCode(updateOb);
                    } else {
                        if (wmsGoods != null) {
                            wmsOutTemporary.setGoodsName(wmsGoods.getGoodsName());
                        }

                        wmsOutTemporary.setId(CommonUtils.getUUID());
                        wmsOutTemporary.setAmount(pallet.getAmount());
                        wmsOutTemporary.setCreateBy("wcs");
                        wmsOutTemporary.setGmtCreate(condition.getGmtCreate());
                        this.wmsOutTemporaryMapper.create(wmsOutTemporary);
                    }

                    WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
                    wmsBoxBarcode.setPalletCode(pallet.getPalletCode());
                    wmsBoxBarcode.setActiveFlag("1");
                    List<WmsBoxBarcode> wmsBoxBarcodeList = this.wmsBoxBarcodeMapper.queryByAny(wmsBoxBarcode);
                    if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                        this.wmsBoxBarcodeMapper.delete(wmsBoxBarcode);
                        List<WmsOutTemporaryBoxBarcode> wmsOutTemporaryBoxBarcodeList = new ArrayList();
                        Iterator var12 = wmsBoxBarcodeList.iterator();

                        while(var12.hasNext()) {
                            WmsBoxBarcode tmp = (WmsBoxBarcode)var12.next();
                            WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode = new WmsOutTemporaryBoxBarcode();
                            wmsOutTemporaryBoxBarcode.setId(CommonUtils.getUUID());
                            wmsOutTemporaryBoxBarcode.setOrderNo(condition.getOrderNo());
                            wmsOutTemporaryBoxBarcode.setGoodsCode(pallet.getGoodsCode());
                            if (wmsGoods != null) {
                                wmsOutTemporaryBoxBarcode.setGoodsName(wmsGoods.getGoodsName());
                            }

                            wmsOutTemporaryBoxBarcode.setBatchNo(pallet.getBatchNo());
                            wmsOutTemporaryBoxBarcode.setBoxBarcode(tmp.getBoxBarcode());
                            wmsOutTemporaryBoxBarcode.setCreateBy("wcs");
                            wmsOutTemporaryBoxBarcode.setGmtCreate(condition.getGmtCreate());
                            wmsOutTemporaryBoxBarcodeList.add(wmsOutTemporaryBoxBarcode);
                        }

                        this.wmsOutTemporaryBoxBarcodeMapper.batchInsert(wmsOutTemporaryBoxBarcodeList);
                    }

                    this.wmsTaskExecutionLogMapper.create(condition);
                    return this.success();
                }

                return this.fail("当前托盘未被锁定");
            }

            return this.fail("托盘码信息有误");
        } else if (condition.getTaskStatus() != null && "4".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList == null || wmsPalletList.isEmpty()) {
                return this.fail("托盘码信息有误");
            }

            pallet = (WmsPallet)wmsPalletList.get(0);
            condition.setGoodsCode(pallet.getGoodsCode());
            condition.setBatchNo(pallet.getBatchNo());
            WmsOrderCrossProduct wmsOrderCrossProduct = new WmsOrderCrossProduct();
            wmsOrderCrossProduct.setOrderNo(condition.getOrderNo());
            wmsOrderCrossProduct.setOrderStatus("4");
            wmsOrderCrossProduct.setLastModifiedBy("wcs");
            wmsOrderCrossProduct.setGmtModified(condition.getGmtCreate());
            this.wmsOrderCrossProductMapper.updateByOrderNo(wmsOrderCrossProduct);
            this.wmsTaskExecutionLogMapper.create(condition);
        }

        return this.success();
    }

    @Transactional
    public Resp reportVirtualpalletTask(WmsTaskExecutionLog condition) {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setPalletCode(condition.getPalletCode());
        wmsPallet.setActiveFlag("1");
        List wmsPalletList;
        WmsPallet pallet;
        if (condition.getTaskStatus() != null && "3".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList == null || wmsPalletList.isEmpty()) {
                return this.fail("托盘码信息有误");
            }

            pallet = (WmsPallet)wmsPalletList.get(0);
            condition.setGoodsCode(pallet.getGoodsCode());
            condition.setBatchNo(pallet.getBatchNo());
            condition.setUserDefined1(Integer.toString(pallet.getAmount()));
            WmsPallet wmsPalletDelOb = new WmsPallet();
            wmsPalletDelOb.setPalletCode(condition.getPalletCode());
            this.wmsPalletMapper.deleteByPalletCode(wmsPalletDelOb);
            WmsLocationStereoscopic searchOb;
            if (pallet.getChannelLocation() != null && !"".equals(pallet.getChannelLocation())) {
                searchOb = new WmsLocationStereoscopic();
                searchOb.setLocationCode(pallet.getChannelLocation());
                searchOb.setUseStatus("0");
                searchOb.setLastModifiedBy("wsc");
                searchOb.setGmtModified(condition.getGmtCreate());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(searchOb);
            }

            searchOb = new WmsLocationStereoscopic();
            searchOb.setPalletCode(pallet.getPalletCode());
            searchOb.setActiveFlag("1");
            List<WmsLocationStereoscopic> list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
            if (list == null || list.isEmpty()) {
                return this.fail("当前托盘对应库位表无记录！");
            }

            if (list.size() != 1) {
                return this.fail("当前托盘对应库位表记录大于1条！");
            }

            WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
            updateOb.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
            updateOb.setPalletCodeNull("null");
            updateOb.setUseStatus("0");
            updateOb.setLastModifiedBy("wcs");
            updateOb.setGmtModified(condition.getGmtCreate());
            this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
            condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
            this.wmsTaskExecutionLogMapper.create(condition);
        } else if (condition.getTaskStatus() != null && "4".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                WmsLocationStereoscopic searchOb;
                if (pallet.getChannelLocation() != null && !"".equals(pallet.getChannelLocation())) {
                    searchOb = new WmsLocationStereoscopic();
                    searchOb.setLocationCode(pallet.getChannelLocation());
                    searchOb.setUseStatus("4");
                    searchOb.setLastModifiedBy("wsc");
                    searchOb.setGmtModified(condition.getGmtCreate());
                    this.wmsLocationStereoscopicMapper.updateByLocationCode(searchOb);
                }

                searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                List<WmsLocationStereoscopic> list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list == null || list.isEmpty()) {
                    return this.fail("当前托盘对应库位表无记录！");
                }

                if (list.size() != 1) {
                    return this.fail("当前托盘对应库位表记录大于1条！");
                }

                WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
                updateOb.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                updateOb.setUseStatus("4");
                updateOb.setLastModifiedBy("wcs");
                updateOb.setGmtModified(condition.getGmtCreate());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        }

        return this.success();
    }

    /**
     *功能描述: 检查wcs请求来的数据
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Transactional
    public Resp checkWcsParamCommon(WcsTransOb wcsTransOb) {
        WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
        if(wcsTransOb!=null) {
            if (wcsTransOb.getPalletCode() != null && !"".equals(wcsTransOb.getPalletCode())) {
                if (wcsTransOb.getTaskStatus() != null && !"".equals(wcsTransOb.getTaskStatus())) {
                    if (wcsTransOb.getTaskType() != null && !"".equals(wcsTransOb.getTaskType())) {
                        if (wcsTransOb.getTaskId() == 0L) {
                            return this.fail("任务号为空！");
                        } else {
                            wmsTaskExecutionLog.setAreaCode(wcsTransOb.getAreaCode());
                            wmsTaskExecutionLog.setTaskId(wcsTransOb.getTaskId());
                            //wmsTaskExecutionLog.setTaskType(wcsTransOb.getTaskType());
                            wmsTaskExecutionLog.setPalletCode(wcsTransOb.getPalletCode());
                            wmsTaskExecutionLog.setTaskStatus(wcsTransOb.getTaskStatus());
                            wmsTaskExecutionLog.setErrorMsg(wcsTransOb.getMsg());
                            wmsTaskExecutionLog.setLastModifiedBy("wcs");
                            wmsTaskExecutionLog.setGmtModified(new Date());
                            wmsTaskExecutionLog.setActiveFlag("1");

                            WmsTaskExecutionLog searchOb = new WmsTaskExecutionLog();
                            searchOb.setTaskId(wcsTransOb.getTaskId());
                            //searchOb.setTaskStatus("1");
                            searchOb.setActiveFlag("1");
                            List<WmsTaskExecutionLog> list = this.wmsTaskExecutionLogMapper.queryByAny(searchOb);
                            if (list != null && !list.isEmpty()) {
                                WmsTaskExecutionLog tmp = (WmsTaskExecutionLog) list.get(0);
                                wmsTaskExecutionLog.setOrderNo(tmp.getOrderNo());
                                wmsTaskExecutionLog.setInAddress(tmp.getInAddress());
                                wmsTaskExecutionLog.setOutAddress(tmp.getOutAddress());
                                wmsTaskExecutionLog.setAreaCode(tmp.getAreaCode());
                                wmsTaskExecutionLog.setLocationCode(tmp.getLocationCode());
                                wmsTaskExecutionLog.setGoodsCode(tmp.getGoodsCode());
                                wmsTaskExecutionLog.setBatchNo(tmp.getBatchNo());
                                wmsTaskExecutionLog.setUserDefined5(tmp.getUserDefined5());
                                wmsTaskExecutionLog.setUserDefined2(tmp.getUserDefined2());
                                if (!wcsTransOb.getPalletCode().equals(tmp.getPalletCode())) {
                                    return this.fail("WCS传递的任务ID与托盘码不匹配！");
                                } /*else if (!wcsTransOb.getTaskType().equals(tmp.getTaskType())) {
                                    return this.fail("WCS传递的任务ID对应的任务类型与传递的任务类型不匹配！");
                                }*/ else {
                                    Resp resp = this.success();
                                    resp.setData(wmsTaskExecutionLog);
                                    return resp;
                                }
                            } else {
                                return this.fail("WCS传递的任务ID有误！");
                            }
                        }
                    } else {
                        return this.fail("任务类型为空！");
                    }
                } else {
                    return this.fail("任务状态为空！");
                }
            } else {
                return this.fail("托盘号为空！");
            }
        }else {
            return this.fail("请求数据为空！");
        }
    }
    /**
     *功能描述: 移库 —— 检查wcs请求来的数据
     * @params
     * @return com.penghaisoft.framework.util.Resp
     */
    @Transactional
    public Resp checkWcsParamCommon_YK(WcsTransOb wcsTransOb) {
        WmsTaskExecutionLog wmsTaskExecutionLog = new WmsTaskExecutionLog();
        if(wcsTransOb!=null) {
            if (wcsTransOb.getPalletCode() != null && !"".equals(wcsTransOb.getPalletCode())) {
                if (wcsTransOb.getTaskStatus() != null && !"".equals(wcsTransOb.getTaskStatus())) {
                    if (wcsTransOb.getTaskType() != null && !"".equals(wcsTransOb.getTaskType())) {
                        if (wcsTransOb.getTaskId() == 0L) {
                            return this.fail("任务号为空！");
                        } else {
                            wmsTaskExecutionLog.setAreaCode(wcsTransOb.getAreaCode());
                            wmsTaskExecutionLog.setTaskId(wcsTransOb.getTaskId());
                            wmsTaskExecutionLog.setOrderNo(wcsTransOb.getOrderNo());
                            wmsTaskExecutionLog.setTaskType(wcsTransOb.getTaskType());
                            wmsTaskExecutionLog.setPalletCode(wcsTransOb.getPalletCode());
                            wmsTaskExecutionLog.setTaskStatus(wcsTransOb.getTaskStatus());
                            wmsTaskExecutionLog.setErrorMsg(wcsTransOb.getMsg());
                            wmsTaskExecutionLog.setCreateBy("wcs");
                            wmsTaskExecutionLog.setGmtCreate(new Date());
                            wmsTaskExecutionLog.setActiveFlag("1");
                            WmsTaskExecutionLog searchOb = new WmsTaskExecutionLog();
                            searchOb.setTaskId(wcsTransOb.getTaskId());
                            searchOb.setOrderNo(wcsTransOb.getOrderNo());
                            searchOb.setTaskStatus("2");
                            searchOb.setActiveFlag("1");
                            List<WmsTaskExecutionLog> list = this.wmsTaskExecutionLogMapper.queryByAny(searchOb);
                            if (list != null && !list.isEmpty()) {
                                WmsTaskExecutionLog tmp = (WmsTaskExecutionLog) list.get(0);
                                wmsTaskExecutionLog.setOrderNo(tmp.getOrderNo());
                                wmsTaskExecutionLog.setInAddress(tmp.getInAddress());
                                wmsTaskExecutionLog.setOutAddress(tmp.getOutAddress());
                                wmsTaskExecutionLog.setAreaCode(tmp.getAreaCode());
                                wmsTaskExecutionLog.setLocationCode(tmp.getLocationCode());
                                wmsTaskExecutionLog.setGoodsCode(tmp.getGoodsCode());
                                wmsTaskExecutionLog.setBatchNo(tmp.getBatchNo());
                                if (!wcsTransOb.getPalletCode().equals(tmp.getPalletCode())) {
                                    return this.fail("WCS传递的任务ID与托盘码不匹配！");
                                } /*else if (!wcsTransOb.getTaskType().equals(tmp.getTaskType())) {
                                    return this.fail("WCS传递的任务ID对应的任务类型与传递的任务类型不匹配！");
                                }*/ else {
                                    Resp resp = this.success();
                                    resp.setData(wmsTaskExecutionLog);
                                    return resp;
                                }
                            } else {
                                return this.fail("未匹配到正在执行的移库任务！");
                            }
                        }
                    } else {
                        return this.fail("任务类型为空！");
                    }
                } else {
                    return this.fail("任务状态为空！");
                }
            } else {
                return this.fail("托盘号为空！");
            }
        }else {
            return this.fail("请求数据为空！");
        }
    }

    public Pager<WmsTaskExecutionLog> countInAndOut(int page, int rows, WmsTaskExecutionLog condition) {
        Pager<WmsTaskExecutionLog> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsTaskExecutionLogMapper.queryCountInAndOut(condition);
        List<WmsTaskExecutionLog> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsTaskExecutionLogMapper.queryInAndOut(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public Resp taskToComplete(long taskId, String palletCode) {
        WmsTaskExecutionLog task = new WmsTaskExecutionLog();
        task.setTaskId(taskId);
        task.setActiveFlag("1");
        List<WmsTaskExecutionLog> list = this.wmsTaskExecutionLogMapper.queryByAny(task);
        if (list != null && !list.isEmpty()) {
            String orderNo = ((WmsTaskExecutionLog)list.get(0)).getOrderNo();
            WmsPallet wmsPallet = new WmsPallet();
            wmsPallet.setPalletCode(palletCode);
            wmsPallet.setActiveFlag("1");
            List<WmsPallet> wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                WmsOrderOutStereoscopicDeail detail = new WmsOrderOutStereoscopicDeail();
                detail.setOrderNo(orderNo);
                detail.setGoodsCode(((WmsPallet)wmsPalletList.get(0)).getGoodsCode());
                detail.setBatchNo(((WmsPallet)wmsPalletList.get(0)).getBatchNo());
                List<WmsOrderOutStereoscopicDeail> detailList = this.wmsOrderOutStereoscopicDeailMapper.queryByAny(detail);
                if (detailList != null && !detailList.isEmpty()) {
                    WmsOrderOutStereoscopicDeail upob = new WmsOrderOutStereoscopicDeail();
                    upob.setOrderOutDetailId(((WmsOrderOutStereoscopicDeail)detailList.get(0)).getOrderOutDetailId());
                    upob.setUserDefined1("1");
                    upob.setUserDefined2(String.valueOf(((WmsPallet)wmsPalletList.get(0)).getAmount()));
                    this.wmsOrderOutStereoscopicDeailMapper.updateBySelect(upob);
                }

                Resp resp = this.success();
                return resp;
            } else {
                log.info("wcs发送状态为9时，显示----wms_pallet根据palletCode查询不到记录！");
                return this.fail("wms_pallet根据palletCode查询不到记录！");
            }
        } else {
            log.info("wcs发送状态为9时，显示----wms_task_execution_log根据taskId查询不到记录！");
            return this.fail("wms_task_execution_log根据taskId查询不到记录！");
        }
    }

    //=============================现场添加接口======================================
    /**
     *功能描述: 查询是否存在入库任务，入库优先 （原材料：50 成品：10）
     * @params
     * @return java.util.List<com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog>
     */
    @Override
    public List<WmsTaskExecutionLog> selReceipt(WmsTaskExecutionLog wmsTaskExecutionLog) {
        return wmsTaskExecutionLogMapper.selReceipt(wmsTaskExecutionLog);
    }

    /**
     *功能描述: 进出周转汇总
     * @params
     * @return java.util.List<com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog>
     */
    @Override
    public List<TurnoverDTO> shaiXuancountInAndOut(WmsTaskExecutionLog wmsTaskExecutionLog){
        long size = this.wmsTaskExecutionLogMapper.queryCountInAndOut(wmsTaskExecutionLog);
        List<TurnoverDTO> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsTaskExecutionLogMapper.queryInAndOutList(wmsTaskExecutionLog);
        }
        return  records;
    }
}
