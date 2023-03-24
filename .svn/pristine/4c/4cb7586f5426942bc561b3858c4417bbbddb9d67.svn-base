//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.storagemanagement.model.dao;

import com.penghaisoft.framework.util.BaseMapper;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.wms.reportmanagement.model.dto.InOutSummaryDto;
import com.penghaisoft.wms.storagemanagement.model.entity.TurnoverDTO;
import com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WmsTaskExecutionLogMapper extends BaseMapper<WmsTaskExecutionLog> {
    Integer batchInsert(List<WmsTaskExecutionLog> list);

    Integer batchDelete(@Param("entity") WmsTaskExecutionLog t);

    Long queryByAnyCount(@Param("entity") WmsTaskExecutionLog t);

    Integer updateByTaskId(@Param("entity") WmsTaskExecutionLog t);

    long queryCountInAndOut(@Param("entity") WmsTaskExecutionLog condition);

    List<WmsTaskExecutionLog> queryInAndOut(@Param("page") Pager<WmsTaskExecutionLog> pager, @Param("entity") WmsTaskExecutionLog condition);
    /**
     *功能描述: 进出周转汇总
     * @params
     * @return java.util.List<com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog>
     */
    List<TurnoverDTO> queryInAndOutList(@Param("entity") WmsTaskExecutionLog condition);

    List<WmsTaskExecutionLog> queryRunInWhTask();

    List<InOutSummaryDto> queryTaskDays(@Param("taskType") String taskType, @Param("days") Integer days);
    /**
     *功能描述: 现场修改： 将收发货修改成 出入库数据
     * @params
     * @return java.util.List<com.penghaisoft.wms.reportmanagement.model.dto.InOutSummaryDto>
     */
    List<InOutSummaryDto> groupByUseStatus(@Param("entity") WmsTaskExecutionLog wmsTaskExecutionLog);

    /**
     *功能描述: 根据任务状态，查询正在执行或异常的任务执行信息
     * @author zhangxin
     * @date 2022/7/18
     * @params
     * @return java.util.List<com.penghaisoft.pda.outwarehouse.model.WmsTaskExecutionLog>
     */
    List<WmsTaskExecutionLog>  selectByTaskStatus(WmsTaskExecutionLog record);

    /**
     *功能描述: 根据任务号查询数据
     * @date 2022/9/17
     * @params
     * @return com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog
     */
    WmsTaskExecutionLog queryByTask(WmsTaskExecutionLog condition);

    //========================现场添加接口====================
    /**
     *功能描述: 查询是否存在入库任务，入库优先 （原材料：50 成品：10）
     * @params
     * @return java.util.List<com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog>
     */
    List<WmsTaskExecutionLog> selReceipt(@Param("entity") WmsTaskExecutionLog wmsTaskExecutionLog);
    /**
     *功能描述: 出库的时候一托一托出，给可出的数据，排个序
     * @params
     * @return java.util.List<com.penghaisoft.wms.storagemanagement.model.entity.WmsTaskExecutionLog>
     */
    List<WmsTaskExecutionLog> queryByAnyGroup(WmsTaskExecutionLog wmsTaskExecutionLog);
    /**
     *功能描述: 添加物理删除
     * @params
     * @return java.lang.Integer
     */
    public Integer deleteWuli(@Param("entity") WmsTaskExecutionLog wmsTaskExecutionLog);
}
