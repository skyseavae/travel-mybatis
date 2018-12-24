package com.skysean.mybatis.statement.process.processor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.session.Configuration;

import com.skysean.mybatis.statement.entity.EntityResolver;
import com.skysean.mybatis.statement.sqlsource.utils.SqlSourceUtil;
import com.skysean.mybatis.statement.standard.SqlStandardConstants;

/**
 * 描述：处理exists
 * @author skysean
 */
public class ExistsStatementProcessor implements MappedStatementProcessor{

	@Override
	public void process(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {
		
        mapperBuilderAssistant.addMappedStatement(method.getName(), structureExistsSqlSource(method, entityClass, mapperBuilderAssistant.getConfiguration()), 
        		StatementType.PREPARED, SqlCommandType.SELECT,null, null, null, null, null, new EntityResolver(entityClass).getIdProperty().getType(), 
        		null, false, true, false, NoKeyGenerator.INSTANCE, null, null, null, mapperBuilderAssistant.getLanguageDriver(null), null);
        return;
	}
	
    private SqlSource structureExistsSqlSource(Method method, Class<?> entityClass, Configuration configuration) {

        EntityResolver entityResolver = new EntityResolver(entityClass);
        Field byProperty = entityResolver.getIdProperty();
        String byColumn = entityResolver.getIdColumn();

        String sql = "select " + entityResolver.getIdColumn() + " from " + SqlSourceUtil.getTableName(entityClass) + " where " + byColumn + "=? LIMIT 1";

        ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, byProperty.getName(),
                byProperty.getType()).mode(ParameterMode.IN).jdbcTypeName(byColumn).build();

        return new StaticSqlSource(configuration, sql, new ArrayList<ParameterMapping>() {
            private static final long serialVersionUID = 7997030961090897339L;
            {
                add(parameterMapping);
            }
        });
    }

	@Override
	public boolean canHandle(Method method, Class<?> entityClass) {
        return SqlStandardConstants.EXISTS.equals(method.getName());
	}

}
