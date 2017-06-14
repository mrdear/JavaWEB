package cn.mrdear.consumer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.mrdear.client.service.IUserService;

/**
 * @author Niu Li
 * @since 2017/6/14
 */
public class ApplicationStart {

  public static void main(String[] args) throws InterruptedException {
    ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "classpath:applicationContext.xml");
    IUserService service = (IUserService) ctx.getBean("userService");
    System.out.println(service.findById(1L));
  }

}
