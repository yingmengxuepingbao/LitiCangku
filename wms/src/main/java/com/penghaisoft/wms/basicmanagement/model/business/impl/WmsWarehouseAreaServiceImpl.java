//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseDictItem;
import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsWarehouseAreaService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsWarehouseAreaMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouseArea;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsWarehouseAreaService")
public class WmsWarehouseAreaServiceImpl extends BaseService implements IWmsWarehouseAreaService {
    @Resource
    private WmsWarehouseAreaMapper wmsWarehouseAreaMapper;

    public WmsWarehouseAreaServiceImpl() {
    }

    public Resp create(WmsWarehouseArea wmsWarehouseArea) {
        WmsWarehouseArea check = new WmsWarehouseArea();
        check.setAreaCode(wmsWarehouseArea.getAreaCode());
        List<WmsWarehouseArea> checkList = this.wmsWarehouseAreaMapper.queryByAny(check);
        if (checkList.size() > 0) {
            return new Resp("1", "该库区编码已存在");
        } else {
            this.wmsWarehouseAreaMapper.create(wmsWarehouseArea);
            return this.success();
        }
    }

    public Resp delete(WmsWarehouseArea wmsWarehouseArea) {
        this.wmsWarehouseAreaMapper.delete(wmsWarehouseArea);
        return this.success();
    }

    public Pager<WmsWarehouseArea> findListByCondition(int page, int rows, WmsWarehouseArea condition) {
        Pager<WmsWarehouseArea> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsWarehouseAreaMapper.queryCount(condition);
        List<WmsWarehouseArea> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsWarehouseAreaMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsWarehouseArea findById(String id) {
        return (WmsWarehouseArea)this.wmsWarehouseAreaMapper.queryById(id);
    }

    public Resp update(WmsWarehouseArea wmsWarehouseArea) {
        this.wmsWarehouseAreaMapper.updateBySelect(wmsWarehouseArea);
        return this.success();
    }

    public List<BaseDictItem> getAreaCodeAll() {
        WmsWarehouseArea wmsWarehouseArea = new WmsWarehouseArea();
        List<WmsWarehouseArea> records = this.wmsWarehouseAreaMapper.queryByAny(wmsWarehouseArea);
        List<BaseDictItem> list = new ArrayList();
        Iterator var4 = records.iterator();

        while(var4.hasNext()) {
            WmsWarehouseArea area = (WmsWarehouseArea)var4.next();
            BaseDictItem item = new BaseDictItem();
            item.setDicItemCode(area.getAreaCode());
            item.setDicItemName(area.getAreaName());
            list.add(item);
        }

        return list;
    }

    public List<BaseDictItem> findAreaCode(WmsWarehouseArea wmsWarehouseArea) {
        wmsWarehouseArea.setAreaType("0");
        List<WmsWarehouseArea> records = this.wmsWarehouseAreaMapper.queryByAny(wmsWarehouseArea);
        List<BaseDictItem> list = new ArrayList();
        Iterator var4 = records.iterator();

        while(var4.hasNext()) {
            WmsWarehouseArea area = (WmsWarehouseArea)var4.next();
            BaseDictItem item = new BaseDictItem();
            item.setDicItemCode(area.getAreaCode());
            item.setDicItemName(area.getAreaCode());
            list.add(item);
        }

        return list;
    }
}
