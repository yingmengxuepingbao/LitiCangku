//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.storagemanagement.model.business.IWmsOrderCheckPalletService;
import com.penghaisoft.wms.storagemanagement.model.dao.WmsOrderCheckPalletMapper;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsOrderCheckPallet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsOrderCheckPalletService")
public class WmsOrderCheckPalletServiceImpl extends BaseService implements IWmsOrderCheckPalletService {
    @Resource
    private WmsOrderCheckPalletMapper wmsOrderCheckPalletMapper;

    public WmsOrderCheckPalletServiceImpl() {
    }

    public Resp create(WmsOrderCheckPallet wmsOrderCheckPallet) {
        this.wmsOrderCheckPalletMapper.create(wmsOrderCheckPallet);
        return this.success();
    }

    public Resp delete(WmsOrderCheckPallet wmsOrderCheckPallet) {
        this.wmsOrderCheckPalletMapper.delete(wmsOrderCheckPallet);
        return this.success();
    }

    public Pager<WmsOrderCheckPallet> findListByCondition(int page, int rows, WmsOrderCheckPallet condition) {
        Pager<WmsOrderCheckPallet> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsOrderCheckPalletMapper.queryCount(condition);
        List<WmsOrderCheckPallet> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsOrderCheckPalletMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderCheckPallet findById(String id) {
        return (WmsOrderCheckPallet)this.wmsOrderCheckPalletMapper.queryById(id);
    }

    public Resp update(WmsOrderCheckPallet wmsOrderCheckPallet) {
        this.wmsOrderCheckPalletMapper.updateBySelect(wmsOrderCheckPallet);
        return this.success();
    }

    public List<WmsOrderCheckPallet> queryByAny(WmsOrderCheckPallet condition) {
        return this.wmsOrderCheckPalletMapper.queryByAny(condition);
    }
}
