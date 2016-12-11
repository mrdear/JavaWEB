import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import cn.mrdear.util.httpUtil.WebUtils;

/**
 * @author Niu Li
 * @date 2016/12/11
 */
public class httpUtilTest {

    @Test
    public void testGet() {
        String url = "http://php.weather.sina.com.cn/iframe/index/w_cl.php";
        Map<String,Object> params = new HashMap<>();
        params.put("code","js");
        params.put("day",0);
        params.put("city","上海");
        params.put("dfc",1);
        params.put("charset","utf-8");
        String result = WebUtils.get(url,params);
        System.out.println(result);
    }
}
