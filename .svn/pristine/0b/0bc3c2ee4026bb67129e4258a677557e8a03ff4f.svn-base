package com.penghaisoft.framework.reportmanagement.model.business;

import com.penghaisoft.framework.reportmanagement.model.entity.Report;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 报表管理业务接口
 * @author 秦超
 * 2017-09-26 15:26:37
 */
public interface IReportManagementBusiness {
	
	/**
	 * 获取错误码
	 * @author 秦超
	 * 2017-09-01 15:23:56
	 */
	public String getLastErrorCode();
	/**
	 * 获取错误信息
	 * @author 秦超
	 * 2017-09-01 15:24:04
	 */
	public String getLastErrorMessage();
	/**
	 * 上传报表文件
	 * @param file
	 * @param fileName
	 * @return
	 * @author 秦超
	 * 2017-09-26 15:29:19
	 */
	public boolean addReport(MultipartFile file, String fileName, String description);
	
	/**
	 * 校验文件
	 * @param file
	 * @param title
	 * @return
	 */
	public boolean checkFile(MultipartFile file, String title);

	/**
	 * 获取报表文件列表
	 * @param currentPage
	 * @return
	 */
	public Map<String, Object> getReportList(int currentPage);

	/**
	 * 获取所有的报表
	 * @return
	 */
    List<Report> getAllReportList();
}
