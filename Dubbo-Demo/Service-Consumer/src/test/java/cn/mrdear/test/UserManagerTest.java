package cn.mrdear.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.mrdear.client.dto.UserDTO;
import cn.mrdear.consumer.manager.UserManager;

import javax.annotation.Resource;

/**
 * @author Niu Li
 * @since 2017/6/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserManagerTest {

  @Resource
  private UserManager userManager;


  @Test
  public void testFindById() {
    UserDTO userDTO = userManager.findById(1L);
    Assert.assertNotNull(userDTO);
  }
}
