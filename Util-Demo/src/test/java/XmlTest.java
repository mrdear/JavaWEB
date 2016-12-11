import org.dom4j.DocumentException;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.bind.annotation.XmlRootElement;

import cn.mrdear.util.xml.XmlUtil;

/**
 * @author Niu Li
 * @date 2016/12/11
 */
public class XmlTest
{
    @Test
    public void testXmlReder() throws DocumentException {
        InputStream stream = XmlTest.class.getResourceAsStream("message.xml");
        String result = XmlUtil.reader(new InputStreamReader(stream),"content","gift");
        System.out.println(result);
    }

    @Test
    public void testBeanToXml() {
        User user = new User();
        user.setUsername("zhangsan");
        user.setPassword("123456");
        System.out.println(XmlUtil.beanToXml(user));
//<?xml version="1.0" encoding="UTF-8" standalone="yes"?><xml><password>123456</password><username>zhangsan</username></xml>
        //需要手动去除前面头部
    }

    @XmlRootElement(name = "xml")
    static class User{
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
