package cn.mrdear.pay.wechat.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Niu Li
 * @date 2016/10/29
 */
@XmlRootElement(name = "xml")
public class WechatRefundQuery {
    private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    //四选一
    private String transaction_id;//微信订单号
    private String out_trade_no;//商户系统内部的订单号
    private String out_refund_no;//商户侧传给微信的退款单号
    private String refund_id;//微信生成的退款单号，在申请退款接口有返回

@XmlRootElement(name = "xml")
public static class Response {
    private String return_code;
    private String return_msg;
    private String result_code;
    private String err_code;//错误代码
    private String err_code_des;//错误代码描述
    private String appid;
    private String mch_id;
    private String device_info;//设备信息
    private String nonce_str;
    private String sign;
    private String transaction_id;//微信订单号
    private String out_trade_no;//商户系统内部的订单号
    private String total_fee;//订单金额
    private String settlement_total_fee ;//去掉非充值代金券后订单金额
    private String fee_type;
    private String cash_fee;//现金支付金额
    private String refund_count;//退款记录数
    private String out_refund_no_$n;//商户退款单号
    private String refund_id_$n;//微信退款单号
    private String refund_channel_$n;//退款渠道
    private String refund_fee_$n;//申请退款金额
    private String settlement_refund_fee_$n;//退款金额
    private String refund_account;//退款资金来源
    private String coupon_type_$n ;//代金券类型
    private String coupon_refund_fee_$n;
    private String coupon_refund_count_$n;
    private String coupon_refund_batch_id_$n_$m;
    private String coupon_refund_id_$n_$m;
    private String coupon_refund_fee_$n_$m;
    private String refund_status_$n;//退款状态SUCCESS—退款成功FAIL—退款失败PROCESSING—退款处理中CHANGE—转入代发
    private String refund_recv_accout_$n;

    public Response(String return_code) {
        this.return_code = return_code;
    }
    public Response() {
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
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

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSettlement_total_fee() {
        return settlement_total_fee;
    }

    public void setSettlement_total_fee(String settlement_total_fee) {
        this.settlement_total_fee = settlement_total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getRefund_count() {
        return refund_count;
    }

    public void setRefund_count(String refund_count) {
        this.refund_count = refund_count;
    }

    public String getOut_refund_no_$n() {
        return out_refund_no_$n;
    }

    public void setOut_refund_no_$n(String out_refund_no_$n) {
        this.out_refund_no_$n = out_refund_no_$n;
    }

    public String getRefund_id_$n() {
        return refund_id_$n;
    }

    public void setRefund_id_$n(String refund_id_$n) {
        this.refund_id_$n = refund_id_$n;
    }

    public String getRefund_channel_$n() {
        return refund_channel_$n;
    }

    public void setRefund_channel_$n(String refund_channel_$n) {
        this.refund_channel_$n = refund_channel_$n;
    }

    public String getRefund_fee_$n() {
        return refund_fee_$n;
    }

    public void setRefund_fee_$n(String refund_fee_$n) {
        this.refund_fee_$n = refund_fee_$n;
    }

    public String getSettlement_refund_fee_$n() {
        return settlement_refund_fee_$n;
    }

    public void setSettlement_refund_fee_$n(String settlement_refund_fee_$n) {
        this.settlement_refund_fee_$n = settlement_refund_fee_$n;
    }

    public String getRefund_account() {
        return refund_account;
    }

    public void setRefund_account(String refund_account) {
        this.refund_account = refund_account;
    }

    public String getCoupon_type_$n() {
        return coupon_type_$n;
    }

    public void setCoupon_type_$n(String coupon_type_$n) {
        this.coupon_type_$n = coupon_type_$n;
    }

    public String getCoupon_refund_fee_$n() {
        return coupon_refund_fee_$n;
    }

    public void setCoupon_refund_fee_$n(String coupon_refund_fee_$n) {
        this.coupon_refund_fee_$n = coupon_refund_fee_$n;
    }

    public String getCoupon_refund_count_$n() {
        return coupon_refund_count_$n;
    }

    public void setCoupon_refund_count_$n(String coupon_refund_count_$n) {
        this.coupon_refund_count_$n = coupon_refund_count_$n;
    }

    public String getCoupon_refund_batch_id_$n_$m() {
        return coupon_refund_batch_id_$n_$m;
    }

    public void setCoupon_refund_batch_id_$n_$m(String coupon_refund_batch_id_$n_$m) {
        this.coupon_refund_batch_id_$n_$m = coupon_refund_batch_id_$n_$m;
    }

    public String getCoupon_refund_id_$n_$m() {
        return coupon_refund_id_$n_$m;
    }

    public void setCoupon_refund_id_$n_$m(String coupon_refund_id_$n_$m) {
        this.coupon_refund_id_$n_$m = coupon_refund_id_$n_$m;
    }

    public String getCoupon_refund_fee_$n_$m() {
        return coupon_refund_fee_$n_$m;
    }

    public void setCoupon_refund_fee_$n_$m(String coupon_refund_fee_$n_$m) {
        this.coupon_refund_fee_$n_$m = coupon_refund_fee_$n_$m;
    }

    public String getRefund_status_$n() {
        return refund_status_$n;
    }

    public void setRefund_status_$n(String refund_status_$n) {
        this.refund_status_$n = refund_status_$n;
    }

    public String getRefund_recv_accout_$n() {
        return refund_recv_accout_$n;
    }

    public void setRefund_recv_accout_$n(String refund_recv_accout_$n) {
        this.refund_recv_accout_$n = refund_recv_accout_$n;
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

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(String refund_id) {
        this.refund_id = refund_id;
    }
}
