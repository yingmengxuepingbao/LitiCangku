package com.penghaisoft.framework.datatransfer;

import com.penghaisoft.framework.constant.Constant.DataTransferConstant;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据转换类
 * @author 徐超
 * @Date 2017年9月23日 下午8:00:42
 */
public class DataTransfer {

	//私有构造函数,禁止该对象被实例化
	private DataTransfer(){
		
	}

	
	/**
	 * 将用户列表、用户总数转换为Map
	 * @author 刘立华
	 * @Date 2017年9月26日
	 * @param users
	 * @param totalCount
	 * @return
	 */
	public static Map<String, Object> transferUserListToMap(List<User> users, Integer totalCount){
		
		//用于保存转换结果
		Map<String, Object> userMap = new HashMap<>();
		userMap.put(DataTransferConstant.TRANSFER_PERMISSIONLIST_TO_MAP_USERLIST, users);
		userMap.put(DataTransferConstant.TRANSFER_PERMISSIONLIST_TO_MAP_USERTOTALCOUNT, totalCount);
		return userMap;
	}

}
