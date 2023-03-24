package com.penghaisoft.framework.constant;

import java.util.*;

/**
 * 常量定义
 *
 */
public class Constant {

	/**
	 * 任务类型
	 * 地址转换中出入库口的定义也根据这个类型来
	 */
	public enum TaskType{
//		QXF
		PRODUCT_IN(10, "生产入库", "expose/report/inwarehouse/task"),
//		ABNORMAL_IN(14, "异常口入库", "expose/report/inwarehouse/task"),
		VIRTUAL_PALLET_IN(15,"虚拟托盘入库", "expose/report/inwarehouse/task"),
		HAND_IN(16,"人工入库", "expose/report/inwarehouse/task"),
		STRAIGHT_OUT(20, "直发出库", "expose/report/outwarehouse/straight"),
		VIRTUAL_PALLET_OUT(25,"虚拟托盘出库", "expose/report/outwarehouse/virtualpallet"),
		HAND_OUT(26, "人工出库", "expose/report/outwarehouse/straight"),
		NORMAL_MOVE(30,"普通移库", "expose/report/move/pallet"),
		//NH
		NH_PRODUCT_IN(10, "生产入库", "nuohua/report/inwarehouse/task"),
		NH_PRODUCT_IN_YCL(50, "原材料入库", "nuohua/report/inwarehouse/task"),
//		--------------------下面的不会用-----------
		SORT_IN(11, "分拣入库", "expose/report/inwarehouse/task"),
		CHECK_IN(12, "盘点入库", "expose/report/inwarehouse/task"),
		PACKING_IN(13, "包材入库", "expose/report/inwarehouse/task"),
		SORT_OUT(21, "分拣出库", "expose/report/outwarehouse/sorting"),
		CHECK_OUT(22, "盘点出库", "expose/report/outwarehouse/check"),
		PACKING_OUT(23,"包材出库", ""),
		ABNORMAL_OUT(24, "异常出库", ""),
		OUT_MOVE(31,"出库移库", "expose/report/outwarehouse/move/pallet"),
		PRODUCT_CROSS(40,"生产越库", "expose/report/cross/pallet");

		private final long taskType;
		private final String taskTypeDesc;
		private final String postWmsUrl;

		TaskType( int taskType , String taskTypeDesc, String postWmsUrl ){
			this.taskType = taskType;
			this.taskTypeDesc = taskTypeDesc;
			this.postWmsUrl = postWmsUrl;
		}

		public long getTaskType() {
			return taskType;
		}

		public String getTaskTypeDesc() {
			return taskTypeDesc;
		}

		public String getPostWmsUrl() {
			return postWmsUrl;
		}

		public static String getTaskTypeDesc(long taskType) {
			TaskType[] taskTypes = values();
			for (TaskType tmp : taskTypes) {
				if (tmp.getTaskType() == taskType) {
					return tmp.getTaskTypeDesc();
				}
			}
			return null;
		}

		public static String getPostWmsUrl(long taskType) {
			TaskType[] taskTypes = values();
			for (TaskType tmp : taskTypes) {
				if (tmp.getTaskType() == taskType) {
					return tmp.getPostWmsUrl();
				}
			}
			return null;
		}

		public static Map<String, String> getAllTaskType() {
			TaskType[] taskTypes = values();
			Map<String, String> map = new HashMap<String, String>();
			for (TaskType tmp : taskTypes) {
				map.put(String.valueOf(tmp.getTaskType()), tmp.getTaskTypeDesc());
			}
			return map;
		}
	}

	/**
	  * @Description 上线工厂适用业务service
	  * @Author luot
	  * @Date 2020/3/20 20:59
	  * @Param
	  * @return
	  **/
	public enum RecommendLocationEnum{
		SHENLI("SHENLI", "differentBusinessSLService"),
		BAOBO("BAOBO", "differentBusinessBaoboDemoService"),
		YHLY("YHLY", "differentBusinessYHLYService"),
		NH("NH","differentBusinessNHService"),
		QXF("QXF", "differentBusinessQXFService");



		private final String factoryCode;
		private final String serviceName;

