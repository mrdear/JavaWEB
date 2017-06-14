package cn.mrdear.provider;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Niu Li
 * @since 2017/6/14
 */
public class ApplicationStart {

  public static void main(String[] args) throws InterruptedException {
    ApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    System.out.println("server start...");
  }

}
