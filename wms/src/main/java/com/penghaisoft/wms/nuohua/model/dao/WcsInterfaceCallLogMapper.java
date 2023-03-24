package com.penghaisoft.wms.nuohua.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wms.nuohua.model.entity.WcsInterfaceCallLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsInterfaceCallLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WcsInterfaceCallLog record);

    int insertSelective(WcsInterfaceCallLog record);

    WcsInterfaceCallLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WcsInterfaceCallLog record);

    int updateByPrimaryKey(WcsInterfaceCallLog record);

    /**
     *功能描述: 接口调用日志记录 - 查询条数
     * @author zhangxin
     * @date 2022/4/14
     * @params
     * @return java.lang.Long
     */
    Long queryCount(@Param("entity") WcsInterfaceCallLog record);
    /**
     *功能描述: 接口调用日志记录 - 查询列表
     * @author zhangxin
     * @date 2022/4/14
     * @params
     * @return java.util.List<com.penghaisoft.wcs.huakang.model.entity.WcsInterfaceCallLog>
     */
    List<WcsInterfaceCallLog> queryList(@Param("page") Pager<WcsInterfaceCallLog> page, @Param("entity") WcsInterfaceCallLog record);

}