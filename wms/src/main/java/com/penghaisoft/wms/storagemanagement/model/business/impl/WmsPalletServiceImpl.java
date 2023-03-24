//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.constant.Constant.TaskType;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.CommonUtils;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsWarehouseAreaMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouseArea;
import com.penghaisoft.wms.common.service.IWmsCommonService;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsPalletService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsBoxBarcodeMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsInTemporaryMapper;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsBoxBarcode;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsInTemporary;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsPallet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service("wmsPalletService")
public class WmsPalletServiceImpl extends BaseService implements IWmsPalletService {
    @Resource
    private WmsPalletMapper wmsPalletMapper;
    @Resource
    private WmsBoxBarcodeMapper wmsBoxBarcodeMapper;
    @Resource
    private WmsInTemporaryMapper wmsInTemporaryMapper;
    @Resource
    private WmsWarehouseAreaMapper warehouseAreaMapper;
    @Autowired
    private IWmsCommonService wmsCommonService;

    public WmsPalletServiceImpl() {
    }

    public Resp create(WmsPallet wmsPallet) {
        WmsPallet check = new WmsPallet();
        check.setPalletCode(wmsPallet.getPalletCode());
        List<WmsPallet> checkList = this.wmsPalletMapper.queryByAny(check);
        if (checkList.size() > 0) {
            return new Resp("1", "该托盘编码已存在");
        } else {
            this.wmsPalletMapper.create(wmsPallet);
            return this.success();
        }
    }

