//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.business.impl;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.business.IWmsCustomerService;
import com.penghaisoft.wms.basicmanagement.model.dao.WmsCustomerMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsCustomer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wmsCustomerService")
public class WmsCustomerServiceImpl extends BaseService implements IWmsCustomerService {
    @Autowired
    private WmsCustomerMapper wmsCustomerMapper;

    public WmsCustomerServiceImpl() {
    }

    public Resp create(WmsCustomer wmsCustomer) {
        WmsCustomer checkCustomer = new WmsCustomer();
        checkCustomer.setCustomerCode(wmsCustomer.getCustomerCode());
        List<WmsCustomer> checkList = this.wmsCustomerMapper.queryByAny(checkCustomer);
        if (checkList.size() > 0) {
            return new Resp("1", "该客户编码已存在");
        } else {
            this.wmsCustomerMapper.create(wmsCustomer);
            return this.success();
        }
    }

    public Resp delete(WmsCustomer wmsCustomer) {
        this.wmsCustomerMapper.deleteCustomer(wmsCustomer);
        return this.success();
    }

    public Pager<WmsCustomer> findListByCondition(int page, int rows, WmsCustomer condition) {
        Pager<WmsCustomer> pager = new Pager();
        pager.setPage(page);
        pager.setRows(rows);
        long size = this.wmsCustomerMapper.queryCount(condition);
        List<WmsCustomer> records = new ArrayList();
        if (size > 0L) {
            records = this.wmsCustomerMapper.queryList(pager, condition);
        }

        pager.setRecords((List)records);
        pager.setTotalCount(size);
        return pager;
    }

    public WmsCustomer findById(String id) {
        return (WmsCustomer)this.wmsCustomerMapper.queryById(id);
    }

    public Resp update(WmsCustomer wmsCustomer) {
        this.wmsCustomerMapper.updateBySelect(wmsCustomer);
        return this.success();
    }
}
