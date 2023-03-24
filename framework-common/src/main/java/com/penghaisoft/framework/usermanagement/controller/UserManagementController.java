package com.penghaisoft.framework.usermanagement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.penghaisoft.framework.authorization.SecurityManager;
import com.penghaisoft.framework.constant.Constant;
import com.penghaisoft.framework.constant.Constant.RESULT;
import com.penghaisoft.framework.datatransfer.DataTransfer;
import com.penghaisoft.framework.distributedsession.IHttpSession;
import com.penghaisoft.framework.entity.ResponseResult;
import com.penghaisoft.framework.usermanagement.model.business.IUserBusiness;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.framework.util.ConvertFactory.Convert;
import com.penghaisoft.framework.util.ConvertFactory.ConvertFactory;
import com.penghaisoft.framework.util.VerificationCode;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


/**
 * 用户管理
 *
 * @author 刘立华
 * @date 2017-08-25
 */
//push test
@Slf4j
@Controller
@RequestMapping(value = "/userManagement")
public class UserManagementController {

    @Autowired
    private IUserBusiness userBusiness;

    @Autowired
    private SecurityManager securityManager;

    @Value("${notice.other-sys-addr.add-user}")
    private String noticeAddUserAddr;

    @Autowired
    private RestTemplate restTemplate;
    //操作redis
    @Resource
    private IHttpSession iHttpSession;

    //密码过期时间90天
    @Value("${framework.passwordTime:90}")
    private Integer passwordTime;


    /**
     * 生成验证码
     *
     * @param request  http请求
     * @param response http返回
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：null
     */
    @RequestMapping(value = "/getVerificationCode", method = RequestMethod.GET)
    public String getVerificationCode(HttpServletRequest request, HttpServletResponse response) {
        //响应结果对象
        ResponseResult responseResult = null;

        response.setContentType("image/jpeg"); //设置响应的类型格式为图片格式
        response.setHeader("Pragma", "no-cache"); //禁止图像缓存。
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //创建验证码对象
        VerificationCode vCode = userBusiness.getVerificationCode();
        //将验证码图片写入输出流
        try {
            vCode.write(response.getOutputStream());

            //构建响应结果对象
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.code, null);
        } catch (IOException e) {
            responseResult = new ResponseResult(RESULT.FAILED.code, RESULT.FAILED.code, null);
        }

