package com.skysean.mybatis.statement.scanner;

import java.util.Set;

/**
 * 描述：mapper接口扫描器
 * @author skysean
 */
public interface MapperScanner {

	/**
	 * 描述：扫描包路径获取mapper接口
	 * @author skysean
	 * @param packages mapper所在包
	 */
	Set<Class<?>> scan(String[] packages);
}
