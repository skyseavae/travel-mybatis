package com.skysean.mybatis.starter;

import com.skysean.mybatis.spring.TravelMybatisConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：travel-mybatis 支持spring boot 自动化配置
 * @author skysean
 */
@Configuration
@ImportAutoConfiguration(TravelMybatisConfiguration.class)
public class TravelMybatisAutoConfiguration {

}
