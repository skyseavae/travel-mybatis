package com.skysean.mybatis.mapper;

import com.skysean.mybatis.mapper.annotation.NoMapperBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import sun.util.resources.cldr.ms.CurrencyNames_ms;

/**
 * 描述：mapper接口工具类
 * @author skysean
 */
public class MapperUtil {

	/**
	 * 描述：判断传入的接口是不是mapper接口(是否继承自{@link com.skysean.mybatis.mapper.Mapper}, 同时没有被{@link NoMapperBean}注解标记)
	 * @author skysean
	 * @param mapper 接口
	 * @return true:是mapper接口， false:不是mapper接口
	 */
	public static boolean isMapper(Class<?> mapper) {
		return isMapper(mapper, Mapper.class);
	}

	/**
	 * 描述：判断传入的接口是不是mapper接口(是否继承自{@link com.skysean.mybatis.mapper.Mapper}, 同时没有被{@link NoMapperBean}注解标记)
	 * @author skysean
	 * @param mapper 接口
	 * @param superMapper 父类接口
	 * @return true:是mapper接口， false:不是mapper接口
	 */
	private static boolean isMapper(Class<?> mapper, Class<?> superMapper){
		
		if(mapper.isAnnotationPresent(NoMapperBean.class)){
			return false;
		}
		
		if(!Mapper.class.isAssignableFrom(superMapper)){
			return false;
		}
		
		return superMapper.isAssignableFrom(mapper);
	}

	/**
	 * 描述：通过mapper接口获取实体类
	 * @author skysean
	 * @param mapper 具体的mapper接口
	 * @return 数据库实体类
	 */
	public static Class<?> getEntityClass(Class<?> mapper){
		Type mapperInterface = getExtendsMapperInterface(mapper);
		Type DOType = ((ParameterizedType)mapperInterface).getActualTypeArguments()[0];
		try {
			return Class.forName(DOType.getTypeName());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 描述：以Type的形式返回本类直接实现的接口,包含了泛型参数信息
	 * @author skysean
	 * @return type形式的接口
	 */
	private static Type getExtendsMapperInterface(Class<?> mapper){
		Type[] interfaces = mapper.getGenericInterfaces();
		for(Type type : interfaces){
			if (type instanceof ParameterizedType) {
	            if(Mapper.class.isAssignableFrom((Class<?>) ((ParameterizedType) type).getRawType())){
	            	return type;
	            }  
	        }
		}
		return null;
	}

}