		RecommendLocationEnum( String factoryCode , String serviceName ){
			this.factoryCode = factoryCode ;
			this.serviceName = serviceName ;
		}

		public String getFactoryCode() {
			return factoryCode;
		}

		public String getServiceName() {
			return serviceName;
		}

		public static String getServiceNameByCode(String factoryCode) {
			RecommendLocationEnum[] recommendLocationEnums = values();
			for (RecommendLocationEnum tmp : recommendLocationEnums) {
				if (tmp.getFactoryCode().equals(factoryCode)) {
					return tmp.getServiceName();
				}
			}
			return null;
		}
	}


	/**
	 * 错误码、错误信息
	 */
	public enum RESULT {
		
		SUCCESS("0", "成功"),
		FAILED("1", "失败"),
		 
		//用户管理错误信息
		USERMANAGEMENT_USER_NOT_EXIST("0101", "用户名不存在"),
		USERMANAGEMENT_USER_EXIST("0102", "用户名已存在"),
		USERMANAGEMENT_PARAMETER("0103", "输入参数错误"),
		USERMANAGEMENT_USER_PASSWORD("0104", "用户名或密码错误"),
		USERMANAGEMENT_VERIFICATION_CODE("0105", "输入验证码错误"),
		USERMANAGEMENT_SESSION_OPERATION_ERROR("0106", "SESSION操作失败"),
		USERMANAGEMENT_GET_USER_INFORMATION("0107", "获取用户信息失败"),
		USERMANAGEMENT_GET_USER_LIST_FAILED("0108", "获取用户列表失败"),	
		USERMANAGEMENT_UPDATE_USER_FAILED("0109", "修改用户信息失败"),	
		USERMANAGEMENT_ADD_USER_FAILED("0110", "添加用户失败"),
		USERMANAGEMENT_DELETE_USER_FAILED("0111", "删除用户失败"),	
		USERMANAGEMENT_USER_ALREADY_ENABLED("0112", "用户已经启用"),
		USERMANAGEMENT_ENABLE_USER_FAILED("0113", "启用用户失败"),
		USERMANAGEMENT_USER_ALREADY_DISABLED("0114", "用户已经停用"),
		USERMANAGEMENT_DISABLE_USER_FAILED("0115", "停用用户失败"),
		USERMANAGEMENT_CHANGE_PASSWORD_FAILED("0116", "修改密码错误"),
		USERMANAGEMENT_RESET_PASSWORD_FAILED("0117", "修改密码错误"),
		USERMANAGEMENT_CURRENT_NOT_EXIST("0118", "用户未登录"),
		
		//构造User实体错误信息
		USERMANAGEMENT_USER_CONSTRUCTOR_ID("0120", "ID参数错误"),
		USERMANAGEMENT_USER_CONSTRUCTOR_USERNAME("0121", "用户名参数错误"),
		USERMANAGEMENT_USER_CONSTRUCTOR_PASSWORD("0122", "密码参数错误"),
		USERMANAGEMENT_USER_CONSTRUCTOR_NICKNAME("0123", "名称参数错误"),
		USERMANAGEMENT_USER_CONSTRUCTOR_EMAIL("0124", "邮箱参数错误"),
		USERMANAGEMENT_USER_CONSTRUCTOR_PHONE("0125", "手机号参数错误"),
		USERMANAGEMENT_USER_CONSTRUCTOR_STATUS("0126", "用户状态参数错误"),
		
		//日志模块构造方法参数错误
		LOGMANAGEMENT_PARAMETER_ERROR("0201","参数错误"),
		//构造登录日志实体错误信息
		LOGMANAGEMENT_LOGIN_LOG_USER_ID_ERROR("0210", "用户id参数错误"),
		LOGMANAGEMENT_LOGIN_LOG_USER_ACCOUNT_ERROR("0211", "用户账户参数错误"),
		LOGMANAGEMENT_LOGIN_LOG_USER_NAME_ERROR("0212", "用户姓名参数错误"),
		LOGMANAGEMENT_LOGIN_LOG_LOGIN_IP_ERROR("0213", "登录ip参数错误"),
		LOGMANAGEMENT_LOGIN_LOG_LOGIN_TIME_ERROR("0214", "登录时间参数错误"),
		
