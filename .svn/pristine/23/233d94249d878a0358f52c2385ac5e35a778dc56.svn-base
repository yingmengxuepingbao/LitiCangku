package com.penghaisoft.wms.nuohua.service.impl;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wms.nuohua.model.dao.WcsInterfaceCallLogMapper;
import com.penghaisoft.wms.nuohua.model.entity.WcsInterfaceCallLog;
import com.penghaisoft.wms.nuohua.service.WcsInterfaceCallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 接口调用记录日志-实现类
 * @Author zhangxin
 * @Date 2022-04-14
 **/
@Service("wcsInterfaceCallLogService")
public class WcsInterfaceCallLogServiceImpl implements WcsInterfaceCallLogService {

    @Autowired
    private WcsInterfaceCallLogMapper wcsInterfaceCallLogMapper;
    /**
     *功能描述: 接口调用，记录日志，存数据库
     * @author zhangxin
     * @date 2022/4/14
     * @params  interfaceName 接口名称
     * @params  status 状态1：成功2：异常
     * @params  gmtCreate   创建时间
     * @params  gmtEnd      完成时间
     * @params  errorMsg    错误信息
     * @params  elapsedTime 消耗时间ms
     * @return int
     */
    @Override
    public int addWcsInterfaceCallLog(String interfaceName, String status, Date gmtCreate, Date gmtEnd, String errorMsg, Integer elapsedTime) {
        WcsInterfaceCallLog wcsInterfaceCallLog =new WcsInterfaceCallLog();
        wcsInterfaceCallLog.setInterfaceName(interfaceName);
        wcsInterfaceCallLog.setStatus(status);
        wcsInterfaceCallLog.setGmtCreate(gmtCreate);
        wcsInterfaceCallLog.setGmtEnd(gmtEnd);
        wcsInterfaceCallLog.setErrorMsg(errorMsg);
        wcsInterfaceCallLog.setElapsedTime(elapsedTime);
        int ret = wcsInterfaceCallLogMapper.insertSelective(wcsInterfaceCallLog);
        return ret;
    }

    @Override
    public Pager<WcsInterfaceCallLog> findListByCondition(int page, int rows, WcsInterfaceCallLog condition) {
        Pager<WcsInterfaceCallLog> pager = new Pager<>();
        pager.setPage(page);
        pager.setRows(rows);
        long size = wcsInterfaceCallLogMapper.queryCount(condition);
        List<WcsInterfaceCallLog> records = new ArrayList<WcsInterfaceCallLog>();
        if (size > 0) {
            records = wcsInterfaceCallLogMapper.queryList(pager, condition);
        }
        pager.setRecords(records);
        pager.setTotalCount(size);
        return pager;
    }
}
