package cn.mrdear.util.xml;


import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * XML工具类
 * 在Reder里面配合缓存使用的话效果更佳
 *
 * 对于Bean需要使用@XmlRootElement(name = "xml")注解
 */
public abstract class XmlUtil {

	/**
	 * 读取指定属性下面的key
	 * @param inputStream xml输入流
	 * @param key 指定读取的key
	 * @return 读取内容
	 * @throws DocumentException 抛出异常
	 */
	public static String reader(Reader inputStream, String key) throws DocumentException {
		SAXReader reader = new SAXReader();
		//设置不检查DTD
		reader.setEntityResolver(new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				return new InputSource(
						new ByteArrayInputStream(
								"<?xml version='1.0' encoding='UTF-8'?>".getBytes()
						));
			}
		});
		Document document = reader.read(inputStream);
		Element node = document.getRootElement();
		return listNodes(node, key);
	}

	/**
	 * 读取指定属性下面的key
	 * @param inputStream xml输入流
	 * @param key 指定读取的key
	 * @param attrKey key父节点的属性attrKey
	 * @return 读取内容
	 * @throws DocumentException 抛出异常
	 */
	public static String reader(Reader inputStream, String key,String attrKey) throws DocumentException {
		SAXReader reader = new SAXReader();
		//设置不检查DTD
		reader.setEntityResolver(new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				return new InputSource(
						new ByteArrayInputStream(
								"<?xml version='1.0' encoding='UTF-8'?>".getBytes()
						));
			}
		});
		Document document = reader.read(inputStream);
		Element node = document.getRootElement();
		return listNodes(node, key,attrKey);
	}

	/**
	 * 遍历当前节点元素下面的所有(元素的)子节点
	 * @param node 节点
	 * @param key 寻找的key
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	private static String listNodes(Element node, String key) {
		System.out.println("当前节点的名称：：" + node.getName());
		if (key.equals(node.getName())) {
			return node.getTextTrim();
		}
		Iterator<Element> it = node.elementIterator();
		while (it.hasNext()) {
			Element e = it.next();
			String result = listNodes(e, key);
			if (result != null && !"".equals(result)){
				return result;
			}
		}
		return "";
	}

	/**
	 * 遍历当前节点元素下面的所有(元素的)子节点
	 * @param node 节点
	 * @param key 寻找的key
	 * @param attributeKey 该key拥有的属性
	 * @return value
	 */
	@SuppressWarnings("unchecked")
	private static String listNodes(Element node, String key,String attributeKey) {
		System.out.println("当前节点的名称：：" + node.getName());
		//判断该节点的属性有没有这个值
		List<Attribute> attributes = node.attributes();
		for (Attribute tempAttr : attributes) {
			System.out.println("当前节点的属性名称：" + tempAttr.getValue());
			if (tempAttr.getValue().equals(attributeKey)){
				String result = listNodes(node, key);
				if (result != null && !"".equals(result)){
					return result;
				}
			}
		}
		Iterator<Element> it = node.elementIterator();
		while (it.hasNext()) {
			Element e = it.next();
			String result = listNodes(e, key,attributeKey);
			if (result !=null && !"".equals(result)){
				return result;
			}
		}
		return "";
	}

	/**
	 * 把document对象写入新的文件
	 *
	 * @param document
	 * @throws Exception
	 */
	public static void writer(Document document, OutputStreamWriter outputStream, String encoding) throws Exception {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);
		XMLWriter writer = new XMLWriter(outputStream, format);
		writer.write(document);
		writer.flush();
		writer.close();
	}

	/**
	 * XML转对象
	 * @param xmlStr xml字串
	 * @param t 对象类型
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlToBean(String xmlStr, Class<T> t) {
		try {
			JAXBContext context = JAXBContext.newInstance(t);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(new StringReader(xmlStr));
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * XML转对象
	 * @param input xml输入流
	 * @param t 该对象
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlToBean(InputStream input, Class<T> t) {
		try {
			JAXBContext context = JAXBContext.newInstance(t);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(new InputStreamReader(input, "UTF-8"));
		} catch (JAXBException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对象转XML
	 * @param to 对象
	 * @return 字符串
	 */
	public static String beanToXml(Object to) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(to.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(to, out);
			return new String(out.toByteArray(), "UTF-8");
		} catch (JAXBException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
