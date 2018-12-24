package com.skysean.mybatis.mapper;

import com.skysean.mybatis.mapper.user.UserDO;
import com.skysean.mybatis.mapper.user.UserMapper;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SelectTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  public void selectOne(){
    UserDO user = userMapper.selectOne(1L);
    TestUtil.assertRow(user, 0);
  }

  @Test
  public void selectAll(){
    List<UserDO> users = userMapper.selectAll();
    for(int i = 0 ; i < TestUtil.ROWS.length; i ++){
      TestUtil.assertRow(users.get(i), i);
    }
  }

  @Test
  public void selectByName(){
    UserDO user = userMapper.selectByName("小苹果");
    Assert.assertNotNull(user);

    TestUtil.assertEquals(user, userMapper.selectOne(user.getId()));
  }

  @Test
  public void selectNameById(){
    String name = userMapper.selectNameById(1L);
    Assert.assertEquals(TestUtil.getRowColumn(0, 1), name);
  }

  @Test
  public void selectCreateTimeById(){
    Date date = userMapper.selectCreateTimeById(1L);
    Assert.assertEquals(TestUtil.getRowColumn(0, 2), TestUtil.dateToString(date));
  }

  @Test
  public void exists(){
    Long exists = userMapper.exists(1L);
    Assert.assertEquals(new Long(1), exists);
  }

}
