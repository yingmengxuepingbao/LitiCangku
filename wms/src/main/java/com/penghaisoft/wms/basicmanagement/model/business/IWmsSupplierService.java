// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   IWmsSupplierService.java

package com.penghaisoft.wms.basicmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsSupplier;

public interface IWmsSupplierService
{

    public abstract Resp create(WmsSupplier wmssupplier);

    public abstract Resp delete(WmsSupplier wmssupplier);

    public abstract Pager findListByCondition(int i, int j, WmsSupplier wmssupplier);

    public abstract WmsSupplier findById(String s);

    public abstract Resp update(WmsSupplier wmssupplier);
}
