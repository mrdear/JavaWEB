package cn.mrdear.util.httpUtil;

import org.apache.commons.lang3.ArrayUtils;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * 添加cookie
	 * @param response HttpServletResponse
	 * @param name  Cookie名称
	 * @param value  Cookie值
	 * @param maxAge  有效期(单位: 秒)
	 * @param path  路径
	 * @param domain  域
	 * @param secure  是否启用加密
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure) {

		try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
			Cookie cookie = new Cookie(name, value);
			if (maxAge != null) {
				cookie.setMaxAge(maxAge);
			}
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			if (secure != null) {
				cookie.setSecure(secure);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	/**
	 * 获取cookie
	 * @param request HttpServletRequest
	 * @param name Cookie名称
	 * @return Cookie值，若不存在则返回null
	 */
	public static String getCookie(HttpServletRequest request, String name) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				name = URLEncoder.encode(name, "UTF-8");
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return null;
	}
	/**
	 * 移除cookie
	 * @param response HttpServletResponse
	 * @param name Cookie名称
	 * @param path 路径
	 * @param domain 域
	 */
	public static void removeCookie(HttpServletResponse response, String name, String path, String domain) {
		try {
			name = URLEncoder.encode(name, "UTF-8");
			Cookie cookie = new Cookie(name, null);
			cookie.setMaxAge(0);
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 把request请求参数转换为Map<String,String>
	 * @param request 该请求
	 * @return Map<String,String>格式的参数
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> request2Map(HttpServletRequest request){
		Enumeration<String> names = request.getParameterNames();
		Map<String, String> resData = new HashMap<String, String>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			resData.put(name, request.getParameter(name));
		}
		return resData;
	}

	/**
	 * 连接Map键值对
	 * @param map Map
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @param separator 连接符
	 * @param ignoreEmptyValue 忽略空值
	 * @param ignoreKeys 忽略Key
	 * @return 字符串
	 */
	public static String joinKeyValue(Map<String, ?> map, String prefix, String suffix, String separator,
									  boolean ignoreEmptyValue, String... ignoreKeys) {
		List<String> list = new ArrayList<String>();
		if (map != null) {
			//使用Java8语法会更加简洁
			for (Map.Entry<String, ?> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = String.valueOf(entry.getValue());
				if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key)
						&& (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
					list.add(key + "=" + (value != null ? value : ""));
				}
			}
		}
		return (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
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
					if (StringUtils.isNoneEmpty(value)) {
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
					if (StringUtils.isNoneEmpty(value)) {
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