package cn.mrdear.util.SecurityUtil;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 摘要签名工具类
 */
public class DigestUtil {

	private static final Logger logger = LoggerFactory.getLogger(DigestUtil.class);

	private static final String SHA1_PRNG = "SHA1PRNG";

	private static SecureRandom sRandom;

	public enum SecurityEnum {
		MD5("MD5"), SHA256("SHA-256"), SHA512("SHA-512"), SHA384("SHA-384"), SHA1("SHA-1"), SHA("SHA");

		SecurityEnum(String value) {
			this.value = value;
		}
		private String value;
		public String getValue() {
			return value;
		}
	}


	static {
		try {
			sRandom = SecureRandom.getInstance(SHA1_PRNG);
		} catch (NoSuchAlgorithmException e) {
			logger.error("生成安全随机数失败:", e);
		}
	}

	/**
     * 生成指定bit的安全随机数
	 * @param bits bit位
	 * @return 生成结果
	 */
	private static byte[] getNextSecureRandom(int bits) {
		if ((bits % 8) != 0) {
			throw new IllegalArgumentException("Size is not divisible by 8!");
		}
		final byte[] bytes = new byte[bits / 8];
		sRandom.nextBytes(bytes);
		return bytes;
	}

	/**
	 * 随机生成指定位数字串
	 * @return 生成字串
	 */
	public static String getNextHexRandomNum(int bits) {
		return Hex.encodeHexString(getNextSecureRandom(bits*8));
	}

	/**
	 * 根据算法得到相应摘要
	 * @param instance 算法
	 * @param bytes 需要摘要的内容
	 * @return 摘要
	 */
	public static String digest(SecurityEnum instance, byte[] bytes) {
		MessageDigest digestUtils = getInstance(instance);
		digestUtils.update(bytes);
		return byte2String(digestUtils.digest());
	}

    /**
     * 根据算法得到相应摘要
	 * @param instance 算法
     * @param content 需要摘要的内容
     * @return 摘要
     */
	public static String digest(SecurityEnum instance, String content) {
		if (content == null) return "";
		return digest(instance,content.getBytes());
	}

	/**
	 * 根据算法拿到一个实现者
	 * @param securityEnum 算法
	 * @return 实例
	 */
	private static MessageDigest getInstance(SecurityEnum securityEnum){
		MessageDigest digestUtils = null;
		try {
			digestUtils = MessageDigest.getInstance(securityEnum.getValue());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.error("不存在的加密算法");
			digestUtils = DigestUtils.getDigest(securityEnum.getValue());
		}
		return digestUtils;
	}

	/**
	 * 加密后的内容转成String
	 * @param content 内容
	 * @return 字符串
	 */
	private static String byte2String(byte[] content){
		StringBuilder hexString = new StringBuilder();
		// 字节数组转换为 十六进制 数
		for (int i = 0; i < content.length; i++) {
			String shaHex = Integer.toHexString(content[i] & 0xFF);
			if (shaHex.length() < 2) {
				hexString.append(0);
			}
			hexString.append(shaHex);
		}
		return hexString.toString();
	}
}
