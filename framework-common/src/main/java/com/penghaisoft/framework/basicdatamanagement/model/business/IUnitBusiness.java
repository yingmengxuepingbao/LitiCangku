package com.penghaisoft.framework.basicdatamanagement.model.business;

import com.penghaisoft.framework.basicdatamanagement.model.entity.BaseUnit;
import com.penghaisoft.framework.util.Resp;

import java.util.List;

/**
 * 单位管理接口类
 *
 * @author jzh
 * @date 2020-02-04
 */
public interface IUnitBusiness {

    //获取单位
    List<BaseUnit> getBaseUnitList(int currentPage, int numberOnePage, String account, String unitId, String unitCode, String unitZhName, String unitEnName, String unitChange, String unitType);

    //获取单位总数
    Integer getBaseUnitTotalCount(String account, String unitId, String unitCode, String unitZhName, String unitEnName, String unitChange, String unitType);

    //新增单位
    Resp addBaseUnit(String unitId, String unitCode, String unitZhName, String unitEnName, String unitChange, String unitType, String loginName);

    //删除单位
    Resp deleteBaseUnit(String unitId);

    //根据id获取单位
    BaseUnit getBaseUnitById(String unitId);

    //修改单位
    Resp updateBaseUnit(BaseUnit baseUnit);
}
