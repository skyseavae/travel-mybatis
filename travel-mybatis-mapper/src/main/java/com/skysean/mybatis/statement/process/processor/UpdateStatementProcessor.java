package com.skysean.mybatis.statement.process.processor;

import com.skysean.mybatis.statement.entity.EntityResolver;
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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：处理update
 * @author skysean
 */
public class UpdateStatementProcessor extends AbstractMappedStatementProcessor{
    @Override
    protected SqlSource buildSqlSource(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {

        if(SqlStandardConstants.UPDATE.equals(method.getName())){
            return structureUpdateEntitySqlSource(method, entityClass, mapperBuilderAssistant.getConfiguration());
        }

        return structureSqlSource(method, entityClass, mapperBuilderAssistant.getConfiguration());
    }
    
    private SqlSource structureUpdateEntitySqlSource(Method method, Class<?> entityClass, Configuration configuration){

        List<ParameterMapping> parameterMappings = new ArrayList<>();
        StringBuilder updateColumns = new StringBuilder();

        EntityResolver entityResolver = new EntityResolver(entityClass);

        Field[] declaredFields = entityClass.getDeclaredFields();
        for(Field f : declaredFields){
            if(Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            String column = entityResolver.getColumn(f.getName());
            updateColumns.append(column + "=?,");
            ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, f.getName(),
                    f.getType()).mode(ParameterMode.IN).jdbcTypeName(column).build();
            parameterMappings.add(parameterMapping);
        }

        String sql = "update " + entityResolver.getTableName() + " set " + updateColumns.subSequence(0, updateColumns.length() - 1)
                + " where " + entityResolver.getIdColumn() + "=?";

        Field idProperty = entityResolver.getIdProperty();
        ParameterMapping idParameterMapping = new ParameterMapping.Builder(configuration, idProperty.getName(),
                idProperty.getType()).mode(ParameterMode.IN).jdbcTypeName(entityResolver.getIdColumn()).build();
        parameterMappings.add(idParameterMapping);

        return new StaticSqlSource(configuration, sql, parameterMappings);
    }


    private SqlSource structureSqlSource(Method method, Class<?> entityClass, Configuration configuration) {

        SqlStandardResolver sqlStandardResolver = new SqlStandardResolver(method, entityClass, SqlCommandType.UPDATE);

        String updateColumn = sqlStandardResolver.getOperationColumn();
        Field updateField = sqlStandardResolver.getOperationProperty();

        Field byField = sqlStandardResolver.getConditionProperty();
        String byColumn = sqlStandardResolver.getConditionColumn();

        String sql = "update " + sqlStandardResolver.getTableName() + " set "
                + updateColumn + "=?"
                + " where " + byColumn + "=?";

        List<ParameterMapping> parameterMappings = new ArrayList<>();
        ParameterMapping byParameterMapping = new ParameterMapping.Builder(configuration, "param2",
                byField.getType()).mode(ParameterMode.IN).jdbcTypeName(byColumn).build();
        ParameterMapping updateParameterMapping = new ParameterMapping.Builder(configuration, "param1",
                updateField.getType()).mode(ParameterMode.IN).jdbcTypeName(updateColumn).build();
        parameterMappings.add(updateParameterMapping);
        parameterMappings.add(byParameterMapping);

        return new StaticSqlSource(configuration, sql, parameterMappings);
    }

    @Override
    protected SqlCommandType sqlCommandType() {
        return SqlCommandType.UPDATE;
    }

    @Override
    public boolean canHandle(Method method, Class<?> entityClass) {
        if(SqlStandardConstants.UPDATE.equals(method.getName())){
            return true;
        }

        SqlStandardResolver sqlStandardResolver = new SqlStandardResolver(method, entityClass, SqlCommandType.UPDATE);
        return method.getName().startsWith(SqlStandardConstants.UPDATE) && sqlStandardResolver.hasOperationAndConditionProperty();
    }
}
