package cn.mrdear.pay.util;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlUtil {


	/**
	 * XML转对象
	 * 
	 * @param xmlStr
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlToBean(String xmlStr, Class<T> t) {
		try {
			JAXBContext context = JAXBContext.newInstance(t);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			T ts = (T) unmarshaller.unmarshal(new StringReader(xmlStr));
			return ts;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * XML转对象
	 * 
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xmlToBean(InputStream input, Class<T> t) {
		try {
			JAXBContext context = JAXBContext.newInstance(t);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			T ts = (T) unmarshaller.unmarshal(new InputStreamReader(input,
					"UTF-8"));
			return ts;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对象转XML
	 * 
	 * @param out
	 * @param to
	 */
	public static String beanToXml(ByteArrayOutputStream out, Object to) {
		try {
			JAXBContext context = JAXBContext.newInstance(to.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(to, out);
			return new String(out.toByteArray(), "UTF-8");
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
