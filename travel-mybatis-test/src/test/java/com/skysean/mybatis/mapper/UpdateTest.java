package com.skysean.mybatis.mapper;


import com.skysean.mybatis.mapper.user.UserDO;
import com.skysean.mybatis.mapper.user.UserMapper;
import java.util.Date;
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
public class UpdateTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  @Transactional
  @Rollback
  public void update(){
    UserDO userDO = userMapper.selectOne(1L);
    userDO.setName("123456");
    Date date = new Date();
    userDO.setCreateTime(date);
    int update = userMapper.update(userDO);
    Assert.assertEquals(1, update);

    UserDO updated = userMapper.selectOne(1L);
    Assert.assertEquals("123456", updated.getName());
    Assert.assertEquals(TestUtil.dateToString(date), TestUtil.dateToString(updated.getCreateTime()));
  }

  @Test
  @Transactional
  @Rollback
  public void updateCreateTimeByName(){
    Date date = new Date();
    userMapper.updateCreateTimeByName(date, "小苹果");

    UserDO updated = userMapper.selectByName("小苹果");
    Assert.assertEquals(TestUtil.dateToString(date), TestUtil.dateToString(updated.getCreateTime()));
  }

}
