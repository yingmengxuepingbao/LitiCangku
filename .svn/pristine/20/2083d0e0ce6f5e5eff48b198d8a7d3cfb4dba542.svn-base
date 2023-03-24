package com.penghaisoft.wcs.monitormanagement.model.business.impl;

import com.penghaisoft.wcs.monitormanagement.model.business.IDynamicViewService;
import com.penghaisoft.wcs.taskmanagement.model.dao.WcsAgvTaskMapper;
import com.penghaisoft.wcs.taskmanagement.model.dao.WcsFourwaycarTaskMapper;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsAgvTask;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsFourwaycarTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DynamicViewServiceImpl
 * @Description 获取动态视图所需数据
 * @Author zhangx
 * @Date 2020/6/4 15:15
 **/
@Service
public class DynamicViewServiceImpl implements IDynamicViewService {

    @Autowired
    private WcsAgvTaskMapper wcsAgvTaskMapper;

    @Autowired
    private WcsFourwaycarTaskMapper wcsFourwaycarTaskMapper;

    /**
     * @param stackerIdArr
     * @return
     * @Description 查询设备状态
     * @Date 15:34
     **/
    @Override
    public Map queryDeviceView() {
        Map rtnMap = new HashMap();
        List<String> taskStatusList = new ArrayList<String>();
        taskStatusList.add("2");
        taskStatusList.add("3");
        taskStatusList.add("4");
        taskStatusList.add("5");
//        任务状态1创建；2下发；3到达接货点；4继续任务；5任务完成；21下发失败；41继续任务失败
        List<WcsAgvTask> wcsAgvTaskList = wcsAgvTaskMapper.selectByTaskStatus(taskStatusList);

        List<String> taskStatusList1 = new ArrayList<String>();
        taskStatusList1.add("3");
        taskStatusList1.add("4");
        taskStatusList1.add("5");
//        任务状态 1创建 2 申请任务 3 下发 4 执行 5取货完成6 任务完成   21 申请失败  31 下发失败
        List<WcsFourwaycarTask> wcsFourwaycarTaskList = wcsFourwaycarTaskMapper.selectByTaskStatus(taskStatusList1);

        rtnMap.put("wcsAgvTaskList", wcsAgvTaskList);
        rtnMap.put("wcsFourwaycarTaskList", wcsFourwaycarTaskList);
        return rtnMap;
    }

}
