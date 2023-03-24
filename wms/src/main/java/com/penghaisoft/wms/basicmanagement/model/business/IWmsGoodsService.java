// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi 
// Source File Name:   IWmsGoodsService.java

package com.penghaisoft.wms.basicmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;

import java.util.List;

public interface IWmsGoodsService
{

    public abstract Resp create(WmsGoods wmsgoods);

    public abstract Resp delete(WmsGoods wmsgoods);

    public abstract Pager findListByCondition(int i, int j, WmsGoods wmsgoods);

    public abstract WmsGoods findById(String s);

    public abstract Resp update(WmsGoods wmsgoods);

    public abstract List getGoodsCodeAll();

    public abstract List getGoodsCodeAllBatchNo();
    /**
     *功能描述: 获取基础商品列表
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    public abstract List getHBGoodsCodeAllBatchNo();
    /**
     *功能描述: -根据[商品类型] 商品编码分组，排重，获取商品编码，商品名称，批次号
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    public List<WmsGoods> getAllBatchNoByType(String goodType);

    public abstract WmsGoods queryByCode(String s);

    /**
     *功能描述:  根据商品编码，获取批次号
     * @params
     * @return java.util.List
     */
    public abstract List getHBBatchNo(WmsGoods wmsgoods);
    /**
     *功能描述: 商品表批量上传-根据商品编码查询数据
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods>
     */
    List<WmsGoods> queryBatch(List<WmsGoods> insertList);

    Resp batchInsertLocation(List<WmsGoods> insertList);
}
