//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOutTemporaryService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOutTemporaryMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOutTemporary;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsOutTemporaryService")
public class WmsOutTemporaryServiceImpl extends BaseService implements IWmsOutTemporaryService {
    @Resource
    private WmsOutTemporaryMapper wmsOutTemporaryMapper;

    public WmsOutTemporaryServiceImpl() {
    }

    public Resp create(WmsOutTemporary wmsOutTemporary) {
        this.wmsOutTemporaryMapper.create(wmsOutTemporary);
        return this.success();
    }

    public Resp delete(WmsOutTemporary wmsOutTemporary) {
        this.wmsOutTemporaryMapper.delete(wmsOutTemporary);
        return this.success();
    }

    public Pager<WmsOutTemporary> findListByCondition(int page, int rows, WmsOutTemporary condition) {
        Pager<WmsOutTemporary> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsOutTemporaryMapper.queryCount(condition);
        List<WmsOutTemporary> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsOutTemporaryMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOutTemporary findById(String id) {
        return (WmsOutTemporary)this.wmsOutTemporaryMapper.queryById(id);
    }

    public Resp update(WmsOutTemporary wmsOutTemporary) {
        this.wmsOutTemporaryMapper.updateBySelect(wmsOutTemporary);
        return this.success();
    }

    public List<WmsOutTemporary> findExportListByCondition(WmsOutTemporary outTemporary) {
        long size = this.wmsOutTemporaryMapper.queryCount(outTemporary);
        List<WmsOutTemporary> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsOutTemporaryMapper.queryList((Pager)null, outTemporary);
        }

        return (List)records;
    }
}
