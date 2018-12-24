package com.skysean.mybatis.spring.mybatis.config;

import com.skysean.mybatis.exception.TravelMybatisException;
import com.skysean.mybatis.spring.mybatis.config.properties.TravelMybatisProperties;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * 描述：mapper接口 BeanDefinition注册
 * @author skysean
 */
public class MapperBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

  private TravelMybatisProperties properties;

  public MapperBeanDefinitionRegistry(TravelMybatisProperties properties){
    this.properties = properties;
  }

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
      throws BeansException {

    if(null == properties.getBasePackages() || "".equals(properties.getBasePackages().trim())){
      throw new TravelMybatisException("没有读取到basePackages属性配置信息");
    }

    ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
    scanner.registerFilters();
    scanner.doScan(properties.getBasePackages());
  }

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
  }
}
