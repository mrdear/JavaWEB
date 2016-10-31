package cn.mrdear.pay.util;

import org.junit.Test;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.annotation.XmlRootElement;

import static cn.mrdear.pay.util.XmlUtil.beanToXml;

/**
 * @author Niu Li
 * @date 2016/10/29
 */
public class XMLUtilTest {

    @Test
    public void testJava2XML(){
        Xml x = new Xml();
        x.setName("冯");
        x.setAttach("丽娟");
        String buffer = beanToXml(new ByteArrayOutputStream(), x);
        System.out.println(buffer);
        System.out.println(buffer.substring(55));
    }


    @XmlRootElement(name = "xml")
    static class Xml {
        private String name;
        private String attach;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        public String getAttach() {
            return attach;
        }
    }
}
