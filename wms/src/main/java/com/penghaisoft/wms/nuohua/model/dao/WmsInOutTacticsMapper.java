package com.penghaisoft.wms.nuohua.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.nuohua.model.entity.WmsInOutTactics;

public interface WmsInOutTacticsMapper extends BaseMapper<WmsInOutTactics> {

    int insert(WmsInOutTactics record);
    int insertSelective(WmsInOutTactics record);
}