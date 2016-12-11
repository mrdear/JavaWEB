import org.junit.Test;

import cn.mrdear.util.SecurityUtil.AESUtil;

/**
 * @author Niu Li
 * @date 2016/12/11
 */
public class AESUtilTest {
    @Test
    public void testAES() {
        String str = "hello world";
        String en = AESUtil.base64AndEncrypt(str, "123456");
        System.out.println(en);
        System.out.println(AESUtil.base64AndDecrypt(en,"123456"));
    }
}
