package com.penghaisoft.pda.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量定义
 */
public class Constant {

    /**
     * 任务类型
     * 地址转换中出入库口的定义也根据这个类型来
     */
    public enum TaskType {
        PRODUCT_IN(10, "生产入库"),
        SORT_IN(11, "分拣入库"),
        CHECK_IN(12, "盘点入库"),
        PACKING_IN(13, "包材入库"),
        ABNORMAL_IN(14, "异常口入库"),
        VIRTUAL_PALLET_IN(15, "虚拟托盘入库"),
        HAND_IN(16, "人工入库"),
        STRAIGHT_OUT(20, "直发出库"),
        SORT_OUT(21, "分拣出库"),
        CHECK_OUT(22, "盘点出库"),
        PACKING_OUT(23, "包材出库"),
        ABNORMAL_OUT(24, "异常出库"),
        VIRTUAL_PALLET_OUT(25, "虚拟托盘出库"),
        HAND_OUT(26, "人工出库"),
        NORMAL_MOVE(30, "普通移库"),
        OUT_MOVE(31, "出库移库"),
        PRODUCT_CROSS(40, "生产越库");

        private final long taskType;
        private final String taskTypeDesc;

        TaskType(int taskType, String taskTypeDesc) {
            this.taskType = taskType;
            this.taskTypeDesc = taskTypeDesc;
        }

        public long getTaskType() {
            return taskType;
        }

        public String getTaskTypeDesc() {
            return taskTypeDesc;
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
     * @Description 立库相关信息
     * @Author luot
     * @Date 2020/7/21 19:05
     * @Param
     * @return
     **/
    public class StereoscopicInfo {
        //		一楼发货口地址
        public static final String FLOOR_ONE_OUT_ADDRESS = "2002";

        //		一楼托盘出库口地址
        public static final String FLOOR_ONE_PALLET_OUT_ADDRESS = "3001";

        //		虚拟托盘商品码
        public static final String VIRTUAL_PALLET_GOODS_CODE = "pallet";

//        立库只启动一楼入库
        public static final boolean ONLY_FLOOR_ONE_START = false;

        //		虚拟托盘多少行
        public static final int PALLET_MAX_ROW_NUM = 5;

        //		一楼车数量
        public static final int CAR_NUM_FLOOR_ONE = 3;

        //		二楼车数量
        public static final int CAR_NUM_FLOOR_TWO = 2;

        //		三楼车数量
        public static final int CAR_NUM_FLOOR_THR = 1;

        //		四楼车数量
        public static final int CAR_NUM_FLOOR_FOU = 1;
    }

    /**
     * 配置常量
     */
    public class ConfigInfo {

        public static final String SESSION_KEY_USER = "userInfo";

        public static final String SESSION_LAST_MSG_TIME = "lastMsgTime";

        private ConfigInfo() {
        }

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
    }
}