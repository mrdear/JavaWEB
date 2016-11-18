/*
 * Copyright 2016, China MPay Co., Ltd. All right reserved.
 *
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF CHINA MPAY CO., LTD. THE CONTENTS OF THIS FILE MAY NOT BE
 * DISCLOSED TO THIRD PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART, WITHOUT THE PRIOR WRITTEN
 * PERMISSION OF CHINA MPAY CO., LTD.
 */
package cn.mrdear.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 交易订单流水表，是否有冲正，取消，终止之类 17
 *
 * */
@Entity
@Table(name = "tbl_pcard_order")
public class PcardOrder implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "order_id")
	private String orderId;// 订单ID
	@Column(name = "order_mnt_dt")
	private String orderMntDt;// 原所属商户订单日期(追踪对账用)
	@Column(name = "order_mnt_time")
	private String orderMntTime;// 原所属商户订单时间(追踪对账用)
	@Column(name = "order_mnt_code")
	private String orderMntCode;// 商户提交的交易订单号
	@Column(name = "order_mnt_id")
	private String orderMntId; // 原所属商户号
	@Column(name = "order_dt")
	private String orderDt;// 订单日期（yyyyMMdd）
	@Column(name = "order_time")
	private String orderTime;// 订单时间（yyyyMMddHHMISS）
	@Column(name = "order_type_id")
	private String orderTypeId;// 订单类型，关键字段，确定订单对账户的加金还是减金
	@Column(name = "acq_acct_id")
	private String acqAcctId;// 订单关联收单账号
	@Column(name = "acq_channel_id")
	private String acqChannelId;// 不同渠道，当订单加金时候（账户充值，购买虚拟卡）填写
	@Column(name = "order_acq_channel_code")
	private String orderAcqChannelCode;// 不同渠道的订单号，方便对账
	@Column(name = "acct_id")
	private String acctId;// 订单关联虚拟账号
	@Column(name = "order_amt")
	private Long orderAmt; // 订单金额，精确到分
	@Column(name = "curr_cd")
	private String currCd;// 订单币别，默认CNY
	@Column(name = "order_st")
	private String orderSt;// 订单状态
	@Column(name = "order_resp_cd")
	private String orderRespCd;// 订单交易响应码
	@Column(name = "order_resp_msg")
	private String orderRespMsg;// 订单交易响应描述
	@Column(name = "create_time")
	private Date createTime; // 创建时间
	@Column(name = "last_modify_time")
	private Date lastModifyTime;// 最后修改时间
	@Column(name = "st")
	private String st; // 状态(0:可用,1:不可用)
	@Column(name = "vcard_id")
	private String vcardId; // 支付卡号
	@Column(name = "dis_amt")
	private Long disAmt; // 优惠金额,便于统计
	@Column(name = "pay_amt")
	private Long payAmt; // 支付金额
	@Column(name = "term_id")
	private String termId;// 终端号
	// term_id,order_reserved,retrivl_ref
	@Column(name = "order_reserved")
	private String orderReserved;// 保留域
	@Column(name = "txn_type")
	private String txnType;// 交易类型
	@Column(name = "resp_st")
	private String respSt;// 状态00 正常 01 已消费撤销 02 已消费冲正 03 已消费撤销冲正 04 已退貨
	@Column(name = "ref_no")
	private String refNo;// 检索引用号
	@Column(name = "versions")
	private String versions;
	@Column(name = "sources")
	private String sources;
	@Column(name = "bank_card")
	// 所属银行
	private String bank_card;

	// 支付方式
	@Column(name = "pay_type")
	private String pay_type;
	@Column(name = "is_scancode")
	private String isScancode;// 是否为扫码订单
	@Column(name = "scancode")
	private String scancode;// 扫码编号
	@Column(name = "eancode")
	private String eancode;// 条形码
	@Column(name = "inst_id")
	private String instId; // 机构号
	@Column(name = "return_url")
	private String returnUrl; // 同步返回地址
	@Column(name = "notify_url")
	private String notifyUrl;// 异步通知地址
	@Column(name = "app_id")
	private String appId; // 应用ID

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppId() {
		return appId;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getInstId() {
		return instId;
	}

	public void setIsScancode(String isScancode) {
		this.isScancode = isScancode;
	}

	public String getIsScancode() {
		return isScancode;
	}

	public void setScancode(String scancode) {
		this.scancode = scancode;
	}

	public void setEancode(String eancode) {
		this.eancode = eancode;
	}

	public String getEancode() {
		return eancode;
	}

	public String getScancode() {
		return scancode;
	}

	public String getBank_card() {
		return bank_card;
	}

	public void setBank_card(String bank_card) {
		this.bank_card = bank_card;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setOrderReserved(String orderReserved) {
		this.orderReserved = orderReserved;
	}

	public String getOrderReserved() {
		return orderReserved;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setRespSt(String respSt) {
		this.respSt = respSt;
	}

	public String getRespSt() {
		return respSt;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTermId() {
		return termId;
	}

	public String getVcardId() {
		return vcardId;
	}

	public void setVcardId(String vcardId) {
		this.vcardId = vcardId;
	}

	public Long getDisAmt() {
		return disAmt;
	}

	public void setDisAmt(Long disAmt) {
		this.disAmt = disAmt;
	}

	public Long getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(Long payAmt) {
		this.payAmt = payAmt;
	}

	public void setOrderMntId(String orderMntId) {
		this.orderMntId = orderMntId;
	}

	public String getOrderMntId() {
		return orderMntId;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderMntDt() {
		return orderMntDt;
	}

	public void setOrderMntDt(String orderMntDt) {
		this.orderMntDt = orderMntDt;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderMntTime() {
		return orderMntTime;
	}

	public void setOrderMntTime(String orderMntTime) {
		this.orderMntTime = orderMntTime;
	}

	public String getOrderMntCode() {
		return orderMntCode;
	}

	public void setOrderMntCode(String orderMntCode) {
		this.orderMntCode = orderMntCode;
	}

	public String getOrderDt() {
		return orderDt;
	}

	public void setOrderDt(String orderDt) {
		this.orderDt = orderDt;
	}

	public String getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(String orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public String getOrderSt() {
		return orderSt;
	}

	public void setOrderSt(String orderSt) {
		this.orderSt = orderSt;
	}

	public String getAcqAcctId() {
		return acqAcctId;
	}

	public void setAcqAcctId(String acqAcctId) {
		this.acqAcctId = acqAcctId;
	}

	public String getAcqChannelId() {
		return acqChannelId;
	}

	public void setAcqChannelId(String acqChannelId) {
		this.acqChannelId = acqChannelId;
	}

	public String getOrderAcqChannelCode() {
		return orderAcqChannelCode;
	}

	public void setOrderAcqChannelCode(String orderAcqChannelCode) {
		this.orderAcqChannelCode = orderAcqChannelCode;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public Long getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(Long orderAmt) {
		this.orderAmt = orderAmt;
	}

	public String getCurrCd() {
		return currCd;
	}

	public void setCurrCd(String currCd) {
		this.currCd = currCd;
	}

	public String getOrderRespCd() {
		return orderRespCd;
	}

	public void setOrderRespCd(String orderRespCd) {
		this.orderRespCd = orderRespCd;
	}

	public String getOrderRespMsg() {
		return orderRespMsg;
	}

	public void setOrderRespMsg(String orderRespMsg) {
		this.orderRespMsg = orderRespMsg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Override
	public String toString() {
		return "PcardOrder [orderId=" + orderId + ", orderMntDt=" + orderMntDt
				+ ", orderMntTime=" + orderMntTime + ", orderMntCode="
				+ orderMntCode + ", orderMntId=" + orderMntId + ", orderDt="
				+ orderDt + ", orderTime=" + orderTime + ", orderTypeId="
				+ orderTypeId + ", acqAcctId=" + acqAcctId + ", acqChannelId="
				+ acqChannelId + ", orderAcqChannelCode=" + orderAcqChannelCode
				+ ", acctId=" + acctId + ", orderAmt=" + orderAmt + ", currCd="
				+ currCd + ", orderSt=" + orderSt + ", orderRespCd="
				+ orderRespCd + ", orderRespMsg=" + orderRespMsg
				+ ", createTime=" + createTime + ", lastModifyTime="
				+ lastModifyTime + ", st=" + st + ", vcardId=" + vcardId
				+ ", disAmt=" + disAmt + ", payAmt=" + payAmt + ", termId="
				+ termId + ", orderReserved=" + orderReserved + ", txnType="
				+ txnType + ", respSt=" + respSt + ", refNo=" + refNo
				+ ", versions=" + versions + ", sources=" + sources
				+ ", bank_card=" + bank_card + ", pay_type=" + pay_type
				+ ", isScancode=" + isScancode + ", scancode=" + scancode
				+ ", eancode=" + eancode + "]";
	}

}
