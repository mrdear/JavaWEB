package cn.mrdear.grpc.clientProcess;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;

/**
 * 客户端拦截器
 * @author Niu Li
 * @date 2017/2/4
 */
//ClientInterceptor接口是针对ClientCall的创建进行拦截
public class ClientInterruptImpl implements ClientInterceptor {
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
                                                               CallOptions callOptions, Channel next) {
        //创建client
        System.out.println("创建client1");
        ClientCall<ReqT,RespT> clientCall = next.newCall(method,callOptions);
        return new ForwardingClientCall<ReqT, RespT>() {
            @Override
            protected ClientCall<ReqT, RespT> delegate() {
                return clientCall;
            }
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                System.out.println("拦截器1,在此可以对header参数进行修改");
                Metadata.Key<String> token = Metadata.Key.of("token",Metadata.ASCII_STRING_MARSHALLER);
                headers.put(token,"123456");
                //包装回调监听,使其也能拦截
                Listener<RespT> forwardListener = new ForwardingClientCallListener.
                        SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        Metadata.Key<String> token = Metadata.Key.of("token",Metadata.ASCII_STRING_MARSHALLER);
                        if (!"123456".equals(headers.get(token))){
                            System.out.println("返回参数无token,关闭该链接");
                            super.onClose(Status.DATA_LOSS,headers);
                        }
                        super.onHeaders(headers);
                    }
                };
                super.start(forwardListener, headers);
            }
        };
    }
}
