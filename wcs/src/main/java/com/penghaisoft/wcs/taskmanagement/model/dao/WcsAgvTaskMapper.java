package com.penghaisoft.wcs.taskmanagement.model.dao;

import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsAgvTaskMapper {
    int deleteByPrimaryKey(Integer agvTaskId);

    int insert(WcsAgvTask record);

    int insertSelective(WcsAgvTask record);

    WcsAgvTask selectByPrimaryKey(Integer agvTaskId);

    int updateByPrimaryKeySelective(WcsAgvTask record);

    int updateByPrimaryKey(WcsAgvTask record);


    List<WcsAgvTask> selectByCond(WcsAgvTask cond);
    /**
     * @Description: 根据taskId 查询全部任务
     * @Author: jzh
     * @Date: 2020/7/8
     * @param taskId
     */ 
    List<WcsAgvTask> queryAnyByKey(Long taskId);

    List<WcsAgvTask> selectAgvTaskById(@Param("taskIdList") List<Long> taskIdList);

    List<WcsAgvTask> selectByTaskStatus(@Param("taskStatusList") List<String> taskStatusList);

    int deleteAgvTaskByIdList(@Param("taskIdList") List<Long> taskIdList);

    void deleteByUserDefined3(String inTaskNo);


    List<WcsAgvTask> selectEarlyWaitingInTaskByCond(@Param("entity") WcsAgvTask cond);


    int updateByTaskCode(WcsAgvTask upd);


    void deleteByTaskCode(Integer taskCode);

    Integer deleteAll();


    List<WcsAgvTask> selectByTaskId(Long taskId);
}