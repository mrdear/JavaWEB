package cn.mrdear.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Niu Li
 * @date 2016/10/10
 */
public final class WEBUtil {


    /**
     * PoolingHttpClientConnectionManager
     */
    private static final PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER;

    /**
     * CloseableHttpClient
     */
    private static final CloseableHttpClient HTTP_CLIENT;

    static {
        HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build());
        HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
        HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(200);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setConnectTimeout(60000).setSocketTimeout(60000).build();
        HTTP_CLIENT = HttpClientBuilder.create().setConnectionManager(HTTP_CLIENT_CONNECTION_MANAGER).setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 不可实例化
     */
    private WEBUtil() {
    }

    /**
     * POST请求,可以附加header的值
     *
     * @param url          URL
     * @param parameterMap 请求参数
     * @return 返回结果
     */
    public static String post(String url, Map<String, Object> parameterMap, Map<String, Object> headerMap) {
        String result = null;
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            if (parameterMap != null) {
                parameterMap.forEach((key,value)->{
                    if (StringUtils.isNotEmpty(key)) {
                        nameValuePairs.add(new BasicNameValuePair(key, value.toString()));
                    }
                });
            }
            HttpPost httpPost = new HttpPost(url);
            //添加header的值
            if (headerMap != null) {
                headerMap.forEach((key,value)->{
                    if (StringUtils.isNotEmpty(key)) {
                        httpPost.addHeader(key, value.toString());
                    }
                });
            }
            //添加entity的值
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

            CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }
            httpResponse.close();
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }


    /**
     * GET请求
     *
     * @param url          URL
     * @param parameterMap 请求参数
     * @return 返回结果
     */
    public static String get(String url, Map<String, Object> parameterMap) {
        String result = null;
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            if (parameterMap != null) {
                parameterMap.forEach((key,value)->{
                    if (StringUtils.isNotEmpty(key)) {
                        nameValuePairs.add(new BasicNameValuePair(key, value.toString()));
                    }
                });
            }
            HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
            CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }
            httpResponse.close();
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return result;
    }
}