        //根据响应结果对象生成JSON字符串
        Convert jsonConvert = ConvertFactory.buildJSON();
        return jsonConvert.convert(responseResult);
    }

    /**
     * 登录
     *
     * @param account          账号
     * @param password         密码
     * @param verificationCode 验证码
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：成功返回用户信息，失败返回null
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(HttpServletRequest request, String account, String password, String verificationCode) {
    //--------现场添加密码输入错误三次，锁定---开始-------
        String username1 = account;
        if(!checkLock(request.getSession(), username1)) {
            return new ResponseResult("1","该账号已被锁定，三分钟后请重试！",(Object)null);
        }
        if (StringUtils.isEmpty(username1)||StringUtils.isEmpty(password)) {
            return new ResponseResult("1","用户名和密码不能为空！",(Object)null);
        }
    //--------现场添加密码输入错误三次，锁定----结束------
        //响应结果对象
        ResponseResult responseResult = null;

        //获取登录IP地址
        String loginIP = request.getRemoteAddr();

        Map<String, Object> result = userBusiness.login(loginIP, account, password, verificationCode);

        if (result != null) {

            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, result);
//			todo 放入目前系统中所有的权限-没有根据工程区分
            securityManager.updateSysAuthorizationInfo();

            //清空登录失败记录
            cleanFailNum(request.getSession(), username1);

        } else {
            addFailNum(request.getSession(), username1);
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 注销
     *
     * @return void
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public void logout(HttpServletResponse response) {
        //清空当前用户的权限信息
        userBusiness.logout();
        return;
    }

    /**
     * 获取用户列表
     *
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：成功返回用户列表，失败返回null
     */
    /*@HasAllPermissions("retrieveUser")*/
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getUserList(int currentPage, int numberOnePage,String account, String nickname, String phone, Integer status, Integer departmentId, Integer roleId) {
        //响应结果对象
        ResponseResult responseResult = null;

        //获取当前页的用户列表
        List<User> userList = userBusiness.getUserList(currentPage,numberOnePage,account, nickname, phone, status, departmentId, roleId);
        if (userList == null) {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        } else {
            //获取总用户数
            Integer userTotalCount = userBusiness.getUserTotalCount(account, nickname, phone, status, departmentId, roleId);

            //将用户列表、总用户数转化为Map
            Map<String, Object> userMap = DataTransfer.transferUserListToMap(userList, userTotalCount);

            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, userMap);
        }

        return responseResult;
    }

    /**
     * 根据用户id查询用户
     */
    /*@HasAllPermissions("retrieveUser")*/
    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getUserById(Integer userId) {
        //响应结果对象
        ResponseResult responseResult = null;

        //获取当前页的用户列表
        User user = userBusiness.selectByPrimaryKey(userId);

        if (user != null) {
            responseResult = new ResponseResult(Constant.RESULT.SUCCESS.code, Constant.RESULT.SUCCESS.message, user);
        } else {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 修改用户信息
     *
     * @param userId   用户ID
     * @param nickname 昵称
     * @param email    邮箱
     * @param phone    手机号
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：null
     */
    /*@HasAllPermissions("updateUser,retrieveUser")*/
    @RequestMapping(value = "/updateUserInformation", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateUserInformation(Integer userId, String nickname, String email, String phone, String officePhone, String entryDate, String description, String departmentIds, String roleIds) {
        //响应结果对象
        ResponseResult responseResult = null;
        if(entryDate==null ||"".equals(entryDate)){
            entryDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }
        boolean result = userBusiness.updateUser(userId, nickname, email, phone, officePhone, entryDate, description, departmentIds, roleIds);

        if (result) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
//            todo 系统间同步
        } else {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：null
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult changePassword(String userId, String oldPassword, String newPassword) {

        //响应结果对象
        ResponseResult responseResult = null;

        boolean result = userBusiness.changePassword(Integer.valueOf(userId), oldPassword, newPassword);

        if (result) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
        } else {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;

    }

    /**
     * 重置密码
     *
     * @param userId 用户ID
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：null
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult resetPassword(Integer userId) {
        //响应结果对象
        ResponseResult responseResult = null;

        boolean result = userBusiness.resetPassword(userId);

        if (result) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
        } else {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }


    /**
     * 添加用户
     *
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：null
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult addUser(String account, String nickname, String email, String phone, String officePhone, String entryDate, String description, String departmentIds, String roleIds) {
        //响应结果对象
        ResponseResult responseResult = null;

        int result = userBusiness.addUser(account, nickname, email, phone, officePhone, entryDate, description, departmentIds, roleIds);

        if (result > 0) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
//          通知另一个系统新增
            Callable<ResponseResult> callAddUser = new Callable<ResponseResult>() {
                @Override
                public ResponseResult call() throws Exception {
                    return noticeAddUser(account, nickname, email, phone, officePhone, entryDate, description);
                }
            };
            FutureTask<ResponseResult> future = new FutureTask<>(callAddUser);
            new Thread(future).start();
            try {
                ResponseResult noticeResult = future.get();
                log.info(noticeResult.getCode()+":"+noticeResult.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        } else {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    private ResponseResult noticeAddUser(String account, String nickname, String email, String phone, String officePhone, String entryDate, String description){
        ResponseResult result = new ResponseResult("0",null,null);

        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("account", account);
        map.add("nickname", nickname);
        map.add("email", email);
        map.add("phone", phone);
        map.add("officePhone", officePhone);
        map.add("entryDate", entryDate);
        map.add("description", description);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<JSONObject> resp = restTemplate.postForEntity(noticeAddUserAddr,request,JSONObject.class);
        //        JSONObject result = restTemplate.postForObject(noticeAddUserAddr,request,JSONObject.class);
        if (resp.getStatusCodeValue()!=200){
            log.error("新增用户同步失败！");
            result.setMessage("新增用户同步失败！");
        }else {
            JSONObject noticeResult = resp.getBody();
            if (noticeResult.getString("code").equals("0")){
                log.info("新增用户同步成功！");
                result.setCode("1");
            }else {
                log.error("新增用户同步失败，" + noticeResult.getString("message"));
                result.setMessage(noticeResult.getString("message"));
            }
        }

        return result;
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：null
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult deleteUser(Integer userId) {
        //响应结果对象
        ResponseResult responseResult = null;

        boolean result = userBusiness.deleteUser(userId);

        if (result) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
//            todo 两个系统同步
        } else {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 停用用户
     *
     * @param userId 用户ID
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：null
     */
    @RequestMapping(value = "/disableUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult disableUser(Integer userId) {
        //响应结果对象
        ResponseResult responseResult = null;

        boolean result = userBusiness.disableUser(userId);

        if (result) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
        } else {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 启用用户
     *
     * @param userId 用户ID
     * @return JSON字符串
     * code：返回码
     * message：返回消息
     * data：null
     */
    @RequestMapping(value = "/enableUser", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseResult enableUser(Integer userId) {
        //响应结果对象
        ResponseResult responseResult = null;

        boolean result = userBusiness.enableUser(userId);

        if (result) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
        } else {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     * 获取当前登录用户信息
     * code：返回码
     * message：返回消息
     * data：用户已登录返回用户信息，否则返回null
     */
    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult getCurrentUser() {
        //响应结果对象
        ResponseResult responseResult = null;

        User user = userBusiness.getCurrentUser();

        if (user != null) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, user);
        } else {
            responseResult = new ResponseResult(userBusiness.getLastErrorCode(), userBusiness.getLastErrorMessage(), null);
        }

        return responseResult;
    }

    /**
     *功能描述: 用户修改session时长
     * @date 2022/9/9
     * @params
     * @return com.penghaisoft.framework.entity.ResponseResult
     */
    @RequestMapping(value = "/usersUpSession", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseResult usersUpSession(Integer sessionTime) {
        //响应结果对象
        ResponseResult responseResult = null;
        JSONObject jSONObject = iHttpSession.updateStringByTime(sessionTime);
        if (jSONObject!=null && !("").equals(jSONObject)) {
            responseResult = new ResponseResult(RESULT.SUCCESS.code, RESULT.SUCCESS.message, null);
        } else {
            responseResult = new ResponseResult(RESULT.FAILED.code, RESULT.FAILED.message, null);
        }
        return responseResult;
    }


    /**
     * 校验用户登录失败次数
     * @param session
     * @param username
     * @return
     */
    public boolean checkLock(HttpSession session, String username) {
        Object o =session.getServletContext().getAttribute(username);
        // session.getServletContext().getInitParameter(username);
        if(o==null) {
            return true;
        }
        HashMap<String,Object> map  = (HashMap<String, Object>) o;
        int num  = (int) map.get("num");
        Date date = (Date) map.get("lastDate");
        long timeDifference = ((new Date().getTime()-date.getTime())/60/1000);
        //锁定三分钟
        if(num>2&&timeDifference<3) {
            return false;
        }
        return true;
    }
    /**
     * 新增用户登录失败次数
     * @param session
     * @param username
     */
    public void addFailNum(HttpSession session, String username) {
        Object o = session.getServletContext().getAttribute(username);
        HashMap<String,Object> map = null;
        int num= 0;
        if(o==null) {
            map = new HashMap<String,Object>();
        }else {
            map  = (HashMap<String, Object>) o;
            num  = (int) map.get("num");
            Date date = (Date) map.get("lastDate");
            long timeDifference = ((new Date().getTime()-date.getTime())/60/1000);
            if(timeDifference>=3) {
                num=0;
            }
        }
        map.put("num", num+1);
        map.put("lastDate", new Date());
        session.getServletContext().setAttribute(username, map);
    }
    /**
     * 清理用户登录失败的记录
     * @param session
     * @param username
     */
    public void cleanFailNum(HttpSession session, String username) {
        session.getServletContext().removeAttribute(username);
    }



}