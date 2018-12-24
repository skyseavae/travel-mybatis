package com.skysean.mybatis.statement.process;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.builder.MapperBuilderAssistant;

import com.skysean.mybatis.statement.process.loader.ClassPathProcessorLoader;
import com.skysean.mybatis.statement.process.loader.ProcessorLoader;
import com.skysean.mybatis.statement.process.processor.MappedStatementProcessor;

/**
 * 描述：mapper接口方法处理聚合
 * @author skysean
 */
public class MethodProcessorComposite {

	private MapperBuilderAssistant mapperBuilderAssistant;//mapper构造辅助类

	private List<MappedStatementProcessor> mappedStatementProcessors = new ArrayList<>();//mappedStatement处理器
	
	private ProcessorLoader processorLoader = new ClassPathProcessorLoader();//classpath的MappedStatementProcessor加载器
	
	public MethodProcessorComposite(MapperBuilderAssistant mapperBuilderAssistant) {
		this.mapperBuilderAssistant = mapperBuilderAssistant;
		initMappedStatementProcessor();
	}

	/**
	 * 描述：初始化系统内部的MappedStatementProcessor
	 * @author skysean
	 */
	private void initMappedStatementProcessor(){
		if(!mappedStatementProcessors.isEmpty()){
			return;
		}
		mappedStatementProcessors.addAll(processorLoader.load());
	}

	/**
	 * 描述：加载MappedStatement到configuration中
	 * @author skysean
	 * @param entityClass 实体类
	 * @param method mapper接口方法
	 */
	public void loadMappedStatement(Class<?> entityClass, Method method) {
		for(MappedStatementProcessor mappedStatementProcessor : mappedStatementProcessors){
			if(mappedStatementProcessor.canHandle(method, entityClass)){
				mappedStatementProcessor.process(method, entityClass, mapperBuilderAssistant);
			}
		}
	}

	public void setProcessorScanner(ProcessorLoader processorLoader) {
		this.processorLoader = processorLoader;
	}

	public void addMappedStatementProcessor(MappedStatementProcessor mappedStatementProcessor){
		mappedStatementProcessors.add(mappedStatementProcessor);
	}

	public void addAllMappedStatementProcessor(List<MappedStatementProcessor> mappedStatementProcessors){
		mappedStatementProcessors.addAll(mappedStatementProcessors);
	}
	
}
