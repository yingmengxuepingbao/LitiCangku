//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.task;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearTask {
    private static final Logger log = LoggerFactory.getLogger(ClearTask.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ClearTask() {
    }

    @Scheduled(
            cron = "30 59 23 * * *"
    )
    public void cleadRedisSeq() {
        Set<String> ks = this.stringRedisTemplate.keys("wms:seq:*");
        if (null != ks && ks.size() > 0) {
            this.stringRedisTemplate.delete(ks);
            log.info("清除序列号 " + ks.size() + " 个");
        } else {
            log.info("今日无序列号产生");
        }

    }
}
