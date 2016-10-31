package cn.mrdear.pay.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static cn.mrdear.pay.util.SignUtil.joinKeyValue;

/**
 * @author Niu Li
 * @date 2016/10/29
 */
public class SignUtilTest {

    @Test
    public void testJoinKeyValue(){
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("service", "create_direct_pay_by_user");
        parameterMap.put("_input_charset", "utf-8");
        parameterMap.put("sign_type", "MD5");
        parameterMap.put("out_trade_no", System.currentTimeMillis());
        parameterMap.put("payment_type", "1");
        parameterMap.put("paymethod", "bankPay");
        parameterMap.put("extend_param", "isv^1860648a1");
        parameterMap.put("extra_common_param", "shopxx");
        System.out.println("测试joinKeyValue,连接Map键值对");
        System.out.println(joinKeyValue(parameterMap,"?",null,"&",true));
    }
}
