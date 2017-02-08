package cn.mrdear.grpc.hellopool;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

/**
 * @author Niu Li
 * @date 2017/1/28
 */
public class HelloWorldClientSingle {

    private final ManagedChannel channel; //一个gRPC信道
    private GreeterGrpc.GreeterBlockingStub greeterBlockingStub;//阻塞/同步 存根

   //初始化信道和存根
    public HelloWorldClientSingle(String host,int port){
      channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    //客户端方法
    public  void greet(String name){
        //需要用到存根时创建,不可复用
        greeterBlockingStub = GreeterGrpc.newBlockingStub(channel).withCompression("gzip");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = greeterBlockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            System.out.println("RPC调用失败:"+e.getMessage());
            return;
        }
        System.out.println("服务器返回信息:"+response.getMessage());
    }
}
