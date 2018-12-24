package com.skysean.mybatis.statement.process.loader;

import java.util.List;

import com.skysean.mybatis.statement.process.processor.MappedStatementProcessor;

/**
 * 描述：内部processor加载器
 * @author skysean
 */
public interface ProcessorLoader {

	/**
	 * 描述：加载MappedStatementProcessor
	 * @author skysean
	 * @return MappedStatementProcessor集合
	 */
	List<MappedStatementProcessor> load();
}
