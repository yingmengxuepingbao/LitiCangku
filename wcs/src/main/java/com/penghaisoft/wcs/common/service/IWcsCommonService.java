package com.penghaisoft.wcs.common.service;

import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsLocationReal;
import com.penghaisoft.wcs.monitormanagement.model.entity.WcsErrorLog;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Wcs公用服务
 */
public interface IWcsCommonService {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat sdfYM = new SimpleDateFormat("MMdd");

    static final String WCS_TASK_NO_SEQ_PREFIX = "wcs:seq:task:";


    /**
     * 获取越库的地址信息
     * @return
     */
    int[] getCrossAddress();

    /**
     * 根据库位获取入库缓冲区线体地址
     * 一个入库口可能对应多个库位
     * 但是一个库位理论上是只有一个入库口的
     *
     * @param locationId
     * @return
     */
    Integer getInBufferAddressByLocation(int locationId);


    /**
     * 下给硬件的任务号，不超过10位
     * 获取任务号 月(不到10补9)日+5位序列号
     * eg 931300001
     * @return
     */
    int[] getTaskNo(int size);

    /**
     * 获取任务号 年月日+任务类型+5位序列号
     * @param taskType
     * @param size
     * @return
     */
    long[] getTaskIds(Constant.TaskType taskType, int size);


    /**
    * @Description 根据taskId获取任务类型
    * @Date 2020/7/28 10:13
    * @param taskId
    * @return java.lang.String
     * zhangx
    **/
    String getTaskTypeByTaskId(Long taskId);

    /**
    * @Description 判断当前地址是否合法的出库口
    * @Date 2020/7/31 10:23
    * @param target
    * @return boolean
    **/
    boolean isOutLine(int target);

    List<String> getSeqLen4(String seqName, int size);
}