		//构造操作日志实体错误信息
		LOGMANAGEMENT_OPERATION_LOG_USER_ID_ERROR("0220", "用户id参数错误"),
		LOGMANAGEMENT_OPERATION_LOG_USER_ACCOUNT_ERROR("0221", "用户账户参数错误"),
		LOGMANAGEMENT_OPERATION_LOG_USER_NAME_ERROR("0222", "用户姓名参数错误"),
		LOGMANAGEMENT_OPERATION_LOG_OPERATION_NAME_ERROR("0223", "操作项名称参数错误"),
		LOGMANAGEMENT_OPERATION_LOG_OPERATION_TIME_ERROR("0224", "操作时间参数错误"),
		
		//权限管理--权限模块返回信息
		//错误信息
		PERMISSIONMANAGEMENT_PERMISSION_PERMISSION_PARAMETER_ERROR("0301","权限参数错误"),
		PERMISSIONMANAGEMENT_PERMISSION_PERMISSION_NAME_NULL_ERROR("0302","权限名称为空"),
		PERMISSIONMANAGEMENT_PERMISSION_PERMISSION_DESCRIPTION_NULL_ERROR("0303","权限描述为空"),
		PERMISSIONMANAGEMENT_PERMISSION_PERMISSION_NAME_NOT_ALL_LETTER_ERROR("0304","权限名称包含非英文字符"),
		PERMISSIONMANAGEMENT_PERMISSION_PERMISSION_CREATETIME_NULL_ERROR("0305","权限创建时间为空"),
		PERMISSIONMANAGEMENT_PERMISSION_PERMISSION_NAME_DUPLICATE("0306","权限名称重复"),
		PERMISSIONMANAGEMENT_PERMISSION_PERMISSION_DELETE_FAILED("0307","权限删除失败,请检查该权限是否被某角色使用,或该权限是否存在"),
		PERMISSIONMANAGEMENT_PERMISSION_PERMISSION_EDIT_FAILED("0308","编辑权限失败,请检查该权限是否存在"),
		PERMISSIONMANAGEMENT_PERMISSION_PERMISSION_LIST_NULL("0309","权限列表为空,请检查分页参数"),
		//成功提示
		PERMISSIONMANAGEMENT_PERMISSION_PERMISSION_NAME_AVAILABLE("0310","该权限名称可用"),
		//权限管理--角色模块返回信息
		//错误信息
		PERMISSIONMANAGEMENT_ROLE_ROLE_PARAMETER_ERROR("0311","角色参数错误"),
		PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_NULL_ERROR("0312","角色名称为空"),
		PERMISSIONMANAGEMENT_ROLE_ROLE_DESCRIPTION_NULL_ERROR("0313","角色描述为空"),
		PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_NOT_ALL_LETTER_ERROR("0314","角色名称包含非英文字符"),
		PERMISSIONMANAGEMENT_ROLE_ROLE_CREATETIME_NULL_ERROR("0315","角色创建时间为空"),
		PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_DUPLICATE("0316","角色名称重复"),
		PERMISSIONMANAGEMENT_ROLE_ROLE_EDIT_FAILED("0317","编辑角色失败,请检查该角色是否存在"),
		PERMISSIONMANAGEMENT_ROLE_ROLE_DELETE_FAILED("0318","角色删除失败,请检查是否有用户使用了该权限或检查该角色是否存在"),
		PERMISSIONMANAGEMENT_ROLE_ROLE_LIST_NULL("0319","角色列表为空,请检查是否添加了角色"),
		PERMISSIONMANAGEMENT_ROLE_ROLE_SAVE_ERROR("0320","角色与权限关系保存失败,请检查要添加的权限是否存在"),
		//成功提示
		PERMISSIONMANAGEMENT_ROLE_ROLE_NAME_AVAILABLE("0321","该角色名称可用"),
		
