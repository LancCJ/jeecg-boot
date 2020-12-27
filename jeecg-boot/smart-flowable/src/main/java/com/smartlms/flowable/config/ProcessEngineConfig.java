package com.smartlms.flowable.config;

import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import javax.sql.DataSource;

/**
 * TODO
 *
 * @author chenjian
 * @version 1.0
 * @date 2020/12/27 11:56 上午
 */
@Configuration
public class ProcessEngineConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * ProcessEngine 配置，其中DataSourceTransactionManager和DataSource自动注入
     * @return
     */
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
        springProcessEngineConfiguration.setDataSource(dataSource);
        //不添加此项配置，在没创建表时，会抛出FlowableWrongDbException异常
        springProcessEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        springProcessEngineConfiguration.setTransactionManager(dataSourceTransactionManager(dataSource));
        return springProcessEngineConfiguration;
    }

}
