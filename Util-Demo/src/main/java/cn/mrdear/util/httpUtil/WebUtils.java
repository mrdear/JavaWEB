package cn.mrdear.util.httpUtil;

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
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

/**
 * WEB连接中常用到的方法
 */
public abstract class WebUtils {

	/** PoolingHttpClientConnectionManager */
	private static final PoolingHttpClientConnectionManager HTTP_CLIENT_CONNECTION_MANAGER;

	/** CloseableHttpClient */
	private static final CloseableHttpClient HTTP_CLIENT;

	static {
		HTTP_CLIENT_CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", SSLConnectionSocketFactory.getSocketFactory()).build());
		HTTP_CLIENT_CONNECTION_MANAGER.setDefaultMaxPerRoute(100);
		HTTP_CLIENT_CONNECTION_MANAGER.setMaxTotal(200);
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(60000).setConnectTimeout(60000).setSocketTimeout(60000).build();
		HTTP_CLIENT = HttpClientBuilder.create().setConnectionManager(HTTP_CLIENT_CONNECTION_MANAGER).setDefaultRequestConfig(requestConfig).build();
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            请求参数
	 * @return 返回结果
	 */
	public static String post(String url, Map<String, ?> parameterMap,String encoding) {
		String result = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<>();
			//使用Java8的话这里可以进一步简化
			if (parameterMap != null) {
				for (Map.Entry<String, ?> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = String.valueOf(entry.getValue());
					if (name != null && !"".equals(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));
			CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpPost);
			result = consumeResponse(httpResponse,encoding);
		} catch ( ParseException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * post请求,请求体为json串
	 * @param url 请求连接
	 * @param json json串
	 * @param encording 编码
	 * @return 结果
	 */
	public static String post(String url, String json,String encording){
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			entity.setContentEncoding(encording);
			httpPost.setEntity(entity);
			// 执行http请求
			response = HTTP_CLIENT.execute(httpPost);
			resultString = consumeResponse(response,encording);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(response != null) response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return resultString;
	}
	/**
	 * POST请求
	 *
	 * @param url
	 *            URL
	 * @param inputStreamEntity
	 *            请求体
	 * @return 返回结果
	 */
	public static String post(String url, InputStreamEntity inputStreamEntity,String encording) {

		String result = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			inputStreamEntity.setContentEncoding(encording);
			httpPost.setEntity(inputStreamEntity);
			CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpPost);
			result = consumeResponse(httpResponse,encording);
		} catch ( ParseException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            请求参数
	 * @return 返回结果
	 */
	public static String get(String url, Map<String, ?> parameterMap) {

		String result = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Map.Entry<String, ?> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = String.valueOf(entry.getValue());
					if (name != null && !"".equals(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			HttpGet httpGet = new HttpGet(url + (url.contains("?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
			CloseableHttpResponse httpResponse = HTTP_CLIENT.execute(httpGet);
			result = consumeResponse(httpResponse,"UTF-8");
		} catch (ParseException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 利用证书请求微信
	 * @param certPath 证书路径
	 * @param passwd  证书密码
	 * @param uri 请求地址
	 * @param entity 请求体xml内容
	 * @return 得到的结果
	 */
	public static String post(String certPath,String passwd, String uri, InputStreamEntity entity,String encording) throws Exception{
		String result = null;
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(certPath));
		try {
			keyStore.load(instream, passwd.toCharArray());
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, passwd.toCharArray()).build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
				null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpPost = new HttpPost(uri);
			entity.setContentEncoding(encording);
			httpPost.setEntity(entity);
			CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
			result = consumeResponse(httpResponse,encording);
		} finally {
			httpclient.close();
		}
		return result;
	}

	/**
	 * 处理返回的请求,拿到返回内容
	 * @param httpResponse 要处理的返回
	 * @return 返回的内容
	 */
	private static String consumeResponse(CloseableHttpResponse httpResponse,String encording){
		String result = null;
		try {
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				result = EntityUtils.toString(httpEntity,encording);
				EntityUtils.consume(httpEntity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
			} catch (IOException ignored) {
			}
		}
		return result;
	}
}