package cn.mrdear.grpc.hellopool;

/**
 * @author Niu Li
 * @since 17-2-8
 */
public class HelloWorldClientTest {

  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      new Thread( HelloWorldClientPool.execute(clientSingle -> {
          clientSingle.greet("world");
      })).start();
    }
  }
}
