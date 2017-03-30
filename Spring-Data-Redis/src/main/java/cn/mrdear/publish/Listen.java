package cn.mrdear.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * 消息订阅者
 * @author Niu Li
 * @since 2017/3/29
 */
@Component
public class Listen {

  private static Logger logger = LoggerFactory.getLogger(Listen.class);

  private CountDownLatch latch = new CountDownLatch(1);

  public void handleMsg(String message) {
    logger.info("reciver msg :" + message);
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }
}
