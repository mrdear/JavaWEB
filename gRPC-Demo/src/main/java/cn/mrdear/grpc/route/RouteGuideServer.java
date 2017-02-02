package cn.mrdear.grpc.route;

import java.io.IOException;
import java.util.List;

import cn.mrdear.route.Feature;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * @author Niu Li
 * @date 2017/2/2
 */
public class RouteGuideServer {
    private final int port;//服务端端口
    private final Server server;//服务器

    public RouteGuideServer(int port) throws IOException {
        this.port = port;
        //获取初始化数据
        List<Feature> features = RouteGuideUtil.parseFeatures(RouteGuideUtil.getDefaultFeaturesFile());
        //初始化Server参数
        server = ServerBuilder.forPort(port)
                              //添加指定服务
                               .addService(new RouteGuideService(features))
                               .build();
    }

    /**
     * 启动服务
     */
    public void start() throws IOException {
        server.start();
        System.out.println("Server started, listening on " + port);
        //程序退出时关闭资源
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            RouteGuideServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    /**
     * 关闭服务
     */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * 使得server一直处于运行状态
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        RouteGuideServer server = new RouteGuideServer(50051);
        server.start();
        server.blockUntilShutdown();
    }

}
