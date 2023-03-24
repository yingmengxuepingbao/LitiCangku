//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsPlaneLocationService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsPlaneLocationMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsPlaneLocation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsPlaneLocationService")
public class WmsPlaneLocationServiceImpl extends BaseService implements IWmsPlaneLocationService {
    @Resource
    private WmsPlaneLocationMapper wmsPlaneLocationMapper;

    public WmsPlaneLocationServiceImpl() {
    }

    public Resp create(WmsPlaneLocation wmsPlaneLocation) {
        this.wmsPlaneLocationMapper.insertBySelect(wmsPlaneLocation);
        return this.success();
    }

    public Resp delete(WmsPlaneLocation wmsPlaneLocation) {
        this.wmsPlaneLocationMapper.delete(wmsPlaneLocation);
        return this.success();
    }

    public Pager<WmsPlaneLocation> findListByCondition(int page, int rows, WmsPlaneLocation condition) {
        Pager<WmsPlaneLocation> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        List<WmsPlaneLocation> records = this.wmsPlaneLocationMapper.queryList(pager, condition);
        long size = this.wmsPlaneLocationMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsPlaneLocation findById(String id) {
        return (WmsPlaneLocation)this.wmsPlaneLocationMapper.queryById(id);
    }

    public Resp update(WmsPlaneLocation wmsPlaneLocation) {
        this.wmsPlaneLocationMapper.updateBySelect(wmsPlaneLocation);
        return this.success();
    }
}
