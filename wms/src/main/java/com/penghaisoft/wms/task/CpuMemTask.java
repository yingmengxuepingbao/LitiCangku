//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.task;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = "spring.profiles",
        name = {"active"},
        havingValue = "kaos"
)
public class CpuMemTask {
    private static final Logger log = LoggerFactory.getLogger(CpuMemTask.class);
    private List<Byte[]> costs = new ArrayList();

    public CpuMemTask() {
    }

    @Scheduled(
            cron = "0/1 * * * * *"
    )
    public void cleadRedisSeq() {
        for(int j = 0; j < 10; ++j) {
            Byte[] bytes = new Byte[1048576];

            for(int i = 0; i < 1048576; ++i) {
                Double val = Math.random() * Math.random();
                bytes[i] = val.byteValue();
            }

            this.costs.add(bytes);
        }

        if (this.costs.size() > 100) {
            this.costs.clear();
        }

    }
}
