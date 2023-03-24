//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsCustomer;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WmsCustomerMapper extends BaseMapper<WmsCustomer> {
    Integer batchInsert(List<WmsCustomer> list);

    Integer batchDelete(@Param("entity") WmsCustomer t);

    void deleteCustomer(@Param("entity") WmsCustomer wmsCustomer);
}
