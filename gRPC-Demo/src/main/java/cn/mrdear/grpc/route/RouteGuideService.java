package cn.mrdear.grpc.route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.mrdear.route.Feature;
import cn.mrdear.route.Point;
import cn.mrdear.route.Rectangle;
import cn.mrdear.route.RouteGuideGrpc;
import cn.mrdear.route.RouteNote;
import cn.mrdear.route.RouteSummary;
import io.grpc.stub.StreamObserver;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * 具体服务类
 * @author Niu Li
 * @date 2017/2/2
 */
public class RouteGuideService extends RouteGuideGrpc.RouteGuideImplBase {
    //保存搜索的位置
    private final Collection<Feature> features;

    private final ConcurrentMap<Point, List<RouteNote>> routeNotes =
            new ConcurrentHashMap<Point, List<RouteNote>>();
    //初始化所有点
    RouteGuideService(Collection<Feature> features) {
        this.features = features;
    }

    @Override
    public void getFeature(Point request, StreamObserver<Feature> responseObserver) {
        System.out.println("getFeature得到的请求参数: " + request.toString());
//        responseObserver.onError(); 代表请求出错
        responseObserver.onNext(checkFeature(request));//包装返回信息
        responseObserver.onCompleted();//完成一次请求
    }
    //找到复核的feature
    private Feature checkFeature(Point location) {
        for (Feature feature : features) {
            if (feature.getLocation().getLatitude() == location.getLatitude()
                    && feature.getLocation().getLongitude() == location.getLongitude()) {
                return feature;
            }
        }
        // No feature was found, return an unnamed feature.
        return Feature.newBuilder().setName("").setLocation(location).build();
    }

    @Override
    public void listFeatures(Rectangle request, StreamObserver<Feature> responseObserver) {
        System.out.println("listFeatures得到的请求参数: " + request.toString());
        int left = min(request.getLo().getLongitude(), request.getHi().getLongitude());
        int right = max(request.getLo().getLongitude(), request.getHi().getLongitude());
        int top = max(request.getLo().getLatitude(), request.getHi().getLatitude());
        int bottom = min(request.getLo().getLatitude(), request.getHi().getLatitude());

        for (Feature feature : features) {
            //如果不存在则继续
            if (!RouteGuideUtil.exists(feature)) {
                continue;
            }

            int lat = feature.getLocation().getLatitude();
            int lon = feature.getLocation().getLongitude();
            if (lon >= left && lon <= right && lat >= bottom && lat <= top) {
                //找到符合的就写入
                responseObserver.onNext(feature);
            }
        }
        //最后标识完成
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Point> recordRoute(StreamObserver<RouteSummary> responseObserver) {
        return new StreamObserver<Point>() {
            int pointCount;
            int featureCount;
            int distance;
            Point previous;
            long startTime = System.nanoTime();
            //客户端每写入一个Point,服务端就会调用该方法
            @Override
            public void onNext(Point point) {
                System.out.println("recordRoute得到的请求参数: " + point.toString());
                pointCount++;
                if (RouteGuideUtil.exists(checkFeature(point))) {
                    featureCount++;
                }
                if (previous != null) {
                    distance += calcDistance(previous, point);
                }
                previous = point;
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                System.err.println("Encountered error in recordRoute");
            }
            //客户端写入结束时调用
            @Override
            public void onCompleted() {
                long seconds = NANOSECONDS.toSeconds(System.nanoTime() - startTime);
                responseObserver.onNext(RouteSummary.newBuilder().setPointCount(pointCount)
                                                    .setFetureCount(featureCount)
                                                    .setDistance(distance)
                                                    .setElapsedTime((int) seconds).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<RouteNote> routeChat(StreamObserver<RouteNote> responseObserver) {
        return new StreamObserver<RouteNote>() {
            @Override
            public void onNext(RouteNote note) {
                System.out.println("routeChat得到参数:" + note);
                List<RouteNote> notes = getOrCreateNotes(note.getLocation());

                for (RouteNote prevNote : notes.toArray(new RouteNote[0])) {
                    responseObserver.onNext(prevNote);
                }
                notes.add(note);
            }
            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                System.err.println("Encountered error in routeChat");
            }

            @Override
            public void onCompleted() {
                System.out.println("finsh");
                responseObserver.onCompleted();
            }
        };
    }

    //创建记录点
    private List<RouteNote> getOrCreateNotes(Point location) {
        List<RouteNote> notes = Collections.synchronizedList(new ArrayList<RouteNote>());
        List<RouteNote> prevNotes = routeNotes.putIfAbsent(location, notes);
        return prevNotes != null ? prevNotes : notes;
    }

    //计算点与点距离
    private static int calcDistance(Point start, Point end) {
        double lat1 = RouteGuideUtil.getLatitude(start);
        double lat2 = RouteGuideUtil.getLatitude(end);
        double lon1 = RouteGuideUtil.getLongitude(start);
        double lon2 = RouteGuideUtil.getLongitude(end);
        int r = 6371000; // meters
        double phi1 = toRadians(lat1);
        double phi2 = toRadians(lat2);
        double deltaPhi = toRadians(lat2 - lat1);
        double deltaLambda = toRadians(lon2 - lon1);

        double a = sin(deltaPhi / 2) * sin(deltaPhi / 2)
                + cos(phi1) * cos(phi2) * sin(deltaLambda / 2) * sin(deltaLambda / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        return (int) (r * c);
    }
}
