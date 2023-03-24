//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.logmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.logmanagement.model.business.IWmsSendLogService;
import com.penghaisoft.wms.logmanagement.model.dao.WmsSendLogMapper;
import com.penghaisoft.wms.logmanagement.model.entity.WmsSendLog;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsSendLogService")
public class WmsSendLogServiceImpl extends BaseService implements IWmsSendLogService {
    @Resource
    private WmsSendLogMapper wmsSendLogMapper;

    public WmsSendLogServiceImpl() {
    }

    public Resp create(WmsSendLog wmsSendLog) {
        this.wmsSendLogMapper.create(wmsSendLog);
        return this.success();
    }

    public Pager<WmsSendLog> findListByCondition(int page, int rows, WmsSendLog condition) {
        Pager<WmsSendLog> pager = new Pager();
        List<WmsSendLog> records = new ArrayList();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsSendLogMapper.queryCount(condition);
        if (size > 0L) {
            records = this.wmsSendLogMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsSendLog findById(String id) {
        return (WmsSendLog)this.wmsSendLogMapper.queryById(id);
    }
}
