package travel.mybatis.samples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserMapper userMapper;

  @RequestMapping("/users")
  public Object users(){
    return userMapper.selectAll();
  }
}
