package cn.mrdear.pay.alipay;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * 配置公共参数
 * @author Niu Li
 * @date 2016/10/29
 */
public final class AliPayConfig {
    /**
     * 应用号
     */
    public static String APP_ID = "XXXXXX";
    /**
     * 商户的私钥
     */
    public static String APP_PRIVATE_KEY = "/XXXXXXXX+W+89LOoSBJkgbtLpAqbriyDW09rWKqRTOaavVo8hP3h7NGhEDNYtlc7+/fMi14Qi+QWKpzl/HfGez52pAgMBAAECgYEAr2U8+NYkNOQclni066NzVytifYtQBSogsPkOkd5H0WCsGdKnNzykKBSC/jdgmquuIBkkXClwzygFOVEM/xuvLqzdCbyDmO8DXw5D1huHLTKn02iUSpER0EnhdEt8jBD1ETyB+7RVCzgryaGfclFhx4R1VbFMum9HYop/C6J4CAECQQDyLmw4Ni5U7slK98AXO03HwFBTv6Fiihni6rKaAKfRV7zy6rOx7pr/fEAf8dqsWihJV9p60RpN+bFZKelDcMPBAkEAxQKHRb5T/3xH2pztGEm3EFYnTmUPws01s/gLcAAZbPEMUVmQ6L+m/j0y7Zvm+HxXFb8+LOcRsHV5EKUo+a4z6QJBAIhujpi8LS27t7p8B8MOraqyNI55jjktA5hKD4OiiufsPY27mq/Kc5cqMBXO8hnWUIvA6lv2IJN87uQEMFzF0gECQGEMac61f3NvqYXLoBBC3+cwSZSYvDbeGAJbwmad5eiApey9DmFZZ83+H8Rk9FacCdowp1Q/T7t5YvYMJi843WECQQChncss2bsaXlnRVLt3NOFlei3kJ0Sjn6M+XgWipkGzgr8SCZbfJT5kXU+6bEZt6M4Ov/Vm7a30BKXa96GwTDST";
    /**
     * 编码
     */
    public static String CHARSET = "UTF-8";
    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    /**
     * 支付宝网关地址
     */
    private static String GATEWAY = "https://openapi.alipay.com/gateway.do";
    /**
     * 成功付款回调
     */
    public static String PAY_NOTIFY = "http://niuli.tunnel.qydev.com/notify/alipay_pay";
    /**
     * 支付成功回调
     */
    public static String REFUND_NOTIFY = "http://niuli.tunnel.qydev.com/notify/alipay_refund";
    /**
     * 前台通知地址
     */
    public static String RETURN_URL = "http://niuli.tunnel.qydev.com/notify/alipay_pay";
    /**
     * 参数类型
     */
    public static String PARAM_TYPE = "json";
    /**
     * 成功标识
     */
    public static final String SUCCESS_REQUEST = "TRADE_SUCCESS";
    /**
     * 交易关闭回调(当该笔订单全部退款完毕,则交易关闭)
     */
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    /**
     * 收款方账号
     */
    public static final String SELLER_ID = "xxxx@qq.com";
    /**
     * 支付宝请求客户端入口
     */
    private volatile static AlipayClient alipayClient = null;

    /**
     * 不可实例化
     */
    private AliPayConfig(){};

    /**
     * 双重锁单例
     * @return 支付宝请求客户端实例
     */
    public static AlipayClient getInstance(){
        if (alipayClient == null){
            synchronized (AliPayConfig.class){
                if (alipayClient == null){
                    alipayClient = new DefaultAlipayClient(GATEWAY,APP_ID,APP_PRIVATE_KEY,PARAM_TYPE,CHARSET,ALIPAY_PUBLIC_KEY);
                }
            }
        }
        return alipayClient;
    }

}
