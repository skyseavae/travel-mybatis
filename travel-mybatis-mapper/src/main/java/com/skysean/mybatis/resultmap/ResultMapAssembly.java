package com.skysean.mybatis.resultmap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.ResultMapping;

import com.skysean.mybatis.statement.sqlsource.utils.SqlSourceUtil;

/**
 * 描述：mybatis ResultMap组装
 * @author skysean
 */
public class ResultMapAssembly {
	
	private MapperBuilderAssistant mapperBuilderAssistant;//mybatis mapper构造辅助类
	
	private Class<?> entityClass;//实体类
	
	public ResultMapAssembly(MapperBuilderAssistant mapperBuilderAssistant, Class<?> entityClass){
		this.mapperBuilderAssistant = mapperBuilderAssistant;
		this.entityClass = entityClass;
	}

	/**
	 * 描述：加载ResultMap到对应的mybatis configuration中
	 * @author skysean
	 */
	public void loadResultMap(){

		if(mapperBuilderAssistant.getConfiguration().hasResultMap(entityClass.getSimpleName())){
			return;
		}

		List<ResultMapping> resultMappings = new ArrayList<>();

		Field[] fields = entityClass.getDeclaredFields();
		for(Field field : fields) {
			if(SqlSourceUtil.isStaticField(field)){
				continue;
			}
			try{
				ResultMapping resultMapping = new ResultMapping.Builder(mapperBuilderAssistant.getConfiguration(), 
						field.getName(), SqlSourceUtil.getColumnName(field), field.getType()).build();
				resultMappings.add(resultMapping);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		mapperBuilderAssistant.addResultMap(entityClass.getSimpleName(), entityClass, null, null, resultMappings, null);
	}
}
