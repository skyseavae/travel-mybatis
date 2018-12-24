package com.skysean.mybatis.mapper;

import com.skysean.mybatis.mapper.user.UserDO;
import com.skysean.mybatis.mapper.user.UserMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  @Transactional
  @Rollback
  public void insertOne(){

    UserDO user = new UserDO();
    user.setName("insert one");
    user.setCreateTime(new Date());

    userMapper.insert(user);

    assertInsert(user);
  }

  private  void assertInsert(UserDO insert){
    Assert.assertNotEquals(insert.getId(), 0);
    UserDO saved = userMapper.selectOne(insert.getId());
    TestUtil.assertEquals(insert, saved);
  }

  @Test
  @Transactional
  @Rollback
  public void multiInsert(){

    List<UserDO> users = new ArrayList<>();
    for(int i = 0; i < 10; i ++){
      UserDO user = new UserDO();
      user.setName("multi insert" + i);
      user.setCreateTime(new Date());
      users.add(user);
    }
    userMapper.multiInsert(users);

    users.forEach(userDO -> assertInsert(userDO));
  }
}
