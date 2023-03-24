//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsStoragePlaneService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsStoragePlaneMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsStoragePlane;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsStoragePlaneService")
public class WmsStoragePlaneServiceImpl extends BaseService implements IWmsStoragePlaneService {
    @Resource
    private WmsStoragePlaneMapper wmsStoragePlaneMapper;

    public WmsStoragePlaneServiceImpl() {
    }

    public Resp create(WmsStoragePlane wmsStoragePlane) {
        this.wmsStoragePlaneMapper.create(wmsStoragePlane);
        return this.success();
    }

    public Resp delete(WmsStoragePlane wmsStoragePlane) {
        this.wmsStoragePlaneMapper.delete(wmsStoragePlane);
        return this.success();
    }

    public Pager<WmsStoragePlane> findListByCondition(int page, int rows, WmsStoragePlane condition) {
        Pager<WmsStoragePlane> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsStoragePlaneMapper.queryCount(condition);
        List<WmsStoragePlane> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsStoragePlaneMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsStoragePlane findById(String id) {
        return (WmsStoragePlane)this.wmsStoragePlaneMapper.queryById(id);
    }

    public Resp update(WmsStoragePlane wmsStoragePlane) {
        this.wmsStoragePlaneMapper.updateBySelect(wmsStoragePlane);
        return this.success();
    }

    public List<BaseDictItem> getAvailableGoods() {
        WmsStoragePlane storagePlane = new WmsStoragePlane();
        List<WmsStoragePlane> records = this.wmsStoragePlaneMapper.getAvailableGoods(storagePlane);
        List<BaseDictItem> list = new ArrayList();
        Iterator var4 = records.iterator();

        while(var4.hasNext()) {
            WmsStoragePlane goods = (WmsStoragePlane)var4.next();
            BaseDictItem item = new BaseDictItem();
            item.setDicItemCode(goods.getGoodsCode());
            item.setDicItemName(goods.getGoodsName());
            list.add(item);
        }

        return list;
    }

    public List<BaseDictItem> findBatchNo(String goodsCode) {
        WmsStoragePlane storagePlane = new WmsStoragePlane();
        storagePlane.setGoodsCode(goodsCode);
        List<WmsStoragePlane> records = this.wmsStoragePlaneMapper.findBatchNo(storagePlane);
        List<BaseDictItem> list = new ArrayList();
        Iterator var5 = records.iterator();

        while(var5.hasNext()) {
            WmsStoragePlane goods = (WmsStoragePlane)var5.next();
            BaseDictItem item = new BaseDictItem();
            item.setDicItemCode(goods.getBatchNo());
            item.setDicItemName(goods.getBatchNo());
            list.add(item);
        }

        return list;
    }

    public List<WmsStoragePlane> findAvaAmount(WmsStoragePlane wmsStoragePlane) {
        List<WmsStoragePlane> records = this.wmsStoragePlaneMapper.queryByAny(wmsStoragePlane);
        return records;
    }

    public List<BaseDictItem> findLocation(WmsStoragePlane wmsStoragePlane) {
        List<WmsStoragePlane> records = this.wmsStoragePlaneMapper.findLocation(wmsStoragePlane);
        List<BaseDictItem> list = new ArrayList();
        Iterator var4 = records.iterator();

        while(var4.hasNext()) {
            WmsStoragePlane goods = (WmsStoragePlane)var4.next();
            BaseDictItem item = new BaseDictItem();
            item.setDicItemCode(goods.getLocationCode());
            item.setDicItemName(goods.getLocationCode());
            list.add(item);
        }

        return list;
    }

    public List<WmsStoragePlane> findAllLocation(WmsStoragePlane wmsStoragePlane) {
        return this.wmsStoragePlaneMapper.findAllLocation(wmsStoragePlane);
    }

    public List<WmsStoragePlane> findExportListByCondition(WmsStoragePlane wmsStoragePlane) {
        List<WmsStoragePlane> records = new ArrayList();
        long size = this.wmsStoragePlaneMapper.queryCount(wmsStoragePlane);
        if (size > 0L) {
            records = this.wmsStoragePlaneMapper.queryList((Pager)null, wmsStoragePlane);
        }

        return (List)records;
    }
}
