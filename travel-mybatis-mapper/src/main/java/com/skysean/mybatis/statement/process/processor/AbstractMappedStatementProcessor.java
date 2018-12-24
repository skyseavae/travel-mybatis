package com.skysean.mybatis.statement.process.processor;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.skysean.mybatis.exception.TravelMybatisException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;

/**
 * 描述：MappedStatement处理器抽象类
 * @author skysean
 */
public abstract class AbstractMappedStatementProcessor implements MappedStatementProcessor{

    @Override
    public void process(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant) {

        SqlSource sqlSource = buildSqlSource(method, entityClass, mapperBuilderAssistant);

        putMappedStatementIntoConfiguration(method, sqlSource, entityClass, mapperBuilderAssistant);
    }

    /**
     * 描述：构造mybatis需要的sqlSource
     * @author skysean
     * @param entityClass 实体类
     * @param mapperBuilderAssistant mapper构造辅助类
     * @return SqlSource
     */
    protected abstract SqlSource buildSqlSource(Method method, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant);

    /**
     * 描述：MappedStatement写入Configuration
     * @author skysean
     * @param method mapper接口方法
     * @param sqlSource mybatis的sqlSource
     * @param entityClass 实体类
     * @param mapperBuilderAssistant mapper构造辅助类
     */
    protected void putMappedStatementIntoConfiguration(Method method, SqlSource sqlSource, Class<?> entityClass, MapperBuilderAssistant mapperBuilderAssistant){

        //通过mapper接口方法获取对应的返回值类型
        Class<?> resultType = getResultType(method);

        //判断resultType是否已经存在对应的resultMap
        String resultMap = getExistsResultMapId(resultType, mapperBuilderAssistant);

        //如果存在resultMap时，则不使用resultType
        if(null != resultMap){
            resultType = null;
        }

        //写入MappedStatement到Configuration
        mapperBuilderAssistant.addMappedStatement(method.getName(), sqlSource, StatementType.PREPARED, sqlCommandType(),
                null, null, null, null, resultMap, resultType, null, false, true, false, NoKeyGenerator.INSTANCE, null, null, null,
                mapperBuilderAssistant.getLanguageDriver(null), null);
    }

    /**
     * 描述：通过mapper接口方法获取对应的返回值类型
     * @author skysean
     * @param method mapper接口方法
     * @return resultType
     */
    private Class<?> getResultType(Method method){
        Class<?> returnType = method.getReturnType();
        if(isBasicType(returnType)){
            return method.getReturnType();
        }else if(Iterable.class.isAssignableFrom(returnType)){
            return getClassByGenericity(method);
        }else if("void".equals(returnType.getName())){
            return null;
        }else{
            return method.getReturnType();
        }
    }

    /**
     * 描述：通过resultType获取已经存在的resultMap
     * @author skysean
     * @param resultType mapper接口方法对应的返回值类型
     * @param mapperBuilderAssistant mapper构造辅助类
     */
    private String getExistsResultMapId(Class<?> resultType, MapperBuilderAssistant mapperBuilderAssistant){

        if(null == resultType){
            return null;
        }

        String resultMapId = mapperBuilderAssistant.applyCurrentNamespace(resultType.getSimpleName(), false);
        if(mapperBuilderAssistant.getConfiguration().hasResultMap(resultMapId)){
            return resultMapId;
        }

        return null;
    }

    /**
     * 描述：获取泛型返回值的具体类型
     * @author skysean
     * @param method mapper接口对应方法
     * @return 泛型返回值的具体类型
     */
    private Class<?> getClassByGenericity(Method method){
        Type type = method.getGenericReturnType();
        if (type instanceof ParameterizedType) {
            Type[] typesto = ((ParameterizedType) type).getActualTypeArguments();
            try {
                return Class.forName(typesto[0].getTypeName());
            } catch (ClassNotFoundException e) {
                throw new TravelMybatisException("接口返回值获取失败, class: " + method.getDeclaringClass().getName() + ", method:" + method.getName());
            }
        }else{
            throw new TravelMybatisException("接口返回值类型不支持, class: " + method.getDeclaringClass().getName() + ", method:" + method.getName());
        }
    }

    /**
     * 描述：sql命令类型
     * @author skysean
     */
    protected abstract SqlCommandType sqlCommandType();

    /**
     * 描述：当前class是否是基础类型
     * @author skysean
     */
    private boolean isBasicType(Class<?> clazz) {
        return clazz.isPrimitive() || Object.class.equals(clazz);
    }
}
