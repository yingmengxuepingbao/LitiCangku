// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   IWmsCustomerService.java

package com.penghaisoft.wms.basicmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsCustomer;

public interface IWmsCustomerService
{

    public abstract Resp create(WmsCustomer wmscustomer);

    public abstract Resp delete(WmsCustomer wmscustomer);

    public abstract Pager findListByCondition(int i, int j, WmsCustomer wmscustomer);

    public abstract WmsCustomer findById(String s);

    public abstract Resp update(WmsCustomer wmscustomer);
}
