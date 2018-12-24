package com.skysean.mybatis.statement;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.skysean.mybatis.statement.process.processor.MappedStatementProcessor;
import com.skysean.mybatis.statement.scanner.ClassPathPackageMapperScanner;
import com.skysean.mybatis.statement.scanner.MapperScanner;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;

import com.skysean.mybatis.mapper.MapperUtil;
import com.skysean.mybatis.resultmap.ResultMapAssembly;
import com.skysean.mybatis.statement.process.MethodProcessorComposite;

/**
 * 描述：MappedStatement解析器
 * @author skysean
 */
public class MappedStatementResolver {

	private Configuration configuration;//mybatis configuration

	private MapperScanner mapperScanner = new ClassPathPackageMapperScanner();//classpath的mapper接口扫描器

	private List<MappedStatementProcessor> customMappedStatementProcessors = new ArrayList<>();
	
	public MappedStatementResolver(Configuration configuration){
		this.configuration = configuration;
	}

	public void setMapperScanner(MapperScanner mapperScanner) {
		this.mapperScanner = mapperScanner;
	}

	/**
	 * 描述：加载MappedStatement到configuration
	 * @author skysean
	 * @param entityMappers 实体对应的mapper接口集合
	 */
	public void loadMappedStatements(Set<Class<?>> entityMappers){

		for(Class<?> entityMapper : entityMappers){
			execute(entityMapper);
		}
	}

	/**
	 * 描述：加载MappedStatement到configuration
	 * @author skysean
	 * @param packages mapper接口包路径
	 */
	public void loadMappedStatements(String[] packages){
		loadMappedStatements(mapperScanner.scan(packages));
	}

	/**
	 * 描述：执行解析方法
	 * @author skysean
	 * @param entityMapper 实体对应的mapper接口
	 */
	private void execute(Class<?> entityMapper){

		MapperBuilderAssistant mapperBuilderAssistant = new MapperBuilderAssistant(configuration, null);
		mapperBuilderAssistant.setCurrentNamespace(entityMapper.getName());

		Class<?> entityClass = MapperUtil.getEntityClass(entityMapper);

		//加载实体与数据库字段映射关系到mybatis中
		new ResultMapAssembly(mapperBuilderAssistant, entityClass).loadResultMap();
		
		//获取将方法处理成mybatis MappedStatement
		MethodProcessorComposite methodProcessorComposite = new MethodProcessorComposite(mapperBuilderAssistant);
		if(!customMappedStatementProcessors.isEmpty()){
			methodProcessorComposite.addAllMappedStatementProcessor(customMappedStatementProcessors);
		}

		//获取entityMapper的所有方法，并加载到mybatis configuration中
		Method[] methods = entityMapper.getMethods();
		for(Method m : methods){
			methodProcessorComposite.loadMappedStatement(entityClass, m);
		}
	}

	public void addMappedStatementProcessor(MappedStatementProcessor mappedStatementProcessor){
		customMappedStatementProcessors.add(mappedStatementProcessor);
	}
}
