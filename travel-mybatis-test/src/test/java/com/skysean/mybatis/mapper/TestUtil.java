package com.skysean.mybatis.mapper;

import com.skysean.mybatis.mapper.user.UserDO;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;

public class TestUtil {

  public static final String[] ROWS =
      {"1,小苹果,2018-10-30 20:54:49",
          "2,小番茄,2018-10-30 20:55:02",
          "3,小橘子,2018-10-30 20:55:13",
          "4,小西瓜,2018-10-30 20:55:26",
          "5,小桃子,2018-10-30 20:55:36",
          "6,小南瓜,2018-10-30 20:55:56"};

  public static String getRowColumn(int rowIndex, int columnIndex){
    return ROWS[rowIndex].split(",")[columnIndex];
  }

  public static void assertRow(UserDO user, int rowIndex){
    String[] rowColumns = ROWS[rowIndex].split(",");
    Assert.assertEquals(String.valueOf(user.getId()), rowColumns[0]);
    Assert.assertEquals(user.getName(), rowColumns[1]);
    Assert.assertEquals(TestUtil.dateToString(user.getCreateTime()), rowColumns[2]);
  }

  public static String dateToString(Date createTime){
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
  }


  public static void assertEquals(UserDO u0, UserDO u1){
    Assert.assertNotNull(u0);
    Assert.assertNotNull(u1);

    Assert.assertEquals(u0.getId(), u1.getId());
    Assert.assertEquals(u0.getName(), u1.getName());

    Assert.assertEquals(TestUtil.dateToString(u0.getCreateTime()), TestUtil.dateToString(u1.getCreateTime()));
  }
}
