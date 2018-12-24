package com.skysean.mybatis.exception;

/**
 * 描述：travel-mybatis异常封装
 * @author skysean
 */
public class TravelMybatisException extends RuntimeException{

    public TravelMybatisException(String message){
        super(message);
    }

    public TravelMybatisException(String message, Throwable e){
        super(message, e);
    }
}
