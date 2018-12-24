package com.skysean.mybatis.statement.process.processor;

import com.skysean.mybatis.statement.entity.EntityResolver;
import com.skysean.mybatis.statement.sqlsource.utils.SqlSourceUtil;
import com.skysean.mybatis.statement.standard.SqlStandardConstants;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：处理count查询
 * @author skysean
 */
public class CountStatementProcessor extends AbstractMappedStatementProcessor {
    @Override
    protected SqlSource buildSqlSource(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {
        String methodName = method.getName();

        if (SqlStandardConstants.COUNT.equals(methodName)) {
            return structureCountSqlSource(method, entityClass, mapperBuilderAssistant.getConfiguration());
        }

        return structureCountBySqlSource(method, entityClass, mapperBuilderAssistant.getConfiguration());
    }

    @Override
    protected SqlCommandType sqlCommandType() {
        return SqlCommandType.SELECT;
    }

    private SqlSource structureCountSqlSource(Method method, Class<?> entityClass, Configuration configuration) {
        String sql = "select count(*) from " + SqlSourceUtil.getTableName(entityClass);
        return new StaticSqlSource(configuration, sql);
    }

    private SqlSource structureCountBySqlSource(Method method, Class<?> entityClass, Configuration configuration) {

        EntityResolver entityResolver = new EntityResolver(entityClass);

        String byProperty = method.getName().replaceFirst(SqlStandardConstants.COUNT_BY, "");
        Field byField = entityResolver.getProperty(byProperty);
        String byColumn = entityResolver.getColumn(byProperty);
        String sql = "select count(*) from " + SqlSourceUtil.getTableName(entityClass) + " where "
                + byColumn + "=?";

        List<ParameterMapping> parameterMappings = new ArrayList<>();
        ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, byField.getName(),
                byField.getType()).mode(ParameterMode.IN).jdbcTypeName(byColumn).build();
        parameterMappings.add(parameterMapping);

        return new StaticSqlSource(configuration, sql, parameterMappings);
    }

    @Override
    public boolean canHandle(Method method, Class<?> entityClass) {

        String methodName = method.getName();

        if (SqlStandardConstants.COUNT.equals(methodName)) {
            return true;
        }
        if (methodName.startsWith(SqlStandardConstants.COUNT_BY)) {
            return true;
        }

        return false;
    }
}
