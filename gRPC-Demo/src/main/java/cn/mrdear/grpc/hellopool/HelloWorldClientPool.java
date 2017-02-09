package cn.mrdear.grpc.hellopool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import cn.mrdear.grpc.common.WorkCallBack;

/**
 * @author Niu Li
 * @since 17-2-8
 */
public class HelloWorldClientPool {

  private static GenericObjectPool<HelloWorldClientSingle> objectPool = null;

  static {
    // 连接池的配置
    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    // 池中的最大连接数
    poolConfig.setMaxTotal(8);
    // 最少的空闲连接数
    poolConfig.setMinIdle(0);
    // 最多的空闲连接数
    poolConfig.setMaxIdle(8);
    // 当连接池资源耗尽时,调用者最大阻塞的时间,超时时抛出异常 单位:毫秒数
    poolConfig.setMaxWaitMillis(-1);
    // 连接池存放池化对象方式,true放在空闲队列最前面,false放在空闲队列最后
    poolConfig.setLifo(true);
    // 连接空闲的最小时间,达到此值后空闲连接可能会被移除,默认即为30分钟
    poolConfig.setMinEvictableIdleTimeMillis(1000L * 60L * 30L);
    // 连接耗尽时是否阻塞,默认为true
    poolConfig.setBlockWhenExhausted(true);
    // 连接池创建
    objectPool = new GenericObjectPool<>(new HelloWorldFactory(), poolConfig);
  }

  /**
   * 从连接池获取对象
   */
  private static HelloWorldClientSingle borrowObject(){
    try {
      HelloWorldClientSingle clientSingle = objectPool.borrowObject();
      System.out.println("总创建线程数"+objectPool.getCreatedCount());
      return clientSingle;
    } catch (Exception e) {
      e.printStackTrace();
    }
    //连接池失败则主动创建
    return createClient();
  }

  /**
   * 当连接池异常,则主动创建对象
   */
  private static HelloWorldClientSingle createClient(){
    return new HelloWorldClientSingle("127.0.0.1", 55001);
  }

  /**
   * 执行器
   * @param workCallBack 主要服务内容
   */
  public static Runnable execute(WorkCallBack<HelloWorldClientSingle> workCallBack){
    return () -> {
      HelloWorldClientSingle client = borrowObject();
      try {
       workCallBack.callback(client);
      } finally {
        /** 将连接对象返回给连接池 */
        objectPool.returnObject(client);
      }
    };
  }

}
