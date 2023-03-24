// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   IWmsWarehouseAreaService.java

package com.penghaisoft.wms.basicmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsWarehouseArea;
import java.util.List;

public interface IWmsWarehouseAreaService
{

    public abstract Resp create(WmsWarehouseArea wmswarehousearea);

    public abstract Resp delete(WmsWarehouseArea wmswarehousearea);

    public abstract Pager findListByCondition(int i, int j, WmsWarehouseArea wmswarehousearea);

    public abstract WmsWarehouseArea findById(String s);

    public abstract Resp update(WmsWarehouseArea wmswarehousearea);

    public abstract List getAreaCodeAll();

    public abstract List findAreaCode(WmsWarehouseArea wmswarehousearea);
}
