//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.logmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.logmanagement.model.entity.WmsSendLog;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WmsSendLogMapper extends BaseMapper<WmsSendLog> {
    Integer batchInsert(List<WmsSendLog> list);

    Integer batchDelete(@Param("entity") WmsSendLog t);

    Integer querySumSendAmountByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
