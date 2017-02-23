package cn.mrdear.excel;

import cn.mrdear.excel.util.BeanUtils;

/**
 * @author Niu Li
 * @since 2017/2/23
 */
public class BeanUtilTest {
    public static void main(String[] args) throws IllegalAccessException {
        Demo demo = new Demo();
        demo.setUserName("111");
        demo.setPassWord("222");
        System.out.println(BeanUtils.bean2Map(demo));
    }
}
