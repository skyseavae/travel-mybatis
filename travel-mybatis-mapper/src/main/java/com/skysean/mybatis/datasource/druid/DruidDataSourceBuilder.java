package com.skysean.mybatis.datasource.druid;

import java.lang.reflect.Field;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.core.env.Environment;
import com.alibaba.druid.pool.DruidDataSource;
import com.skysean.mybatis.datasource.AbstractDataSourceBuilder;

/**
 * 描述：druid数据源构造
 * @author skysean
 */
public class DruidDataSourceBuilder extends AbstractDataSourceBuilder {

    public DruidDataSourceBuilder(Environment env) {
		super(env);
	}
    
	@Override
	public DataSource build() throws SQLException{
		
        DruidDataSourceProperties druidDataSourceProperties = loadProperties();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(druidDataSourceProperties.getDriver());
        dataSource.setUrl(druidDataSourceProperties.getUrl());
        dataSource.setUsername(druidDataSourceProperties.getUsername());
        dataSource.setPassword(druidDataSourceProperties.getPassword());
        dataSource.setMaxActive(druidDataSourceProperties.getMaxActive());
        dataSource.setInitialSize(druidDataSourceProperties.getInitialSize());
        dataSource.setMinIdle(druidDataSourceProperties.getMinIdle());
        dataSource.setMaxWait(druidDataSourceProperties.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(druidDataSourceProperties.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidDataSourceProperties.getMinEvictableIdleTimeMillis());
        dataSource.setUseUnfairLock(druidDataSourceProperties.isUseUnfairLock());
        dataSource.setValidationQuery(druidDataSourceProperties.getValidationQuery());
        dataSource.setTestWhileIdle(druidDataSourceProperties.isTestWhileIdle());
        dataSource.setTestOnBorrow(druidDataSourceProperties.isTestOnBorrow());
        dataSource.setTestOnReturn(druidDataSourceProperties.isTestOnReturn());
        dataSource.setPoolPreparedStatements(druidDataSourceProperties.isPoolPreparedStatements());
        dataSource.setMaxOpenPreparedStatements(druidDataSourceProperties.getMaxOpenPreparedStatements());
        dataSource.setFilters(druidDataSourceProperties.getFilters());

        try {
            dataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSource;
	}

	/**
	 * 描述：加载druid数据源属性
	 * @author skysean
   * @return druid数据源属性
	 */
	private DruidDataSourceProperties loadProperties(){
    	PropertyLoader propertyLoader = getPropertyLoader();
    	
        DruidDataSourceProperties druidDataSourceProperties = new DruidDataSourceProperties();
        Field[] declaredFields = DruidDataSourceProperties.class.getDeclaredFields();
        for (Field declaredField : declaredFields){
            declaredField.setAccessible(true);
            String name = declaredField.getName();

            Object value = null;
            if(declaredField.getType() == String.class){
                value = propertyLoader.get(name);
            }else if(declaredField.getType() == boolean.class
                    || declaredField.getType() == Boolean.class){
                value = propertyLoader.getBoolean(name);
            }else if(declaredField.getType() == int.class
                    || declaredField.getType() == Integer.class){
                value = propertyLoader.getInt(name);
            }

            if(null != value){
                try {
                    declaredField.set(druidDataSourceProperties, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }

        return druidDataSourceProperties;
	}
}
