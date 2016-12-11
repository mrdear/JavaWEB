package cn.mrdear.util.redis.calback;

/**
 * 模仿Spring ConnectionCallback写的模板接口
 * T为操作返回值  E为参数类型
 * @author Niu Li
 * @date 2016/12/8
 */
public interface WorkCallback<T,E> {
    /**
     * 具体执行策略
     * @param e 传入参数
     * @return 结果
     */
    T doWorkCallback(E e);
}
