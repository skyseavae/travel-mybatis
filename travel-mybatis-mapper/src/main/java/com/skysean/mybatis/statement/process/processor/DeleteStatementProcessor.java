package com.skysean.mybatis.statement.process.processor;

import com.skysean.mybatis.statement.entity.EntityResolver;
import com.skysean.mybatis.statement.sqlsource.utils.SqlSourceUtil;
import com.skysean.mybatis.statement.standard.SqlStandardConstants;
import com.skysean.mybatis.statement.standard.SqlStandardResolver;
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
 * 描述：处理delete操作
 * @author skysean
 */
public class DeleteStatementProcessor extends AbstractMappedStatementProcessor{

    @Override
    protected SqlSource buildSqlSource(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {
        if(SqlStandardConstants.DELETE.equals(method.getName())){
            return structureDeleteByIdSqlSource(method, entityClass, mapperBuilderAssistant.getConfiguration());
        }

        return structureSqlSource(method, entityClass, mapperBuilderAssistant.getConfiguration());
    }

    private SqlSource structureDeleteByIdSqlSource(Method method, Class<?> entityClass, Configuration configuration){

        EntityResolver entityResolver = new EntityResolver(entityClass);
        Field byField = entityResolver.getIdProperty();

        return structureSqlSource(entityResolver.getTableName(), byField, configuration);
    }

    private SqlSource structureSqlSource(Method method, Class<?> entityClass, Configuration configuration) {

        SqlStandardResolver sqlStandardResolver = new SqlStandardResolver(method, entityClass, SqlCommandType.DELETE);

        Field conditionField = sqlStandardResolver.getConditionProperty();

        return structureSqlSource(sqlStandardResolver.getTableName(), conditionField, configuration);
    }

    private SqlSource structureSqlSource(String tableName, Field byField, Configuration configuration) {

        String byColumn = SqlSourceUtil.getColumnName(byField);

        String sql = "delete from " + tableName
                + " where " + byColumn + "=?";

        List<ParameterMapping> parameterMappings = new ArrayList<>();
        ParameterMapping byParameterMapping = new ParameterMapping.Builder(configuration, byField.getName(),
                byField.getType()).mode(ParameterMode.IN).jdbcTypeName(byColumn).build();
        parameterMappings.add(byParameterMapping);

        return new StaticSqlSource(configuration, sql, parameterMappings);
    }

    @Override
    protected SqlCommandType sqlCommandType() {
        return SqlCommandType.DELETE;
    }

    @Override
    public boolean canHandle(Method method, Class<?> entityClass) {
        String name = method.getName();
        if(name.startsWith(SqlStandardConstants.DELETE_BY)){
            return true;
        }
        if(name.equals(SqlStandardConstants.DELETE)){
            return true;
        }
        return false;
    }
}
