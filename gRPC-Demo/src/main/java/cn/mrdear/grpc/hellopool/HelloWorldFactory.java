package cn.mrdear.grpc.hellopool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * 连接池所需工厂
 * @author Niu Li
 * @since 17-2-8
 */
public class HelloWorldFactory extends BasePooledObjectFactory<HelloWorldClientSingle> {

  private String host = "127.0.0.1";

  private int port = 50051;

  @Override
  public HelloWorldClientSingle create() throws Exception {
    return new HelloWorldClientSingle(this.host,this.port);
  }

  @Override
  public PooledObject<HelloWorldClientSingle> wrap(HelloWorldClientSingle helloWorldClientSingle) {
    return new DefaultPooledObject<>(helloWorldClientSingle);
  }

  @Override
  public void destroyObject(PooledObject<HelloWorldClientSingle> p) throws Exception {
    p.getObject().shutdown();
    super.destroyObject(p);
  }
}
