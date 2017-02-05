package cn.mrdear.grpc.clientProcess.forwardExample;

/**
 * 用于包装Client到另一个Client
 * @author Niu Li
 * @date 2017/2/4
 */
public abstract class ForwardingClient extends Client{
    //要包装的对象
    protected abstract Client delegate();

    @Override
    public void start(String say) {
        delegate().start(say);
    }
}
