package cn.mrdear.grpc.clientProcess;

import io.grpc.ForwardingServerCall;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.netty.util.internal.StringUtil;

/**
 * 服务端拦截器
 * @author Niu Li
 * @date 2017/2/4
 */
public class ServerInterruptImpl  implements ServerInterceptor{
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        System.out.println("执行server拦截器1,获取token");
        //获取客户端参数
        Metadata.Key<String> token = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
        String tokenStr = headers.get(token);
        if (StringUtil.isNullOrEmpty(tokenStr)){
            System.out.println("未收到客户端token,关闭此连接");
            call.close(Status.DATA_LOSS,headers);
        }
        //服务端写回参数
        ServerCall<ReqT, RespT> serverCall = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void sendHeaders(Metadata headers) {
                System.out.println("执行server拦截器2,写入token");
                headers.put(token,tokenStr);
                super.sendHeaders(headers);
            }
        };
        return next.startCall(serverCall,headers);
    }
}
