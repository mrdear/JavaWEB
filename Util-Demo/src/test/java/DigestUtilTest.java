import org.junit.Test;

import cn.mrdear.util.SecurityUtil.DigestUtil;

/**
 * @author Niu Li
 * @date 2016/12/11
 */
public class DigestUtilTest {

    @Test
    public void testMD5() {
        String str = "hello world";
        System.out.println(DigestUtil.digest(DigestUtil.SecurityEnum.MD5,str));
    }
}