		//权限管理--用户角色模块返回信息
		PERMISSIONMANAGEMENT_USERROLE_ROLE_LIST_NULL("0331","角色列表为空,请检查是否为该用户添加了角色"),
		PERMISSIONMANAGEMENT_USERROLE_ROLE_SAVE_ERROR("0332","用户与角色关系保存失败,请检查要添加的角色是否存在"),

		//权限管理--权限分组返回信息
        PERMISSIONMANAGEMENT_GROUP_NAME_EMPTY("0333","权限分组名称为空"),
        PERMISSIONMANAGEMENT_GROUP_ID_EMPTY("0334","权限ID为空"),

		//菜单管理错误信息
		MENUMANAGEMENT_PARAMETER_ERROR("0401","菜单参数错误"),
		MENUMANAGEMENT_MENU_FILE_EMPTY("0402","资源文件为空"),
		MENUMANAGEMENT_MENU_TABLE_EMPTY("0403","菜单数据为空"),
		MENUMANAGEMENT_MENU_ADD_NOT_THIS_PARENT("0404","当前父级菜单不存在"),
		MENUMANAGEMENT_MENU_DELETE_HAS_CHILDREN("0405","当前菜单拥有子菜单，不可删除"),
		MENUMANAGEMENT_MENU_DELETE_NOT_EXIST("0406","当前菜单不存在"),
		MENUMANAGEMENT_MENU_UPDATE_NOT_EXIST("0407","当前菜单不存在"),
		MENUMANAGEMENT_MENU_SQL_CONNECTION_ERROR("0408","数据库连接异常"),
		MENUMANAGEMENT_MENU_JSON_FORMAT_ERROR("0409","菜单信息数据格式错误"),
		MENUMANAGEMENT_MENU_CHILD_URL_EMPTY("0410","子菜单路径不可为空"),
		MENUMANAGEMENT_MENU_ICON_EMPTY("0431","菜单图标文件为空"),

		//菜单实体--构造参数错误
		MENUMANAGEMENT_MENU_ID_ERROR("0411","菜单id格式错误"),
		MENUMANAGEMENT_MENU_PARENTID_ERROR("0412","父级菜单id格式错误"),
		MENUMANAGEMENT_MENU_LEVEL_ERROR("0413","菜单级别格式错误"),
		MENUMANAGEMENT_MENU_SEQUENCE_ERROR("0414","菜单排序码格式错误"),
		MENUMANAGEMENT_MENU_TITLE_ERROR("0415","菜单名不可为空"),
		MENUMANAGEMENT_MENU_LEVEL_FIRST_ERROR("0416","一级菜单级别格式错误"),
		MENUMANAGEMENT_MENU_EMPTY("0417","资源不存在"),
		
		//菜单角色管理错误信息
		MENUMANAGEMENT_MENU_ROLE_EMPTY("0421","角色列表为空"),
		MENUMANAGEMENT_MENU_ROLE_JSON_FORMAT_ERROR("0422","菜单角色关联信息数据格式错误"),
		MENUMANAGEMENT_MENU_ROLE_UPDATE_ERROR("0423","菜单角色关联信息更新错误"),
		
		REPORTMANAGEMENT_UPLOAD_FILE_EMPTY("0501","上传文件不可为空"),
		REPORTMANAGEMENT_UPLOAD_FORMAT("0502","上传文件格式不正确"),
		REPORTMANAGEMENT_UPLOAD_FILESIZE("0503","文件大小限制在10M以内"),
		REPORTMANAGEMENT_UPLOAD_INSERT("0504","报表文件数据保存失败"),
		REPORTMANAGEMENT_UPLOAD_FILE_EXIST("0505","文件名已存在"),
		REPORTMANAGEMENT_UPLOAD_FREE_SPACE("0506","磁盘剩余空间不足"),
		REPORTMANAGEMENT_UPLOAD_SAVE_ERROR("0507","报表文件保存失败"),
		REPORTMANAGEMENT_REPORT_LIST_ERROR("0508","获取报表信息列表失败"),
		REPORTMANAGEMENT_UPLOAD_FILE_ERROR("0509","当前系统未开启报表"),
		//权限控制错误信息
		AUTHORIZATION_ERROR("0601","对不起，您没有使用该功能的权限！"),

