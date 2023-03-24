//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.aop;

import com.penghaisoft.wms.datasource.DataSourceContext;
import com.penghaisoft.wms.datasource.DatasourceUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {
    private static final Logger log = LoggerFactory.getLogger(ControllerAspect.class);
    @Autowired
    private DataSourceContext dataSourceContext;

    public ControllerAspect() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)|| @annotation(org.springframework.web.bind.annotation.GetMapping)|| @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        this.dataSourceContext.setDataSource(DatasourceUtil.ALIVE_DS);
    }
}