    public Resp delete(WmsPallet wmsPallet) {
        Resp resp = new Resp();
        List<WmsPallet> list = this.wmsPalletMapper.queryByAny(wmsPallet);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("托盘不存在");
            return resp;
        } else {
            String goodsCode = ((WmsPallet)list.get(0)).getGoodsCode();
            if (goodsCode != null && !"".equals(goodsCode)) {
                resp.setCode("1");
                resp.setMsg("空托盘才可删除");
                return resp;
            } else {
                this.wmsPalletMapper.delete(wmsPallet);
                return this.success();
            }
        }
    }

    public Pager<WmsPallet> findListByCondition(int page, int rows, WmsPallet condition) {
        Pager<WmsPallet> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsPalletMapper.queryCount(condition);
        List<WmsPallet> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsPalletMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsPallet findById(String id) {
        return (WmsPallet)this.wmsPalletMapper.queryById(id);
    }

    @Transactional
    public Resp update(WmsPallet wmsPallet) {
        Resp resp = new Resp();
        Date now = new Date();
        String palletCode = wmsPallet.getPalletCode();
        List<String> boxList = wmsPallet.getBoxList();
        WmsPallet pallet = new WmsPallet();
        pallet.setPalletCode(palletCode);
        List<WmsPallet> pallets = this.wmsPalletMapper.queryByAny(pallet);
        if (pallets.size() > 0 && ((WmsPallet)pallets.get(0)).getGoodsCode() != null && !"".equals(((WmsPallet)pallets.get(0)).getGoodsCode())) {
            resp.setCode("1");
            resp.setMsg("该托盘已绑定商品");
            return resp;
        } else {
            Set<String> s = new HashSet();
            Iterator var9 = boxList.iterator();

            while(var9.hasNext()) {
                String str = (String)var9.next();
                boolean b = s.add(str);
                if (!b) {
                    resp.setCode("1");
                    resp.setMsg("箱码列表中有重复箱码");
                    return resp;
                }
            }

            WmsInTemporary wmsInTemporary = new WmsInTemporary();
            wmsInTemporary.setGoodsCode(wmsPallet.getGoodsCode());
            wmsInTemporary.setActiveFlag("1");
            List<WmsInTemporary> inTemporaryList = this.wmsInTemporaryMapper.queryByAny(wmsInTemporary);
            if (inTemporaryList.size() <= 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resp.setCode("1");
                resp.setMsg("查询不到该商品暂存区库存，无法绑定");
                return resp;
            } else if (((WmsInTemporary)inTemporaryList.get(0)).getAmount() < wmsPallet.getAmount()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                resp.setCode("1");
                resp.setMsg("该商品暂存区库存不足，无法绑定");
                return resp;
            } else {
                int changeAmount = ((WmsInTemporary)inTemporaryList.get(0)).getAmount() - wmsPallet.getAmount();
                WmsInTemporary inTemporary = new WmsInTemporary();
                inTemporary.setId(((WmsInTemporary)inTemporaryList.get(0)).getId());
                inTemporary.setAmount(changeAmount);
                inTemporary.setLastModifiedBy(wmsPallet.getLastModifiedBy());
                inTemporary.setGmtModified(now);
                int tempNum = this.wmsInTemporaryMapper.updateBySelect(inTemporary);
                if (tempNum <= 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    resp.setCode("1");
                    resp.setMsg("异常 该商品暂存区库存不足，无法绑定");
                    return resp;
                } else {
                    int palletNum = this.wmsPalletMapper.updateBySelect(wmsPallet);
                    if (palletNum <= 0) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        resp.setCode("1");
                        resp.setMsg("异常 绑定失败");
                        return resp;
                    } else {
                        WmsBoxBarcode boxBarcodeDelete = new WmsBoxBarcode();
                        boxBarcodeDelete.setPalletCode(palletCode);
                        this.wmsBoxBarcodeMapper.deleteAllBarcode(boxBarcodeDelete);
                        WmsBoxBarcode wmsBoxBarcode;
                        int var20;
                        if (boxList != null && boxList.size() > 0) {
                            for(Iterator var17 = boxList.iterator(); var17.hasNext(); var20 = this.wmsBoxBarcodeMapper.create(wmsBoxBarcode)) {
                                String boxBarcode = (String)var17.next();
                                wmsBoxBarcode = new WmsBoxBarcode();
                                wmsBoxBarcode.setBoxBarcode(boxBarcode);
                                wmsBoxBarcode.setPalletCode(palletCode);
                                wmsBoxBarcode.setActiveFlag("1");
                                wmsBoxBarcode.setGmtCreate(now);
                                wmsBoxBarcode.setCreateBy(wmsPallet.getLastModifiedBy());
                            }
                        }

                        return this.success();
                    }
                }
            }
        }
    }

    public List<WmsPallet> queryByLocationCode(String locationCode) {
        WmsPallet wmsPallet = new WmsPallet();
        wmsPallet.setLocationCode(locationCode);
        List<WmsPallet> list = this.wmsPalletMapper.queryByAny(wmsPallet);
        return list;
    }

    public Pager<WmsPallet> findStereoscopicList(int page, int rows, WmsPallet condition) {
        Pager<WmsPallet> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsPalletMapper.queryCountStereoscopic(condition);
        List<WmsPallet> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsPalletMapper.queryListStereoscopic(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public Pager<WmsPallet> findStereoscopicListHz(int page, int rows, WmsPallet condition) {
        Pager<WmsPallet> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsPalletMapper.queryCountStereoscopicHz(condition);
        List<WmsPallet> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsPalletMapper.queryListStereoscopicHz(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public List<WmsPallet> queryByAny(WmsPallet wmsPallet) {
        List<WmsPallet> list = this.wmsPalletMapper.queryByAny(wmsPallet);
        return list;
    }

    public Resp updateByPalletCode(WmsPallet wmsPallet) {
        this.wmsPalletMapper.updateByPalletCode(wmsPallet);
        return this.success();
    }

    @Transactional
    public Resp relieve(WmsPallet wmsPallet) {
        Resp resp = new Resp();
        WmsPallet newPallet = new WmsPallet();
        newPallet.setPalletId(wmsPallet.getPalletId());
        List<WmsPallet> list = this.wmsPalletMapper.queryByAny(newPallet);
        if (list.size() <= 0) {
            resp.setCode("1");
            resp.setMsg("托盘不存在");
            return resp;
        } else {
            String goodsCode = ((WmsPallet)list.get(0)).getGoodsCode();
            String locationCode = ((WmsPallet)list.get(0)).getLocationCode();
            String palletCode = ((WmsPallet)list.get(0)).getPalletCode();
            if (goodsCode != null && !"".equals(goodsCode)) {
                if (locationCode != null && !"".equals(locationCode)) {
                    resp.setCode("1");
                    resp.setMsg("该托盘已入库");
                    return resp;
                } else {
                    this.wmsPalletMapper.cleanPallet(wmsPallet);
                    WmsBoxBarcode boxBarcodeDelete = new WmsBoxBarcode();
                    boxBarcodeDelete.setPalletCode(palletCode);
                    this.wmsBoxBarcodeMapper.deleteAllBarcode(boxBarcodeDelete);
                    WmsInTemporary wmsInTemporary = new WmsInTemporary();
                    wmsInTemporary.setGoodsCode(goodsCode);
                    wmsInTemporary.setActiveFlag("1");
                    List<WmsInTemporary> inTemporaryList = this.wmsInTemporaryMapper.queryByAny(wmsInTemporary);
                    if (inTemporaryList.size() <= 0) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        resp.setCode("1");
                        resp.setMsg("异常 查询不到该商品暂存区库存");
                        return resp;
                    } else {
                        int changeAmount = ((WmsInTemporary)inTemporaryList.get(0)).getAmount() + ((WmsPallet)list.get(0)).getAmount();
                        WmsInTemporary inTemporary = new WmsInTemporary();
                        inTemporary.setId(((WmsInTemporary)inTemporaryList.get(0)).getId());
                        inTemporary.setAmount(changeAmount);
                        inTemporary.setLastModifiedBy(wmsPallet.getLastModifiedBy());
                        inTemporary.setGmtModified(new Date());
                        int tempNum = this.wmsInTemporaryMapper.updateBySelect(inTemporary);
                        if (tempNum <= 0) {
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            resp.setCode("1");
                            resp.setMsg("异常 该商品暂存区库存不足，无法绑定");
                            return resp;
                        } else {
                            return this.success();
                        }
                    }
                }
            } else {
                resp.setCode("1");
                resp.setMsg("该托盘未绑定");
                return resp;
            }
        }
    }

    public Resp palletizerBind(WmsPallet wmsPallet) {
        Resp resp = new Resp();
        String palletCode = wmsPallet.getPalletCode();
        String batchNo = wmsPallet.getBatchNo();
        String goodsCode = wmsPallet.getGoodsCode();
        Integer amount = wmsPallet.getAmount();
        String areaCode = wmsPallet.getAreaCode();
        WmsPallet pallet = new WmsPallet();
        pallet.setPalletCode(palletCode);
        List<WmsPallet> pallets = this.wmsPalletMapper.queryByAny(pallet);
        long taskId = 0L;
        String whCode;
        if (pallets.size() > 0) {
            WmsPallet tmp = (WmsPallet)pallets.get(0);
            String tmpGoodsCode = tmp.getGoodsCode() == null ? "" : tmp.getGoodsCode();
            whCode = tmp.getBatchNo() == null ? "" : tmp.getBatchNo();
            if (!"".equals(tmpGoodsCode)) {
                if (!tmpGoodsCode.equals(goodsCode) || !whCode.equals(batchNo)) {
                    resp.setCode("2");
                    resp.setMsg("该托盘已绑定其他商品");
                    return resp;
                }

                taskId = tmp.getTaskId();
            }

            if (taskId == 0L) {
                taskId = this.wmsCommonService.getTaskIds(TaskType.PRODUCT_IN, 1)[0];
            }

            pallet.setPalletId(((WmsPallet)pallets.get(0)).getPalletId());
            pallet.setGoodsCode(goodsCode);
            pallet.setBatchNo(batchNo);
            pallet.setAmount(amount);
            pallet.setAreaCode(areaCode);
            pallet.setLastModifiedBy("wcs");
            pallet.setGmtModified(new Date());
           // String whCode = "";
            WmsWarehouseArea warehouseArea = new WmsWarehouseArea();
            warehouseArea.setAreaCode(areaCode);
            List<WmsWarehouseArea> areaList = this.warehouseAreaMapper.queryByAny(warehouseArea);
            if (areaList.size() > 0) {
                whCode = ((WmsWarehouseArea)areaList.get(0)).getWarehouseCode();
                pallet.setWarehouseCode(whCode);
            }

            this.wmsPalletMapper.updateBySelect(pallet);
        } else {
            pallet.setPalletId(CommonUtils.getUUID());
            pallet.setGoodsCode(goodsCode);
            pallet.setBatchNo(batchNo);
            pallet.setAmount(amount);
            pallet.setAreaCode(areaCode);
            pallet.setCreateBy("wcs");
            pallet.setGmtCreate(new Date());
            pallet.setActiveFlag("1");
            if (taskId == 0L) {
                taskId = this.wmsCommonService.getTaskIds(TaskType.PRODUCT_IN, 1)[0];
            }

            WmsWarehouseArea warehouseArea = new WmsWarehouseArea();
            warehouseArea.setAreaCode(areaCode);
            List<WmsWarehouseArea> areaList = this.warehouseAreaMapper.queryByAny(warehouseArea);
            if (areaList.size() > 0) {
                whCode = ((WmsWarehouseArea)areaList.get(0)).getWarehouseCode();
                pallet.setWarehouseCode(whCode);
            }

            this.wmsPalletMapper.create(pallet);
        }

        return this.success(String.valueOf(taskId));
    }

    public List<WmsPallet> findExportUnshelvesListByCondition(WmsPallet wmsPallet) {
        long size = this.wmsPalletMapper.queryCount(wmsPallet);
        List<WmsPallet> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsPalletMapper.queryList((Pager)null, wmsPallet);
            Iterator var5 = ((List)records).iterator();

            while(var5.hasNext()) {
                WmsPallet pallet = (WmsPallet)var5.next();
                if ("1".equals(pallet.getHasBoxCode())) {
                    WmsBoxBarcode cond = new WmsBoxBarcode();
                    cond.setPalletCode(pallet.getPalletCode());
                    List<String> boxBarcodes = this.wmsBoxBarcodeMapper.queryBarcodeByCondition(cond);
                    pallet.setBoxBarcodeList(String.join(",", boxBarcodes));
                    pallet.setHasBoxCode("是");
                } else {
                    pallet.setHasBoxCode("否");
                }
            }
        }

        return (List)records;
    }

    public List<WmsPallet> findExportStereoscopicListHz(WmsPallet wmsPallet) {
        long size = this.wmsPalletMapper.queryCountStereoscopicHz(wmsPallet);
        List<WmsPallet> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsPalletMapper.queryListStereoscopicHz((Pager)null, wmsPallet);
        }

        return (List)records;
    }
}