        PARAMETER_IS_EMPTY("0001","参数为空"),

        POST_ADD_POST_NAME_EMPTY("0001","新增岗位名称为空"),
        POST_ADD_POST_CODE_EMPTY("0001","新增岗位编码为空"),
        POST_ADD_DEPARTMENT_ID_EMPTY("0001","新增岗位部门ID为空"),
        POST_ADD_DATABASE_ERROR("0003","新增岗位异常"),

        POST_POST_CODE_NOT_AVAILABLE("010201","岗位编码已存在"),

        POST_EDIT_POST_ID_EMPTY("0001","编辑岗位ID为空"),
        POST_EDIT_POST_NAME_EMPTY("0001","编辑岗位名称为空"),
        POST_EDIT_POST_CODE_EMPTY("0001","编辑岗位编码为空"),
        POST_EDIT_DEPARTMENT_ID_EMPTY("0001","编辑岗位部门ID为空"),
        POST_EDIT_DATABASE_ERROR("0003","编辑岗位异常"),

        POST_ASSIGNED_EMPLOYEE_ERROR("0003","为岗位指派用户异常"),

        DEPARTMENT_CODE_EXIST("010101","部门编码已存在"),
        DEPARTMENT_TYPE_NAME_EXIST("010102","部门属性名称已存在"),
        DEPARTMENT_HAS_CHILDREN("010103","该部门存在下属部门"),
        DEPARTMENT_HAS_POST("010104","该部门存在岗位"),
        EMPLOYEE_CODE_NAME_EXIST("010301","员工号已存在"),

        DEPARTMENT_PARENT_ID_EMPTY("010105","上级部门id为空"),
        DEPARTMENT_CODE_EMPTY("010106","部门编码为空"),
        DEPARTMENT_NAME_EMPTY("010107","部门名称为空"),
        DEPARTMENT_TYPE_EMPTY("010108","部门属性为空"),

        DATA_FORMAT_ERROR("0002","参数格式错误"),
        DATABASE_ERROR("0003","数据源连接异常"),

		PALLET_CODE_ERROR("000101", "托盘条码不存在");
		
	    public String code;
	    public String message;
	      
	    RESULT( String code , String message ){
	        this.code = code ;
	        this.message = message ;
	    }    
	    
	}

	/**
	  * @Description 立库相关信息
	  * @Author luot
	  * @Date 2020/7/21 19:05
	  * @Param
	  * @return
	  **/
	public class StereoscopicInfo {
		//3个缓存位
		public static final String  YIKU_WEI_1="0701";
		public static final String  YIKU_WEI_2="0801";
		public static final String  YIKU_WEI_3="0901";

//		一楼发货口地址
		public static final String FLOOR_ONE_OUT_TO_ADDRESS = "2001";
		//public static final String FLOOR_ONE_OUT_ADDRESS = "2002";
		//成品出库，agv搬货的目的地
		public static final String FLOOR_ONE_OUT_TO_AGV_1 = "P1_1";
		public static final String FLOOR_ONE_OUT_TO_AGV_2 = "P1_2";


//		一楼托盘出库口地址
		public static final String FLOOR_ONE_PALLET_OUT_ADDRESS = "3001";

//		虚拟托盘商品码
		public static final String VIRTUAL_PALLET_GOODS_CODE = "pallet";

//		立库只启动一楼入库
		public static final boolean ONLY_FLOOR_ONE_START = false;

//		虚拟托盘多少行
		public static final int PALLET_MAX_ROW_NUM = 5;

//		一楼车数量
		public static final double CAR_NUM_FLOOR_ONE = 2;

//		二楼车数量
		public static final double CAR_NUM_FLOOR_TWO = 2;

//		三楼车数量
		public static final double CAR_NUM_FLOOR_THR = 1;

//		四楼车数量
		public static final double CAR_NUM_FLOOR_FOU = 1;
	}
	
	/**
	 * 配置常量
	 */
	public class ConfigInfo {
        public static final String SESSION_KEY_USER = "userInfo";

		public static final String SESSION_LAST_MSG_TIME = "lastMsgTime";

