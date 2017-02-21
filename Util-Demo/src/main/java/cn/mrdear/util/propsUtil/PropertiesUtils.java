package cn.mrdear.util.propsUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获取配置文件的类
 */
public class PropertiesUtils {

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	/**
	 * 得到用户根目录下user.home + props + filename路径的文件
	 * @param key 键
	 * @param filename 文件名
	 * @return 值
	 */
	public static String getUserDirProperty(String key, String filename) {
		String absolutePath = System.getProperty("user.home") +File.separator+ filename;
		logger.debug("配置文件绝对路径:"+absolutePath);
		return getAbsoluteProperty(key,absolutePath);
	}

	/**
	 * 从ClassPath获取配置文件
	 * @param key 键
	 * @param fileName 文件名
	 * @return 值
	 */
	public static String getClassPathProperty(String key, String fileName) {
		InputStream input = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
		return getPropertiesFromInput(input,key);
	}

	/**
	 * 从绝对路径获取配置文件
	 * @param key 键
	 * @param path 路径
	 * @return 值
	 */
	public static String getAbsoluteProperty(String key, String path) {
		try {
			InputStream input = new FileInputStream(new File(path));
			return getPropertiesFromInput(input,key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.warn("文件不存在!!!"+path);
		}
		return null;
	}

	/**
	 * 从输入流中获取指定键值
	 * @param input 输入流
	 * @param key 需要的key
	 * @return 值
	 */
	private static String getPropertiesFromInput(InputStream input,String key){
		Properties pt = null;
		try {
			pt = new Properties();
			pt.load(input);
			return pt.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (pt != null) {
				pt.clear();
			}
		}
	}

}
