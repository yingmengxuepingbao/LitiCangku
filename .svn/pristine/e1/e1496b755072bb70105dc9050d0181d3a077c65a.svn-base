package com.penghaisoft.framework.reportmanagement.model.business.ipml;

import com.penghaisoft.framework.annotation.OperationLogAspect;
import com.penghaisoft.framework.constant.Constant.ConfigInfo;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.reportmanagement.model.business.IReportManagementBusiness;
import com.penghaisoft.framework.reportmanagement.model.dao.IReportManagementDao;
import com.penghaisoft.framework.reportmanagement.model.entity.Report;
import com.penghaisoft.framework.util.PageNumberTransfermation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表管理业务
 * @author 秦超
 * 2017-10-09 14:30:38
 */
@Slf4j
@Service
public class ReportManagementBusinessImpl implements IReportManagementBusiness {


	@Autowired
	private IReportManagementDao iReportManagementDao;

	//保存错误码
	private List<String> errorCodes = new ArrayList<>();
	//保存错误描述
	private List<String> errorMessages = new ArrayList<>();

	@Value("${report.webroot:empty}")
	private String reportWebroot;

	@Value("${report.contextPath:/ReportServer}")
	private String reportContextPath;


	/**
	 * 获取错误码
	 * @return
	 * @author 秦超
	 * 2017-09-01 15:23:56
	 */
	@Override
	public String getLastErrorCode(){
		return errorCodes.get(errorCodes.size()-1);
	}

	/**
	 * 获取错误信息
	 * @return
	 * @author 秦超
	 * 2017-09-01 15:24:04
	 */
	@Override
	public String getLastErrorMessage(){
		return errorMessages.get(errorMessages.size()-1);
	}

	/**
	 * 校验文件
	 * @author 秦超
	 * 2017-10-09 14:31:02
	 */
	@Override
	public boolean checkFile(MultipartFile reportFile, String reportName) {

		//1.上传文件是否为空
		if(reportFile == null){
			errorCodes.add(RESULT.REPORTMANAGEMENT_UPLOAD_FILE_EMPTY.code);
			errorMessages.add(RESULT.REPORTMANAGEMENT_UPLOAD_FILE_EMPTY.message);

			return false;
		}

		//2.获取文件后缀名判断文件格式
		String expandedName = reportFile.getOriginalFilename().substring(reportFile.getOriginalFilename().lastIndexOf('.'));
		if(!expandedName.equals(ConfigInfo.MENU_FILE_FORMAT_CPT) && !expandedName.equals(ConfigInfo.MENU_FILE_FORMAT_FRM)){
			errorCodes.add(RESULT.REPORTMANAGEMENT_UPLOAD_FORMAT.code);
			errorMessages.add(RESULT.REPORTMANAGEMENT_UPLOAD_FORMAT.message);

			return false;
		}

		//3..判断文件大小
		if(reportFile.getSize() > 10 * 1024 * 1024) {
			errorCodes.add(RESULT.REPORTMANAGEMENT_UPLOAD_FILESIZE.code);
			errorMessages.add(RESULT.REPORTMANAGEMENT_UPLOAD_FILESIZE.message);

			return false;
		}

		//	如果没启用报表，不会上传
		if (reportWebroot.equals("empty")){
			errorCodes.add(RESULT.REPORTMANAGEMENT_UPLOAD_FILE_ERROR.code);
			errorMessages.add(RESULT.REPORTMANAGEMENT_UPLOAD_FILE_ERROR.message);
			return false;
		}else {
			//4.判断文件是否存在
			String reportFullName = reportName + expandedName;
//		/G:/IdeaWorkSpace/penghaisoft/javawebframework-spring-boot/target/classes/WEB-INF/reportlets/zhangx-2.frm

			String filePath = reportWebroot + ConfigInfo.MENU_FILE_REPORT_PATH + reportFullName;
			File uploadFile = new File(filePath);

			if (uploadFile.exists()) {
				errorCodes.add(RESULT.REPORTMANAGEMENT_UPLOAD_FILE_EXIST.code);
				errorMessages.add(RESULT.REPORTMANAGEMENT_UPLOAD_FILE_EXIST.message);

				return false;
			}
		}

		return true;
	}

