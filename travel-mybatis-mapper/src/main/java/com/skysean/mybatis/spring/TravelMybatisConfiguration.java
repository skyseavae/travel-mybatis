package com.skysean.mybatis.spring;

import com.skysean.mybatis.datasource.druid.DruidDataSourceBuilder;
import com.skysean.mybatis.spring.mybatis.config.MapperBeanDefinitionRegistry;
import com.skysean.mybatis.spring.mybatis.config.MybatisConfigurationCustomizer;
import com.skysean.mybatis.spring.mybatis.config.properties.ConfigMybatisPropertiesLoader;
import com.skysean.mybatis.spring.mybatis.config.properties.MybatisPropertiesLoader;
import com.skysean.mybatis.spring.mybatis.config.properties.TravelMybatisProperties;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;

/**
 * 描述：travel-mybatis配置
 * @author skysean
 */
@Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
public class TravelMybatisConfiguration implements EnvironmentAware {

    private Environment environment;

    @Bean(name = "defaultDataSource")
    @ConditionalOnMissingBean
    public DataSource dataSource() throws SQLException {
    	return buildDataSource();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    protected DataSource buildDataSource() throws SQLException{
    	return new DruidDataSourceBuilder(environment).build();
    }

    @Bean
    @DependsOn("travelMybatisProperties")
    public MapperBeanDefinitionRegistry mapperBeanDefinitionRegistry(TravelMybatisProperties travelMybatisProperties){
        return new MapperBeanDefinitionRegistry(travelMybatisProperties);
    }

    @Bean
    @DependsOn("mybatisPropertiesLoader")
    public TravelMybatisProperties travelMybatisProperties(MybatisPropertiesLoader mybatisPropertiesLoader){
        return mybatisPropertiesLoader.load();
    }

    @Bean
    @ConditionalOnMissingBean
    public MybatisPropertiesLoader mybatisPropertiesLoader(){
        return new ConfigMybatisPropertiesLoader(environment);
    }

    @Bean
    public MybatisConfigurationCustomizer mybatisConfigurationCustomizer(ApplicationContext applicationContext,
        TravelMybatisProperties travelMybatisProperties, SqlSessionFactory sqlSessionFactory){
        return new MybatisConfigurationCustomizer(applicationContext).build(travelMybatisProperties, sqlSessionFactory.getConfiguration());
    }
}
