package com.skysean.mybatis.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 描述：数据源builder
 * @author skysean
 */
public interface DataSourceBuilder {

	/**
	 * 描述：构建数据源
	 * @author skysean
	 * @return 数据源
	 */
	DataSource build() throws SQLException;
}
