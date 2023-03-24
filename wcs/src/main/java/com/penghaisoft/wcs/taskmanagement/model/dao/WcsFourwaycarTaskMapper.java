package com.penghaisoft.wcs.taskmanagement.model.dao;

import com.penghaisoft.wcs.taskmanagement.model.entity.WcsFourwaycarTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsFourwaycarTaskMapper {
    int deleteByPrimaryKey(Integer fourwaycarTaskId);

    int insert(WcsFourwaycarTask record);

    int insertSelective(WcsFourwaycarTask record);

    WcsFourwaycarTask selectByPrimaryKey(Integer fourwaycarTaskId);

    int updateByPrimaryKeySelective(WcsFourwaycarTask record);

    int updateByTaskIdSelective(WcsFourwaycarTask record);

    int updateByPrimaryKey(WcsFourwaycarTask record);

    List<WcsFourwaycarTask> selectByTaskId(Long taskId);

    List<WcsFourwaycarTask> queryAnyByKey(Long parseInt);

    List<WcsFourwaycarTask> selectFourwaycarTaskById(@Param("taskIdList") List<Long> taskIdList);

    int deleteFourwaycarTaskByIdList(@Param("taskIdList") List<Long> taskIdList);

    int updateByTaskNoSelective(WcsFourwaycarTask upd);

    List<WcsFourwaycarTask> selectByTaskNo(Integer taskNo);

    int updateByPrimaryKeyList(WcsFourwaycarTask upd);

    List<WcsFourwaycarTask> selectByTaskStatus(@Param("taskStatusList") List<String> taskStatusList);
}