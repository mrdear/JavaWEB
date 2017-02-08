package cn.mrdear.grpc.clientProcess.forwardExample;

/**
 * 一个简单的委托模式.
 * @author Niu Li
 * @date 2017/2/4
 */
public class InterceptTest {

    public static void main(String[] args) {
        Client client = new ClientImp();//主要想执行的方法
        //构造第一个拦截器
        Client intercept1 = new ForwardingClientImpl(client){
            @Override
            public void start(String say) {
                System.out.println("拦截器1");
                super.start(say);
            }
        };
        //构造第二个拦截器
        Client intercept2 = new ForwardingClientImpl(intercept1){
            @Override
            public void start(String say) {
                System.out.println("拦截器2");
                super.start(say);
            }
        };
        //执行主方法
        intercept2.start("这是要执行的方法");
    }
}
