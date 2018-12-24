package com.skysean.mybatis.statement.process.processor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.StaticSqlSource;
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
 * 描述：处理insert
 * @author skysean
 */
public class InsertStatementProcessor extends AbstractMappedStatementProcessor {
    @Override
    protected SqlSource buildSqlSource(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {
        return new StaticSqlSource(mapperBuilderAssistant.getConfiguration(), structureInsertSql(entityClass),
                structureInsertParameterMappings(mapperBuilderAssistant.getConfiguration(), entityClass));
    }

    @Override
    protected void putMappedStatementIntoConfiguration(Method method, SqlSource sqlSource, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {
        EntityResolver entityResolver = new EntityResolver(entityClass);

        mapperBuilderAssistant.addMappedStatement(method.getName(), sqlSource, StatementType.PREPARED,
                SqlCommandType.INSERT, null, null, null, null, null, null, null, false, true, false,
                entityResolver.getKeyGenerator(), entityResolver.getIdProperty().getName(), entityResolver.getIdColumn(), null,
                mapperBuilderAssistant.getLanguageDriver(null), null);
    }

    private String structureInsertSql(Class<?> entityClass) {

        String tableName = SqlSourceUtil.getTableName(entityClass);
        StringBuilder builder = new StringBuilder("insert into " + tableName + " ");

        StringBuilder fieldBuilder = new StringBuilder("(");
        StringBuilder markBuilder = new StringBuilder("(");
        Field[] declaredFields = entityClass.getDeclaredFields();

        for (Field f : declaredFields) {
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            if (isAutoIncrementId(f)) {
                continue;
            }
            fieldBuilder.append(SqlSourceUtil.getColumnName(f) + ",");
            markBuilder.append("?,");
        }
        builder.append(fieldBuilder.substring(0, fieldBuilder.length() - 1) + ")");
        builder.append(" values ");
        builder.append(markBuilder.substring(0, markBuilder.length() - 1) + ")");

        return builder.toString();
    }

    private List<ParameterMapping> structureInsertParameterMappings(Configuration configuration, Class<?> entityClass) {

        List<ParameterMapping> parameterMappings = new ArrayList<>();
        Field[] declaredFields = entityClass.getDeclaredFields();

        for (Field f : declaredFields) {
            boolean isStatic = Modifier.isStatic(f.getModifiers());
            if (isStatic) {
                continue;
            }
            if (isAutoIncrementId(f)) {
                continue;
            }
            ParameterMapping parameterMapping = new ParameterMapping.Builder(configuration, f.getName(), f.getType())
                    .mode(ParameterMode.IN).jdbcTypeName(SqlSourceUtil.getColumnName(f)).build();
            parameterMappings.add(parameterMapping);
        }

        return parameterMappings;
    }

    private boolean isAutoIncrementId(Field field) {
    	return SqlSourceUtil.isAutoIncrementId(field);
    }

    @Override
    protected SqlCommandType sqlCommandType() {
        return SqlCommandType.INSERT;
    }

    @Override
    public boolean canHandle(Method method, Class<?> entityClass) {
        return method.getName().equals(SqlStandardConstants.INSERT);
    }
}
