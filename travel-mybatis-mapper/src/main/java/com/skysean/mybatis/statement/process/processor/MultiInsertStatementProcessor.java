package com.skysean.mybatis.statement.process.processor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;

import com.skysean.mybatis.statement.entity.EntityResolver;
import com.skysean.mybatis.statement.sqlsource.utils.SqlSourceUtil;
import com.skysean.mybatis.statement.standard.SqlStandardConstants;

/**
 * 描述：处理批量insert
 * @author skysean
 */
public class MultiInsertStatementProcessor extends AbstractMappedStatementProcessor {
	
	private static final String ITEM = "item";
	private static final String COLLECTION_EXPRESSION = "list";
	
    @Override
    protected SqlSource buildSqlSource(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {
        
    	EntityResolver entityResolver = new EntityResolver(entityClass);
    	
        StringBuilder fieldBuilder = new StringBuilder("(");
        StringBuilder forEachBuilder = new StringBuilder("(");
        Field[] declaredFields = entityClass.getDeclaredFields();
        for (Field f : declaredFields) {
            if (SqlSourceUtil.isStaticField(f)) {
                continue;
            }
            if (SqlSourceUtil.isAutoIncrementId(f)) {
                continue;
            }
            fieldBuilder.append(SqlSourceUtil.getColumnName(f) + ",");
            forEachBuilder.append("#{" + ITEM + "." + f.getName() + "},");
        }
        String fieldSqlPart = fieldBuilder.substring(0, fieldBuilder.length() - 1) + ")";
        String forEachSqlPart = forEachBuilder.substring(0, forEachBuilder.length() - 1) + ")";
    	
        //insert into xxx() values部分
    	StaticTextSqlNode insertNode = new StaticTextSqlNode("insert into " + entityResolver.getTableName() + fieldSqlPart + " values " );
    	
    	//forEach部分
    	StaticTextSqlNode forEachNode = new StaticTextSqlNode(forEachSqlPart);
    	MixedSqlNode mixedSqlNode = new MixedSqlNode(new ArrayList<SqlNode>(){
			private static final long serialVersionUID = 1L;
			{
    			add(forEachNode);
    		}
    	});
    	
    	ForEachSqlNode forEachSql = new ForEachSqlNode(mapperBuilderAssistant.getConfiguration(), mixedSqlNode, COLLECTION_EXPRESSION, null, ITEM, "", "", ",");
    	MixedSqlNode res = new MixedSqlNode(new ArrayList<SqlNode>(){
			private static final long serialVersionUID = 1L;
			{
    			add(insertNode);
    			add(forEachSql);
    		}
    	});
    	
    	return new DynamicSqlSource(mapperBuilderAssistant.getConfiguration(), res);
    }

    @Override
    protected void putMappedStatementIntoConfiguration(Method method, SqlSource sqlSource, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {
        EntityResolver entityResolver = new EntityResolver(entityClass);

        mapperBuilderAssistant.addMappedStatement(method.getName(), sqlSource, StatementType.PREPARED,
                SqlCommandType.INSERT, null, null, null, entityClass, null, null, null, false, true, false,
                entityResolver.getKeyGenerator(), entityResolver.getIdProperty().getName(), entityResolver.getIdColumn(), null,
                mapperBuilderAssistant.getLanguageDriver(null), null);
    }

    @Override
    protected SqlCommandType sqlCommandType() {
        return SqlCommandType.INSERT;
    }

    @Override
    public boolean canHandle(Method method, Class<?> entityClass) {
        return SqlStandardConstants.MULTI_INSERT.equals(method.getName());
    }
}
