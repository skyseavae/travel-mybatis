package com.skysean.mybatis.spring.mybatis.config;

import com.skysean.mybatis.spring.mybatis.config.properties.TravelMybatisProperties;
import com.skysean.mybatis.statement.MappedStatementResolver;
import com.skysean.mybatis.statement.process.processor.MappedStatementProcessor;
import java.util.Map;
import org.apache.ibatis.session.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

/**
 * 描述：mybatis配置自定义操作
 * @author skysean
 */
public class MybatisConfigurationCustomizer {

    private ApplicationContext applicationContext;//spring context

    public MybatisConfigurationCustomizer(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    /**
     * 描述：设置自定义语法的MappedStatement处理器到解析器中
     * @author skysean
     * @param mappedStatementResolver mappedStatement解析器
     */
    private void setCustomMappedStatementProcessor(MappedStatementResolver mappedStatementResolver){
        Map<String, MappedStatementProcessor> beans = applicationContext.getBeansOfType(MappedStatementProcessor.class);
        if(null == beans || beans.isEmpty()){
            return;
        }

        for(Map.Entry<String, MappedStatementProcessor> bean : beans.entrySet()){
            mappedStatementResolver.addMappedStatementProcessor(bean.getValue());
        }
    }

    /**
     * 描述：构造mappedStatement，并加载到configuration中
     * @author skysean
     * @param travelMybatisProperties travel-mybatis属性
     * @param configuration mybatis configuration
     * @return 当前对象
     */
    public MybatisConfigurationCustomizer build(TravelMybatisProperties travelMybatisProperties, Configuration configuration){

        //构造MappedStatement解析器
        MappedStatementResolver mappedStatementResolver = new MappedStatementResolver(configuration);

        //设置自定义语法的MappedStatement处理器到解析器中
        setCustomMappedStatementProcessor(mappedStatementResolver);

        //加载所有的MappedStatement
        mappedStatementResolver.loadMappedStatements(StringUtils.tokenizeToStringArray(travelMybatisProperties.getBasePackages(), ",; \t\n"));

        return this;
    }
}
