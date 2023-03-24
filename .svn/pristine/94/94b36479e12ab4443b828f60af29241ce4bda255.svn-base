package com.penghaisoft.wcs.jobmanagement.model.business;

import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.jobmanagement.model.entity.WcsJobExecutionSummary;

import java.util.List;

public interface IWcsJobExecutionSummaryService {

    public Pager<WcsJobExecutionSummary> findListByCondition(int page, int rows, WcsJobExecutionSummary condition);

    List<WcsJobExecutionSummary> selectMothAgoExecutionSummary();

    Resp dealExecutionSummary(List<WcsJobExecutionSummary> listExecutionSummary);
}
