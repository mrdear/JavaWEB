package cn.mrdear.pay.wechat;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import cn.mrdear.pay.util.SignUtil;
import cn.mrdear.pay.util.XmlUtil;
import cn.mrdear.pay.wechat.entity.WechatPayNotify;
import cn.mrdear.pay.wechat.entity.WechatRefund;
import cn.mrdear.pay.wechat.entity.WechatRefundQuery;
import cn.mrdear.pay.wechat.entity.WechatUnifiedOrder;


/**
 * 微信交易
 * @author Niu Li
 * @date 2016/10/29
 */
public class WechatTrade {

    private static Logger logger = LoggerFactory.getLogger(WechatTrade.class);
    /**
     * 微信统一下单
     * @param unifiedOrder 要下单的内容
     * @return 返回H5下单请求需要内容
     */
    public TreeMap<String,String> unifiedOrderRequest(WechatUnifiedOrder unifiedOrder){
        WechatUnifiedOrder.Response response =  WechatConfig.getInstance().unifiedOrder(unifiedOrder);
        if (response.getResult_code().equals(WechatConfig.SUCCESS_REQUEST)){
            TreeMap<String,String> prepareH5Pay = new TreeMap<String, String>();
            prepareH5Pay.put("appid", WechatConfig.APP_ID);
            prepareH5Pay.put("partnerid", WechatConfig.MCH_ID);
            prepareH5Pay.put("noncestr", WechatConfig.getInstance().nonce_str(16));
            prepareH5Pay.put("package", "Sign=WXPay");
            prepareH5Pay.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            prepareH5Pay.put("prepayid",response.getPrepay_id());
            prepareH5Pay.put("sign", WechatConfig.getInstance().sign(prepareH5Pay));
            return prepareH5Pay;
        }
        return null;
    }

    /**
     * 微信退款请求
     * @param refund 退款请求参数
     * @return 返回参数(同步接口,直接返回),只有return_code和result_code都成功则退款成功
     */
    public WechatRefund.Response refundRequest(WechatRefund refund){
        WechatRefund.Response response = WechatConfig.getInstance().refund(refund);
        return response;
    }
    /**
     * 微信退款查询请求
     * @param refund 退款请求参数
     * @return 返回参数(同步接口,直接返回),只有return_code和result_code都成功则查询成功
     */
    public WechatRefundQuery.Response refundQueryRequest(WechatRefundQuery refund){
        WechatRefundQuery.Response response = WechatConfig.getInstance().refundQuery(refund);
        return response;
    }

    /**
     * 微信回调验签
     * @param request  回调请求
     * @return true成功
     */
    public boolean verifyNotify(HttpServletRequest request){
        try {
            InputStream inputStream = request.getInputStream();
            WechatPayNotify notice = XmlUtil.xmlToBean(inputStream, WechatPayNotify.class);
            if (notice == null) return false;
            logger.debug("微信回调参数:"+ JSON.toJSONString(notice));
            String sign = WechatConfig.getInstance().sign(SignUtil.bean2TreeMap(notice));
            boolean ischeck = sign.equals(notice.getSign());
            logger.debug("微信验签结果:"+ischeck);
            return ischeck;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
