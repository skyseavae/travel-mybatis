package com.skysean.mybatis.mapper;

import com.skysean.mybatis.mapper.user.UserDO;
import com.skysean.mybatis.mapper.user.UserMapper;
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
public class DeleteTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  @Transactional
  @Rollback
  public void delete(){
    int deleted = userMapper.delete(1L);
    Assert.assertEquals(1, deleted);

    UserDO user = userMapper.selectOne(1L);
    Assert.assertNull(user);
  }

  @Test
  @Transactional
  @Rollback
  public void deleteByName(){
    int deleted = userMapper.deleteByName("小苹果");
    Assert.assertEquals(1, deleted);

    UserDO user = userMapper.selectByName("小苹果");
    Assert.assertNull(user);
  }
}
