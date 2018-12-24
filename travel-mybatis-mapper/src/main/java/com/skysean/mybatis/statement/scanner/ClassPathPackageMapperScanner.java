package com.skysean.mybatis.statement.scanner;

import java.util.HashSet;
import java.util.Set;

import com.skysean.mybatis.mapper.MapperUtil;
import com.skysean.mybatis.utils.ClassScanner;

/**
 * 描述：classpath的mapper接口扫描器
 * @author skysean
 */
public class ClassPathPackageMapperScanner implements MapperScanner {

	@Override
	public Set<Class<?>> scan(String[] packages){

		ClassScanner classScanner = new ClassScanner();
		Set<Class<?>> classSet = classScanner.scan(packages);

		Set<Class<?>> mapperClassSet = new HashSet<>();
		for (Class<?> c : classSet) {
			if (MapperUtil.isMapper(c)) {
				mapperClassSet.add(c);
			}
		}
		
		return mapperClassSet;
	}
}
