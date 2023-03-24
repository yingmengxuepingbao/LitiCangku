//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsAddressTransformService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsAddressTransformMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressTransform;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsAddressTransformService")
public class WmsAddressTransformServiceImpl extends BaseService implements IWmsAddressTransformService {
    @Resource
    private WmsAddressTransformMapper wmsAddressTransformMapper;

    public WmsAddressTransformServiceImpl() {
    }

    public Resp create(WmsAddressTransform wmsAddressTransform) {
        this.wmsAddressTransformMapper.create(wmsAddressTransform);
        return this.success();
    }

    public Resp delete(WmsAddressTransform wmsAddressTransform) {
        this.wmsAddressTransformMapper.delete(wmsAddressTransform);
        return this.success();
    }

    public Pager<WmsAddressTransform> findListByCondition(int page, int rows, WmsAddressTransform condition) {
        Pager<WmsAddressTransform> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsAddressTransformMapper.queryCount(condition);
        List<WmsAddressTransform> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsAddressTransformMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsAddressTransform findById(String id) {
        return (WmsAddressTransform)this.wmsAddressTransformMapper.queryById(id);
    }

    public Resp update(WmsAddressTransform wmsAddressTransform) {
        this.wmsAddressTransformMapper.updateBySelect(wmsAddressTransform);
        return this.success();
    }

    public List<WmsAddressTransform> queryByAny(WmsAddressTransform condition) {
        return this.wmsAddressTransformMapper.queryByAny(condition);
    }
}
