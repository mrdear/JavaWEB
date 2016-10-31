/**
 *
 * Licensed Property to China UnionPay Co., Ltd.
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *   xshu       2014-05-28      MPI工具类
 * =============================================================================
 */
package cn.mrdear.pay.unionpay.sdk;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;


public class SDKUtil {
	
	/**
	 * 生成签名值(SHA1摘要算法)
	 * 
	 * @param data
	 *            待签名数据Map键值对形式
	 * @param encoding
	 *            编码
	 * @return 签名是否成功
	 */
	public static boolean sign(Map<String, String> data, String encoding) {
		if (isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		// 设置签名证书序列号
		data.put(SDKConstants.param_certId, CertUtil.getSignCertId());
		// 将Map信息转换成key1=value1&key2=value2的形式
		String stringData = coverMap2String(data);
		LogUtil.writeLog("待签名请求报文串:[" + stringData + "]");
		/**
		 * 签名\base64编码
		 */
		byte[] byteSign = null;
		String stringSign = null;
		try {
			// 通过SHA1进行摘要并转16进制
			byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
			byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(
					CertUtil.getSignCertPrivateKey(), signDigest));
			stringSign = new String(byteSign);
			// 设置签名域值
			data.put(SDKConstants.param_signature, stringSign);
			return true;
		} catch (Exception e) {
			LogUtil.writeErrorLog("签名异常", e);
			return false;
		}
	}

	/**
	 * 通过传入的证书绝对路径和证书密码读取签名证书进行签名并返回签名值<br>
	 * 
	 * @param data
	 *            待签名数据Map键值对形式
	 * @param encoding
	 *            编码
	 * @param certPath
	 *            证书绝对路径
	 * @param certPwd
	 *            证书密码
	 * @return 签名值
	 */
	public static boolean signByCertInfo(Map<String, String> data,
			 String certPath, String certPwd,String encoding) {
		if (isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		if (isEmpty(certPath) || isEmpty(certPwd)) {
			LogUtil.writeLog("Invalid Parameter:CertPath=[" + certPath
					+ "],CertPwd=[" + certPwd + "]");
			return false;
		}
		// 设置签名证书序列号
		data.put(SDKConstants.param_certId,
				CertUtil.getCertIdByKeyStoreMap(certPath, certPwd));
		// 将Map信息转换成key1=value1&key2=value2的形式
		String stringData = coverMap2String(data);
		/**
		 * 签名\base64编码
		 */
		byte[] byteSign = null;
		String stringSign = null;
		try {
			byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
			byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(
					CertUtil.getSignCertPrivateKeyByStoreMap(certPath, certPwd),
					signDigest));
			stringSign = new String(byteSign);
			// 设置签名域值
			data.put(SDKConstants.param_signature, stringSign);
			return true;
		} catch (Exception e) {
			LogUtil.writeErrorLog("签名异常", e);
			return false;
		}
	}


	/**
	 * 将Map中的数据转换成按照Key的ascii码排序后的key1=value1&key2=value2的形式 不包含签名域signature
	 * 
	 * @param data
	 *            待拼接的Map数据
	 * @return 拼接好后的字符串
	 */
	public static String coverMap2String(Map<String, String> data) {
		TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			if (SDKConstants.param_signature.equals(en.getKey().trim())) {
				continue;
			}
			tree.put(en.getKey(), en.getValue());
		}
		it = tree.entrySet().iterator();
		StringBuffer sf = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			sf.append(en.getKey() + SDKConstants.EQUAL + en.getValue()
					+ SDKConstants.AMPERSAND);
		}
		return sf.substring(0, sf.length() - 1);
	}


	/**
	 * 兼容老方法 将形如key=value&key=value的字符串转换为相应的Map对象
	 * 
	 * @param result
	 * @return
	 */
	public static Map<String, String> coverResultString2Map(String result) {
		return convertResultStringToMap(result);
	}

	/**
	 * 将形如key=value&key=value的字符串转换为相应的Map对象
	 * 
	 * @param result
	 * @return
	 */
	public static Map<String, String> convertResultStringToMap(String result) {
		Map<String, String> map =null;
		try {
			
			if(StringUtils.isNotBlank(result)){
				if(result.startsWith("{") && result.endsWith("}")){
					System.out.println(result.length());
					result = result.substring(1, result.length()-1);
				}
				 map = parseQString(result);
			}
			
		} catch (UnsupportedEncodingException e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		}
		return map;
	}

	
	/**
	 * 解析应答字符串，生成应答要素
	 * 
	 * @param str
	 *            需要解析的字符串
	 * @return 解析的结果map
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> parseQString(String str)
			throws UnsupportedEncodingException {

		Map<String, String> map = new HashMap<String, String>();
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		char curChar;
		String key = null;
		boolean isKey = true;
		boolean isOpen = false;//值里有嵌套
		char openName = 0;
		if(len>0){
			for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
				curChar = str.charAt(i);// 取当前字符
				if (isKey) {// 如果当前生成的是key
					
					if (curChar == '=') {// 如果读取到=分隔符 
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					} else {
						temp.append(curChar);
					}
				} else  {// 如果当前生成的是value
					if(isOpen){
						if(curChar == openName){
							isOpen = false;
						}
						
					}else{//如果没开启嵌套
						if(curChar == '{'){//如果碰到，就开启嵌套
							isOpen = true;
							openName ='}';
						}
						if(curChar == '['){
							isOpen = true;
							openName =']';
						}
					}
					if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
						putKeyValueToMap(temp, isKey, key, map);
						temp.setLength(0);
						isKey = true;
					}else{
						temp.append(curChar);
					}
				}
				
			}
			putKeyValueToMap(temp, isKey, key, map);
		}
		return map;
	}

	private static void putKeyValueToMap(StringBuilder temp, boolean isKey,
			String key, Map<String, String> map)
			throws UnsupportedEncodingException {
		if (isKey) {
			key = temp.toString();
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, "");
		} else {
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, temp.toString());
		}
	}

	/**
	 * 判断字符串是否为NULL或空
	 * 
	 * @param s
	 *            待判断的字符串数据
	 * @return 判断结果 true-是 false-否
	 */
	public static boolean isEmpty(String s) {
		return null == s || "".equals(s.trim());
	}

	/**
	 * 过滤请求报文中的空字符串或者空字符串
	 * @param contentData
	 * @return
	 */
	public static Map<String, String> filterBlank(Map<String, String> contentData){
		LogUtil.writeLog("打印请求报文域 :");
		Map<String, String> submitFromData = new HashMap<String, String>();
		Set<String> keyset = contentData.keySet();
		
		for(String key:keyset){
			String value = contentData.get(key);
			if (StringUtils.isNotBlank(value)) {
				// 对value值进行去除前后空处理
				submitFromData.put(key, value.trim());
				LogUtil.writeLog(key + "-->" + String.valueOf(value));
			}
		}
		return submitFromData;
	}
}
