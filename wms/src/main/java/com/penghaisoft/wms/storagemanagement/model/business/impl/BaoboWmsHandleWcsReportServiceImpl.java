//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsGoodsMapper;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsLocationStereoscopicMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicDeailMapper;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopic;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsHandleWcsReportService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsBoxBarcodeMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOutTemporaryBoxBarcodeMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOutTemporaryMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsTaskExecutionLogMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsBoxBarcode;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOutTemporary;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOutTemporaryBoxBarcode;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import onbon.bx06.Bx6GEnv;
import onbon.bx06.Bx6GScreenClient;
import onbon.bx06.area.TextCaptionBxArea;
import onbon.bx06.area.page.TextBxPage;
import onbon.bx06.file.ProgramBxFile;
import onbon.bx06.series.Bx6E;
import onbon.bx06.utils.DisplayStyleFactory;
import onbon.bx06.utils.DisplayStyleFactory.DisplayStyle;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("baoboWmsHandleWcsReportService")
public class BaoboWmsHandleWcsReportServiceImpl extends BaseService implements IWmsHandleWcsReportService {
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

    public BaoboWmsHandleWcsReportServiceImpl() {
    }

    @Transactional
    public Resp handlePalletHandOutTask(WmsTaskExecutionLog condition) {
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
                    updateOb1.setGmtModified(condition.getGmtCreate());
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
                    updateOb.setGmtModified(condition.getGmtCreate());
                    this.wmsPalletMapper.updateBySelect(updateOb);
                }

                WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail = new WmsOrderOutStereoscopicDeail();
                wmsOrderOutStereoscopicDeail.setOrderNo(condition.getOrderNo());
                wmsOrderOutStereoscopicDeail.setGoodsCode(pallet.getGoodsCode());
                wmsOrderOutStereoscopicDeail.setBatchNo(pallet.getBatchNo());
                wmsOrderOutStereoscopicDeail.setRealAmountAdd(pallet.getAmount());
                wmsOrderOutStereoscopicDeail.setLastModifiedBy("wcs");
                wmsOrderOutStereoscopicDeail.setGmtModified(condition.getGmtCreate());
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
                    record.setGmtModified(condition.getGmtCreate());
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
                updateOb1.setGmtModified(condition.getGmtCreate());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb1);
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
    public Resp handleSortHandOutTask(WmsTaskExecutionLog condition) {
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
                    updateOb.setLastModifiedBy("wcs-upload");
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
                wmsPalletUpdateOb.setLocationCodeNull("null");
                wmsPalletUpdateOb.setLastModifiedBy("wcs-upload");
                wmsPalletUpdateOb.setGmtModified(condition.getGmtCreate());
                this.wmsPalletMapper.updateBySelect(wmsPalletUpdateOb);
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        } else if (condition.getTaskStatus() != null && "4".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
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
                updateOb.setLastModifiedBy("wcs-upload");
                updateOb.setGmtModified(condition.getGmtCreate());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                WmsOrderOutStereoscopic wmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
                wmsOrderOutStereoscopic.setOrderNo(condition.getOrderNo());
                wmsOrderOutStereoscopic.setOrderStatus("4");
                wmsOrderOutStereoscopic.setLastModifiedBy("wcs-upload");
                wmsOrderOutStereoscopic.setGmtModified(condition.getGmtCreate());
                this.wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        }

        return this.success();
    }

    @Transactional
    public Resp handleAutoSelectTask(WmsTaskExecutionLog condition) {
        Date now = new Date();
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setPalletCode(condition.getPalletCode());
        wmsPallet.setActiveFlag("1");
        List wmsPalletList;
        WmsPallet pallet;
        if (condition.getTaskStatus() != null && "3".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                String orderNo = pallet.getUserDefined1();
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                condition.setOrderNo(orderNo);
                WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
                searchOb.setPalletCode(pallet.getPalletCode());
                searchOb.setActiveFlag("1");
                List<WmsLocationStereoscopic> list = this.wmsLocationStereoscopicMapper.queryByAny(searchOb);
                if (list != null && !list.isEmpty()) {
                    if (list.size() != 1) {
                        return this.fail("当前托盘对应库位表记录大于1条！");
                    }

                    WmsLocationStereoscopic updateOb = new WmsLocationStereoscopic();
                    updateOb.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                    updateOb.setPalletCodeNull("null");
                    updateOb.setUseStatus("0");
                    updateOb.setLastModifiedBy("wcs-upload");
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
                wmsPalletUpdateOb.setLastModifiedBy("wcs-upload");
                wmsPalletUpdateOb.setGmtModified(condition.getGmtCreate());
                this.wmsPalletMapper.updateBySelect(wmsPalletUpdateOb);
                WmsOrderOutStereoscopicDeail searchDetailOb = new WmsOrderOutStereoscopicDeail();
                searchDetailOb.setOrderNo(condition.getOrderNo());
                searchDetailOb.setGoodsCode(condition.getGoodsCode());
                searchDetailOb.setBatchNo(condition.getBatchNo());
                List<WmsOrderOutStereoscopicDeail> deailList = this.wmsOrderOutStereoscopicDeailMapper.queryByAny(searchDetailOb);
                WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail;
                if (deailList != null && !deailList.isEmpty()) {
                    wmsOrderOutStereoscopicDeail = (WmsOrderOutStereoscopicDeail)deailList.get(0);
                    WmsOrderOutStereoscopicDeail detailOb = new WmsOrderOutStereoscopicDeail();
                    detailOb.setOrderOutDetailId(wmsOrderOutStereoscopicDeail.getOrderOutDetailId());
                    Integer realAmount = wmsOrderOutStereoscopicDeail.getRealAmount() == null ? 0 : wmsOrderOutStereoscopicDeail.getRealAmount();
                    if (wmsOrderOutStereoscopicDeail.getPlanAmount() < realAmount + pallet.getAmount()) {
                        detailOb.setRealAmount(wmsOrderOutStereoscopicDeail.getPlanAmount());
                    } else {
                        detailOb.setRealAmountAdd(pallet.getAmount());
                    }

                    detailOb.setLastModifiedBy("wcs-upload");
                    detailOb.setGmtModified(now);
                    this.wmsOrderOutStereoscopicDeailMapper.updateByPrimaryKeySelective(detailOb);
                }

                wmsOrderOutStereoscopicDeail = new WmsOrderOutStereoscopicDeail();
                wmsOrderOutStereoscopicDeail.setOrderNo(condition.getOrderNo());
                wmsOrderOutStereoscopicDeail.setPlanAmountMoreRealAmount("PlanAmountMoreRealAmount");
                wmsOrderOutStereoscopicDeail.setActiveFlag("1");
                List<WmsOrderOutStereoscopicDeail> completeList = this.wmsOrderOutStereoscopicDeailMapper.queryByAny(wmsOrderOutStereoscopicDeail);
                if (completeList == null || completeList.isEmpty()) {
                    WmsOrderOutStereoscopic record = new WmsOrderOutStereoscopic();
                    record.setOrderNo(orderNo);
                    record.setOrderStatus("3");
                    record.setLastModifiedBy("wcs-upload");
                    record.setGmtModified(now);
                    this.wmsOrderOutStereoscopicMapper.updateByOrderNo(record);
                }
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        } else if (condition.getTaskStatus() != null && "4".equals(condition.getTaskStatus())) {
            wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                pallet = (WmsPallet)wmsPalletList.get(0);
                condition.setGoodsCode(pallet.getGoodsCode());
                condition.setBatchNo(pallet.getBatchNo());
                WmsLocationStereoscopic searchOb = new WmsLocationStereoscopic();
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
                updateOb.setLastModifiedBy("wcs-upload");
                updateOb.setGmtModified(condition.getGmtCreate());
                this.wmsLocationStereoscopicMapper.updateByLocationCode(updateOb);
                condition.setLocationCode(((WmsLocationStereoscopic)list.get(0)).getLocationCode());
                WmsOrderOutStereoscopic wmsOrderOutStereoscopic = new WmsOrderOutStereoscopic();
                wmsOrderOutStereoscopic.setOrderNo(condition.getOrderNo());
                wmsOrderOutStereoscopic.setOrderStatus("4");
                wmsOrderOutStereoscopic.setLastModifiedBy("wcs-upload");
                wmsOrderOutStereoscopic.setGmtModified(condition.getGmtCreate());
                this.wmsOrderOutStereoscopicMapper.updateByOrderNo(wmsOrderOutStereoscopic);
            }

            this.wmsTaskExecutionLogMapper.create(condition);
        }

        return this.success();
    }

    public void lockPallet(WmsTaskExecutionLog wmsTaskExecutionLog) {
        WmsPallet pallet = new WmsPallet();
        pallet.setPalletCode(wmsTaskExecutionLog.getPalletCode());
        pallet.setLockBy(wmsTaskExecutionLog.getTaskId().toString());
        pallet.setLocationCode(wmsTaskExecutionLog.getLocationCode());
        pallet.setGmtModified(new Date());
        pallet.setLastModifiedBy("auto-select");
        this.wmsPalletMapper.updateByPalletCode(pallet);
        this.wmsTaskExecutionLogMapper.create(wmsTaskExecutionLog);
    }

    public Resp sendLEDData(WmsTaskExecutionLog condition, String ledIp, String ledPort) {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setPalletCode(condition.getPalletCode());
        wmsPallet.setActiveFlag("1");
        if (condition.getTaskStatus() != null && "3".equals(condition.getTaskStatus())) {
            List<WmsPallet> wmsPalletList = this.wmsPalletMapper.queryByAny(wmsPallet);
            if (wmsPalletList != null && !wmsPalletList.isEmpty()) {
                WmsPallet pallet = (WmsPallet)wmsPalletList.get(0);
                String hasBoxCode = "0";
                WmsBoxBarcode wmsBoxBarcode = new WmsBoxBarcode();
                wmsBoxBarcode.setPalletCode(pallet.getPalletCode());
                List<WmsBoxBarcode> wmsBoxBarcodeList = this.wmsBoxBarcodeMapper.queryByAny(wmsBoxBarcode);
                if (wmsBoxBarcodeList != null && !wmsBoxBarcodeList.isEmpty()) {
                    hasBoxCode = "1";
                }

                WmsOrderOutStereoscopicDeail searchOb = new WmsOrderOutStereoscopicDeail();
                searchOb.setOrderNo(condition.getOrderNo());
                searchOb.setGoodsCode(pallet.getGoodsCode());
                searchOb.setBatchNo(pallet.getBatchNo());
                List<WmsOrderOutStereoscopicDeail> deailList = this.wmsOrderOutStereoscopicDeailMapper.queryByAny(searchOb);
                String isSortingAll = "1";
                Integer planSortAmount = 0;
                Integer amount = pallet.getAmount() == null ? 0 : pallet.getAmount();
                if (deailList != null && !deailList.isEmpty()) {
                    WmsOrderOutStereoscopicDeail detail = (WmsOrderOutStereoscopicDeail)deailList.get(0);
                    Integer planAmount = detail.getPlanAmount() == null ? 0 : detail.getPlanAmount();
                    Integer realAmount = detail.getRealAmount() == null ? 0 : detail.getRealAmount();
                    if (amount > planAmount - realAmount) {
                        isSortingAll = "0";
                        planSortAmount = planAmount - realAmount;
                    }
                } else {
                    isSortingAll = "0";
                    planSortAmount = amount;
                }

                try {
                    DisplayStyle[] styles = (DisplayStyle[])DisplayStyleFactory.getStyles().toArray(new DisplayStyle[0]);
                    Bx6GEnv.initial();
                    Bx6GScreenClient screen = new Bx6GScreenClient("MyScreen", new Bx6E());
                    screen.connect(ledIp, Integer.parseInt(ledPort));
                    ProgramBxFile pf = new ProgramBxFile("P000", screen.getProfile());
                    TextCaptionBxArea area = new TextCaptionBxArea(0, 0, 192, 64, screen.getProfile());
                    TextBxPage page = new TextBxPage("托盘号:" + pallet.getPalletCode());
                    WmsGoods wmsGoods = this.wmsGoodsMapper.queryByCode(pallet.getGoodsCode());
                    if (wmsGoods != null) {
                        page.newLine("商品名称:" + wmsGoods.getGoodsName());
                    } else {
                        page.newLine("商品名称:" + pallet.getGoodsCode());
                    }

                    page.newLine("商品数量:" + pallet.getAmount());
                    if (isSortingAll.equals("0")) {
                        page.newLine("拆托分拣-待分拣数量:" + planSortAmount);
                    } else {
                        page.newLine("整托分拣");
                    }

                    page.setFont(new Font("宋体", 0, 12));
                    page.setDisplayStyle(styles[2]);
                    area.addPage(page);
                    pf.addArea(area);
                    screen.writeProgram(pf);
                    screen.disconnect();
                } catch (Exception var21) {
                    var21.printStackTrace();
                }
            }
        }

        return this.success();
    }

    public Resp sendLEDDataWelcome(String ledIp, String ledPort) {
        try {
            DisplayStyle[] styles = (DisplayStyle[])DisplayStyleFactory.getStyles().toArray(new DisplayStyle[0]);
            Bx6GEnv.initial();
            Bx6GScreenClient screen = new Bx6GScreenClient("MyScreen", new Bx6E());
            screen.connect(ledIp, Integer.parseInt(ledPort));
            ProgramBxFile pf = new ProgramBxFile("P000", screen.getProfile());
            TextCaptionBxArea area = new TextCaptionBxArea(0, 0, 192, 64, screen.getProfile());
            TextBxPage page = new TextBxPage("青岛宝博生物有限公司");
            page.setFont(new Font("宋体", 0, 16));
            page.setDisplayStyle(styles[2]);
            area.addPage(page);
            pf.addArea(area);
            screen.writeProgram(pf);
            screen.disconnect();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return this.success();
    }
}
