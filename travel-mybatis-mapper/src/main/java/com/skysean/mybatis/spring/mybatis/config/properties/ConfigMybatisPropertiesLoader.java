package com.skysean.mybatis.spring.mybatis.config.properties;

import org.springframework.core.env.Environment;

/**
 * 描述：环境配置加载travel-mybatis相关配置
 * @author skysean
 */
public class ConfigMybatisPropertiesLoader implements MybatisPropertiesLoader{

    private Environment env;

    public ConfigMybatisPropertiesLoader(Environment env){
        this.env = env;
    }

    @Override
    public TravelMybatisProperties load() {
        TravelMybatisProperties mybatisProperties = new TravelMybatisProperties();
        mybatisProperties.setBasePackages(env.getProperty(PropertyConstants.BASE_PACKAGES));
        return mybatisProperties;
    }
}
