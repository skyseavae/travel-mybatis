package com.skysean.mybatis.datasource.druid;

/**
 * 描述：druid数据源属性
 * @author skysean
 */
public class DruidDataSourceProperties {

    private String driver;

    private String url;

    private String username;

    private String password;

    private int maxActive = 300;//最大连接数

    private int initialSize = 20; //初始连接数

    private int minIdle = 10;//最小连接

    private long maxWait = 60000L;//最大等待时间

    private long timeBetweenEvictionRunsMillis = 60000L;//间隔多久进行检测,关闭空闲连接

    private long minEvictableIdleTimeMillis = 300000L;//一个连接最小生存时间

    private boolean useUnfairLock = true;//是否使用非公平锁

    private String validationQuery = "select 1";//检测是否有效的sql

    private boolean testWhileIdle = true;//建议配置为true，不影响性能，并且保证安全性,申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。

    private boolean testOnBorrow = false;//申请连接时执行validationQuery检测连接是否有效，配置为true会降低性能

    private boolean testOnReturn = false;//归还连接时执行validationQuery检测连接是否有效，配置为true会降低性能

    private boolean poolPreparedStatements = true;// 打开PSCache,并指定每个连接的PSCache大小启用poolPreparedStatements后，PreparedStatements 和CallableStatements 都会被缓存起来复用，即相同逻辑的SQL可以复用一个游标，这样可以减少创建游标的数量。

    private int maxOpenPreparedStatements = 20;//最大缓存数量

    private String filters;//过滤器

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public boolean isUseUnfairLock() {
        return useUnfairLock;
    }

    public void setUseUnfairLock(boolean useUnfairLock) {
        this.useUnfairLock = useUnfairLock;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxOpenPreparedStatements() {
        return maxOpenPreparedStatements;
    }

    public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
        this.maxOpenPreparedStatements = maxOpenPreparedStatements;
    }
}
