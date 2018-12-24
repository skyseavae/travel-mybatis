package com.skysean.mybatis.mapper;

import com.skysean.mybatis.mapper.annotation.NoMapperBean;

import java.io.Serializable;

/**
 * 描述：mapper接口超类，所有mapper接口均需要继承此接口才能被扫描加载
 * @author skysean
 */
@NoMapperBean
public interface Mapper<T, ID extends Serializable> {

}
