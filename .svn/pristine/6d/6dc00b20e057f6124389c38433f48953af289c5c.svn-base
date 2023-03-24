//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCheckPalletBoxBarcodeService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCheckPalletBoxBarcodeMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheckPalletBoxBarcode;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsOrderCheckPalletBoxBarcodeService")
public class WmsOrderCheckPalletBoxBarcodeServiceImpl extends BaseService implements IWmsOrderCheckPalletBoxBarcodeService {
    @Resource
    private WmsOrderCheckPalletBoxBarcodeMapper wmsOrderCheckPalletBoxBarcodeMapper;

    public WmsOrderCheckPalletBoxBarcodeServiceImpl() {
    }

    public Resp create(WmsOrderCheckPalletBoxBarcode wmsOrderCheckPalletBoxBarcode) {
        this.wmsOrderCheckPalletBoxBarcodeMapper.create(wmsOrderCheckPalletBoxBarcode);
        return this.success();
    }

    public Resp delete(WmsOrderCheckPalletBoxBarcode wmsOrderCheckPalletBoxBarcode) {
        this.wmsOrderCheckPalletBoxBarcodeMapper.delete(wmsOrderCheckPalletBoxBarcode);
        return this.success();
    }

    public Pager<WmsOrderCheckPalletBoxBarcode> findListByCondition(int page, int rows, WmsOrderCheckPalletBoxBarcode condition) {
        Pager<WmsOrderCheckPalletBoxBarcode> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsOrderCheckPalletBoxBarcodeMapper.queryCount(condition);
        List<WmsOrderCheckPalletBoxBarcode> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsOrderCheckPalletBoxBarcodeMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderCheckPalletBoxBarcode findById(String id) {
        return (WmsOrderCheckPalletBoxBarcode)this.wmsOrderCheckPalletBoxBarcodeMapper.queryById(id);
    }

    public Resp update(WmsOrderCheckPalletBoxBarcode wmsOrderCheckPalletBoxBarcode) {
        this.wmsOrderCheckPalletBoxBarcodeMapper.updateBySelect(wmsOrderCheckPalletBoxBarcode);
        return this.success();
    }

    public List<WmsOrderCheckPalletBoxBarcode> queryByAny(WmsOrderCheckPalletBoxBarcode condition) {
        return this.wmsOrderCheckPalletBoxBarcodeMapper.queryByAny(condition);
    }
}
