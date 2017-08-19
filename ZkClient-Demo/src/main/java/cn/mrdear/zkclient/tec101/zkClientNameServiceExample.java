package cn.mrdear.zkclient.tec101;

import org.I0Itec.zkclient.ZkClient;

import cn.mrdear.zkclient.conf.ZkClientConf;

/**
 * zk的命名服务,参考博文 http://www.hollischuang.com/archives/1595
 * @author Quding Ding
 * @since 2017/8/19
 */
public class zkClientNameServiceExample {

  private static final ZkClient ZK_CLIENT = new ZkClient(ZkClientConf.zkHost, ZkClientConf.zkTimeOut);


  private static final String nameServicePath = "/quding/nameService/name";

  /**
   * 每次拿到id都需要创建相应的节点,再删除,适合读多写少的环境下
   * @return id
   */
  public Long getUUID() {
    final String sequential = ZK_CLIENT.createPersistentSequential(nameServicePath, "id");
    ZK_CLIENT.delete(sequential);
    return Long.valueOf(sequential.substring(nameServicePath.length()));
  }


  public static void main(String[] args) {
    zkClientNameServiceExample example = new zkClientNameServiceExample();
    System.out.println(example.getUUID());
  }

}
