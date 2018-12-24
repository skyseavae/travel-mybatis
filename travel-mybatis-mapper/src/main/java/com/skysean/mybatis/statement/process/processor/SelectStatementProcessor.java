package com.skysean.mybatis.statement.process.processor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import com.skysean.mybatis.statement.standard.SqlStandardConstants;
import com.skysean.mybatis.statement.standard.SqlStandardResolver;

/**
 * 描述：处理select
 * @author skysean
 */
public class SelectStatementProcessor extends AbstractMappedStatementProcessor{

    @Override
    protected SqlSource buildSqlSource(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {
        return structureSqlSource(method, entityClass, mapperBuilderAssistant.getConfiguration());
    }

    private SqlSource structureSqlSource(Method method, Class<?> entityClass, Configuration configuration) {

        SqlStandardResolver sqlStandardResolver = new SqlStandardResolver(method, entityClass, SqlCommandType.SELECT);

        String selectColumn = sqlStandardResolver.getOperationColumn();

        Field byField = sqlStandardResolver.getConditionProperty();
        String byColumn = sqlStandardResolver.getConditionColumn();

        String sql = "select " + (null == selectColumn ? "*" : selectColumn) + " from "
                + sqlStandardResolver.getTableName() + " where " + byColumn + "=?";

        ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, byField.getName(),
                byField.getType()).mode(ParameterMode.IN).jdbcTypeName(byColumn).build();

        return new StaticSqlSource(configuration, sql, new ArrayList<ParameterMapping>() {
            private static final long serialVersionUID = 7997030961090897339L;
            {
                add(parameterMapping);
            }
        });
    }

    @Override
    protected SqlCommandType sqlCommandType() {
        return SqlCommandType.SELECT;
    }

    @Override
    public boolean canHandle(Method method, Class<?> entityClass) {
        return method.getName().startsWith(SqlStandardConstants.SELECT) 
        		&& new SqlStandardResolver(method, entityClass, SqlCommandType.SELECT).hasConditionProperty();
    }
}
