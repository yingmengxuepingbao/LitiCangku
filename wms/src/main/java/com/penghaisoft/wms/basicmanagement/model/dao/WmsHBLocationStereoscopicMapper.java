package com.penghaisoft.wms.basicmanagement.model.dao;

import com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 *功能描述: 项目-业务数据层
 * @params
 * @return
 */
public interface WmsHBLocationStereoscopicMapper {
    /**
     *功能描述:  根据指定层查找可用库位 编号小的
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getLocationInfoByFloor(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述:  根据指定层查找可用库位 编号大的
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getLocationInfoByFloorMax(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述:  查找所有可用库位
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getLocationInfoBylocationCodeList(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 根据商品编码，批次号，查询同层的，同批次的，可用货位的 状态 0：可用
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getLocationCodeByUseStatus(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 查询可用库位
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getKongLocation(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 查询被占用的库位信息
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getZhanyongLocation(@Param("entity") WmsLocationStereoscopic seachOb);

    List<WmsLocationStereoscopic> getRightRecommendLocation(@Param("entity") WmsLocationStereoscopic seachOb);

    List<WmsLocationStereoscopic> getLeftRecommendLocation(@Param("entity") WmsLocationStereoscopic seachOb);

    List<WmsLocationStereoscopic> getMixRightRecommendLocation(@Param("entity") WmsLocationStereoscopic seachOb);

    List<WmsLocationStereoscopic> getMixLeftRecommendLocation(@Param("entity") WmsLocationStereoscopic seachOb);

    List<WmsLocationStereoscopic> getEmptyRecommendLocation();

    List<WmsLocationStereoscopic> getTotalRowLocation(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 移库-根据库位编码，查询信息
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getHBTotalRowLocation(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述:  - 根据 z层,x行，y列，查找是否有货位阻碍通道
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getLocationPass(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述:   现场添加：// 预留的短巷道，根据 z层,x行，y列，查找是否有货位阻碍通道 可用状态不为0
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getLocationPassDuanDao(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述:  - 根据 z层,x行，y列，查找 向道库位是否被锁
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getLocationorlockBy(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述:  -根据商品编码，批次号，查找同一批次,都放在哪些向道里 状态 3：占用
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getLayerByGoodsAndBatch(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述:  -查询备用库位的可用库位
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getBeiYongLocation(@Param("entity") WmsLocationStereoscopic seachOb);

    Long queryUseableGoodsAmount(@Param("entity") WmsLocationStereoscopic t);

    Long queryUseablePalletAmount(@Param("entity") WmsLocationStereoscopic t);

    List<WmsLocationStereoscopic> getOutRihtLocation(@Param("entity") WmsLocationStereoscopic seachOb);

    List<WmsLocationStereoscopic> getOutLeftLocation(@Param("entity") WmsLocationStereoscopic seachOb);

    List<WmsLocationStereoscopic> getOutRihtSimpleLocation(@Param("entity") WmsLocationStereoscopic seachOb);

    List<WmsLocationStereoscopic> getOutLeftSimpleLocation(@Param("entity") WmsLocationStereoscopic seachOb);

    List<WmsLocationStereoscopic> getAllLocationInfo();

    /**
     *功能描述:  - 查询货位前面是否有货物
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> selHuoWu(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述:  -查询空巷道
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getKongXiangDao(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 简单查询同巷道 从大到小排序
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getxiangdaoMax(@Param("entity") WmsLocationStereoscopic seachOb);


    //=============================================现场添加接口查询================================
    /**
     *功能描述: wcs 路径规划失败！根据库位号查询库位信息
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    WmsLocationStereoscopic  seleInformationByLocation(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: wcs 路径规划失败了，将上次推荐的货位的通道排除，查询同批次的可用货位
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    List<WmsLocationStereoscopic>  selectLocationCodeByShelvesNumber(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: wcs 路径规划失败了，将上次推荐的货位的通道排除，查询可用巷道的库位
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    List<WmsLocationStereoscopic>  selectKongXiangDao(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 查询各层，总使用巷道数,并从小到大排序，分配权重
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    List<Integer>   selTunnelCount(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 根据物料号，批次号，查询同批次，被占用的层数信息
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    List<Integer>  getZhanyongCeng(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 根据物料号，批次号，查询 原材料，未过账的，同批次，被占用巷道的信息
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    List<Integer>  getZhanyongDao(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 自动理库：根据物料号，批次号，查询 库位，未过账的，同批次，被占用巷道-库位个数的信息
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    List<WmsLocationStereoscopic>  getZhanyongKuWei(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 根据物料号，批次号，巷道，查询 原材料，未过账的，同批次，被占用库位的信息
     * @params
     * @return com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic
     */
    List<Integer>  selectXiangDaoLocationCode(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 根据商品编码，批次号，查询某巷道，同批次的，状态 变量
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> getLocationCodeByShelvesNumber(@Param("entity") WmsLocationStereoscopic seachOb);
    /**
     *功能描述: 根据商品编码，批次号，查询某巷道，同批次的，状态 变量
     * @params
     * @return java.util.List<com.penghaisoft.wms.basicmanagement.model.entity.WmsLocationStereoscopic>
     */
    List<WmsLocationStereoscopic> quertXiangdaoCheckOutTask(@Param("entity") WmsLocationStereoscopic seachOb);

}