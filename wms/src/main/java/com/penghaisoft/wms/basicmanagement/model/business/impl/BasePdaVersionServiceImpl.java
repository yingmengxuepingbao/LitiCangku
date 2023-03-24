//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IBasePdaVersionService;
import com.penghaisoft.wms.basicmanagement.model.dao.BasePdaVersionMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.BasePdaVersion;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("basePdaVersionService")
public class BasePdaVersionServiceImpl extends BaseService implements IBasePdaVersionService {
    @Resource
    private BasePdaVersionMapper basePdaVersionMapper;

    public BasePdaVersionServiceImpl() {
    }

    public Resp create(BasePdaVersion basePdaVersion) {
        this.basePdaVersionMapper.create(basePdaVersion);
        return this.success();
    }

    public Resp delete(BasePdaVersion basePdaVersion) {
        this.basePdaVersionMapper.delete(basePdaVersion);
        return this.success();
    }

    public Pager<BasePdaVersion> findListByCondition(int page, int rows, BasePdaVersion condition) {
        Pager<BasePdaVersion> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.basePdaVersionMapper.queryCount(condition);
        List<BasePdaVersion> records = new ArrayList();
        if (size > 0L) {
            records = this.basePdaVersionMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public BasePdaVersion findById(String id) {
        return (BasePdaVersion)this.basePdaVersionMapper.queryById(id);
    }

    public Resp update(BasePdaVersion basePdaVersion) {
        this.basePdaVersionMapper.updateBySelect(basePdaVersion);
        return this.success();
    }

    public List<BasePdaVersion> queryByAny(BasePdaVersion condition) {
        return this.basePdaVersionMapper.queryByAny(condition);
    }
}