        private ConfigInfo(){}
		//初始密码
		public static final String INITIAL_PASSWORD = "12345678";
		
		//用户状态
		public static final int USER_STATUS_ENABLED = 0;
		public static final int USER_STATUS_DISABLED = 1;
		
		//session存储登录用户名的key值
		public static final String SESSION_KEY_LOGINNAME = "loginName";
		//session存储登录IP的key值
		public static final String SESSION_KEY_LOGINIP = "loginIP";
		//session存储授权url的key值
		public static final String SESSION_KEY_AUTHORIZED_URLS = "authorizedUrls";
		
		//分页显示条数
		public static final int NUMBER_ONE_PAGE = 20;
		//时间存储格式
		public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
		
		//定义ConvertFactory类中JSON字符串
		public static final String FORMAT_JSON = "JSON";
		//定义ConvertFactory类中JSON字符串
		public static final String FORMAT_XML = "XML";	
		
		//接口返回信息键值
		public static final String CODE_KEY = "code";//返回结果码
		public static final String MESSAGE_KEY = "message";//返回结果信息
		public static final String DATA_KEY = "data";//返回结果数据
		public static final String DATA_LIST_KEY = "dataList";//返回结果列表
		public static final String DATA_COUNT_KEY = "dataCount";//返回结果条数
		
		//菜单资源文件根目录
//		public static final String MENU_FILE_ROOT_PATH = "../../";
//		public static final String MENU_FILE_ROOT_PATH = "./";
		public static final String MENU_URL_SPLIT = "/";
		public static final String MENU_CPT_URL_PREFIX = "ReportServer?reportlet=";
		public static final String MENU_FRM_URL_PREFIX = "ReportServer?formlet=";
		public static final String MENU_HTML_URL_PREFIX = "menu/load/";

		public static final String MENU_ICON_PATH = "/framework/images/menuIcon/";
//		public static final String MENU_ICON_REAL_PATH = "../../framework/images/menuIcon/";
		public static final String MENU_ICON_REAL_PATH = "./static/framework/images/menuIcon/";

		//报表文件目录
		public static final String MENU_FILE_REPORT_PATH = "WEB-INF/reportlets/";
		//文件格式信息
		public static final String MENU_FILE_FORMAT_JSP = ".jsp";
		public static final String MENU_FILE_FORMAT_HTML = ".html";
		public static final String MENU_FILE_FORMAT_CPT = ".cpt";
		public static final String MENU_FILE_FORMAT_FRM = ".frm";
		
		public static final int REDIS_REPORT_FILE_EXPIRE = 300;
		public static final String REDIS_REPORT_FILE_CHANNEL_NAME = "reportChannel";
		public static final String REDIS_REPORT_FILE = "reportFile";
		public static final String REDIS_REPORT_FILE_NAME = "reportName";
		
