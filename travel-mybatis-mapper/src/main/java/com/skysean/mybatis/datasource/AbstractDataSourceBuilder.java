package com.skysean.mybatis.datasource;

import org.springframework.core.env.Environment;

/**
 * 描述：数据源构造抽象类
 * @author skysean
 */
public abstract class AbstractDataSourceBuilder implements DataSourceBuilder{

    private PropertyLoader propertyLoader;

    public AbstractDataSourceBuilder(Environment env){
        this.propertyLoader = new PropertyLoader(env); 
    }
    
    protected PropertyLoader getPropertyLoader(){
    	return propertyLoader;
    }

    /**
     * 描述：属性加载器
     * @author skysean
     */
    public static class PropertyLoader{
    	private static final String KEY_PREFIX = "spring.datasource.";//数据源配置前缀
        private Environment env;
        public PropertyLoader(Environment env){
            this.env = env;
        }

        public Integer getInt(String property){
            String res = get(property);
            try{
                return Integer.parseInt(res);
            }catch (Exception e){
                return null;
            }
        }

        public Boolean getBoolean(String property){
            String res = get(property);
            try{
                return Boolean.parseBoolean(res);
            }catch (Exception e){
                return null;
            }
        }

        public String get(String property){
            return env.getProperty(getKey(property));
        }

        private String getKey(String property){
        	return KEY_PREFIX + property;
        }
    }
}
