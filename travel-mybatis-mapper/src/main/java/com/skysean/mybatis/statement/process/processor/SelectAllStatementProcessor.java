package com.skysean.mybatis.statement.process.processor;

import java.lang.reflect.Method;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.session.Configuration;

import com.skysean.mybatis.statement.sqlsource.utils.SqlSourceUtil;
import com.skysean.mybatis.statement.standard.SqlStandardConstants;

/**
 * 描述：处理select all
 * @author skysean
 */
public class SelectAllStatementProcessor implements MappedStatementProcessor{

	@Override
	public void process(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {
        mapperBuilderAssistant.addMappedStatement(method.getName(), structureSelectAllSqlSource(method, entityClass, mapperBuilderAssistant.getConfiguration()), 
        		StatementType.PREPARED, SqlCommandType.SELECT,null, null, null, null, entityClass.getSimpleName(), null, 
        		null, false, true, false, NoKeyGenerator.INSTANCE, null, null, null, mapperBuilderAssistant.getLanguageDriver(null), null);
	}

    public SqlSource structureSelectAllSqlSource(Method method, Class<?> entityClass, Configuration configuration){
        String sql = "select * from " + SqlSourceUtil.getTableName(entityClass);
        return new StaticSqlSource(configuration, sql);
    }
    
    
	@Override
	public boolean canHandle(Method method, Class<?> entityClass) {
        return SqlStandardConstants.SELECT_ALL.equals(method.getName());
	}

}
