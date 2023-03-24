package com.penghaisoft.pda.outwarehouse.dao;

import com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WmsTaskExecutionLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(WmsTaskExecutionLog record);

    WmsTaskExecutionLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WmsTaskExecutionLog record);

    List<WmsTaskExecutionLog> selectByTaskId(@Param("taskId") Long taskId);


    List<WmsTaskExecutionLog> selectLastOutInfoByPallet(@Param("palletCode") String palletCode);

    List<WmsTaskExecutionLog> selectByStatus(String status,String type);

    //=================现场添加=============================
    /**
     *功能描述:
     * @params
     * @return java.util.List<com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog>
     */
    List<WmsTaskExecutionLog> selectTaskByPallet(@Param("entity") WmsTaskExecutionLog condition);
}