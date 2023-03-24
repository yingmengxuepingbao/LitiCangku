//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsPlatformService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsPlatformMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsPlatform;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("wmsPlatformService")
public class WmsPlatformServiceImpl extends BaseService implements IWmsPlatformService {
    @Resource
    private WmsPlatformMapper wmsPlatformMapper;

    public WmsPlatformServiceImpl() {
    }

    public Resp create(WmsPlatform wmsPlatform) {
        WmsPlatform check = new WmsPlatform();
        check.setPlatformCode(wmsPlatform.getPlatformCode());
        List<WmsPlatform> checkList = this.wmsPlatformMapper.queryByAny(check);
        if (checkList.size() > 0) {
            return new Resp("1", "该月台编码已存在");
        } else {
            this.wmsPlatformMapper.create(wmsPlatform);
            return this.success();
        }
    }

    public Resp delete(WmsPlatform wmsPlatform) {
        this.wmsPlatformMapper.delete(wmsPlatform);
        return this.success();
    }

    public Pager<WmsPlatform> findListByCondition(int page, int rows, WmsPlatform condition) {
        Pager<WmsPlatform> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        List<WmsPlatform> records = this.wmsPlatformMapper.queryList(pager, condition);
        long size = this.wmsPlatformMapper.queryCount(condition);
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsPlatform findById(String id) {
        return (WmsPlatform)this.wmsPlatformMapper.queryById(id);
    }

    public Resp update(WmsPlatform wmsPlatform) {
        this.wmsPlatformMapper.updateBySelect(wmsPlatform);
        return this.success();
    }
}
