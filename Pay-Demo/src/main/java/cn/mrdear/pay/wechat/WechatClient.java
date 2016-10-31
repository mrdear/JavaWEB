package cn.mrdear.pay.wechat;


import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.TreeMap;

import cn.mrdear.pay.util.SignUtil;
import cn.mrdear.pay.util.WebUtils;
import cn.mrdear.pay.util.XmlUtil;
import cn.mrdear.pay.wechat.entity.WechatRefund;
import cn.mrdear.pay.wechat.entity.WechatRefundQuery;
import cn.mrdear.pay.wechat.entity.WechatUnifiedOrder;

/**
 * 微信交易客户端
 * @author Niu Li
 * @date 2016/10/29
 */
public class WechatClient {
    private String app_id;
    private String mch_id;
    private String app_secret;
    private String trade_type;

    private static Logger logger = LoggerFactory.getLogger(WechatClient.class);

    public WechatClient(String app_id, String mch_id, String app_secret, String trade_type) {
        this.app_id = app_id;
        this.mch_id = mch_id;
        this.app_secret = app_secret;
        this.trade_type = trade_type;
    }

    /**
     * 统一下单,会自动签名和补上noce_str
     * @return 下单后返回信息
     */
    public WechatUnifiedOrder.Response unifiedOrder(WechatUnifiedOrder unifiedOrder){
        unifiedOrder.setAppid(this.app_id);
        unifiedOrder.setMch_id(this.mch_id);
        unifiedOrder.setTrade_type(this.trade_type);
        unifiedOrder.setNotify_url(WechatConfig.NOTIFY_PAY);
        unifiedOrder.setNonce_str(nonce_str(16));
        unifiedOrder.setSign(sign(SignUtil.bean2TreeMap(unifiedOrder)).toUpperCase());
        String tempXmlStr = XmlUtil.beanToXml(new ByteArrayOutputStream(), unifiedOrder);
        String requestXml = tempXmlStr!= null?tempXmlStr.substring(55):"";
        logger.debug("xml转义后内容:"+requestXml);
        try {
            InputStreamEntity inputStreamEntity = new InputStreamEntity(
                    new ByteArrayInputStream(requestXml.getBytes("UTF-8")), ContentType.APPLICATION_XML);
            String resultXml = WebUtils.post(WechatConfig.UNIFIEDORDER_URL,inputStreamEntity);
            logger.debug("微信返回内容:"+resultXml);
            return resultXml!= null
                    ?XmlUtil.xmlToBean(resultXml, WechatUnifiedOrder.Response.class)
                    :new WechatUnifiedOrder.Response("FAIL");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new WechatUnifiedOrder.Response("FAIL");
    }

    /**
     * 微信退货请求
     * @param wechatRefund 退货请求参数
     * @return 请求返回内容
     */
    public WechatRefund.Response refund(WechatRefund wechatRefund){
        wechatRefund.setAppid(this.app_id);
        wechatRefund.setMch_id(this.mch_id);
        wechatRefund.setNonce_str(nonce_str(16));
        wechatRefund.setSign(sign(SignUtil.bean2TreeMap(wechatRefund)).toUpperCase());
        String tempXmlStr = XmlUtil.beanToXml(new ByteArrayOutputStream(), wechatRefund);
        String requestXml = tempXmlStr!= null?tempXmlStr.substring(55):"";
        try {
            InputStreamEntity inputStreamEntity = new InputStreamEntity(
                    new ByteArrayInputStream(requestXml.getBytes("UTF-8")), ContentType.APPLICATION_XML);
            String resultXml = WebUtils.post(WechatConfig.CERT_PATH,WechatConfig.MCH_ID,WechatConfig.REFUND_URL,inputStreamEntity);
            logger.debug("微信退货返回内容:"+resultXml);
            return resultXml!= null
                    ?XmlUtil.xmlToBean(resultXml, WechatRefund.Response.class)
                    :new WechatRefund.Response("FAIL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new WechatRefund.Response("FAIL");
    }

    /**
     * 微信退款查询
     * @param wechatRefundQuery 要查询的请求
     * @return 查询结果
     */
    public WechatRefundQuery.Response refundQuery(WechatRefundQuery wechatRefundQuery){
        wechatRefundQuery.setAppid(this.app_id);
        wechatRefundQuery.setMch_id(this.mch_id);
        wechatRefundQuery.setNonce_str(nonce_str(16));
        wechatRefundQuery.setSign(sign(SignUtil.bean2TreeMap(wechatRefundQuery)).toUpperCase());
        String tempXmlStr = XmlUtil.beanToXml(new ByteArrayOutputStream(), wechatRefundQuery);
        String requestXml = tempXmlStr!= null?tempXmlStr.substring(55):"";
        try {
            InputStreamEntity inputStreamEntity = new InputStreamEntity(
                    new ByteArrayInputStream(requestXml.getBytes("UTF-8")), ContentType.APPLICATION_XML);
            String resultXml = WebUtils.post(WechatConfig.REFUND_QUERY,inputStreamEntity);
            logger.debug("微信退货查询内容:"+resultXml);
            return resultXml!= null
                    ?XmlUtil.xmlToBean(resultXml, WechatRefundQuery.Response.class)
                    :new WechatRefundQuery.Response("FAIL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new WechatRefundQuery.Response("FAIL");
    }

    /**
     * 对请求进行签名
     * @param param 要签名的参数
     * @return
     */
    public String sign(TreeMap<String, ?> param){
        String paramUrl = SignUtil.joinKeyValue(new TreeMap<String, Object>(param),null,"&key="+this.app_secret,"&",true,"sign_type","sign");
        logger.debug("微信待签名串:"+paramUrl);
        MessageDigest digestUtils = DigestUtils.getMd5Digest();
        digestUtils.update(paramUrl.getBytes());
        byte[] sign = digestUtils.digest();
        String result = Hex.encodeHexString(sign);
        logger.debug("签名结果:"+result);
        return result;
    }


    /**
     * 微信nonce_str生成算法
     * @param bits 生成位数,选择64bit
     * @return 生成后的nonce_str
     */
    public String nonce_str(int bits) {
        final byte[] bytes;
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            if ((bits % 8) != 0) {
                throw new IllegalArgumentException("Size is not divisible by 8!");
            }
            bytes = new byte[bits / 8];
            secureRandom.nextBytes(bytes);
            return Hex.encodeHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis()+"";
    }
}
