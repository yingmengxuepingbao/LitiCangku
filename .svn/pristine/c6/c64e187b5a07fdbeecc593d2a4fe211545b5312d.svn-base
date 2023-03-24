//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.task;

import com.penghaisoft.framework.usermanagement.model.dao.UserMapper;
import com.penghaisoft.framework.usermanagement.model.entity.User;
import com.penghaisoft.wms.datasource.DataSourceContext;
import com.penghaisoft.wms.datasource.DatasourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DbCheckTask {
    private static final Logger log = LoggerFactory.getLogger(DbCheckTask.class);
    @Autowired
    private DataSourceContext dataSourceContext;
    @Autowired
    private UserMapper userMapper;

    public DbCheckTask() {
    }

//    @Scheduled(cron = "0/1 * * * * *")
    public void chechDb() {
        try {
            this.dataSourceContext.setDataSource(DatasourceUtil.ALIVE_DS);
            User var1 = this.userMapper.selectByPrimaryKey(35);
        } catch (Exception var2) {
            if ("slave".equals(DatasourceUtil.ALIVE_DS)) {
                log.info("检测到当前数据源slave异常,切换到master");
                DatasourceUtil.ALIVE_DS = "master";
            } else if ("master".equals(DatasourceUtil.ALIVE_DS)) {
                log.info("检测到当前数据源切换到master异常,切换到slave");
                DatasourceUtil.ALIVE_DS = "slave";
            } else {
                log.error(DatasourceUtil.ALIVE_DS);
            }
        }

    }
}
