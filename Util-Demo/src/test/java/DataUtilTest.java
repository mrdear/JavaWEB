import org.junit.Test;

import java.util.Date;

import cn.mrdear.util.dateutil.DateUtil;

/**
 * @author Niu Li
 * @date 2016/12/11
 */
public class DataUtilTest {
    @Test
    public void testParse(){
        String str = "2016-12-11 09:38:19";
        System.out.println(DateUtil.parse(str,DateUtil.YMDHMS_));
    }

    @Test
    public void testFormat(){
        String str = DateUtil.format(new Date(),DateUtil.YMDHMS_);
        System.out.println(str);
    }

}
