package cn.mrdear.grpc.common;

/**
 * 给模板类使用的接口
 * @author Niu Li
 * @since 17-2-8
 */
public interface WorkCallBack<S> {

  void callback(S s);
}
