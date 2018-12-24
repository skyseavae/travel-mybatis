# Travel Mybatis

travel-mybatis 致力于在spring boot环境下，提供类似spring-data-repository的mybatis版本的通用CRUD操作，旨在去除繁重、重复、简单的代码编写，您只需要添加一些简单的配置，即可接入使用！

### 特性

**1，支持基本查询**

```java
	<S extends T> void insert(S entity);
	
	<S extends T> void multiInsert(List<S> entities);
	
	<S extends T> int update(S entity);
	
	T selectOne(ID id);
	
	ID exists(ID id);
	
	List<T> selectAll();
	
	long count();
	
	int delete(ID id);
```

**2，支持自定义查询**

```java
UserDO selectByName(String name);//select * from table where column=?

String selectNameById(long id);//select column1 from table where column2=?

Date selectCreateTimeById(long id);//select column1 from table where column2=?

int updateName(String name, long id);//update table set name=? where id=?

int updateCreateTimeByName(Date createTime, String name);//update table set name=? where name=?

int deleteByName(String name);//delete from table where name=?
```

### 构建

travel-mybatis使用Maven构建，JDK版本需要1.8+，Maven版本：3.0+，将代码clone到本地以后，在travel-mybatis目录下执行以下命令（使用idea，可以直接打开）：

```shell
mvn clean install -Dmaven.test.skip=true
```

### 使用

**1，添加依赖配置**

```xml
    <dependency>
      <groupId>com.skysean</groupId>
      <artifactId>travel-mybatis-mapper-starter</artifactId>
      <version>1.0.0</version>
    </dependency>
```

**2，配置nexus私服**

```xml
    <profile>
      <id>travel</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <repositories>
        <repository>
          <id>nexus</id>
          <name>private nexus</name>
          <url>http://nexus.skysean.com/nexus/content/groups/public/</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>
```

3，在配置文件中添加配置

```properties
#指定自定义mapper接口包目录
travel.mybatis.basePackages=travel.mybatis.samples
```

**注意：**项目需要支持mybatis，且在spring中注入有**SqlSessionFactory**，此框架只是为mybatis基础查询提供解决方案，出于对项目已有配置做到尽可能的少侵入，所以没有引入spring boot mybatis的自动化配置

**项目demo：**[travel-mybatis-samples](https://github.com/skyseavae/travel-mybatis/tree/master/travel-mybatis-samples)