package com.penghaisoft.wcs.taskmanagement.model.dao;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WcsTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WcsTask record);

    int insertSelective(WcsTask record);

    WcsTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WcsTask record);

    int updateByPrimaryKey(WcsTask record);

    List<WcsTask> selectByTaskId(@Param("taskId") Long taskId);

    List<WcsTask> selectByTaskIds(@Param("taskIds")List<Long> taskIds);

    int updateByTaskIdSelective(WcsTask updateTask);

    List<WcsTask> selectByRelyTaskId(@Param("taskId") Long taskId);

    List<WcsTask> selectCompleteTask();

    int updateByPrimaryKeyList(WcsTask updateTask);

    List<WcsTask> queryByGmtEndMax(WcsTask wcsTask);

    int deleteTaskByIdList(@Param("taskIdList") List<Integer> taskIdList);

    List<WcsTask> selectByFromAddressAndStatus(@Param("fromAddress")Integer fromAddress,@Param("taskStatus")String taskStatus);

    List<WcsTask> queryList(@Param("page") Pager<WcsTask> pager,@Param("entity") WcsTask condition);

    long queryCount(@Param("entity") WcsTask condition);

    List<WcsTask> selectByTaskNo(Integer taskNo);


    /**
     * 找到进行中的入库任务
     * @param palletCode
     * @param ingStatus
     * @param taskType
     * @return
     */
    List<WcsTask> selectOneInTaskByByPalletAndStatus(@Param("palletCode")String palletCode, @Param("status")String ingStatus, @Param("taskType")String taskType);

    /**
    * @Description 条件查询
    * @Date 2020/7/17 9:54
    * @return java.util.List<com.penghaisoft.wcs.taskmanagement.model.entity.WcsTask>
    **/
    List<WcsTask> selectByCond(@Param("entity") WcsTask cond);


    List<WcsTask> selectWithFourwaycarTaskNoByStatusOrderBy(@Param("entity") WcsTask cond);


    List<WcsTask> selectByPalletAndTaskIdPrefix(@Param("palletCode")String palletCode, @Param("taskId")String nowYMD);
}