package com.penghaisoft.framework.reportmanagement.model.dao;

import com.penghaisoft.framework.reportmanagement.model.entity.Report;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface IReportManagementDao {

	/**
	 * 获取当前页报表列表数据接口
	 * @param startNumber 起始数据位置
	 * @param numberOnePage 每页条数
	 * @author 秦超
	 * 2017-09-28 16:02:16
	 */
	List<Report> getReportList(@Param("startNumber") int startNumber, @Param("numberOnePage") int numberOnePage);

	/**
	 * 获取所有的报表列表
	 * @return
	 */
	List<Report> getAllReportList();

	/**
	 * 获取报表数据总条数
	 * @author 秦超
	 * 2017-09-28 16:02:35
	 */
	int getReportTotalCount();
	
	/**
	 * 记录报表文件数据
	 * @param report
	 * @return
	 */
	boolean insert(Report report);

}
