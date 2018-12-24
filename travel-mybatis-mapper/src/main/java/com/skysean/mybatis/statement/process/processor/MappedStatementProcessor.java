package com.skysean.mybatis.statement.process.processor;

import org.apache.ibatis.builder.MapperBuilderAssistant;

import java.lang.reflect.Method;

/**
 * 描述：MappedStatement处理器
 * @author skysean
 */
public interface MappedStatementProcessor {

    /**
     * 描述：处理mapper接口中的方法，并将MappedStatement写入mybatis Configuration中
     * @author skysean
     * @param entityClass 实体类
     * @param mapperBuilderAssistant mapper构造辅助类
     */
    void process(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant);

    /**
     * 描述：处理器是否是可以处理mapper接口方法(如果当前处理器不能处理，则不会执行process方法)
     * @author skysean
     * @param method mapper接口方法
     * @param entityClass 实体类
     * @return true:可以处理， false:不能处理
     */
    boolean canHandle(Method method, Class<?> entityClass);
}
