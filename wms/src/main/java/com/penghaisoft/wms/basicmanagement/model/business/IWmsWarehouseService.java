// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   IWmsWarehouseService.java

package com.penghaisoft.wms.basicmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouse;
import java.util.List;

public interface IWmsWarehouseService
{

    public abstract Resp create(WmsWarehouse wmswarehouse);

    public abstract Resp delete(WmsWarehouse wmswarehouse);

    public abstract Pager findListByCondition(int i, int j, WmsWarehouse wmswarehouse);

    public abstract WmsWarehouse findById(String s);

    public abstract Resp update(WmsWarehouse wmswarehouse);

    public abstract List getWarehouseCode();
}
