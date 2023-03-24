package com.penghaisoft.wcs.taskmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.taskmanagement.model.entity.*;

import java.util.List;

public interface IWcsTaskService {

    List<WcsTask> selectCompleteTask();

    int updateByPrimaryKeySelective(WcsTask record);

    List<WcsTask> selectMothAgoTask();

    Resp dealTask(List<WcsTask> listTask, List<Integer> taskIdList);

    Pager<WcsTask> findListByCondition(int page, int rows, WcsTask wcsTask);

    List<WcsTaskDetailDTO> queryDetailByTaskId(String taskId);

    Resp changeTaskStatus(WcsTaskDetailDTO wcsTaskDetailDTO);

    Resp changeMain(WcsTask wcsTask);

    Resp changeReportWms(WcsTask wcsTask);

    List<WcsFourwaycarTask> selectFourwaycarTaskById(List<Long> taskIdList);

    Resp dealFourWayCarTask(List<WcsFourwaycarTask> fourWayCarTaskList, List<Long> taskIdList);

    List<WcsAgvTask> selectAgvTaskById(List<Long> taskIdList);

    Resp dealAgvTask(List<WcsAgvTask> listAgvTask, List<Long> taskIdList);


    /**
     * 删除无效任务：长时间没完成的
     * @param id
     * @return
     */
    Resp delUselessTask(Integer id);
}
