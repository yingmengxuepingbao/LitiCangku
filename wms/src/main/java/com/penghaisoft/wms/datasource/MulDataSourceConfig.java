//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.penghaisoft.wms.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MulDataSourceConfig {
    private static final Logger log = LoggerFactory.getLogger(MulDataSourceConfig.class);

    public MulDataSourceConfig() {
    }

    @Bean
    @ConfigurationProperties(
            prefix = "datasource-config.master"
    )
    public DruidDataSource dsMaster() {
        DruidDataSource ds = new DruidDataSourceInit();
        return ds;
    }

    @Bean
    @ConfigurationProperties(
            prefix = "datasource-config.slave"
    )
    public DruidDataSource dsSlave() {
        return new DruidDataSourceInit();
    }

    @Primary
    @Bean(
            name = {"multiDataSource"}
    )
    public MultiRouteDataSource exampleRouteDataSource() {
        MultiRouteDataSource multiDataSource = new MultiRouteDataSource();
        Map<Object, Object> targetDataSources = new HashMap();
        this.fillTargetDataSources(targetDataSources, "master", this.dsMaster());
        this.fillTargetDataSources(targetDataSources, "slave", this.dsSlave());
        multiDataSource.setTargetDataSources(targetDataSources);
        multiDataSource.setDefaultTargetDataSource(this.dsMaster());
        return multiDataSource;
    }

    private void fillTargetDataSources(Map<Object, Object> targetDataSources, String dsName, DruidDataSource druidDataSource) {
        targetDataSources.put(dsName, druidDataSource);
    }

    @Bean
    public DataSourceContext dataSourceContext() {
        return new DataSourceContext();
    }
}
