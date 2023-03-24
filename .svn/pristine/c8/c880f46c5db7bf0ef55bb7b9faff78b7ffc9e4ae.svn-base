//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsBoxBarcodeService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsBoxBarcodeMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsBoxBarcode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsBoxBarcodeService")
public class WmsBoxBarcodeServiceImpl extends BaseService implements IWmsBoxBarcodeService {
    @Resource
    private WmsBoxBarcodeMapper wmsBoxBarcodeMapper;

    public WmsBoxBarcodeServiceImpl() {
    }

    public Resp create(WmsBoxBarcode wmsBoxBarcode) {
        this.wmsBoxBarcodeMapper.create(wmsBoxBarcode);
        return this.success();
    }

    public Resp delete(WmsBoxBarcode wmsBoxBarcode) {
        this.wmsBoxBarcodeMapper.delete(wmsBoxBarcode);
        return this.success();
    }

    public Pager<WmsBoxBarcode> findListByCondition(int page, int rows, WmsBoxBarcode condition) {
        Pager<WmsBoxBarcode> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsBoxBarcodeMapper.queryCount(condition);
        List<WmsBoxBarcode> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsBoxBarcodeMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsBoxBarcode findById(String id) {
        return (WmsBoxBarcode)this.wmsBoxBarcodeMapper.queryById(id);
    }

    public Resp update(WmsBoxBarcode wmsBoxBarcode) {
        this.wmsBoxBarcodeMapper.updateBySelect(wmsBoxBarcode);
        return this.success();
    }

    public List<WmsBoxBarcode> queryByAny(WmsBoxBarcode condition) {
        return this.wmsBoxBarcodeMapper.queryByAny(condition);
    }
}
