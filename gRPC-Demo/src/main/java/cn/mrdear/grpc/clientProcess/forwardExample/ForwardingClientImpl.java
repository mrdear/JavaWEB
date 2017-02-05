package cn.mrdear.grpc.clientProcess.forwardExample;

/**
 * 一个简单的包装示例,必须要传入要包装的对象
 * @author Niu Li
 * @date 2017/2/4
 */
public class ForwardingClientImpl extends ForwardingClient{

    //被委托对象
    private final Client client;

    public ForwardingClientImpl(Client client) {
        this.client = client;
    }

    @Override
    protected Client delegate() {
        return client;
    }
}
