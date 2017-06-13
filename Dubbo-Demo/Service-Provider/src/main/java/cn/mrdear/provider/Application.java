package cn.mrdear.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Niu Li
 * @since 2017/6/12
 */
public class Application {
  public static void main(String[] args) throws IOException {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    context.start();
    System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
  }
}
