package com.skysean.mybatis.statement.process.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.javassist.Modifier;

import com.skysean.mybatis.statement.process.processor.MappedStatementProcessor;
import com.skysean.mybatis.utils.ClassScanner;

/**
 * 描述：classpath的MappedStatementProcessor加载器
 * @author skysean
 */
public class ClassPathProcessorLoader implements ProcessorLoader{

	private static final String[] PACKAGES = new String[]{"com.skysean.mybatis.statement.process.processor"};
	
	@Override
	public List<MappedStatementProcessor> load() {
		
		ClassScanner classScanner = new ClassScanner();
		Set<Class<?>> classSet = classScanner.scan(PACKAGES);

		List<MappedStatementProcessor> processors = new ArrayList<>();
		for (Class<?> c : classSet) {
			//c实现 MappedStatementProcessor接口 同时 c不是抽象类
			if (MappedStatementProcessor.class.isAssignableFrom(c) && !Modifier.isAbstract(c.getModifiers())) {
				try {
					processors.add((MappedStatementProcessor)c.newInstance());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		return processors;
		
	}

}
