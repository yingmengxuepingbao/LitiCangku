package com.penghaisoft.wcs.taskmanagement.model.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import com.penghaisoft.framework.util.BaseService;
import com.penghaisoft.framework.util.Pager;
import com.penghaisoft.framework.util.Resp;
import com.penghaisoft.wcs.basicmanagement.model.business.IWcsAddressService;
import com.penghaisoft.wcs.basicmanagement.model.entity.WcsAddress;
import com.penghaisoft.wcs.operation.model.AgvPositionItem;
import com.penghaisoft.wcs.operation.model.AgvTask;
import com.penghaisoft.wcs.operation.service.AgvOperationService;
import com.penghaisoft.wcs.taskmanagement.model.business.IWcsManualAgvTaskService;
import com.penghaisoft.wcs.taskmanagement.model.dao.WcsManualAgvTaskMapper;
import com.penghaisoft.wcs.taskmanagement.model.entity.WcsManualAgvTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Description: AGV手动控制管理
 * @Author: jzh
 * @Date: 2020/7/16
 */ 
@Service("wcsManualAgvTaskService")
public class WcsManualAgvTaskServiceImpl extends BaseService implements IWcsManualAgvTaskService {
		@Resource
		private WcsManualAgvTaskMapper wcsManualAgvTaskMapper;
		@Autowired
		private IWcsAddressService wcsAddressService;
		@Autowired
		private AgvOperationService agvOperationService;


	/**
	   * 新增记录
	   * @return
	   */
		@Override
		public Resp create(WcsManualAgvTask wcsManualAgvTask){
			//编码重复校验 todo
			wcsManualAgvTaskMapper.insertBySelect(wcsManualAgvTask);
			return success();
		}

	  /**
	   * 删除记录
	   * @return
	   */
		@Override
		public Resp delete(WcsManualAgvTask wcsManualAgvTask){
			List<WcsManualAgvTask> list = wcsManualAgvTaskMapper.queryByAny(wcsManualAgvTask);
			if (list.size()>0){
				String taskStatus = list.get(0).getTaskStatus();
				if (!"1".equals(taskStatus)){
					return fail("任务状态不为创建不可删除");
				}else {
					wcsManualAgvTaskMapper.delete(wcsManualAgvTask);
				}
			}else {
				return fail("查询不到该任务信息");
			}
			return success();
		}
		
	  /**
	   * 查询列表
	   * @return
	   */
		@Override
		public Pager<WcsManualAgvTask> findListByCondition(int page, int rows,WcsManualAgvTask condition){
		    Pager<WcsManualAgvTask> pager = new Pager<>();
		    pager.setPage(page);
		    pager.setRows(rows);
			List<WcsManualAgvTask> records = wcsManualAgvTaskMapper.queryList(pager,condition);
			long size = wcsManualAgvTaskMapper.queryCount(condition);
			pager.setRecords(records);
			pager.setTotalCount(size);
			return pager;
		}

	  /**
	   * 查询单条
	   * @return
	   */
		@Override
		public WcsManualAgvTask findById(String id){
			return wcsManualAgvTaskMapper.queryById(id);
		}
		
	  /**
	   * 修改记录
	   * @return
	   */
		@Override
		public Resp update(WcsManualAgvTask wcsManualAgvTask){
			wcsManualAgvTaskMapper.updateBySelect(wcsManualAgvTask);
			return success();
		}
		/**
		 * @Description: 手动AGV任务下发
		 * @Author: jzh
		 * @Date: 2020/7/16
		 */ 
		@Override
		public Resp startTask(WcsManualAgvTask wcsManualAgvTask, String loginName) {
			//判断线发条件
			List<WcsManualAgvTask> list = wcsManualAgvTaskMapper.queryByAny(wcsManualAgvTask);
			if (list.size()>0){
				WcsManualAgvTask manualAgv = list.get(0);
				String taskStatus = manualAgv.getTaskStatus();
				if ("1".equals(taskStatus)||"21".equals(taskStatus)){
					//符合条件 调用接口拼装参数

					AgvTask agvTask = new AgvTask();
					agvTask.setReqCode(UUID.randomUUID().toString().replace("-",""));
					agvTask.setTaskCode(manualAgv.getTaskNo());
					agvTask.setTaskTyp(manualAgv.getAgvTaskType());
					//根据规则生成位置编号数据
					//起始地
					List<AgvPositionItem> agvPositionItemList = new ArrayList<>();
					Integer fromAddressId = manualAgv.getFromAddressId();
					WcsAddress fromAddress = wcsAddressService.findById(String.valueOf(fromAddressId));
					AgvPositionItem itemFrom = new AgvPositionItem();
					itemFrom.setPositionCode(fromAddress.getUserDefined1());
					itemFrom.setType(fromAddress.getUserDefined2());
					agvPositionItemList.add(itemFrom);

					//目的地
					Integer toAddressId = manualAgv.getToAddressId();
					WcsAddress toAddress = wcsAddressService.findById(String.valueOf(toAddressId));
					AgvPositionItem itemTo = new AgvPositionItem();
					itemTo.setPositionCode(toAddress.getUserDefined1());
					itemTo.setType(toAddress.getUserDefined2());
					agvPositionItemList.add(itemTo);
					//放置任务
					agvTask.setPositionCodePath(agvPositionItemList);
					//调用AGV任务下发接口
					Resp resp = agvOperationService.sendTask(agvTask);
					String respCode = resp.getCode();
					//2-1下发成功更新状态
					if ("1".equals(respCode)){//调用接口成功
						//修改状态 2下发
						wcsManualAgvTask.setTaskStatus("2");
						wcsManualAgvTask.setLastModifiedBy(loginName);
						wcsManualAgvTask.setGmtModified(new Date());
						Integer upAmount = wcsManualAgvTaskMapper.updateBySelect(wcsManualAgvTask);

//        				2-2下发失败记录
					} else {
						// 21下发失败
						wcsManualAgvTask.setTaskStatus("21");
						wcsManualAgvTask.setLastModifiedBy(loginName);
						wcsManualAgvTask.setGmtModified(new Date());
						Integer upAmount = wcsManualAgvTaskMapper.updateBySelect(wcsManualAgvTask);
						return fail("下发失败:"+resp.getMsg());

					}

				}else {
					return fail("该任务状态不符合下发条件");
				}
			}else {
				return fail("查询不到该任务信息");
			}
			return success();
		}

}
