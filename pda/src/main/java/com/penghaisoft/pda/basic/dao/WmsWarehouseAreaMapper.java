package com.penghaisoft.pda.basic.dao;

import com.penghaisoft.pda.basic.model.WmsWarehouseArea;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsWarehouseAreaMapper {

    WmsWarehouseArea selectByPrimaryKey(String areaId);

    List<WmsWarehouseArea> selectRootAreaCodeByWarehouse();

    List<WmsWarehouseArea> queryAreaInfoByCode(String areaCode);
}