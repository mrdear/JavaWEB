package cn.mrdear.pay.wechat.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信下单请求实例
 * @author Niu Li
 * @date 2016/10/29
 */
@XmlRootElement(name = "xml")
public class WechatUnifiedOrder {
    private String appid;// 应用ID appid 是
    private String mch_id;// 商户号 mch_id 是
    private String device_info;// 设备号 device_info 否
    private String nonce_str;// 随机字符串 nonce_str 是
    private String sign;// 签名 sign 是
    private String body;// 商品描述 body 是
    private String detail;// 商品详情 detail 否
    private String attach;// 附加数据 attach 否
    private String out_trade_no;// 商户订单号 out_trade_no 是
    private String fee_type;// 货币类型 fee_type 否 默认人民币：CNY
    private Integer total_fee;// 总金额 total_fee 是 单位分
    private String spbill_create_ip;// 终端IP spbill_create_ip 是
    private String time_start;// 交易起始时间 time_start 否
    private String time_expire;// 交易结束时间 time_expire 否
    private String goods_tag;// 商品标记 goods_tag 否
    private String notify_url;// 通知地址 notify_url 是
    private String trade_type;// 交易类型 trade_type 是
    private String limit_pay;// 指定支付方式 limit_pay 否
    private String open_id;// 用户标识 否

    @XmlRootElement(name = "xml")
    public static class Response {
        private String appid;
        private String mch_id;
        private String device_info;
        private String nonce_str;
        private String sign;// 签名 sign 是
        private String result_code;// 业务结果 result_code 是 SUCCESS/FAIL
        private String return_msg;//
        private String err_code;// 错误代码 err_code 否
        private String err_code_des;// 错误代码描述 err_code_des 否
        private String trade_type;// 交易类型 trade_type 是 APP
        private String prepay_id;// 预支付交易会话标识 prepay_id 是

        public Response(String result_code) {
            this.result_code = result_code;
        }
        public Response() {
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getDevice_info() {
            return device_info;
        }

        public void setDevice_info(String device_info) {
            this.device_info = device_info;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getErr_code() {
            return err_code;
        }

        public void setErr_code(String err_code) {
            this.err_code = err_code;
        }

        public String getErr_code_des() {
            return err_code_des;
        }

        public void setErr_code_des(String err_code_des) {
            this.err_code_des = err_code_des;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }
}
