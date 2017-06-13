package cn.mrdear.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.mrdear.client.dto.UserDTO;
import cn.mrdear.provider.service.UserServiceImpl;

import javax.annotation.Resource;

/**
 * @author Niu Li
 * @since 2017/6/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserServiceTest {

  @Resource
  private UserServiceImpl userService;

  @Test
  public void testFindById() {
    UserDTO userDTO = userService.findById(1L);
    Assert.assertNotNull(userDTO);
  }

}
