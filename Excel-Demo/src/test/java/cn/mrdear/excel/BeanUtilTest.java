package cn.mrdear.excel;

import org.junit.Test;

import java.util.Map;

import cn.mrdear.excel.util.BeanUtils;

/**
 * @author Niu Li
 * @since 2017/2/23
 */
public class BeanUtilTest {


    @Test
    public void test() throws IllegalAccessException {
        Demo demo = new Demo();
        demo.setUserName("111");
        demo.setPassWord("222");
        Map<String, Object> map = BeanUtils.bean2Map(demo);
        System.out.println(map);
        Demo demo1 = BeanUtils.map2Bean(map,Demo.class);
        System.out.println(demo1);
    }

}
