package com.penghaisoft.framework.log.aspect;

import com.penghaisoft.framework.annotation.OperationLogAspect;
import com.penghaisoft.framework.authorization.constant.SecurityManagerConstant;
import com.penghaisoft.framework.constant.OperationTypeEnum;
import com.penghaisoft.framework.distributedsession.HttpSession;
import com.penghaisoft.framework.log.model.business.IOperationLogBusiness;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 记录操作日志aop
 * @author 秦超
 * 2017-08-28 14:25:42
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
	@Autowired
	private IOperationLogBusiness iOperationLogBusiness;

	@Resource
	private HttpSession httpSession;

	/**
	 * 定义切点
	 *
	 * @Pointcut 注解的所在路径
	 */
	@Pointcut("@annotation(com.penghaisoft.framework.annotation.OperationLogAspect)")
	public void logPointCut() {
	}

    /**
     * 返回值切面  判断是否记录操作日志
     * @param joinPoint
     */
	@AfterReturning(value = "logPointCut()")
	public void afterReturning(JoinPoint joinPoint) {

    	try {
			//1.获取操作描述
			List<String> operationDescrition = getControllerMethodDescription(joinPoint);

			String operationName = operationDescrition.get(0);
			String operationType = OperationTypeEnum.find(operationDescrition.get(1)).getOperationType();

			//2.获取当前session
			String session = httpSession.getSessionIdFromHeader();
			String userIdString = httpSession.getString(SecurityManagerConstant.USER_ID);
			//3.记录登录用户操作日志
			if(userIdString != null && userIdString != "null"){
				int userId = Integer.parseInt(userIdString);
				boolean addResult = iOperationLogBusiness.addOperationLog(userId, operationName, operationType);
				if(!addResult){
					log.error("操作日志记录失败！");
				}
			}
		} catch (Exception e) {
			log.error("操作日志记录错误！",e);
		}
	}

	/**
	 * 方法运行前
	 */
	public void before(JoinPoint joinPoint){
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		log.info("<====================================================================");
		log.info("请求来源:  => " + request.getRemoteAddr());
		log.info("请求URL: " + request.getRequestURL().toString());
		log.info("请求方式: " + request.getMethod());
		log.info("响应方法: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		log.info("请求参数 : " + Arrays.toString(joinPoint.getArgs()));
		log.info("---------------------------------------------------------------------");
	}

    /**
     * 异常切面  记录异常信息
     * @param e
     */
    public void afterThrowing(JoinPoint joinPoint,Throwable e){
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		log.info("<====================================================================");
		log.info("请求来源:  => " + request.getRemoteAddr());
		log.info("请求URL: " + request.getRequestURL().toString());
		log.info("请求方式: " + request.getMethod());
		log.info("响应方法: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		log.info("请求参数 : " + Arrays.toString(joinPoint.getArgs()));
		log.info("---------------------------------------------------------------------");
    	log.error("业务抛错:", e);
    }

	/**
	 * 获取注解方法的描述参数信息
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	public static List<String> getControllerMethodDescription(JoinPoint joinPoint)  throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        List<String> descrition = new ArrayList<>();
         for (Method method : methods) {
             if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                 if (clazzs.length == arguments.length) {
                	 descrition.add(method.getAnnotation(OperationLogAspect.class).operationName());
                	 descrition.add(method.getAnnotation(OperationLogAspect.class).operationType());
                     break;
                }
            }
        }
         return descrition;
    }
}
