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
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("accNo", "6222021304008026228");
        Map<String,String> customerInfo = new HashMap<String,String>();
        customerInfo.put("certifTp", "01");						//证件类型
        customerInfo.put("certifId", "341126197709218366");		//证件号码
        customerInfo.put("customerNm", "全渠道");					//姓名
        customerInfo.put("phoneNo", "13552535506");			        //手机号
        paramMap.put("orderId", System.currentTimeMillis()+"");
        unionPayTrade.tokenOpen(paramMap,customerInfo);
    }

    /**
     * 测试消费短信
     */
    @Test
    public void testSmsCode(){
        UnionPayTrade unionPayTrade = new UnionPayTrade();
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("currencyCode", "156");
        paramMap.put("txnAmt", "1");
        paramMap.put("tokenPayData", "{token=6235240xxxx17620679&trId=62000000016&tokenLevel=40&tokenBegin=20161030154742&tokenEnd=20211029154742&tokenType=01}");
        Map<String,String> customerInfo = new HashMap<String,String>();
        customerInfo.put("certifTp", "01");						//证件类型
        customerInfo.put("certifId", "341126197709218366");		//证件号码
        customerInfo.put("customerNm", "全渠道");					//姓名
        customerInfo.put("phoneNo", "13552535506");			        //手机号
        String value = System.currentTimeMillis() + "";
        System.out.println(value);//商户订单
        paramMap.put("orderId", value);
        paramMap.put("txnSubType", "02");// 用于区分发送短信的类型：00——开通短信02——消费短信04——预授权451342
        unionPayTrade.msg(paramMap,customerInfo);
    }

    /**
     * 测试消费
     */
    @Test
    public void testConsume(){
        UnionPayTrade unionPayTrade = new UnionPayTrade();
        Map<String,String> paramMap = new HashMap<String,String>();
        paramMap.put("currencyCode", "156");
        paramMap.put("txnAmt", "200");
        paramMap.put("tokenPayData", "{token=6235xxxx000217620679&trId=62000000016&tokenLevel=40&tokenBegin=20161030154742&tokenEnd=20211029154742&tokenType=01}");
        Map<String,String> customerInfo = new HashMap<String,String>();
        customerInfo.put("smsCode", "13552535506");			        //手机号
        paramMap.put("orderId", "1477903831526");
        paramMap.put("txnSubType", "01");//01：自助消费，通过地址的方式区分前台消费和后台消费（含无跳转 支付）03：分期付款
        unionPayTrade.consume(paramMap,customerInfo);
    }

    @Test
    public void testRefund(){
        UnionPayTrade unionPayTrade = new UnionPayTrade();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("orderId",System.currentTimeMillis()+"");
        paramMap.put("origQryId","201610312015598471058");
        paramMap.put("txnAmt","112555");
        paramMap.put("txnSubType", "00");
        unionPayTrade.refund(paramMap);
    }
}
