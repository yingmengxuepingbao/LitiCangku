//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.basicmanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods;
import java.util.List;

import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import org.apache.ibatis.annotations.Param;

public interface WmsGoodsMapper extends BaseMapper<WmsGoods> {
    Integer batchInsert(List<WmsGoods> list);

    Integer batchDelete(@Param("entity") WmsGoods t);

    void deleteWmsGoods(@Param("entity") WmsGoods wmsGoods);

    WmsGoods queryByCode(String goodsCode);

    List<WmsGoods> getGoodsCodeAllBatchNo(@Param("entity") WmsGoods t);
    /**
     *功能描述: 获取基础商品列表
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    List<WmsGoods> getHBGoodsCodeAllBatchNo(@Param("entity") WmsGoods t);
    /**
     *功能描述: 根据[商品类型] 商品编码分组，排重，获取商品编码，商品名称，批次号
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    List<WmsGoods> getAllBatchNoByType(@Param("entity") WmsGoods t);
    /**
     *功能描述:  根据商品编码，获取批次号
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsGoods>
     */
    List<WmsGoods> getHBBatchNo(@Param("entity") WmsGoods t);

    List<WmsGoods> queryBatch(@Param("dataList") List<WmsGoods> dataList);
}
