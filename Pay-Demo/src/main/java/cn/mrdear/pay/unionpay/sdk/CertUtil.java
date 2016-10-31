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
 *   xshu       2014-05-28       证书工具类.
 * =============================================================================
 */
package cn.mrdear.pay.unionpay.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.RSAPublicKeySpec;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.mrdear.pay.unionpay.sdk.SDKUtil.isEmpty;

public class CertUtil {
	/** 证书容器. */
	private static KeyStore keyStore = null;
	/** 密码加密证书 */
	private static X509Certificate encryptCert = null;
//	/** 磁道加密证书 */
//	private static X509Certificate encryptTrackCert = null;
	/** 磁道加密公钥 */
	private static PublicKey encryptTrackKey = null;
	
	/** 验证签名证书. */
	private static X509Certificate validateCert = null;
	/** 验签证书存储Map. */
	private static Map<String, X509Certificate> certMap = new HashMap<String, X509Certificate>();
	/** 根据传入证书文件路径和密码读取指定的证书容器.(一种线程安全的实现方式) */
	private final static ThreadLocal<KeyStore> certKeyStoreLocal = new ThreadLocal<KeyStore>();
	/** 基于Map存储多商户RSA私钥 */
	private final static Map<String, KeyStore> certKeyStoreMap = new ConcurrentHashMap<String, KeyStore>();
	
	static {
		init();
	}

	/**
	 * 添加签名，验签，加密算法提供者
	 */
	private static void addProvider(){
		if (Security.getProvider("BC") == null) {
			LogUtil.writeLog("add BC provider");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} else {
			Security.removeProvider("BC"); //解决eclipse调试时tomcat自动重新加载时，BC存在不明原因异常的问题。
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			LogUtil.writeLog("re-add BC provider");
		}
		printSysInfo();
	}
	
	/**
	 * 初始化所有证书.
	 */
	public static void init() {
		addProvider();
		if (SDKConstants.TRUE_STRING.equals(SDKConfig.getConfig()
				.getSingleMode())) {
			// 单证书模式,初始化配置文件中的签名证书
			initSignCert();
		}
		initEncryptCert();// 初始化加密公钥证书
		initTrackKey();
		initValidateCertFromDir();// 初始化所有的验签证书
	}

	/**
	 * 加载签名证书
	 */
	public static void initSignCert() {
		if (null != keyStore) {
			keyStore = null;
		}
		try {
			keyStore = getKeyInfo(SDKConfig.getConfig().getSignCertPath(),
					SDKConfig.getConfig().getSignCertPwd(), SDKConfig
							.getConfig().getSignCertType());
			LogUtil.writeLog("InitSignCert Successful. CertId=["
					+ getSignCertId() + "]");
		} catch (IOException e) {
			LogUtil.writeErrorLog("InitSignCert Error", e);
		}
	}

	/**
	 * 根据传入的证书文件路径和证书密码加载指定的签名证书
	 * @deprecated
	 */
	public static void initSignCert(String certFilePath, String certPwd) {
		LogUtil.writeLog("加载证书文件[" + certFilePath + "]和证书密码[" + certPwd
				+ "]的签名证书开始.");
		certKeyStoreLocal.remove();
		File files = new File(certFilePath);
		if (!files.exists()) {
			LogUtil.writeLog("证书文件不存在,初始化签名证书失败.");
			return;
		}
		try {
			certKeyStoreLocal.set(getKeyInfo(certFilePath, certPwd, "PKCS12"));
		} catch (IOException e) {
			LogUtil.writeErrorLog("加载签名证书失败", e);
		}
		LogUtil.writeLog("加载证书文件[" + certFilePath + "]和证书密码[" + certPwd
				+ "]的签名证书结束.");
	}
	

	/**
	 * 加载RSA签名证书
	 * 
	 * @param certFilePath
	 * @param certPwd
	 */
	public static void loadRsaCert(String certFilePath, String certPwd) {
		KeyStore keyStore = null;
		try {
			keyStore = getKeyInfo(certFilePath, certPwd, "PKCS12");
			certKeyStoreMap.put(certFilePath, keyStore);
			LogUtil.writeLog("LoadRsaCert Successful");
		} catch (IOException e) {
			LogUtil.writeErrorLog("LoadRsaCert Error", e);
		}
	}

