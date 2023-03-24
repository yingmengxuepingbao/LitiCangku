//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOutTemporaryBoxBarcodeService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOutTemporaryBoxBarcodeMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOutTemporaryBoxBarcode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsOutTemporaryBoxBarcodeService")
public class WmsOutTemporaryBoxBarcodeServiceImpl extends BaseService implements IWmsOutTemporaryBoxBarcodeService {
    @Resource
    private WmsOutTemporaryBoxBarcodeMapper wmsOutTemporaryBoxBarcodeMapper;

    public WmsOutTemporaryBoxBarcodeServiceImpl() {
    }

    public Resp create(WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode) {
        this.wmsOutTemporaryBoxBarcodeMapper.create(wmsOutTemporaryBoxBarcode);
        return this.success();
    }

    public Resp delete(WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode) {
        this.wmsOutTemporaryBoxBarcodeMapper.delete(wmsOutTemporaryBoxBarcode);
        return this.success();
    }

    public Pager<WmsOutTemporaryBoxBarcode> findListByCondition(int page, int rows, WmsOutTemporaryBoxBarcode condition) {
        Pager<WmsOutTemporaryBoxBarcode> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsOutTemporaryBoxBarcodeMapper.queryCount(condition);
        List<WmsOutTemporaryBoxBarcode> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsOutTemporaryBoxBarcodeMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOutTemporaryBoxBarcode findById(String id) {
        return (WmsOutTemporaryBoxBarcode)this.wmsOutTemporaryBoxBarcodeMapper.queryById(id);
    }

    public Resp update(WmsOutTemporaryBoxBarcode wmsOutTemporaryBoxBarcode) {
        this.wmsOutTemporaryBoxBarcodeMapper.updateBySelect(wmsOutTemporaryBoxBarcode);
        return this.success();
    }

    public List<WmsOutTemporaryBoxBarcode> queryByAny(WmsOutTemporaryBoxBarcode condition) {
        return this.wmsOutTemporaryBoxBarcodeMapper.queryByAny(condition);
    }
}
