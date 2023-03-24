package com.penghaisoft.pda.common;

import java.util.List;

/**
 * 一些公用的服务
 *
 * @author zhangxu
 * @date 2017年9月28日 下午1:09:35
 */
public interface IWmsCommonService {

    /**
     * 获取任务号 年月日+任务类型+5位序列号
     * @param taskType
     * @param size
     * @return
     */
    long[] getTaskIds(Constant.TaskType taskType, int size);

    /**
     * 根据订单类型生成流水号
     * 前缀+年月日+序列号
     * @param orderType
     * @return
     */
    String getOrderNoByType(String orderType);

    List<String> getSeqLen4(String seqName, int size);
}
