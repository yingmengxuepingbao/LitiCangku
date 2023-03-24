package com.penghaisoft.wms.nuohua.service;



import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wms.nuohua.model.entity.WcsInterfaceCallLog;

import java.util.Date;

/**
 * @Description 接口调用记录日志-接口
 * @Author zhangxin
 * @Date 2022-04-14
 **/
public interface WcsInterfaceCallLogService {
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
    public int addWcsInterfaceCallLog(String interfaceName, String status, Date gmtCreate, Date gmtEnd, String errorMsg, Integer elapsedTime);
    /**
     *功能描述: 查询列表
     * @author zhangxin
     * @date 2022/4/14
     * @params
     * @return com.penghaisoft.framework.util.Pager<com.penghaisoft.wcs.huakang.model.entity.WcsInterfaceCallLog>
     */
    public Pager<WcsInterfaceCallLog> findListByCondition(int page, int rows, WcsInterfaceCallLog condition);
}
