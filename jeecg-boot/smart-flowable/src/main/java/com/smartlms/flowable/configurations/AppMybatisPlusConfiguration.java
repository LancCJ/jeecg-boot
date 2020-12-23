//package com.smartlms.flowable.configurations;
//
//import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ResourceLoader;
//
//import javax.sql.DataSource;
//
///**
// * @ClassName: Test1DataSource
// * @Author: ren
// * @Description:
// * @CreateTIme: 2019/7/17 0017 下午 1:55
// **/
//@MapperScan(
////        basePackages = {  去除此处配置  使用 jeecg 配置  MybatisPlusConfig  在这里
////        "com.smartlms.flowable.modules.app.dao.jpas"
////},
//        sqlSessionTemplateRef = "appSqlSessionTemplate",
//        sqlSessionFactoryRef = "appSqlSessionFactory"
//)
//@EnableConfigurationProperties(MybatisPlusProperties.class)
//@Configuration
//public class AppMybatisPlusConfiguration extends AbstractMybatisPlusConfiguration {
//
//    @Bean(name = "appSqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource,
//                                                   MybatisPlusProperties properties,
//                                                   ResourceLoader resourceLoader,
//                                                   ApplicationContext applicationContext) throws Exception {
//        return getSqlSessionFactory(dataSource,
//                properties,
//                resourceLoader,
//                null,
//                null,
//                applicationContext);
//    }
//
//    @Bean(name = "appSqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(MybatisPlusProperties properties,
//                                                 @Qualifier("appSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return getSqlSessionTemplate(sqlSessionFactory, properties);
//    }
//}
