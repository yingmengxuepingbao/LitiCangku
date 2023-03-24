// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   IWmsAddressRealRelaService.java

package com.penghaisoft.wms.basicmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressRealRela;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IWmsAddressRealRelaService
{

    public abstract Resp create(WmsAddressRealRela wmsaddressrealrela);

    public abstract Resp delete(WmsAddressRealRela wmsaddressrealrela);

    public abstract Pager findListByCondition(int i, int j, WmsAddressRealRela wmsaddressrealrela);

    public abstract WmsAddressRealRela findById(String s);

    public abstract Resp update(WmsAddressRealRela wmsaddressrealrela);

    public abstract List queryByAny(WmsAddressRealRela wmsaddressrealrela);

    public abstract List queryOutAddress(WmsAddressRealRela wmsaddressrealrela);
    /**
     *功能描述:  查询出库口（只有一个出货口）
     * @author zhangxin
     * @date 2022/8/1
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsAddressRealRela>
     */
    List<WmsAddressRealRela> queryHBOutAddress(@Param("entity") WmsAddressRealRela t);
}
