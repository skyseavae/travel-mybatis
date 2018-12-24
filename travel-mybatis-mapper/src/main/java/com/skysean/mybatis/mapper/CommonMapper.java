package com.skysean.mybatis.mapper;

import com.skysean.mybatis.mapper.annotation.NoMapperBean;

import java.io.Serializable;

/**
 * 描述：通用mapper接口
 * @author skysean
 */
@NoMapperBean
public interface CommonMapper<T, ID extends Serializable> extends CrudMapper<T, ID>{

}
