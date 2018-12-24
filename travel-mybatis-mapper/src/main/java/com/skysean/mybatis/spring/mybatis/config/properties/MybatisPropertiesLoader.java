package com.skysean.mybatis.spring.mybatis.config.properties;

/**
 * 描述：travel-mybatis相关配置加载
 * @author skysean
 */
public interface MybatisPropertiesLoader {

    /**
     * 描述：加载travel-mybatis相关配置
     * @author skysean
     * @return travel-mybatis需要的配置
     */
    TravelMybatisProperties load();
}