		//登录页面url
		public static final String LOGIN_URL = "framework/login.html";
		//首页url
		public static final String INDEX_URL = "framework/index.html";
		//账户信息页面url
		public static final String USER_INFO_LIST_URL = "framework/module/accountManagement/userInfoList.html";
		//账户信息修改页面url
		public static final String MODIFY_PERSONAL_INFORMATION_URL = "framework/module/accountManagement/modifyPersonalInformation.html";
		//账户密码修改页面url
		public static final String MODIFY_PERSONAL_PASSWORD_URL = "framework/module/accountManagement/modifyPersonalPassword.html";
		//登录日志信息页面url
		public static final String LOGIN_LOG_URL = "framework/module/logManagement/loginLog.html";
		//操作日志信息页面url
		public static final String OPERATION_LOG_URL = "framework/module/logManagement/operationLog.html";
		//菜单管理页面url
		public static final String EDIT_MENU_LIST_URL = "framework/module/menuManagement/editMenuList.html";
		//用户角色配置页面url
		public static final String CONFIGURING_USER_ROLES_URL = "framework/module/privilegeManagement/configuringUserRoles.html";
		//菜单权限配置页面url
		public static final String MENU_AUTHORITY_SETTING_URL = "framework/module/privilegeManagement/menuAuthoritySettings.html";
		//菜单岗位权限配置页面url
		public static final String MENU_POST_AUTHORITY_SETTING_URL = "framework/module/basicInformation/menuPostAuthoritySettings.html";
		//权限管理页面url
		public static final String PRIVILEGE_MANAGEMENT_URL = "framework/module/privilegeManagement/priviliegeManagement.html";
		//角色管理页面url
		public static final String ROLE_MANAGEMENT_URL = "framework/module/privilegeManagement/roleManagement.html";
		//报表管理页面url
		public static final String REPORTS_MANAGEMENT_URL = "framework/module/reportsManagement/reportsManagement.html";
		//部门管理页面url
		public static final String BASICS_DEPARTMENT_MANAGEMENT_URL = "framework/module/basicInformation/manageDepartment.html";
		//岗位管理页面url
		public static final String BASICS_POST_MANAGEMENT_URL = "framework/module/basicInformation/postManagement.html";
		//员工管理页面url
		public static final String BASICS_EMPLOYEE_MANAGEMENT_URL = "framework/module/basicInformation/managePerson.html";
		//任务信息url
		public static final String JOB_EXECUTION_LOG_URL = "framework/module/jobManagement/executionLog.html";
		//任务执行日志明细url
		public static final String JOB_EXECUTION_DETAIL_LOG_URL = "framework/module/jobManagement/executionDetialLog.html";
		//文件服务-文件上传url
		public static final String FILE_SERVICE_FILE_UPLOAD = "framework/module/fileService/fileUpload.html";
		//文件服务-文件管理url
		public static final String FILE_SERVICE_FILE_MANAGER = "framework/module/fileService/fileManager.html";

		//序列号前缀
		public static final String SEQ_PREFIX = "wms:seq:";
		//序列号前缀短
		public static final String SEQ_PRO_SHORT_PREFIX = "wms:seq:short:";
        //任务序列号前缀
        public static final String SEQ_PREFIX_TASK = "wms:seq:task:";

        //redis存wcs接口调用统计
		public static final String INTERFACE_FOR_WCS_REDIS = "wms:interface:pallet:";
		//redis存wcs接口调用统计【失效时间】
		public static final int INTERFACE_FOR_WCS_REDIS_LOST_MINUTES = 1;

//		深库位标志
		public static final String DEEP_LOCATION_FLAG = "2";
//		浅库位标志
		public static final String SHALLOW_LOCATION_FLAG = "1";
	}
	
	/**
	 * 数据转换类需要的常量配置
	 * @author 徐超
	 * @Date 2017年9月23日 下午8:12:28
	 */
	public class DataTransferConstant{
		
		private DataTransferConstant(){}
		/*
		 * 将权限组件返回的角色列表转换为角色Map列表方法所使用map的键
		 */
		//角色ID
		public static final String TRANSFER_ROLELIST_TO_MAP_ROLEID = "id";
		//角色名称
		public static final String TRANSFER_ROLELIST_TO_MAP_ROLENAME = "roleName";
		//角色描述
		public static final String TRANSFER_ROLELIST_TO_MAP_ROLEDESCRIPTION = "roleDescription";
		//权限ID列表
		public static final String TRANSFER_ROLELIST_TO_MAP_PERMISSIONIDS = "permissionIds";
		
		/*
		 * 将权限组件返回的权限列表装换为权限Map列表方法所使用map的键
		 */
		//权限ID
		public static final String TRANSFER_PERMISSIONLIST_TO_MAP_PERMISSIONID = "permissionId";
		//权限名称
		public static final String TRANSFER_PERMISSIONLIST_TO_MAP_PERMISSIONNAME = "permissionName";
		//权限描述
		public static final String TRANSFER_PERMISSIONLIST_TO_MAP_PERMISSIONDESCRIPTION = "permissionDescription";

		/*
		 * 将用户列表、用户总数转换为Map所使用map的键
		 */
		//用户总数
		public static final String TRANSFER_PERMISSIONLIST_TO_MAP_USERTOTALCOUNT = "userTotalCount";
		//用户列表
		public static final String TRANSFER_PERMISSIONLIST_TO_MAP_USERLIST = "userList";
		
	}

}