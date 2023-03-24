//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DruidDataSourceInit extends DruidDataSource {
    private static final Logger log = LoggerFactory.getLogger(DruidDataSourceInit.class);

    public DruidDataSourceInit() {
        this.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.setInitialSize(5);
        this.setMaxActive(20);
        this.setMinIdle(5);
        this.setMaxWait(6000L);
        this.setTimeBetweenEvictionRunsMillis(60000L);
        this.setMinEvictableIdleTimeMillis(300000L);
        //this.setValidationQuery("select 1 from dual");
        this.setValidationQuery("select 1 ");
        this.setTestWhileIdle(true);
        this.setTestOnBorrow(true);
        this.setTestOnReturn(false);
        this.setPoolPreparedStatements(true);
        this.setMaxPoolPreparedStatementPerConnectionSize(20);

        try {
            this.setFilters("");
        } catch (SQLException var2) {
            log.error("druid configuration initialization filter", var2);
        }

        Properties properties = new Properties();
        properties.setProperty("druid.stat.mergeSql", "true");
        properties.setProperty("druid.stat.slowSqlMillis", "5000");
        this.setConnectProperties(properties);
        this.setUseGlobalDataSourceStat(false);
    }

    public DruidDataSourceInit(String url, String userName, String passWord) {
        this();
        this.setUrl(url);
        this.setUsername(userName);
        this.setPassword(passWord);
    }
}
