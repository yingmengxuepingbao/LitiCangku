//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsInTemporaryService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsInTemporaryMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsInTemporary;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsInTemporaryService")
public class WmsInTemporaryServiceImpl extends BaseService implements IWmsInTemporaryService {
    @Resource
    private WmsInTemporaryMapper wmsInTemporaryMapper;

    public WmsInTemporaryServiceImpl() {
    }

    public Resp create(WmsInTemporary wmsInTemporary) {
        this.wmsInTemporaryMapper.create(wmsInTemporary);
        return this.success();
    }

    public Resp delete(WmsInTemporary wmsInTemporary) {
        this.wmsInTemporaryMapper.delete(wmsInTemporary);
        return this.success();
    }

    public Pager<WmsInTemporary> findListByCondition(int page, int rows, WmsInTemporary condition) {
        Pager<WmsInTemporary> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsInTemporaryMapper.queryCount(condition);
        List<WmsInTemporary> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsInTemporaryMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsInTemporary findById(String id) {
        return (WmsInTemporary)this.wmsInTemporaryMapper.queryById(id);
    }

    public Resp update(WmsInTemporary wmsInTemporary) {
        this.wmsInTemporaryMapper.updateBySelect(wmsInTemporary);
        return this.success();
    }

    public List<WmsInTemporary> findExportListByCondition(WmsInTemporary condition) {
        long size = this.wmsInTemporaryMapper.queryCount(condition);
        List<WmsInTemporary> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsInTemporaryMapper.queryList((Pager)null, condition);
        }

        return (List)records;
    }
}
