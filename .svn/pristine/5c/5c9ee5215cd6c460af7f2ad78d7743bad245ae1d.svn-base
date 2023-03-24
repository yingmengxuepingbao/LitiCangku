package com.penghaisoft.framework.controlleradvice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.penghaisoft.framework.util.ConvertFactory.Convert;
import com.penghaisoft.framework.util.ConvertFactory.ConvertFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.servlet.http.HttpServletResponse;

//@ControllerAdvice
@Slf4j
public class FrameworkResponseBodyAdvice implements ResponseBodyAdvice {

//    @Autowired
//    private HttpServletResponse response;

    /**
     * 用于控制是否进行统一处理
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        response.setContentType("application/json;charset=UTF-8");
        //将返回对象转化为JSON
        Convert jsonConvert = ConvertFactory.buildJSON();
        return JSON.parseObject((jsonConvert.convert(o)), Feature.DisableCircularReferenceDetect);
    }
}
