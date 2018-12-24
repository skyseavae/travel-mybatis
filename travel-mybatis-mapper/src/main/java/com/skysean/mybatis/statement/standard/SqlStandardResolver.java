package com.skysean.mybatis.statement.standard;

import com.skysean.mybatis.statement.entity.EntityResolver;
import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 描述：解析mapper方法到sql语法
 * @author skysean
 */
public class SqlStandardResolver {

	private Method method;
	private Class<?> entityClass;
	private SqlCommandType sqlCommandType;
	private EntityResolver entityResolver;

	public SqlStandardResolver(Method method, Class<?> entityClass, SqlCommandType type){
		this.method = method;
		this.entityClass = entityClass;
		this.sqlCommandType = type;
		this.entityResolver = new EntityResolver(this.entityClass);
	}

	public Field getOperationProperty(){

		int indexOfBy = getIndexOfBy();

		if(-1 == indexOfBy){
			return null;
		}

		String sqlCommandKeyWord = getSqlCommandKeyWord();

		String operationProperty = method.getName().substring(sqlCommandKeyWord.length(), indexOfBy);
		operationProperty = firstToLowerCase(operationProperty);

		return entityResolver.getProperty(operationProperty);
	}

	public String getOperationColumn(){
		Field operationProperty = getOperationProperty();
		if(null == operationProperty){
			return null;
		}

		return entityResolver.getColumn(operationProperty.getName());
	}

	private String getSqlCommandKeyWord(){
		switch (sqlCommandType) {
		case DELETE:
			return SqlStandardConstants.DELETE;
		case SELECT:
			return SqlStandardConstants.SELECT;
		case UPDATE:
			return SqlStandardConstants.UPDATE;
		case INSERT:
			return SqlStandardConstants.INSERT;
		default:
			return null;
		}
	}
	
	public Field getConditionProperty(){
		int indexOfBy = getIndexOfBy();
		if(-1 == indexOfBy){
			return null;
		}
		String conditionProperty = method.getName().substring(indexOfBy + 2, method.getName().length());
        conditionProperty = firstToLowerCase(conditionProperty);
		return entityResolver.getProperty(conditionProperty);
	}
	
	private String firstToLowerCase(String str){
		if(null == str || "".equals(str)){
			return null;
		}
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	
	public int getIndexOfBy(){
		String methodName = method.getName();
		int indexOfBy = methodName.lastIndexOf(SqlStandardConstants.BY);
		if(indexOfBy == methodName.length() - 2){
			indexOfBy = methodName.lastIndexOf(SqlStandardConstants.BY, methodName.length() - 3);
		}
		return indexOfBy;
	}
	
	public String getConditionColumn(){
		Field conditionProperty = getConditionProperty();
		if(null == conditionProperty){
			return null;
		}
		return entityResolver.getColumn(conditionProperty.getName());
	}
	
	public String getTableName(){
		return entityResolver.getTableName();
	}

	public boolean hasOperationAndConditionProperty(){
		int indexOfBy = getIndexOfBy();

		if(indexOfBy != -1
				&& null != getOperationProperty()
				&& null != getConditionProperty()){
			return true;
		}

		return false;
	}

	public boolean hasConditionProperty(){
		int indexOfBy = getIndexOfBy();

		if(indexOfBy != -1
				&& null != getConditionProperty()){
			return true;
		}

		return false;
	}

}
