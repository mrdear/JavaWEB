package cn.mrdear.pay.unionpay;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.mrdear.pay.unionpay.sdk.AcpService;
import cn.mrdear.pay.unionpay.sdk.SDKConfig;
import cn.mrdear.pay.util.DateUtil;

/**
 * @author Niu Li
 * @date 2016/10/29
 */
public class UnionPayClient {

    private HashMap<String,String> commonMap;

    private static Logger logger = LoggerFactory.getLogger(UnionPayConfig.class);
    /**
     * 初始化公共参数
     * @param commonMap 公共参数,在UnionPayConfig中配置
     */
    public UnionPayClient(HashMap<String, String> commonMap) {
        this.commonMap = commonMap;
    }

    /**
     * 开通token请求
     * @param paramMap 要传送的参数
     * @return 生成的form表单
     */
    public String tokenOpen(Map<String, String> paramMap,Map<String, String> customerInfo){
        paramMap.putAll(commonMap);
        paramMap.put("txnTime", DateUtil.formatDate(new Date(),DateUtil.YMDHMS));
        paramMap.put("txnType", "79");
        paramMap.put("txnSubType", "00");
        paramMap.put("frontUrl", UnionPayConfig.FRONTURL);
        paramMap.put("backUrl", UnionPayConfig.TOKEN_BACKURL);
        paramMap.put("tokenPayData", UnionPayConfig.TOKENPAYDATA);
        paramMap.put("customerInfo",AcpService.getCustomerInfo(customerInfo,paramMap.get("accNo"),UnionPayConfig.ENCODING_UTF8));
        //是否加密取决于商户是否开通了隐私加密权限
//      paramMap.put("customerInfo",AcpService.getCustomerInfoWithEncrypt(customerInfo,paramMap.get("accNo"),UnionPayConfig.ENCODING_UTF8));
        Map<String, String> reqData = AcpService.sign(paramMap,UnionPayConfig.ENCODING_UTF8);  			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();    						 //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
        String html = AcpService.createAutoFormHtml(requestFrontUrl,reqData,UnionPayConfig.ENCODING_UTF8);     //生成自动跳转的Html表单
        logger.debug("银联token请求:"+html);
        return html;
    }

    /**
     * 请求银联发送消费短信
     * @param paramMap 要传送的参数
     * @return 请求返回的结果
     */
    public Map<String, String> msgSend(Map<String, String> paramMap,Map<String, String> customerInfo){
        paramMap.putAll(commonMap);
        paramMap.put("txnType", "77");
        paramMap.put("txnTime", DateUtil.formatDate(new Date(),DateUtil.YMDHMS));
        paramMap.put("customerInfo",AcpService.getCustomerInfo(customerInfo,paramMap.get("accNo"),UnionPayConfig.ENCODING_UTF8));
        //是否加密取决于商户是否开通了隐私加密权限
//      paramMap.put("customerInfo",AcpService.getCustomerInfoWithEncrypt(customerInfo,paramMap.get("accNo"),UnionPayConfig.ENCODING_UTF8));
        Map<String, String> reqData = AcpService.sign(paramMap,UnionPayConfig.ENCODING_UTF8);			 //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();   								 //交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
        Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,UnionPayConfig.ENCODING_UTF8);
        logger.debug("银联短信请求结果:"+rspData.toString());
        return rspData;
    }

    /**
     * 消费请求
     * @param paramMap 需要传送的参数
     * @return 请求结果,消费结果返回异步通知
     */
    public Map<String,String> consume(Map<String, String> paramMap,Map<String, String> customerInfo){
        paramMap.putAll(commonMap);
        paramMap.put("txnType", "01");
        paramMap.put("frontUrl", UnionPayConfig.FRONTURL);
        paramMap.put("backUrl", UnionPayConfig.CONSUME_BACKURL);
        paramMap.put("customerIp", "127.0.0.1");
        paramMap.put("txnTime", DateUtil.formatDate(new Date(),DateUtil.YMDHMS));
        paramMap.put("customerInfo",AcpService.getCustomerInfo(customerInfo,paramMap.get("accNo"),UnionPayConfig.ENCODING_UTF8));
        //是否加密取决于商户是否开通了隐私加密权限
//      paramMap.put("customerInfo",AcpService.getCustomerInfoWithEncrypt(customerInfo,paramMap.get("accNo"),UnionPayConfig.ENCODING_UTF8));
        Map<String, String> reqData = AcpService.sign(paramMap,UnionPayConfig.ENCODING_UTF8);				//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
        String requestBackUrl = SDKConfig.getConfig().getBackRequestUrl();   							//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
        Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,UnionPayConfig.ENCODING_UTF8);	//发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
        logger.debug("银联消费请求结果:"+rspData.toString());
        return rspData;
    }

    /**
     * 交易退货类请求
     * @param paramMap 退货请求参数
     * @return 退货结果
     */
    public Map<String,String> refund(Map<String, String> paramMap){
        paramMap.putAll(commonMap);
        paramMap.put("txnType", "04");
        paramMap.put("txnSubType", "00");
        paramMap.put("frontUrl", UnionPayConfig.FRONTURL);
        paramMap.put("backUrl", UnionPayConfig.REFUND_BACKURL);
        paramMap.put("txnTime", DateUtil.formatDate(new Date(),DateUtil.YMDHMS));
        Map<String, String> reqData  = AcpService.sign(paramMap,UnionPayConfig.ENCODING_UTF8);
        String url = SDKConfig.getConfig().getBackRequestUrl();
        Map<String, String> rspData = AcpService.post(reqData, url,UnionPayConfig.ENCODING_UTF8);
        logger.debug("银行卡退款结果:"+rspData.toString());
        return rspData;
    }
}