	/**
	 * 上传报表文件
	 * @return
	 * @author 秦超
	 * 2017-09-26 15:29:19
	 */
	@Override
	@OperationLogAspect(operationName = "新增报表", operationType = "5")
	public boolean addReport(MultipartFile reportFile, String reportName, String description) {

		boolean result = true;

		//1.获取文件后缀名
		String expandedName = reportFile.getOriginalFilename().substring(reportFile.getOriginalFilename().lastIndexOf('.'));
		//2.新的文件名
		String reportFullName = reportName + expandedName;
		//3.保存文件
		String path =  saveReport(reportFile, reportFullName);
		if(null == path){
			return false;
		}

		try {
			//保存文件信息到数据库
//			报表访问路径
//			去除开头/
			String prefix = reportContextPath;
			if(prefix.startsWith("/")){
				prefix = prefix.substring(1, prefix.length());
			}
			String url = prefix + "?formlet=" + reportFullName;
			//构造报表实体
			Report report = new Report(reportName, description, url, path);

			//记录数据
			result = iReportManagementDao.insert(report);
		} catch (Exception e) {
			errorCodes.add(RESULT.REPORTMANAGEMENT_UPLOAD_INSERT.code);
			errorMessages.add(RESULT.REPORTMANAGEMENT_UPLOAD_INSERT.message);

			log.error("业务抛错：", e);
			//删除文件
			deleteReport(reportFullName);
			return false;
		}

		return result;
	}

	/**
	 * 保存文件
	 * @param reportFile 文件
	 * @param reportName 文件名
	 * @return
	 * @author 秦超
	 * 2017-09-26 18:43:27
	 */
	private String saveReport(MultipartFile reportFile, String reportName){

		//1.1 文件上传目录绝对路径
		String directoryPath = reportWebroot + ConfigInfo.MENU_FILE_REPORT_PATH;
		File fileDirectory = new File(directoryPath);

		//1.2 上传目录不存在则创建
		if (!fileDirectory.exists()) {
			fileDirectory.mkdirs();
		}

		//2. 判断存储目录剩余空间大小
		if(fileDirectory.getFreeSpace() < reportFile.getSize()){
			errorCodes.add(RESULT.REPORTMANAGEMENT_UPLOAD_FREE_SPACE.code);
			errorMessages.add(RESULT.REPORTMANAGEMENT_UPLOAD_FREE_SPACE.message);

			return null;
		}

		//4. 保存文件
		String filePath = directoryPath + reportName;
		File uploadFile = new File(filePath);
		try {
			byte[] reportInfo = reportFile.getBytes();
			reportFile.transferTo(uploadFile);

			redisPublishReport(reportInfo, reportName);

		} catch (IOException e) {
			errorCodes.add(RESULT.REPORTMANAGEMENT_UPLOAD_SAVE_ERROR.code);
			errorMessages.add(RESULT.REPORTMANAGEMENT_UPLOAD_SAVE_ERROR.message);

			log.error("业务抛错：", e);
			return null;
		}

		return filePath;
	}

	/**
	 * 删除文件
	 * @author 秦超
	 * @param reportName
	 * @return
	 */
	private boolean deleteReport(String reportName){

		String filePath = reportWebroot + ConfigInfo.MENU_FILE_REPORT_PATH;
		File uploadFile = new File(filePath);

		return uploadFile.delete();
	}

	/**
	 * 通过Redis发布报表文件
	 * @param reportInfo 文件信息
	 * @param reportName 文件名
	 * @return
	 * @author 秦超
	 * 2017-09-26 18:43:09
	 * @throws IOException
	 */
	private boolean redisPublishReport(byte[] reportInfo, String reportName){
//		todo 推送至其他服务器
//		Jedis publishJedis = jedisPool.getResource();
//
//		publishJedis.set(reportName.getBytes(), reportInfo);
//		publishJedis.expire(reportName.getBytes(), ConfigInfo.REDIS_REPORT_FILE_EXPIRE);
//
//		publishJedis.publish(ConfigInfo.REDIS_REPORT_FILE_CHANNEL_NAME, reportName);

		return true;
	}

	/**
	 * 获取报表文件列表
	 * @author 秦超
	 * @param currentPage
	 * @return
	 */
	@Override
	@OperationLogAspect(operationName = "查看报表列表", operationType = "2")
	public Map<String, Object> getReportList(int currentPage) {
		//1. 初始化查询结果Map
		Map<String, Object> resultMap = new HashMap<>();
		//2.1 获取sql查询起始条数
		int numberOnePage = ConfigInfo.NUMBER_ONE_PAGE;
		int startNumber = PageNumberTransfermation.pageNumberToSelectStartNumber(currentPage, numberOnePage);

		//3. 获取报表列表查询结果及条数
		try {
			int reportTotalCount = iReportManagementDao.getReportTotalCount();
			List<Report> reportList = iReportManagementDao.getReportList(startNumber, numberOnePage);

			resultMap.put("dataList", reportList);
			resultMap.put("dataCount", reportTotalCount);
		} catch (Exception e) {
			errorCodes.add(RESULT.REPORTMANAGEMENT_REPORT_LIST_ERROR.code);
			errorMessages.add(RESULT.REPORTMANAGEMENT_REPORT_LIST_ERROR.message);

			log.error("业务抛错：", e);
			return null;
		}

		return resultMap;
	}

	/**
	 * 获取所有的报表
	 *
	 * @return
	 */
	@Override
	public List<Report> getAllReportList() {
		if (reportWebroot.equals("empty")){
			return null;
		}
		return iReportManagementDao.getAllReportList();
	}

}
