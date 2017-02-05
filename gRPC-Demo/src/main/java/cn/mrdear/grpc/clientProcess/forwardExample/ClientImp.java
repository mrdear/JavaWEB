package cn.mrdear.grpc.clientProcess.forwardExample;

/**
 * @author Niu Li
 * @date 2017/2/4
 */
public class ClientImp extends Client {
    @Override
    public void start(String say) {
        System.out.println(say);
    }
}
