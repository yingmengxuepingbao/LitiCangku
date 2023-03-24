//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.logmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.logmanagement.model.business.IWmsMoveLogService;
import com.penghaisoft.wms.logmanagement.model.dao.WmsMoveLogMapper;
import com.penghaisoft.wms.logmanagement.model.entity.WmsMoveLog;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsMoveLogService")
public class WmsMoveLogServiceImpl extends BaseService implements IWmsMoveLogService {
    @Resource
    private WmsMoveLogMapper wmsMoveLogMapper;

    public WmsMoveLogServiceImpl() {
    }

    public Resp create(WmsMoveLog wmsMoveLog) {
        this.wmsMoveLogMapper.create(wmsMoveLog);
        return this.success();
    }

    public Pager<WmsMoveLog> findListByCondition(int page, int rows, WmsMoveLog condition) {
        Pager<WmsMoveLog> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        List<WmsMoveLog> records = new ArrayList();
        long size = this.wmsMoveLogMapper.queryCount(condition);
        if (size > 0L) {
            records = this.wmsMoveLogMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsMoveLog findById(String id) {
        return (WmsMoveLog)this.wmsMoveLogMapper.queryById(id);
    }
}
