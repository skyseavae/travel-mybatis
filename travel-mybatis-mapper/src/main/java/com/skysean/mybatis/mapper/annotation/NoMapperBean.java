package com.skysean.mybatis.mapper.annotation;

import java.lang.annotation.*;

/**
 * 描述：标记不需要被扫描加载的mapper接口
 * @author skysean
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface NoMapperBean {

}
