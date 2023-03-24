package com.penghaisoft.pda.nuohua.model.dao;

import com.penghaisoft.pda.nuohua.model.entity.WmsInterfaceLog;
import org.springframework.stereotype.Repository;

@Repository
public interface WmsInterfaceMapper {

    int insert(WmsInterfaceLog wmsInterfaceLog);
}
