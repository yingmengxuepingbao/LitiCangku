// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   IBasePdaVersionService.java

package com.penghaisoft.wms.basicmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.BasePdaVersion;
import java.util.List;

public interface IBasePdaVersionService
{

    public abstract Resp create(BasePdaVersion basepdaversion);

    public abstract Resp delete(BasePdaVersion basepdaversion);

    public abstract Pager findListByCondition(int i, int j, BasePdaVersion basepdaversion);

    public abstract BasePdaVersion findById(String s);

    public abstract Resp update(BasePdaVersion basepdaversion);

    public abstract List queryByAny(BasePdaVersion basepdaversion);
}
