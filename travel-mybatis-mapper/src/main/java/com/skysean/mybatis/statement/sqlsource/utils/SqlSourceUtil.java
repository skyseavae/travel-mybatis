package com.skysean.mybatis.statement.sqlsource.utils;

import com.skysean.mybatis.statement.entity.EntityResolver;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 描述：mybatis SqlSource工具类
 * @author skysean
 */
public class SqlSourceUtil {
	
	public static String getTableName(Class<?> entityClass) {
		return new EntityResolver(entityClass).getTableName();
	}
	
	public static String getColumnName(Field field) {
		Column column = field.getAnnotation(Column.class);
		if(null == column) {
			return field.getName();
		}
		
		if(null == column.name() || column.name().trim().length() < 1) {
			return field.getName();
		}
		
		return column.name();
	}
	
    public static boolean isAutoIncrementId(Field field) {

        if (!field.isAnnotationPresent(Id.class)) {
            return false;
        }

        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (null == generatedValue) {
            return false;
        }

        return generatedValue.strategy() == GenerationType.IDENTITY;
    }
    
    public static boolean isStaticField(Field field){
    	return Modifier.isStatic(field.getModifiers());
    }
}
