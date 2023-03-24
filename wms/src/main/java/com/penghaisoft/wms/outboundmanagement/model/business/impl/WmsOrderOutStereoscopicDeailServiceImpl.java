//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.outboundmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.outboundmanagement.model.business.IWmsOrderOutStereoscopicDeailService;
import com.penghaisoft.wms.outboundmanagement.model.dao.WmsOrderOutStereoscopicDeailMapper;
import com.penghaisoft.wms.outboundmanagement.model.entity.WmsOrderOutStereoscopicDeail;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsOrderOutStereoscopicDeailService")
public class WmsOrderOutStereoscopicDeailServiceImpl extends BaseService implements IWmsOrderOutStereoscopicDeailService {
    @Resource
    private WmsOrderOutStereoscopicDeailMapper wmsOrderOutStereoscopicDeailMapper;

    public WmsOrderOutStereoscopicDeailServiceImpl() {
    }

    public Resp create(WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail) {
        this.wmsOrderOutStereoscopicDeailMapper.create(wmsOrderOutStereoscopicDeail);
        return this.success();
    }

    public Resp delete(WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail) {
        this.wmsOrderOutStereoscopicDeailMapper.delete(wmsOrderOutStereoscopicDeail);
        return this.success();
    }

    public Pager<WmsOrderOutStereoscopicDeail> findListByCondition(int page, int rows, WmsOrderOutStereoscopicDeail condition) {
        Pager<WmsOrderOutStereoscopicDeail> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsOrderOutStereoscopicDeailMapper.queryCount(condition);
        List<WmsOrderOutStereoscopicDeail> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsOrderOutStereoscopicDeailMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsOrderOutStereoscopicDeail findById(String id) {
        return (WmsOrderOutStereoscopicDeail)this.wmsOrderOutStereoscopicDeailMapper.queryById(id);
    }

    public Resp update(WmsOrderOutStereoscopicDeail wmsOrderOutStereoscopicDeail) {
        this.wmsOrderOutStereoscopicDeailMapper.updateBySelect(wmsOrderOutStereoscopicDeail);
        return this.success();
    }

    public List<WmsOrderOutStereoscopicDeail> queryByAny(WmsOrderOutStereoscopicDeail condition) {
        return this.wmsOrderOutStereoscopicDeailMapper.queryByAny(condition);
    }
}
