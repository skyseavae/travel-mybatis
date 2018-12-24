package com.skysean.mybatis.spring.mybatis.config.properties;

/**
 * 描述：travel-mybatis属性
 * @author skysean
 */
public class TravelMybatisProperties {

    private String basePackages;//mapper接口所在包

    public String getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String basePackages) {
        this.basePackages = basePackages;
    }
}
