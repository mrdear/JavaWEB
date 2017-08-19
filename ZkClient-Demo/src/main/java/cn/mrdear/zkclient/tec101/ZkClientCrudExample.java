package cn.mrdear.zkclient.tec101;

import org.I0Itec.zkclient.ZkClient;

import cn.mrdear.zkclient.conf.ZkClientConf;

import java.util.List;

/**
 * 连接zk demo
 * @author Quding Ding
 * @since 2017/8/18
 */
public class ZkClientCrudExample {

  private static final ZkClient ZK_CLIENT = new ZkClient(ZkClientConf.zkHost, ZkClientConf.zkTimeOut);

  /**
   * 创建节点,节点存在则抛异常,一次只能创建一个目录节点,否则报错
   * @param path 路径
   * @param value 节点携带的数据
   */
  public void createNode(String path, String value) {
    ZK_CLIENT.createPersistent(path, value);
  }

  /**
   * 得到子节点,节点不存在直接报错
   * @param root 查找子节点
   */
  public List<String> children(String root) {
    return ZK_CLIENT.getChildren(root);
  }

  public static void main(String[] args) {
    ZkClientCrudExample example = new ZkClientCrudExample();
    example.createNode("/quding/nameService","命名服务");
  }

}
