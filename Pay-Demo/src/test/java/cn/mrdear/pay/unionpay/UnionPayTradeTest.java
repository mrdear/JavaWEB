package cn.mrdear.pay.unionpay;


import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import cn.mrdear.pay.unionpay.sdk.SDKConfig;

/**
 * @author Niu Li
 * @date 2016/10/30
 */
public class UnionPayTradeTest {

    @Before
    public void loadSDK(){
        SDKConfig.getConfig().loadPropertiesFromSrc();
    }

    /**
     * 测试开通
     */
    @Test
    public void testTokenOpen(){
        UnionPayTrade unionPayTrade = new UnionPayTrade();
        Map<String,String> customerInfoMap = new HashMap<String,String>();
        customerInfoMap.put("accNo", "6222021304008026228");
        customerInfoMap.put("customerInfo", "{}");
        customerInfoMap.put("orderId", System.currentTimeMillis()+"");
        unionPayTrade.tokenOpen(customerInfoMap);
    }

    /**
     * 测试消费短信
     */
    @Test
    public void testSmsCode(){
        UnionPayTrade unionPayTrade = new UnionPayTrade();
        Map<String,String> customerInfoMap = new HashMap<String,String>();
        customerInfoMap.put("currencyCode", "156");
        customerInfoMap.put("txnAmt", "1");
        customerInfoMap.put("tokenPayData", "{token=6235240xxxx17620679&trId=62000000016&tokenLevel=40&tokenBegin=20161030154742&tokenEnd=20211029154742&tokenType=01}");
        customerInfoMap.put("customerInfo", "{}");
        String value = System.currentTimeMillis() + "";
        System.out.println(value);//商户订单
        customerInfoMap.put("orderId", value);
        customerInfoMap.put("txnSubType", "02");// 用于区分发送短信的类型：00——开通短信02——消费短信04——预授权451342
        unionPayTrade.msg(customerInfoMap);
    }

    /**
     * 测试消费
     */
    @Test
    public void testConsume(){
        UnionPayTrade unionPayTrade = new UnionPayTrade();
        Map<String,String> customerInfoMap = new HashMap<String,String>();
        customerInfoMap.put("currencyCode", "156");
        customerInfoMap.put("txnAmt", "200");
        customerInfoMap.put("tokenPayData", "{token=6235xxxx000217620679&trId=62000000016&tokenLevel=40&tokenBegin=20161030154742&tokenEnd=20211029154742&tokenType=01}");
        customerInfoMap.put("customerInfo", "{smsCode=451342}");
        customerInfoMap.put("orderId", "1477903831526");
        customerInfoMap.put("txnSubType", "01");//01：自助消费，通过地址的方式区分前台消费和后台消费（含无跳转 支付）03：分期付款
        unionPayTrade.consume(customerInfoMap);
    }

    @Test
    public void testRefund(){
        UnionPayTrade unionPayTrade = new UnionPayTrade();
        Map<String, String> customerInfoMap = new HashMap<>();
        customerInfoMap.put("orderId",System.currentTimeMillis()+"");
        customerInfoMap.put("origQryId","201610312015598471058");
        customerInfoMap.put("txnAmt","112555");
        customerInfoMap.put("txnSubType", "00");
        unionPayTrade.refund(customerInfoMap);
    }
}