	/**
	 * 加载密码加密证书 目前支持有两种加密
	 */
	private static void initEncryptCert() {
		LogUtil.writeLog("加载敏感信息加密证书==>"+SDKConfig.getConfig().getEncryptCertPath());
		if (!isEmpty(SDKConfig.getConfig().getEncryptCertPath())) {
			encryptCert = initCert(SDKConfig.getConfig().getEncryptCertPath());
			LogUtil.writeLog("LoadEncryptCert Successful");
		} else {
			LogUtil.writeLog("WARN: acpsdk.encryptCert.path is empty");
		}
//		if (!isEmpty(SDKConfig.getConfig().getEncryptTrackCertPath())) {
//			encryptTrackCert = initCert(SDKConfig.getConfig()
//					.getEncryptTrackCertPath());
//			LogUtil.writeLog("LoadEncryptTrackCert Successful");
//		} else {
//			LogUtil.writeLog("WARN: acpsdk.encryptTrackCert.path is empty");
//		}
	}
	
	/**
	 * 加载磁道公钥
	 */
	private static void initTrackKey() {
		if (!isEmpty(SDKConfig.getConfig().getEncryptTrackKeyModulus())
				&& !isEmpty(SDKConfig.getConfig().getEncryptTrackKeyExponent())) {
			encryptTrackKey = SecureUtil.getPublicKey(SDKConfig.getConfig().getEncryptTrackKeyModulus(), 
					SDKConfig.getConfig().getEncryptTrackKeyExponent());
			LogUtil.writeLog("LoadEncryptTrackKey Successful");
		} else {
			LogUtil.writeLog("WARN: acpsdk.encryptTrackKey.modulus or acpsdk.encryptTrackKey.exponent is empty");
		}
	}
	/**
	 * 
	 * @param path
	 * @return
	 */
	private static X509Certificate initCert(String path) {
		X509Certificate encryptCertTemp = null;
		CertificateFactory cf = null;
		FileInputStream in = null;
		try {
			cf = CertificateFactory.getInstance("X.509", "BC");
			in = new FileInputStream(path);
			encryptCertTemp = (X509Certificate) cf.generateCertificate(in);
			// 打印证书加载信息,供测试阶段调试
			LogUtil.writeLog("[" + path + "][CertId="
					+ encryptCertTemp.getSerialNumber().toString() + "]");
		} catch (CertificateException e) {
			LogUtil.writeErrorLog("InitCert Error", e);
		} catch (FileNotFoundException e) {
			LogUtil.writeErrorLog("InitCert Error File Not Found", e);
		} catch (NoSuchProviderException e) {
			LogUtil.writeErrorLog("LoadVerifyCert Error No BC Provider", e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					LogUtil.writeErrorLog(e.toString());
				}
			}
		}
		return encryptCertTemp;
	}

	/**
	 * 从指定目录下加载验证签名证书
	 * 
	 */
	private static void initValidateCertFromDir() {
		certMap.clear();
		String dir = SDKConfig.getConfig().getValidateCertDir();
		LogUtil.writeLog("加载验证签名证书目录==>" + dir);
		if (isEmpty(dir)) {
			LogUtil.writeLog("ERROR: acpsdk.validateCert.dir is empty");
			return;
		}
		CertificateFactory cf = null;
		FileInputStream in = null;
		try {
			cf = CertificateFactory.getInstance("X.509", "BC");
			File fileDir = new File(dir);
			File[] files = fileDir.listFiles(new CerFilter());
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				in = new FileInputStream(file.getAbsolutePath());
				validateCert = (X509Certificate) cf.generateCertificate(in);
				certMap.put(validateCert.getSerialNumber().toString(),
						validateCert);
				// 打印证书加载信息,供测试阶段调试
				LogUtil.writeLog("[" + file.getAbsolutePath() + "][CertId="
						+ validateCert.getSerialNumber().toString() + "]");
			}
			LogUtil.writeLog("LoadVerifyCert Successful");
		} catch (CertificateException e) {
			LogUtil.writeErrorLog("LoadVerifyCert Error", e);
		} catch (FileNotFoundException e) {
			LogUtil.writeErrorLog("LoadVerifyCert Error File Not Found", e);
		} catch (NoSuchProviderException e) {
			LogUtil.writeErrorLog("LoadVerifyCert Error No BC Provider", e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					LogUtil.writeErrorLog(e.toString());
				}
			}
		}
	}


	/**
	 * 获取签名证书私钥（单证书模式）
	 * 
	 * @return
	 */
	public static PrivateKey getSignCertPrivateKey() {
		try {
			Enumeration<String> aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias,
					SDKConfig.getConfig().getSignCertPwd().toCharArray());
			return privateKey;
		} catch (KeyStoreException e) {
			LogUtil.writeErrorLog("getSignCertPrivateKey Error", e);
			return null;
		} catch (UnrecoverableKeyException e) {
			LogUtil.writeErrorLog("getSignCertPrivateKey Error", e);
			return null;
		} catch (NoSuchAlgorithmException e) {
			LogUtil.writeErrorLog("getSignCertPrivateKey Error", e);
			return null;
		}
	}
	

	/**
	 * 通过传入证书绝对路径和证书密码获取所对应的签名证书私钥
	 * 
	 * @param certPath
	 *            证书绝对路径
	 * @param certPwd
	 *            证书密码
	 * @return 证书私钥
	 * 
	 * @deprecated
	 */
	public static PrivateKey getSignCertPrivateKeyByThreadLocal(
			String certPath, String certPwd) {
		if (null == certKeyStoreLocal.get()) {
			// 初始化指定certPath和certPwd的签名证书容器
			initSignCert(certPath, certPwd);
		}
		try {
			Enumeration<String> aliasenum = certKeyStoreLocal.get().aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			PrivateKey privateKey = (PrivateKey) certKeyStoreLocal.get()
					.getKey(keyAlias, certPwd.toCharArray());
			return privateKey;
		} catch (Exception e) {
			LogUtil.writeErrorLog("获取[" + certPath + "]的签名证书的私钥失败", e);
			return null;
		}
	}
	
	public static PrivateKey getSignCertPrivateKeyByStoreMap(String certPath,
			String certPwd) {
		if (!certKeyStoreMap.containsKey(certPath)) {
			loadRsaCert(certPath, certPwd);
		}
		try {
			Enumeration<String> aliasenum = certKeyStoreMap.get(certPath)
					.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			PrivateKey privateKey = (PrivateKey) certKeyStoreMap.get(certPath)
					.getKey(keyAlias, certPwd.toCharArray());
			return privateKey;
		} catch (KeyStoreException e) {
			LogUtil.writeErrorLog("getSignCertPrivateKeyByStoreMap Error", e);
			return null;
		} catch (UnrecoverableKeyException e) {
			LogUtil.writeErrorLog("getSignCertPrivateKeyByStoreMap Error", e);
			return null;
		} catch (NoSuchAlgorithmException e) {
			LogUtil.writeErrorLog("getSignCertPrivateKeyByStoreMap Error", e);
			return null;
		}
	}

	/**
	 * 获取加密证书公钥.密码加密时需要
	 * 
	 * @return
	 */
	public static PublicKey getEncryptCertPublicKey() {
		if (null == encryptCert) {
			String path = SDKConfig.getConfig().getEncryptCertPath();
			if (!isEmpty(path)) {
				encryptCert = initCert(path);
				return encryptCert.getPublicKey();
			} else {
				LogUtil.writeLog("ERROR: acpsdk.encryptCert.path is empty");
				return null;
			}
		} else {
			return encryptCert.getPublicKey();
		}
	}
	
	/**
	 * 获取加密证书公钥.密码加密时需要
	 * 加密磁道信息证书
	 * 
	 * @return
	 */
	public static PublicKey getEncryptTrackPublicKey() {
//		if (null == encryptTrackCert) {
//			String path = SDKConfig.getConfig().getEncryptTrackCertPath();
//			if (!isEmpty(path)) {
//				encryptTrackCert = initCert(path);
//				return encryptTrackCert.getPublicKey();
//			} else {
//				LogUtil.writeLog("ERROR: acpsdk.encryptTrackCert.path is empty");
//				return null;
//			}
//		} else {
//			return encryptTrackCert.getPublicKey();
//		}
		if (null == encryptTrackKey) {
			initTrackKey();
		}
		return encryptTrackKey;
	}

	/**
	 * 验证签名证书
	 * 
	 * @return 验证签名证书的公钥
	 */
	public static PublicKey getValidateKey() {
		if (null == validateCert) {
			return null;
		}
		return validateCert.getPublicKey();
	}

	/**
	 * 通过certId获取证书Map中对应证书的公钥
	 * 
	 * @param certId
	 *            证书物理序号
	 * @return 通过证书编号获取到的公钥
	 */
	public static PublicKey getValidateKey(String certId) {
		X509Certificate cf = null;
		if (certMap.containsKey(certId)) {
			// 存在certId对应的证书对象
			cf = certMap.get(certId);
			return cf.getPublicKey();
		} else {
			// 不存在则重新Load证书文件目录
			initValidateCertFromDir();
			if (certMap.containsKey(certId)) {
				// 存在certId对应的证书对象
				cf = certMap.get(certId);
				return cf.getPublicKey();
			} else {
				LogUtil.writeErrorLog("缺少certId=[" + certId + "]对应的验签证书.");
				return null;
			}
		}
	}

	

	/**
	 * 获取签名证书中的证书序列号（单证书）
	 * 
	 * @return 证书的物理编号
	 */
	public static String getSignCertId() {
		try {
			Enumeration<String> aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			X509Certificate cert = (X509Certificate) keyStore
					.getCertificate(keyAlias);
			return cert.getSerialNumber().toString();
		} catch (Exception e) {
			LogUtil.writeErrorLog("getSignCertId Error", e);
			return null;
		}
	}

	/**
	 * 获取加密证书的证书序列号
	 * 
	 * @return
	 */
	public static String getEncryptCertId() {
		if (null == encryptCert) {
			String path = SDKConfig.getConfig().getEncryptCertPath();
			if (!isEmpty(path)) {
				encryptCert = initCert(path);
				return encryptCert.getSerialNumber().toString();
			} else {
				LogUtil.writeLog("ERROR: acpsdk.encryptCert.path is empty");
				return null;
			}
		} else {
			return encryptCert.getSerialNumber().toString();
		}
	}
	
	/**
	 * 获取磁道加密证书的证书序列号
	 * @deprecated 磁道根本没给证书啊啊啊啊啊啊啊
	 * @return
	 */
	public static String getEncryptTrackCertId() {
//		if (null == encryptTrackCert) {
//			String path = SDKConfig.getConfig().getEncryptTrackCertPath();
//			if (!isEmpty(path)) {
//				encryptTrackCert = initCert(path);
//				return encryptTrackCert.getSerialNumber().toString();
//			} else {
//				LogUtil.writeLog("ERROR: acpsdk.encryptTrackCert.path is empty");
//				return null;
//			}
//		} else {
//			return encryptTrackCert.getSerialNumber().toString();
//		}
		return "";
	}

	/**
	 * 获取签名证书公钥对象
	 * 
	 * @return
	 */
	public static PublicKey getSignPublicKey() {
		try {
			Enumeration<String> aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) // we are readin just one
			// certificate.
			{
				keyAlias = (String) aliasenum.nextElement();
			}
			Certificate cert = keyStore.getCertificate(keyAlias);
			PublicKey pubkey = cert.getPublicKey();
			return pubkey;
		} catch (Exception e) {
			LogUtil.writeErrorLog(e.toString());
			return null;
		}
	}
	
	
	/**
	 * 将证书文件读取为证书存储对象
	 * 
	 * @param pfxkeyfile
	 *            证书文件名
	 * @param keypwd
	 *            证书密码
	 * @param type
	 *            证书类型
	 * @return 证书对象
	 * @throws IOException 
	 */
	public static KeyStore getKeyInfo(String pfxkeyfile, String keypwd,
			String type) throws IOException {
		LogUtil.writeLog("加载签名证书==>" + pfxkeyfile);
		FileInputStream fis = null;
		try {
			KeyStore ks = KeyStore.getInstance(type, "BC");
			LogUtil.writeLog("Load RSA CertPath=[" + pfxkeyfile + "],Pwd=["+ keypwd + "],type=["+type+"]");
			fis = new FileInputStream(pfxkeyfile);
			char[] nPassword = null;
			nPassword = null == keypwd || "".equals(keypwd.trim()) ? null: keypwd.toCharArray();
			if (null != ks) {
				ks.load(fis, nPassword);
			}
			return ks;
		} catch (Exception e) {
			if (Security.getProvider("BC") == null) {
				LogUtil.writeLog("BC Provider not installed.");
			}
			LogUtil.writeErrorLog("getKeyInfo Error", e);
			if ((e instanceof KeyStoreException) && "PKCS12".equals(type)) {
				Security.removeProvider("BC");
			}
			return null;
		} finally {
			if(null!=fis)
				fis.close();
		}
	}

	// 打印系统环境信息
	public static void printSysInfo() {
		LogUtil.writeLog("================= SYS INFO begin====================");
		LogUtil.writeLog("os_name:" + System.getProperty("os.name"));
		LogUtil.writeLog("os_arch:" + System.getProperty("os.arch"));
		LogUtil.writeLog("os_version:" + System.getProperty("os.version"));
		LogUtil.writeLog("java_vm_specification_version:"
				+ System.getProperty("java.vm.specification.version"));
		LogUtil.writeLog("java_vm_specification_vendor:"
				+ System.getProperty("java.vm.specification.vendor"));
		LogUtil.writeLog("java_vm_specification_name:"
				+ System.getProperty("java.vm.specification.name"));
		LogUtil.writeLog("java_vm_version:"
				+ System.getProperty("java.vm.version"));
		LogUtil.writeLog("java_vm_name:" + System.getProperty("java.vm.name"));
		LogUtil.writeLog("java.version:" + System.getProperty("java.version"));
		LogUtil.writeLog("java.vm.vendor=[" + System.getProperty("java.vm.vendor") + "]");
		LogUtil.writeLog("java.version=[" + System.getProperty("java.version") + "]");
		
		printProviders();
		LogUtil.writeLog("================= SYS INFO end=====================");
	}
	
	public static void printProviders() {
		LogUtil.writeLog("Providers List:");
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			LogUtil.writeLog(i + 1 + "." + providers[i].getName());
		}
	}

	/**
	 * 证书文件过滤器
	 * 
	 */
	static class CerFilter implements FilenameFilter {
		public boolean isCer(String name) {
			if (name.toLowerCase().endsWith(".cer")) {
				return true;
			} else {
				return false;
			}
		}
		public boolean accept(File dir, String name) {
			return isCer(name);
		}
	}
	
	/**
	 * <pre>
	 * 从一个ThreadLocal中获取当前KeyStore中的CertId,
	 * 如果获取失败则重新初始化这个KeyStore并存入ThreadLocal
	 * </pre>>
	 * @deprecated
	 * @param certPath
	 * @param certPwd
	 * @return
	 */
	public static String getCertIdByThreadLocal(String certPath, String certPwd) {
		// 初始化指定certPath和certPwd的签名证书容器
		initSignCert(certPath, certPwd);
		try {
			Enumeration<String> aliasenum = certKeyStoreLocal.get().aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			X509Certificate cert = (X509Certificate) certKeyStoreLocal.get()
					.getCertificate(keyAlias);
			return cert.getSerialNumber().toString();
		} catch (Exception e) {
			LogUtil.writeErrorLog("获取签名证书的序列号失败", e);
			return "";
		}
	}
	
	
	public static String getCertIdByKeyStoreMap(String certPath, String certPwd) {
		if (!certKeyStoreMap.containsKey(certPath)) {
			// 缓存中未查询到,则加载RSA证书
			loadRsaCert(certPath, certPwd);
		}
		return getCertIdIdByStore(certKeyStoreMap.get(certPath));
	}

	private static String getCertIdIdByStore(KeyStore keyStore) {
		Enumeration<String> aliasenum = null;
		try {
			aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			X509Certificate cert = (X509Certificate) keyStore
					.getCertificate(keyAlias);
			return cert.getSerialNumber().toString();
		} catch (KeyStoreException e) {
			LogUtil.writeErrorLog("getCertIdIdByStore Error", e);
			return null;
		}
	}
	

	/**
	 * 获取证书容器
	 * 
	 * @return
	 */
	public static Map<String, X509Certificate> getCertMap() {
		return certMap;
	}

	/**
	 * 设置证书容器
	 * 
	 * @param certMap
	 */
	public static void setCertMap(Map<String, X509Certificate> certMap) {
		CertUtil.certMap = certMap;
	}
	
	/**
	 * 使用模和指数生成RSA公钥 注意：此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同
	 * 
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static PublicKey getPublicKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			LogUtil.writeErrorLog("构造RSA公钥失败：" + e);
			return null;
		}
	}
	
	/**
	 * 使用模和指数的方式获取公钥对象
	 * 
	 * @return
	 */
	public static PublicKey getEncryptTrackCertPublicKey(String modulus,
			String exponent) {
		if (isEmpty(modulus) || isEmpty(exponent)) {
			LogUtil.writeErrorLog("[modulus] OR [exponent] invalid");
			return null;
		}
		return getPublicKey(modulus, exponent);
	}
	
}
