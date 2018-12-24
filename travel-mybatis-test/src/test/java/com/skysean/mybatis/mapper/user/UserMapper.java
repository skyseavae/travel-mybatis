package com.skysean.mybatis.mapper.user;

import com.skysean.mybatis.mapper.CommonMapper;
import java.util.Date;

public interface UserMapper extends CommonMapper<UserDO, Long> {

UserDO selectByName(String name);//select * from table where column=?

String selectNameById(long id);//select column1 from table where column2=?

Date selectCreateTimeById(long id);//select column1 from table where column2=?

int updateName(String name, long id);//update table set name=? where id=?

int updateCreateTimeByName(Date createTime, String name);//update table set name=? where name=?

int deleteByName(String name);//delete from table where name=?
}
