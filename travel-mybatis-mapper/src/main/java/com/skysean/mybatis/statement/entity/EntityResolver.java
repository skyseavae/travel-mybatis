package com.skysean.mybatis.statement.entity;

import com.skysean.mybatis.exception.TravelMybatisException;
import com.skysean.mybatis.statement.sqlsource.utils.SqlSourceUtil;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;

import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：实体类解析器
 * @author skysean
 */
public class EntityResolver {
	
	private Class<?> entityClass;
	
	private Field idField;
	
	private Map<String, String> columnMappings = new HashMap<>();
	
	private Map<String, Field> propertyMappings = new HashMap<>();
	
	public EntityResolver(Class<?> entityClass){
		this.entityClass = entityClass;
		
		Field[] declaredFields = entityClass.getDeclaredFields();
		for(Field f : declaredFields){
			if(f.isAnnotationPresent(Id.class)){
				this.idField = f;
			}
			
			columnMappings.put(f.getName(), SqlSourceUtil.getColumnName(f));
			propertyMappings.put(f.getName(), f);
		}
	}
	
	public Field getProperty(String property){
		return propertyMappings.get(property);
	}
	
	public String getColumn(String property){
		return columnMappings.get(property);
	}
	
	public boolean hasAutoIncrementId(){
		
		assertId(idField);
		
		return SqlSourceUtil.isAutoIncrementId(idField);
		
	}
	
	public Field getIdProperty(){
		
		assertId(idField);
		
		return idField;
	}
	
	private void assertId(Field idField){
		if(null == idField){
			throw new TravelMybatisException("entity class ["+entityClass.getName()+"]没有找到对应id");
		}
	}
	
	public String getIdColumn(){
		
		assertId(idField);
		
		return SqlSourceUtil.getColumnName(idField);
	}
	
	public KeyGenerator getKeyGenerator(){
		
		if(hasAutoIncrementId()){
			return Jdbc3KeyGenerator.INSTANCE;
		}
		
		return NoKeyGenerator.INSTANCE;
	}
	
	public String getTableName() {
		Table table = entityClass.getAnnotation(Table.class);
		
		if(null == table) {
			return entityClass.getSimpleName();
		}
		
		if(null == table.name() || table.name().trim().length() < 1) {
			return entityClass.getSimpleName();
		}
		
		return table.name();
	}

	public boolean hasProperty(String property){
		return propertyMappings.containsKey(property);
	}
	
}
