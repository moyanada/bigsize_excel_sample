package com.sample.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

public class DataConfig {

    public static final String BASE_PACKAGE = "com.sample.batch.dao"; // 최상단 패키지 경로
    public static final String TYPE_ALIASES_PACKAGE = "com.sample.batch.dto"; // domain 패키지 주소
    public static final String MAPPER_LOCATIONS_PATH = "classpath:mapper/**/*Mapper.xml"; // Mapper 경로
    public static final String CONFIG_PATH = "classpath:mapper/mybatis-config.xml"; // Config 경로

    protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATIONS_PATH));
        sessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(CONFIG_PATH));
    }
}

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = DataConfig.BASE_PACKAGE, sqlSessionFactoryRef = "sqlSessionFactoryLocal")
class DataSampleConfig extends DataConfig {
    @ConfigurationProperties(prefix = "spring.datasource.localdb")
    @Bean(name = "dataSourceLocal")
    public DataSource dataSourceLocal() throws Exception {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactoryLocal")
    public SqlSessionFactoryBean sqlSessionFactoryLocal(ApplicationContext applicationContext, @Qualifier("dataSourceLocal") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        configureSqlSessionFactory(factoryBean, dataSource);
        return factoryBean;
    }

    @Bean(name = "sqlSessionTemplateLocal")
    public SqlSessionTemplate sqlSessionTemplateLocal(@Qualifier("sqlSessionFactoryLocal") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "txManagerLocal")
    public PlatformTransactionManager txManagerLocal(@Qualifier("dataSourceLocal") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
}
