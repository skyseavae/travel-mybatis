package travel.mybatis.samples;

import com.skysean.mybatis.mapper.CommonMapper;
import java.util.Date;

public interface UserMapper extends CommonMapper<UserDO, Long> {

  UserDO selectByName(String name);

  String selectNameById(long id);

  Date selectCreateTimeById(long id);

  int updateName(String name, long id);

  int updateCreateTimeByName(Date createTime, String name);

  int deleteByName(String name);
}
