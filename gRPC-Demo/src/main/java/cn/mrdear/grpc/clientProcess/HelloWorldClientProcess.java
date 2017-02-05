package cn.mrdear.grpc.clientProcess;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

/**
 * 按照hello world来分析整个客户端执行过程
 * @author Niu Li
 * @date 2017/1/28
 */
public class HelloWorldClientProcess {

    private final ManagedChannel channel; //一个gRPC信道
    private final GreeterGrpc.GreeterBlockingStub blockingStub;//阻塞/同步 存根

   //初始化信道和存根
    public HelloWorldClientProcess(String host, int port){

        this(ManagedChannelBuilder.forAddress(host, port)
                                  // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                                  // needing certificates.
                                  .usePlaintext(true));
    }

    //创建channel的时候添加上转换器
    private HelloWorldClientProcess(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.intercept(new ClientInterruptImpl()).build();
        //使用gzip压缩
        blockingStub = GreeterGrpc.newBlockingStub(channel).withCompression("gzip");
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    //客户端方法
    public  void greet(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            System.out.println("RPC调用失败:"+e.getMessage());
            return;
        }
        System.out.println("服务器返回信息:"+response.getMessage());
    }

    public static void main(String[] args) throws InterruptedException {
        HelloWorldClientProcess client = new HelloWorldClientProcess("127.0.0.1",50051);
        try {
            for(int i=0;i<5;i++){
                client.greet("world:"+i);
            }
        }finally {
            client.shutdown();
        }
    }
}
