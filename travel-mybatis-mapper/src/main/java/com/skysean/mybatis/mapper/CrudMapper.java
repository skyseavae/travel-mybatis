package com.skysean.mybatis.mapper;

import java.io.Serializable;
import java.util.List;

import com.skysean.mybatis.mapper.annotation.NoMapperBean;

/**
 * 描述：处理crud操作的mapper接口
 * @author skysean
 */
@NoMapperBean
public interface CrudMapper<T, ID extends Serializable> extends Mapper<T, ID>{
	
	<S extends T> void insert(S entity);
	
	<S extends T> void multiInsert(List<S> entities);
	
	<S extends T> int update(S entity);
	
	T selectOne(ID id);
	
	ID exists(ID id);
	
	List<T> selectAll();
	
	long count();
	
	int delete(ID id);
}