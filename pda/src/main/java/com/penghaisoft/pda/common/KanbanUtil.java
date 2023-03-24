package com.penghaisoft.pda.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName KanbanUtil
 * @Description 电子看板工具类
 * @Author zhangx
 * @Date 2020/6/12 14:01
 **/
@Slf4j
@Service
public class KanbanUtil {

    @Value("${led.ip}")
    private String ledIp;

    @Value("${led.port}")
    private String ledPort;



}
