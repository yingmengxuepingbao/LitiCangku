//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.logmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.logmanagement.model.business.IWmsCombineLogService;
import com.penghaisoft.wms.logmanagement.model.dao.WmsCombineLogMapper;
import com.penghaisoft.wms.logmanagement.model.entity.WmsCombineLog;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsCombineLogService")
public class WmsCombineLogServiceImpl extends BaseService implements IWmsCombineLogService {
    @Resource
    private WmsCombineLogMapper wmsCombineLogMapper;

    public WmsCombineLogServiceImpl() {
    }

    public Resp create(WmsCombineLog wmsCombineLog) {
        this.wmsCombineLogMapper.create(wmsCombineLog);
        return this.success();
    }

    public Pager<WmsCombineLog> findListByCondition(int page, int rows, WmsCombineLog condition) {
        Pager<WmsCombineLog> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        List<WmsCombineLog> records = this.wmsCombineLogMapper.queryList(pager, condition);
        long size = this.wmsCombineLogMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsCombineLog findById(String id) {
        return (WmsCombineLog)this.wmsCombineLogMapper.queryById(id);
    }
}
