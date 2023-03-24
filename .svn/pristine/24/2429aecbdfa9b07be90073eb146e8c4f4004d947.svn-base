package com.penghaisoft.wcs.taskmanagement.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTaskPlane;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
  * @Description
  * @ClassName WcsAgvTaskPlaneMapper
  * @Author luot
  * @Date 2020/7/29 17:34
  **/
@Repository
public interface WcsAgvTaskPlaneMapper {
    int deleteByPrimaryKey(Integer agvTaskId);

    int insert(WcsAgvTaskPlane record);

    int insertSelective(WcsAgvTaskPlane record);

    WcsAgvTaskPlane selectByPrimaryKey(Integer agvTaskId);

    int updateByPrimaryKeySelective(WcsAgvTaskPlane record);

    int updateByPrimaryKey(WcsAgvTaskPlane record);


    List<WcsAgvTaskPlane> selectByCond(WcsAgvTaskPlane cond);
    /**
     * @Description: 根据taskId 查询全部任务
     * @Author: jzh
     * @Date: 2020/7/8
     * @param taskId
     */ 
    List<WcsAgvTaskPlane> queryAnyByKey(Long taskId);

    List<WcsAgvTaskPlane> selectAgvTaskById(@Param("taskIdList") List<Long> taskIdList);

    int deleteAgvTaskByIdList(@Param("taskIdList") List<Long> taskIdList);

    void deleteByUserDefined3(String inTaskNo);


    List<WcsAgvTaskPlane> selectEarlyWaitingInTaskByCond(@Param("entity") WcsAgvTaskPlane cond);


    int updateByTaskCode(WcsAgvTaskPlane upd);


    void deleteByTaskCode(Integer taskCode);

    Integer deleteAll();

    Long queryCount(@Param("entity") WcsAgvTaskPlane record);

    List<WcsAgvTaskPlane> queryList(@Param("page") Pager<WcsAgvTaskPlane> page, @Param("entity") WcsAgvTaskPlane record);
}