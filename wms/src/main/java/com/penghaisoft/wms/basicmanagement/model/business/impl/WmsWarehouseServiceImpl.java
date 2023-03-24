//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsWarehouseService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsWarehouseMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsWarehouseService")
public class WmsWarehouseServiceImpl extends BaseService implements IWmsWarehouseService {
    @Resource
    private WmsWarehouseMapper wmsWarehouseMapper;

    public WmsWarehouseServiceImpl() {
    }

    public Resp create(WmsWarehouse wmsWarehouse) {
        WmsWarehouse check = new WmsWarehouse();
        check.setWarehouseCode(wmsWarehouse.getWarehouseCode());
        List<WmsWarehouse> checkList = this.wmsWarehouseMapper.queryByAny(check);
        if (checkList.size() > 0) {
            return new Resp("1", "该仓库编码已存在");
        } else {
            this.wmsWarehouseMapper.create(wmsWarehouse);
            return this.success();
        }
    }

    public Resp delete(WmsWarehouse wmsWarehouse) {
        this.wmsWarehouseMapper.delete(wmsWarehouse);
        return this.success();
    }

    public Pager<WmsWarehouse> findListByCondition(int page, int rows, WmsWarehouse condition) {
        condition.setActiveFlag("1");
        Pager<WmsWarehouse> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsWarehouseMapper.queryCount(condition);
        List<WmsWarehouse> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsWarehouseMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsWarehouse findById(String id) {
        return (WmsWarehouse)this.wmsWarehouseMapper.queryById(id);
    }

    public Resp update(WmsWarehouse wmsWarehouse) {
        this.wmsWarehouseMapper.updateBySelect(wmsWarehouse);
        return this.success();
    }

    public List<BaseDictItem> getWarehouseCode() {
        WmsWarehouse wmsWarehouse = new WmsWarehouse();
        List<WmsWarehouse> records = this.wmsWarehouseMapper.queryByAny(wmsWarehouse);
        List<BaseDictItem> list = new ArrayList();
        Iterator var4 = records.iterator();

        while(var4.hasNext()) {
            WmsWarehouse warehouse = (WmsWarehouse)var4.next();
            BaseDictItem item = new BaseDictItem();
            item.setDicItemCode(warehouse.getWarehouseCode());
            item.setDicItemName(warehouse.getWarehouseName());
            list.add(item);
        }

        return list;
    }
}
