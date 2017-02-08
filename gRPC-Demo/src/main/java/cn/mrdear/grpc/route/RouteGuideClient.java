package cn.mrdear.grpc.route;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cn.mrdear.route.Feature;
import cn.mrdear.route.Point;
import cn.mrdear.route.Rectangle;
import cn.mrdear.route.RouteGuideGrpc;
import cn.mrdear.route.RouteNote;
import cn.mrdear.route.RouteSummary;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

/**
 * @author Niu Li
 * @date 2017/2/2
 */
public class RouteGuideClient {

    private final ManagedChannel channel;//grpc信道,需要指定端口和地址
    private final RouteGuideGrpc.RouteGuideBlockingStub blockingStub;//阻塞/同步存根
    private final RouteGuideGrpc.RouteGuideStub asyncStub;//非阻塞,异步存根


    public RouteGuideClient(String host,int port) {
        //创建信道
        channel = ManagedChannelBuilder.forAddress(host, port)
                                        .usePlaintext(true)
                                        .build();
        //创建存根
        blockingStub = RouteGuideGrpc.newBlockingStub(channel);
        asyncStub = RouteGuideGrpc.newStub(channel);
    }

    /**
     * 关闭方法
     */
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * 主方法中调用这一些函数的执行
     */
    public static void main(String[] args) throws InterruptedException {
        List<Feature> features;
        try {
            features = RouteGuideUtil.parseFeatures(RouteGuideUtil.getDefaultFeaturesFile());
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        RouteGuideClient client = new RouteGuideClient("localhost", 50051);
        try {
//            client.getFeature(409146138, -746188906);//成功案例
//            client.getFeature(0, 0);//失败案例
//            client.listFeatures(400000000, -750000000, 420000000, -730000000);
//            client.recordRoute(features, 10);
            CountDownLatch finishLatch = client.routeChat();

            if (!finishLatch.await(1, TimeUnit.MINUTES)) {
                System.out.println("routeChat can not finish within 1 minutes");
            }
        } finally {
            client.shutdown();
        }
    }

    //1.简单grpc
    public void getFeature(int lat,int lon){
        System.out.println("start getFeature");
        Point request = Point.newBuilder()
                             .setLatitude(lat)
                             .setLongitude(lon)
                             .build();
        Feature feature;
        try {
            //同步阻塞调用
            feature = blockingStub.getFeature(request);
            System.out.println("getFeature服务端返回 :" + feature);
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed " +e.getStatus());
        }
    }

    //2.服务端流式RPC
    public void listFeatures(int lowLat, int lowLon, int hiLat, int hiLon){
        System.out.println("start listFeatures");
        Rectangle request =
            Rectangle.newBuilder()
                     .setLo(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
                     .setHi(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
        Iterator<Feature> features;
        try {
            features = blockingStub.listFeatures(request);
            for (int i = 1; features.hasNext(); i++) {
                Feature feature = features.next();
                System.out.println("listFeatures服务端返回 :" + feature);
            }
        } catch (Exception e) {
            System.out.println("RPC failed " +e.getMessage());
        }
    }
//    客户端流式RPC
    public void recordRoute(List<Feature> features, int numPoints) throws InterruptedException {
        System.out.println("start recordRoute");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        //建一个应答者接受返回数据
        StreamObserver<RouteSummary> responseObserver = new StreamObserver<RouteSummary>() {
            @Override
            public void onNext(RouteSummary summary) {
                System.out.println("recordRoute服务端返回 :" + summary);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("RecordRoute Failed");
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("RecordRoute finish");
                finishLatch.countDown();
            }
        };
        //客户端写入操作
        StreamObserver<Point> requestObserver = asyncStub.recordRoute(responseObserver);
        Random random = new Random();
        try {
            for (int i = 0; i < numPoints; ++i) {
                int index = random.nextInt(features.size());
                Point point = features.get(index).getLocation();
                System.out.println("客户端写入point:" + point);
                requestObserver.onNext(point);

                Thread.sleep(random.nextInt(1000) + 500);
                if (finishLatch.getCount() == 0) {
                    return;
                }
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }
        //标识已经写完
        requestObserver.onCompleted();
        // Receiving happens asynchronously
        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            System.out.println("recordRoute can not finish within 1 minutes");
        }
    }
//    双向流式RPC
    public CountDownLatch routeChat() {
        System.out.println("start routeChat");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        StreamObserver<RouteNote> requestObserver =
                asyncStub.routeChat(new StreamObserver<RouteNote>() {
                    @Override
                    public void onNext(RouteNote note) {
                        System.out.println("服务端写回: " + note);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        System.out.println("RouteChat Failed:");
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Finished RouteChat");
                        finishLatch.countDown();
                    }
                });

        try {
            RouteNote[] requests =
                    {newNote("First message", 0, 0), newNote("Second message", 0, 1),
                            newNote("Third message", 1, 0), newNote("Fourth message", 1, 1)};

            for (RouteNote request : requests) {
                System.out.println("客户端写入:" + request);
                requestObserver.onNext(request);
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }
        //标识写完
        requestObserver.onCompleted();
        return finishLatch;
    }

    private RouteNote newNote(String message, int lat, int lon) {
        return RouteNote.newBuilder().setMessage(message)
                        .setLocation(Point.newBuilder().setLatitude(lat).setLongitude(lon).build()).build();
    }
}
