//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsSupplierService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsSupplierMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsSupplier;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsSupplierService")
public class WmsSupplierServiceImpl extends BaseService implements IWmsSupplierService {
    @Resource
    private WmsSupplierMapper wmsSupplierMapper;

    public WmsSupplierServiceImpl() {
    }

    public Resp create(WmsSupplier wmsSupplier) {
        Resp resp = new Resp();
        WmsSupplier supplier = new WmsSupplier();
        supplier.setSupplierCode(wmsSupplier.getSupplierCode());
        List<WmsSupplier> list = this.wmsSupplierMapper.queryByAny(supplier);
        if (list.size() > 0) {
            resp.setCode("1");
            resp.setMsg("该供应商编码已存在");
            return resp;
        } else {
            this.wmsSupplierMapper.create(wmsSupplier);
            return this.success();
        }
    }

    public Resp delete(WmsSupplier wmsSupplier) {
        this.wmsSupplierMapper.delete(wmsSupplier);
        return this.success();
    }

    public Pager<WmsSupplier> findListByCondition(int page, int rows, WmsSupplier condition) {
        Pager<WmsSupplier> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsSupplierMapper.queryCount(condition);
        List<WmsSupplier> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsSupplierMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsSupplier findById(String id) {
        return (WmsSupplier)this.wmsSupplierMapper.queryById(id);
    }

    public Resp update(WmsSupplier wmsSupplier) {
        this.wmsSupplierMapper.updateBySelect(wmsSupplier);
        return this.success();
    }
}
